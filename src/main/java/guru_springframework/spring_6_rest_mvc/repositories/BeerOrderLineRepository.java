package guru_springframework.spring_6_rest_mvc.repositories;

import guru_springframework.spring_6_rest_mvc.entities.BeerOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerOrderLineRepository extends JpaRepository<BeerOrderLine, UUID> {
}
