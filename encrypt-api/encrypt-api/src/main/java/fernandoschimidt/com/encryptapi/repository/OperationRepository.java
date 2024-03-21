package fernandoschimidt.com.encryptapi.repository;

import fernandoschimidt.com.encryptapi.domain.operation.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
