package rating.dao.impl;

import rating.dao.RoadDAO;
import rating.domain.RoadEntity;
import rating.dto.RoadRecDTO;
import rating.util.forbsgridcontroller.SortingClass;
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
public class RoadDAOJDBCImpl implements RoadDAO {


    @Autowired
    private JdbcTemplate jdbcTemplate;


    private static final String SQL_SELECT_BY_ID = "select id,kod,code,code_rus,name,dlina,kodtype,shifridwr,index from public.tb_roads where id=?";
    private static final String SQL_SELECT_ALL = "select id,kod,code,code_rus,name,dlina,kodtype,shifridwr,index from public.tb_roads";
    private static final String SQL_SELECT_ALL_FOR_TYPE = "select id,kod,code,code_rus,name,dlina,kodtype,shifridwr,index from public.tb_roads where kodtype=? order by id";
    private static final String SQL_SELECT_ALL_FOR_WID_ROAD = "select id,kod,code,code_rus,name,dlina,kodtype,shifridwr,index from public.tb_roads where coalesce(shifridwr,0)=? order by id";
    private static final String SQL_DELETE_REC = "delete from public.tb_roads where id=?";
    private static final String SQL_UPDATE_REC = "update public.tb_roads set kod=?,code=?,code_rus=?,name=?,dlina=?,kodtype=?,shifridwr=?,index=? where id=?";
    private static final String SQL_INSERT_REC = "insert into public.tb_roads (kod,code,code_rus,name,dlina,kodtype,shifridwr,index) values (?,?,?,?,?,?,?,?)";

    private static final String SQL_PAGE = " limit ? offset ?";
    private static final String SQL_SELECT_REC_PAGE = SQL_SELECT_ALL + SQL_PAGE;
    private static final String SQL_SELECT_REC_PAGE_SORT = SQL_SELECT_ALL;


    private static final String SQL_SELECT_COUNT_ROAD = "select count(*) from public.tb_roads";
    private static final String SQL_SELECT_COUNT_ROAD_FOR_WR = "select count(*) from public.tb_roads where coalesce(shifridwr,0)=?";


