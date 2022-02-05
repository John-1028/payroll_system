package payroll.implementation;

import payroll.domain.Paycheck;
import payroll.domain.Affiliation;

/**
 * @author zhangyunlong
 */
public class NoAffiliation implements Affiliation {
    @Override
    public double calculateDeductions(Paycheck pc) {
        return 0;
    }
}
