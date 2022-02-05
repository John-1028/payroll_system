package payroll.implementation;

import utils.DateUtils;
import payroll.domain.PaymentSchedule;

import java.time.LocalDate;

/**
 * @author zhangyunlong
 */
public class WeeklySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDay(LocalDate payDate) {
        return DateUtils.isFriday(payDate);
    }

    @Override
    public LocalDate getPayPeriodStartDate(LocalDate itsPayDate) {
        return itsPayDate.plusDays(-7 + 1);
    }
}
