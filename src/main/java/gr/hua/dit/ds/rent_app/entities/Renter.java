package gr.hua.dit.ds.rent_app.entities;

import jakarta.persistence.*;

import java.util.List;

/*Entity class for renter which extends user class*/
@Entity
public class Renter extends User{
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name="owner_renter",
            joinColumns = @JoinColumn(name="renter_id"),
            inverseJoinColumns = @JoinColumn(name="owner_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"renter_id", "owner_id"})
    )
    private List<Owner> owners;//owners of renter

    @OneToMany(mappedBy = "renter", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<Property> properties;//properties of renter

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<RentApplication> rentApplications;//rent application of renter

    //Getters and setters
    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<RentApplication> getRentApplications() {
        return rentApplications;
    }

    public void setRentApplications(List<RentApplication> rentApplications) {
        this.rentApplications = rentApplications;
    }

    //Constructors and toString
    public Renter() {
    }

    public Renter( List<Owner> owners,
                  List<Property> properties, List<RentApplication> rentApplications) {
        this.owners = owners;
        this.properties = properties;
        this.rentApplications = rentApplications;
    }

    @Override
    public String toString() {
        return "Renter{" +
                ", owners=" + owners +
                ", properties=" + properties +
                ", rentApplications=" + rentApplications +
                '}';
    }
}
