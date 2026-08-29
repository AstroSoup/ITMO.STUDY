package ru.astrosoup.weblab3.DAOs.hit;

import io.ebean.DB;
import io.ebean.Database;
import io.ebean.Transaction;
import jakarta.inject.Inject;
import ru.astrosoup.weblab3.entities.authorisation.UserEntity;
import ru.astrosoup.weblab3.entities.hit.HitEntity;

import java.util.List;
import java.util.Optional;

public class HitRepository {

    private final Database db;

    public HitRepository() {
        this.db = DB.getDefault();
    }

    public HitEntity save(HitEntity hit) {
        db.save(hit);
        return hit;
    }

    public Optional<HitEntity> findById(Long id) {
        return Optional.ofNullable(db.find(HitEntity.class, id));
    }

    public List<HitEntity> findByUser(UserEntity user) {
        return db.find(HitEntity.class).where().eq("user", user).findList();
    }

    public List<HitEntity> findAll() {
        return db.find(HitEntity.class).findList();
    }
}
