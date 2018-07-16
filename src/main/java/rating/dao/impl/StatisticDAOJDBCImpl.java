package rating.dao.impl;


import rating.dao.StatisticDAO;
import rating.domain.StatisticEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

@Repository
@Transactional
public class StatisticDAOJDBCImpl implements StatisticDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String SQL_SELECT_ALL = "select id,ip,shifrwrk,datewrkin,datewrkout  from public.tb_statistic order by datewrkin";
    private static final String SQL_SELECT_BY_ID = "select id,ip,shifrwrk,datewrkin,datewrkout from public.tb_statistic where id=?";
    private static final String SQL_UPDATE_REC = "update public.tb_statistic set datewrkout=? where id=?";
    private static final String SQL_INSERT_REC = "insert into public.tb_statistic (ip,shifrwrk) values (?,?)";


    @Override
    @Transactional(readOnly = true)
    public List<StatisticEntity> findAll() {
        RowMapper<StatisticEntity> mapper = new RowMapper<StatisticEntity>() {
            public StatisticEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                StatisticEntity sEntity = new StatisticEntity();
                sEntity.setId(rs.getInt("id"));
                sEntity.setIp(rs.getString("ip"));
                sEntity.setShifrwrk(rs.getInt("shifrwrk"));
                sEntity.setDatewrkin(rs.getDate("datewrkin"));
                sEntity.setDatewrkout(rs.getDate("datewrkin"));
                return sEntity;
            }
        };
        return jdbcTemplate.query(
                SQL_SELECT_ALL, mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public StatisticEntity getById(final int wantedId) {
        RowMapper<StatisticEntity> mapper = new RowMapper<StatisticEntity>() {
            public StatisticEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                StatisticEntity sEntity = new StatisticEntity();
                sEntity.setId(wantedId);
                sEntity.setIp(rs.getString("ip"));
                sEntity.setShifrwrk(rs.getInt("shifrwrk"));
                sEntity.setDatewrkin(rs.getDate("datewrkin"));
                sEntity.setDatewrkout(rs.getDate("datewrkin"));
                return sEntity;
            }
        };
        StatisticEntity sEntity = (StatisticEntity) jdbcTemplate.queryForObject(
                SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
        return sEntity;
    }

    @Override
    @Transactional(readOnly = false)
    public void saveRecord(StatisticEntity sEntity) {
        if (sEntity.getId() > 0) {
            java.sql.Timestamp sqlTimestamp =
                    new java.sql.Timestamp(new java.util.Date().getTime());

            jdbcTemplate.update(SQL_UPDATE_REC,
                    //   name=?,shifrkaf=?,shifrgaluz=?,shifrnapr=?,shifrf=?,okr=?,yearpri=?,kurs=?,nmbofstu=?,shifrspeci=?
                    new Object[]{sqlTimestamp,
                            sEntity.getId()});
        } else {
            insertRecord(sEntity);
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Transactional(readOnly = false)
    public void insertRecord(final StatisticEntity sEntity) {
        //   KeyHolder keyHolder = new GeneratedKeyHolder();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_REC, new String[]{"id"});
                        pst.setString(1, sEntity.getIp());
                        pst.setInt(2, sEntity.getShifrwrk());
                        return pst;
                    }
                },
                keyHolder);
        sEntity.setId(keyHolder.getKey().intValue());
    }

}
