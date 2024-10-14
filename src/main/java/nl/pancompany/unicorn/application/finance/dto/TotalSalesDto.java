package nl.pancompany.unicorn.application.finance.dto;

import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.UnicornId;

public record TotalSalesDto(UnicornId unicornId, long salesTotal) {
}