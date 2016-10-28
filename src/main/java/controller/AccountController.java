package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
public class AccountController {

    @RequestMapping("/login")
    public String loginPage() {
        return "account/login";
    }

    @RequestMapping("/denied")
    public String deniedPage(Model model, @RequestParam(value = "message", defaultValue = "") String message) {
        model.addAttribute("message", message);
        return "account/denied";
    }
}
