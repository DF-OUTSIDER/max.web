package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
public class AccountController {

    @RequestMapping("/login")
    public String loginPage(Model model,
                            @RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout) {
        if (error != null)
            model.addAttribute("error", "Invalid Username or Password!");
        if (logout != null)
            model.addAttribute("logout", "You have logout successfully!");
        return "account/login";
    }

    @RequestMapping("/denied")
    public String deniedPage(Model model, @RequestParam(value = "message", defaultValue = "") String message) {
        model.addAttribute("message", message);
        return "account/denied";
    }
}
