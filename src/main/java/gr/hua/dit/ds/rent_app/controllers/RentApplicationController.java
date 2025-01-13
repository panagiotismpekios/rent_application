package gr.hua.dit.ds.rent_app.controllers;

import gr.hua.dit.ds.rent_app.entities.RentApplication;
import gr.hua.dit.ds.rent_app.entities.VerificationRequest;
import gr.hua.dit.ds.rent_app.service.RentApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/*Controller class for rent application requests*/
@Controller
@RequestMapping("/rent-applications")
public class RentApplicationController {

    public RentApplicationService rentApplicationService;

    public RentApplicationController(RentApplicationService rentApplicationService) {
        this.rentApplicationService = rentApplicationService;
    }

    /*Handles the requests to display the list of rent applications and returns the page with them*/
    @GetMapping("")
    public String showRentApplications(Model model){
        //models the model to hold the list of rent application
        model.addAttribute("RentApplications", rentApplicationService.getRentApplications());
        return "";
    }

    /*Handles the requests to display a specific rent application and returns the page with it*/
    @GetMapping("/{id}")
    public String showRentApplication(@PathVariable Integer id, Model model){
        //Models the model to hold the rent application
        model.addAttribute("rentApplication", rentApplicationService.getRentApplication(id));
        return "";
    }

    /*Handles the requests to change the status of a rent application and returns the page*/
    @GetMapping("/{id}/change-status")
    public String showChangeApplicationStatus(@PathVariable Integer id, Model model){
        RentApplication application = rentApplicationService.getRentApplication(id);
        model.addAttribute("application", application);//Models the model to hold the application
        model.addAttribute("status", rentApplicationService.getRentApplication(id).getStatus());//Models the model to hold the status of application
        return "";
    }

    /*Handles the post requests to change the status of an application and returns the updated page with applications*/
    @PostMapping("/{id}/change-status")
    public String showUpdatedApplicationStatus(@PathVariable Integer id, Model model){
        System.out.println(id);
        RentApplication rentApplication = rentApplicationService.getRentApplication(id);
        System.out.println(rentApplication.getStatus());
        rentApplicationService.updateApplicationStatus(id,rentApplication.getStatus());
        //models the model to hold the updated list of applications
        model.addAttribute("rentApplications", rentApplicationService.getRentApplications());
        //models the model to hold the success message
        model.addAttribute("succesMessage", "Status successfully updated");
        return "";
    }

    /*Handles the requests to create a new rent application and returns the page to do*/
    @GetMapping("/new")
    public String createNewApplication(Model model){
        RentApplication rentApplication = new RentApplication();
        model.addAttribute("rentApplication", rentApplication);//models the model to hold the new application
        return "";
    }

    /*Handles the post requests to create a new application and returns the updated list of applications*/
    @PostMapping("/new")
    public String saveNewApplication(@ModelAttribute("rentApplication") RentApplication rentApplication, Model model){
        rentApplicationService.saveNewApplication(rentApplication);
        //models the model to hold the updated list of applications
        model.addAttribute("rentApplications", rentApplicationService.getRentApplications());
        return "";
    }
}
