package rating.dao.impl;


import org.springframework.core.annotation.Order;
import rating.dao.UserDAO;
import rating.domain.UserEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rating.dto.ChgPwdDTO;
import rating.dto.UserNPRDTOForVUEList;

import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("userDAO")
@Transactional
public class UserDAOJDBCImpl implements UserDAO {
    protected static Logger logger = Logger.getLogger("service");

    @Autowired
    private JdbcTemplate jdbcTemplate;
//    id,name,shifrpodr,datacreate,datadelete,active,login,password,tabno,email,fam,nam,otc,uzwan,ustep,dopinf,engfio,authorideliblaryru,hrefelibraryru,orcidscopuscom,hrefscopuscom,reseacheridwebofknoledgecom,hrefwebofknoledgecom,dopinfforsearch,shifrkat,shifrdol,shifruni,shifrfac,shifrkaf,shifrwr,dolgosnmr,usercode,statuscode,dataverification,shifridsup,
    private static final String SQL_SELECT_ALL = "select id, name,shifrpodr,datacreate,datadelete,active,login,password,tabno,email,fam,nam,otc,uzwan,ustep,dopinf,engfio,authorideliblaryru,hrefelibraryru,orcidscopuscom,hrefscopuscom,reseacheridwebofknoledgecom,hrefwebofknoledgecom,dopinfforsearch,shifrkat,shifrdol,shifruni,shifrfac,shifrkaf,shifrwr,dolgosnmr,usercode,statuscode,dataverification,shifridsup,dopslinfo,verifiedsupfio from public.tb_users order by id";
    private static final String SQL_SELECT_ALL_FOR_USER = "select id, name,shifrpodr,datacreate,datadelete,active,login,password,tabno,email,fam,nam,otc,uzwan,ustep,dopinf,engfio,authorideliblaryru,hrefelibraryru,orcidscopuscom,hrefscopuscom,reseacheridwebofknoledgecom,hrefwebofknoledgecom,dopinfforsearch,shifrkat,shifrdol,shifruni,shifrfac,shifrkaf,shifrwr,dolgosnmr,usercode,statuscode,dataverification,shifridsup,dopslinfo,verifiedsupfio from public.tb_users where id=?";
    private static final String SQL_SELECT_ALL_FOR_QUERY = "select id, name,shifrpodr,datacreate,datadelete,active,login,password,tabno,email,fam,nam,otc,uzwan,ustep,dopinf,engfio,authorideliblaryru,hrefelibraryru,orcidscopuscom,hrefscopuscom,reseacheridwebofknoledgecom,hrefwebofknoledgecom,dopinfforsearch,shifrkat,shifrdol,shifruni,shifrfac,shifrkaf,shifrwr,dolgosnmr,usercode,statuscode,dataverification,shifridsup,dopslinfo,verifiedsupfio from public.tb_users where upper(trim(coalesce(fam,'')))||' '||upper(trim(coalesce(nam,'')))||' '||upper(trim(coalesce(otc,''))) like ? order by fam";
    private static final String SQL_SELECT_ALL_FOR_PREDP = "select id, name,shifrpodr,datacreate,datadelete,active,login,password,tabno,email,fam,nam,otc,uzwan,ustep,dopinf,engfio,authorideliblaryru,hrefelibraryru,orcidscopuscom,hrefscopuscom,reseacheridwebofknoledgecom,hrefwebofknoledgecom,dopinfforsearch,shifrkat,shifrdol,shifruni,shifrfac,shifrkaf,shifrwr,dolgosnmr,usercode,statuscode,dataverification,shifridsup,dopslinfo,verifiedsupfio from public.tb_users where shifrpodr=?";
    private static final String SQL_SELECT_ALL_FOR_PREDP_OWNER = "with recursive vp(shifrpod,shortname,namepodr) as ("+
            "select a.id shifrpod,'','' from public.tb_predp a where a.id=?"+
            " union all"+
            " select d.id shifrpod,vp.shortname||' / '||trim(d.shortname),vp.namepodr||' / '||trim(d.name) namepodr from public.tb_predp d,vp  where d.owner=vp.shifrpod"+
            "),"+
            " vu as ("+
            "select b.id, b.name,b.shifrpodr,b.datacreate,b.datadelete,b.active,b.login,b.password,b.tabno,b.email,b.fam,b.nam,b.otc,b.uzwan,b.ustep,b.dopinf,b.engfio,b.authorideliblaryru,b.hrefelibraryru,b.orcidscopuscom,b.hrefscopuscom,b.reseacheridwebofknoledgecom,b.hrefwebofknoledgecom,b.dopinfforsearch,b.shifrkat,b.shifrdol,b.shifruni,b.shifrfac,b.shifrkaf,b.shifrwr,b.dolgosnmr,b.usercode,b.statuscode,b.dataverification,b.shifridsup,b.dopslinfo,b.verifiedsupfio,"+
            "coalesce((select coalesce(name,'') from public.tb_dolg where tb_dolg.id=b.shifrdol limit 1),'') dolg,"+
            "case coalesce(b.statuscode,0)"+
               "when 1 then 'Зарегистрован'"+
               "else ''"+
            "end status,"+
            "c.shortname,"+
            "c.namepodr "+
    " from public.tb_users b inner join vp c on c.shifrpod=b.shifrpodr"+
            ")"+
            "select * from vu";
    private static final String SQL_SELECT_PAGE_FOR_PREDP_OWNER = "with recursive vp(shifrpod,shortname,name) as ("+
            "select a.id shifrpod,'','' from public.tb_predp,'','' a where a.id=?"+
            " union all"+
            " select d.id shifrpod,vp.shortname||' / '||trim(d.shortname),vp.name||' / '||trim(d.name) from public.tb_predp d,vp  where d.owner=vp.shifrpod"+
            "),"+
            " vu as ("+
            "select b.id, b.name,b.shifrpodr,b.datacreate,b.datadelete,b.active,b.login,b.password,b.tabno,b.email,b.fam,b.nam,b.otc,b.uzwan,b.ustep,b.dopinf,b.engfio,b.authorideliblaryru,b.hrefelibraryru,b.orcidscopuscom,b.hrefscopuscom,b.reseacheridwebofknoledgecom,b.hrefwebofknoledgecom,b.dopinfforsearch,b.shifrkat,b.shifrdol,b.shifruni,b.shifrfac,b.shifrkaf,b.shifrwr,b.dolgosnmr,b.usercode,b.statuscode,b.dataverification,b.shifridsup,b.dopslinfo,b.verifiedsupfio from public.tb_users b inner join vp c on c.shifrpod=b.shifrpodr"+
            ")"+
            "select * from vu limit ? offset ?";
    private static final String SQL_SELECT_COUNT_FOR_PREDP_OWNER = "with recursive vp(shifrpod) as ("+
            "select a.id shifrpod from public.tb_predp a where a.id=?"+
            " union all"+
            " select d.id shifrpod from public.tb_predp d,vp  where d.owner=vp.shifrpod"+
            "),"+
            " vu as ("+
            "select b.id, b.name,b.shifrpodr,b.datacreate,b.datadelete,b.active,b.login,b.password,b.tabno,b.email,b.fam,b.nam,b.otc,b.uzwan,b.ustep,b.dopinf,b.engfio,b.authorideliblaryru,b.hrefelibraryru,b.orcidscopuscom,b.hrefscopuscom,b.reseacheridwebofknoledgecom,b.hrefwebofknoledgecom,b.dopinfforsearch,b.shifrkat,b.shifrdol,b.shifruni,b.shifrfac,b.shifrkaf,b.shifrwr,b.dolgosnmr,b.usercode,b.statuscode,b.dataverification,b.shifridsup,b.dopslinfo,b.verifiedsupfio from public.tb_users b inner join vp c on c.shifrpod=b.shifrpodr"+
            ")"+
            " select count(*) from vu";
    private static final String SQL_SELECT_BY_ID = "select id, name,shifrpodr,datacreate,datadelete,active,login,password,tabno,email,fam,nam,otc,uzwan,ustep,dopinf,engfio,authorideliblaryru,hrefelibraryru,orcidscopuscom,hrefscopuscom,reseacheridwebofknoledgecom,hrefwebofknoledgecom,dopinfforsearch,shifrkat,shifrdol,shifruni,shifrfac,shifrkaf,shifrwr,dolgosnmr,usercode,statuscode,dataverification,shifridsup,dopslinfo,verifiedsupfio from public.tb_users where id=?";
    private static final String SQL_SELECT_BY_LOGIN = "select id, name,shifrpodr,datacreate,datadelete,active,login,password,tabno,email,fam,nam,otc,uzwan,ustep,dopinf,engfio,authorideliblaryru,hrefelibraryru,orcidscopuscom,hrefscopuscom,reseacheridwebofknoledgecom,hrefwebofknoledgecom,dopinfforsearch,shifrkat,shifrdol,shifruni,shifrfac,shifrkaf,shifrwr,dolgosnmr,usercode,statuscode,dataverification,shifridsup,dopslinfo,verifiedsupfio from public.tb_users where login=?";
    private static final String SQL_SELECT_COUNT_BY_LOGIN = "select count(*) from public.tb_users where login=?";
    private static final String SQL_DELETE_USER = "delete from public.tb_users where id=?";
    //                                                                          1        2         3        4        5         6       7      8    9       10    11      12      13       14            15                  16              17               18                  19                      20                     21                22          23         24        25         26        27          28        29           30           31           32            33
    private static final String SQL_UPDATE_USER = "update public.tb_users set name=?,shifrpodr=?,active=?,login=?,password=?,tabno=?,email=?,fam=?,nam=?,otc=?,uzwan=?,ustep=?,dopinf=?,engfio=?,authorideliblaryru=?,hrefelibraryru=?,orcidscopuscom=?,hrefscopuscom=?,reseacheridwebofknoledgecom=?,hrefwebofknoledgecom=?,dopinfforsearch=?,shifrkat=?,shifrdol=?,shifruni=?,shifrfac=?,shifrkaf=?,shifrwr=?,dolgosnmr=?,usercode=?,statuscode=?,shifridsup=?,dopslinfo=?,verifiedsupfio=?  where id=?";
    private static final String SQL_SET_VERIFIED_USER = "update public.tb_users set statuscode=1,shifridsup=?,verifiedsupfio=?  where id=?";
    private static final String SQL_SET_PASSWORD_USER = "update public.tb_users set password=?  where id=?";
                                               //                                1       2        3     4       5      6     7    8   9   10  11     12    13     14       15                  16              17            18               19                          20                21            22       23       24       25       26      27        28       29        30         31        32
    private static final String SQL_INSERT_USER = "insert into public.tb_users (name,shifrpodr,active,login,password,tabno,email,fam,nam,otc,uzwan,ustep,dopinf,engfio,authorideliblaryru,hrefelibraryru,orcidscopuscom,hrefscopuscom,reseacheridwebofknoledgecom,hrefwebofknoledgecom,dopinfforsearch,shifrkat,shifrdol,shifruni,shifrfac,shifrkaf,shifrwr,dolgosnmr,usercode,statuscode,shifridsup,dopslinfo,verifiedsupfio) values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?)";
    private static final String SQL_SELECT_COUNT_LOGIN = "select count(*) from public.tb_users a where trim(a.login)=?";

