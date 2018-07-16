package rating.controller;

import rating.domain.RoadEntity;
import rating.domain.RoadDocEntity;
import rating.domain.WidBlobEntity;
import rating.dto.ItemErrorDTO;
import rating.dto.RoadDocRecDTO;
import rating.service.RoadDocService;
import rating.service.RoadService;
import rating.service.WidRoadService;
import rating.service.WidBlobService;
import rating.service.validators.RoadDocValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/rdoc")
public class RoadDocController {
    @Autowired
    private RoadService roadService;
    @Autowired
    private WidBlobService widBlobService;
    @Autowired
    private RoadDocService rDocService;
    @Autowired
    private RoadDocValidator rDocValidator;
    @Autowired
    private WidRoadService wrService;

    protected static Logger logger = Logger.getLogger("controller");

    @RequestMapping(method = RequestMethod.GET)
    public String browseRoadDocList(@RequestParam(required = true) int idroad, Model model, HttpSession session) {
        RoadDocEntity rDocEntity = new RoadDocEntity();
        RoadEntity rEntity = roadService.getById(idroad);
        session.setAttribute("t", rEntity);
        logger.debug(" idroad=" + idroad);
        model.addAttribute("nameRoad", rEntity.getCode().trim() + " " + rEntity.getName().trim()+" "+(rEntity.getShifridwr()>0?wrService.getById(rEntity.getShifridwr()).getName():""));
        List<RoadDocRecDTO> lrdoct = rDocService.getListOfDTOForRoad(idroad);
//        List<RoadDocEntity> lrdoct=rDocService.getAllForRoad(idroad);
        logger.debug(" amount of docs = " + lrdoct.size());
        model.addAttribute("detlist", lrdoct);
        model.addAttribute("tDet", rDocEntity);
        model.addAttribute("wbloblist",widBlobService.getAll());
        String viewName;
        viewName = "browseRDocTable";
        return viewName;
    }

    @RequestMapping(value = "/getgrid", method = RequestMethod.POST)
    public String listGrid(Model model, HttpSession session) {
        //    logger.debug("Listing tableR1P2Rows ");
        RoadEntity t = (RoadEntity) session.getAttribute("t");
        int idroad = t.getId();
        String viewName = new String();
//        List<RoadDocEntity> lrdoct=rDocService.getAllForRoad(idroad);
        List<RoadDocRecDTO> lrdoct = rDocService.getListOfDTOForRoad(idroad);
        model.addAttribute("detlist", lrdoct);
        viewName = "rDocRowsGrid";
        return viewName;
    }


    @RequestMapping(value = "/getrec", method = RequestMethod.POST)
    //  @ResponseBody
    public String getTableRow(@RequestParam int id, Model model, HttpSession session) {
        //      logger.debug("Request Ajax for group. For id = " + id);
        RoadEntity t = (RoadEntity) session.getAttribute("t");
        if (t==null) {
            return "redirect:errors/customerror";
        }
        int idroad = t.getId();
        logger.debug(" id road=" + idroad);
        RoadDocEntity tDet;
        if (id > 0) {
            tDet = rDocService.getById(id);
        } else {
            tDet = new RoadDocEntity();
            tDet.setIdroad(idroad);
        }
        model.addAttribute("tDet", tDet);
        model.addAttribute("wbloblist",widBlobService.getAll());
        logger.debug("Response Ajax  " + tDet.toString());

        String viewName = "tRDocFormBootstrap";
        return viewName;
    }


/*
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> saveRDocRow(@ModelAttribute("RoadDocEntity") RoadDocEntity tDet, BindingResult result, HttpSession session, Model model) {
        //     if (result.hasErrors()) {
        //         return "browseAllGroups";
        //     }
        rDocValidator.validate(tDet, result);
        if (result.hasErrors()) {
            return getErrList(result);
        }
        logger.debug("save RoadDet row for " + tDet.toString());
        if (tDet.getIdroad() == 0) {
            RoadEntity t = (RoadEntity) session.getAttribute("t");
            int idroad = t.getId();
            tDet.setIdroad(idroad);
        }
        rDocService.saveRecord(tDet);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(0, "Ok", "Ok"));
        return okList;
    }

*/

    @RequestMapping(value = "/delrec/{id}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String delTableRow(@PathVariable int id, HttpSession session) {
        logger.debug("Request Ajax for delete group. For id = " + id);
        rDocService.deleteRecord(id);
        String viewName = "Запись удалена!";
        return viewName;
    }


