package nl.pancompany.unicorn.testbuilders;

import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.UnicornId;
import nl.pancompany.unicorn.application.unicorn.dto.LegDto;
import nl.pancompany.unicorn.application.unicorn.dto.UnicornDto;

import java.util.HashSet;
import java.util.Set;

import static nl.pancompany.unicorn.application.unicorn.domain.model.Color.*;
import static nl.pancompany.unicorn.application.unicorn.domain.model.Leg.LegPosition.*;
import static nl.pancompany.unicorn.application.unicorn.domain.model.Leg.LegSize.SMALL;

public class UnicornDtoTestBuilder {

    private UnicornId unicornId;
    private String name;
    private Set<LegDto> legs;

    public UnicornDtoTestBuilder defaults() {
        name = "Rainbow Jinglehorn";
        legs = new HashSet<>();
        legs.add(new LegDto(FRONT_LEFT, CYAN, SMALL));
        legs.add(new LegDto(FRONT_RIGHT, AQUA, SMALL));
        legs.add(new LegDto(BACK_LEFT, LIME, SMALL));
        legs.add(new LegDto(BACK_RIGHT, PINK, SMALL));
        return this;
    }

    public UnicornDtoTestBuilder unicornId(UnicornId unicornId) {
        this.unicornId = unicornId;
        return this;
    }

    public UnicornDtoTestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UnicornDtoTestBuilder legs(Set<LegDto> legs) {
        this.legs = legs;
        return this;
    }

    public UnicornDtoTestBuilder withLeg(LegDto newLeg) {
        LegDto oldLeg = legs.stream()
                .filter(leg -> leg.legPosition().equals(newLeg.legPosition()))
                .findFirst().orElseThrow(IllegalStateException::new);
        legs.remove(oldLeg);
        legs.add(newLeg);
        return this;
    }

    public UnicornDto build() {
        return new UnicornDto(unicornId, name, legs, null);
    }
}
