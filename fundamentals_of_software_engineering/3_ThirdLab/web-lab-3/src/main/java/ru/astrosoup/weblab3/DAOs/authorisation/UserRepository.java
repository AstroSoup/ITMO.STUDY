package ru.astrosoup.weblab3.DAOs.authorisation;

import io.ebean.DB;
import io.ebean.Database;
import io.ebean.Transaction;
import io.ebean.annotation.Transactional;
import io.ebean.annotation.TxIsolation;
import ru.astrosoup.weblab3.entities.authorisation.UserEntity;

import java.util.Optional;

public class UserRepository {

    private Database db;


    public UserRepository() {
        this.db = DB.getDefault();
    }

    public UserEntity save(UserEntity user) {
        db.save(user);
        return user;
    }

    public Optional<UserEntity> findById(Long id) {
        return Optional.ofNullable(db.find(UserEntity.class, id));
    }

    public Optional<UserEntity> findByName(String name) {
        return Optional.ofNullable(db.find(UserEntity.class)
                .where().eq("username", name)
                .findOne());
    }
}
