package gr.hua.dit.ds.rent_app.service;

import gr.hua.dit.ds.rent_app.entities.Owner;
import gr.hua.dit.ds.rent_app.entities.Property;
import gr.hua.dit.ds.rent_app.repositories.OwnerRepository;
import gr.hua.dit.ds.rent_app.repositories.PropertyRepository;
import gr.hua.dit.ds.rent_app.repositories.RenterRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/*Service class for managing owner-related operations*/
@Service
public class OwnerService {

    private OwnerRepository ownerRepository;

    private PropertyRepository propertyRepository;

    private RenterRepository renterRepository;

    public OwnerService(OwnerRepository ownerRepository, PropertyRepository propertyRepository,
                        RenterRepository renterRepository){
        this.ownerRepository = ownerRepository;
        this.renterRepository = renterRepository;
        this.propertyRepository = propertyRepository;
    }

    //Retrieves a list of all owners and returns it
    @Transactional
    public List<Owner> getOwners(){ return ownerRepository.findAll();}

    //Retrieves a specific owner by its id and returns it
    @Transactional
    public Owner getOwner(Integer ownerId){ return ownerRepository.findById(ownerId).get();}

    //Retrieves an owner by his username and returns the owner if exists
    @Transactional
    public Owner getOwnerByUsername(String username) {
        return ownerRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Owner not found"));
    }

    //Saves an owner
    @Transactional
    public void saveOwner(Owner owner){ ownerRepository.save(owner);}

    //Deletes an owner
    @Transactional
    public void deleteOwner(Integer ownerId) { ownerRepository.deleteById(ownerId);}

    //Retrieves a list properties of a specific owner by his id and returns the list
    @Transactional
    public List<Property> getOwnerProperties(Integer id){ return ownerRepository.findById(id).get().getProperties();}


}
