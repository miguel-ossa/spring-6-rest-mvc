package guru_springframework.spring_6_rest_mvc.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class BeerOrderUpdateDTO {
    private String customerRef;
    @NotNull
    private UUID customerId;

    private Set<BeerOrderLineUpdateDTO> beerOrderLines;
    private BeerOrderShipmentUpdateDTO beerOrderShipment;

    private BigDecimal paymentAmount;
}
