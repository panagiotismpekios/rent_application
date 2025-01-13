package gr.hua.dit.ds.rent_app.service;

import gr.hua.dit.ds.rent_app.entities.*;
import gr.hua.dit.ds.rent_app.repositories.OwnerRepository;
import gr.hua.dit.ds.rent_app.repositories.RoleRepository;
import gr.hua.dit.ds.rent_app.repositories.UserRepository;
import gr.hua.dit.ds.rent_app.repositories.VerificationRequestRepository;
import jakarta.persistence.Enumerated;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/*Service class for managing verification request-related operations*/
@Service
public class VerificationRequestService {

    private VerificationRequestRepository verificationRequestRepository;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private OwnerRepository ownerRepository;

    public VerificationRequestService(VerificationRequestRepository verificationRequestRepository,
                                      UserRepository userRepository, RoleRepository roleRepository,
                                      OwnerRepository ownerRepository) {
        this.verificationRequestRepository = verificationRequestRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.ownerRepository = ownerRepository;
    }

    //Retrieves a list of all verification requests and returns it
    @Transactional
    public List<VerificationRequest> getVerificationRequests(){ return verificationRequestRepository.findAll();}

    //Retrieves a specific verification request and returns it
    @Transactional
    public VerificationRequest getVerificationRequest(Integer verificationRequestId){
        return verificationRequestRepository.findById(verificationRequestId).get();}

    //Updates the status of a specific verification request
    @Transactional
    public VerificationRequest updateRequestStatus(Integer verificationRequestId, Status status) {
        VerificationRequest request = verificationRequestRepository.findById(verificationRequestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(status);
        return verificationRequestRepository.save(request);
    }

    //Submits a new verification request
    @Transactional
    public void submitVerificationRequest(Integer userId, TypeOfRequest typeOfRequest) {
        //checks if user with this id exists; if not throws error
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        VerificationRequest request = new VerificationRequest();
        request.setUser(user);//sets the user
        request.setStatus(Status.Pending);//sets the status to pending
        request.setTypeOfRequest(typeOfRequest);//sets the type of request

        verificationRequestRepository.save(request);//saves it
    }

    //Approves a verification request
    @Transactional
    public void approveVerificationRequest(Integer requestId) {
        //checks if verification request with this id exist; if not throws error
        VerificationRequest request = verificationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Error: Verification request not found."));
        //checks if request's status is pending; if not throws error
        if (request.getStatus() != Status.Pending) {
            throw new RuntimeException("Error: Only pending requests can be approved.");
        }

        User user = request.getUser();
        Role role_owner = roleRepository.findByName("ROLE_OWNER")
                .orElseThrow(() -> new RuntimeException("Error: Role ROLE_OWNER not found."));
        user.getRoles().add(role_owner);//adds role owner to user
        userRepository.save(user);//saves the user
        Owner owner = new Owner();
        ownerRepository.save(owner);//saves the owner
        request.setStatus(Status.Approved);//sets the status to approved
        verificationRequestRepository.save(request);//saves the request
    }

    //Rejects verification request
    @Transactional
    public void rejectVerificationRequest(Integer requestId) {
        //checks if request with this id exists; if not throws error
        VerificationRequest request = verificationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Error: Verification request not found."));
        //checks if request's status is pending; if not throws error
        if (request.getStatus() != Status.Pending) {
            throw new RuntimeException("Error: Only pending requests can be rejected.");
        }

        request.setStatus(Status.Rejected);//sets status to rejected
        verificationRequestRepository.save(request);//saves the request
    }

    //Saves new request
    @Transactional
    public void saveNewRequest(VerificationRequest verificationRequest) {
        verificationRequestRepository.save(verificationRequest);
    }
}
