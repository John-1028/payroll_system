package payroll;

import org.junit.Test;
import payroll.domain.Paycheck;
import payroll.implementation.BiweeklySchedule;
import payroll.implementation.CommissionedClassification;
import payroll.implementation.DirectMethod;
import payroll.implementation.HoldMethod;
import payroll.implementation.HourlyClassification;
import payroll.implementation.MailMethod;
import payroll.implementation.MonthlySchedule;
import payroll.implementation.NoAffiliation;
import payroll.implementation.SalariedClassification;
import payroll.implementation.SalesReceipt;
import payroll.implementation.ServiceCharge;
import payroll.implementation.TimeCard;
import payroll.implementation.UnionAffiliation;
import payroll.implementation.WeeklySchedule;
import payroll.domain.Affiliation;
import payroll.domain.Employee;
import payroll.domain.PaymentClassification;
import payroll.domain.PaymentMethod;
import payroll.domain.PaymentSchedule;
import payroll.database.PayrollDataBase;
import transaction.application.Transaction;
import transaction.factory.TransactionFactory;
import transaction.implementation.ChangeAddressTransaction;
import transaction.implementation.ChangeCommissionedTransaction;
import transaction.implementation.ChangeDirectTransaction;
import transaction.implementation.ChangeHoldTransaction;
import transaction.implementation.ChangeHourlyTransaction;
import transaction.implementation.ChangeMailTransaction;
import transaction.implementation.ChangeMemberTransaction;
import transaction.implementation.ChangeNameTransaction;
import transaction.implementation.ChangeSalariedTransaction;
import transaction.implementation.ChangeUnaffiliatedTransaction;
import transaction.implementation.PaydayTransaction;
import transaction.implementation.SalesReceiptTransaction;
import transaction.implementation.ServiceChargeTransaction;
import transaction.implementation.TimeCardTransaction;
import transaction.ChangeEmployeeTransaction;
import transaction.implementation.TransactionFactoryImplementation;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * @author zhangyunlong
 */
public class PayrollTest {
    private PayrollDataBase payrollDataBase = PayrollDataBase.getInstance();
    private TransactionFactory transactionFactory = new TransactionFactoryImplementation();

    @Test
    public void testAddSalariedEmployee() {
        int empId = 1;
        Transaction t =
                transactionFactory.makeAddSalariedEmployee(empId, "Bob", "Beijing", 1000.00);
        t.execute();

        Employee e = payrollDataBase.getEmployee(empId);
        assert "Bob".equals(e.getName());

        PaymentClassification pc = e.getClassification();
        SalariedClassification sc = (SalariedClassification) pc;
        assertNotNull(sc);

        assertEquals(1000.00, sc.getSalary(), .001);
        PaymentSchedule ps = e.getSchedule();
        MonthlySchedule ms = (MonthlySchedule) ps;
        assertNotNull(ms);
        PaymentMethod pm = e.getMethod();
        HoldMethod hm = (HoldMethod) pm;
        assertNotNull(hm);
    }

    @Test
    public void testAddHourlyEmployee() {
        int empId = 2;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Tom", "Shanghai", 10.00);
        t.execute();

        Employee e = payrollDataBase.getEmployee(empId);
        assert "Tom".equals(e.getName());

        PaymentClassification pc = e.getClassification();
        HourlyClassification hc = (HourlyClassification) pc;
        assertNotNull(hc);

        PaymentSchedule ps = e.getSchedule();
        WeeklySchedule ws = (WeeklySchedule) ps;
        assertNotNull(ws);
        PaymentMethod pm = e.getMethod();
        HoldMethod hm = (HoldMethod) pm;
        assertNotNull(hm);
    }

    @Test
    public void testAddCommissionedEmployee() {
        int empId = 3;
        Transaction t =
                transactionFactory.makeAddCommissionedEmployee(empId, "Jack", "Shanghai", 1000.00, 100.00);
        t.execute();

        Employee e = payrollDataBase.getEmployee(empId);
        assert "Jack".equals(e.getName());

        PaymentClassification pc = e.getClassification();
        CommissionedClassification cc = (CommissionedClassification) pc;
        assertNotNull(cc);

        PaymentSchedule ps = e.getSchedule();
        BiweeklySchedule bs = (BiweeklySchedule) ps;
        assertNotNull(bs);
        PaymentMethod pm = e.getMethod();
        HoldMethod hm = (HoldMethod) pm;
        assertNotNull(hm);
    }

    @Test
    public void testDeleteEmployee() {
        int empId = 4;
        Transaction t =
                transactionFactory.makeAddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
        t.execute();
        {
            Employee e = payrollDataBase.getEmployee(empId);
            assertNotNull(e);
        }
        Transaction dt = transactionFactory.makeDeleteEmployeeTransaction(empId);
        dt.execute();
        {
            Employee e = payrollDataBase.getEmployee(empId);
            assertNull(e);
        }
    }

