package gr.hua.dit.ds.rent_app.repositories;

import gr.hua.dit.ds.rent_app.entities.RentApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*Repository interface for the rent application entity, provides data access methods for rent application objects in the database*/
@Repository
public interface RentApplicationRepository extends JpaRepository<RentApplication, Integer> {

}
