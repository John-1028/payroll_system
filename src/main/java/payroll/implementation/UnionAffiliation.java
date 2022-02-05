package payroll.implementation;

import utils.DateUtils;
import payroll.domain.Paycheck;
import payroll.domain.Affiliation;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyunlong
 */
public class UnionAffiliation implements Affiliation {
    private int itsMemberId;
    private double itsDues;
    private Map<LocalDate, ServiceCharge> serviceCharges = new HashMap<>();

    public UnionAffiliation(int memberId, double dues) {
        this.itsMemberId = memberId;
        this.itsDues = dues;
    }

    public ServiceCharge getServiceCharge(LocalDate date) {
        return serviceCharges.get(date);
    }

    public void addServiceCharge(LocalDate date, double charge) {
        serviceCharges.put(date, new ServiceCharge(date, charge));
    }

    public double getDues() {
        return itsDues;
    }

    public int getMemberId() {
        return itsMemberId;
    }

    @Override
    public double calculateDeductions(Paycheck pc) {
        int fridays = numberOfFridaysInPayPeriod(pc.getPayPeriodStartDate(), pc.getPayPeriodEndDate());
        double unionMemberDues = itsDues * fridays;
        double totalServiceCharges = 0;
        for (ServiceCharge sc : serviceCharges.values()) {
            if (DateUtils.isBetween(sc.getDate(),
                    pc.getPayPeriodStartDate(), pc.getPayPeriodEndDate())) {
                totalServiceCharges += sc.getCharge();
            }
        }
        return unionMemberDues + totalServiceCharges;
    }

    /**
     * 统计指定时间段内周五的数量
     *
     * @param payPeriodStartDate 起始日期
     * @param payPeriodEndDate 结束日期
     * @return 周五的数量
     */
    private int numberOfFridaysInPayPeriod(LocalDate payPeriodStartDate, LocalDate payPeriodEndDate) {
        int fridays = 0;
        LocalDate temp = payPeriodStartDate;
        while (temp.compareTo(payPeriodEndDate) <= 0) {
            if (temp.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                fridays++;
            }
            temp = temp.plusDays(1);
        }
        return fridays;
    }
}
