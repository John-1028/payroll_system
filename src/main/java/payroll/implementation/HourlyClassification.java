package payroll.implementation;

import utils.DateUtils;
import payroll.domain.Paycheck;
import payroll.domain.PaymentClassification;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyunlong
 */
public class HourlyClassification implements PaymentClassification {
    private static final double STANDARD_HOURS = 8;
    private double itsRate;
    private Map<LocalDate, TimeCard> timeCards = new HashMap<>();

    public HourlyClassification(double rate) {
        this.itsRate = rate;
    }

    public void addTimeCard(TimeCard timeCard) {
        timeCards.put(timeCard.getDate(), timeCard);
    }

    public TimeCard getTimeCard(LocalDate date) {
        return timeCards.get(date);
    }

    public double getRate() {
        return itsRate;
    }

    @Override
    public double calculatePay(Paycheck pc) {
        double totalPay = 0;
        Collection<TimeCard> values = timeCards.values();
        for (TimeCard t : values) {
            if (DateUtils.isBetween(t.getDate(),
                    pc.getPayPeriodStartDate(), pc.getPayPeriodEndDate())) {
                totalPay += calculatePayForTimeCard(t);
            }
        }
        return totalPay;
    }

    private double calculatePayForTimeCard(TimeCard t) {
        double hours = t.getHours();
        double overtime = Math.max(0.0, hours - STANDARD_HOURS);
        double straightTime = hours - overtime;
        return basicSalary(straightTime) + overtimeSalary(overtime);
    }

    /**
     * 计算加班费
     *
     * @param workHours 加班时长
     * @return 加班费
     */
    private double overtimeSalary(double workHours) {
        return workHours * itsRate * 1.5;
    }

    /**
     * 计算工资
     *
     * @param workHours 工作时长
     * @return 工资
     */
    private double basicSalary(double workHours) {
        return workHours * itsRate;
    }
}
