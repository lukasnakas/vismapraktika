package lt.lukasnakas.Service;

import lt.lukasnakas.Model.Bank;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {
    private List<Bank> bankList;

    public BankService(List<Bank> bankList) {
        this.bankList = bankList;
        this.bankList.add(new Bank(1, "Revolut", "oa_sand_rJh8MDaseBsGEHl8QRZM3Kp7slW-qI_o8wD68I_5TYQ", "https://sandbox-b2b.revolut.com/api/1.0/accounts"));
        this.bankList.add(new Bank(2, "Swedbank", "", ""));
    }

    public List<Bank> getAllBanks(){
        return bankList;
    }

    public Bank getBankByID(int id){
        return bankList.get(id - 1);
    }

}
