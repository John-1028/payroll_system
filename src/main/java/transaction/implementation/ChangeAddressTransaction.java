package transaction.implementation;

import transaction.ChangeEmployeeTransaction;
import payroll.domain.Employee;

/**
 * @author zhangyunlong
 */
public class ChangeAddressTransaction extends ChangeEmployeeTransaction {
    private String itsAddress;

    public ChangeAddressTransaction(int empId, String itsAddress) {
        super(empId);
        this.itsAddress = itsAddress;
    }

    @Override
    protected void change(Employee e) {
        e.setAddress(itsAddress);
    }
}
