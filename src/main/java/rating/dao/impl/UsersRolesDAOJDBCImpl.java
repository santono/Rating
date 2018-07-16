package rating.dao.impl;


import rating.dao.UsersRolesDAO;
import rating.domain.UsersRolesEntity;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UsersRolesDAOJDBCImpl implements UsersRolesDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select shifriduser, shifridrole from public.tb_users_roles where id=?";
    private static final String SQL_SELECT_ALL = "select id,shifriduser, shifridrole from public.tb_users_roles";
    private static final String SQL_SELECT_BY_USER = "select id,shifridrole from public.tb_users_roles where shifriduser=?";
    private static final String SQL_SELECT_NAMES_BY_USER = "select public.tb_roles.name from public.tb_users_roles join public.tb_roles on public.tb_users_roles.shifridrole=public.tb_roles.id where public.tb_users_roles.shifriduser=?";
    private static final String SQL_UPDATE_UR = "update public.tb_users_roles set shifriduser=?,shifridrole=? where id=?";
    private static final String SQL_INSERT_UR = "insert into public.tb_users_roles (shifriduser,shifridrole) values(?,?)";
    private static final String SQL_DELETE_UR = "delete from public.tb_users_roles  where id=?";
    private static final String SQL_DELETE_UR_FOR_USER = "delete from public.tb_users_roles  where shifriduser=?";

    @Transactional(readOnly = true)
    @Override
    public UsersRolesEntity getById(final Integer wantedId) {
        RowMapper<UsersRolesEntity> mapper = new RowMapper<UsersRolesEntity>() {
            public UsersRolesEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UsersRolesEntity urEntity = new UsersRolesEntity();
                urEntity.setId(wantedId);
                urEntity.setShifrIdUser(rs.getInt("shifriduser"));
                urEntity.setShifrIdRole(rs.getInt("shifridrole"));
                return urEntity;
            }
        };
        UsersRolesEntity urEntity = (UsersRolesEntity) jdbcTemplate.queryForObject(
                SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
        return urEntity;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UsersRolesEntity> getAll() {
        RowMapper<UsersRolesEntity> mapper = new RowMapper<UsersRolesEntity>() {
            public UsersRolesEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UsersRolesEntity urEntity = new UsersRolesEntity();
                urEntity.setId(rs.getInt("id"));
                urEntity.setShifrIdUser(rs.getInt("shifriduser"));
                urEntity.setShifrIdRole(rs.getInt("shifridrole"));
                return urEntity;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UsersRolesEntity> getAllForUser(Integer wantedId) {
        RowMapper<UsersRolesEntity> mapper = new RowMapper<UsersRolesEntity>() {
            public UsersRolesEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UsersRolesEntity urEntity = new UsersRolesEntity();
                urEntity.setId(rs.getInt("id"));
                urEntity.setShifrIdRole(rs.getInt("shifridrole"));
                return urEntity;
            }
        };
        List<UsersRolesEntity> urList = jdbcTemplate.query(
                SQL_SELECT_BY_USER, mapper, new Object[]{wantedId});
        return urList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> getAllRoleNamesForUser(Integer wantedId) {
        RowMapper<String> mapper = new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                String name = new String(rs.getString("name").trim());
                return name;
            }
        };
        List<String> namesList = jdbcTemplate.query(
                SQL_SELECT_NAMES_BY_USER, mapper, new Object[]{wantedId});
        return namesList;
    }

    @Override
    public void saveRecord(UsersRolesEntity urEntity) {
        if (urEntity.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE_UR,
                    new Object[]{urEntity.getShifrIdUser(),
                            urEntity.getShifrIdRole(),
                            urEntity.getId()});
        } else {
            insertRecord(urEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_UR, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final UsersRolesEntity urEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_UR, new String[]{"id"});
                        pst.setInt(1, urEntity.getShifrIdUser());
                        pst.setInt(2, urEntity.getShifrIdRole());
                        return pst;
                    }
                },
                keyHolder);
        urEntity.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void deleteRecordsForUser(int userId) {
        jdbcTemplate.update(SQL_DELETE_UR_FOR_USER, new Object[]{userId});
    }
}