    @Test
    public void testTimeCardTransaction() {
        int empId = 5;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        LocalDate date = LocalDate.of(2001, 10, 31);
        Transaction tct = new TimeCardTransaction(empId, date, 8.0);
        tct.execute();
        Employee e = payrollDataBase.getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getClassification();
        HourlyClassification hc = (HourlyClassification) pc;
        assertNotNull(hc);
        TimeCard tc = hc.getTimeCard(date);
        assertNotNull(tc);
        assertEquals(8.0, tc.getHours(), .001);
    }

    @Test
    public void testSalesReceiptTransaction() {
        int empId = 6;
        Transaction t =
                transactionFactory.makeAddCommissionedEmployee(empId, "Lance", "Home", 2500, 3.2);
        t.execute();

        LocalDate date = LocalDate.of(2001, 10, 31);
        Transaction srt = new SalesReceiptTransaction(empId, date, 10);
        srt.execute();

        Employee e = payrollDataBase.getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getClassification();
        CommissionedClassification cc = (CommissionedClassification) pc;
        assertNotNull(cc);
        SalesReceipt sr = cc.getSalesReceipt(date);
        assertNotNull(sr);
        assertEquals(10, sr.getAmount(), .001);
    }

    @Test
    public void testAddServiceCharge() {
        int empId = 7;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Fowler", "Home", 15.25);
        t.execute();

        Employee e = payrollDataBase.getEmployee(empId);
        assertNotNull(e);

        int memberId = 86; // Maxwell Smart
        UnionAffiliation af = new UnionAffiliation(memberId, 12.5);
        e.setAffiliation(af);
        payrollDataBase.addUnionMember(memberId, e);
        LocalDate date = LocalDate.of(2001, 11, 1);
        Transaction sct = new ServiceChargeTransaction(memberId, date, 12.95);
        sct.execute();
        ServiceCharge sc = af.getServiceCharge(date);
        assertNotNull(sc);
        assertEquals(12.95, sc.getCharge(), .001);
    }

    @Test
    public void testChangeNameTransaction() {
        int empId = 8;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();

        ChangeEmployeeTransaction cet = new ChangeNameTransaction(empId, "Bob");
        cet.execute();
        Employee e = payrollDataBase.getEmployee(empId);
        assertNotNull(e);
        assertEquals("Bob", e.getName());
    }

    @Test
    public void testChangeAddressTransaction() {
        int empId = 9;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Bob", "Home", 15.25);
        t.execute();

        Transaction cet = new ChangeAddressTransaction(empId, "Beijing");
        cet.execute();
        Employee e = payrollDataBase.getEmployee(empId);
        assertNotNull(e);
        assertEquals("Beijing", e.getAddress());
    }

    @Test
    public void testChangeHourlyTransaction() {
        int empId = 10;
        Transaction t =
                transactionFactory.makeAddCommissionedEmployee(empId, "Bob", "Home", 1500, 3.2);
        t.execute();

        Transaction cet = new ChangeHourlyTransaction(empId, 27.52);
        cet.execute();
        Employee e = payrollDataBase.getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getClassification();
        HourlyClassification hc = (HourlyClassification) pc;
        assertNotNull(hc);
        assertEquals(27.52, hc.getRate(), .001);
        PaymentSchedule ps = e.getSchedule();
        WeeklySchedule ws = (WeeklySchedule) ps;
        assertNotNull(ws);
    }

    @Test
    public void testChangeSalariedTransaction() {
        int empId = 11;
        Transaction t =
                transactionFactory.makeAddCommissionedEmployee(empId, "Bob", "Home", 1500, 3.2);
        t.execute();

        Transaction cet = new ChangeSalariedTransaction(empId, 2500);
        cet.execute();
        Employee e = payrollDataBase.getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getClassification();
        SalariedClassification sc = (SalariedClassification) pc;
        assertNotNull(sc);
        assertEquals(2500, sc.getSalary(), .001);
        PaymentSchedule ps = e.getSchedule();
        MonthlySchedule ms = (MonthlySchedule) ps;
        assertNotNull(ms);
    }

    @Test
    public void testChangeCommissionedTransaction() {
        int empId = 12;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Bob", "Home", 15.25);
        t.execute();

        Transaction cet = new ChangeCommissionedTransaction(empId, 1500, 3.2);
        cet.execute();
        Employee e = payrollDataBase.getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getClassification();
        CommissionedClassification cc = (CommissionedClassification) pc;
        assertNotNull(cc);
        PaymentSchedule ps = e.getSchedule();
        BiweeklySchedule bs = (BiweeklySchedule) ps;
        assert bs != null;
        assertNotNull(bs);
    }

