package transaction.implementation;

import payroll.implementation.HoldMethod;
import transaction.ChangeMethodTransaction;
import payroll.domain.PaymentMethod;

/**
 * @author zhangyunlong
 */
public class ChangeHoldTransaction extends ChangeMethodTransaction {
    private String itsAddress;

    public ChangeHoldTransaction(int empId, String address) {
        super(empId);
        this.itsAddress = address;
    }

    @Override
    protected PaymentMethod getMethod() {
        return new HoldMethod(itsAddress);
    }
}
