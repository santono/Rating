package rating.controller;

import rating.service.WidExplorerService;
import rating.service.validators.WidExplorerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.log4j.Logger;
@Controller
@RequestMapping("/widexplorer")
public class WidExplorerController {
    @Autowired
    private WidExplorerService widExporereService;
    protected static Logger logger = Logger.getLogger("controller");

    @Autowired
    private WidExplorerValidator widExlorerValidator;

}
