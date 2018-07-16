package rating.controller;

import rating.domain.RoadEntity;
import rating.domain.RoadDetEntity;
import rating.dto.ColsDTO;
import rating.dto.ItemErrorDTO;
import rating.dto.ItemRDColsDTO;
import rating.dto.RoadDetDTO;
import rating.service.PodrService;
import rating.service.RoadDetService;
import rating.service.RoadService;
import rating.service.WidRoadService;
import rating.service.validators.RoadDetValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/rd")
public class RoadDetController {
    @Autowired
    private RoadService roadService;
    @Autowired
    private PodrService podrService;
    @Autowired
    private RoadDetService rdService;
    @Autowired
    private RoadDetValidator rdValidator;
    @Autowired
    private WidRoadService wrService;

    protected static Logger logger = Logger.getLogger("controller");

    @RequestMapping(method = RequestMethod.GET)
    public String browseRoadDetList(@RequestParam(required = true) int idroad, Model model, HttpSession session) {
        RoadDetEntity rdEntity = new RoadDetEntity();
        RoadEntity rEntity = roadService.getById(idroad);
        session.setAttribute("t", rEntity);
        logger.debug(" idroad=" + idroad);
        model.addAttribute("nameRoad", rEntity.getCode().trim() + " " + rEntity.getName().trim()+" "+(rEntity.getShifridwr()>0?wrService.getById(rEntity.getShifridwr()).getName():""));
        List<RoadDetDTO> lrdt=rdService.getAllScreenDetRowsForRoad(idroad);
        logger.debug(" amount of dets = " + lrdt.size());
        model.addAttribute("detlist", lrdt);
        model.addAttribute("tDet", rdEntity);
        model.addAttribute("podrlist",podrService.getAll());
        session.setAttribute("needRestPosition", 1);
        List<ItemRDColsDTO> listRDCols;
        listRDCols=(List<ItemRDColsDTO>)session.getAttribute("listRDCols");
        if (listRDCols==null)  {
           session.setAttribute("listRDCols",rdService.getColsList());
        }
        Map<String,String> mapneed=rdService.getColsMap((List<ItemRDColsDTO>) session.getAttribute("listRDCols"));
        int colspan=rdService.getColSpan(mapneed);
        ColsDTO colsDTO=rdService.getColsDTO(mapneed);
        model.addAttribute("listRDCols",listRDCols);
        model.addAttribute("colspan",colspan);
        model.addAttribute("mapneed",mapneed);
        model.addAttribute("colsDTO",colsDTO);
        logger.debug(" colspan==" + colspan+" mapneed.get('3')="+mapneed.get("3")+ " mapneed.size()="+mapneed.size());
        String viewName;
        viewName = "browseRDTable";
        return viewName;
    }

    @RequestMapping(value = "/getgrid", method = RequestMethod.POST)
    public String listGrid(Model model, HttpSession session) {
        //    logger.debug("Listing tableR1P2Rows ");
        RoadEntity t = (RoadEntity) session.getAttribute("t");
        int idroad = t.getId();
        String viewName = new String();
        List<RoadDetDTO> tDetList = rdService.getAllScreenDetRowsForRoad(idroad);
        model.addAttribute("detlist", tDetList);
        List<ItemRDColsDTO> listRDCols;
        listRDCols=(List<ItemRDColsDTO>)session.getAttribute("listRDCols");
        if (listRDCols==null)  {
            session.setAttribute("listRDCols",rdService.getColsList());
        }
        Map<String,String> mapneed=rdService.getColsMap((List<ItemRDColsDTO>) session.getAttribute("listRDCols"));
        ColsDTO colsDTO=rdService.getColsDTO(mapneed);
        model.addAttribute("mapneed",mapneed);
        int colspan=rdService.getColSpan(mapneed);
        model.addAttribute("listRDCols",listRDCols);
        model.addAttribute("colspan",colspan);
        model.addAttribute("colsDTO",colsDTO);
        logger.debug(" colspan/getgrid==" + colspan);
        viewName = "rdRowsGrid";
        logger.debug(" nmb_get_grid or rddet " + tDetList.size());
        return viewName;
    }


    @RequestMapping(value = "/getrec", method = RequestMethod.POST)
    //  @ResponseBody
    public String getTableRow(@RequestParam int id, Model model, HttpSession session) {
        //      logger.debug("Request Ajax for group. For id = " + id);
        RoadEntity t = (RoadEntity) session.getAttribute("t");
        int idroad = t.getId();
        logger.debug(" id road=" + idroad);
        RoadDetEntity tDet;
        if (id > 0) {
            tDet = rdService.getById(id);
        } else {
            tDet = new RoadDetEntity();
            tDet.setIdroad(idroad);
        }
        model.addAttribute("tDet", tDet);
        model.addAttribute("podrlist",podrService.getAll());
        logger.debug("Response Ajax  " + tDet.toString());

        String viewName = "tRDRowFormBootstrap";
        return viewName;
    }

    @RequestMapping(value = "/setcols", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> setTableCols(@ModelAttribute("ColsDTO") ColsDTO tDet, BindingResult result, HttpSession session, Model model) {
        //      logger.debug("Request Ajax for group. For id = " + id);
        List<ItemRDColsDTO> listRDCols,listRDColsOut;
        listRDCols=(List<ItemRDColsDTO>) session.getAttribute("listRDCols");
        listRDColsOut=rdService.setCols(tDet,listRDCols);
        session.setAttribute("listRDCols",listRDColsOut);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(0, "Ok", "Ok"));
        return okList;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> saveTableR1P1Row(@ModelAttribute("RoadDetEntity") RoadDetEntity tDet, BindingResult result, HttpSession session, Model model) {
        //     if (result.hasErrors()) {
        //         return "browseAllGroups";
        //     }
        rdValidator.validate(tDet, result);
        if (result.hasErrors()) {
            return getErrList(result);
        }
        logger.debug("save RoadDet row for " + tDet.toString());
        if (tDet.getIdroad() == 0) {
            RoadEntity t = (RoadEntity) session.getAttribute("t");
            int idroad = t.getId();
            tDet.setIdroad(idroad);
        }
        rdService.saveRecord(tDet);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(0, "Ok", "Ok"));
        return okList;
    }



    @RequestMapping(value = "/delrec/{id}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String delTableRow(@PathVariable int id, HttpSession session) {
        //       logger.debug("Request Ajax for delete group. For id = " + id);
        rdService.deleteRecord(id);
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


}
