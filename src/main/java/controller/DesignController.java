package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/design")
public class DesignController {


    @RequestMapping({"/index", "/"})
    public String indexPage(Model model) {

        return "design/index";
    }
}
