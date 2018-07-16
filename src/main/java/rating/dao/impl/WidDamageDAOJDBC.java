package rating.dao.impl;

import rating.dao.WidDamageDAO;
import rating.domain.WidDamageEntity;
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
public class WidDamageDAOJDBC implements WidDamageDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select name from public.tb_wid_damage where id=?";
    private static final String SQL_SELECT_ALL   = "select id,name from public.tb_wid_damage order by id";
    private static final String SQL_DELETE_WR    = "delete from public.tb_wid_damage where id=?";
    private static final String SQL_UPDATE_WR    = "update public.tb_wid_damage set name=? where id=?";
    private static final String SQL_INSERT_WR    = "insert into public.tb_wid_damage (name) values (?)";


    @Override
    @Transactional(readOnly = true)
    public WidDamageEntity getById(final int wantedId) {
        RowMapper<WidDamageEntity> mapper = new RowMapper<WidDamageEntity>() {
            public WidDamageEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                WidDamageEntity widDamage = new WidDamageEntity();
                widDamage.setId(wantedId);
                widDamage.setName(rs.getString("name"));
                return widDamage;
            }
        };
        WidDamageEntity widDamage;
        try {
            widDamage = (WidDamageEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            widDamage = null;
        }
        return widDamage;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WidDamageEntity> getAll() {
        // Maps a SQL result to a Java object
        RowMapper<WidDamageEntity> mapper = new RowMapper<WidDamageEntity>() {
            public WidDamageEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                WidDamageEntity widDamage = new WidDamageEntity();
                widDamage.setId(rs.getInt("id"));
                widDamage.setName(rs.getString("name"));
                return widDamage;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Override
    public void saveRecord(WidDamageEntity widDamageEntity) {
        if (widDamageEntity.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE_WR,
                    new Object[]{widDamageEntity.getName(),
                            widDamageEntity.getId()
                    });
        } else {
            insertRecord(widDamageEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_WR, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final WidDamageEntity widDamageEntity) {
        //   KeyHolder keyHolder = new GeneratedKeyHolder();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_WR, new String[]{"id"});
                        pst.setString(1, widDamageEntity.getName());
                        return pst;
                    }
                },
                keyHolder);
        widDamageEntity.setId(keyHolder.getKey().intValue());
    }
}