    private static final String SQL_ORDER_0=" order by id asc";
    private static final String SQL_ORDER_10=" order by id desc";
    private static final String SQL_ORDER_1=" order by fam asc,nam asc,otc asc";
    private static final String SQL_ORDER_11=" order by fam desc,nam desc,otc desc";
    private static final String SQL_ORDER_2=" order by dolg asc";
    private static final String SQL_ORDER_12=" order by dolg desc";
    private static final String SQL_ORDER_3=" order by status asc";
    private static final String SQL_ORDER_13=" order by status desc";
    private static final String SQL_ORDER_4=" order by namepodr asc";
    private static final String SQL_ORDER_14=" order by namepodr desc";


    public static final Map<Integer, String> ordersMap = Collections.unmodifiableMap(new HashMap<Integer, String>() {
        {
            put(0,  SQL_ORDER_0);
            put(10, SQL_ORDER_10);
            put(1,  SQL_ORDER_1);
            put(11, SQL_ORDER_11);
            put(2,  SQL_ORDER_2);
            put(12, SQL_ORDER_12);
            put(3,  SQL_ORDER_3);
            put(13, SQL_ORDER_13);
            put(4,  SQL_ORDER_4);
            put(14, SQL_ORDER_14);
            //rest of code here
        }
    });
    
