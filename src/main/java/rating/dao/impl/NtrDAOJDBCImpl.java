package rating.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rating.dao.NtrDAO;
import rating.domain.NtrEntity;
import rating.dto.NtrRecDTO;
import rating.dto.SemanticUISearchItemDTO;

import java.sql.*;
import java.util.*;

@Repository
@Transactional
public class NtrDAOJDBCImpl implements NtrDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_BY_ID   = "select id,name,datepubl,status,dateapprove,shifrwrkapprove,fioapprove,shifrpre,shifrwrk,fiowrk,datewrk,parametry,idrinc,hrefrinc,ypubl from public.tb_ntr where id=?";
    private static final String SQL_SELECT_BY_NAME = "select id,name,datepubl,status,dateapprove,shifrwrkapprove,fioapprove,shifrpre,shifrwrk,fiowrk,datewrk,parametry,idrinc,hrefrinc,ypubl from public.tb_ntr where coalesce(upper(trim(name)),'---')=?";
    private static final String SQL_SELECT_ALL_BY_NAME = "select id,name from public.tb_ntr where coalesce(upper(trim(name)),'---') like ?||'%'";
    private static final String SQL_SELECT_ALL = "select id,name,datepubl,status,dateapprove,shifrwrkapprove,fioapprove,shifrpre,shifrwrk,fiowrk,datewrk,parametry,idrinc,hrefrinc,ypubl from public.tb_ntr order by id";
    private static final String SQL_SELECT_ALL_FOR_AUTHOR = "select a.id,a.name,a.datepubl,a.status,a.dateapprove,a.shifrwrkapprove,a.fioapprove,a.shifrpre,a.shifrwrk,a.fiowrk,a.datewrk,a.parametry,a.idrinc,a.hrefrinc,a.ypubl from public.tb_ntr a left inner join public.tb_ntr_au b on a.id=b.idowner where b.id=? order by a.name";
    private static final String SQL_SELECT_ALL_FOR_PODR = "select a.id,a.name,a.datepubl,a.status,a.dateapprove,a.shifrwrkapprove,a.fioapprove,a.shifrpre,a.shifrwrk,a.fiowrk,a.datewrk,a.parametry,a.idrinc,a.hrefrinc,a.ypubl from public.tb_ntr a where coalesce(a.shifrpre,0)=? order by a.name";
    private static final String SQL_SELECT_ALL_FOR_PODR_AND_CHILDS = "with recursive n as (\n" +
            "SELECT name na,0 l,id id,owner o from tb_predp where id=?\n" +
            "union all\n" +
            "   select a.name na,n1.l+1,a.id l,a.owner w from tb_predp a\n" +
            "   inner join n n1 on a.owner=n1.id\n" +
            "      )\n" +
            "\n" +
            "select a.id,a.name,a.datepubl,a.status,a.dateapprove,a.shifrwrkapprove,a.fioapprove,a.shifrpre,a.shifrwrk,a.fiowrk,a.datewrk,a.parametry,a.idrinc,a.hrefrinc,a.ypubl from public.tb_ntr a where coalesce(a.shifrpre,0) in (select coalesce(id) from n order by id)\n" +
            "\n" +
            " order by a.name";

    private static final String SQL_COUNT_ALL_FOR_PODR_AND_CHILDS = "with recursive n as (\n" +
            "SELECT name na,0 l,id id,owner o from tb_predp where id=?\n" +
            "union all\n" +
            "   select a.name na,n1.l+1,a.id l,a.owner w from tb_predp a\n" +
            "   inner join n n1 on a.owner=n1.id\n" +
            "      )\n" +
            "\n" +
            "select count(*) from public.tb_ntr a where coalesce(a.shifrpre,0) in (select coalesce(id) from n order by id)";

    private static final String SQL_COUNT_ALL_FOR_NPR = "select count(*) from tb_ntr a\n" +
            "       join tb_ntr_au b on a.id=b.idntr\n" +
            "       where b.idauth=?";

    
    private static final String SQL_SELECT_ALL_FOR_NPR = "select a.id,a.name,a.datepubl,a.status,a.dateapprove,a.shifrwrkapprove,a.fioapprove,a.shifrpre,a.shifrwrk,a.fiowrk,a.datewrk,a.parametry,a.idrinc,a.hrefrinc,a.ypubl from public.tb_ntr a where exists(select * from public.tb_ntr_au b where b.idauth=? and b.idntr=a.id) order by a.name";
    private static final String SQL_DELETE_NTR = "delete from public.tb_ntr where id=?";
