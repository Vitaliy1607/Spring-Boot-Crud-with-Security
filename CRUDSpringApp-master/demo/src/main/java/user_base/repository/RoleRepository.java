package user_base.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import user_base.Entity.RoleDocument;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<RoleDocument,Long> {

    Optional<RoleDocument> findByRoleIgnoreCase(String role);
}
