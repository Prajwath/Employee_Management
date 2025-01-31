package com.example.employee_management.controller;

import com.example.employee_management.model.Employee;
import com.example.employee_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Show Employee List Page
    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("employee", new Employee()); // For the form
        return "employee"; // Renders employee.html
    }

    // Show Add/Edit Form
    @GetMapping("/edit/{id}")
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
    @PostMapping
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    // Delete Employee
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
}