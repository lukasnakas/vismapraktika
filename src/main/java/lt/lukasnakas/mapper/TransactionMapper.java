package lt.lukasnakas.mapper;

import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.danske.transaction.DanskeTransaction;
import lt.lukasnakas.model.dto.CommonTransactionDTO;
import lt.lukasnakas.model.revolut.transaction.RevolutTransaction;

public interface TransactionMapper {

	CommonTransaction danskeTransactionToCommonTransaction(DanskeTransaction danskeTransaction);

	CommonTransaction revolutTransactionToCommonTransaction(RevolutTransaction revolutTransaction);

	CommonTransaction revolutTransactionToCommonTransaction(RevolutTransaction revolutTransaction, Payment payment);

	CommonTransaction commonTransactionDtoToCommonTransaction(CommonTransactionDTO commonTransactionDTO);

	CommonTransactionDTO commonTransactionToCommonTransactionDto(CommonTransaction commonTransaction);

}
