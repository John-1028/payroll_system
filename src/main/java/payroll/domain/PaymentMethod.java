package payroll.domain;

/**
 * @author zhangyunlong
 */
public interface PaymentMethod {
    void pay(Paycheck pc);
}
