package lt.lukasnakas.repository;

import lt.lukasnakas.model.CommonTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<CommonTransaction, String> {
	List<CommonTransaction> findAll();
}
