package rating.controller;

import javax.servlet.http.HttpSession;

import rating.service.*;
import rating.util.UserInfo;
import net.sf.jasperreports.engine.JRDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MainController {
    @Autowired
    private UsersRolesService urService;
    @Autowired
    private RoadDetService rdService;
    @Autowired
    private WidRoadService wrService;

	protected static Logger logger = Logger.getLogger("controller");

    @RequestMapping(value="/register",method = RequestMethod.GET)
    public String registNewUser() {
        String viewName;
        viewName="registnpr";
        return viewName;
    }
    @RequestMapping(value="/mainpage",method = RequestMethod.GET)
    public String mainUserPage(Model model,HttpSession session) {
        UserInfo userInfo=new UserInfo();
        String viewName;
        int shifrPodr;
        shifrPodr=(int)userInfo.getUserDTO().getShifrPodr();
        model.addAttribute("shifrPodr",shifrPodr);
        boolean isAdmin     = userInfo.isAdmin();
        boolean isDataAdmin = userInfo.isDataAdmin(); //Администратор данных
        boolean isNPR       = userInfo.isNPR();
        boolean isSUP       = userInfo.isSUP();
        boolean isDOD       = userInfo.isSUP();
        boolean isDNID      = userInfo.isDNID();
        if (isAdmin) viewName="adminpage";
        else
        if (isNPR) viewName="adminpage";
        else
        if (isDataAdmin) viewName="adminpage";
        else
        if (isSUP || isDOD || isDNID) viewName="adminpage";
        else
        viewName="t1";

        return viewName;
    }

    @RequestMapping(value = "/print",method = RequestMethod.GET)
    public ModelAndView reportGroupList(@RequestParam(required = true) int shifridwr,ModelAndView modelAndView, ModelMap model,HttpSession session) {
        logger.debug("Received request to download multi report shifridwr="+shifridwr+" "+wrService.getById(shifridwr).getName());

        // Retrieve our data from a custom data provider
        // Our data comes from a DAO layer

        // Assign the datasource to an instance of JRDataSource
        // JRDataSource is the datasource that Jasper understands
        // This is basically a wrapper to Java's collection classes
        String viewName=new String();
        JRDataSource datasource  = rdService.getDataSource(shifridwr);
        //  JRProperties.setProperty("net.sf.jasperreports.default.pdf.font.name", "Arial");
        //  JRProperties.setProperty("net.sf.jasperreports.default.pdf.encoding", "UTF-8");
        //  JRProperties.setProperty("net.sf.jasperreports.default.pdf.embedded", "false");

        // In order to use Spring's built-in Jasper support,
        // We are required to pass our datasource as a map parameter

        // Add our datasource parameter

        model.addAttribute("datasource", datasource);
        // Add the report format
        model.addAttribute("format", "pdf");
//        model.addAttribute("format", "rtf");
        // Add university name
        String s;
        switch (shifridwr) {
            case 1:
                s="Титульний список международных автомобильных дорог общего пользования государственного значения";
                break;
            case 2:
                s="Титульний список национальных автомобильных дорог общего пользования государственного значения";
                break;
            case 3:
                s="Титульний список региональних автомобильных дорог общего пользования государственного значения";
                break;
            case 4:
                s="Титульний список територриальных автомобильных дорог общего пользования государственного значения";
                break;
            case 5:
                s="Титульний список областных автомобильных дорог общего пользования государственного значения";
                break;
            case 6:
                s="Титульний список районных автомобильных дорог общего пользования государственного значения";
                break;
            default:
                s  = "Неверно указан тип дороги";
        }

    //            s = "Титульный список автодорог "+wrService.getById(shifridwr).getName();
        //  s="1.Cведения об основных структурных подразделениях *";
        model.addAttribute("header",s);

        viewName="roadReport";
        modelAndView = new ModelAndView(viewName, model);

        // Return the View and the Model combined
        return modelAndView;
    }





    // Total control - setup a model and return the view name yourself. Or consider
    // subclassing ExceptionHandlerExceptionResolver (see below).
//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleError(HttpServletRequest req, Exception exception) {
//        logger.error("Request: " + req.getRequestURL() + " raised " + exception);
//
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception", exception);
//        mav.addObject("url", req.getRequestURL());
//        mav.setViewName("errors/customerror");
//        return mav;
//    }

}

    

