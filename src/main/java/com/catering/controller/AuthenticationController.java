package com.catering.controller;

import com.catering.model.Credentials;
import com.catering.model.User;
import com.catering.service.*;
import com.catering.validator.CredentialsValidator;
import com.catering.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class AuthenticationController {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private CredentialsValidator credentialsValidator;

    @Autowired
    private BuffetService buffetService;
    @Autowired
    private ChefService chefService;
    @Autowired
    private PiattoService piattoService;
    @Autowired
    private IngredienteService ingredienteService;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterForm (Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("credentials", new Credentials());
        return "registerUser";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm (Model model) {
        return "loginForm";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model) {
        return "index";
    }

    public String caricaHomeAdmin(Model model) {

        model.addAttribute("buffets", this.buffetService.tuttiBuffet());
        model.addAttribute("chefs", this.chefService.chefs());
        model.addAttribute("piatti", this.piattoService.piatti());
        model.addAttribute("ingredienti", this.ingredienteService.ingredienti());
        return "/admin/home";
    }

    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String defaultAfterLogin(Model model) {

        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            model.addAttribute("buffets", this.buffetService.tuttiBuffet());
            model.addAttribute("chefs", this.chefService.chefs());
            model.addAttribute("piatti", this.piattoService.piatti());
            model.addAttribute("ingredienti", this.ingredienteService.ingredienti());
            return "/admin/home";
        }
        return "/index";
    }

    @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") User user,
                               BindingResult userBindingResult,
                               @ModelAttribute("credentials") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               Model model) {

        // validate user and credentials fields
        this.userValidator.validate(user, userBindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);

        // if neither of them had invalid contents, store the User and the Credentials into the DB
        if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
            // set the user and store the credentials;
            // this also stores the User, thanks to Cascade.ALL policy
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            return "registrationSuccessful";
        }
        return "registerUser";
    }
}
