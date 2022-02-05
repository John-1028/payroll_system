package transaction.implementation;

import payroll.implementation.MailMethod;
import transaction.ChangeMethodTransaction;
import payroll.domain.PaymentMethod;

/**
 * @author zhangyunlong
 */
public class ChangeMailTransaction extends ChangeMethodTransaction {
    private String itsAddress;

    public ChangeMailTransaction(int empId, String address) {
        super(empId);
        this.itsAddress = address;
    }

    @Override
    protected PaymentMethod getMethod() {
        return new MailMethod(itsAddress);
    }
}
