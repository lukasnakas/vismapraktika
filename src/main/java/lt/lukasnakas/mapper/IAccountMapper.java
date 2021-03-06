package lt.lukasnakas.mapper;

import lt.lukasnakas.model.CommonAccount;
import lt.lukasnakas.model.danske.account.DanskeAccount;
import lt.lukasnakas.model.dto.CommonAccountDTO;
import lt.lukasnakas.model.revolut.account.RevolutAccount;

public interface IAccountMapper {

    CommonAccount revolutAccountToCommonAccount(RevolutAccount revolutAccount);

    CommonAccount danskeAccountToCommonAccount(DanskeAccount danskeAccount);

    CommonAccount commonAccountDtoToCommonAccount(CommonAccountDTO commonAccountDTO);

    CommonAccountDTO commonAccountToCommonAccountDto(CommonAccount commonAccount);

}
