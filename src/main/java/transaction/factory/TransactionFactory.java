package transaction.factory;

import transaction.implementation.AddCommissionedEmployee;
import transaction.implementation.AddHourlyEmployee;
import transaction.implementation.AddSalariedEmployee;
import transaction.implementation.ChangeCommissionedTransaction;
import transaction.implementation.ChangeDirectTransaction;
import transaction.implementation.ChangeHoldTransaction;
import transaction.implementation.ChangeHourlyTransaction;
import transaction.implementation.ChangeMailTransaction;
import transaction.implementation.ChangeMemberTransaction;
import transaction.implementation.ChangeSalariedTransaction;
import transaction.implementation.ChangeUnaffiliatedTransaction;
import transaction.implementation.DeleteEmployeeTransaction;
import transaction.implementation.PaydayTransaction;
import transaction.implementation.SalesReceiptTransaction;
import transaction.implementation.TimeCardTransaction;

/**
 * @author zhangyunlong
 */
public interface TransactionFactory {
    AddSalariedEmployee
    makeAddSalariedEmployee(int epmId, String name, String address, double salary);

    AddCommissionedEmployee
    makeAddCommissionedEmployee(int empId, String name, String address, double salary, double commissionRate);

    AddHourlyEmployee
    makeAddHourlyEmployee(int empId, String name, String address, double hourlyRate);

    DeleteEmployeeTransaction makeDeleteEmployeeTransaction(int empId) ;

    PaydayTransaction makePaydayTransaction();

    TimeCardTransaction makeTimeCardTransaction();

    SalesReceiptTransaction makeSalesReceiptTransaction();

    ChangeHourlyTransaction makeChangeHourlyTransaction();

    ChangeCommissionedTransaction makeChangeCommissionedTransaction();

    ChangeSalariedTransaction makeChangeSalariedTransaction();

    ChangeMailTransaction makeChangeMailTransaction();

    ChangeDirectTransaction makeChangeDirectTransaction();

    ChangeHoldTransaction makeChangeHoldTransaction();

    ChangeMemberTransaction makeChangeMemberTransaction();

    ChangeUnaffiliatedTransaction makeChangeUnaffiliatedTransaction();
}
