package gr.hua.dit.ds.rent_app.controllers;

import gr.hua.dit.ds.rent_app.entities.*;
import gr.hua.dit.ds.rent_app.service.OwnerService;
import gr.hua.dit.ds.rent_app.service.PropertyService;
import gr.hua.dit.ds.rent_app.service.RenterService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*Controller class for property requests*/
@Controller
@RequestMapping("property")
public class PropertyController {
    private PropertyService propertyService;

    private RenterService renterService;

    private OwnerService ownerService;

    public PropertyController(PropertyService propertyService, RenterService renterService, OwnerService ownerService) {
        this.propertyService = propertyService;
        this.renterService = renterService;
        this.ownerService = ownerService;
    }

    /*Handles the requests to display the properties available and returns the page with the properties' list*/
    @GetMapping("")
    public String showProperties(Model model){
        model.addAttribute("properties", propertyService.getProperties());//models the model to hold properties' list
        return "property/properties";
    }

    /*Handles the requests to display a specific property's details and returns the page with the details*/
    @GetMapping("/{id}")
    public String showProperty(@PathVariable Integer id, Model model){
        model.addAttribute("property", propertyService.getPropertyDetails(id));//models the model to hold the property
        return "";
    }

    /*Handles the requests to add new property and returns the page to add the property*/
    @GetMapping("/new")
    public String addProperty(Model model){
        Property property = new Property();
        Property_profile property_profile = new Property_profile();
        property.setProperty_profile(property_profile);
        model.addAttribute("property", property);//models the model to hold the property
        model.addAttribute("typeOfProperty", TypeOfProperty.values());//models the model to hold the type of property
        model.addAttribute("property_profile", property_profile);//models the model to hold the property details
        return "property/property";
    }

    /*Handles post requests to add new property and returns the page with the updated property list*/
    @PostMapping("/new")
    public String saveProperty(@Valid @ModelAttribute("property") Property property, BindingResult bindingResult,
                               Model model){
        /*checks if the fields for the details of new property is correctly filled from user;
        if not it returns the page for new property*/
        if (bindingResult.hasErrors()) {
            return "property/property";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Owner owner = ownerService.getOwnerByUsername(username);
        property.setOwner(owner);
        propertyService.saveProperty(property);
        model.addAttribute("properties", propertyService.getProperties());//models the model to hold the updated properties' list
        return "property/properties";
    }

    /*Handles requests to delete a property and returns the updated list of properties*/
    @DeleteMapping ("/{id}")
    public String deleteProperty(@PathVariable Integer id, Model model) {
        propertyService.deleteProperty(id);
        model.addAttribute("property", propertyService.getProperties());//models the model to hold the updated list
        return "";
    }

    /*Handles requests to display the form for assigning a property to a renter and returns the form */
    @GetMapping("/{id}/add_renter")
    public String showAddPropertyToRenter(@PathVariable Integer id, Model model){
        Property property = propertyService.getProperty(id);
        List<Renter> renters = renterService.getRenters();
        model.addAttribute("property", property);//models the model to hold the property
        model.addAttribute("renters", renters);//models the model to hold the renters' list
        return "";
    }

    /*Handles post requests to assign a property to a renter*/
    @PostMapping("/{id}/add_renter")
    public String addPropertyToRenter(@PathVariable Integer id,
                                      @RequestParam(value = "renter", required = true) Integer renterId, Model model){
        System.out.println(renterId);
        Renter renter = renterService.getRenter(renterId);
        Property property = propertyService.getProperty(id);
        System.out.println(property);
        propertyService.addPropertyToRenter(id, renter);
        model.addAttribute("properties", propertyService.getProperties());//models the model to hold the updated list of property
        model.addAttribute("successMessage", "Form submitted successfully!");//models the model to hold the success message
        return "";
    }
}
