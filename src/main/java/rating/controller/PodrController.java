package rating.controller;

import rating.domain.PodrEntity;
import rating.dto.ItemErrorDTO;
import rating.service.PodrService;
import rating.service.validators.PodrValidator;
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
@RequestMapping("/podr")

public class PodrController {

    @Autowired
    private PodrService podrService;
    protected static Logger logger = Logger.getLogger("controller");

    @Autowired
    private PodrValidator podrValidator;


    @RequestMapping(method = RequestMethod.GET)
    public String browsePodrsList(Model model, HttpSession session) {
        List<PodrEntity> podrList = podrService.getAll();
        PodrEntity podr=new PodrEntity();
        model.addAttribute("podrList", podrList);
        model.addAttribute("podr", podr);
        logger.debug("Amnt of rec=" + podrList.size());
        return "browsePodrs";
    }
    @RequestMapping(value = "/getrec", method = RequestMethod.POST)
    //  @ResponseBody
    public String getTableRow(@RequestParam int id, Model model, HttpSession session) {
        //      logger.debug("Request Ajax for group. For id = " + id);
        int idpodr = id;
        logger.debug(" id podr for edit =" + idpodr);
        PodrEntity podr;
        if (id > 0) {
            podr = podrService.getById(id);
        } else {
            podr = new PodrEntity();
        }
        model.addAttribute("podr", podr);
        logger.debug("Response Ajax  " + podr.toString());

        String viewName = "podrFormBootstrap";
        return viewName;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> saveTableR1P1Row(@ModelAttribute("PodrEntity") PodrEntity podr, BindingResult result, HttpSession session, Model model) {
        //     if (result.hasErrors()) {
        //         return "browseAllGroups";
        //     }
        podrValidator.validate(podr, result);
        if (result.hasErrors()) {
            return getErrList(result);
        }
        logger.debug("save PodrPoint row for " + podr.toString());
        podrService.saveRecord(podr);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(0, "Ok", "Ok"));
     //   session.setAttribute("needRestPosition", 1);
        return okList;
    }


    @RequestMapping(value = "/delrec/{id}", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String delPodrRec(@PathVariable int id, HttpSession session) {
        //       logger.debug("Request Ajax for delete group. For id = " + id);
        podrService.deleteRecord(id);
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
