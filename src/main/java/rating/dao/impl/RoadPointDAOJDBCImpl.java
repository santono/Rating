package rating.dao.impl;


import rating.dao.RoadPointDAO;
import rating.domain.RoadPointEntity;
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
public class RoadPointDAOJDBCImpl implements RoadPointDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select id,idroad,lineno,xx,yy,balloon from public.tb_road_point where id=?";
    private static final String SQL_SELECT_ALL = "select id,idroad,lineno,xx,yy,balloon from public.tb_road_point order by id";
    private static final String SQL_SELECT_ALL_FOR_ROAD = "select id,idroad,lineno,xx,yy,balloon from public.tb_road_point where idroad=? order by lineno";
    private static final String SQL_SELECT_COUNT_FOR_ROAD = "select count(*) from public.tb_road_point where idroad=? ";
    private static final String SQL_DELETE_REC = "delete from public.tb_road_point where id=?";
    private static final String SQL_UPDATE_REC = "update public.tb_road_point set idroad=?,lineno=?,xx=?,yy=?,balloon=? where id=?";
    private static final String SQL_INSERT_REC = "insert into public.tb_road_point (idroad,lineno,xx,yy,balloon) values (?,?,?,?,?)";


    @Override
    @Transactional(readOnly = true)
    public RoadPointEntity getById(final int wantedId) {
        RowMapper<RoadPointEntity> mapper = new RowMapper<RoadPointEntity>() {
            public RoadPointEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadPointEntity roadPoint = new RoadPointEntity();
                roadPoint.setId(wantedId);
                roadPoint.setIdroad(rs.getInt("idroad"));
                roadPoint.setLineno(rs.getInt("lineno"));
                roadPoint.setXx(rs.getDouble("xx"));
                roadPoint.setYy(rs.getDouble("yy"));
                roadPoint.setBalloon(rs.getString("balloon"));
                return roadPoint;
            }
        };
        RoadPointEntity roadPoint;
        try {
            roadPoint = (RoadPointEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            roadPoint = null;
        }
        return roadPoint;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadPointEntity> getAll() {
        RowMapper<RoadPointEntity> mapper = new RowMapper<RoadPointEntity>() {
            public RoadPointEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadPointEntity roadPoint = new RoadPointEntity();
                roadPoint.setId(rs.getInt("id"));
                roadPoint.setIdroad(rs.getInt("idroad"));
                roadPoint.setLineno(rs.getInt("lineno"));
                roadPoint.setXx(rs.getDouble("xx"));
                roadPoint.setYy(rs.getDouble("yy"));
                roadPoint.setBalloon(rs.getString("balloon"));
                return roadPoint;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadPointEntity> getAllForRoad(int idroad) {
        RowMapper<RoadPointEntity> mapper = new RowMapper<RoadPointEntity>() {
            public RoadPointEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadPointEntity roadPoint = new RoadPointEntity();
                roadPoint.setId(rs.getInt("id"));
                roadPoint.setIdroad(rs.getInt("idroad"));
                roadPoint.setLineno(rs.getInt("lineno"));
                roadPoint.setXx(rs.getDouble("xx"));
                roadPoint.setYy(rs.getDouble("yy"));
                roadPoint.setBalloon(rs.getString("balloon"));
                return roadPoint;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_ROAD, mapper, new Object[]{idroad});
    }

    @Override
    @Transactional(readOnly = true)
    public int getCountPointsForRoad(int wantedId) {
        return (int) jdbcTemplate.queryForObject(SQL_SELECT_COUNT_FOR_ROAD, Integer.class, new Object[]{wantedId});
    }

    @Override
    public void saveRecord(RoadPointEntity roadPointEntity) {
        if (roadPointEntity.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE_REC,
                    new Object[]{roadPointEntity.getIdroad(),
                            roadPointEntity.getLineno(),
                            roadPointEntity.getXx(),
                            roadPointEntity.getYy(),
                            roadPointEntity.getBalloon(),
                            roadPointEntity.getId()
                    });
        } else {
            insertRecord(roadPointEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_REC, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final RoadPointEntity roadPointEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_REC, new String[]{"id"});
                        pst.setInt(1, roadPointEntity.getIdroad());
                        pst.setInt(2, roadPointEntity.getLineno());
                        pst.setDouble(3, roadPointEntity.getXx());
                        pst.setDouble(4, roadPointEntity.getYy());
                        pst.setString(5, roadPointEntity.getBalloon());
                        return pst;
                    }
                },
                keyHolder);
        roadPointEntity.setId(keyHolder.getKey().intValue());
    }
}
