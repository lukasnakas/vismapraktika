package lt.lukasnakas.test.mapper;

import lt.lukasnakas.mapper.TransactionMapper;
import lt.lukasnakas.mapper.TransactionMapperImpl;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.danske.transaction.DanskeTransaction;
import lt.lukasnakas.model.danske.transaction.DanskeTransactionAmount;
import lt.lukasnakas.model.dto.CommonTransactionDTO;
import lt.lukasnakas.model.revolut.transaction.*;
import lt.lukasnakas.util.TestDataGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionMapperTest {

    private TestDataGenerator testDataGenerator = new TestDataGenerator();
    private TransactionMapper transactionMapper = new TransactionMapperImpl();

    @Test
    public void danskeTransactionToCommonTransaction_shouldReturnTrue_whenCommonTransactionsMatch() {
        DanskeTransactionAmount danskeTransactionAmount = new DanskeTransactionAmount();
        danskeTransactionAmount.setCurrency("EUR");
        danskeTransactionAmount.setAmount(100);

        DanskeTransaction danskeTransaction = new DanskeTransaction();
        danskeTransaction.setId("123");
        danskeTransaction.setCreditDebitIndicator("debit");
        danskeTransaction.setAccountId("456");
        danskeTransaction.setTransactionAmount(danskeTransactionAmount);

        CommonTransaction expected = testDataGenerator.buildCommonTransaction(danskeTransaction);
        CommonTransaction actual = transactionMapper.danskeTransactionToCommonTransaction(danskeTransaction);

        assertEquals(expected, actual);
    }

    @Test
    public void revolutTransactionToCommonTransaction_shouldReturnTrue_whenCommonTransactionsMatch() {
        RevolutCounterparty revolutCounterparty = new RevolutCounterparty();
        revolutCounterparty.setAccountType("type");
        revolutCounterparty.setAccountId("987");

        RevolutTransactionLegs[] revolutTransactionLegs = new RevolutTransactionLegs[1];
        revolutTransactionLegs[0] = new RevolutTransactionLegs();
        revolutTransactionLegs[0].setId("123");
        revolutTransactionLegs[0].setAccountId("456");
        revolutTransactionLegs[0].setCounterparty(revolutCounterparty);
        revolutTransactionLegs[0].setAmount(100);
        revolutTransactionLegs[0].setCurrency("EUR");
        revolutTransactionLegs[0].setDescription("desc");
        revolutTransactionLegs[0].setBalance(1000);

        RevolutTransaction revolutTransaction = new RevolutTransaction();
        revolutTransaction.setId("123");
        revolutTransaction.setType("type");
        revolutTransaction.setRequestId("456");
        revolutTransaction.setCreatedAt("2020");
        revolutTransaction.setUpdatedAt("2021");
        revolutTransaction.setCompletedAt("2022");
        revolutTransaction.setReference("ref");
        revolutTransaction.setState("active");
        revolutTransaction.setLegs(revolutTransactionLegs);

        CommonTransaction expected = testDataGenerator.buildCommonTransaction(revolutTransaction);
        CommonTransaction actual = transactionMapper.revolutTransactionToCommonTransaction(revolutTransaction);

        assertEquals(expected, actual);
    }

    @Test
    public void revolutTransactionPaymentToCommonTransaction_shouldReturnTrue_whenCommonTransactionsMatch() {
        RevolutCounterparty revolutCounterparty = new RevolutCounterparty();
        revolutCounterparty.setAccountType("type");
        revolutCounterparty.setAccountId("987");

        RevolutTransactionLegs[] revolutTransactionLegs = new RevolutTransactionLegs[1];
        revolutTransactionLegs[0] = new RevolutTransactionLegs();
        revolutTransactionLegs[0].setId("123");
        revolutTransactionLegs[0].setAccountId("456");
        revolutTransactionLegs[0].setCounterparty(revolutCounterparty);
        revolutTransactionLegs[0].setAmount(100);
        revolutTransactionLegs[0].setCurrency("EUR");
        revolutTransactionLegs[0].setDescription("desc");
        revolutTransactionLegs[0].setBalance(1000);

        RevolutTransaction revolutTransaction = new RevolutTransaction();
        revolutTransaction.setId("123");
        revolutTransaction.setType("type");
        revolutTransaction.setRequestId("456");
        revolutTransaction.setCreatedAt("2020");
        revolutTransaction.setUpdatedAt("2021");
        revolutTransaction.setCompletedAt("2022");
        revolutTransaction.setReference("ref");
        revolutTransaction.setState("active");
        revolutTransaction.setLegs(revolutTransactionLegs);

        RevolutReceiver revolutReceiver = new RevolutReceiver();
        revolutReceiver.setAccountId("321");
        revolutReceiver.setCounterPartyId("654");

        RevolutPayment revolutPayment = new RevolutPayment();
        revolutPayment.setAccountId("123");
        revolutPayment.setAmount(100);
        revolutPayment.setRequestId("456");
        revolutPayment.setReference("ref");
        revolutPayment.setCurrency("EUR");
        revolutPayment.setReceiver(revolutReceiver);

        CommonTransaction expected = testDataGenerator.buildCommonTransaction(revolutTransaction, revolutPayment);
        CommonTransaction actual = transactionMapper.revolutTransactionToCommonTransaction(revolutTransaction, revolutPayment);

        assertEquals(expected, actual);
    }

    @Test
    public void commonTransactionDtoToCommonTransaction_shouldReturnTrue_whenCommonTransactionsMatch() {
        CommonTransactionDTO commonTransactionDTO = new CommonTransactionDTO();
        commonTransactionDTO.setId("123");
        commonTransactionDTO.setSenderAccountId("456");
        commonTransactionDTO.setReceiverAccountId("789");
        commonTransactionDTO.setCurrency("EUR");
        commonTransactionDTO.setAmount(100);

        CommonTransaction expected = new CommonTransaction();
        expected.setId("123");
        expected.setSenderAccountId("456");
        expected.setReceiverAccountId("789");
        expected.setCurrency("EUR");
        expected.setAmount(100);

        CommonTransaction actual = transactionMapper.commonTransactionDtoToCommonTransaction(commonTransactionDTO);

        assertEquals(expected, actual);
    }

    @Test
    public void commonTransactionToCommonTransactionDto_shouldReturnTrue_whenCommonTransactionDtosMatch() {
        CommonTransaction commonTransaction = new CommonTransaction();
        commonTransaction.setId("123");
        commonTransaction.setSenderAccountId("456");
        commonTransaction.setReceiverAccountId("789");
        commonTransaction.setCurrency("EUR");
        commonTransaction.setAmount(100);

        CommonTransactionDTO expected = new CommonTransactionDTO();
        expected.setId("123");
        expected.setSenderAccountId("456");
        expected.setReceiverAccountId("789");
        expected.setCurrency("EUR");
        expected.setAmount(100);

        CommonTransactionDTO actual = transactionMapper.commonTransactionToCommonTransactionDto(commonTransaction);

        assertEquals(expected, actual);
    }

}
