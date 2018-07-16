package rating.controller;

import rating.domain.RoadEntity;
import rating.domain.RoadPointEntity;
import rating.dto.ItemErrorDTO;
import rating.service.RoadPointService;
import rating.service.RoadService;
import rating.service.validators.RoadPointValidator;
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

@Controller
@RequestMapping("/rp")
public class RoadPointController {
    @Autowired
    private RoadService roadService;
    @Autowired
    private RoadPointService rpService;
    @Autowired
    private RoadPointValidator rpValidator;


    protected static Logger logger = Logger.getLogger("controller");

    @RequestMapping(method = RequestMethod.GET)
    public String browseRoadCityList(@RequestParam(required = true) int idroad, Model model, HttpSession session) {
        RoadPointEntity rpEntity = new RoadPointEntity();
        RoadEntity rEntity = roadService.getById(idroad);
        session.setAttribute("t", rEntity);
        logger.debug(" idroad=" + idroad);
        model.addAttribute("nameRoad", rEntity.getCode().trim() + " " + rEntity.getName().trim());
        model.addAttribute("detlist", rpService.getAllForRoad(idroad));
        model.addAttribute("tDet", rpEntity);
        session.setAttribute("needRestPosition", 1);
        String viewName;
        viewName = "browseRPTable";
        return viewName;
    }

    @RequestMapping(value = "/getgrid", method = RequestMethod.POST)
    public String listGrid(Model model, HttpSession session) {
        //    logger.debug("Listing tableR1P2Rows ");
        RoadEntity t = (RoadEntity) session.getAttribute("t");
        int idroad = t.getId();
        String viewName = new String();
        List<RoadPointEntity> tDetList = rpService.getAllForRoad(idroad);
        model.addAttribute("detlist", tDetList);
        viewName = "rpRowsGrid";
        logger.debug(" nmb_get_grid or rpdet " + tDetList.size());
        return viewName;
    }


    @RequestMapping(value = "/getrec", method = RequestMethod.POST)
    //  @ResponseBody
    public String getTableRow(@RequestParam int id, Model model, HttpSession session) {
        //      logger.debug("Request Ajax for group. For id = " + id);
        RoadEntity t = (RoadEntity) session.getAttribute("t");
        int idroad = t.getId();
        logger.debug(" id road=" + idroad);
        RoadPointEntity tDet;
        if (id > 0) {
            tDet = rpService.getById(id);
        } else {
            tDet = new RoadPointEntity();
            tDet.setIdroad(idroad);
        }
        model.addAttribute("tDet", tDet);
        logger.debug("Response Ajax  " + tDet.toString());

        String viewName = "tRPRowFormBootstrap";
        return viewName;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> saveTableR1P1Row(@ModelAttribute("RoadPointEntity") RoadPointEntity tDet, BindingResult result, HttpSession session, Model model) {
        //     if (result.hasErrors()) {
        //         return "browseAllGroups";
        //     }
        rpValidator.validate(tDet, result);
        if (result.hasErrors()) {
            return getErrList(result);
        }
        logger.debug("save RoadPoint row for " + tDet.toString());
        if (tDet.getIdroad() == 0) {
            RoadEntity t = (RoadEntity) session.getAttribute("t");
            int idroad = t.getId();
            tDet.setIdroad(idroad);
        }
        rpService.saveRecord(tDet);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(0, "Ok", "Ok"));
        return okList;
    }



    @RequestMapping(value = "/delrec/{id}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String delTableRow(@PathVariable int id, HttpSession session) {
        //       logger.debug("Request Ajax for delete group. For id = " + id);
        rpService.deleteRecord(id);
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
