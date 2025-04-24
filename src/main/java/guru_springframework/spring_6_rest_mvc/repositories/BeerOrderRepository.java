package guru_springframework.spring_6_rest_mvc.repositories;

import guru_springframework.spring_6_rest_mvc.entities.BeerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}
