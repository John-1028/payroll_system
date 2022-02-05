package transaction.implementation;

import payroll.domain.Employee;
import transaction.factory.TransactionFactory;

/**
 * @author zhangyunlong
 */
public class TransactionFactoryImplementation implements TransactionFactory {
    @Override
    public AddSalariedEmployee
    makeAddSalariedEmployee(int epmId, String name, String address, double salary) {
        return new AddSalariedEmployee(epmId, name, address, salary);
    }

    @Override
    public AddCommissionedEmployee
    makeAddCommissionedEmployee(int empId, String name, String address, double salary, double commissionRate) {
        return new AddCommissionedEmployee(empId, name, address, salary, commissionRate);
    }

    @Override
    public AddHourlyEmployee
    makeAddHourlyEmployee(int empId, String name, String address, double hourlyRate) {
        return new AddHourlyEmployee(empId, name, address, hourlyRate);
    }

    @Override
    public DeleteEmployeeTransaction makeDeleteEmployeeTransaction(int empId)  {
        return new DeleteEmployeeTransaction(empId);
    }

    @Override
    public PaydayTransaction makePaydayTransaction() {
        return null;
    }

    @Override
    public TimeCardTransaction makeTimeCardTransaction() {
        return null;
    }

    @Override
    public SalesReceiptTransaction makeSalesReceiptTransaction() {
        return null;
    }

    @Override
    public ChangeHourlyTransaction makeChangeHourlyTransaction() {
        return null;
    }

    @Override
    public ChangeCommissionedTransaction makeChangeCommissionedTransaction() {
        return null;
    }

    @Override
    public ChangeSalariedTransaction makeChangeSalariedTransaction() {
        return null;
    }

    @Override
    public ChangeMailTransaction makeChangeMailTransaction() {
        return null;
    }

    @Override
    public ChangeDirectTransaction makeChangeDirectTransaction() {
        return null;
    }

    @Override
    public ChangeHoldTransaction makeChangeHoldTransaction() {
        return null;
    }

    @Override
    public ChangeMemberTransaction makeChangeMemberTransaction() {
        return null;
    }

    @Override
    public ChangeUnaffiliatedTransaction makeChangeUnaffiliatedTransaction() {
        return null;
    }
}
