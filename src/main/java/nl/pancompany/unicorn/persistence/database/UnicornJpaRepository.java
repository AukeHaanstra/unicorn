package nl.pancompany.unicorn.persistence.database;

import nl.pancompany.unicorn.persistence.database.model.UnicornJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnicornJpaRepository extends JpaRepository<UnicornJpaEntity, Long> {

    Optional<UnicornJpaEntity> findByUnicornId(String unicornId);

    boolean existsByUnicornId(String unicornId);
}
