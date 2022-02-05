package payroll.database;

import payroll.domain.Employee;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyunlong
 */
public class PayrollDataBase {
    private static final PayrollDataBase singleton = new PayrollDataBase();
    private Map<Integer, Employee> itsEmployees = new HashMap<>();
    private Map<Integer, Employee> itsMembers = new HashMap<>();

    private PayrollDataBase() {
    }

    public static PayrollDataBase getInstance() {
        return singleton;
    }

    public Employee getEmployee(int empId) {
        return itsEmployees.get(empId);
    }

    public void addEmployee(int empId, Employee e) {
        itsEmployees.put(empId, e);
    }

    public void clear() {
        itsEmployees.clear();
    }

    public void deleteEmployee(int empId) {
        itsEmployees.remove(empId);
    }

    public void addUnionMember(int memberId, Employee employee) {
        itsMembers.put(memberId, employee);
    }

    public Employee getUnionMember(int memberId) {
        return itsMembers.get(memberId);
    }

    public void removeUnionMember(int memberId) {
        itsMembers.remove(memberId);
    }

    public Collection<Employee> getAllEmployees() {
        return itsEmployees.values();
    }
}
