package rating.controller;

import rating.dto.RoadRecDTO;
import rating.service.CityService;
import rating.service.RoadPointService;
import rating.service.RoadService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/roadmap")

public class RoadMapController {
    @Autowired
    private RoadService roadService;
    @Autowired
    private CityService cityService;
    @Autowired
    private RoadPointService rpService;
    protected static Logger logger = Logger.getLogger("controller");

    @RequestMapping(method = RequestMethod.GET)
    public String browseRoadsList(Model model, HttpSession session) {
        List<RoadRecDTO> roadList = roadService.getListRoadsGos();
        model.addAttribute("rList", roadList);
        logger.debug("Amnt of rec=" + roadList.size());
        return "yamaproads";
    }


}
