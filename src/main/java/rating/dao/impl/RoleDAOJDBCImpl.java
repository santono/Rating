package rating.dao.impl;

import rating.dao.RoleDAO;
import rating.domain.RoleEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository("roleDAO")
@Transactional

public class RoleDAOJDBCImpl implements RoleDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected static Logger logger = Logger.getLogger("service");


    private static final String SQL_SELECT_BY_ID = "select id,name,description from public.tb_roles where id=?";
    private static final String SQL_SELECT_ALL = "select id,name,description from public.tb_roles order by id";
    private static final String SQL_SELECT_ALL_FOR_UNI = "select id,name,description from public.tb_roles where id in (2,3,6,7,8,13,14,15) order by id";
    private static final String SQL_SELECT_ALL_FOR_UNI_FAC = "select id,name,description from public.tb_roles where id in (3,7,8,15) order by id";

    @Override
    @Transactional(readOnly = true)
    public RoleEntity getById(final int wantedId) {
        RowMapper<RoleEntity> mapper = new RowMapper<RoleEntity>() {
            public RoleEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoleEntity role = new RoleEntity();
                role.setId(rs.getLong("id"));
                role.setName(rs.getString("name"));
                role.setDescription(rs.getString("description"));
                return role;
            }
        };
        RoleEntity role;
        //       logger.debug("inside getrole id="+wantedId);
        if (wantedId > 0) {
            role = (RoleEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
        } else {
            role = null;
        }
        return role;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleEntity> getAll() {
        RowMapper<RoleEntity> mapper = new RowMapper<RoleEntity>() {
            public RoleEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoleEntity role = new RoleEntity();
                role.setId(rs.getLong("id"));
                role.setName(rs.getString("name"));
                role.setDescription(rs.getString("description"));
                return role;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleEntity> getAllForUni() {
        RowMapper<RoleEntity> mapper = new RowMapper<RoleEntity>() {
            public RoleEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoleEntity role = new RoleEntity();
                role.setId(rs.getLong("id"));
                role.setName(rs.getString("name"));
                role.setDescription(rs.getString("description"));
                return role;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_UNI, mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleEntity> getAllForUniFac(int shifrUni, int shifrFac) {
        if (shifrUni <= 0) {
            return getAll();
        }
        if (shifrFac <= 0) {
            return getAllForUni();
        }
        RowMapper<RoleEntity> mapper = new RowMapper<RoleEntity>() {
            public RoleEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoleEntity role = new RoleEntity();
                role.setId(rs.getLong("id"));
                role.setName(rs.getString("name"));
                role.setDescription(rs.getString("description"));
                return role;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_UNI_FAC, mapper);
    }
}
