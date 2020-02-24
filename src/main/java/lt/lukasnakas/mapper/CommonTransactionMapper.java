package lt.lukasnakas.mapper;

import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.dto.CommonTransactionDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CommonTransactionMapper {

	CommonTransaction commonTransactionDtoToCommonTransaction(CommonTransactionDTO commonTransactionDTO);

	CommonTransactionDTO commonTransactionToCommonTransactionDto(CommonTransaction commonTransaction);

}
