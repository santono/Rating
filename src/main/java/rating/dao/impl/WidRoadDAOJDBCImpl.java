package rating.dao.impl;


import rating.dao.WidRoadDAO;
import rating.domain.WidRoadEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class WidRoadDAOJDBCImpl implements WidRoadDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select name from public.tb_wid_road where id=?";
    private static final String SQL_SELECT_ALL = "select id,name from public.tb_wid_road order by id";
    private static final String SQL_DELETE_WR = "delete from public.tb_wid_road where id=?";
    private static final String SQL_UPDATE_WR = "update public.tb_wid_road set name=? where id=?";
    private static final String SQL_INSERT_WR = "insert into public.tb_wid_road (name) values (?)";

    @Override
    @Transactional(readOnly = true)
    public WidRoadEntity getById(final Integer wantedId) {
        RowMapper<WidRoadEntity> mapper = new RowMapper<WidRoadEntity>() {
            public WidRoadEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                WidRoadEntity widRoad = new WidRoadEntity();
                widRoad.setId(wantedId);
                widRoad.setName(rs.getString("name"));
                return widRoad;
            }
        };
        WidRoadEntity widRoad;
        try {
            widRoad = (WidRoadEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            widRoad = null;
        }
        return widRoad;

    }

    @Override
    @Transactional(readOnly = true)
    public List<WidRoadEntity> getAll() {

        // Maps a SQL result to a Java object
        RowMapper<WidRoadEntity> mapper = new RowMapper<WidRoadEntity>() {
            public WidRoadEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                WidRoadEntity widRoad = new WidRoadEntity();
                widRoad.setId(rs.getLong("id"));
                widRoad.setName(rs.getString("name"));
                return widRoad;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);

    }


    @Override
    public void deleteRecord(final int wantedId) {
        jdbcTemplate.update(SQL_DELETE_WR, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final WidRoadEntity widRoadEntity) {
        //   KeyHolder keyHolder = new GeneratedKeyHolder();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_WR, new String[]{"id"});
                        pst.setString(1, widRoadEntity.getName());
                        return pst;
                    }
                },
                keyHolder);
        widRoadEntity.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void saveRecord(WidRoadEntity widRoadEntity) {
        if (widRoadEntity.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE_WR,
                    new Object[]{widRoadEntity.getName(),
                            widRoadEntity.getId()
                    });
        } else {
            insertRecord(widRoadEntity);
        }

    }


}
