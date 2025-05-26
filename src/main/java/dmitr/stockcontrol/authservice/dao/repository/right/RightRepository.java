package dmitr.stockcontrol.authservice.dao.repository.right;

import dmitr.stockcontrol.authservice.dao.entity.right.Right;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RightRepository extends JpaRepository<Right, UUID> {
}
