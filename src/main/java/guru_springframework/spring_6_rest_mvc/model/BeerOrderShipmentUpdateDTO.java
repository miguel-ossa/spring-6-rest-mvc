package guru_springframework.spring_6_rest_mvc.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeerOrderShipmentUpdateDTO {
    @NotBlank
    private String trackingNumber;
}
