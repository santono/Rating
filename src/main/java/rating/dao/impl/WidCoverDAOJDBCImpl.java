package rating.dao.impl;


import rating.dao.WidCoverDAO;
import rating.domain.WidCoverEntity;
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
public class WidCoverDAOJDBCImpl implements WidCoverDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select name,price from public.tb_wid_cover where id=?";
    private static final String SQL_SELECT_ALL   = "select id,name,price from public.tb_wid_cover order by id";
    private static final String SQL_UPDATE_WC    = "update public.tb_wid_cover set name=?,price=? where id=?";
    private static final String SQL_DELETE_WC    = "delete from public.tb_wid_cover where id=?";
    private static final String SQL_INSERT_WC    = "insert into public.tb_wid_cover (name,price) values (?,?)";

    @Override
    @Transactional(readOnly = true)
    public WidCoverEntity getById(final int wantedId) {
        RowMapper<WidCoverEntity> mapper = new RowMapper<WidCoverEntity>() {
            public WidCoverEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                WidCoverEntity widCover = new WidCoverEntity();
                widCover.setId(wantedId);
                widCover.setName(rs.getString("name"));
                widCover.setPrice(rs.getDouble("price"));
                return widCover;
            }
        };
        WidCoverEntity widCover;
        try {
            widCover = (WidCoverEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            widCover = null;
        }
        return widCover;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WidCoverEntity> getAll() {
        // Maps a SQL result to a Java object
        RowMapper<WidCoverEntity> mapper = new RowMapper<WidCoverEntity>() {
            public WidCoverEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                WidCoverEntity widCover = new WidCoverEntity();
                widCover.setId(rs.getInt("id"));
                widCover.setName(rs.getString("name"));
                widCover.setPrice(rs.getDouble("price"));
                return widCover;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Override
    public void saveRecord(WidCoverEntity widCoverEntity) {
        if (widCoverEntity.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE_WC,
                    new Object[]{widCoverEntity.getName(),
                                 widCoverEntity.getPrice(),
                                 widCoverEntity.getId()
                    });
        } else {
            insertRecord(widCoverEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_WC, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final WidCoverEntity widCoverEntity) {
        //   KeyHolder keyHolder = new GeneratedKeyHolder();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_WC, new String[]{"id"});
                        pst.setString(1, widCoverEntity.getName());
                        pst.setDouble(2, widCoverEntity.getPrice());
                        return pst;
                    }
                },
                keyHolder);
        widCoverEntity.setId(keyHolder.getKey().intValue());
    }
}
