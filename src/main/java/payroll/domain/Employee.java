package payroll.domain;

import java.time.LocalDate;

/**
 * @author zhangyunlong
 */
public class Employee {
    private int itsEmpId;
    private String itsName;
    private String itsAddress;
    private PaymentClassification itsClassification;
    private PaymentSchedule itsSchedule;
    private PaymentMethod itsPaymentMethod;
    private Affiliation itsAffiliation;

    public Employee(int empId, String name, String address) {
        this.itsEmpId = empId;
        this.itsName = name;
        this.itsAddress = address;
    }

    public String getName() {
        return itsName;
    }

    public int getEmpId() {
        return itsEmpId;
    }

    public String getAddress() {
        return itsAddress;
    }

    public PaymentClassification getClassification() {
        return itsClassification;
    }

    public PaymentSchedule getSchedule() {
        return itsSchedule;
    }

    public PaymentMethod getMethod() {
        return itsPaymentMethod;
    }

    public Affiliation getAffiliation() {
        return itsAffiliation;
    }

    public void setName(String name) {
        this.itsName = name;
    }

    public void setAddress(String address) {
        this.itsAddress = address;
    }

    public void setClassification(PaymentClassification pc) {
        itsClassification = pc;
    }

    public void setSchedule(PaymentSchedule ps) {
        itsSchedule = ps;
    }

    public void setMethod(PaymentMethod pm) {
        itsPaymentMethod = pm;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.itsAffiliation = affiliation;
    }

    public boolean isPayDate(LocalDate payDate) {
        return itsSchedule.isPayDay(payDate);
    }

    public void payday(Paycheck pc) {
        double grossPay = itsClassification.calculatePay(pc);
        double deductions = itsAffiliation.calculateDeductions(pc);
        double netPay = grossPay - deductions;
        pc.setGrossPay(grossPay);
        pc.setDeductions(deductions);
        pc.setNetPay(netPay);
        itsPaymentMethod.pay(pc);
    }

    public LocalDate getPayPeriodStartDate(LocalDate payPeriodEndDate) {
        return itsSchedule.getPayPeriodStartDate(payPeriodEndDate);
    }
}
