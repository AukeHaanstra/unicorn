package nl.pancompany.unicorn.application.finance.service;

import lombok.Getter;
import nl.pancompany.unicorn.application.finance.domain.model.Customer.CustomerId;
import nl.pancompany.unicorn.application.finance.domain.model.Sale;
import nl.pancompany.unicorn.application.finance.domain.model.Sale.SaleId;
import nl.pancompany.unicorn.application.finance.dto.TotalSalesDto;
import nl.pancompany.unicorn.application.unicorn.domain.model.Unicorn.UnicornId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SalesService {

    @Getter
    private final List<Sale> sales = new ArrayList<>();

    { // create stub data
        UnicornId stubUnicornId = UnicornId.of("ffffffff-ffff-ffff-ffff-ffffffffffff");
        for (int i = 0; i < 6; i++) {
            SaleId saleId = SaleId.generate();
            CustomerId customerId = CustomerId.generate();
            sales.add(new Sale(saleId, stubUnicornId, customerId, LocalDateTime.now(), 1000L * (i + 1)));
        }
    }

    public TotalSalesDto calculateTotalSales(UnicornId unicornId) {
        long total = sales.stream()
                .filter(sale -> sale.unicornId().equals(unicornId))
                .mapToLong(Sale::price)
                .sum();
        return new TotalSalesDto(unicornId, total);
    }
}