package rating.dao.impl;

import rating.dao.RoadCityDAO;
import rating.domain.RoadCityEntity;
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
public class RoadCityDAOJDBCImpl implements RoadCityDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select id,idroad,idcity,lineno from public.tb_roads_cities where id=?";
    private static final String SQL_SELECT_ALL = "select id,idroad,idcity,lineno from public.tb_roads_cities order by id";
    private static final String SQL_SELECT_ALL_FOR_ROAD = "select id,idroad,idcity,lineno from public.tb_roads_cities where idroad=? order by id";
    private static final String SQL_SELECT_ALL_FOR_CITY = "select id,idroad,idcity,lineno from public.tb_roads_cities where idcity=? order by id";
    private static final String SQL_DELETE_REC = "delete from public.tb_roads_cities where id=?";
    private static final String SQL_UPDATE_REC = "update public.tb_roads_cities set idroad=?,idcity=?,lineno=? where id=?";
    private static final String SQL_INSERT_REC = "insert into public.tb_roads_cities (idroad,idcity,lineno) values (?,?,?)";


    @Override
    @Transactional(readOnly = true)
    public RoadCityEntity getById(final int wantedId) {
        RowMapper<RoadCityEntity> mapper = new RowMapper<RoadCityEntity>() {
            public RoadCityEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadCityEntity roadCity = new RoadCityEntity();
                roadCity.setId(wantedId);
                roadCity.setIdroad(rs.getInt("idroad"));
                roadCity.setIdcity(rs.getInt("idcity"));
                roadCity.setLineno(rs.getInt("lineno"));
                return roadCity;
            }
        };
        RoadCityEntity roadCity;
        try {
            roadCity = (RoadCityEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            roadCity = null;
        }
        return roadCity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadCityEntity> getAll() {
        RowMapper<RoadCityEntity> mapper = new RowMapper<RoadCityEntity>() {
            public RoadCityEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadCityEntity roadCity = new RoadCityEntity();
                roadCity.setId(rs.getInt("id"));
                roadCity.setIdroad(rs.getInt("idroad"));
                roadCity.setIdcity(rs.getInt("idcity"));
                roadCity.setLineno(rs.getInt("lineno"));
                return roadCity;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadCityEntity> getAllForRoad(int idroad) {
        RowMapper<RoadCityEntity> mapper = new RowMapper<RoadCityEntity>() {
            public RoadCityEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadCityEntity roadCity = new RoadCityEntity();
                roadCity.setId(rs.getInt("id"));
                roadCity.setIdroad(rs.getInt("idroad"));
                roadCity.setIdcity(rs.getInt("idcity"));
                roadCity.setLineno(rs.getInt("lineno"));
                return roadCity;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_ROAD, mapper, new Object[]{idroad});
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadCityEntity> getAllForCity(int idcity) {
        RowMapper<RoadCityEntity> mapper = new RowMapper<RoadCityEntity>() {
            public RoadCityEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadCityEntity roadCity = new RoadCityEntity();
                roadCity.setId(rs.getInt("id"));
                roadCity.setIdroad(rs.getInt("idroad"));
                roadCity.setIdcity(rs.getInt("idcity"));
                roadCity.setLineno(rs.getInt("lineno"));
                return roadCity;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_CITY, mapper, new Object[]{idcity});
    }

    @Override
    public void saveRecord(RoadCityEntity roadCityEntity) {
        if (roadCityEntity.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE_REC,
                    new Object[]{roadCityEntity.getIdroad(),
                            roadCityEntity.getIdcity(),
                            roadCityEntity.getLineno(),
                            roadCityEntity.getId()
                    });
        } else {
            insertRecord(roadCityEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_REC, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final RoadCityEntity roadCityEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_REC, new String[]{"id"});
                        pst.setInt(1, roadCityEntity.getIdroad());
                        pst.setInt(2, roadCityEntity.getIdcity());
                        pst.setInt(3, roadCityEntity.getLineno());
                        return pst;
                    }
                },
                keyHolder);
        roadCityEntity.setId(keyHolder.getKey().intValue());
    }
}
