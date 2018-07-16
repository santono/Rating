package rating.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rating.dao.UStepDAO;
import rating.domain.UStepEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
@Transactional
public class UStepDAOJDBCImpl implements UStepDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select name,shortname,kind from public.tb_usteps where id=?";
    private static final String SQL_SELECT_ALL   = "select id,name,shortname,kind from public.tb_usteps order by id";
    private static final String SQL_DELETE_US    = "delete from public.tb_usteps where id=?";
    private static final String SQL_UPDATE_US    = "update public.tb_usteps set name=?,shortname=?,kind=? where id=?";
    private static final String SQL_INSERT_US    = "insert into public.tb_usteps (name,shortname,kind) values (?,?,?)";

    @Override
    @Transactional(readOnly = true)
    public UStepEntity getById(final int wantedId) {
        RowMapper<UStepEntity> mapper = new RowMapper<UStepEntity>() {
            public UStepEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UStepEntity uStepEntity = new UStepEntity();
                uStepEntity.setId(wantedId);
                uStepEntity.setName(rs.getString("name"));
                uStepEntity.setShortName(rs.getString("shortName"));
                uStepEntity.setKind(rs.getInt("kind"));
                return uStepEntity;
            }
        };
        UStepEntity uStepEntity;
        try {
            uStepEntity = (UStepEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            uStepEntity = null;
        }
        return uStepEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UStepEntity> getAll() {
        // Maps a SQL result to a Java object
        RowMapper<UStepEntity> mapper = new RowMapper<UStepEntity>() {
            public UStepEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UStepEntity uStepEntity = new UStepEntity();
                uStepEntity.setId(rs.getInt("id"));
                uStepEntity.setName(rs.getString("name"));
                uStepEntity.setShortName(rs.getString("shortname"));
                uStepEntity.setKind(rs.getInt("kind"));
                return uStepEntity;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Override
    public void saveRecord(UStepEntity uStepEntity) {
        if (uStepEntity.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE_US,
                    new Object[]{uStepEntity.getName(),
                            uStepEntity.getShortName(),
                            uStepEntity.getKind(),
                            uStepEntity.getId()
                    });
        } else {
            insertRecord(uStepEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_US, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final UStepEntity uStepEntity) {
        //   KeyHolder keyHolder = new GeneratedKeyHolder();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_US, new String[]{"id"});
                        pst.setString(1, uStepEntity.getName());
                        pst.setString(2, uStepEntity.getShortName());
                        pst.setInt(3, uStepEntity.getKind());
                        return pst;
                    }
                },
                keyHolder);
        uStepEntity.setId(keyHolder.getKey().intValue());
    }
}
