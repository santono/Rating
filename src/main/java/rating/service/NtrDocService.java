package rating.service;


import rating.dao.NtrDocDAO;
import rating.domain.NtrDocEntity;

import rating.dto.NtrDocRecDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NtrDocService {

    protected static Logger logger = Logger.getLogger("controller");


    @Autowired
    private NtrDocDAO ntrDocDAO;
    @Autowired
    private WidBlobService widBlobService;



    public NtrDocEntity getById(final int wantedId) {
        return ntrDocDAO.getById(wantedId);
    }



    public List<NtrDocEntity> getAllForNtr(int idntr) {
        return ntrDocDAO.getAllForNtr(idntr);
    }

    public void saveRecord(NtrDocEntity ntrDocEntity) {
        ntrDocDAO.saveRecord(ntrDocEntity);
    }

    public void deleteRecord(final int wantedId) {
        ntrDocDAO.deleteRecord(wantedId);
    }

    public void deleteAllForOwner(int id) {
        ntrDocDAO.deleteAllForOwner(id);
    }

    public void insertRecord(final NtrDocEntity ntrDocEntity) {
        ntrDocDAO.insertRecord(ntrDocEntity);
    }
    public int getAnmtOfDocForNtr(int idntr) {
        List<NtrDocEntity> l= ntrDocDAO.getAllForNtr(idntr);
        return l!=null?l.size():0;
    }
    public int getAnmtOfImagesForNtr(int idntr) {
        List<NtrDocEntity> l= ntrDocDAO.getAllForNtr(idntr);
        int retVal;
        retVal=0;
        for (NtrDocEntity ntrDocEntity:l ){
            String filename=ntrDocEntity.getFilename();
            if (filename==null) continue;
            if (filename.trim().length()<3) continue;
            if (
                (filename.trim().toUpperCase().endsWith("JPEG")) ||
                (filename.trim().toUpperCase().endsWith("JPG"))  ||
                (filename.trim().toUpperCase().endsWith("GIF"))  ||
                (filename.trim().toUpperCase().endsWith("PNG"))
               )
                retVal++; 
            else {
                int idWidBlob=(int) ntrDocEntity.getIdwidblob();
                if (idWidBlob>0) {
                    String widBlob;
                    widBlob = widBlobService.getById(idWidBlob).getMime();
                    if (widBlob.trim().length()>0) {
                        if (widBlob.toUpperCase().contains("IMAGE")) {
                            retVal++;
                        }
                    }
                }
            }
                
        }
        return retVal;
    }

    public byte[] getFileIntoBytes(MultipartFile file, NtrDocEntity ntrDocEntity,String errorMes) throws Exception { // modeFile 1 - excel 2 settings
 //       this.errorMes="";
 //       this.succesMes="";
        String fileName = null;
        String blobName = null;
        if  (file.getName().trim().length()<1) {
            throw new Exception("Не указано имя файла");

        }
        if (file.isEmpty()) {
            throw new Exception("Пустой файл "+file.getName());
        }
        if (file.getSize()<1) {
            throw new Exception("Нулевой размер файла "+file.getName());
        }
//        logger.info("filename="+file.getName());
//        System.out.println("---------------");
        blobName=file.getContentType();
//        logger.info("Content type="+blobName);
//        System.out.println("================");
        int idblob=widBlobService.getIdByName(blobName);
//        System.out.println("++++++++++++++");
//        System.out.println("idblob="+idblob);
        if (idblob<0) {
            widBlobService.saveNewWidBlob(blobName);
            idblob=widBlobService.getIdByName(blobName);
        }
//        logger.info("idblob 2 version="+idblob);
        ntrDocEntity.setIdwidblob(idblob);
        try {
//            logger.info("before file name " );
            fileName = file.getOriginalFilename();
//            logger.info("fileName = "+fileName );
            ntrDocEntity.setFilename(fileName);
            byte[] bytes = file.getBytes();
//            if (bytes!=null)
//               logger.info("bytes length in service= "+bytes.length );
//            else
//                logger.info("bytes is null " );
//
            return bytes;
/*
            String rootPath = System.getProperty("catalina.home");
            File dir = new File(rootPath + File.separator + "tmpFiles");
            if (!dir.exists())
                dir.mkdirs();
            // Create the file on server
            dstFileName=dir.getAbsolutePath()
                    + File.separator + fileName;

            //     logger.debug("dstFileName="+dstFileName);
            File serverFile = new File(dstFileName);
            BufferedOutputStream buffStream =
                    new BufferedOutputStream(new FileOutputStream(serverFile));
            buffStream.write(bytes);
            buffStream.close();
            //      logger.debug("Server File Location="
            //                 + serverFile.getAbsolutePath());
            succesMes="Документ успешно загружен " + fileName;
            return true;
*/        } catch (Exception e) {
              errorMes="Ошибка загрузки документа " + fileName + ": " + e.getMessage();
            return null;
        }

    }

    public void updateDocument(byte[] bytes,int id) {
          ntrDocDAO.updateDocument(bytes,id);
    }
    
    public List<NtrDocRecDTO> getListOfDTOForNtr(int idntr) {
        List<NtrDocRecDTO> ntrList= new ArrayList<NtrDocRecDTO>();
        for (NtrDocEntity ntrDocEntity : getAllForNtr(idntr)) {
            NtrDocRecDTO ntrDTO=new NtrDocRecDTO();
            ntrDTO.setId(ntrDocEntity.getId());
            ntrDTO.setComment(ntrDocEntity.getComment());
            ntrDTO.setFilename(ntrDocEntity.getFilename());
            ntrDTO.setDateUpload(ntrDocEntity.getDateupload());
            String mimeType;
            int idwidblob=(int)ntrDocEntity.getIdwidblob();
            if (idwidblob>0)
                mimeType=widBlobService.getById(idwidblob).getMime();
            else
                mimeType="";
            ntrDTO.setMimetype(mimeType);
            if (ntrDTO.getDateUpload()!=null) {
                logger.info("date="+ntrDTO.getDateUpload().getTime().toString());
               SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
               String date1 = format1.format(ntrDTO.getDateUpload().getTime());
               ntrDTO.setDateUploadStr(date1);
            }
            String fname;
            try {
                fname=ntrDTO.getFilename().toUpperCase();
            } catch(NullPointerException e) {
                fname="не указан";
            }
            if (fname!=null)
                if (fname.trim().length()>0) {
                    if (fname.endsWith(".XLS")) {
                        ntrDTO.setImageName("Excel.png");
                        ntrDTO.setItIsImage(0);
                        ntrDTO.setTitle("Документ Excel");
                    }
                    else
                    if (fname.endsWith(".XLSX")) {
                        ntrDTO.setImageName("Excel.png");
                        ntrDTO.setItIsImage(0);
                        ntrDTO.setTitle("Документ Excel");
                    }
                    else
                    if (fname.endsWith(".DOC")) {
                        ntrDTO.setImageName("Word.png");
                        ntrDTO.setItIsImage(0);
                        ntrDTO.setTitle("Документ Word");
                    }
                    else
                    if (fname.endsWith(".DOCX")){
                        ntrDTO.setImageName("Word.png");
                        ntrDTO.setItIsImage(0);
                        ntrDTO.setTitle("Документ Word");
                    }
                    else
                    if (fname.endsWith(".TXT")) {
                        ntrDTO.setImageName("File TXT.png");
                        ntrDTO.setItIsImage(0);
                        ntrDTO.setTitle("Текстовый документ");
                    }
                    else
                    if (fname.endsWith(".PDF")) {
                        ntrDTO.setImageName("PDF.png");
                        ntrDTO.setItIsImage(0);
                        ntrDTO.setTitle("Документ PDF");
                    }
                    else
                    if (fname.endsWith(".JPEG")) {
                        ntrDTO.setImageName("JPG.png");
                        ntrDTO.setItIsImage(1);
             //           System.out.println("JPEG=1");
                        ntrDTO.setTitle("Изображение");
                    }
                    else
                    if (fname.endsWith(".JPG")) {
                        ntrDTO.setImageName("JPG.png");
                        ntrDTO.setItIsImage(1);
             //           System.out.println("JPG=1");
                        ntrDTO.setTitle("Изображение");
                    }
                    else
                    if (fname.endsWith(".GIF")) {
                        ntrDTO.setImageName("JPG.png");
                        ntrDTO.setItIsImage(1);
                        ntrDTO.setTitle("Изображение");
                    }
                    else
                    if (fname.endsWith(".PNG")) {
                        ntrDTO.setItIsImage(1);
                        ntrDTO.setImageName("JPG.png");
                        ntrDTO.setTitle("Изображение");
                    }
                    else
                        ntrDTO.setItIsImage(0);


                }
            ntrList.add(ntrDTO);
        }
        return ntrList;
    }

    public byte[] getBlobAsBytes(long wantedId) {
        return ntrDocDAO.getBlobAsBytes(wantedId);
    }
    public void updateIdNtr(int idntr,int iddoc) {
        ntrDocDAO.updateIdNtr(idntr,iddoc);
    }
    public List<Integer> getIdListOfImagesForNtr(int idntr) {
        List<Integer> listIds=new ArrayList<Integer>();
        List<NtrDocEntity> l= ntrDocDAO.getAllForNtr(idntr);
        for (NtrDocEntity ntrDocEntity:l ){
            String filename=ntrDocEntity.getFilename();
            if (filename==null) continue;
            if (filename.trim().length()<3) continue;
            if (
                    (filename.trim().toUpperCase().endsWith("JPEG")) ||
                            (filename.trim().toUpperCase().endsWith("JPG"))  ||
                            (filename.trim().toUpperCase().endsWith("GIF"))  ||
                            (filename.trim().toUpperCase().endsWith("PNG"))
                    )
                listIds.add((int)ntrDocEntity.getId());
            else {
                int idWidBlob=(int) ntrDocEntity.getIdwidblob();
                if (idWidBlob>0) {
                    String widBlob;
                    widBlob = widBlobService.getById(idWidBlob).getMime();
                    if (widBlob.trim().length()>0) {
                        if (widBlob.toUpperCase().contains("IMAGE")) {
                            listIds.add((int)ntrDocEntity.getId());
                        }
                    }
                }
            }

        }
        return listIds;
    }
}
