package rating.dao.impl;

import rating.dao.RoadDocDAO;
import rating.domain.RoadDocEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.sql.*;
import java.util.List;

@Repository
@Transactional
public class RoadDocDAOJDBCImpl implements RoadDocDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
                                                        // 1      2      3    4
    private static final String SQL_SELECT_BY_ID = "select id ,idroad,iddet,idwidblob,comment,shifrwrk,filename,dateupload from public.tb_roads_doc where id=?";
    private static final String SQL_SELECT_ALL = "select id ,idroad,iddet,idwidblob,comment,shifrwrk,filename,dateupload from public.tb_roads_doc order by id";
    private static final String SQL_SELECT_ALL_FOR_ROAD = "select id ,iddet,idwidblob,comment,shifrwrk,filename,dateupload from public.tb_roads_doc  where idroad=? order by id";
    private static final String SQL_SELECT_ALL_FOR_DET  = "select id ,idroad,idwidblob,comment,shifrwrk,filename,dateupload from public.tb_roads_doc  where iddet=? order by id";
    private static final String SQL_DELETE_REC = "delete from public.tb_roads_Doc where id=?";
                                                                               // 1         2        3
    private static final String SQL_UPDATE_REC = "update public.tb_roads_doc set idroad=?,iddet=?,idwidblob=?,comment=?,shifrwrk=?,filename=? where id=?";
    private static final String SQL_INSERT_REC = "insert into public.tb_roads_doc(idroad,iddet,idwidblob,comment,shifrwrk,filename) values (?,?,?,?,?, ?)";
    private static final String SQL_UPDATE_BLOB = "update public.tb_roads_doc set doc=?,dateupload=? where id=?";
    private static final String SQL_SELECT_BLOB_BY_ID = "select doc from public.tb_roads_doc where id=?";


    @Override
    @Transactional(readOnly = true)
    public RoadDocEntity getById(final int wantedId) {
        RowMapper<RoadDocEntity> mapper = new RowMapper<RoadDocEntity>() {
            public RoadDocEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                java.sql.Date dateupload;
                RoadDocEntity roadDoc = new RoadDocEntity();
                roadDoc.setId(wantedId);
                roadDoc.setIdroad(rs.getInt("idroad"));
                roadDoc.setIddet(rs.getInt("iddet"));
                roadDoc.setIdwidblob(rs.getInt("idwidblob"));
                roadDoc.setComment(rs.getString("comment"));
                roadDoc.setShifrwrk(rs.getInt("shifrwrk"));
                roadDoc.setFilename(rs.getString("filename"));
                dateupload=rs.getDate("dateupload");
                if (dateupload!=null) {
                    roadDoc.setDateUpload(dateupload);
                }
                return roadDoc;
            }
        };
        RoadDocEntity roadDoc;
        try {
            roadDoc = (RoadDocEntity) jdbcTemplate.queryForObject(
                    SQL_SELECT_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            roadDoc = null;
        }
        return roadDoc;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadDocEntity> getAll() {
        RowMapper<RoadDocEntity> mapper = new RowMapper<RoadDocEntity>() {
            public RoadDocEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                java.sql.Date dateupload;
                RoadDocEntity roadDoc = new RoadDocEntity();
                roadDoc.setId(rs.getInt("id"));
                roadDoc.setIdroad(rs.getInt("idroad"));
                roadDoc.setIddet(rs.getInt("iddet"));
                roadDoc.setIdwidblob(rs.getInt("idwidblob"));
                roadDoc.setComment(rs.getString("comment"));
                roadDoc.setShifrwrk(rs.getInt("shifrwrk"));
                roadDoc.setFilename(rs.getString("filename"));
                dateupload=rs.getDate("dateupload");
                if (dateupload!=null) {
                    roadDoc.setDateUpload(dateupload);
                }
                return roadDoc;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL, mapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadDocEntity> getAllForRoad(final int idroad) {
        RowMapper<RoadDocEntity> mapper = new RowMapper<RoadDocEntity>() {
            public RoadDocEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                java.sql.Date dateupload;
                RoadDocEntity roadDoc = new RoadDocEntity();
                roadDoc.setId(rs.getInt("id"));
                roadDoc.setIdroad(idroad);
                roadDoc.setIddet(rs.getInt("iddet"));
                roadDoc.setIdwidblob(rs.getInt("idwidblob"));
                roadDoc.setComment(rs.getString("comment"));
                roadDoc.setShifrwrk(rs.getInt("shifrwrk"));
                roadDoc.setFilename(rs.getString("filename"));
                dateupload=rs.getDate("dateupload");
//                System.out.println(dateupload.toString());
                if (dateupload!=null) {
                    roadDoc.setDateUpload(dateupload);
                }
                return roadDoc;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_ROAD, mapper, new Object[]{idroad});
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoadDocEntity> getAllForDet(final int iddet) {
        RowMapper<RoadDocEntity> mapper = new RowMapper<RoadDocEntity>() {
            public RoadDocEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                java.sql.Date dateupload;
                RoadDocEntity roadDoc = new RoadDocEntity();
                roadDoc.setId(rs.getInt("id"));
                roadDoc.setIdroad(rs.getInt("idroad"));
                roadDoc.setIddet(iddet);
                roadDoc.setIdwidblob(rs.getInt("idwidblob"));
                roadDoc.setComment(rs.getString("comment"));
                roadDoc.setShifrwrk(rs.getInt("shifrwrk"));
                roadDoc.setFilename(rs.getString("filename"));
                dateupload=rs.getDate("dateupload");
                if (dateupload!=null) {
                    roadDoc.setDateUpload(dateupload);
                }
                return roadDoc;
            }
        };
        return jdbcTemplate.query(SQL_SELECT_ALL_FOR_DET, mapper, new Object[]{iddet});
    }


    @Override
    public void saveRecord(RoadDocEntity roadDocEntity) {
        if (roadDocEntity.getId() > 0) {

            jdbcTemplate.update(SQL_UPDATE_REC,
                    new Object[]{roadDocEntity.getIdroad(),
                            roadDocEntity.getIddet(),
                            roadDocEntity.getIdwidblob(),
                            roadDocEntity.getComment(),
                            roadDocEntity.getShifrwrk(),
                            roadDocEntity.getFilename(),
                            roadDocEntity.getId()

                    });
        } else {
            insertRecord(roadDocEntity);
        }
    }

    @Override
    public void deleteRecord(int wantedId) {
        jdbcTemplate.update(SQL_DELETE_REC, new Object[]{wantedId});
    }

    @Override
    public void insertRecord(final RoadDocEntity roadDocEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst =
                                con.prepareStatement(SQL_INSERT_REC, new String[]{"id"});
                        pst.setLong(1, roadDocEntity.getIdroad());
                        pst.setLong(2, roadDocEntity.getIddet());
                        pst.setLong(3, roadDocEntity.getIdwidblob());
                        pst.setString(4, roadDocEntity.getComment());
                        pst.setInt(5, roadDocEntity.getShifrwrk());
                        pst.setString(6, roadDocEntity.getFilename());

                        return pst;
                    }
                },
                keyHolder);
        roadDocEntity.setId(keyHolder.getKey().intValue());
    }

   @Override
   public void updateDocument(String fileName,int id) {
       long time = System.currentTimeMillis();
       java.sql.Date currentSQLDate = new java.sql.Date(time);

        try {
            final File image = new File(fileName);
            final InputStream imageIs = new FileInputStream(image);
            LobHandler lobHandler = new DefaultLobHandler();
            jdbcTemplate.update(
                    SQL_UPDATE_BLOB,
                    new Object[] {
                            new SqlLobValue(imageIs, (int)image.length(), lobHandler),
                            currentSQLDate,
                            id
                    },
                    new int[] {Types.BLOB,Types.DATE,Types.INTEGER}
            );
            imageIs.close();


//        } catch (DataAccessException e) {
//            System.out.println("DataAccessException " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException " + e.getMessage());

        } catch (IOException e) {
           System.out.println("IOException " + e.getMessage());
        }
    }

    @Override

    public void updateDocument(byte[] bytes,int id) {
    //    System.out.println("inside update document");
        long time = System.currentTimeMillis();
        java.sql.Date currentSQLDate = new java.sql.Date(time);

        try {
            final InputStream imageIs = new ByteArrayInputStream(bytes);
            LobHandler lobHandler = new DefaultLobHandler();
            jdbcTemplate.update(
                    SQL_UPDATE_BLOB,
                    new Object[] {
                            new SqlLobValue(imageIs, (int)bytes.length, lobHandler),
                            currentSQLDate,
                            id
                    },
                    new int[] {Types.BLOB,Types.DATE,Types.INTEGER}
            );
            imageIs.close();


//        } catch (DataAccessException e) {
//            System.out.println("DataAccessException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }
    }
    @Override
    @Transactional(readOnly = true)
    public byte[] getBlobAsBytes(final long wantedId) {
        RowMapper<byte []> mapper = new RowMapper<byte []>() {
            public byte[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new DefaultLobHandler().getBlobAsBytes(rs, 1);
            }
        };
        byte[] retVal;
        try {
            retVal = (byte[]) jdbcTemplate.queryForObject(
                    SQL_SELECT_BLOB_BY_ID, mapper, new Object[]{wantedId});
            //		sql, new Object[] { wantedId }, mapper);
        } catch (EmptyResultDataAccessException e) {
            retVal = null;
        }
        return retVal;

//        return (byte[]) jdbcTemplate.queryForObject(getSql(), getParams(null), new RowMapper<byte[]>() {
//            @Override
//            public byte[] mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return new DefaultLobHandler().getBlobAsBytes(rs, 1);
//            }
//        });
    }


//    public void addToDB(final long documentId, final File file) throws IOException {
//        String sql = "insert into filetest (id, content) values (?,?)";
//        final InputStream blobIs = new FileInputStream(file);
//        DefaultLobHandler defaultLobHandler = new DefaultLobHandler();
//        jdbcTemplate
//                .execute(sql, new AbstractLobCreatingPreparedStatementCallback(defaultLobHandler) {
//
//                    @Override
//                    protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
//                        ps.setLong(1, documentId);
//                        lobCreator
//                                .setBlobAsBinaryStream(ps, 2, blobIs, (int) file
//                                        .length());
//                    }
//                });
//        blobIs.close();
//    }


//    Now it’s time to read the LOB data from the database. Again, you use a JdbcTemplate with the same instance variable l obHandler and a reference to a DefaultLobHandler.
//
//            List<Map<String, Object>> l = jdbcTemplate.query("select id, a_clob, a_blob from lob_table",
//            new RowMapper<Map<String, Object>>() {
//                public Map<String, Object> mapRow(ResultSet rs, int i) throws SQLException {
//                    Map<String, Object> results = new HashMap<String, Object>();
//                    String clobText = lobHandler.getClobAsString(rs, "a_clob"); <<1>>
//                            results.put("CLOB", clobText); byte[] blobBytes = lobHandler.getBlobAsBytes(rs, "a_blob"); <<2>>
//                            results.put("BLOB", blobBytes); return results; } });
//
//    ??? Using the method getClobAsString, retrieve the contents of the CLOB.
//
//    ??? Using the method getBlobAsBytes, retrieve the contents of the BLOB.
//
//

}
