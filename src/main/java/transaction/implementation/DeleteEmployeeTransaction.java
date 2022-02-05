package transaction.implementation;

import payroll.database.PayrollDataBase;
import transaction.application.Transaction;

/**
 * @author zhangyunlong
 */
public class DeleteEmployeeTransaction implements Transaction {
    private int itsEmpId;
    private PayrollDataBase payrollDataBase = PayrollDataBase.getInstance();

    public DeleteEmployeeTransaction(int empId) {
        itsEmpId = empId;
    }

    @Override
    public void execute() {
        payrollDataBase.deleteEmployee(itsEmpId);
    }
}
