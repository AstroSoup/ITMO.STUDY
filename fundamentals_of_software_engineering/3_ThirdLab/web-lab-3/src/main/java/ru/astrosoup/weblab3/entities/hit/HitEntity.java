package ru.astrosoup.weblab3.entities.hit;

import jakarta.persistence.*;
import lombok.*;
import ru.astrosoup.weblab3.entities.authorisation.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class HitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int r;
    private int x;
    private float y;
    private boolean hit;
    private LocalDate date = LocalDate.now();
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HitEntity hitEntity = (HitEntity) o;
        return getId() != null && Objects.equals(getId(), hitEntity.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
