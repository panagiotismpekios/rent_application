package gr.hua.dit.ds.rent_app.service;

import gr.hua.dit.ds.rent_app.entities.Property;
import gr.hua.dit.ds.rent_app.entities.Renter;
import gr.hua.dit.ds.rent_app.repositories.OwnerRepository;
import gr.hua.dit.ds.rent_app.repositories.RenterRepository;
import gr.hua.dit.ds.rent_app.repositories.PropertyRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/*Service class for managing renter-related operations*/
@Service
public class RenterService {
    private RenterRepository renterRepository;

    private OwnerRepository ownerRepository;

    private PropertyRepository propertyRepository;

    public RenterService( RenterRepository renterRepository, OwnerRepository ownerRepository, PropertyRepository propertyRepository){
        this.renterRepository = renterRepository;
        this.ownerRepository = ownerRepository;
        this.propertyRepository = propertyRepository;
    }

    //Retrieves a list of all renters and returns it
    @Transactional
    public List<Renter> getRenters(){ return renterRepository.findAll();}

    //Retrieves a specific renter and returns it
    @Transactional
    public Renter getRenter(Integer renterId){ return renterRepository.findById(renterId).get();}

    //Saves a new renter
    @Transactional
    public void saveRenter(Renter renter){ renterRepository.save(renter);}

    //Deletes a renter
    @Transactional
    public void deleteRenter(Integer renterId) { renterRepository.deleteById(renterId);}

    //Retrieves a specific renter's properties and returns the list of them
    @Transactional
    public List<Property> getRenterProperties(Integer id){ return renterRepository.findById(id).get().getProperties();}


}
