package lt.lukasnakas.repository;

import lt.lukasnakas.model.CommonAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<CommonAccount, String> {
	List<CommonAccount> findAll();
}
