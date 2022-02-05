package transaction.implementation;

import payroll.implementation.DirectMethod;
import transaction.ChangeMethodTransaction;
import payroll.domain.PaymentMethod;

/**
 * @author zhangyunlong
 */
public class ChangeDirectTransaction extends ChangeMethodTransaction {
    private String itsBank;
    private String itsAccount;

    public ChangeDirectTransaction(int empId, String bank, String account) {
        super(empId);
        this.itsBank = bank;
        this.itsAccount = account;
    }

    @Override
    protected PaymentMethod getMethod() {
        return new DirectMethod(itsBank, itsAccount);
    }
}
