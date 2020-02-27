package lt.lukasnakas.repository;

import lt.lukasnakas.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {
	List<Payment> findAll();
}
