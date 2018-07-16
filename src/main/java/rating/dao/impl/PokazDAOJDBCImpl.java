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
import rating.dao.PokazDAO;
import rating.domain.PokazEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class PokazDAOJDBCImpl implements PokazDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select name,shortname,lineno,idowner from public.tb_pokaz where id=?";
    private static final String SQL_SELECT_ALL   = "select id,name,shortname,lineno,idowner from public.tb_pokaz order by id";
    private static final String SQL_DELETE_US    = "delete from public.tb_pokaz where id=?";
    private static final String SQL_UPDATE_US    = "update public.tb_pokaz set name=?,shortname=?,lineno=?,idowner=? where id=?";
    private static final String SQL_INSERT_US    = "insert into public.tb_pokaz (name,shortname,lineno,idowner) values (?,?,?,?)";

    @Override
    @Transactional(readOnly = true)
    public PokazEntity getById(final int wantedId) {
        RowMapper<PokazEntity> mapper = new RowMapper<PokazEntity>() {
            public PokazEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                PokazEntity pokazEntity = new PokazEntity();
                pokazEntity.setId(wantedId);
                pokazEntity.setName(rs.getString("name"));
                pokazEntity.setShortname(rs.getString("shortName"));
                pokazEntity.setLineno(rs.getInt("lineno"));
                pokazEntity.setIdowner(rs.getInt("idowner"));
                return pokazEntity;
            }
        };
        PokazEntity pokazEntity;
        try {
            pokazEntity = (PokazEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            pokazEntity = null;
        }
        return pokazEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PokazEntity> getAll() {
        // Maps a SQL result to a Java object
        RowMapper<PokazEntity> mapper = new RowMapper<PokazEntity>() {
            public PokazEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                PokazEntity pokazEntity = new PokazEntity();
                pokazEntity.setId(rs.getInt("id"));
                pokazEntity.setName(rs.getString("name"));
                pokazEntity.setShortname(rs.getString("shortname"));
                pokazEntity.setLineno(rs.getInt("lineno"));
                pokazEntity.setIdowner(rs.getInt("idowner"));
                return pokazEntity;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Override
    public void saveRecord(PokazEntity pokazEntity) {
        if (pokazEntity.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE_US,
                    new Object[]{pokazEntity.getName(),
                            pokazEntity.getShortname(),
                            pokazEntity.getLineno(),
                            pokazEntity.getIdowner(),
                            pokazEntity.getId()
                    });
        } else {
            insertRecord(pokazEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_US, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final PokazEntity pokazEntity) {
        //   KeyHolder keyHolder = new GeneratedKeyHolder();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_US, new String[]{"id"});
                        pst.setString(1, pokazEntity.getName());
                        pst.setString(2, pokazEntity.getShortname());
                        pst.setInt(3, pokazEntity.getLineno());
                        pst.setInt(4, pokazEntity.getIdowner());
                        return pst;
                    }
                },
                keyHolder);
        pokazEntity.setId(keyHolder.getKey().intValue());
    }
    
}
