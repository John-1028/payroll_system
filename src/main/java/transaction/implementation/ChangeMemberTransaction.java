package transaction.implementation;

import payroll.implementation.UnionAffiliation;
import transaction.ChangeAffiliationTransaction;
import payroll.domain.Affiliation;
import payroll.domain.Employee;
import payroll.database.PayrollDataBase;

/**
 * @author zhangyunlong
 */
public class ChangeMemberTransaction extends ChangeAffiliationTransaction {
    private int itsMemberId;
    private double itsDues;
    private PayrollDataBase payrollDataBase = PayrollDataBase.getInstance();

    public ChangeMemberTransaction(int empId, int memberId, double dues) {
        super(empId);
        this.itsMemberId = memberId;
        this.itsDues = dues;
    }

    @Override
    protected Affiliation getAffiliation() {
        return new UnionAffiliation(itsMemberId, itsDues);
    }

    @Override
    protected void recordMembership(Employee e) {
        payrollDataBase.addUnionMember(itsMemberId, e);
    }
}
