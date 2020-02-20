package lt.lukasnakas.repository;

import lt.lukasnakas.model.CommonAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<CommonAccount, String> {
}
