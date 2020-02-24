package lt.lukasnakas.service;

import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.model.CommonTransaction;
import lt.lukasnakas.model.Payment;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.danske.transaction.DanskeTransaction;
import lt.lukasnakas.model.revolut.account.RevolutAccount;
import lt.lukasnakas.model.revolut.transaction.RevolutPayment;
import lt.lukasnakas.model.revolut.transaction.RevolutTransaction;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommonEntityMapperService {

	public CommonAccount convertToCommonAccount(DanskeAccount danskeAccount){
		return new CommonAccount("Danske",
				danskeAccount.getData().getBalance()[0].getAccountId(),
				danskeAccount.getData().getBalance()[0].getAmount().getAmount(),
				danskeAccount.getData().getBalance()[0].getAmount().getCurrency());
	}

	public CommonAccount convertToCommonAccount(RevolutAccount revolutAccount){
		return new CommonAccount("Revolut",
				revolutAccount.getId(),
				revolutAccount.getBalance(),
				revolutAccount.getCurrency());
	}

	public CommonTransaction convertToCommonTransaction(DanskeTransaction danskeTransaction){
		return new CommonTransaction(danskeTransaction.getId(),
				null,
				danskeTransaction.getAccountId(),
				danskeTransaction.getTransactionAmount().getAmount(),
				danskeTransaction.getTransactionAmount().getCurrency());
	}

	public CommonTransaction convertToCommonTransaction(RevolutTransaction revolutTransaction){
		return new CommonTransaction(revolutTransaction.getId(),
				revolutTransaction.getLegs()[0].getAccountId(),
				revolutTransaction.getLegs()[0].getCounterparty().getAccountId(),
				revolutTransaction.getLegs()[0].getAmount(),
				revolutTransaction.getLegs()[0].getCurrency());
	}

	public CommonTransaction convertToCommonTransaction(RevolutTransaction revolutTransaction, Payment payment){
		RevolutPayment revolutPayment = (RevolutPayment) payment;
		return new CommonTransaction(revolutTransaction.getId(),
				revolutPayment.getAccountId(),
				revolutPayment.getReceiver().getAccountId(),
				revolutPayment.getAmount(),
				revolutPayment.getCurrency());
	}

	public boolean hasCounterparty(RevolutTransaction revolutTransaction){
		return Optional.ofNullable(revolutTransaction.getLegs()[0].getCounterparty()).isPresent();
	}

}
