package lt.lukasnakas.Service;

import lt.lukasnakas.Model.Bank;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    private List<Bank> bankList;

    public BankService(List<Bank> bankList) {
        this.bankList = bankList;
        this.bankList.add(new Bank(1, "Revolut", "oa_sand_iuWx8Porx_7NW3EjRyROJ6sRs2IoUIak-YvNiSy-QZE", "https://sandbox-b2b.revolut.com/api/1.0/accounts"));
        this.bankList.add(new Bank(2, "Luminor", "eyJ0eXAiOiJKV1QiLCJ6aXAiOiJOT05FIiwia2lkIjoiNjZHNVV2TDU4WERxWFQ2UXZ1MWFBUVB2UU1BPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJlZXVzZXJBIiwiY3RzIjoiT0FVVEgyX0dSQU5UX1NFVCIsImF1dGhfbGV2ZWwiOjAsImF1ZGl0VHJhY2tpbmdJZCI6ImFmNDhmOGM2LTk1MTEtNDAwMC05MjFmLWFhNGEyY2VlMjQzOS0zODk2NiIsImlzcyI6Imh0dHBzOi8vYXBpLmRldmVsb3Blci5sdW1pbm9yb3BlbmJhbmtpbmcuY29tL3YxL29wZW5hbS9vYXV0aDIvcmVhbG1zL3Jvb3QvcmVhbG1zL3BzZDIiLCJ0b2tlbk5hbWUiOiJhY2Nlc3NfdG9rZW4iLCJ0b2tlbl90eXBlIjoiQmVhcmVyIiwiYXV0aEdyYW50SWQiOiIwVjJySlFVR1dTZ3k4SERaaVJrRFZ6UmtvTzQuNTRlSXNIcjlvbk5JdXlNNnZ4ZzFYUGw4MUdBIiwiYXVkIjoiZmFiZjM5NjItYWU5NS00MmY3LWIzNWQtNjI0NzlmNTA0MzI5IiwibmJmIjoxNTgwODUzNjQyLCJncmFudF90eXBlIjoiYXV0aG9yaXphdGlvbl9jb2RlIiwic2NvcGUiOlsicGlzcCIsInRwcCIsIm9wZW5pZCIsInBpaXNwIiwiYXNwc3AiLCJwcm9maWxlIiwiYWlzcCJdLCJhdXRoX3RpbWUiOjE1ODA4NTM2NDIsInJlYWxtIjoiL3BzZDIiLCJleHAiOjE1ODA4NTcyNDIsImlhdCI6MTU4MDg1MzY0MiwiZXhwaXJlc19pbiI6MzYwMCwianRpIjoiMFYyckpRVUdXU2d5OEhEWmlSa0RWelJrb080LnduNDlhN1o2M1FqX0R3N19MOGZ6RXVQZG93ayIsImJhbmtfY291bnRyeSI6IkVFIiwiY2NjX3VzZXJuYW1lIjoiZWV1c2VyYSIsImNjY19jdXN0b21lcl9pZCI6ImVldXNlcmEtY2NjLWN1c3RvbWVyLWlkLWRlZmF1bHQiLCJhdXRoX21ldGhvZCI6InNtYXJ0aWQiLCJhdXRoX21ldGhvZF9yZWZlcmVuY2UiOiJQTk9FRS0xMDEwMTAxMDAwNS1aMUIyLVEifQ.wJ4KIcQ4HD8cpLPUf1BBaMIlqkxlyMVRM2eKBBK2tRrhgLgWmHw0o1yq05m5I8br_72fz59lNaB8CuoB6Lzi591gf6ESBihJy7zZOoBx8tM0uTLsM-oeE5lkh_lJrTmq2rhhg_S4AANXhPlTNqeMM12twnxIne5kgrMuqmGIgtrut78G0vgmzlVpcAd9E4x-H-IYo10zkCQQ4QbJYH3CETbkT5aQ9ErwDNGHiTgXroghuteFnqiw1Zal0VpuMCZFOP8SPdbTDP_UKuAoo3CVfcOLfX5O1U4F0QvBQr-lSX6hmcIf6Mu7Tucz7uJa9Jq3_5hn4ThVsdoWdHEe37Jihw", "https://api.developer.luminoropenbanking.com/sandbox/v1/account-list"));
    }

    public List<Bank> getAllBanks(){
        return bankList;
    }

    public Bank getBankByID(int id){
        return bankList.get(id - 1);
    }

}