    public UserDAOJDBCImpl() {

    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getById(final int id) {
        RowMapper<UserEntity> mapper = new RowMapper<UserEntity>() {
            public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserEntity user = new UserEntity();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setDataCreate(rs.getDate("datacreate"));
                user.setDataDelete(rs.getDate("datadelete"));
                user.setActive(rs.getBoolean("active"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setTabno(rs.getInt("tabno"));
                user.setShifrPodr(rs.getInt("shifrpodr"));
                user.setEmail(rs.getString("email"));
                user.setFam(rs.getString("fam"));
                user.setNam(rs.getString("nam"));
                user.setOtc(rs.getString("otc"));
                user.setUzwan(rs.getInt("uzwan"));
                user.setUstep(rs.getInt("ustep"));
                user.setDopInf(rs.getString("dopinf"));
                user.setEngFio(rs.getString("engfio"));
                user.setAuthorIdEliblaryRu(rs.getString("authorideliblaryru"));
                user.setHrefElibraryRu(rs.getString("hrefelibraryru"));
                user.setOrcIdScopusCom(rs.getString("orcidscopuscom"));
                user.setHrefScopusCom(rs.getString("hrefscopuscom"));
                user.setReseacherIdWebOfKnoledgeCom(rs.getString("reseacheridwebofknoledgecom"));
                user.setHrefWebOfKnoledgeCom(rs.getString("hrefwebofknoledgecom"));
                user.setDopInfForSearch(rs.getString("dopinfforsearch"));
                user.setShifrKat(rs.getInt("shifrkat"));
                user.setShifrDol(rs.getInt("shifrdol"));
                user.setShifrUni(rs.getInt("shifruni"));
                user.setShifrFac(rs.getInt("shifrfac"));
                user.setShifrKaf(rs.getInt("shifrkaf"));
                user.setShifrWr(rs.getInt("shifrwr"));
                user.setDolgOsnMr(rs.getString("dolgosnmr"));
                user.setUserCode(rs.getLong("usercode"));
                user.setStatusCode(rs.getInt("statuscode"));
                user.setDataVerification(rs.getDate("dataVerification"));
                user.setShifrIdSup(rs.getLong("shifridsup"));
                user.setDopSlInfo(rs.getString("dopslinfo"));
                user.setVerifiedSupFIO(rs.getString("verifiedsupfio"));

                //     user.setRole(roleDAO.getById((int) rs.getLong("shifridrole")));
                return user;
            }
        };
        UserEntity user = (UserEntity) jdbcTemplate.queryForObject(
                SQL_SELECT_BY_ID, mapper, new Object[]{id});
        //		sql, new Object[] { wantedId }, mapper);

        return user;
        //   return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, UserEntity.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByLogin(String login) {
        //      logger.debug("inside getByLogin");
        RowMapper<UserEntity> mapper = new RowMapper<UserEntity>() {
            public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserEntity user = new UserEntity();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setDataCreate(rs.getDate("datacreate"));
                user.setDataDelete(rs.getDate("datadelete"));
                user.setActive(rs.getBoolean("active"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setTabno(rs.getInt("tabno"));
                user.setShifrPodr(rs.getInt("shifrpodr"));
                user.setEmail(rs.getString("email"));
                user.setFam(rs.getString("fam"));
                user.setNam(rs.getString("nam"));
                user.setOtc(rs.getString("otc"));
                user.setUzwan(rs.getInt("uzwan"));
                user.setUstep(rs.getInt("ustep"));
                user.setDopInf(rs.getString("dopinf"));
                user.setEngFio(rs.getString("engfio"));
                user.setAuthorIdEliblaryRu(rs.getString("authorideliblaryru"));
                user.setHrefElibraryRu(rs.getString("hrefelibraryru"));
                user.setOrcIdScopusCom(rs.getString("orcidscopuscom"));
                user.setHrefScopusCom(rs.getString("hrefscopuscom"));
                user.setReseacherIdWebOfKnoledgeCom(rs.getString("reseacheridwebofknoledgecom"));
                user.setHrefWebOfKnoledgeCom(rs.getString("hrefwebofknoledgecom"));
                user.setDopInfForSearch(rs.getString("dopinfforsearch"));
                user.setShifrKat(rs.getInt("shifrkat"));
                user.setShifrDol(rs.getInt("shifrdol"));
                user.setShifrUni(rs.getInt("shifruni"));
                user.setShifrFac(rs.getInt("shifrfac"));
                user.setShifrKaf(rs.getInt("shifrkaf"));
                user.setShifrWr(rs.getInt("shifrwr"));
                user.setDolgOsnMr(rs.getString("dolgosnmr"));
                user.setUserCode(rs.getLong("usercode"));
                user.setStatusCode(rs.getInt("statuscode"));
                user.setDataVerification(rs.getDate("dataVerification"));
                user.setShifrIdSup(rs.getLong("shifridsup"));
                user.setDopSlInfo(rs.getString("dopslinfo"));
                user.setVerifiedSupFIO(rs.getString("verifiedsupfio"));
                return user;
            }
        };
        UserEntity user = (UserEntity) jdbcTemplate.queryForObject(
                SQL_SELECT_BY_LOGIN, mapper, new Object[]{login});

        return user;
    }

    public int getCountByLogin(String login) {
        return jdbcTemplate.queryForObject(SQL_SELECT_COUNT_BY_LOGIN, Integer.class, login);
    }

    @Override
    public void saveRecord(UserEntity uEntity) {
        if (uEntity.getId() > 0) {
//   1       2         3        4        5         6       7      8    9       10    11      12      13       14
//name=?,shifrpodr=?,active=?,login=?,password=?,tabno=?,email=?,fam=?,nam=?,otc=?,uzwan=?,ustep=?,dopinf=?,engfio=?,
//       15                  16              17               18                  19                      20                     21                22          23         24        25         26        27          28        29           30           31           32            33
// authorideliblaryru=?,hrefelibraryru=?,orcidscopuscom=?,hrefscopuscom=?,reseacheridwebofknoledgecom=?,hrefwebofknoledgecom=?,dopinfforsearch=?,shifrkat=?,shifrdol=?,shifruni=?,shifrfac=?,shifrkaf=?,shifrwr=?,dolgosnmr=?,usercode=?,statuscode=?,shifridsup=?,dopslinfo=?,verifiedsupfio=?  where id=?";

            jdbcTemplate.update(SQL_UPDATE_USER,
                    new Object[]{uEntity.getName(),   //1
                            uEntity.getShifrPodr(),   //2
                            uEntity.isActive(),       //3
                            uEntity.getLogin(),       //4
                            uEntity.getPassword(),    //5
                            uEntity.getTabno(),       //6
                            uEntity.getEmail(),       //7
                            uEntity.getFam(),         //8
                            uEntity.getNam(),         //9
                            uEntity.getOtc(),         //10
                            uEntity.getUzwan(),       //11
                            uEntity.getUstep(),       //12
                            uEntity.getDopInf(),      //13
                            uEntity.getEngFio(),      //14
                            uEntity.getAuthorIdEliblaryRu(),  //15
                            uEntity.getHrefElibraryRu(),      //16
                            uEntity.getOrcIdScopusCom(),      //17
                            uEntity.getHrefScopusCom(),       //18
                            uEntity.getReseacherIdWebOfKnoledgeCom(), //19
                            uEntity.getHrefWebOfKnoledgeCom(),      //20
                            uEntity.getDopInfForSearch(),      //21
                            uEntity.getShifrKat(),             //22
                            uEntity.getShifrDol(),             //23
                            uEntity.getShifrUni(),             //24
                            uEntity.getShifrFac(),             //25
                            uEntity.getShifrKaf(),             //26
                            uEntity.getShifrWr(),              //27
                            uEntity.getDolgOsnMr(),            //28
                            uEntity.getUserCode(),             //29
                            uEntity.getStatusCode(),           //30
                  //          uEntity.getDataVerification(),
                            uEntity.getShifrIdSup(),           //31
                            uEntity.getDopSlInfo(),            //32
                            uEntity.getVerifiedSupFIO(),       //33
                            uEntity.getId()
                    });
        } else {
            insertRecord(uEntity);
        }
    }

    @Override
    public void setVerified(UserEntity uEntity) {
        if (uEntity.getId() > 0) {

            jdbcTemplate.update(SQL_SET_VERIFIED_USER,
                    new Object[]{
                            uEntity.getShifrIdSup(),
                            uEntity.getVerifiedSupFIO(),
                            uEntity.getId()
                    });
        }
    }

    @Override
    public void setPassword(ChgPwdDTO up) {
        if (up.getIduser() > 0)
        if (up.getPwd()!=null)
        if (up.getPwd().trim().length()>0)
        if (up.getPwd().trim().length()<16) {
           jdbcTemplate.update(SQL_SET_PASSWORD_USER,
           new Object[]{
                        up.getPwd().trim(),
                        up.getIduser()
           });
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_USER, new Object[]{wantedId});
    }

    private void insertRecord(final UserEntity uEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();


        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_USER, new String[]{"id"});
                        //  1       2        3     4       5      6     7    8   9   10  11     12    13     14       15                  16              17            18               19                          20                21            22       23       24       25       26      27        28       29        30           31             32
                        // name,shifrpodr,active,login,password,tabno,email,fam,nam,otc,uzwan,ustep,dopinf,engfio,authorideliblaryru,hrefelibraryru,orcidscopuscom,hrefscopuscom,reseacheridwebofknoledgecom,hrefwebofknoledgecom,dopinfforsearch,shifrkat,shifrdol,shifruni,shifrfac,shifrkaf,shifrwr,dolgosnmr,usercode,statuscode,dataverification,shifridsup) values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?)";

                        pst.setString(1, uEntity.getName());
                        pst.setInt(2, uEntity.getShifrPodr());
                        pst.setBoolean(3, uEntity.isActive());
                        pst.setString(4, uEntity.getLogin());
                        pst.setString(5, uEntity.getPassword());
                        pst.setInt(6, uEntity.getTabno());
                        pst.setString(7, uEntity.getEmail());
                        pst.setString(8, uEntity.getFam());
                        pst.setString(9, uEntity.getNam());
                        pst.setString(10, uEntity.getOtc());

                        pst.setInt(11, uEntity.getUzwan());
                        pst.setInt(12, uEntity.getUstep());
                        pst.setString(13, uEntity.getDopInf());
                        pst.setString(14, uEntity.getEngFio());
                        pst.setString(15, uEntity.getAuthorIdEliblaryRu());
                        pst.setString(16, uEntity.getHrefElibraryRu());
                        pst.setString(17, uEntity.getOrcIdScopusCom());
                        pst.setString(18, uEntity.getHrefScopusCom());
                        pst.setString(19, uEntity.getReseacherIdWebOfKnoledgeCom());
                        pst.setString(20, uEntity.getHrefWebOfKnoledgeCom());

                        pst.setString(21, uEntity.getDopInfForSearch());
                        pst.setInt(22, uEntity.getShifrKat());
                        pst.setInt(23, uEntity.getShifrDol());
                        pst.setInt(24, uEntity.getShifrUni());
                        pst.setInt(25, uEntity.getShifrFac());
                        pst.setInt(26, uEntity.getShifrKaf());
                        pst.setInt(27, uEntity.getShifrWr());
                        pst.setString(28, uEntity.getDolgOsnMr());
                        pst.setLong(29, uEntity.getUserCode());
                        pst.setInt(30, uEntity.getStatusCode());

//                        pst.setDate(31, uEntity.getDataVerification());
                        pst.setLong(31, uEntity.getShifrIdSup());
                        pst.setString(32, uEntity.getDopSlInfo());
                        pst.setString(33, uEntity.getVerifiedSupFIO());
                        return pst;
                    }
                },
                keyHolder);
        uEntity.setId(keyHolder.getKey().intValue());
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserEntity> getAll() {
        RowMapper<UserEntity> mapper = new RowMapper<UserEntity>() {
            public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserEntity user = new UserEntity();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setDataCreate(rs.getDate("datacreate"));
                user.setDataDelete(rs.getDate("datadelete"));
                user.setActive(rs.getBoolean("active"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setTabno(rs.getInt("tabno"));
                user.setShifrPodr(rs.getInt("shifrpodr"));
                user.setEmail(rs.getString("email"));
                user.setFam(rs.getString("fam"));
                user.setNam(rs.getString("nam"));
                user.setOtc(rs.getString("otc"));
                user.setUzwan(rs.getInt("uzwan"));
                user.setUstep(rs.getInt("ustep"));
                user.setDopInf(rs.getString("dopinf"));
                user.setEngFio(rs.getString("engfio"));
                user.setAuthorIdEliblaryRu(rs.getString("authorideliblaryru"));
                user.setHrefElibraryRu(rs.getString("hrefelibraryru"));
                user.setOrcIdScopusCom(rs.getString("orcidscopuscom"));
                user.setHrefScopusCom(rs.getString("hrefscopuscom"));
                user.setReseacherIdWebOfKnoledgeCom(rs.getString("reseacheridwebofknoledgecom"));
                user.setHrefWebOfKnoledgeCom(rs.getString("hrefwebofknoledgecom"));
                user.setDopInfForSearch(rs.getString("dopinfforsearch"));
                user.setShifrKat(rs.getInt("shifrkat"));
                user.setShifrDol(rs.getInt("shifrdol"));
                user.setShifrUni(rs.getInt("shifruni"));
                user.setShifrFac(rs.getInt("shifrfac"));
                user.setShifrKaf(rs.getInt("shifrkaf"));
                user.setShifrWr(rs.getInt("shifrwr"));
                user.setDolgOsnMr(rs.getString("dolgosnmr"));
                user.setUserCode(rs.getLong("usercode"));
                user.setStatusCode(rs.getInt("statuscode"));
                user.setDataVerification(rs.getDate("dataVerification"));
                user.setShifrIdSup(rs.getLong("shifridsup"));
                user.setDopSlInfo(rs.getString("dopslinfo"));
                user.setVerifiedSupFIO(rs.getString("verifiedsupfio"));
                return user;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> getAllForUser(String whereStmnt) {
        RowMapper<UserEntity> mapper = new RowMapper<UserEntity>() {
            public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserEntity user = new UserEntity();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setDataCreate(rs.getDate("datacreate"));
                user.setDataDelete(rs.getDate("datadelete"));
                user.setActive(rs.getBoolean("active"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setTabno(rs.getInt("tabno"));
                user.setShifrPodr(rs.getInt("shifrpodr"));
                user.setEmail(rs.getString("email"));
                user.setFam(rs.getString("fam"));
                user.setNam(rs.getString("nam"));
                user.setOtc(rs.getString("otc"));
                user.setUzwan(rs.getInt("uzwan"));
                user.setUstep(rs.getInt("ustep"));
                user.setDopInf(rs.getString("dopinf"));
                user.setEngFio(rs.getString("engfio"));
                user.setAuthorIdEliblaryRu(rs.getString("authorideliblaryru"));
                user.setHrefElibraryRu(rs.getString("hrefelibraryru"));
                user.setOrcIdScopusCom(rs.getString("orcidscopuscom"));
                user.setHrefScopusCom(rs.getString("hrefscopuscom"));
                user.setReseacherIdWebOfKnoledgeCom(rs.getString("reseacheridwebofknoledgecom"));
                user.setHrefWebOfKnoledgeCom(rs.getString("hrefwebofknoledgecom"));
                user.setDopInfForSearch(rs.getString("dopinfforsearch"));
                user.setShifrKat(rs.getInt("shifrkat"));
                user.setShifrDol(rs.getInt("shifrdol"));
                user.setShifrUni(rs.getInt("shifruni"));
                user.setShifrFac(rs.getInt("shifrfac"));
                user.setShifrKaf(rs.getInt("shifrkaf"));
                user.setShifrWr(rs.getInt("shifrwr"));
                user.setDolgOsnMr(rs.getString("dolgosnmr"));
                user.setUserCode(rs.getLong("usercode"));
                user.setStatusCode(rs.getInt("statuscode"));
                user.setDataVerification(rs.getDate("dataVerification"));
                user.setShifrIdSup(rs.getLong("shifridsup"));
                user.setVerifiedSupFIO(rs.getString("verifiedsupfio"));
                return user;
            }
        };
        StringBuilder sb = new StringBuilder();
        sb.append(SQL_SELECT_ALL_FOR_USER);
        if (whereStmnt != null) {
            if (whereStmnt.trim().length() > 0) {
                sb.append(" where ");
                sb.append(" " + whereStmnt.trim());
            }
        }
        sb.append(" order by id");
        return jdbcTemplate.query(sb.toString(), mapper);
    }


    @Override
    public List<UserEntity> getAllForUniFacKaf(int shifruni, int shifrfac, int shifrkaf) {
        int shifrPre;
        shifrPre=shifruni;
        if (shifrfac>0) shifrPre=shifrfac;
        if (shifrkaf>0) shifrPre=shifrkaf;
        return getAllForPodr(shifrPre);
    }
    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> getAllForPodr(int shifrPodr) {
        RowMapper<UserEntity> mapper = new RowMapper<UserEntity>() {
            public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserEntity user = new UserEntity();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setDataCreate(rs.getDate("datacreate"));
                user.setDataDelete(rs.getDate("datadelete"));
                user.setActive(rs.getBoolean("active"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setTabno(rs.getInt("tabno"));
                user.setShifrPodr(rs.getInt("shifrpodr"));
                user.setEmail(rs.getString("email"));
                user.setFam(rs.getString("fam"));
                user.setNam(rs.getString("nam"));
                user.setOtc(rs.getString("otc"));
                user.setUzwan(rs.getInt("uzwan"));
                user.setUstep(rs.getInt("ustep"));
                user.setDopInf(rs.getString("dopinf"));
                user.setEngFio(rs.getString("engfio"));
                user.setAuthorIdEliblaryRu(rs.getString("authorideliblaryru"));
                user.setHrefElibraryRu(rs.getString("hrefelibraryru"));
                user.setOrcIdScopusCom(rs.getString("orcidscopuscom"));
                user.setHrefScopusCom(rs.getString("hrefscopuscom"));
                user.setReseacherIdWebOfKnoledgeCom(rs.getString("reseacheridwebofknoledgecom"));
                user.setHrefWebOfKnoledgeCom(rs.getString("hrefwebofknoledgecom"));
                user.setDopInfForSearch(rs.getString("dopinfforsearch"));
                user.setShifrKat(rs.getInt("shifrkat"));
                user.setShifrDol(rs.getInt("shifrdol"));
                user.setShifrUni(rs.getInt("shifruni"));
                user.setShifrFac(rs.getInt("shifrfac"));
                user.setShifrKaf(rs.getInt("shifrkaf"));
                user.setShifrWr(rs.getInt("shifrwr"));
                user.setDolgOsnMr(rs.getString("dolgosnmr"));
                user.setUserCode(rs.getLong("usercode"));
                user.setStatusCode(rs.getInt("statuscode"));
                user.setDataVerification(rs.getDate("dataVerification"));
                user.setShifrIdSup(rs.getLong("shifridsup"));
                user.setDopSlInfo(rs.getString("dopslinfo"));
                user.setVerifiedSupFIO(rs.getString("verifiedsupfio"));
                return user;
            }
        };


        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_PREDP_OWNER, mapper , new Object[]{shifrPodr});
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> getPageForPodr(int shifrPodr,int pageNo,int pageSize,int order) {
        RowMapper<UserEntity> mapper = new RowMapper<UserEntity>() {
            public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserEntity user = new UserEntity();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setDataCreate(rs.getDate("datacreate"));
                user.setDataDelete(rs.getDate("datadelete"));
                user.setActive(rs.getBoolean("active"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setTabno(rs.getInt("tabno"));
                user.setShifrPodr(rs.getInt("shifrpodr"));
                user.setEmail(rs.getString("email"));
                user.setFam(rs.getString("fam"));
                user.setNam(rs.getString("nam"));
                user.setOtc(rs.getString("otc"));
                user.setUzwan(rs.getInt("uzwan"));
                user.setUstep(rs.getInt("ustep"));
                user.setDopInf(rs.getString("dopinf"));
                user.setEngFio(rs.getString("engfio"));
                user.setAuthorIdEliblaryRu(rs.getString("authorideliblaryru"));
                user.setHrefElibraryRu(rs.getString("hrefelibraryru"));
                user.setOrcIdScopusCom(rs.getString("orcidscopuscom"));
                user.setHrefScopusCom(rs.getString("hrefscopuscom"));
                user.setReseacherIdWebOfKnoledgeCom(rs.getString("reseacheridwebofknoledgecom"));
                user.setHrefWebOfKnoledgeCom(rs.getString("hrefwebofknoledgecom"));
                user.setDopInfForSearch(rs.getString("dopinfforsearch"));
                user.setShifrKat(rs.getInt("shifrkat"));
                user.setShifrDol(rs.getInt("shifrdol"));
                user.setShifrUni(rs.getInt("shifruni"));
                user.setShifrFac(rs.getInt("shifrfac"));
                user.setShifrKaf(rs.getInt("shifrkaf"));
                user.setShifrWr(rs.getInt("shifrwr"));
                user.setDolgOsnMr(rs.getString("dolgosnmr"));
                user.setUserCode(rs.getLong("usercode"));
                user.setStatusCode(rs.getInt("statuscode"));
                user.setDataVerification(rs.getDate("dataVerification"));
                user.setShifrIdSup(rs.getLong("shifridsup"));
                user.setDopSlInfo(rs.getString("dopslinfo"));
                user.setVerifiedSupFIO(rs.getString("verifiedsupfio"));
                return user;
            }
        };

        int offset;
        offset = pageSize*(pageNo-1);
        StringBuilder SQLStmnt=new StringBuilder(SQL_SELECT_ALL_FOR_PREDP_OWNER);
        String orderS=ordersMap.get(order);
        if (orderS!=null) {
            SQLStmnt.append(" "+orderS);
        }
        SQLStmnt.append(" limit ? offset ?");
        return jdbcTemplate.query(SQLStmnt.toString(), mapper , new Object[]{shifrPodr,pageSize,offset});
    }


    @Override
    @Transactional(readOnly = true)
    public int getCountUserForPodr(int shifrPodr) {
        return jdbcTemplate.queryForObject(SQL_SELECT_COUNT_FOR_PREDP_OWNER, new Object[]{shifrPodr}, Integer.class);
    }


    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> getAllForQuery(String query) {
        RowMapper<UserEntity> mapper = new RowMapper<UserEntity>() {
            public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserEntity user = new UserEntity();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setDataCreate(rs.getDate("datacreate"));
                user.setDataDelete(rs.getDate("datadelete"));
                user.setActive(rs.getBoolean("active"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setTabno(rs.getInt("tabno"));
                user.setShifrPodr(rs.getInt("shifrpodr"));
                user.setEmail(rs.getString("email"));
                user.setFam(rs.getString("fam"));
                user.setNam(rs.getString("nam"));
                user.setOtc(rs.getString("otc"));
                user.setUzwan(rs.getInt("uzwan"));
                user.setUstep(rs.getInt("ustep"));
                user.setDopInf(rs.getString("dopinf"));
                user.setEngFio(rs.getString("engfio"));
                user.setAuthorIdEliblaryRu(rs.getString("authorideliblaryru"));
                user.setHrefElibraryRu(rs.getString("hrefelibraryru"));
                user.setOrcIdScopusCom(rs.getString("orcidscopuscom"));
                user.setHrefScopusCom(rs.getString("hrefscopuscom"));
                user.setReseacherIdWebOfKnoledgeCom(rs.getString("reseacheridwebofknoledgecom"));
                user.setHrefWebOfKnoledgeCom(rs.getString("hrefwebofknoledgecom"));
                user.setDopInfForSearch(rs.getString("dopinfforsearch"));
                user.setShifrKat(rs.getInt("shifrkat"));
                user.setShifrDol(rs.getInt("shifrdol"));
                user.setShifrUni(rs.getInt("shifruni"));
                user.setShifrFac(rs.getInt("shifrfac"));
                user.setShifrKaf(rs.getInt("shifrkaf"));
                user.setShifrWr(rs.getInt("shifrwr"));
                user.setDolgOsnMr(rs.getString("dolgosnmr"));
                user.setUserCode(rs.getLong("usercode"));
                user.setStatusCode(rs.getInt("statuscode"));
                user.setDataVerification(rs.getDate("dataVerification"));
                user.setShifrIdSup(rs.getLong("shifridsup"));
                user.setDopSlInfo(rs.getString("dopslinfo"));
                user.setVerifiedSupFIO(rs.getString("verifiedsupfio"));
                return user;
            }
        };

        String filter="";
        if (query!=null)
            if (query.trim().length()<50) {
               filter=query.trim()+"%";
            }
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_QUERY, mapper , new Object[]{filter});
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkLogin(String login) {
        boolean retVal;
        int i;
//        System.out.println("inside checklogin login="+login);
        if (login==null) return false;
        if (login.trim().length()<1) return false;
        if (login.trim().length()>18) return false;
        i= jdbcTemplate.queryForObject(SQL_SELECT_COUNT_LOGIN, new Object[]{login.trim()}, Integer.class);
        if (i==0)
            retVal=true;
        else
            retVal=false;
        return retVal;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