    @Test
    public void testChangeDirectTransaction() {
        int empId = 13;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Bob", "Home", 15.25);
        t.execute();

        Transaction cet =
                new ChangeDirectTransaction(empId, "bank", "11111");
        cet.execute();
        Employee e = payrollDataBase.getEmployee(empId);
        PaymentMethod pm = e.getMethod();
        DirectMethod dm = (DirectMethod) pm;
        assertNotNull(dm);
        assertEquals("11111", dm.getAccount());
    }

    @Test
    public void testChangeMailTransaction() {
        int empId = 14;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Bob", "Home", 15.25);
        t.execute();

        Transaction cet = new ChangeMailTransaction(empId, "Home");
        cet.execute();
        Employee e = payrollDataBase.getEmployee(empId);
        PaymentMethod pm = e.getMethod();
        MailMethod mm = (MailMethod) pm;
        assertNotNull(mm);
        assertEquals("Home", mm.getAddress());
    }

    @Test
    public void testChangeHoldTransaction() {
        int empId = 15;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Bob", "Home", 15.25);
        t.execute();

        Transaction cet = new ChangeMailTransaction(empId, "Home");
        cet.execute();

        cet = new ChangeHoldTransaction(empId, "Home");
        cet.execute();
        Employee e = payrollDataBase.getEmployee(empId);
        PaymentMethod pm = e.getMethod();
        HoldMethod hm = (HoldMethod) pm;
        assertNotNull(hm);
    }

    @Test
    public void testChangeMemberTransaction() {
        int empId = 16;
        int memberId = 7732;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Bob", "Home", 15.25);
        t.execute();

        Transaction cet = new ChangeMemberTransaction(empId, memberId, 99.42);
        cet.execute();
        Employee e = payrollDataBase.getEmployee(empId);
        assertNotNull(e);
        Affiliation af = e.getAffiliation();
        assertNotNull(af);
        UnionAffiliation uaf = (UnionAffiliation) af;
        assertEquals(99.42, uaf.getDues(), .001);
        Employee member = payrollDataBase.getUnionMember(memberId);
        assertNotNull(member);
        assertEquals(e, member);
    }

    @Test
    public void testChangeUnaffiliatedTransaction() {
        int empId = 17;
        int memberId = 7733;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Bob", "Home", 15.25);
        t.execute();

        // add affiliation
        Transaction cet = new ChangeMemberTransaction(empId, memberId, 99.42);
        cet.execute();

        // remove affiliation
        cet = new ChangeUnaffiliatedTransaction(empId);
        cet.execute();
        Employee e = payrollDataBase.getEmployee(empId);
        assertNotNull(e);
        Affiliation af = e.getAffiliation();
        NoAffiliation naf = (NoAffiliation) af;
        assertNotNull(naf);
        Employee member = payrollDataBase.getUnionMember(memberId);
        assertNull(member);
    }

