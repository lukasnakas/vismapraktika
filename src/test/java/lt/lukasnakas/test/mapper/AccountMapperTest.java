package lt.lukasnakas.test.mapper;

import lt.lukasnakas.mapper.IAccountMapper;
import lt.lukasnakas.mapper.AccountMapper;
import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.model.danske.account.Balance;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.danske.account.Data;
import lt.lukasnakas.model.danske.transaction.DanskeTransactionAmount;
import lt.lukasnakas.model.dto.CommonAccountDTO;
import lt.lukasnakas.model.revolut.account.RevolutAccount;
import lt.lukasnakas.util.TestDataGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountMapperTest {

    private TestDataGenerator testDataGenerator = new TestDataGenerator();
    private IAccountMapper accountMapper = new AccountMapper();

    @Test
    public void revolutAccountToCommonAccount_shouldReturnTrue_whenCommonAccountsMatch() {
        RevolutAccount revolutAccount = new RevolutAccount();
        revolutAccount.setId("123");
        revolutAccount.setName("name");
        revolutAccount.setBalance(10);
        revolutAccount.setCurrency("GBP");
        revolutAccount.setState("state");
        revolutAccount.setPublicAccount(true);
        revolutAccount.setCreatedAt("2020");
        revolutAccount.setUpdatedAt("2020");

        CommonAccount expected = testDataGenerator.buildCommonAccount(revolutAccount);
        CommonAccount actual = accountMapper.revolutAccountToCommonAccount(revolutAccount);

        assertEquals(expected, actual);
    }

    @Test
    public void danskeAccountToCommonAccount_shouldReturnTrue_whenCommonAccountsMatch() {
        DanskeTransactionAmount danskeTransactionAmount = new DanskeTransactionAmount();
        danskeTransactionAmount.setAmount(55);
        danskeTransactionAmount.setCurrency("EUR");

        Balance[] balances = new Balance[1];
        balances[0] = new Balance();
        balances[0].setAccountId("123");
        balances[0].setCreditDebitIndicator("debit");
        balances[0].setType("type");
        balances[0].setDatetime("2020");
        balances[0].setAmount(danskeTransactionAmount);

        Data data = new Data();
        data.setBalance(balances);

        DanskeAccount danskeAccount = new DanskeAccount();
        danskeAccount.setId("456");
        danskeAccount.setData(data);

        CommonAccount expected = testDataGenerator.buildCommonAccount(danskeAccount);
        CommonAccount actual = accountMapper.danskeAccountToCommonAccount(danskeAccount);

        assertEquals(expected, actual);
    }

    @Test
    public void commonAccountDtoToCommonAccount_shouldReturnTrue_whenCommonAccountsMatch() {
        CommonAccountDTO commonAccountDTO = new CommonAccountDTO();
        commonAccountDTO.setAccountId("123");
        commonAccountDTO.setBankName("bank");
        commonAccountDTO.setBalance(100);
        commonAccountDTO.setCurrency("EUR");

        CommonAccount expected = new CommonAccount();
        expected.setAccountId("123");
        expected.setBankName("bank");
        expected.setBalance(100);
        expected.setCurrency("EUR");

        CommonAccount actual = accountMapper.commonAccountDtoToCommonAccount(commonAccountDTO);

        assertEquals(expected, actual);
    }

    @Test
    public void commonAccountToCommonAccountDto_shouldReturnTrue_whenCommonAccountDtosMatch() {
        CommonAccount commonAccount = new CommonAccount();
        commonAccount.setAccountId("123");
        commonAccount.setBankName("bank");
        commonAccount.setBalance(100);
        commonAccount.setCurrency("EUR");

        CommonAccountDTO expected = new CommonAccountDTO();
        expected.setAccountId("123");
        expected.setBankName("bank");
        expected.setBalance(100);
        expected.setCurrency("EUR");

        CommonAccountDTO actual = accountMapper.commonAccountToCommonAccountDto(commonAccount);

        assertEquals(expected, actual);
    }

}
