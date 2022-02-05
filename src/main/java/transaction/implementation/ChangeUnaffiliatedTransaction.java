package transaction.implementation;

import payroll.implementation.NoAffiliation;
import payroll.implementation.UnionAffiliation;
import transaction.ChangeAffiliationTransaction;
import payroll.domain.Affiliation;
import payroll.domain.Employee;
import payroll.database.PayrollDataBase;

/**
 * @author zhangyunlong
 */
public class ChangeUnaffiliatedTransaction extends ChangeAffiliationTransaction {
    private PayrollDataBase payrollDataBase = PayrollDataBase.getInstance();

    public ChangeUnaffiliatedTransaction(int empId) {
        super(empId);
    }

    @Override
    protected Affiliation getAffiliation() {
        return new NoAffiliation();
    }

    @Override
    protected void recordMembership(Employee e) {
        Affiliation af = e.getAffiliation();
        UnionAffiliation uaf = (UnionAffiliation) af;
        int memberId = uaf.getMemberId();
        payrollDataBase.removeUnionMember(memberId);
    }
}
