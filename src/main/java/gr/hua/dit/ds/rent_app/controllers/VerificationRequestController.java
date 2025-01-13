package gr.hua.dit.ds.rent_app.controllers;

import gr.hua.dit.ds.rent_app.entities.Status;
import gr.hua.dit.ds.rent_app.entities.VerificationRequest;
import gr.hua.dit.ds.rent_app.service.VerificationRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;

/*Controller class for verification requests requests*/
@Controller
@RequestMapping("request")
public class VerificationRequestController {

    private VerificationRequestService verificationRequestService;

    public VerificationRequestController(VerificationRequestService verificationRequestService) {
        this.verificationRequestService = verificationRequestService;
    }

    /*Handles the requests to display the verification requests' list and returns the page with them*/
    @GetMapping("")
    public String showVerificationRequests(Model model){
        //models the model to hold the verification requests' list
        model.addAttribute("verificationRequests", verificationRequestService.getVerificationRequests());
        return "request/requests";
    }

    /*Handles the requests to display a specific verification request and returns the page with it*/
    @GetMapping("/{id}")
    public String showVerificationRequest(@PathVariable Integer id, Model model){
        //models the model to hold the specific verification request
        model.addAttribute("verificationRequest", verificationRequestService.getVerificationRequest(id));
        return "";
    }

    /*Handles the requests to change the status of a verification request and returns the page to change it*/
    @GetMapping("/{id}/change-status")
    public String showChangeRequestStatus(@PathVariable Integer id, Model model){
        VerificationRequest request = verificationRequestService.getVerificationRequest(id);
        //models the model to hold the verification request and the status of it
        model.addAttribute("request", request);
        model.addAttribute("status", verificationRequestService.getVerificationRequest(id).getStatus());
        return "";
    }

    /*Handles the post requests to change the status of a verification request and returns the updated list of verification requests*/
    @PostMapping("/{id}/change-status")
    public String showUpdatedRequestStatus(@PathVariable Integer id, Model model){
        System.out.println(id);
        VerificationRequest verificationRequest = verificationRequestService.getVerificationRequest(id);
        System.out.println(verificationRequest.getStatus());
        verificationRequestService.updateRequestStatus(id,verificationRequest.getStatus());
        model.addAttribute("verificationRequests", verificationRequestService.getVerificationRequests());
        model.addAttribute("succesMessage", "Status successfully updated");
        return "";
    }

    /*Handles the request to add a new request and returns the page to create one*/
    @GetMapping("/new")
    public String createNewRequest(Model model){
        VerificationRequest verificationRequest = new VerificationRequest();
        //models the model to hold the new verification request
        model.addAttribute("verificationRequest", verificationRequest);
        return "";
    }

    /*Handles the post requests to add a new verification request and returns the updated list of requests*/
    @PostMapping("/new")
    public String saveNewRequest(@ModelAttribute("verificationRequest") VerificationRequest verificationRequest, Model model){
        verificationRequestService.saveNewRequest(verificationRequest);
        //models the model to hold the updated verification requests' list
        model.addAttribute("verificationRequests", verificationRequestService.getVerificationRequests());
        return "";
    }
}
