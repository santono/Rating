package rating.dao.impl;

import rating.dao.WidBlobDAO;
import rating.domain.WidBlobEntity;
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
public class WidBlobDAOJDBC implements WidBlobDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select name,mime from public.tb_wid_blob where id=?";
    private static final String SQL_SELECT_ID_BY_NAME = "select id from public.tb_wid_blob where mime=? limit 1";
    private static final String SQL_SELECT_ALL   = "select id,name,mime from public.tb_wid_blob order by id";
    private static final String SQL_DELETE_WB    = "delete from public.tb_wid_blob where id=?";
    private static final String SQL_UPDATE_WB    = "update public.tb_wid_blob set name=?,mime=? where id=?";
    private static final String SQL_INSERT_WB    = "insert into public.tb_wid_blob (name,mime) values (?,?)";


    @Override
    @Transactional(readOnly = true)
    public WidBlobEntity getById(final int wantedId) {
        RowMapper<WidBlobEntity> mapper = new RowMapper<WidBlobEntity>() {
            public WidBlobEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                WidBlobEntity widBlob = new WidBlobEntity();
                widBlob.setId(wantedId);
                widBlob.setName(rs.getString("name"));
                widBlob.setMime(rs.getString("mime"));
                return widBlob;
            }
        };
        WidBlobEntity widBlob;
        try {
            widBlob = (WidBlobEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            widBlob = null;
        }
        return widBlob;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WidBlobEntity> getAll() {
        // Maps a SQL result to a Java object
        RowMapper<WidBlobEntity> mapper = new RowMapper<WidBlobEntity>() {
            public WidBlobEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                WidBlobEntity widBlob = new WidBlobEntity();
                widBlob.setId(rs.getInt("id"));
                widBlob.setName(rs.getString("name"));
                widBlob.setMime(rs.getString("mime"));
                return widBlob;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Override
    public void saveRecord(WidBlobEntity widBlobEntity) {
        if (widBlobEntity.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE_WB,
                    new Object[]{widBlobEntity.getName(),
					             widBlobEntity.getMime(), 
                            widBlobEntity.getId()
                    });
        } else {
            insertRecord(widBlobEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_WB, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final WidBlobEntity widBlobEntity) {
        //   KeyHolder keyHolder = new GeneratedKeyHolder();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_WB, new String[]{"id"});
                        pst.setString(1, widBlobEntity.getName());
                        pst.setString(2, widBlobEntity.getMime());
                        return pst;
                    }
                },
                keyHolder);
        widBlobEntity.setId(keyHolder.getKey().intValue());
    }
    @Override
    @Transactional(readOnly = true)
    public int getIdByName(String name) {
        int retVal;
        RowMapper<WidBlobEntity> mapper = new RowMapper<WidBlobEntity>() {
            public WidBlobEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                WidBlobEntity widBlob = new WidBlobEntity();
                widBlob.setId(rs.getInt("id"));
                widBlob.setName("");
                widBlob.setMime("");
                return widBlob;
            }
        };
        WidBlobEntity widBlob;
        try {
            widBlob = (WidBlobEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_ID_BY_NAME, mapper, new Object[]{name});
            //		sql, new Object[] { wantedId }, mapper);
            retVal=widBlob.getId();
        } catch (EmptyResultDataAccessException e) {
            widBlob = null;
            retVal=-1;
        }
        return retVal;
        
    }

    @Override
    public void saveNewWidBlob(String name) {
        WidBlobEntity widBlobEntity=new WidBlobEntity();
        widBlobEntity.setName("Автоматически добавлено");
        widBlobEntity.setMime(name);
        insertRecord(widBlobEntity);
        
    }
    
}
