package nl.pancompany.unicorn.persistence;

import nl.pancompany.unicorn.application.unicorn.repository.UnicornRepository;
import nl.pancompany.unicorn.application.unicorn.domain.model.Leg;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.UnicornId;
import nl.pancompany.unicorn.application.unicorn.exception.UnicornNotFoundException;
import nl.pancompany.unicorn.testbuilders.LegTestBuilder;
import nl.pancompany.unicorn.testbuilders.UnicornTestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static nl.pancompany.unicorn.application.unicorn.domain.model.Color.*;
import static nl.pancompany.unicorn.application.unicorn.domain.model.Leg.LegPosition.FRONT_RIGHT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

abstract class PersistenceTest {

    /**
     * TODO: Add tests for full logic in add() and update()
     */

    static final String NIL_UUID = "00000000-0000-0000-0000-000000000000";

    @Autowired
    UnicornRepository unicornRepository;

    @Test
    void addsAndFindsBack() {
        Unicorn unicorn = new UnicornTestBuilder().defaults().name("Prancey Dazzlepuff").build();
        var savedUnicorn = unicornRepository.add(unicorn);

        var foundUnicorn = unicornRepository.find(savedUnicorn.getUnicornId());
        assertThat(foundUnicorn).isEqualTo(savedUnicorn);
        assertThat(foundUnicorn.getLegs()).hasSize(4);
        assertThat(foundUnicorn.getName()).isEqualTo("Prancey Dazzlepuff");
    }

    @Test
    void addsAndFindsBackWithDifferentCase() {
        String uuid = UUID.randomUUID().toString();
        Unicorn unicorn = new UnicornTestBuilder().defaults().name("Prancey Dazzlepuff")
                .unicornId(UnicornId.of(uuid.toUpperCase())).build();
        var savedUnicorn = unicornRepository.add(unicorn);

        var foundUnicorn = unicornRepository.find(UnicornId.of(uuid.toLowerCase()));
        assertThat(foundUnicorn).isEqualTo(savedUnicorn);
        assertThat(foundUnicorn.getLegs()).hasSize(4);
        assertThat(foundUnicorn.getName()).isEqualTo("Prancey Dazzlepuff");
    }

    @Test
    void addsAndFindsBackWithDifferentCase2() {
        String uuid = UUID.randomUUID().toString();
        Unicorn unicorn = new UnicornTestBuilder().defaults().name("Prancey Dazzlepuff")
                .unicornId(UnicornId.of(uuid.toLowerCase())).build();
        var savedUnicorn = unicornRepository.add(unicorn);

        var foundUnicorn = unicornRepository.find(UnicornId.of(uuid.toUpperCase()));
        assertThat(foundUnicorn).isEqualTo(savedUnicorn);
        assertThat(foundUnicorn.getLegs()).hasSize(4);
        assertThat(foundUnicorn.getName()).isEqualTo("Prancey Dazzlepuff");
    }

    @Test
    void throwsIfFindIdNotPersisted() {
        assertThatThrownBy(() -> unicornRepository.find(UnicornId.of(NIL_UUID))).isInstanceOf(UnicornNotFoundException.class);
    }

    @Test
    void throwsIfFindIdIsNull() {
        assertThatThrownBy(() -> unicornRepository.find(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void modifiedEntityMergesWithPersistedEntityOnUpdate() {
        Leg leg = new LegTestBuilder().defaults().color(TURQUOISE).build();
        Unicorn unicorn = new UnicornTestBuilder()
                .defaults()
                .withLeg(leg)
                .build();
        Unicorn persistedUnicorn = unicornRepository.add(unicorn);
        persistedUnicorn.setLegColor(leg.getLegPosition(), NAVY);

        var updatedPersistedUnicorn = unicornRepository.update(persistedUnicorn);

        var foundUnicorn = unicornRepository.find(persistedUnicorn.getUnicornId());
        assertThat(foundUnicorn).isEqualTo(updatedPersistedUnicorn);
        assertThat(foundUnicorn.getLeg(leg.getLegPosition()).getColor()).isEqualTo(NAVY);
    }

    @Test
    void throwsIfSaveEntityIsNull() {
        assertThatThrownBy(() -> unicornRepository.add(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void countsPersistedUnicorns() {
        final long initialCount = unicornRepository.count();
        unicornRepository.add(new UnicornTestBuilder().defaults().build());
        assertThat(unicornRepository.count()).isEqualTo(initialCount + 1);
        unicornRepository.add(new UnicornTestBuilder().defaults().build());
        assertThat(unicornRepository.count()).isEqualTo(initialCount + 2);
        unicornRepository.add(new UnicornTestBuilder().defaults().build());
        assertThat(unicornRepository.count()).isEqualTo(initialCount + 3);
    }

    @Test
    void dataButNotObjectItselfIsPersistedOnAdd() {
        Unicorn unicorn = new UnicornTestBuilder().defaults().build();
        unicorn.setLegColor(FRONT_RIGHT, EMERALD);

        var savedUnicorn = unicornRepository.add(unicorn);
        savedUnicorn.setLegColor(FRONT_RIGHT, SAPPHIRE);

        var foundUnicorn = unicornRepository.find(savedUnicorn.getUnicornId());
        assertThat(foundUnicorn.getLeg(FRONT_RIGHT).getColor()).isEqualTo(EMERALD);
        assertThat(savedUnicorn == foundUnicorn).isFalse();
    }

    @Test
    void dataButNotObjectItselfIsPersistedOnUpdate() {
        Unicorn unicorn = new UnicornTestBuilder().defaults().build();
        var savedUnicorn = unicornRepository.add(unicorn);
        savedUnicorn.setLegColor(FRONT_RIGHT, SAPPHIRE);

        var updatedUnicorn = unicornRepository.update(savedUnicorn);
        updatedUnicorn.setLegColor(FRONT_RIGHT, CHERRY);

        var foundUnicorn = unicornRepository.find(updatedUnicorn.getUnicornId());
        assertThat(foundUnicorn.getLeg(FRONT_RIGHT).getColor()).isEqualTo(SAPPHIRE);
        assertThat(updatedUnicorn == foundUnicorn).isFalse();
    }

    @Test
    void returnsDifferentObjectOnAdd() {
        Unicorn unicorn = new UnicornTestBuilder().defaults().build();
        unicorn.setLegColor(FRONT_RIGHT, EMERALD);

        var savedUnicorn = unicornRepository.add(unicorn);
        savedUnicorn.setLegColor(FRONT_RIGHT, SAPPHIRE);

        assertThat(savedUnicorn).isNotEqualTo(unicorn);
        assertThat(savedUnicorn == unicorn).isFalse();
    }

    @Test
    void returnsDifferentObjectOnUpdate() {
        Unicorn unicorn = new UnicornTestBuilder().defaults().build();
        var savedUnicorn = unicornRepository.add(unicorn);
        savedUnicorn.setLegColor(FRONT_RIGHT, EMERALD);

        var updatedUnicorn = unicornRepository.update(savedUnicorn);
        updatedUnicorn.setLegColor(FRONT_RIGHT, SAPPHIRE);

        assertThat(updatedUnicorn).isNotEqualTo(savedUnicorn);
        assertThat(updatedUnicorn == unicorn).isFalse();
    }
}
