package rating.controller;

import rating.domain.WidDamageEntity;
import rating.dto.ItemErrorDTO;
import rating.service.WidDamageService;
import rating.service.validators.WidDamageValidator;
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
@RequestMapping("/widdamage")
public class WidDamageController {

    @Autowired
    private WidDamageService widDamageService;
    protected static Logger logger = Logger.getLogger("controller");

    @Autowired
    private WidDamageValidator widDamageValidator;


    @RequestMapping(method = RequestMethod.GET)
    public String browseWidDamageList(Model model) {
        List<WidDamageEntity> widDamageList = widDamageService.getAll();
        WidDamageEntity widDamage=new WidDamageEntity();
        model.addAttribute("wdList", widDamageList);
        model.addAttribute("wd", widDamage);
        logger.debug("Amnt of wd rec=" + widDamageList.size());
        return "browseWidDamage";
    }

    @RequestMapping(value = "/getrec", method = RequestMethod.POST)
    //  @ResponseBody
    public String getTableRow(@RequestParam int id, Model model, HttpSession session) {
        //      logger.debug("Request Ajax for group. For id = " + id);
        int idwd = id;
        logger.debug(" id wd for edit =" + idwd);
        WidDamageEntity wd;
        if (id > 0) {
            wd = widDamageService.getById(id);
        } else {
            wd = new WidDamageEntity();
        }
        model.addAttribute("wd", wd);
        logger.debug("Response Ajax  " + wd.toString());

        String viewName = "widDamageFormBootstrap";
        return viewName;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> saveTableWDRow(@ModelAttribute("WidDamageEntity") WidDamageEntity widDamageEntity, BindingResult result, HttpSession session, Model model) {
        //     if (result.hasErrors()) {
        //         return "browseAllGroups";
        //     }
        logger.debug("----save started save WidDamagEentity row for " + widDamageEntity.toString());
        widDamageValidator.validate(widDamageEntity, result);
        if (result.hasErrors()) {
            return getErrList(result);
        }
        logger.debug("save WidDamagEentity row for " + widDamageEntity.toString());
        widDamageService.saveRecord(widDamageEntity);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(0, "Ok", "Ok"));
        //   session.setAttribute("needRestPosition", 1);
        return okList;
    }


    @RequestMapping(value = "/delrec/{id}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String delWidDamageRec(@PathVariable int id) {
        //       logger.debug("Request Ajax for delete group. For id = " + id);
        widDamageService.deleteRecord(id);
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
