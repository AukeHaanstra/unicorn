package nl.pancompany.unicorn.application.unicorn.adapter;

import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.UnicornId;
import nl.pancompany.unicorn.application.unicorn.dto.FinancialHealthDto;

public interface FinancialHealthAdapter {

    FinancialHealthDto getFinancialHealth(UnicornId unicornId);
}
