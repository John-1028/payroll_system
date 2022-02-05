package transaction.implementation;

import payroll.domain.Affiliation;
import payroll.domain.Employee;
import payroll.database.PayrollDataBase;
import payroll.implementation.UnionAffiliation;
import transaction.application.Transaction;

import java.time.LocalDate;

/**
 * @author zhangyunlong
 */
public class ServiceChargeTransaction implements Transaction {
    private int itsMemberId;
    private LocalDate itsDate;
    private double itsCharge;
    private PayrollDataBase payrollDataBase = PayrollDataBase.getInstance();

    public ServiceChargeTransaction(int memberId, LocalDate date, double charge) {
        this.itsMemberId = memberId;
        this.itsDate = date;
        this.itsCharge = charge;
    }

    @Override
    public void execute() {
        Employee e = payrollDataBase.getUnionMember(itsMemberId);
        Affiliation af = e.getAffiliation();
        UnionAffiliation uaf = (UnionAffiliation) af;
        uaf.addServiceCharge(itsDate, itsCharge);
    }
}
