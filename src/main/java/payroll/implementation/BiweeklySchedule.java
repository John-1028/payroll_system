package payroll.implementation;

import utils.DateUtils;
import payroll.domain.PaymentSchedule;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * @author zhangyunlong
 */
public class BiweeklySchedule implements PaymentSchedule {
    @Override
    public boolean isPayDay(LocalDate payDate) {
        return DateUtils.isBiweekly(payDate)
                && DayOfWeek.FRIDAY.equals(payDate.getDayOfWeek());
    }

    @Override
    public LocalDate getPayPeriodStartDate(LocalDate payDate) {
        return payDate.plusDays(-14 + 1);
    }
}