    private List<ItemErrorDTO> getErrList(BindingResult result) {
        if (result.hasErrors()) {
            List<ItemErrorDTO> errList = new ArrayList<ItemErrorDTO>();
            int errCnt = result.getErrorCount();
            if (errCnt > 0) {
                int i;
                i = 0;
                for (ObjectError oError : result.getAllErrors()) {
                    ItemErrorDTO errDTO = new ItemErrorDTO(i++, oError.getDefaultMessage(), oError.getCode());
                    errList.add(errDTO);
                }
            }
            return errList;
        } else
            return null;

    }


    @RequestMapping(value = "/save",method=RequestMethod.POST, produces = "application/json" )
    @ResponseBody

    public List<ItemErrorDTO> saveDocFromForm(@ModelAttribute("roadDocEntity") RoadDocEntity roadDocEntity,
                                  BindingResult bindingResult,
//                                  @RequestParam(value = "needDocumentImport", required = false) Integer needDocumentImport,
                                  @RequestParam(value = "document", required = false) MultipartFile filedoc,
                                  Model model, HttpServletResponse response) {

//        logger.debug("save roadDocRev Ajax. Comment  " + roadDocEntity.getComment());

        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        rDocValidator.validate(roadDocEntity, bindingResult);
        if (bindingResult.hasErrors()) {
            return getErrList(bindingResult);
        }
  //      setCookiesToBrowser(plan,response);
        String succesMes="";
        String errorMes="";
//        if (filedoc==null) {
//            logger.debug("filedoc is null  ");
//        }
//        else
//        if (filedoc.isEmpty()) {
//            logger.debug("filedoc is empty  ");
//        }
        if ((filedoc==null) || (filedoc.isEmpty())) {
//            logger.debug("save roadDocRev Ajax. Before save  ");
            rDocService.saveRecord(roadDocEntity);
//            logger.debug("save roadDocRev Ajax. Before save  ");
            okList.add(new ItemErrorDTO(0, "Ok", "Ok"));
        }
//        if (needDocumentImport!=null)
//        if (needDocumentImport==1)
        else {
             if (filedoc !=null)
             if (!filedoc.isEmpty())
                try {
//            System.out.println("inside try ");
                     byte[] bytes=rDocService.getFileIntoBytes(filedoc,roadDocEntity,succesMes,errorMes);

                     if (bytes!=null) {
                        rDocService.saveRecord(roadDocEntity);
                        rDocService.updateDocument(bytes,(int)roadDocEntity.getId());
                     }
                     okList.add(new ItemErrorDTO(0,"Ok","Ok"));
                } catch (Exception e) {
                     okList.add(new ItemErrorDTO(1,"Err"+e.getMessage(),"Ошибка обработки файла документа "+e.getMessage()));
//            model.addAttribute("message","Ошибка обработки файла документа "+e.getMessage());

                }
        }

        return okList;
    }

//    @RequestMapping(value = "/image/{imgId}", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseEntity<InputStreamResource> downloadUserAvatarImage(@PathVariable Long userId) {
//        GridFSDBFile gridFsFile = fileService.findUserAccountAvatarById(userId);
//
//        return ResponseEntity.ok()
//                .contentLength(gridFsFile.getLength())
//                .contentType(PageAttributes.MediaType.parseMediaType(gridFsFile.getContentType()))
//                .body(new InputStreamResource(gridFsFile.getInputStream()));
//    }

//    @RequestMapping(value = "/image/{id}")
//    public @ResponseBody
//    byte[] showImage(@PathVariable long id) {
//        logger.debug("start ShowImage conlroller. id="+id);
//
//        byte[] b;
//        if (id>0) {
//           b=rDocService.getBlobAsBytes(id);
//           logger.debug("bytearay lenght="+b.length);
//        }
//        else
//         b=null;
//        return b;
//    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImageAsResponseEntity(@PathVariable long id) {
        HttpHeaders headers = new HttpHeaders();
        byte[] media;
        RoadDocEntity rDocEntity;
        WidBlobEntity widBlobEntity;
        if (id>0) {
           media=rDocService.getBlobAsBytes(id);
            rDocEntity=rDocService.getById((int)id);
            if (rDocEntity.getIdwidblob()>0) {
                widBlobEntity=widBlobService.getById((int) rDocEntity.getIdwidblob());
            } else {
                widBlobEntity=null;
            }
      //     logger.debug("bytearay length="+media.length);
        } else {
           media=null;
           rDocEntity=null;
            widBlobEntity=null;
        }
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        try {
            headers.setContentLength(media.length);
        }
        catch (Exception e) {
            headers.setContentLength(0);
        }
        try {
            if (widBlobEntity!=null)
                headers.setContentType(MediaType.parseMediaType(widBlobEntity.getMime()));
            else
                headers.setContentType(MediaType.TEXT_HTML);
        }
        catch (Exception e) {
            headers.setContentType(MediaType.TEXT_HTML);
        }

