package payroll.implementation;

import payroll.domain.Paycheck;
import payroll.domain.PaymentMethod;

/**
 * @author zhangyunlong
 */
public class HoldMethod implements PaymentMethod {
    private String itsAddress;

    public HoldMethod(String address) {
        this.itsAddress = address;
    }

    public String getAddress() {
        return itsAddress;
    }

    @Override
    public void pay(Paycheck pc) {
        pc.setField("Disposition", "Hold");
    }
}
