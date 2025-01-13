package gr.hua.dit.ds.rent_app.service;

import gr.hua.dit.ds.rent_app.entities.RentApplication;
import gr.hua.dit.ds.rent_app.entities.Status;
import gr.hua.dit.ds.rent_app.entities.VerificationRequest;
import gr.hua.dit.ds.rent_app.repositories.PropertyRepository;
import gr.hua.dit.ds.rent_app.repositories.RentApplicationRepository;
import gr.hua.dit.ds.rent_app.repositories.RenterRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/*Service class for managing Rent application-related operations*/
@Service
public class RentApplicationService {

    public RentApplicationRepository rentApplicationRepository;

    public RentApplicationService(RentApplicationRepository rentApplicationRepository) {
        this.rentApplicationRepository = rentApplicationRepository;
    }

    //Retrieves a list of rent applications and returns it
    @Transactional
    public List<RentApplication> getRentApplications(){ return rentApplicationRepository.findAll();}

    //Retrieves a specific rent application and returns it
    @Transactional
    public RentApplication getRentApplication(Integer rentApplicationId){
        return rentApplicationRepository.findById(rentApplicationId).get();}

    //Updates an application's status
    @Transactional
    public RentApplication updateApplicationStatus(Integer rentApplicationId, Status status) {
        RentApplication application = rentApplicationRepository.findById(rentApplicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(status);
        return rentApplicationRepository.save(application);
    }

    //Saves a new rent application
    @Transactional
    public void saveNewApplication(RentApplication rentApplication) {
        rentApplicationRepository.save(rentApplication);
    }
}
