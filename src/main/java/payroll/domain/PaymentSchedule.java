package payroll.domain;

import java.time.LocalDate;

/**
 * @author zhangyunlong
 */
public interface PaymentSchedule {
    boolean isPayDay(LocalDate payDate);

    LocalDate getPayPeriodStartDate(LocalDate itsPayDate);
}
