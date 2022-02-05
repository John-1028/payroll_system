package payroll.implementation;

import payroll.domain.Paycheck;
import payroll.domain.PaymentMethod;

/**
 * @author zhangyunlong
 */
public class DirectMethod implements PaymentMethod {
    private String itsBank;
    private String itsAccount;

    public DirectMethod(String bank, String account) {
        this.itsBank = bank;
        itsAccount = account;
    }

    public String getBank() {
        return itsBank;
    }

    public String getAccount() {
        return itsAccount;
    }

    @Override
    public void pay(Paycheck pc) {
        pc.setField("Disposition", "Direct");
    }
}