    //    ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(media, headers, HttpStatus.OK);
        //    return responseEntity;
      return new ResponseEntity<byte[]>(media, headers, HttpStatus.OK);
//        return ResponseEntity.ok()
//                .contentLength(media.length)
//                .contentType(MediaType.parseMediaType(widBlobEntity.getMime())
//                .body(media);
        
    }

    @RequestMapping(value = "/doc/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getDocumentAsResponseEntity(@PathVariable long id) {
        HttpHeaders headers = new HttpHeaders();
        byte[] media;
        RoadDocEntity rDocEntity;
        WidBlobEntity widBlobEntity;
        if (id>0) {
            media=rDocService.getBlobAsBytes(id);
            rDocEntity=rDocService.getById((int)id);
            if (rDocEntity.getIdwidblob()>0) {
                widBlobEntity=widBlobService.getById((int) rDocEntity.getIdwidblob());
            } else {
                widBlobEntity=null;
            }
            logger.debug("bytearay length="+media.length);
        } else {
            media=null;
            rDocEntity=null;
            widBlobEntity=null;
        }
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        try {
            headers.setContentLength(media.length);
        }
        catch (Exception e) {
            headers.setContentLength(0);
        }
        try {
            if (widBlobEntity!=null)
                headers.setContentType(MediaType.parseMediaType(widBlobEntity.getMime()));
            else
                headers.setContentType(MediaType.TEXT_HTML);
        }
        catch (Exception e) {
            headers.setContentType(MediaType.TEXT_HTML);
        }

        //    ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(media, headers, HttpStatus.OK);
        //    return responseEntity;
        return new ResponseEntity<byte[]>(media, headers, HttpStatus.OK);
    }
    




/*

    @RequestMapping(value = "/save",method=RequestMethod.POST )
    public String saveDocFromForm(@ModelAttribute("roadDocEntity") RoadDocEntity roadDocEntity,
                                              BindingResult bindingResult,
                                              @RequestParam(value = "needDocumentImport", required = false) Integer needDocumentImport,
                                              @RequestParam(value = "document", required = false) MultipartFile filedoc,
                                              Model model, HttpServletResponse response,
                                              RedirectAttributes redirectAttributes) {
*/
/*
    public String saveDocFromForm(@ModelAttribute("roadDocEntity") RoadDocEntity roadDocEntity,
                                  BindingResult bindingResult,
                                  @RequestParam(value = "document", required = false) MultipartFile filedoc,
                                  Model model, HttpServletResponse response,
                                  RedirectAttributes redirectAttributes) {
//        logger.debug("save roadDocRev Ajax. Comment  " + roadDocEntity.getComment());

        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        rDocValidator.validate(roadDocEntity, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Ошибка формата");
            return "redirect:uploadStatus";
        }
        //      setCookiesToBrowser(plan,response);
        String succesMes="";
        String errorMes="";
//        logger.debug("save roadDocRev Ajax. Before save  ");
        rDocService.saveRecord(roadDocEntity);
//        logger.debug("save roadDocRev Ajax. After save  ");
        int needDocumentImport=1;
//        if (needDocumentImport!=null)
            if (needDocumentImport==1)
                if (filedoc !=null)
                    if (!filedoc.isEmpty())
                        try {
                            byte[] bytes=rDocService.getFileIntoBytes(filedoc,roadDocEntity,succesMes,errorMes);

                            if (bytes!=null) {
//                                logger.debug("bytes length= "+bytes.length);
                                rDocService.saveRecord(roadDocEntity);
//                                logger.debug("filename= "+roadDocEntity.getFilename()+" iswidblob="+roadDocEntity.getIdwidblob());

                                rDocService.updateDocument(bytes,(int)roadDocEntity.getId());
                            }
                            okList.add(new ItemErrorDTO(0,"Ok","Ok"));
                        } catch (Exception e) {
                            okList.add(new ItemErrorDTO(1,"Err","Ошибка обработки файла документа "+e.getMessage()));
//            model.addAttribute("message","Ошибка обработки файла документа "+e.getMessage());

                        }

        redirectAttributes.addFlashAttribute("message", "Файл загружен");
        return "redirect:uploadStatus";
    }

    @RequestMapping(value = "/uploadStatus",method=RequestMethod.GET )
    public String uploadPage() {
        return "statuspage";
    }
*/
}
