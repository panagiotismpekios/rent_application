package gr.hua.dit.ds.rent_app.controllers;

import gr.hua.dit.ds.rent_app.entities.TypeOfRequest;
import gr.hua.dit.ds.rent_app.entities.User;
import gr.hua.dit.ds.rent_app.entities.VerificationRequest;
import gr.hua.dit.ds.rent_app.repositories.RoleRepository;
import gr.hua.dit.ds.rent_app.service.UserService;
import gr.hua.dit.ds.rent_app.service.VerificationRequestService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/*Controller class for user requests*/
@Controller
public class UserController {

    private UserService userService;
    private RoleRepository roleRepository;

    private VerificationRequestService verificationRequestService;

    private TypeOfRequest typeOfRequest;

    public UserController(UserService userService, RoleRepository roleRepository,
                          VerificationRequestService verificationRequestService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.verificationRequestService = verificationRequestService;
    }

    /*Handles the requests to register on the app and returns the page with the form to register*/
    @GetMapping("/register")
    public String register(Model model){
        User user = new User();
        model.addAttribute("user", user);//models the model to hold the user
        return "auth/register";
    }

    /*Handles the post requests to save a user, saves the user and returns the index page*/
    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute User user,  BindingResult bindingResult,
                           @RequestParam(value = "requestOwnerRole", required = false) Boolean requestOwnerRole,
                           Model model){
        //checks if the fields of the user registration are filled correctly; if not it returns the register page with the constraints
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        System.out.println("Roles: "+user.getRoles());
        Integer id = userService.saveUser(user);
        //checks if the user requests to register requests an owner role too; if so it creates a request for admin
        if (Boolean.TRUE.equals(requestOwnerRole)) {
            verificationRequestService.submitVerificationRequest(id, TypeOfRequest.OwnerApproval);
        }
        String message = "User '"+id+"' saved successfully '!";
        model.addAttribute("msg", message);//
        return "index";
    }

    /*Handles the requests to display users' list, is accessible only for admin and returns the page with the users*/
    @GetMapping("/users")
    @Secured("ROLE_ADMIN")
    public String showUsers(Model model){
        model.addAttribute("users", userService.getUsers());//models the model to hold the list of users
        model.addAttribute("roles", roleRepository.findAll());//models the model to hold the roles of each user
        return "";
    }

    /*Handles the requests to display a specific user and returns the page with the specific user*/
    @GetMapping("/users/{user_id}")
    public String showUser(@PathVariable Integer userid, Model model){
        model.addAttribute("user", userService.getUser(userid));//models the model to hold the user
        return "";
    }

    /*Handles the requests to add a new user and returns the page to add the user*/
    @GetMapping("users/add_user")
    public String addUser(Model model){
        User user = new User();
        model.addAttribute("user", user);//models the model to hold the user
        return "";
    }

    /*Handles the post requests to add a new user and returns the updated list of users*/
    @PostMapping("users/add_users")
    public String saveNewUser(@ModelAttribute("user") User user, Model model){
        userService.saveNewUser(user);
        model.addAttribute("users", userService.getUsers());//models the model to hold the updated list of users
        return "";
    }
}

