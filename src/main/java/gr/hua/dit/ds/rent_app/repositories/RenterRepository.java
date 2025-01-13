package gr.hua.dit.ds.rent_app.repositories;

import gr.hua.dit.ds.rent_app.entities.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*Repository interface for the renter entity, provides data access methods for renter objects in the database*/
@Repository
public interface RenterRepository extends JpaRepository<Renter, Integer> {
}
