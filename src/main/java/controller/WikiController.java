package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.FileTemplateService;

@Controller
@RequestMapping("/wiki")
public class WikiController {
    @Autowired
    private FileTemplateService fileTemplateService;

    //BIG DATA TEST
    @RequestMapping("/home")
    public String index(Model model,
                        @RequestParam(name = "pageIndex", defaultValue = "0") Integer pageIndex,
                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        model.addAttribute("fileTemplates", fileTemplateService.findFileTemplate(pageIndex, pageSize));

        return "wiki/index";
    }

}
