package gr.hua.dit.ds.rent_app.controllers;

import gr.hua.dit.ds.rent_app.entities.Owner;
import gr.hua.dit.ds.rent_app.service.OwnerService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*Controller class for owner requests*/
@Controller
@RequestMapping("owner")
public class OwnerController {

    private OwnerService ownerService;

    public OwnerController(OwnerService ownerService){ this.ownerService = ownerService;}

    /*Handles requests for owners list and returns the owners page which shows the list*/
    @GetMapping("")
    public String showOwners(Model model){
        model.addAttribute("owners", ownerService.getOwners());//models the model to hold the owners' list
        return "";
    }

    /*Handles requests for displaying a specific owner and returns the page with the owner's details*/
    @GetMapping("/{id}")
    public String showOwner(@PathVariable Integer id, Model model){
        model.addAttribute("owner", ownerService.getOwner(id));//models the model to hold the owner
        return "";
    }

    /*Handles requests for adding a new owner, is accessible only from admin and returns the page for adding a new owner*/
    @GetMapping("/new")
    @Secured("ROLE_ADMIN")
    public String addOwner(Model model){
        Owner owner = new Owner();
        model.addAttribute("owner", owner);//models the model to hold the new owner
        return "";
    }

    /*Handles post requests to save new owner and is only accessible from admin and returns the page with the owners' list*/
    @PostMapping("/new")
    @Secured("ROLE_ADMIN")
    public String saveOwner(@ModelAttribute("owner") Owner owner, Model model){
        ownerService.saveOwner(owner);
        model.addAttribute("owners", ownerService.getOwners());//models the model to hold the updated owners' list
        return "";
    }

    /*Handles delete requests to remove an owner by ID, is only accessible from admin and returns the updated page with owners'list*/
    @DeleteMapping ("/{id}")
    @Secured("ROLE_ADMIN")
    public String deleteOwner(@PathVariable Integer id, Model model) {
        ownerService.deleteOwner(id);
        model.addAttribute("owner", ownerService.getOwners());//models the model to hold the owners' list
        return "";
    }

    /*Handles the requests to display the properties of a specific owner*/
    @GetMapping("/{id}/properties")
    public String showOwnerProperties(@PathVariable Integer id, Model model){
        model.addAttribute("properties", ownerService.getOwnerProperties(id));//models the model to hold the owner' properties
        return "";
    }
}
