package rating.controller;

import rating.domain.WidCoverEntity;
import rating.dto.ItemErrorDTO;
import rating.service.WidCoverService;
import rating.service.validators.WidCoverValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/widcover")
public class WidCoverController {
    @Autowired
    private WidCoverService widCoverService;
    protected static Logger logger = Logger.getLogger("controller");

    @Autowired
    private WidCoverValidator widCoverValidator;
    @RequestMapping(method = RequestMethod.GET)
    public String browseWidDamageList(Model model) {
        List<WidCoverEntity> widCoverList = widCoverService.getAll();
        WidCoverEntity widCover=new WidCoverEntity();
        model.addAttribute("wcList", widCoverList);
        model.addAttribute("wc", widCover);
        logger.debug("Amnt of wc rec=" + widCoverList.size());
        return "browseWidCover";
    }

    @RequestMapping(value = "/getrec", method = RequestMethod.POST)
    //  @ResponseBody
    public String getTableRow(@RequestParam int id, Model model) {
    //    logger.debug("Request Ajax for widcoverrec. For id = " + id);
        int idwd = id;
        logger.debug(" id wc for edit =" + idwd);
        WidCoverEntity wc;
        if (id > 0) {
            wc = widCoverService.getById(id);
        } else {
            wc = new WidCoverEntity();
        }
        model.addAttribute("wc", wc);
        logger.debug("Response Ajax  " + wc.toString());

        String viewName = "widCoverFormBootstrap";
        return viewName;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> saveTableWCRow(@ModelAttribute("WidCoverEntity") WidCoverEntity widCoverEntity, BindingResult result, Model model) {
        //     if (result.hasErrors()) {
        //         return "browseAllGroups";
        //     }
        logger.debug("----save started save WidCOverEntity row for " + widCoverEntity.toString());
        widCoverValidator.validate(widCoverEntity, result);
        if (result.hasErrors()) {
            return getErrList(result);
        }
        logger.debug("save WidCoverEntity row for " + widCoverEntity.toString());
        widCoverService.saveRecord(widCoverEntity);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(0, "Ok", "Ok"));
        //   session.setAttribute("needRestPosition", 1);
        return okList;
    }

    @RequestMapping(value = "/delrec/{id}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String delWidCoverRec(@PathVariable int id) {
        //       logger.debug("Request Ajax for delete group. For id = " + id);
        widCoverService.deleteRecord(id);
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
