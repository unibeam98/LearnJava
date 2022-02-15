package CJV.v1ch05.abstractClasses;

import java.time.LocalDate;

public class Employee extends Person{
    private double salary;
    private LocalDate hireDay;

    public Employee(String name, double salary, int year, int month, int day){
        super(name);
        this.salary = salary;
        this.hireDay = LocalDate.of(year, month, day);
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getHireDay() {
        return hireDay;
    }

    public void setHireDay(LocalDate hireDay) {
        this.hireDay = hireDay;
    }

    public String getDescription(){
        return String.format("an employee with a salary of $%.2f", salary);
    }

    public void raiseSalay(double byPercent){
        double raise = salary * byPercent / 100;
        salary += raise;
    }
}
