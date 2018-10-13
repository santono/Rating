package rating.dao.impl;


import rating.dao.PodrDAO;
import rating.domain.PodrEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rating.dto.PodrEntityDTO;
import rating.dto.PokazBarByYearDTO;
import rating.dto.PokazEntityDTO;
import rating.dto.RatingNprRecDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional
public class PodrDAOJDBCImpl implements PodrDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID = "select name,owner,shortname from public.tb_predp where id=?";
    private static final String SQL_SELECT_BY_OWNER_ID = "select id,name,shortname from public.tb_predp where owner=? limit 1";
    private static final String SQL_SELECT_ALL = "select id,owner,name,shortname from public.tb_predp order by id";
    private static final String SQL_SELECT_ALL_FOR_OWNER = "select id,name,shortname from public.tb_predp where coalesce(owner,0)=? order by name";
    private static final String SQL_SELECT_ALL_FOR_OWNER_DTO = "select a.id,a.name,a.shortname,coalesce((select count(*) from public.tb_predp b where b.owner=a.id),0) amntofdet from public.tb_predp a where coalesce(owner,0)=? order by name";
    private static final String SQL_DELETE_POD = "delete from public.tb_predp where id=?";
    private static final String SQL_UPDATE_POD = "update public.tb_predp set name=?,owner=?,shortname=? where id=?";
    private static final String SQL_INSERT_POD = "insert into public.tb_predp (name,owner,shortname) values (?,?,?)";
    private static final String SQL_GET_LEVEL_BY_ID =
            "with recursive n as (\n" +
            "SELECT name na,0 l,id id,owner o from tb_predp where OWNER=0\n" +
            "union all\n" +
            "   select a.name na,n1.l+1,a.id l,a.owner w from tb_predp a\n" +
            "   inner join n n1 on a.owner=n1.id\n" +
            ")\n" +
            "select coalesce(l) from n\n" +
            "where id=?";
    private static final String SQL_SELECT_POKAZ_FOR_PREDP="select a.id,a.lineno,a.name,valverified, valnotverified from public.tb_pokaz a,fn_getpokazforpredp(a.id,?,?)";

    private static final String SQL_SELECT_POKAZS_FOR_PREDP="select p.id id,p.lineno lineno,p.name nam,coalesce(f.valverified,0) valverified, coalesce(f.countntr,0) countntr from tb_pokaz p left outer join fn_getpokazsforpredp(?,?) f on p.id=f.id_v order by p.lineno";

    private static final String SQL_SELECT_RATING_FOR_PREDP="select lineno,fio,cnt from fn_user_rating(?,?,?) where cnt>0";

    private static final String SQL_SELECT_POKAZ_BAR_SERIE_FOR_PREDP="select y_p,cnt_p from fn_diapokazforpredp(?,?,?,?)";

    @Override
    @Transactional(readOnly = true)
    public PodrEntity getById(final Integer wantedId) {
        RowMapper<PodrEntity> mapper = new RowMapper<PodrEntity>() {
            public PodrEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                PodrEntity podr = new PodrEntity();
                podr.setId(wantedId);
                podr.setName(rs.getString("name"));
                podr.setShifrIdOwner(rs.getInt("owner"));
                podr.setShortName(rs.getString("shortname"));
                return podr;
            }
        };
        PodrEntity podr;
        try {
            podr = (PodrEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            podr = null;
        }
        return podr;

    }

    @Override
    @Transactional(readOnly = true)
    public PodrEntity getByOwnerId(final Integer wantedOwnerId) {
        RowMapper<PodrEntity> mapper = new RowMapper<PodrEntity>() {
            public PodrEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                PodrEntity podr = new PodrEntity();
                podr.setId(rs.getInt("id"));
                podr.setName(rs.getString("name"));
                podr.setShifrIdOwner(wantedOwnerId);
                podr.setShortName(rs.getString("shortname"));
                return podr;
            }
        };
        PodrEntity podr;
        try {
            podr = (PodrEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_OWNER_ID, mapper, new Object[]{wantedOwnerId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            podr = null;
        }
        return podr;

    }
    
    
    @Override
    @Transactional(readOnly = true)
    public List<PodrEntity> getAll() {

        // Maps a SQL result to a Java object
        RowMapper<PodrEntity> mapper = new RowMapper<PodrEntity>() {
            public PodrEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                PodrEntity podr = new PodrEntity();
                podr.setId(rs.getLong("id"));
                podr.setName(rs.getString("name"));
                podr.setShifrIdOwner(rs.getInt("owner"));
                podr.setShortName(rs.getString("shortname"));

                return podr;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);

    }

    @Override
    @Transactional(readOnly = true)
    public List<PodrEntity> getAllForOwner(final int wantedId) {

        // Maps a SQL result to a Java object
        RowMapper<PodrEntity> mapper = new RowMapper<PodrEntity>() {
            public PodrEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                PodrEntity podr = new PodrEntity();
                podr.setId(rs.getLong("id"));
                podr.setName(rs.getString("name"));
                podr.setShortName(rs.getString("shortname"));
                podr.setShifrIdOwner(wantedId);
                return podr;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_OWNER, mapper, new Object[]{wantedId});

    }

    @Override
    @Transactional(readOnly = true)
    public List<PodrEntityDTO> getAllForOwnerDTO(final int wantedId) {

        // Maps a SQL result to a Java object
        RowMapper<PodrEntityDTO> mapper = new RowMapper<PodrEntityDTO>() {
            public PodrEntityDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                PodrEntityDTO podr = new PodrEntityDTO();
                podr.setId(rs.getLong("id"));
                podr.setName(rs.getString("name"));
                podr.setShortName(rs.getString("shortname"));
                int amntOfDetail;
                amntOfDetail=rs.getInt("amntofdet");
                if (amntOfDetail>0) {
                    podr.setCanbedeleted(false);
                } else {
                    podr.setCanbedeleted(true);
                }
                podr.setShifrIdOwner(wantedId);
                int level=getLevelById((int) podr.getId());
                podr.setLevel(level);
                return podr;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_OWNER_DTO, mapper, new Object[]{wantedId});

    }

    @Override
    public void deleteRecord(final int wantedId) {
        jdbcTemplate.update(SQL_DELETE_POD, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final PodrEntity podrEntity) {
        //   KeyHolder keyHolder = new GeneratedKeyHolder();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_POD, new String[]{"id"});
                        pst.setString(1, podrEntity.getName());
                        pst.setInt(2, (int) podrEntity.getShifrIdOwner());
                        pst.setString(3, podrEntity.getShortName());
                        return pst;
                    }
                },
                keyHolder);
        podrEntity.setId(keyHolder.getKey().intValue());
    }

    @Override
    public void saveRecord(PodrEntity podrEntity) {
        if (podrEntity.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE_POD,
                    new Object[]{podrEntity.getName(),
                            podrEntity.getShifrIdOwner(),
                            podrEntity.getShortName(),
                            podrEntity.getId()
                    });
        } else {
            insertRecord(podrEntity);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public int getLevelById(final Integer wantedId) {
//        System.out.println("getLevelById="+wantedId);
         int retVal;
         retVal=jdbcTemplate.queryForObject(SQL_GET_LEVEL_BY_ID,Integer.class,new Object[] {wantedId} );
         return retVal;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PokazEntityDTO> getPokazAllForPre(int wantedId,final int wantedY) {
        RowMapper<PokazEntityDTO> mapper = new RowMapper<PokazEntityDTO>() {
            public PokazEntityDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                PokazEntityDTO pe = new PokazEntityDTO();
                pe.setName(rs.getString("nam"));
                pe.setAmnt(rs.getDouble("valverified"));
                pe.setLineno(rs.getInt("lineno"));
                pe.setCountntr(rs.getInt("countntr"));
//                pe.setAmntn(rs.getDouble("valnotverified"));
                pe.setAmntn(0);
                return pe;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_POKAZS_FOR_PREDP, mapper, new Object[]{wantedId,wantedY});

    }
    @Override
    @Transactional(readOnly = true)
    public List<RatingNprRecDTO> getRatingAllForPre(int wantedId, int yfr,int yto) {
        RowMapper<RatingNprRecDTO> mapper = new RowMapper<RatingNprRecDTO>() {
            public RatingNprRecDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                RatingNprRecDTO re = new RatingNprRecDTO();
                re.setFio(rs.getString("fio"));
                re.setAmnt(rs.getInt("cnt"));
                re.setLineno(rs.getInt("lineno"));
                re.setNamePre("");
                re.setAmntProc(0.0);
                return re;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_RATING_FOR_PREDP, mapper, new Object[]{wantedId,yfr,yto});

    }
    @Override
    @Transactional(readOnly = true)
    public List<Integer> getPokazBarSerie(int pokazid,int shifrpre,int yfr,int yto) {
        RowMapper<Integer> mapper = new RowMapper<Integer>() {
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                int ri=rs.getInt("cnt_p");
                Integer re = new Integer(ri);
                return re;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_POKAZ_BAR_SERIE_FOR_PREDP, mapper, new Object[]{pokazid,shifrpre,yfr,yto});

    }
}
