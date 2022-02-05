package payroll.implementation;

import utils.DateUtils;
import payroll.domain.PaymentSchedule;

import java.time.LocalDate;

/**
 * @author zhangyunlong
 */
public class MonthlySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDay(LocalDate payDate) {
        return DateUtils.isLastDayOfMonth(payDate);
    }

    @Override
    public LocalDate getPayPeriodStartDate(LocalDate itsPayDate) {
        return itsPayDate.withDayOfMonth(1);
    }
}
