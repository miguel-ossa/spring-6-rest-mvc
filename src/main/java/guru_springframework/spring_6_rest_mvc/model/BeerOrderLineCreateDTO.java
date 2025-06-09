package guru_springframework.spring_6_rest_mvc.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class BeerOrderLineCreateDTO {

    @NotNull
    private UUID beerId;

    @Min(value = 1, message = "Quantity On Hand must be greater than 0")
    private Integer orderQuantity;
    private Integer quantityAllocated;
}
