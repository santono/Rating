package rating.dao.impl;


import rating.dao.WidExplorerDAO;
import rating.domain.WidExplorerEntity;
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
public class WidExplorerDAOJDBCImpl implements WidExplorerDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select name from public.tb_wid_explorer where id=?";
    private static final String SQL_SELECT_ALL   = "select id,name from public.tb_wid_explorer order by id";
    private static final String SQL_DELETE_WE    = "delete from public.tb_wid_explorer where id=?";
    private static final String SQL_UPDATE_WE    = "update public.tb_wid_explorer set name=? where id=?";
    private static final String SQL_INSERT_WE    = "insert into public.tb_wid_explorer (name) values (?)";


    @Override
    @Transactional(readOnly = true)
    public WidExplorerEntity getById(final int wantedId) {
        RowMapper<WidExplorerEntity> mapper = new RowMapper<WidExplorerEntity>() {
            public WidExplorerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                WidExplorerEntity widExplorer = new WidExplorerEntity();
                widExplorer.setId(wantedId);
                widExplorer.setName(rs.getString("name"));
                return widExplorer;
            }
        };
        WidExplorerEntity widExplorer;
        try {
            widExplorer = (WidExplorerEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            widExplorer = null;
        }
        return widExplorer;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WidExplorerEntity> getAll() {
        // Maps a SQL result to a Java object
        RowMapper<WidExplorerEntity> mapper = new RowMapper<WidExplorerEntity>() {
            public WidExplorerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                WidExplorerEntity widExplorer = new WidExplorerEntity();
                widExplorer.setId(rs.getInt("id"));
                widExplorer.setName(rs.getString("name"));
                return widExplorer;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Override
    public void saveRecord(WidExplorerEntity widExplorerEntity) {
        if (widExplorerEntity.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE_WE,
                    new Object[]{widExplorerEntity.getName(),
                            widExplorerEntity.getId()
                    });
        } else {
            insertRecord(widExplorerEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_WE, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final WidExplorerEntity widExplorerEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_WE, new String[]{"id"});
                        pst.setString(1, widExplorerEntity.getName());
                        return pst;
                    }
                },
                keyHolder);
        widExplorerEntity.setId(keyHolder.getKey().intValue());
    }
}
