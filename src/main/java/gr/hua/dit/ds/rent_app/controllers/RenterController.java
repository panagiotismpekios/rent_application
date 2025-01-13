package gr.hua.dit.ds.rent_app.controllers;

import gr.hua.dit.ds.rent_app.entities.Owner;
import gr.hua.dit.ds.rent_app.entities.Renter;
import gr.hua.dit.ds.rent_app.service.RenterService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*Controller class for renter requests*/
@Controller
@RequestMapping("renter")
public class RenterController {

    private RenterService renterService;

    public RenterController(RenterService renterService) {this.renterService = renterService;}

    /*Handles requests to display the renters and returns the page with the list of renters*/
    @GetMapping("")
    public String showRenters(Model model){
        model.addAttribute("renters", renterService.getRenters());//models the model to hold the list of renters
        return "";
    }

    /*Handles the requests to display a specific renter and returns the page of the specific renter*/
    @GetMapping("/{id}")
    public String showRenter(@PathVariable Integer id, Model model){
        model.addAttribute("renter", renterService.getRenter(id));//Models the model to hold the specific renter
        return "";
    }

    /*Handles the requests to add a new renter, is accessible only for admin and returns the page to add a renter*/
    @GetMapping("/new")
    @Secured("ROLE_ADMIN")
    public String addRenter(Model model){
        Renter renter = new Renter();
        model.addAttribute("renter", renter);//models the model to hold the new renter
        return "";
    }

    /*Handles the post requests to add a new renter, is accessible only for admin and returns the updated list of renters */
    @PostMapping("/new")
    @Secured("ROLE_ADMIN")
    public String saveRenter(@ModelAttribute("renter") Renter renter, Model model){
        renterService.saveRenter(renter);
        model.addAttribute("renters", renterService.getRenters());//models the model to hold the updated list of renters
        return "";
    }

    /*Handles the requests to delete a renter, is only accessible for admin and returns the updated list of renters*/
    @DeleteMapping ("/{id}")
    @Secured("ADMIN_ROLE")
    public String deleteRenter(@PathVariable Integer id, Model model) {
        renterService.deleteRenter(id);
        model.addAttribute("renter", renterService.getRenters());//models the model to hold the updated list of renters
        return "";
    }

    /*Handles the requests to display a specific renter's properties and is only accessible for admin and returns the page
    * with the list of properties of the renter*/
    @GetMapping("/{id}/properties")
    @Secured("ADMIN_ROLE")
    public String showRenterProperties(@PathVariable Integer id, Model model){
        model.addAttribute("properties", renterService.getRenterProperties(id));//models the model to hold the specific renter's properties' list
        return "";
    }
}
