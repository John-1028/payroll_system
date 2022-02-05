package payroll.implementation;

import utils.DateUtils;
import payroll.domain.Paycheck;
import payroll.domain.PaymentClassification;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyunlong
 */
public class CommissionedClassification implements PaymentClassification {
    private double itsSalary;
    private double itsCommissionRate;
    private Map<LocalDate, SalesReceipt> salesReceipts = new HashMap<>();

    public CommissionedClassification(double salary, double commissionRate) {
        this.itsSalary = salary;
        this.itsCommissionRate = commissionRate;
    }

    public void addSalesReceipt(SalesReceipt salesReceipt) {
        salesReceipts.put(salesReceipt.getDate(), salesReceipt);
    }

    public SalesReceipt getSalesReceipt(LocalDate date) {
        return salesReceipts.get(date);
    }

    @Override
    public double calculatePay(Paycheck pc) {
        double totalSalesAmount = 0;
        for (SalesReceipt sr : salesReceipts.values()) {
            if (DateUtils.isBetween(sr.getDate(),
                    pc.getPayPeriodStartDate(), pc.getPayPeriodEndDate())) {
                totalSalesAmount += sr.getAmount();
            }
        }
        return itsSalary + calculateCommission(totalSalesAmount);
    }

    /**
     * 计算提成
     *
     * @param totalSalesAmount 总销售额
     * @return 提成
     */
    private double calculateCommission(double totalSalesAmount) {
        return totalSalesAmount * itsCommissionRate * 0.01;
    }
}
