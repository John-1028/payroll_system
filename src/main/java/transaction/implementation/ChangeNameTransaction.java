package transaction.implementation;

import transaction.ChangeEmployeeTransaction;
import payroll.domain.Employee;

/**
 * @author zhangyunlong
 */
public class ChangeNameTransaction extends ChangeEmployeeTransaction {
    private String itsName;

    public ChangeNameTransaction(int empId, String itsName) {
        super(empId);
        this.itsName = itsName;
    }

    @Override
    protected void change(Employee e) {
        e.setName(itsName);
    }
}
