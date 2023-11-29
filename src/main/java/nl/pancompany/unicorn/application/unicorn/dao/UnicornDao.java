package nl.pancompany.unicorn.application.unicorn.dao;

import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn;

public interface UnicornDao extends Dao<Unicorn, Unicorn.UnicornId> {

    default void requireNonNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("null");
        }
    }
}
