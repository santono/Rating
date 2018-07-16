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
import rating.dao.NtrAuthDAO;
import rating.domain.NtrAuthEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class NtrAuthDAOJDBCImpl implements NtrAuthDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select id,idauth,idntr,amode,name,procent from public.tb_ntr_au where id=?";
    private static final String SQL_SELECT_ALL_FOR_NTR_A = "select id,idauth,amode,name,procent from public.tb_ntr_au where idntr=? order by id";
    private static final String SQL_DELETE_NTR_A = "delete from public.tb_ntr_au where id=?";
    private static final String SQL_DELETE_ALL_FOR_NTR_A = "delete from public.tb_ntr_au where idntr=?";
    private static final String SQL_UPDATE_NTR_A = "update public.tb_ntr_au set idauth=?,idntr=?,amode=?,name=?,procent=? where id=?";
    private static final String SQL_INSERT_NTR_A = "insert into public.tb_ntr_au (idauth,idntr,amode,name,procent) values (?,?,?,?,?)";

    @Override
    @Transactional(readOnly = true)
    public NtrAuthEntity getById(final int wantedId) {
        RowMapper<NtrAuthEntity> mapper = new RowMapper<NtrAuthEntity>() {
            public NtrAuthEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                NtrAuthEntity ntrAuth = new NtrAuthEntity();
                ntrAuth.setId(wantedId);
                ntrAuth.setName(rs.getString("name"));
                ntrAuth.setIdauth(rs.getInt("idauth"));
                ntrAuth.setIdntr(rs.getInt("idntr"));
                ntrAuth.setAmode(rs.getInt("amode"));
                ntrAuth.setProcent(rs.getInt("procent"));
                return ntrAuth;
            }
        };
        NtrAuthEntity ntrAuth;
        try {
            ntrAuth = (NtrAuthEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            ntrAuth = null;
        }
        return ntrAuth;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NtrAuthEntity> getAllForNtr(final int wantedId) {
        RowMapper<NtrAuthEntity> mapper = new RowMapper<NtrAuthEntity>() {
            public NtrAuthEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                NtrAuthEntity ntrAuth = new NtrAuthEntity();
                ntrAuth.setId(rs.getInt("id"));
                ntrAuth.setName(rs.getString("name"));
                ntrAuth.setIdauth(rs.getInt("idauth"));
                ntrAuth.setIdntr(wantedId);
                ntrAuth.setAmode(rs.getInt("amode"));
                ntrAuth.setProcent(rs.getInt("procent"));
                return ntrAuth;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_NTR_A, mapper, new Object[]{wantedId});
    }
    @Override
    public void saveRecord(NtrAuthEntity ntrAuthEntity) {
        if (ntrAuthEntity.getId() > 0) {
            jdbcTemplate.update(SQL_UPDATE_NTR_A,
                    new Object[]{
                            ntrAuthEntity.getIdauth(),
                            ntrAuthEntity.getIdntr(),
                            ntrAuthEntity.getAmode(),
                            ntrAuthEntity.getName(),
                            ntrAuthEntity.getProcent(),
                            ntrAuthEntity.getId()
                    });
        } else {
            insertRecord(ntrAuthEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_NTR_A, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final NtrAuthEntity ntrAuthEntity) {
        //   KeyHolder keyHolder = new GeneratedKeyHolder();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_NTR_A, new String[]{"id"});
                        pst.setInt(1, ntrAuthEntity.getIdauth());
                        pst.setInt(2, ntrAuthEntity.getIdntr());
                        pst.setInt(3, ntrAuthEntity.getAmode());
                        pst.setString(4, ntrAuthEntity.getName());
                        pst.setInt(5,ntrAuthEntity.getProcent());
                        return pst;
                    }
                },
                keyHolder);
        ntrAuthEntity.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void deleteAllForOwner(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_ALL_FOR_NTR_A, new Object[]{wantedId});
    }
}
