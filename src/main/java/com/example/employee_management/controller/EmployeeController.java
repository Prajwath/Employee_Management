package com.example.employee_management.controller;

import com.example.employee_management.model.Employee;
import com.example.employee_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Show Employee List Page
    @GetMapping("/employees")
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("employee", new Employee()); // For the form
        return "employee"; // Renders employee.html
    }

    // Show Add/Edit Form
    @GetMapping("/employees/edit/{id}")
    public String editEmployee(@PathVariable(required = false) Long id, Model model) {
        if (id != null) {
            Employee employee = employeeService.getEmployeeById(id);
            model.addAttribute("employee", employee);
        } else {
            model.addAttribute("employee", new Employee());
        }
        return "employee";
    }

    // Save or Update Employee
    @PostMapping("/employees")
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    // Delete Employee
    @GetMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }

    // API Endpoint to Fetch Employee by ID
    @GetMapping("/api/employees/{id}")
    @ResponseBody // Indicates that the response should be returned as JSON
    public ResponseEntity<Employee> getEmployeeByIdApi(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee); // Return 200 OK with the employee data
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if employee doesn't exist
        }
    }
}