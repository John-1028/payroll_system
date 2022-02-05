package transaction.implementation;

import payroll.domain.Paycheck;
import payroll.domain.Employee;
import payroll.database.PayrollDataBase;
import transaction.application.Transaction;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyunlong
 */
public class PaydayTransaction implements Transaction {
    private LocalDate itsPayDate;
    private Map<Integer, Paycheck> itsPaychecks = new HashMap<>();
    private PayrollDataBase payrollDataBase = PayrollDataBase.getInstance();

    public PaydayTransaction(LocalDate payDate) {
        this.itsPayDate = payDate;
    }

    @Override
    public void execute() {
        Collection<Employee> employees = payrollDataBase.getAllEmployees();
        for (Employee e : employees) {
            if (e.isPayDate(itsPayDate)) {
                Paycheck pc = new Paycheck(e.getPayPeriodStartDate(itsPayDate), itsPayDate);
                itsPaychecks.put(e.getEmpId(), pc);
                e.payday(pc);
            }
        }
    }

    public Paycheck getPaycheck(int empId) {
        return itsPaychecks.get(empId);
    }
}