    @Test
    public void testPaySingleSalariedEmployee() {
        int empId = 18;
        Transaction t =
                transactionFactory.makeAddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 30);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertNotNull(pc);
        assertEquals(payDate, pc.getPayPeriodEndDate());
        assertEquals(1000.00, pc.getGrossPay(), .001);
        assertEquals("Hold", pc.getField("Disposition"));
        assertEquals(0.0, pc.getDeductions(), .001);
        assertEquals(1000.00, pc.getNetPay(), .001);
    }

    @Test
    public void testPaySingleSalariedEmployeeOnWrongDate() {
        int empId = 19;
        Transaction t =
                transactionFactory.makeAddSalariedEmployee(empId, "Bob", "Home", 100.00);
        t.execute();
        LocalDate payDate = LocalDate.of(2001, 11, 29);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertNull(pc);
    }

    @Test
    public void testPaySingleHourlyEmployeeNoTimeCards() {
        int empId = 20;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Bob", "Home", 15.25);
        t.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 9); // Friday
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 0.0, 0.0);
    }

    @Test
    public void testPaySingleHourlyEmployeeOneTimeCard() {
        int empId = 21;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Bob", "Home", 15.25);
        t.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 9);
        Transaction tc = new TimeCardTransaction(empId, payDate, 2.0);
        tc.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 30.5, 0.0);
    }

    @Test
    public void testPaySingleHourlyEmployeeOvertimeOneTimeCard() {
        int empId = 22;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Bob", "Home", 15);
        t.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 9);
        Transaction tc = new TimeCardTransaction(empId, payDate, 9.0);
        tc.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 142.5, 0.0);
    }

    @Test
    public void testPaySingleHourlyEmployeeOnWrongDate() {
        int empId = 23;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Bob", "Home", 15);
        t.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 8);
        Transaction tc = new TimeCardTransaction(empId, payDate, 8);
        tc.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        assertNull(pc);
    }

    @Test
    public void testPaySingleHourlyEmployeeTwoTimeCards() {
        int empId = 24;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Bob", "Home", 15);
        t.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 9); // Friday
        Transaction tc = new TimeCardTransaction(empId, payDate, 2.0);
        tc.execute();
        Transaction tc2 = new TimeCardTransaction(empId, LocalDate.of(2001, 11, 8), 5.0);
        tc2.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 105, 0.0);
    }

    @Test
    public void testPaySingleHourlyEmployeeWithTimeCardsSpanningTwoPeriods() {
        int empId = 25;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Bob", "Home", 15);
        t.execute();
        LocalDate payDate = LocalDate.of(2001, 11, 9); // Friday
        LocalDate dateInPreviousPayPeriod = LocalDate.of(2001, 11, 2); // Previous Friday

        Transaction tc = new TimeCardTransaction(empId, payDate, 2.0);
        tc.execute();
        Transaction tc2 = new TimeCardTransaction(empId, dateInPreviousPayPeriod, 5.0);
        tc2.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 2 * 15, 0.0);
    }

    @Test
    public void testSalariedUnionMemberDues() {
        int empId = 26;
        Transaction t =
                transactionFactory.makeAddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();

        int memberId = 7734;
        Transaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();
        LocalDate payDate = LocalDate.of(2001, 11, 30);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 1000.00, 47.1);
    }

    @Test
    public void testHourlyUnionMemberServiceCharge() {
        int empId = 27;
        Transaction t =
                transactionFactory.makeAddHourlyEmployee(empId, "Bill", "Home", 15.24);
        t.execute();

        int memberId = 7735;
        Transaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 9);
        Transaction sct = new ServiceChargeTransaction(memberId, payDate, 19.42);
        sct.execute();

        Transaction tct = new TimeCardTransaction(empId, payDate, 8.0);
        tct.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        validatePaycheck(pt, empId, payDate, 8 * 15.24, 9.42 + 19.42);
    }

    @Test
    public void testServiceChargesSpanningMultiplePayPeriods() {
        int empId = 28;
        Transaction t = transactionFactory.makeAddHourlyEmployee(empId, "Bill", "Home", 15.24);
        t.execute();

        int memberId = 7736;
        Transaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();

        LocalDate earlyDate = LocalDate.of(2001, 11, 2); // previous Friday
        LocalDate payDate = LocalDate.of(2001, 11, 9); // next Friday
        LocalDate lateDate = LocalDate.of(2001, 11, 16);
        Transaction sct = new ServiceChargeTransaction(memberId, payDate, 19.42);
        sct.execute();
        Transaction sctEarly = new ServiceChargeTransaction(memberId, earlyDate, 100.00);
        sctEarly.execute();
        Transaction sctLate = new ServiceChargeTransaction(memberId, lateDate, 200.00);
        sctLate.execute();

        Transaction tct = new TimeCardTransaction(empId, payDate, 8.0);
        tct.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        validatePaycheck(pt, empId, payDate, 8 * 15.24, 9.42 + 19.42);
    }

    @Test
    public void testPaySingleCommissionedEmployee() {
        int empId = 28;
        Transaction t =
                transactionFactory.makeAddCommissionedEmployee(empId, "Jack", "Shanghai", 1000.00, 3.2);
        t.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 16);
        Transaction srt = new SalesReceiptTransaction(empId, payDate, 1000);
        srt.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        validatePaycheck(pt, empId, payDate, 1032, 0);
    }

    @Test
    public void testPaySingleCommissionedEmployeeOnWrongDate() {
        int empId = 29;
        Transaction t =
                transactionFactory.makeAddCommissionedEmployee(empId, "Jack", "Shanghai", 1000.00, 3.2);
        t.execute();

        LocalDate payDate = LocalDate.of(2001, 11, 9);
        Transaction srt = new SalesReceiptTransaction(empId, payDate, 1000);
        srt.execute();

        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        assertNull(pt.getPaycheck(empId));
    }

    private void validatePaycheck(PaydayTransaction pt, int empId,
                                  LocalDate payDate, double pay, double deductions) {
        Paycheck pc = pt.getPaycheck(empId);
        assertNotNull(pc);
        assertEquals(payDate, pc.getPayPeriodEndDate());
        assertEquals("Hold", pc.getField("Disposition"));
        assertEquals(pay, pc.getGrossPay(), .001);
        assertEquals(deductions, pc.getDeductions(), .001);
        assertEquals(pay - deductions, pc.getNetPay(), .001);
    }
}
