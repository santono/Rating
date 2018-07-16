package rating.dao.impl;

import rating.dao.CityDAO;
import rating.domain.CityEntity;
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
public class CityDAOJDBCImpl implements CityDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select kod,name,ymapl,xx,yy,balloon from public.tb_cities where id=?";
    private static final String SQL_SELECT_ALL = "select id,kod,name,ymapl,xx,yy,balloon from public.tb_cities order by id";
    private static final String SQL_DELETE_REC = "delete from public.tb_cities where id=?";
    private static final String SQL_UPDATE_REC = "update public.tb_cities set kod=?,name=?,ymapl=?,xx=?,yy=?,balloon=? where id=?";
    private static final String SQL_INSERT_REC = "insert into public.tb_cities (kod,name,ymapl,xx,yy,balloon) values (?,?,?,?,?,?)";

    @Override
    @Transactional(readOnly = true)
    public CityEntity getById(final int wantedId) {
        RowMapper<CityEntity> mapper = new RowMapper<CityEntity>() {
            public CityEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                CityEntity city = new CityEntity();
                city.setId(wantedId);
                city.setKod(rs.getInt("kod"));
                city.setName(rs.getString("name"));
                city.setYmapl(rs.getString("ymapl"));
                city.setXx(rs.getString("xx"));
                city.setYy(rs.getString("yy"));
                city.setBalloon(rs.getString("balloon"));
                return city;
            }
        };
        CityEntity city;
        try {
            city = (CityEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            city = null;
        }
        return city;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CityEntity> getAll() {
        RowMapper<CityEntity> mapper = new RowMapper<CityEntity>() {
            public CityEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                CityEntity city = new CityEntity();
                city.setId(rs.getInt("id"));
                city.setKod(rs.getInt("kod"));
                city.setName(rs.getString("name"));
                city.setYmapl(rs.getString("ymapl"));
                city.setXx(rs.getString("xx"));
                city.setYy(rs.getString("yy"));
                city.setBalloon(rs.getString("balloon"));
                return city;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }


    @Override
    public void saveRecord(CityEntity cityEntity) {
        if (cityEntity.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE_REC,
                    new Object[]{cityEntity.getKod(),
                            cityEntity.getName(),
                            cityEntity.getYmapl(),
                            cityEntity.getXx(),
                            cityEntity.getYy(),
                            cityEntity.getBalloon(),
                            cityEntity.getId()
                    });
        } else {
            insertRecord(cityEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_REC, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final CityEntity cityEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_REC, new String[]{"id"});
                        pst.setInt(1, (int) cityEntity.getKod());
                        pst.setString(2, cityEntity.getName());
                        pst.setString(3, cityEntity.getYmapl());
                        pst.setString(4, cityEntity.getXx());
                        pst.setString(5, cityEntity.getYy());
                        pst.setString(6, cityEntity.getBalloon());
                        return pst;
                    }
                },
                keyHolder);
        cityEntity.setId(keyHolder.getKey().intValue());

        //To change body of implemented methods use File | Settings | File Templates.
    }
}