//    private static final String SQL_UPDATE_NTR = "update public.tb_ntr set name=?,datepubl=?,status=?,shifrwrkapprove=?,fioapprove=?,shifrpre=?,shifrwrk=?,fiowrk=?,parametry=? where id=?";
    private static final String SQL_UPDATE_NTR = "update public.tb_ntr set name=?,datepubl=?,shifrpre=?,shifrwrk=?,fiowrk=?,parametry=?,idrinc=?,hrefrinc=?,ypubl=? where id=?";
//    private static final String SQL_INSERT_NTR = "insert into public.tb_ntr (name,datepubl,status,shifrwrkapprove,fioapprove,shifrpre,shifrwrk,fiowrk,parametry) values (?,?,?,?,?, ?,?,?,?)";
    private static final String SQL_INSERT_NTR = "insert into public.tb_ntr (name,datepubl,shifrpre,shifrwrk,fiowrk,parametry,idrinc,hrefrinc,ypubl) values (?,?,?,?,?,?,?,?,?)";
    private static final String SQL_APPROVE_NTR = "update public.tb_ntr set status=1,shifrwrkapprove=?,fioapprove=?,dateapprove=? where id=?";
    private static final String SQL_DISMISSAPPROVE_NTR = "update public.tb_ntr set status=0,shifrwrkapprove=?,fioapprove=?,dateapprove=? where id=?";
    private static final String SQL_ALL_DTO_PAGER="select  id,name,approved,dataapproved,fioapproved,"+
            "hasattachement,authors,namepre,parametry,"+
            "pokaz, amntofimages, amntofdocs , lineno,ypubl"+
            " from fn_getntrrecdtos(?,?,?,?,?,?,?,?,?,0)";
    private static final String SQL_COUNT_ALL_DTO_PAGER="select  id,name,approved,dataapproved,fioapproved,"+
            "hasattachement,authors,namepre,parametry,"+
            "pokaz, amntofimages, amntofdocs , lineno,ypubl"+
            " from fn_getntrrecdtos(?,?,?,?,?,?,?,?,?,1)";

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
            put(0, SQL_ORDER_0);
            put(10, SQL_ORDER_10);
            put(1, SQL_ORDER_1);
            put(11, SQL_ORDER_11);
            put(2, SQL_ORDER_2);
            put(12, SQL_ORDER_12);
            put(3, SQL_ORDER_3);
            put(13, SQL_ORDER_13);
            put(4, SQL_ORDER_4);
            put(14, SQL_ORDER_14);
            //rest of code here
        }
    });


    @Override
    @Transactional(readOnly = true)
    public NtrEntity getById(final int wantedId) {
        RowMapper<NtrEntity> mapper = new RowMapper<NtrEntity>() {
            public NtrEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                NtrEntity ntr = new NtrEntity();
                java.sql.Date dateSQL;
                ntr.setId(wantedId);
                ntr.setName(rs.getString("name"));
                dateSQL=rs.getDate("datepubl");
                if (dateSQL!=null) {
                    ntr.setDatepubl(dateSQL);
                }
                ntr.setStatus(rs.getInt("status"));
                dateSQL=rs.getDate("dateapprove");
                if (dateSQL!=null) {
                    ntr.setDateapprove(dateSQL);
                }
                ntr.setShifrwrkapprove(rs.getInt("shifrwrkapprove"));
                ntr.setFioapprove(rs.getString("fioapprove"));
                ntr.setShifrpre(rs.getInt("shifrpre"));
                ntr.setShifrwrk(rs.getInt("shifrwrk"));
                ntr.setFiowrk(rs.getString("fiowrk"));
                ntr.setParametry(rs.getString("parametry"));
                ntr.setIdRinc(rs.getInt("idrinc"));
                ntr.setHrefRinc(rs.getString("hrefrinc"));
                ntr.setyPubl(rs.getInt("ypubl"));
                dateSQL=rs.getDate("datewrk");
                if (dateSQL!=null) {
                    ntr.setDatewrk(dateSQL);
                }
                return ntr;
            }
        };
        NtrEntity ntr;
        try {
            ntr = (NtrEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            ntr = null;
        }
        return ntr;
    }

    @Override
    @Transactional(readOnly = true)
    public NtrEntity getByName(final String wantedName) {
        RowMapper<NtrEntity> mapper = new RowMapper<NtrEntity>() {
            public NtrEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                NtrEntity ntr = new NtrEntity();
                java.sql.Date dateSQL;
                ntr.setId(rs.getInt("id"));
                ntr.setName(wantedName);
                dateSQL=rs.getDate("datepubl");
                if (dateSQL!=null) {
                    ntr.setDatepubl(dateSQL);
                }
                ntr.setStatus(rs.getInt("status"));
                dateSQL=rs.getDate("dateapprove");
                if (dateSQL!=null) {
                    ntr.setDateapprove(dateSQL);
                }
                ntr.setShifrwrkapprove(rs.getInt("shifrwrkapprove"));
                ntr.setFioapprove(rs.getString("fioapprove"));
                ntr.setShifrpre(rs.getInt("shifrpre"));
                ntr.setShifrwrk(rs.getInt("shifrwrk"));
                ntr.setFiowrk(rs.getString("fiowrk"));
                ntr.setParametry(rs.getString("parametry"));
                ntr.setIdRinc(rs.getInt("idrinc"));
                ntr.setHrefRinc(rs.getString("hrefrinc"));
                ntr.setyPubl(rs.getInt("ypubl"));
                dateSQL=rs.getDate("datewrk");
                if (dateSQL!=null) {
                    ntr.setDatewrk(dateSQL);
                }
                return ntr;
            }
        };
        NtrEntity ntr;
        try {
            ntr = (NtrEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_NAME, mapper, new Object[]{wantedName.toUpperCase()});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            ntr = null;
        }
        return ntr;
    }


    @Override
    @Transactional(readOnly = true)
    public List<NtrEntity> getAll() {
        RowMapper<NtrEntity> mapper = new RowMapper<NtrEntity>() {
            public NtrEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                NtrEntity ntr = new NtrEntity();
                java.sql.Date dateSQL;
                ntr.setId(rs.getInt("id"));
                ntr.setName(rs.getString("name"));
                dateSQL=rs.getDate("datepubl");
                if (dateSQL!=null) {
                    ntr.setDatepubl(dateSQL);
                }
                ntr.setStatus(rs.getInt("status"));
                dateSQL=rs.getDate("dateapprove");
                if (dateSQL!=null) {
                    ntr.setDateapprove(dateSQL);
                }
                ntr.setShifrwrkapprove(rs.getInt("shifrwrkapprove"));
                ntr.setFioapprove(rs.getString("fioapprove"));
                ntr.setShifrpre(rs.getInt("shifrpre"));
                ntr.setShifrwrk(rs.getInt("shifrwrk"));
                ntr.setFiowrk(rs.getString("fiowrk"));
                ntr.setParametry(rs.getString("parametry"));
                ntr.setIdRinc(rs.getInt("idrinc"));
                ntr.setHrefRinc(rs.getString("hrefrinc"));
                ntr.setyPubl(rs.getInt("ypubl"));
                dateSQL=rs.getDate("datewrk");
                if (dateSQL!=null) {
                    ntr.setDatewrk(dateSQL);
                }
                return ntr;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Transactional(readOnly = true)
    @Override
    public List<NtrEntity> getPageForPre(int shifrpre, int pageNo, int pageSize, int order) {
        RowMapper<NtrEntity> mapper = new RowMapper<NtrEntity>() {
            public NtrEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                NtrEntity ntr = new NtrEntity();
                java.sql.Date dateSQL;
                ntr.setId(rs.getInt("id"));
                ntr.setName(rs.getString("name"));
                dateSQL=rs.getDate("datepubl");
                if (dateSQL!=null) {
                    ntr.setDatepubl(dateSQL);
                }
                ntr.setStatus(rs.getInt("status"));
                dateSQL=rs.getDate("dateapprove");
                if (dateSQL!=null) {
                    ntr.setDateapprove(dateSQL);
                }
                ntr.setShifrwrkapprove(rs.getInt("shifrwrkapprove"));
                ntr.setFioapprove(rs.getString("fioapprove"));
                ntr.setShifrpre(rs.getInt("shifrpre"));
                ntr.setShifrwrk(rs.getInt("shifrwrk"));
                ntr.setFiowrk(rs.getString("fiowrk"));
                ntr.setParametry(rs.getString("parametry"));
                ntr.setIdRinc(rs.getInt("idrinc"));
                ntr.setHrefRinc(rs.getString("hrefrinc"));
                ntr.setyPubl(rs.getInt("ypubl"));
                dateSQL=rs.getDate("datewrk");
                if (dateSQL!=null) {
                    ntr.setDatewrk(dateSQL);
                }
                return ntr;
            }
        };
        int offset;
        offset = pageSize*(pageNo-1);
        StringBuilder SQLStmnt=new StringBuilder(SQL_SELECT_ALL);
        String orderS=ordersMap.get(order);
        if (orderS!=null) {
            SQLStmnt.append(" "+orderS);
        }
        SQLStmnt.append(" limit ? offset ?");
        return jdbcTemplate.query(SQLStmnt.toString(), mapper , new Object[]{shifrpre,pageSize,offset});
    }

    @Transactional(readOnly = true)
    @Override
    public List<NtrRecDTO> getPageForPreFromFn(int kind,int shifrpre,int yfr, int yto, int pageNo, int pageSize, int order,int shifridnprforfilter,int shifriddetforfilter) {
        RowMapper<NtrRecDTO> mapper = new RowMapper<NtrRecDTO>() {
            public NtrRecDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                NtrRecDTO ntr = new NtrRecDTO();
                ntr.setId(rs.getInt("id"));
                ntr.setName(rs.getString("name"));
                ntr.setApproved(rs.getString("approved"));
                ntr.setDataapproved(rs.getString("dataapproved"));
                ntr.setHasattachement(rs.getBoolean("hasattachement"));
                ntr.setAuthors(rs.getString("authors"));
                ntr.setNamepre(rs.getString("namepre"));
                ntr.setParametry(rs.getString("parametry"));
                ntr.setPokaz(rs.getString("pokaz"));
                ntr.setAmntOfImages(rs.getInt("amntofimages"));
                ntr.setAmntOfDocs(rs.getInt("amntofdocs"));
                ntr.setLineno(rs.getInt("lineno"));
                ntr.setyPubl(rs.getInt("ypubl"));
                return ntr;
            }
        };
        return jdbcTemplate.query(SQL_ALL_DTO_PAGER, mapper , new Object[]{Integer.valueOf(kind),shifrpre,yfr,yto,pageNo,pageSize,order,shifridnprforfilter,shifriddetforfilter});
    }

    @Transactional(readOnly = true)
    @Override
    public int getCountNtr(int kind,int shifrpre,int yfr,int yto,int shifridnprforfilter,int shifriddetforfilter) {
        int retVal=0;
        RowMapper<NtrRecDTO> mapper = new RowMapper<NtrRecDTO>() {
            public NtrRecDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                NtrRecDTO ntr = new NtrRecDTO();
                ntr.setId(rs.getInt("id"));
                ntr.setName(rs.getString("name"));
                ntr.setApproved(rs.getString("approved"));
                ntr.setDataapproved(rs.getString("dataapproved"));
                ntr.setHasattachement(rs.getBoolean("hasattachement"));
                ntr.setAuthors(rs.getString("authors"));
                ntr.setNamepre(rs.getString("namepre"));
                ntr.setParametry(rs.getString("parametry"));
                ntr.setPokaz(rs.getString("pokaz"));
                ntr.setAmntOfImages(rs.getInt("amntofimages"));
                ntr.setAmntOfDocs(rs.getInt("amntofdocs"));
                ntr.setLineno(rs.getInt("lineno"));
                ntr.setyPubl(rs.getInt("ypubl"));
                return ntr;
            }
        };
        List<NtrRecDTO> l = jdbcTemplate.query(SQL_COUNT_ALL_DTO_PAGER, mapper , new Object[]{Integer.valueOf(kind),shifrpre,yfr,yto,1,10,1,shifridnprforfilter,shifriddetforfilter});
        if (l.size()==1) {
            retVal=l.get(0).getAmntOfImages();
        }
        return retVal;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void saveRecord(NtrEntity ntrEntity) {
        if (ntrEntity.getId() > 0) {
            java.sql.Date dateSQL;
            if (ntrEntity.getDatepubl()!=null)
               dateSQL=new java.sql.Date(ntrEntity.getDatepubl().getTimeInMillis());
            else
               dateSQL=null;
            jdbcTemplate.update(SQL_UPDATE_NTR,
                    new Object[]{ntrEntity.getName(),
                            dateSQL,
//                            ntrEntity.getStatus(),
//                            ntrEntity.getShifrwrkapprove(),
//                            ntrEntity.getFioapprove(),
                            ntrEntity.getShifrpre(),
                            ntrEntity.getShifrwrk(),
                            ntrEntity.getFiowrk(),
                            ntrEntity.getParametry(),
                            ntrEntity.getIdRinc(),
                            ntrEntity.getHrefRinc(),
                            ntrEntity.getyPubl(),
                            ntrEntity.getId()
                    });
        } else {
            insertRecord(ntrEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_NTR, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final NtrEntity ntrEntity) {
        //   KeyHolder keyHolder = new GeneratedKeyHolder();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final java.sql.Date dateSQL;
        if (ntrEntity.getDatepubl()!=null)
           dateSQL=new java.sql.Date(ntrEntity.getDatepubl().getTimeInMillis());
        else
           dateSQL=null;
        jdbcTemplate.update(
                new PreparedStatementCreator() {
//name,datepubl,status,shifrwrkapprove,fioapprove,shifrpre,shifrwrk,fiowrk
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_NTR, new String[]{"id"});
                        pst.setString(1, ntrEntity.getName());
                        if (dateSQL!=null) {
                           pst.setDate(2, dateSQL);
                        } else {
                          pst.setNull(2, Types.DATE);
                        }
//                        pst.setInt(3, ntrEntity.getStatus());
//                        pst.setInt(4, ntrEntity.getShifrwrkapprove());
//                        pst.setString(5, ntrEntity.getFioapprove());
                        pst.setInt(3, ntrEntity.getShifrpre());
                        pst.setInt(4, ntrEntity.getShifrwrk());
                        pst.setString(5, ntrEntity.getFiowrk());
                        pst.setString(6, ntrEntity.getParametry());
                        pst.setInt(7, ntrEntity.getIdRinc());
                        pst.setString(8, ntrEntity.getHrefRinc());
                        pst.setInt(9,ntrEntity.getyPubl());
                        return pst;
                    }
                },
                keyHolder);
        ntrEntity.setId(keyHolder.getKey().intValue());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NtrEntity> getAllForPre(final int shifrpre) {
        RowMapper<NtrEntity> mapper = new RowMapper<NtrEntity>() {
            public NtrEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                NtrEntity ntr = new NtrEntity();
                java.sql.Date dateSQL;
                ntr.setId(rs.getInt("id"));
                ntr.setName(rs.getString("name"));
                dateSQL=rs.getDate("datepubl");
                if (dateSQL!=null) {
                    ntr.setDatepubl(dateSQL);
                }
                ntr.setStatus(rs.getInt("status"));
                dateSQL=rs.getDate("dateapprove");
                if (dateSQL!=null) {
                    ntr.setDateapprove(dateSQL);
                }
                ntr.setShifrwrkapprove(rs.getInt("shifrwrkapprove"));
                ntr.setFioapprove(rs.getString("fioapprove"));
                ntr.setShifrpre(shifrpre);
                ntr.setShifrwrk(rs.getInt("shifrwrk"));
                ntr.setFiowrk(rs.getString("fiowrk"));
                ntr.setParametry(rs.getString("parametry"));
                ntr.setIdRinc(rs.getInt("idrinc"));
                ntr.setHrefRinc(rs.getString("hrefrinc"));
                ntr.setyPubl(rs.getInt("ypubl"));
                dateSQL=rs.getDate("datewrk");
                if (dateSQL!=null) {
                    ntr.setDatewrk(dateSQL);
                }
                dateSQL=rs.getDate("datepubl");
                if (dateSQL!=null) {
                    ntr.setDatepubl(dateSQL);
                }
                return ntr;
            }
        };
//        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_PODR, mapper, new Object[]{shifrpre});
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_PODR_AND_CHILDS, mapper, new Object[]{shifrpre});

    }
    @Override
    @Transactional(readOnly = true)
    public List<NtrEntity> getAllForNPR(final int shifrnpr) {
        RowMapper<NtrEntity> mapper = new RowMapper<NtrEntity>() {
            public NtrEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                NtrEntity ntr = new NtrEntity();
                java.sql.Date dateSQL;
                ntr.setId(rs.getInt("id"));
                ntr.setName(rs.getString("name"));
                dateSQL=rs.getDate("datepubl");
                if (dateSQL!=null) {
                    ntr.setDatepubl(dateSQL);
                }
                ntr.setStatus(rs.getInt("status"));
                dateSQL=rs.getDate("dateapprove");
                if (dateSQL!=null) {
                    ntr.setDateapprove(dateSQL);
                }
                ntr.setShifrwrkapprove(rs.getInt("shifrwrkapprove"));
                ntr.setFioapprove(rs.getString("fioapprove"));
                ntr.setShifrpre(rs.getInt("shifrpre"));
                ntr.setShifrwrk(rs.getInt("shifrwrk"));
                ntr.setFiowrk(rs.getString("fiowrk"));
                ntr.setParametry(rs.getString("parametry"));
                ntr.setIdRinc(rs.getInt("idrinc"));
                ntr.setHrefRinc(rs.getString("hrefrinc"));
                ntr.setyPubl(rs.getInt("ypubl"));
                dateSQL=rs.getDate("datewrk");
                if (dateSQL!=null) {
                    ntr.setDatewrk(dateSQL);
                }
                dateSQL=rs.getDate("datepubl");
                if (dateSQL!=null) {
                    ntr.setDatepubl(dateSQL);
                }
                return ntr;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_NPR, mapper, new Object[]{shifrnpr});

    }

    @Override
    public void approveNtrRec(int idntr,int idUser,String fio) {
        if (idntr > 0) {
            java.sql.Date dateSQL;
            GregorianCalendar d;
            d=new GregorianCalendar();
            dateSQL=new java.sql.Date(d.getTimeInMillis());
            jdbcTemplate.update(SQL_APPROVE_NTR,
                    new Object[]{idUser,
                            fio,
                            dateSQL,
                            idntr
                    });
        }

    }
    @Override
    public void dismissApproveNtrRec(int idntr) {
        if (idntr > 0) {
            java.sql.Date dateSQL;
            GregorianCalendar d;
            d=new GregorianCalendar();
            dateSQL=new java.sql.Date(d.getTimeInMillis());
            jdbcTemplate.update(SQL_DISMISSAPPROVE_NTR,
                    new Object[]{null,
                            null,
                            null,
                            idntr
                    });
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<SemanticUISearchItemDTO> getSourceForSemanticUISearch(String wantedName) {
        RowMapper<SemanticUISearchItemDTO> mapper = new RowMapper<SemanticUISearchItemDTO>() {
            public SemanticUISearchItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                SemanticUISearchItemDTO ntr = new SemanticUISearchItemDTO();
                ntr.setId(rs.getInt("id"));
                ntr.setTitle(rs.getString("name"));
                return ntr;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_BY_NAME, mapper, new Object[] {wantedName});
    }

}
