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
import rating.dao.NtrPokazDAO;
import rating.domain.NtrPokazEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class NtrPokazDAOJDBCImpl implements NtrPokazDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select id,idpokaz,idntr from public.tb_ntr_det where id=?";
    private static final String SQL_SELECT_ALL_FOR_NTR_P = "select id,idpokaz from public.tb_ntr_det where idntr=? order by id";
    private static final String SQL_DELETE_NTR_P = "delete from public.tb_ntr_det where id=?";
    private static final String SQL_DELETE_ALL_FOR_NTR = "delete from public.tb_ntr_det where idntr=?";
    private static final String SQL_UPDATE_NTR_P = "update public.tb_ntr_det set idpokaz=?,idntr=? where id=?";
    private static final String SQL_INSERT_NTR_P = "insert into public.tb_ntr_det (idpokaz,idntr) values (?,?)";

    @Override
    @Transactional(readOnly = true)
    public NtrPokazEntity getById(final int wantedId) {
        RowMapper<NtrPokazEntity> mapper = new RowMapper<NtrPokazEntity>() {
            public NtrPokazEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                NtrPokazEntity ntrPokaz = new NtrPokazEntity();
                ntrPokaz.setId(wantedId);
                ntrPokaz.setIdpokaz(rs.getInt("idpokaz"));
                ntrPokaz.setIdntr(rs.getInt("idntr"));
                return ntrPokaz;
            }
        };
        NtrPokazEntity ntrPokaz;
        try {
            ntrPokaz = (NtrPokazEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            ntrPokaz = null;
        }
        return ntrPokaz;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NtrPokazEntity> getAllForNtr(final int wantedId) {
        RowMapper<NtrPokazEntity> mapper = new RowMapper<NtrPokazEntity>() {
            public NtrPokazEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                NtrPokazEntity ntrPokaz = new NtrPokazEntity();
                ntrPokaz.setId(rs.getInt("id"));
                ntrPokaz.setIdpokaz(rs.getInt("idpokaz"));
                ntrPokaz.setIdntr(wantedId);
                return ntrPokaz;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_NTR_P, mapper, new Object[]{wantedId});
    }
    @Override
    public void saveRecord(NtrPokazEntity ntrPokazEntity) {
        if (ntrPokazEntity.getId() > 0) {
            jdbcTemplate.update(SQL_UPDATE_NTR_P,
                    new Object[]{
                            ntrPokazEntity.getIdpokaz(),
                            ntrPokazEntity.getIdntr(),
                            ntrPokazEntity.getId()
                    });
        } else {
            insertRecord(ntrPokazEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_NTR_P, new Object[]{wantedId});
    }

    @Override
    public void deleteRecordsForNtr(final int wantedId) {
        jdbcTemplate.update(SQL_DELETE_ALL_FOR_NTR, new Object[]{wantedId});
    }


    @Override
    public void insertRecord(final NtrPokazEntity ntrPokazEntity) {
        //   KeyHolder keyHolder = new GeneratedKeyHolder();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_NTR_P, new String[]{"id"});
                        pst.setInt(1, ntrPokazEntity.getIdpokaz());
                        pst.setInt(2, ntrPokazEntity.getIdntr());
                        return pst;
                    }
                },
                keyHolder);
        ntrPokazEntity.setId(keyHolder.getKey().intValue());
    }
    
}
