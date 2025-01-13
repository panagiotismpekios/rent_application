package gr.hua.dit.ds.rent_app.service;

import gr.hua.dit.ds.rent_app.entities.Owner;
import gr.hua.dit.ds.rent_app.entities.Property;
import gr.hua.dit.ds.rent_app.entities.Property_profile;
import gr.hua.dit.ds.rent_app.entities.Renter;
import gr.hua.dit.ds.rent_app.repositories.OwnerRepository;
import gr.hua.dit.ds.rent_app.repositories.PropertyRepository;
import gr.hua.dit.ds.rent_app.repositories.RenterRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/*Service class for managing property-related operations*/
@Service
public class PropertyService {

    private PropertyRepository propertyRepository;

    private OwnerRepository ownerRepository;

    private RenterRepository renterRepository;

    public PropertyService(PropertyRepository propertyRepository, OwnerRepository ownerRepository,
                           RenterRepository renterRepository){
        this.propertyRepository = propertyRepository;
        this.ownerRepository= ownerRepository;
        this.renterRepository= renterRepository;
    }

    //Retrieves a list of all properties and returns the list
    @Transactional
    public List<Property> getProperties(){ return propertyRepository.findAll();}

    //Retrieves a specific property and returns the property
    @Transactional
    public Property getProperty(Integer propertyId){ return propertyRepository.findById(propertyId).get();}

    //Retrieves a specific property and returns the details of it
    @Transactional
    public Property_profile getPropertyDetails(Integer propertyId){
        return propertyRepository.findById(propertyId).get().getProperty_profile();
    }

    //Saves a new property
    @Transactional
    public void saveProperty(Property property){ propertyRepository.save(property);}

    //Deletes a property
    @Transactional
    public void deleteProperty(Integer propertyId) { propertyRepository.deleteById(propertyId);}

    //Assigns a property to a renter and saves it
    @Transactional
    public void addPropertyToRenter(Integer propertyId, Renter renter){
        Property property = propertyRepository.findById(propertyId).get();
        System.out.println(property);
        System.out.println(property.getRenter());
        property.setRenter(renter);
        System.out.println(property.getRenter());
        propertyRepository.save(property);
    }

}
