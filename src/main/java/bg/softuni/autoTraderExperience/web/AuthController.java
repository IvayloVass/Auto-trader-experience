package bg.softuni.autoTraderExperience.web;


import bg.softuni.autoTraderExperience.exceptions.UserAlreadyExistException;
import bg.softuni.autoTraderExperience.models.binding.UserRegisterBindingModel;
import bg.softuni.autoTraderExperience.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userRegisterBindingModel")
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }


    @GetMapping("/login")
    public String login() {
        return "auth-login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(@ModelAttribute("username") String username,
                                RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("username", username)
                .addFlashAttribute("bad_credentials", true);
        return "redirect:/users/login";
    }

    @GetMapping("/register")
    public String register() {
        return "auth-register";
    }

    @ExceptionHandler({UserAlreadyExistException.class})
    @PostMapping("/register")
    public String userRegister(@Valid UserRegisterBindingModel userRegisterBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",
                    bindingResult);
            return "redirect:/users/register";

        }
        userService.register(userRegisterBindingModel);
        model.addAttribute("userRegisterBindingModel", userRegisterBindingModel);
        return "redirect:/";
    }
}
