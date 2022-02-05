package transaction;

import payroll.domain.Affiliation;
import payroll.domain.Employee;
import payroll.implementation.HoldMethod;
import payroll.implementation.NoAffiliation;
import payroll.domain.PaymentClassification;
import payroll.domain.PaymentMethod;
import payroll.domain.PaymentSchedule;
import payroll.database.PayrollDataBase;
import transaction.application.Transaction;

/**
 * @author zhangyunlong
 */
public abstract class AddEmployeeTransaction implements Transaction {
    private int itsEmpId;
    private String itsName;
    private String itsAddress;
    private PayrollDataBase payrollDatabase = PayrollDataBase.getInstance();

    public AddEmployeeTransaction(int empId, String name, String address) {
        this.itsEmpId = empId;
        this.itsName = name;
        this.itsAddress = address;
    }

    public void execute() {
        PaymentClassification pc = getClassification();
        PaymentSchedule ps = getSchedule();
        PaymentMethod pm = new HoldMethod("Home");
        Affiliation af = new NoAffiliation();
        Employee e = new Employee(itsEmpId, itsName, itsAddress);
        e.setClassification(pc);
        e.setSchedule(ps);
        e.setMethod(pm);
        e.setAffiliation(af);
        payrollDatabase.addEmployee(itsEmpId, e);
    }

    public abstract PaymentClassification getClassification();

    public abstract PaymentSchedule getSchedule();
}
