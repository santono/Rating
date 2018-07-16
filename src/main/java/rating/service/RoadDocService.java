package rating.service;

import rating.dao.RoadDocDAO;
import rating.domain.RoadDocEntity;

import rating.dto.RoadDocRecDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RoadDocService {

    protected static Logger logger = Logger.getLogger("controller");


    @Autowired
    private RoadDocDAO rDocDAO;
    @Autowired
    private WidBlobService widBlobService;



    public RoadDocEntity getById(final int wantedId) {
        return rDocDAO.getById(wantedId);
    }


    public List<RoadDocEntity> getAll() {
        return rDocDAO.getAll();
    }

    public List<RoadDocEntity> getAllForRoad(int idroad) {
        return rDocDAO.getAllForRoad(idroad);
    }

    public List<RoadDocEntity> getAllForDet(int iddet) {
        return rDocDAO.getAllForDet(iddet);
    }

    public void saveRecord(RoadDocEntity roadDocEntity) {
        rDocDAO.saveRecord(roadDocEntity);
    }

    public void deleteRecord(final int wantedId) {
        rDocDAO.deleteRecord(wantedId);
    }

    public void insertRecord(final RoadDocEntity roadDocEntity) {
        rDocDAO.insertRecord(roadDocEntity);
    }

    public byte[] getFileIntoBytes(MultipartFile file, RoadDocEntity rDocEntity, String succesMes,String errorMes) throws Exception { // modeFile 1 - excel 2 settings
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
        rDocEntity.setIdwidblob(idblob);
        try {
//            logger.info("before file name " );
            fileName = file.getOriginalFilename();
//            logger.info("fileName = "+fileName );
            rDocEntity.setFilename(fileName);
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
          rDocDAO.updateDocument(bytes,id);
    }
    
    public List<RoadDocRecDTO> getListOfDTOForRoad(int idroad) {
        List<RoadDocRecDTO> rdrList= new ArrayList<RoadDocRecDTO>();
        for (RoadDocEntity rDocEntity : getAllForRoad(idroad)) {
            RoadDocRecDTO rdrDTO=new RoadDocRecDTO();
            rdrDTO.setId(rDocEntity.getId());
            rdrDTO.setComment(rDocEntity.getComment());
            rdrDTO.setFilename(rDocEntity.getFilename());
            rdrDTO.setDateUpload(rDocEntity.getDateUpload());
            if (rdrDTO.getDateUpload()!=null) {
                logger.info("date="+rdrDTO.getDateUpload().getTime().toString());
               SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
               String date1 = format1.format(rdrDTO.getDateUpload().getTime());
               rdrDTO.setDateUploadStr(date1);
            }
            String fname=rdrDTO.getFilename().toUpperCase();
            if (fname!=null)
                if (fname.trim().length()>0) {
                    if (fname.endsWith(".XLS")) {
                        rdrDTO.setImageName("Excel.png");
                        rdrDTO.setItIsImage(0);
                        rdrDTO.setTitle("Документ Excel");
                    }
                    else
                    if (fname.endsWith(".XLSX")) {
                        rdrDTO.setImageName("Excel.png");
                        rdrDTO.setItIsImage(0);
                        rdrDTO.setTitle("Документ Excel");
                    }
                    else
                    if (fname.endsWith(".DOC")) {
                        rdrDTO.setImageName("Word.png");
                        rdrDTO.setItIsImage(0);
                        rdrDTO.setTitle("Документ Word");
                    }
                    else
                    if (fname.endsWith(".DOCX")){
                        rdrDTO.setImageName("Word.png");
                        rdrDTO.setItIsImage(0);
                        rdrDTO.setTitle("Документ Word");
                    }
                    else
                    if (fname.endsWith(".TXT")) {
                        rdrDTO.setImageName("File TXT.png");
                        rdrDTO.setItIsImage(0);
                        rdrDTO.setTitle("Текстовый документ");
                    }
                    else
                    if (fname.endsWith(".PDF")) {
                        rdrDTO.setImageName("PDF.png");
                        rdrDTO.setItIsImage(0);
                        rdrDTO.setTitle("Документ PDF");
                    }
                    else
                    if (fname.endsWith(".JPEG")) {
                        rdrDTO.setImageName("JPG.png");
                        rdrDTO.setItIsImage(1);
                        System.out.println("JPEG=1");
                        rdrDTO.setTitle("Изображение");
                    }
                    else
                    if (fname.endsWith(".JPG")) {
                        rdrDTO.setImageName("JPG.png");
                        rdrDTO.setItIsImage(1);
                        System.out.println("JPG=1");
                        rdrDTO.setTitle("Изображение");
                    }
                    else
                    if (fname.endsWith(".GIF")) {
                        rdrDTO.setImageName("JPG.png");
                        rdrDTO.setItIsImage(1);
                        rdrDTO.setTitle("Изображение");
                    }
                    else
                    if (fname.endsWith(".PNG")) {
                        rdrDTO.setItIsImage(1);
                        rdrDTO.setImageName("JPG.png");
                        rdrDTO.setTitle("Изображение");
                    }
                    else
                        rdrDTO.setItIsImage(0);


                }
            rdrList.add(rdrDTO);
        }
        return rdrList;
    }

    public byte[] getBlobAsBytes(long wantedId) {
        return rDocDAO.getBlobAsBytes(wantedId);
    }
}
