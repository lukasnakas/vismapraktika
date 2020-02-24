package lt.lukasnakas.mapper;

import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.danske.transaction.DanskeTransaction;
import lt.lukasnakas.model.dto.CommonTransactionDTO;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.model.revolut.transaction.RevolutTransaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public CommonTransaction danskeTransactionToCommonTransaction(DanskeTransaction danskeTransaction) {
        if (danskeTransaction == null) {
            return null;
        }

        CommonTransaction commonTransaction = new CommonTransaction();

        commonTransaction.setId(danskeTransaction.getId());
        commonTransaction.setSenderAccountId(null);
        commonTransaction.setReceiverAccountId(danskeTransaction.getAccountId());
        commonTransaction.setAmount(danskeTransaction.getTransactionAmount().getAmount());
        commonTransaction.setCurrency(danskeTransaction.getTransactionAmount().getCurrency());

        return commonTransaction;
    }

    @Override
    public CommonTransaction revolutTransactionToCommonTransaction(RevolutTransaction revolutTransaction) {
        if (revolutTransaction == null) {
            return null;
        }

        CommonTransaction commonTransaction = new CommonTransaction();

        commonTransaction.setId(revolutTransaction.getId());
        commonTransaction.setSenderAccountId(revolutTransaction.getLegs()[0].getAccountId());
        commonTransaction.setReceiverAccountId(revolutTransaction.getLegs()[0].getCounterparty().getAccountId());
        commonTransaction.setAmount(revolutTransaction.getLegs()[0].getAmount());
        commonTransaction.setCurrency(revolutTransaction.getLegs()[0].getCurrency());

        return commonTransaction;
    }

    @Override
    public CommonTransaction revolutTransactionToCommonTransaction(RevolutTransaction revolutTransaction, Payment payment) {
        if (revolutTransaction == null) {
            return null;
        }

        RevolutPayment revolutPayment = (RevolutPayment) payment;
        CommonTransaction commonTransaction = new CommonTransaction();

        commonTransaction.setId(revolutTransaction.getId());
        commonTransaction.setSenderAccountId(revolutPayment.getAccountId());
        commonTransaction.setReceiverAccountId(revolutPayment.getReceiver().getAccountId());
        commonTransaction.setAmount(revolutPayment.getAmount());
        commonTransaction.setCurrency(revolutPayment.getCurrency());

        return commonTransaction;
    }

    @Override
    public CommonTransaction commonTransactionDtoToCommonTransaction(CommonTransactionDTO commonTransactionDTO) {
        if (commonTransactionDTO == null) {
            return null;
        }

        CommonTransaction commonTransaction = new CommonTransaction();

        commonTransaction.setId(commonTransactionDTO.getId());
        commonTransaction.setSenderAccountId(commonTransactionDTO.getSenderAccountId());
        commonTransaction.setReceiverAccountId(commonTransactionDTO.getReceiverAccountId());
        commonTransaction.setAmount(commonTransactionDTO.getAmount());
        commonTransaction.setCurrency(commonTransactionDTO.getCurrency());

        return commonTransaction;
    }

    @Override
    public CommonTransactionDTO commonTransactionToCommonTransactionDto(CommonTransaction commonTransaction) {
        if (commonTransaction == null) {
            return null;
        }

        CommonTransactionDTO commonTransactionDTO = new CommonTransactionDTO();

        commonTransactionDTO.setId(commonTransaction.getId());
        commonTransactionDTO.setSenderAccountId(commonTransaction.getSenderAccountId());
        commonTransactionDTO.setReceiverAccountId(commonTransaction.getReceiverAccountId());
        commonTransactionDTO.setAmount(commonTransaction.getAmount());
        commonTransactionDTO.setCurrency(commonTransaction.getCurrency());

        return commonTransactionDTO;
    }
}
