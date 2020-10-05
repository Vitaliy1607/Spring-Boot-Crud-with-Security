package user_base.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import user_base.Entity.UserDocument;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, Long> {

    Optional<UserDocument> findByEmailAddress(String email);

    boolean existsByEmailAddress(String emailAddress);
    
    boolean existsById(String id);
    
    void deleteById(String id);

    boolean existsByEmailAddressIgnoreCase(String emailAddress);

    Optional<UserDocument> findById(String id);
}
