package transaction;

import payroll.domain.Employee;
import payroll.database.PayrollDataBase;
import transaction.application.Transaction;

/**
 * @author zhangyunlong
 */
public abstract class ChangeEmployeeTransaction implements Transaction {
    private int itsEmpId;
    private PayrollDataBase payrollDataBase = PayrollDataBase.getInstance();

    public ChangeEmployeeTransaction(int empId) {
        this.itsEmpId = empId;
    }

    @Override
    public void execute() {
        Employee e = payrollDataBase.getEmployee(itsEmpId);
        if (e != null) {
            change(e);
        }
    }

    protected abstract void change(Employee e);
}
