package guru_springframework.spring_6_rest_mvc.mappers;

import guru.springframework.spring6restmvcapi.model.BeerOrderDTO;
import guru.springframework.spring6restmvcapi.model.BeerOrderLineDTO;
import guru.springframework.spring6restmvcapi.model.BeerOrderShipmentDTO;
import guru_springframework.spring_6_rest_mvc.entities.BeerOrder;
import guru_springframework.spring_6_rest_mvc.entities.BeerOrderLine;
import guru_springframework.spring_6_rest_mvc.entities.BeerOrderShipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BeerOrderMapper {

    @Mapping(target = "beerOrder", ignore = true)
    //@Mapping(target = "hashCode", ignore = true)
    BeerOrderShipment beerOrderShipmentDtoToBeerOrderShipment(BeerOrderShipmentDTO dto);

    BeerOrderShipmentDTO beerOrderShipmentToBeerOrderShipmentDto(BeerOrderShipment beerOrderShipment);

    //@Mapping(target = "isNew", ignore = true)
    @Mapping(target = "beerOrder", ignore = true)
    BeerOrderLine beerOrderLineDtoToBeerOrderLine(BeerOrderLineDTO dto);

    BeerOrderLineDTO beerOrderLineToBeerOrderLineDto(BeerOrderLine beerOrderLine);

    BeerOrder beerOrderDtoToBeerOrder(BeerOrderDTO dto);

    BeerOrderDTO beerOrderToBeerOrderDto(BeerOrder beerOrder);
}
