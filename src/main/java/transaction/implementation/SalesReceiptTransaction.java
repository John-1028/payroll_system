package transaction.implementation;

import payroll.domain.Employee;
import payroll.domain.PaymentClassification;
import payroll.database.PayrollDataBase;
import payroll.implementation.CommissionedClassification;
import payroll.implementation.SalesReceipt;
import transaction.application.Transaction;

import java.time.LocalDate;
import java.util.NoSuchElementException;

/**
 * @author zhangyunlong
 */
public class SalesReceiptTransaction implements Transaction {
    private int itsEmpId;
    private LocalDate itsDate;
    private double itsAmount;
    private PayrollDataBase payrollDataBase = PayrollDataBase.getInstance();

    public SalesReceiptTransaction(int empId, LocalDate date, double amount) {
        this.itsEmpId = empId;
        this.itsDate = date;
        this.itsAmount = amount;
    }

    @Override
    public void execute() {
        Employee e = payrollDataBase.getEmployee(itsEmpId);
        if (e != null) {
            PaymentClassification pc = e.getClassification();
            try {
                CommissionedClassification cc = (CommissionedClassification) pc;
                cc.addSalesReceipt(new SalesReceipt(itsDate, itsAmount));
            } catch (Exception ex) {
                throw new UnsupportedOperationException("Tried to add sales receipt to non-commission employee");
            }
        } else {
            throw new NoSuchElementException("No such employee.");
        }
    }
}
