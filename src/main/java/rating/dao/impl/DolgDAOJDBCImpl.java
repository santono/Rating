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
import rating.dao.DolgDAO;
import rating.domain.DolgEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class DolgDAOJDBCImpl implements DolgDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select name,shortname,kind from public.tb_dolg where id=?";
    private static final String SQL_SELECT_ALL   = "select id,name,shortname,kind from public.tb_dolg order by id";
    private static final String SQL_DELETE_REC   = "delete from public.tb_dolg where id=?";
    private static final String SQL_UPDATE_REC   = "update public.tb_dolg set name=?,shortname=?,kind=? where id=?";
    private static final String SQL_INSERT_REC   = "insert into public.tb_dolg (id,name,shortname,kind) values (?,?,?,?)";



    @Override
    @Transactional(readOnly = true)
    public DolgEntity getById(final int wantedId) {
        RowMapper<DolgEntity> mapper = new RowMapper<DolgEntity>() {
            public DolgEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                DolgEntity dolgEntity = new DolgEntity();
                dolgEntity.setId(wantedId);
                dolgEntity.setName(rs.getString("name"));
                dolgEntity.setShortname(rs.getString("shortName"));
                dolgEntity.setKind(rs.getInt("kind"));
                return dolgEntity;
            }
        };
        DolgEntity dolgEntity;
        try {
            dolgEntity = (DolgEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
        } catch (EmptyResultDataAccessException e) {
            dolgEntity = null;
        }
        return dolgEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DolgEntity> getAll() {
        RowMapper<DolgEntity> mapper = new RowMapper<DolgEntity>() {
            public DolgEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                DolgEntity dolgEntity = new DolgEntity();
                dolgEntity.setId(rs.getInt("id"));
                dolgEntity.setName(rs.getString("name"));
                dolgEntity.setShortname(rs.getString("shortname"));
                dolgEntity.setKind(rs.getInt("kind"));
                return dolgEntity;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Override
    public void saveRecord(DolgEntity dolgEntity) {
        if (dolgEntity.getId() > 0) {
            DolgEntity dE=getById(dolgEntity.getId());
            if (dE!=null) {
            jdbcTemplate.update(SQL_UPDATE_REC,
                    new Object[]{dolgEntity.getName(),
                            dolgEntity.getShortname(),
                            dolgEntity.getKind(),
                            dolgEntity.getId()
                    });
            } else {
              insertRecord(dolgEntity);
            }
        } else {
            insertRecord(dolgEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_REC, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final DolgEntity dolgEntity) {
        //   KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(SQL_INSERT_REC,
                new Object[]{
                        dolgEntity.getId(),
                        dolgEntity.getName(),
                        dolgEntity.getShortname(),
                        dolgEntity.getKind()
                });

/*
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_REC, new String[]{"id"});
                        pst.setInt(1, dolgEntity.getId());
                        pst.setString(2, dolgEntity.getName());
                        pst.setString(3, dolgEntity.getShortname());
                        pst.setInt(4, dolgEntity.getKind());
                        return pst;
                    }
                },
                keyHolder);
        dolgEntity.setId(keyHolder.getKey().intValue());
*/
    }

}
