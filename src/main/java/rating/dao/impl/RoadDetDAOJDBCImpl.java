package rating.dao.impl;

import rating.dao.RoadDetDAO;
import rating.domain.RoadDetEntity;
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
public class RoadDetDAOJDBCImpl implements RoadDetDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
 // id ,idroad,idpodr,comment,latitudefr,longitudefr,latitudeto,longitudeto,posstart,posend,wsegodorog,wsegodoroghardcover,procent,cementbeton,asfaltbeton,
 // chernshosse,beloeshosse,bruschatka,degtegrunt,gruntovye,
 // pokrkat1,pokrkat2,pokrkat3,pokrkat4,pokrkat5,
 // mostsht,mostpm,mostshtder,mostpmder ,
 // trubysht,trubypm ,shifrwrk,
                                                        // 1      2      3    4
    private static final String SQL_SELECT_BY_ID = "select id ,idroad,idpodr,comment,"+
            //  5            6           7         8         9       10
            "latitudefr,longitudefr,latitudeto,longitudeto,posstart,posend,"+
         //      11              12          13          14          15
            "wsegodorog,wsegodoroghardcover,procent,cementbeton,asfaltbeton,"+
         //      16          17        18          19         20
            "chernshosse,beloeshosse,bruschatka,degtegrunt,gruntovye,"+
         //      21      22      23        24       25
            "pokrkat1,pokrkat2,pokrkat3,pokrkat4,pokrkat5,"+
         //      26        27        28   29
            "mostsht,mostpm,mostshtder,mostpmder ,"+
         //    30        31      32
            "trubysht,trubypm ,shifrwrk from public.tb_roads_det where id=?";
    private static final String SQL_SELECT_ALL = "select id ,idroad,idpodr,comment,"+
            "latitudefr,longitudefr,latitudeto,longitudeto,posstart,posend,"+
            "wsegodorog,wsegodoroghardcover,procent,cementbeton,asfaltbeton,"+
            "chernshosse,beloeshosse,bruschatka,degtegrunt,gruntovye,"+
            "pokrkat1,pokrkat2,pokrkat3,pokrkat4,pokrkat5,"+
            "mostsht,mostpm,mostshtder,mostpmder ,"+
            "trubysht,trubypm ,shifrwrk from public.tb_roads_det order by id";
    private static final String SQL_SELECT_ALL_FOR_ROAD = "select id ,idroad,idpodr,comment,"+
            "latitudefr,longitudefr,latitudeto,longitudeto,posstart,posend,"+
            "wsegodorog,wsegodoroghardcover,procent,cementbeton,asfaltbeton,"+
            "chernshosse,beloeshosse,bruschatka,degtegrunt,gruntovye,"+
            "pokrkat1,pokrkat2,pokrkat3,pokrkat4,pokrkat5,"+
            "mostsht,mostpm,mostshtder,mostpmder ,"+
            "trubysht,trubypm ,shifrwrk from public.tb_roads_det  where idroad=? order by id";
    private static final String SQL_SELECT_SUM_FOR_ROAD = "select "+
            "coalesce(sum(coalesce(wsegodorog,0.00)),0.00) wdorog,coalesce(sum(coalesce(wsegodoroghardcover,0.00)),0.00) wdoroghardcover,coalesce(sum(coalesce(procent,0.00)),0.00) prcnt,coalesce(sum(coalesce(cementbeton,0.00)),0.00) cbeton,coalesce(sum(coalesce(asfaltbeton,0.00)),0.00) abeton,"+
            "coalesce(sum(coalesce(chernshosse,0.00)),0.00) cshosse,coalesce(sum(coalesce(beloeshosse,0.00)),0.00) bshosse,coalesce(sum(coalesce(bruschatka,0.00)),0.00) brschtk,coalesce(sum(coalesce(degtegrunt,0.00)),0.00) dgrunt,coalesce(sum(coalesce(gruntovye,0.00)),0.00) grntv,"+
            "coalesce(sum(coalesce(pokrkat1,0.00)),0.00) pt1,coalesce(sum(coalesce(pokrkat2,0.00)),0.00) pt2,coalesce(sum(coalesce(pokrkat3,0.00)),0.00) pt3,coalesce(sum(coalesce(pokrkat4,0.00)),0.00) pt4,coalesce(sum(coalesce(pokrkat5,0.00)),0.00) pt5,"+
            "coalesce(sum(coalesce(mostsht,0.00)),0.00) msht,coalesce(sum(coalesce(mostpm,0.00)),0.00) mpm,coalesce(sum(coalesce(mostshtder,0.00)),0.00) mshtder,coalesce(sum(coalesce(mostpmder,0.00)),0.00) mpmder,"+
            "coalesce(sum(coalesce(trubysht,0.00)),0.00) tsht,coalesce(sum(coalesce(trubypm,0.00)),0.00) tpm  from public.tb_roads_det  where idroad=? and " +
            "((comment is null) or (length(coalesce(comment,''))<1 ))";
    private static final String SQL_SELECT_SUM_FOR_WID_ROAD = "select "+
            "coalesce(sum(coalesce(wsegodorog,0.00)),0.00) wdorog,coalesce(sum(coalesce(wsegodoroghardcover,0.00)),0.00) wdoroghardcover,coalesce(sum(coalesce(procent,0.00)),0.00) prcnt,coalesce(sum(coalesce(cementbeton,0.00)),0.00) cbeton,coalesce(sum(coalesce(asfaltbeton,0.00)),0.00) abeton,"+
            "coalesce(sum(coalesce(chernshosse,0.00)),0.00) cshosse,coalesce(sum(coalesce(beloeshosse,0.00)),0.00) bshosse,coalesce(sum(coalesce(bruschatka,0.00)),0.00) brschtk,coalesce(sum(coalesce(degtegrunt,0.00)),0.00) dgrunt,coalesce(sum(coalesce(gruntovye,0.00)),0.00) grntv,"+
            "coalesce(sum(coalesce(pokrkat1,0.00)),0.00) pt1,coalesce(sum(coalesce(pokrkat2,0.00)),0.00) pt2,coalesce(sum(coalesce(pokrkat3,0.00)),0.00) pt3,coalesce(sum(coalesce(pokrkat4,0.00)),0.00) pt4,coalesce(sum(coalesce(pokrkat5,0.00)),0.00) pt5,"+
            "coalesce(sum(coalesce(mostsht,0.00)),0.00) msht,coalesce(sum(coalesce(mostpm,0.00)),0.00) mpm,coalesce(sum(coalesce(mostshtder,0.00)),0.00) mshtder,coalesce(sum(coalesce(mostpmder,0.00)),0.00) mpmder,"+
            "coalesce(sum(coalesce(trubysht,0.00)),0.00) tsht,coalesce(sum(coalesce(trubypm,0.00)),0.00) tpm  from public.tb_roads_det join public.tb_roads on public.tb_roads_det.idroad=public.tb_roads.id where public.tb_roads.shifridwr=? and " +
            "((comment is null) or (length(coalesce(comment,''))<1 ))";
    private static final String SQL_SELECT_ALL_FOR_PODR = "select id ,idroad,idpodr,comment,"+
            "latitudefr,longitudefr,latitudeto,longitudeto,posstart,posend,"+
            "wsegodorog,wsegodoroghardcover,procent,cementbeton,asfaltbeton,"+
            "chernshosse,beloeshosse,bruschatka,degtegrunt,gruntovye,"+
            "pokrkat1,pokrkat2,pokrkat3,pokrkat4,pokrkat5,"+
            "mostsht,mostpm,mostshtder,mostpmder ,"+
            "trubysht,trubypm ,shifrwrk from public.tb_roads_det  where idpodr=? order by id";
    private static final String SQL_DELETE_REC = "delete from public.tb_roads_det where id=?";
                                                                                // 1         2        3
    private static final String SQL_UPDATE_REC = "update public.tb_roads_det set idroad=?,idpodr=?,comment=?,"+
            //  4             5              6           7            8           9
            "latitudefr=?,longitudefr=?,latitudeto=?,longitudeto=?,posstart=?,posend=?,"+
            //  10              11                12          13            14
            "wsegodorog=?,wsegodoroghardcover=?,procent=?,cementbeton=?,asfaltbeton=?,"+
            //  15              16            17        18          19
            "chernshosse=?,beloeshosse=?,bruschatka=?,degtegrunt=?,gruntovye=?,"+
            //  20         21        22         23         24
            "pokrkat1=?,pokrkat2=?,pokrkat3=?,pokrkat4=?,pokrkat5=?,"+
            //  25       26        27        28
            "mostsht=?,mostpm=?,mostshtder=?,mostpmder=? ,"+
            //  29         30        31
            "trubysht=?,trubypm=? ,shifrwrk=? where id=?";
                                                                            //       1     2      3
    private static final String SQL_INSERT_REC = "insert into public.tb_roads_det(idroad,idpodr,comment,"+
            //  4          5            6           7         8        9
            "latitudefr,longitudefr,latitudeto,longitudeto,posstart,posend,"+
            //  10              11            12        13          14
            "wsegodorog,wsegodoroghardcover,procent,cementbeton,asfaltbeton,"+
            //   15          16        17          18        19
            "chernshosse,beloeshosse,bruschatka,degtegrunt,gruntovye,"+
            //  20      21       22      23        24
            "pokrkat1,pokrkat2,pokrkat3,pokrkat4,pokrkat5,"+
            // 25      26       27        28
            "mostsht,mostpm,mostshtder,mostpmder ,"+
            //  29      30        31
            "trubysht,trubypm ,shifrwrk) values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,  ?)";


    @Override
    @Transactional(readOnly = true)
    public RoadDetEntity getById(final int wantedId) {
        RowMapper<RoadDetEntity> mapper = new RowMapper<RoadDetEntity>() {
            public RoadDetEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadDetEntity roadDet = new RoadDetEntity();
                roadDet.setId(wantedId);
                roadDet.setIdroad(rs.getInt("idroad"));
                roadDet.setIdpodr(rs.getInt("idpodr"));
                roadDet.setComment(rs.getString("comment"));
                roadDet.setLatitudefr(rs.getDouble("latitudefr"));
                roadDet.setLongitudefr(rs.getDouble("longitudefr"));
                roadDet.setLatitudeto(rs.getDouble("latitudefr"));
                roadDet.setLongitudeto(rs.getDouble("longitudeto"));
                roadDet.setPosstart(rs.getDouble("posstart"));
                roadDet.setPosend(rs.getDouble("posend"));
                roadDet.setWsegodorog(rs.getDouble("wsegodorog"));
                roadDet.setWsegodoroghardcover(rs.getDouble("wsegodoroghardcover"));
                roadDet.setProcent(rs.getDouble("procent"));
                roadDet.setCementbeton(rs.getDouble("cementbeton"));
                roadDet.setAsfaltbeton(rs.getDouble("asfaltbeton"));
                roadDet.setChernshosse(rs.getDouble("chernshosse"));
                roadDet.setBeloeshosse(rs.getDouble("beloeshosse"));
                roadDet.setBruschatka(rs.getDouble("bruschatka"));
                roadDet.setDegtegrunt(rs.getDouble("degtegrunt"));
                roadDet.setGruntovye(rs.getDouble("gruntovye"));
                roadDet.setPokrkat1(rs.getDouble("pokrkat1"));
                roadDet.setPokrkat2(rs.getDouble("pokrkat2"));
                roadDet.setPokrkat3(rs.getDouble("pokrkat3"));
                roadDet.setPokrkat4(rs.getDouble("pokrkat4"));
                roadDet.setPokrkat5(rs.getDouble("pokrkat5"));
                roadDet.setMostsht(rs.getDouble("mostsht"));
                roadDet.setMostpm(rs.getDouble("mostpm"));
                roadDet.setMostshtder(rs.getDouble("mostshtder"));
                roadDet.setMostpmder(rs.getDouble("mostpmder"));
                roadDet.setTrubysht(rs.getDouble("trubysht"));
                roadDet.setTrubypm(rs.getDouble("trubypm"));
                roadDet.setShifrwrk(rs.getInt("shifrwrk"));
                return roadDet;
            }
        };
        RoadDetEntity roadDet;
        try {
            roadDet = (RoadDetEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            roadDet = null;
        }
        return roadDet;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadDetEntity> getAll() {
        RowMapper<RoadDetEntity> mapper = new RowMapper<RoadDetEntity>() {
            public RoadDetEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadDetEntity roadDet = new RoadDetEntity();
                roadDet.setId(rs.getInt("id"));
                roadDet.setIdroad(rs.getInt("idroad"));
                roadDet.setIdpodr(rs.getInt("idpodr"));
                roadDet.setComment(rs.getString("comment"));
                roadDet.setLatitudefr(rs.getDouble("latitudefr"));
                roadDet.setLongitudefr(rs.getDouble("longitudefr"));
                roadDet.setLatitudeto(rs.getDouble("latitudefr"));
                roadDet.setLongitudeto(rs.getDouble("longitudeto"));
                roadDet.setPosstart(rs.getDouble("posstart"));
                roadDet.setPosend(rs.getDouble("posend"));
                roadDet.setWsegodorog(rs.getDouble("wsegodorog"));
                roadDet.setWsegodoroghardcover(rs.getDouble("wsegodoroghardcover"));
                roadDet.setProcent(rs.getDouble("procent"));
                roadDet.setCementbeton(rs.getDouble("cementbeton"));
                roadDet.setAsfaltbeton(rs.getDouble("asfaltbeton"));
                roadDet.setChernshosse(rs.getDouble("chernshosse"));
                roadDet.setBeloeshosse(rs.getDouble("beloeshosse"));
                roadDet.setBruschatka(rs.getDouble("bruschatka"));
                roadDet.setDegtegrunt(rs.getDouble("degtegrunt"));
                roadDet.setGruntovye(rs.getDouble("gruntovye"));
                roadDet.setPokrkat1(rs.getDouble("pokrkat1"));
                roadDet.setPokrkat2(rs.getDouble("pokrkat2"));
                roadDet.setPokrkat3(rs.getDouble("pokrkat3"));
                roadDet.setPokrkat4(rs.getDouble("pokrkat4"));
                roadDet.setPokrkat5(rs.getDouble("pokrkat5"));
                roadDet.setMostsht(rs.getDouble("mostsht"));
                roadDet.setMostpm(rs.getDouble("mostpm"));
                roadDet.setMostshtder(rs.getDouble("mostshtder"));
                roadDet.setMostpmder(rs.getDouble("mostpmder"));
                roadDet.setTrubysht(rs.getDouble("trubysht"));
                roadDet.setTrubypm(rs.getDouble("trubypm"));
                roadDet.setShifrwrk(rs.getInt("shifrwrk"));
                return roadDet;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadDetEntity> getAllForRoad(int idroad) {
        RowMapper<RoadDetEntity> mapper = new RowMapper<RoadDetEntity>() {
            public RoadDetEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadDetEntity roadDet = new RoadDetEntity();
                roadDet.setId(rs.getInt("id"));
                roadDet.setIdroad(rs.getInt("idroad"));
                roadDet.setIdpodr(rs.getInt("idpodr"));
                roadDet.setComment(rs.getString("comment"));
                roadDet.setLatitudefr(rs.getDouble("latitudefr"));
                roadDet.setLongitudefr(rs.getDouble("longitudefr"));
                roadDet.setLatitudeto(rs.getDouble("latitudefr"));
                roadDet.setLongitudeto(rs.getDouble("longitudeto"));
                roadDet.setPosstart(rs.getDouble("posstart"));
                roadDet.setPosend(rs.getDouble("posend"));
                roadDet.setWsegodorog(rs.getDouble("wsegodorog"));
                roadDet.setWsegodoroghardcover(rs.getDouble("wsegodoroghardcover"));
                roadDet.setProcent(rs.getDouble("procent"));
                roadDet.setCementbeton(rs.getDouble("cementbeton"));
                roadDet.setAsfaltbeton(rs.getDouble("asfaltbeton"));
                roadDet.setChernshosse(rs.getDouble("chernshosse"));
                roadDet.setBeloeshosse(rs.getDouble("beloeshosse"));
                roadDet.setBruschatka(rs.getDouble("bruschatka"));
                roadDet.setDegtegrunt(rs.getDouble("degtegrunt"));
                roadDet.setGruntovye(rs.getDouble("gruntovye"));
                roadDet.setPokrkat1(rs.getDouble("pokrkat1"));
                roadDet.setPokrkat2(rs.getDouble("pokrkat2"));
                roadDet.setPokrkat3(rs.getDouble("pokrkat3"));
                roadDet.setPokrkat4(rs.getDouble("pokrkat4"));
                roadDet.setPokrkat5(rs.getDouble("pokrkat5"));
                roadDet.setMostsht(rs.getDouble("mostsht"));
                roadDet.setMostpm(rs.getDouble("mostpm"));
                roadDet.setMostshtder(rs.getDouble("mostshtder"));
                roadDet.setMostpmder(rs.getDouble("mostpmder"));
                roadDet.setTrubysht(rs.getDouble("trubysht"));
                roadDet.setTrubypm(rs.getDouble("trubypm"));
                roadDet.setShifrwrk(rs.getInt("shifrwrk"));
                return roadDet;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_ROAD, mapper, new Object[]{idroad});
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadDetEntity> getAllForPodr(int idpodr) {
        RowMapper<RoadDetEntity> mapper = new RowMapper<RoadDetEntity>() {
            public RoadDetEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadDetEntity roadDet = new RoadDetEntity();
                roadDet.setId(rs.getInt("id"));
                roadDet.setIdroad(rs.getInt("idroad"));
                roadDet.setIdpodr(rs.getInt("idpodr"));
                roadDet.setComment(rs.getString("comment"));
                roadDet.setLatitudefr(rs.getDouble("latitudefr"));
                roadDet.setLongitudefr(rs.getDouble("longitudefr"));
                roadDet.setLatitudeto(rs.getDouble("latitudefr"));
                roadDet.setLongitudeto(rs.getDouble("longitudeto"));
                roadDet.setPosstart(rs.getDouble("posstart"));
                roadDet.setPosend(rs.getDouble("posend"));
                roadDet.setWsegodorog(rs.getDouble("wsegodorog"));
                roadDet.setWsegodoroghardcover(rs.getDouble("wsegodoroghardcover"));
                roadDet.setProcent(rs.getDouble("procent"));
                roadDet.setCementbeton(rs.getDouble("cementbeton"));
                roadDet.setAsfaltbeton(rs.getDouble("asfaltbeton"));
                roadDet.setChernshosse(rs.getDouble("chernshosse"));
                roadDet.setBeloeshosse(rs.getDouble("beloeshosse"));
                roadDet.setBruschatka(rs.getDouble("bruschatka"));
                roadDet.setDegtegrunt(rs.getDouble("degtegrunt"));
                roadDet.setGruntovye(rs.getDouble("gruntovye"));
                roadDet.setPokrkat1(rs.getDouble("pokrkat1"));
                roadDet.setPokrkat2(rs.getDouble("pokrkat2"));
                roadDet.setPokrkat3(rs.getDouble("pokrkat3"));
                roadDet.setPokrkat4(rs.getDouble("pokrkat4"));
                roadDet.setPokrkat5(rs.getDouble("pokrkat5"));
                roadDet.setMostsht(rs.getDouble("mostsht"));
                roadDet.setMostpm(rs.getDouble("mostpm"));
                roadDet.setMostshtder(rs.getDouble("mostshtder"));
                roadDet.setMostpmder(rs.getDouble("mostpmder"));
                roadDet.setTrubysht(rs.getDouble("trubysht"));
                roadDet.setTrubypm(rs.getDouble("trubypm"));
                roadDet.setShifrwrk(rs.getInt("shifrwrk"));
                return roadDet;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_PODR, mapper, new Object[]{idpodr});
    }


    @Override
    @Transactional(readOnly = true)
    public RoadDetEntity getSumDetForRoad(final int roadId) {
        RowMapper<RoadDetEntity> mapper = new RowMapper<RoadDetEntity>() {
            public RoadDetEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadDetEntity roadDet = new RoadDetEntity();
                roadDet.setId(1111111);
                roadDet.setIdroad(roadId);
                roadDet.setIdpodr(0);
                roadDet.setComment("");
                roadDet.setLatitudefr(0.0);
                roadDet.setLongitudefr(0.0);
                roadDet.setLatitudeto(0.0);
                roadDet.setLongitudeto(0.0);
                roadDet.setPosstart(0.0);
                roadDet.setPosend(0.0);
                roadDet.setWsegodorog(rs.getDouble("wdorog"));
                roadDet.setWsegodoroghardcover(rs.getDouble("wdoroghardcover"));
                roadDet.setProcent(rs.getDouble("prcnt"));
                roadDet.setCementbeton(rs.getDouble("cbeton"));
                roadDet.setAsfaltbeton(rs.getDouble("abeton"));
                roadDet.setChernshosse(rs.getDouble("cshosse"));
                roadDet.setBeloeshosse(rs.getDouble("bshosse"));
                roadDet.setBruschatka(rs.getDouble("brschtk"));
                roadDet.setDegtegrunt(rs.getDouble("dgrunt"));
                roadDet.setGruntovye(rs.getDouble("grntv"));
                roadDet.setPokrkat1(rs.getDouble("pt1"));
                roadDet.setPokrkat2(rs.getDouble("pt2"));
                roadDet.setPokrkat3(rs.getDouble("pt3"));
                roadDet.setPokrkat4(rs.getDouble("pt4"));
                roadDet.setPokrkat5(rs.getDouble("pt5"));
                roadDet.setMostsht(rs.getDouble("msht"));
                roadDet.setMostpm(rs.getDouble("mpm"));
                roadDet.setMostshtder(rs.getDouble("mshtder"));
                roadDet.setMostpmder(rs.getDouble("mpmder"));
                roadDet.setTrubysht(rs.getDouble("tsht"));
                roadDet.setTrubypm(rs.getDouble("tpm"));
                roadDet.setShifrwrk(0);
                return roadDet;
            }
        };
        RoadDetEntity roadDet;
        try {
            roadDet = (RoadDetEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_SUM_FOR_ROAD, mapper, new Object[]{roadId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            roadDet = null;
        }
        return roadDet;
    }

    @Override
    @Transactional(readOnly = true)
    public RoadDetEntity getSumDetForWidRoad(final int shifridwr) {
        RowMapper<RoadDetEntity> mapper = new RowMapper<RoadDetEntity>() {
            public RoadDetEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                RoadDetEntity roadDet = new RoadDetEntity();
                roadDet.setId(1111111);
                roadDet.setIdroad(1);
                roadDet.setIdpodr(0);
                roadDet.setComment("");
                roadDet.setLatitudefr(0.0);
                roadDet.setLongitudefr(0.0);
                roadDet.setLatitudeto(0.0);
                roadDet.setLongitudeto(0.0);
                roadDet.setPosstart(0.0);
                roadDet.setPosend(0.0);
                roadDet.setWsegodorog(rs.getDouble("wdorog"));
                roadDet.setWsegodoroghardcover(rs.getDouble("wdoroghardcover"));
                roadDet.setProcent(rs.getDouble("prcnt"));
                roadDet.setCementbeton(rs.getDouble("cbeton"));
                roadDet.setAsfaltbeton(rs.getDouble("abeton"));
                roadDet.setChernshosse(rs.getDouble("cshosse"));
                roadDet.setBeloeshosse(rs.getDouble("bshosse"));
                roadDet.setBruschatka(rs.getDouble("brschtk"));
                roadDet.setDegtegrunt(rs.getDouble("dgrunt"));
                roadDet.setGruntovye(rs.getDouble("grntv"));
                roadDet.setPokrkat1(rs.getDouble("pt1"));
                roadDet.setPokrkat2(rs.getDouble("pt2"));
                roadDet.setPokrkat3(rs.getDouble("pt3"));
                roadDet.setPokrkat4(rs.getDouble("pt4"));
                roadDet.setPokrkat5(rs.getDouble("pt5"));
                roadDet.setMostsht(rs.getDouble("msht"));
                roadDet.setMostpm(rs.getDouble("mpm"));
                roadDet.setMostshtder(rs.getDouble("mshtder"));
                roadDet.setMostpmder(rs.getDouble("mpmder"));
                roadDet.setTrubysht(rs.getDouble("tsht"));
                roadDet.setTrubypm(rs.getDouble("tpm"));
                roadDet.setShifrwrk(0);
                return roadDet;
            }
        };
        RoadDetEntity roadDet;
        try {
            roadDet = (RoadDetEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_SUM_FOR_WID_ROAD, mapper, new Object[]{shifridwr});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            roadDet = null;
        }
        return roadDet;
    }


    @Override
    public void saveRecord(RoadDetEntity roadDetEntity) {
        if (roadDetEntity.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE_REC,
                    new Object[]{roadDetEntity.getIdroad(),
                            roadDetEntity.getIdpodr(),
                            roadDetEntity.getComment(),
                            roadDetEntity.getLatitudefr(),
                            roadDetEntity.getLongitudefr(),
                            roadDetEntity.getLatitudeto(),
                            roadDetEntity.getLongitudeto(),
                            roadDetEntity.getPosstart(),
                            roadDetEntity.getPosend(),
                            roadDetEntity.getWsegodorog(),
                            roadDetEntity.getWsegodoroghardcover(),
                            roadDetEntity.getProcent(),
                            roadDetEntity.getCementbeton(),
                            roadDetEntity.getAsfaltbeton(),
                            roadDetEntity.getChernshosse(),
                            roadDetEntity.getBeloeshosse(),
                            roadDetEntity.getBruschatka(),
                            roadDetEntity.getDegtegrunt(),
                            roadDetEntity.getGruntovye(),
                            roadDetEntity.getPokrkat1(),
                            roadDetEntity.getPokrkat2(),
                            roadDetEntity.getPokrkat3(),
                            roadDetEntity.getPokrkat4(),
                            roadDetEntity.getPokrkat5(),
                            roadDetEntity.getMostsht(),
                            roadDetEntity.getMostpm(),
                            roadDetEntity.getMostshtder(),
                            roadDetEntity.getMostpmder(),
                            roadDetEntity.getTrubysht(),
                            roadDetEntity.getTrubypm(),
                            roadDetEntity.getShifrwrk(),
                            roadDetEntity.getId()

                    });
        } else {
            insertRecord(roadDetEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_REC, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final RoadDetEntity roadDetEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_REC, new String[]{"id"});
                        pst.setLong(1, roadDetEntity.getIdroad());
                        pst.setLong(2, roadDetEntity.getIdpodr());
                        pst.setString(3, roadDetEntity.getComment());
                        pst.setDouble(4, roadDetEntity.getLatitudefr());
                        pst.setDouble(5, roadDetEntity.getLongitudefr());

                        pst.setDouble(6, roadDetEntity.getLatitudeto());
                        pst.setDouble(7, roadDetEntity.getLongitudeto());
                        pst.setDouble(8, roadDetEntity.getPosstart());
                        pst.setDouble(9, roadDetEntity.getPosend());
                        pst.setDouble(10, roadDetEntity.getWsegodorog());

                        pst.setDouble(11, roadDetEntity.getWsegodoroghardcover());
                        pst.setDouble(12, roadDetEntity.getProcent());
                        pst.setDouble(13, roadDetEntity.getCementbeton());
                        pst.setDouble(14, roadDetEntity.getAsfaltbeton());
                        pst.setDouble(15, roadDetEntity.getChernshosse());

                        pst.setDouble(16, roadDetEntity.getBeloeshosse());
                        pst.setDouble(17, roadDetEntity.getBruschatka());
                        pst.setDouble(18, roadDetEntity.getDegtegrunt());
                        pst.setDouble(19, roadDetEntity.getGruntovye());
                        pst.setDouble(20, roadDetEntity.getPokrkat1());

                        pst.setDouble(21, roadDetEntity.getPokrkat2());
                        pst.setDouble(22, roadDetEntity.getPokrkat3());
                        pst.setDouble(23, roadDetEntity.getPokrkat4());
                        pst.setDouble(24, roadDetEntity.getPokrkat5());
                        pst.setDouble(25, roadDetEntity.getMostsht());

                        pst.setDouble(26, roadDetEntity.getMostpm());
                        pst.setDouble(27, roadDetEntity.getMostshtder());
                        pst.setDouble(28, roadDetEntity.getMostpmder());
                        pst.setDouble(29, roadDetEntity.getTrubysht());
                        pst.setDouble(30, roadDetEntity.getTrubypm());

                        pst.setInt(31, roadDetEntity.getShifrwrk());

                        return pst;
                    }
                },
                keyHolder);
        roadDetEntity.setId(keyHolder.getKey().intValue());
    }
}