    @Override
    @Transactional(readOnly = true)
    public RoadEntity getById(final int wantedId) {
        RowMapper<RoadEntity> mapper = new RowMapper<RoadEntity>() {
            public RoadEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadEntity road = new RoadEntity();
                road.setId(wantedId);
                road.setKod(rs.getInt("kod"));
                road.setCode(rs.getString("code"));
                road.setCoderus(rs.getString("code_rus"));
                road.setName(rs.getString("name"));
                road.setDlina(rs.getDouble("dlina"));
                road.setKodtype(rs.getInt("kodtype"));
                road.setShifridwr(rs.getInt("shifridwr"));
                road.setIndex(rs.getString("index"));
                return road;
            }
        };
        RoadEntity road;
        try {
            road = (RoadEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            road = null;
        }
        return road;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadEntity> getAll() {
        RowMapper<RoadEntity> mapper = new RowMapper<RoadEntity>() {
            public RoadEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadEntity road = new RoadEntity();
                road.setId(rs.getInt("id"));
                road.setKod(rs.getInt("kod"));
                road.setCode(rs.getString("code"));
                road.setCoderus(rs.getString("code_rus"));
                road.setName(rs.getString("name"));
                road.setDlina(rs.getDouble("dlina"));
                road.setKodtype(rs.getInt("kodtype"));
                road.setShifridwr(rs.getInt("shifridwr"));
                road.setIndex(rs.getString("index"));
                return road;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadEntity> getAllForType(final int kodtype) {
        RowMapper<RoadEntity> mapper = new RowMapper<RoadEntity>() {
            public RoadEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadEntity road = new RoadEntity();
                road.setId(rs.getInt("id"));
                road.setKod(rs.getInt("kod"));
                road.setCode(rs.getString("code"));
                road.setCoderus(rs.getString("code_rus"));
                road.setName(rs.getString("name"));
                road.setDlina(rs.getDouble("dlina"));
                road.setKodtype(kodtype);
                road.setShifridwr(rs.getInt("shifridwr"));
                road.setIndex(rs.getString("index"));
                return road;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_TYPE, mapper, new Object[]{kodtype});
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadEntity> getAllForWidRoad(int shifridwr) {
    RowMapper<RoadEntity> mapper = new RowMapper<RoadEntity>() {
        public RoadEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            RoadEntity road = new RoadEntity();
            road.setId(rs.getInt("id"));
            road.setKod(rs.getInt("kod"));
            road.setCode(rs.getString("code"));
            road.setCoderus(rs.getString("code_rus"));
            road.setName(rs.getString("name"));
            road.setDlina(rs.getDouble("dlina"));
            road.setKodtype(rs.getInt("kodtype"));
            road.setShifridwr(rs.getInt("shifridwr"));
            road.setIndex(rs.getString("index"));
            return road;
        }
    };
    return jdbcTemplate.query(SQL_SELECT_ALL_FOR_WID_ROAD, mapper, new Object[]{shifridwr});
}


    @Override
    public void saveRecord(RoadEntity roadEntity) {
        if (roadEntity.getId() > 0) {
            jdbcTemplate.update(SQL_UPDATE_REC,
                    new Object[]{roadEntity.getKod(),
                            roadEntity.getCode(),
                            roadEntity.getCoderus(),
                            roadEntity.getName(),
                            roadEntity.getDlina(),
                            roadEntity.getKodtype(),
                            roadEntity.getShifridwr(),
                            roadEntity.getIndex(),
                            roadEntity.getId()
                    });
        } else {
            insertRecord(roadEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_REC, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final RoadEntity roadEntity) {
        // kod=?,code=?,code_rus=?,name=?,dlina=?,kodtype=?
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_REC, new String[]{"id"});
                        pst.setInt(1, roadEntity.getKod());
                        pst.setString(2, roadEntity.getCode());
                        pst.setString(3, roadEntity.getCoderus());
                        pst.setString(4, roadEntity.getName());
                        pst.setDouble(5, roadEntity.getDlina());
                        pst.setInt(6, roadEntity.getKodtype());
                        pst.setInt(7, roadEntity.getShifridwr());
                        pst.setString(8, roadEntity.getIndex());
                        return pst;
                    }
                },
                keyHolder);
        roadEntity.setId(keyHolder.getKey().intValue());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadRecDTO> getListGroup() {
        RowMapper<RoadRecDTO> mapper = new RowMapper<RoadRecDTO>() {
            public RoadRecDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadRecDTO roadRec = new RoadRecDTO();
                roadRec.setId(rs.getInt("id"));
                roadRec.setKod(rs.getInt("kod"));
                roadRec.setCode(rs.getString("code"));
                roadRec.setName(rs.getString("name"));
                roadRec.setKodtype(rs.getInt("kodtype"));
                roadRec.setDlina(rs.getDouble("dlina"));
                roadRec.setShifridwr(rs.getInt("shifridwr"));
                roadRec.setIndex(rs.getString("index"));
                roadRec.setMenur("");
                return roadRec;
            }
        };
        return jdbcTemplate.query(
                SQL_SELECT_ALL, mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadRecDTO> getPageListGroup(final int pageNo, final int pageRows, List<SortingClass> sorting, String whereStmnt) {
        int offset;
        int pageN;
        int pageR;
        int countRow;
        StringBuilder SQLStmnt;
        String str;
        int lineno;
        StringBuilder sb;
        RowMapper<RoadRecDTO> mapper = new RowMapper<RoadRecDTO>() {
            public RoadRecDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadRecDTO roadRec = new RoadRecDTO();
                roadRec.setId(rs.getInt("id"));
                roadRec.setKod(rs.getInt("kod"));
                roadRec.setCode(rs.getString("code"));
                roadRec.setName(rs.getString("name"));
                roadRec.setKodtype(rs.getInt("kodtype"));
                roadRec.setDlina(rs.getDouble("dlina"));
                roadRec.setShifridwr(rs.getInt("shifridwr"));
                roadRec.setIndex(rs.getString("index"));
                roadRec.setMenur("");


                return roadRec;
            }
        };
        pageN = pageNo;
        if (pageNo <= 0) {
            pageN = 1;
        }
        pageR = pageRows;
        if (pageR <= 0) {
            pageR = 10;
        }
        offset = pageRows * (pageNo - 1);
        countRow = getCountRec();
        if (countRow < offset) {
            offset = countRow;
        }
        lineno = 0;
        sb = new StringBuilder(0);
        if (sorting != null) {
            if (sorting.size() > 0) {
                for (SortingClass scl : sorting) {
                    lineno++;
                    str = "asc";
                    if (scl.getOrder().length() > 4) {
                        if (scl.getOrder().trim().startsWith("desc")) {
                            str = "desc";
                        }
                    }
                    if (scl.getField().trim().length() > 0) {
                        if (lineno == 1) {
                            sb.append("order by ");
                        } else {
                            sb.append(",");
                        }
                        sb.append(scl.getField().trim().toLowerCase());
                        sb.append(" " + str.trim());
                    }
                }
            }
        }
        SQLStmnt = new StringBuilder();
        SQLStmnt.append(SQL_SELECT_REC_PAGE_SORT);
        if (whereStmnt != null)
            if (whereStmnt.trim().length() > 0) {
                SQLStmnt.append(" " + whereStmnt);
            }
        if (sb.toString().length() > 0) {
            SQLStmnt.append(" " + sb.toString());
        }
        SQLStmnt.append(" " + SQL_PAGE);
        return jdbcTemplate.query(
                SQLStmnt.toString(), mapper, new Object[]{pageRows, offset});

    }

    @Override
    @Transactional(readOnly = true)
    public int getCountRec() {
        int countRow;
        countRow = (int) jdbcTemplate.queryForObject(SQL_SELECT_COUNT_ROAD, Integer.class);
        return countRow;
    }

    @Override
    @Transactional(readOnly = true)
    public int getCountRecForWantedShifrWR(int shifrWR) {
        int countRow;
        countRow = (int) jdbcTemplate.queryForObject(SQL_SELECT_COUNT_ROAD_FOR_WR, Integer.class,new Object[]{shifrWR});
        return countRow;
    }

    @Override
    @Transactional(readOnly = true)
    public int getCountRecWithFilter(String whereStmnt) {
        String SQLStmnt;
        if (whereStmnt == null)
            return getCountRec();
        if (whereStmnt.trim().length() < 1)
            return getCountRec();
        if (!whereStmnt.trim().toLowerCase().contains("where "))
            return getCountRec();

        SQLStmnt = SQL_SELECT_COUNT_ROAD + " " + whereStmnt;
        int countRow;
        countRow = (int) jdbcTemplate.queryForObject(SQLStmnt, Integer.class);
        return countRow;
    }


}
