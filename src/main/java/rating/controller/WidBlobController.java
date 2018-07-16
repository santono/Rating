package rating.controller;

import rating.domain.WidBlobEntity;
import rating.dto.ItemErrorDTO;
import rating.service.WidBlobService;
import rating.service.validators.WidBlobValidator;
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
@RequestMapping("/widblob")
public class WidBlobController {

    @Autowired
    private WidBlobService widBlobService;
    protected static Logger logger = Logger.getLogger("controller");

    @Autowired
    private WidBlobValidator widBlobValidator;


    @RequestMapping(method = RequestMethod.GET)
    public String browseWidBlobList(Model model) {
        List<WidBlobEntity> widBlobList = widBlobService.getAll();
        WidBlobEntity widBlob=new WidBlobEntity();
        model.addAttribute("wbList", widBlobList);
        model.addAttribute("wb", widBlob);
        logger.debug("Amnt of wb rec=" + widBlobList.size());
        return "browseWidBlob";
    }

    @RequestMapping(value = "/getrec", method = RequestMethod.POST)
    //  @ResponseBody
    public String getTableRow(@RequestParam int id, Model model, HttpSession session) {
        //      logger.debug("Request Ajax for group. For id = " + id);
        int idwb = id;
        logger.debug(" id wb for edit =" + idwb);
        WidBlobEntity wb;
        if (id > 0) {
            wb = widBlobService.getById(id);
        } else {
            wb = new WidBlobEntity();
        }
        model.addAttribute("wb", wb);
        logger.debug("Response Ajax  " + wb.toString());

        String viewName = "widBlobFormBootstrap";
        return viewName;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> saveTableWDRow(@ModelAttribute("WidBlobEntity") WidBlobEntity widBlobEntity, BindingResult result, HttpSession session, Model model) {
        //     if (result.hasErrors()) {
        //         return "browseAllGroups";
        //     }
        logger.debug("----save started save WidBlobentity row for " + widBlobEntity.toString());
        widBlobValidator.validate(widBlobEntity, result);
        if (result.hasErrors()) {
            return getErrList(result);
        }
        logger.debug("save WidBlobentity row for " + widBlobEntity.toString());
        widBlobService.saveRecord(widBlobEntity);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(0, "Ok", "Ok"));
        //   session.setAttribute("needRestPosition", 1);
        return okList;
    }


    @RequestMapping(value = "/delrec/{id}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String delWidBlobRec(@PathVariable int id) {
        //       logger.debug("Request Ajax for delete group. For id = " + id);
        widBlobService.deleteRecord(id);
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
