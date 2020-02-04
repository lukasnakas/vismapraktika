package lt.lukasnakas.Service;

import lt.lukasnakas.Model.Bank;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    private List<Bank> bankList;

    public BankService(List<Bank> bankList) {
        this.bankList = bankList;
        this.bankList.add(new Bank(1, "Revolut", "oa_sand_jHcTy71JCD1zdUSI1A5AZaV1Ho5zkC753IW23MPUTFc", "https://sandbox-b2b.revolut.com/api/1.0/accounts"));
        this.bankList.add(new Bank(2, "Swedbank", "", ""));
    }

    public List<Bank> getAllBanks(){
        return bankList;
    }

    public Bank getBankByID(int id){
        return bankList.get(id - 1);
    }

}
