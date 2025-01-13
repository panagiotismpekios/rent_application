package gr.hua.dit.ds.rent_app.repositories;

import gr.hua.dit.ds.rent_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*Repository interface for the user entity, provides data access methods for user objects in the database*/
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);//finds the user by his username
    Boolean existsByUsername(String username);//does the username exists;
    Boolean existsByEmail(String email);//does the email exists
}
