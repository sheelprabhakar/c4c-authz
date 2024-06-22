package com.c4c.auth.core.repositories;

import com.c4c.auth.core.models.entities.Permission;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends MongoRepository<Permission, ObjectId> {
  Optional<Permission> findByName(String name);
}
