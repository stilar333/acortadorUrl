package com.Stilar.acortadorUrl.repositorys;

import com.Stilar.acortadorUrl.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUser extends MongoRepository<UserModel, String> {
        public abstract Optional<UserModel> findByEmail(String email);
        public abstract Optional<UserModel> findByUsername(String username);
}
