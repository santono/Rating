package rating.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import rating.domain.UserEntity;
import rating.dto.ItemErrorDTO;
import rating.service.DolgService;
import rating.service.PodrService;
import rating.service.UStepService;
import rating.service.UserService;
import rating.service.validators.UserValidator;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService uService;
    @Autowired
    private UStepService uStepService;
    @Autowired
    private PodrService podrService;
    @Autowired
    private DolgService dolgService;
    @Autowired
    private UserValidator userValidator;


    protected static Logger logger = Logger.getLogger("controller");

    @RequestMapping(value="/register",method = RequestMethod.GET)
    public String registNewUser(Model model, HttpSession session) {
        UserEntity userEntity;
        userEntity = new UserEntity();
        userEntity.setShifrPodr(1);
        userEntity.setShifrWr(1);
        userEntity.setShifrKat(1);
        model.addAttribute("userEntity",userEntity);
        String viewName;
        model.addAttribute("uStepList",uStepService.getAll());
        model.addAttribute("univList",podrService.getListDTOForOwner(1));
        model.addAttribute("dolgList",dolgService.getAll());
        viewName="registnpr";
        return viewName;
    }

    @InitBinder
    public void initialiseBinder(WebDataBinder binder) {
        binder.setDisallowedFields("dataCreate", "dataDelete","dataVerification");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<ItemErrorDTO> saveTableR1P1Row(@ModelAttribute("UserEntity") UserEntity uEntity, BindingResult result, HttpSession session, Model model) {
        //     if (result.hasErrors()) {
        //         return "browseAllGroups";
        //     }
        userValidator.validate(uEntity, result);
        if (result.hasErrors()) {
            return getErrList(result);
        }
        String[] suppressedFields = result.getSuppressedFields();
        if (suppressedFields.length > 0) {
            throw new RuntimeException("Attempting to bind disallowed fields: " +
                    StringUtils.arrayToCommaDelimitedString(suppressedFields));
        }
        logger.debug("save userEntity row for " + uEntity.toString());
        if (uEntity.getShifrUni()==0) {
            uEntity.setShifrFac(0);
            uEntity.setShifrKaf(0);
            uEntity.setShifrPodr(1);
        }
        if (uEntity.getShifrPodr()<1) {
            if (uEntity.getShifrKaf()>0)
                uEntity.setShifrPodr(uEntity.getShifrKaf());
            else
            if (uEntity.getShifrFac()>0)
                uEntity.setShifrPodr(uEntity.getShifrFac());
            else
            if (uEntity.getShifrUni()>0)
                uEntity.setShifrPodr(uEntity.getShifrUni());
        }
        uService.saveRecord(uEntity);
        uService.MakePPSRoleForNewUser(uEntity);
        List<ItemErrorDTO> okList = new ArrayList<ItemErrorDTO>();
        okList.add(new ItemErrorDTO(0, "Ok", "Ok"));
        return okList;
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
