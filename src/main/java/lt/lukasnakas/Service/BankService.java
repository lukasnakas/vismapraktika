package lt.lukasnakas.Service;

import lt.lukasnakas.Model.Bank;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    private List<Bank> bankList;

    public BankService(List<Bank> bankList) {
        this.bankList = bankList;
        this.bankList.add(new Bank(1, "Revolut", "oa_sand_mKgq_XkDfCGXrV3ZxjJAKIhqouIOJiWgu4zIyOIORI0", "https://sandbox-b2b.revolut.com/api/1.0/accounts"));
        this.bankList.add(new Bank(2, "Luminor", "eyJ0eXAiOiJKV1QiLCJ6aXAiOiJOT05FIiwia2lkIjoiNjZHNVV2TDU4WERxWFQ2UXZ1MWFBUVB2UU1BPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJlZXVzZXJBIiwiY3RzIjoiT0FVVEgyX0dSQU5UX1NFVCIsImF1dGhfbGV2ZWwiOjAsImF1ZGl0VHJhY2tpbmdJZCI6ImFmNDhmOGM2LTk1MTEtNDAwMC05MjFmLWFhNGEyY2VlMjQzOS00MTM2OSIsImlzcyI6Imh0dHBzOi8vYXBpLmRldmVsb3Blci5sdW1pbm9yb3BlbmJhbmtpbmcuY29tL3YxL29wZW5hbS9vYXV0aDIvcmVhbG1zL3Jvb3QvcmVhbG1zL3BzZDIiLCJ0b2tlbk5hbWUiOiJhY2Nlc3NfdG9rZW4iLCJ0b2tlbl90eXBlIjoiQmVhcmVyIiwiYXV0aEdyYW50SWQiOiJjeUd6dWZ3WVBrSWY2QzNxTlYzSEJLSHliWlUuU21Yc1hxZ0QtYXZpem01Vkx5RzFVbkRSVzFJIiwiYXVkIjoiZmFiZjM5NjItYWU5NS00MmY3LWIzNWQtNjI0NzlmNTA0MzI5IiwibmJmIjoxNTgwODg4MTE0LCJncmFudF90eXBlIjoiYXV0aG9yaXphdGlvbl9jb2RlIiwic2NvcGUiOlsicGlzcCIsInRwcCIsIm9wZW5pZCIsInBpaXNwIiwiYXNwc3AiLCJwcm9maWxlIiwiYWlzcCJdLCJhdXRoX3RpbWUiOjE1ODA4ODgxMTQsInJlYWxtIjoiL3BzZDIiLCJleHAiOjE1ODA4OTE3MTQsImlhdCI6MTU4MDg4ODExNCwiZXhwaXJlc19pbiI6MzYwMCwianRpIjoiY3lHenVmd1lQa0lmNkMzcU5WM0hCS0h5YlpVLmx1TUlzdXR4Q2d2Q0haY3N5WEZ4ZGtydXdENCIsImJhbmtfY291bnRyeSI6IkVFIiwiY2NjX3VzZXJuYW1lIjoiZWV1c2VyYSIsImNjY19jdXN0b21lcl9pZCI6ImVldXNlcmEtY2NjLWN1c3RvbWVyLWlkLWRlZmF1bHQiLCJhdXRoX21ldGhvZCI6InNtYXJ0aWQiLCJhdXRoX21ldGhvZF9yZWZlcmVuY2UiOiJQTk9FRS0xMDEwMTAxMDAwNS1aMUIyLVEifQ.6xY-pgic-tYbBYXwoKouAkwS9I-axma-iG3wbxP4VXM9KqbhioSfXdCHFKG8fV4AArA2jroskTs8x53G9HzNi2zVJQIWk7yH-B5ZnY9HBf-ZF4B8KOGgafMxm3WBGYifk4A2CnRcI7qpeBWuRcl6NZ1sMfSYtk_rzD8ArQlXEgN6J3j1GTE_4R0voEv_7erSCMqbtCh-pVdSy9dxjzhDLArtbFHpkw2u-Cn21GIZf04o4Ynvszwg-wEeD7d4KqooSgw2__FoExwMEtoqG7rC3AHYRKgyGZggSEfxgxKmquGJwlcFfhJZRseHH5IsxlGyHznlDRhg4bpzKocge_bPjw", "https://api.developer.luminoropenbanking.com/sandbox/v1/account-list"));
    }

    public List<Bank> getAllBanks(){
        return bankList;
    }

    public Bank getBankByID(int id){
        return bankList.get(id - 1);
    }

}
