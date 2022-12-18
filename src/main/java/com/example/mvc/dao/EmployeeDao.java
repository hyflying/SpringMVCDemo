package com.example.mvc.dao;

import com.example.mvc.pojo.Employee;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeDao {
    private static Map<Integer, Employee> employees;
    static {
        employees = new HashMap<Integer, Employee>();
        employees.put(1001, new Employee(1001, "E-AA", "aaa.163.com", 1));
        employees.put(1002, new Employee(1002, "E-BB", "bbb.163.com", 0));
        employees.put(1003, new Employee(1003, "E-CC", "ccc.163.com", 1));
        employees.put(1004, new Employee(1004, "E-DD", "ddd.163.com", 0));
        employees.put(1005, new Employee(1005, "E-EE", "eee.163.com", 1));
    }
    private static Integer initId = 1006;
    public void saveEmployee(Employee e) {
        if(e.getId() == null) {
            e.setId(initId ++);
        }
        employees.put(e.getId(), e);
    }

    public Collection<Employee> getAll() {
        return employees.values();
    }

    public Employee get(Integer id) {
        return employees.get(id);
    }

    public void delete(Integer id) {
        employees.remove(id);
    }
}
