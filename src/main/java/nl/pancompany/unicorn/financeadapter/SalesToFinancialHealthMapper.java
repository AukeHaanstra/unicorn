package nl.pancompany.unicorn.financeadapter;


import nl.pancompany.unicorn.application.finance.dto.SalesDto;
import nl.pancompany.unicorn.application.unicorn.dto.FinancialHealthDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface SalesToFinancialHealthMapper {

    @Mapping(target = "financialHealth", source="salesTotal", qualifiedByName = "calculateFinancialHealth")
    FinancialHealthDto toFinancialHealthDto(SalesDto salesDto);

    @Named("calculateFinancialHealth")
    default FinancialHealthDto.FinancialHealth calculateFinancialHealth(long salesTotal) {
        if (salesTotal >= 100000) {
            return FinancialHealthDto.FinancialHealth.EXCELLENT;
        } else if (salesTotal >= 50000) {
            return FinancialHealthDto.FinancialHealth.GOOD;
        } else if (salesTotal >= 20000) {
            return FinancialHealthDto.FinancialHealth.SUFFICIENT;
        } else if (salesTotal >= 5000) {
            return FinancialHealthDto.FinancialHealth.INSUFFICIENT;
        } else {
            return FinancialHealthDto.FinancialHealth.CRITICAL;
        }
    }
}