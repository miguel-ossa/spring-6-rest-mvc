package guru_springframework.spring_6_rest_mvc.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BeerOrderShipmentUpdateDTO {
    @NotBlank
    private String trackingNumber;
}
