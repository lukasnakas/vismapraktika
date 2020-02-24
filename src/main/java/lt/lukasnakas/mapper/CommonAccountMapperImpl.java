package lt.lukasnakas.mapper;

import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.dto.CommonAccountDTO;
import lt.lukasnakas.model.revolut.account.RevolutAccount;
import org.springframework.stereotype.Component;

@Component
public class CommonAccountMapperImpl implements CommonAccountMapper {

	@Override
	public CommonAccount revolutAccountToCommonAccount(RevolutAccount revolutAccount) {
		if(revolutAccount == null) {
			return null;
		}

		CommonAccount commonAccount = new CommonAccount();

		commonAccount.setBankName("Revolut");
		commonAccount.setAccountId(revolutAccount.getId());
		commonAccount.setBalance(revolutAccount.getBalance());
		commonAccount.setCurrency(revolutAccount.getCurrency());

		return commonAccount;
	}

	@Override
	public CommonAccount danskeAccountToCommonAccount(DanskeAccount danskeAccount) {
		if(danskeAccount == null) {
			return null;
		}

		CommonAccount commonAccount = new CommonAccount();

		commonAccount.setBankName("Danske");
		commonAccount.setAccountId(danskeAccount.getData().getBalance()[0].getAccountId());
		commonAccount.setBalance(danskeAccount.getData().getBalance()[0].getAmount().getAmount());
		commonAccount.setCurrency(danskeAccount.getData().getBalance()[0].getAmount().getCurrency());

		return commonAccount;
	}

	@Override
	public CommonAccount commonAccountDtoToCommonAccount(CommonAccountDTO commonAccountDTO) {
		return null;
	}

	@Override
	public CommonAccountDTO commonAccountToCommonAccountDto(CommonAccount commonAccount) {
		return null;
	}
}
