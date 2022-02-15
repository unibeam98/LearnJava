package CJV.v1ch04;

import java.util.Date;

public class EmployeeTest {
    public static void main(String[] args)
    {
        Employeee[] staff = new Employeee[3];

        staff[0] = new Employeee("Carl Cracker", 75000, 1987, 12, 15);
        staff[1] = new Employeee("Harry Hacker", 50000, 1989, 10, 1);
        staff[2] = new Employeee("Tony Tester", 40000, 1990, 3, 15);

        System.out.println("name=" + staff[0].getName() + ",salary=" + staff[0].getSalary() + ",hireDay="
                + staff[0].getHireday());

        Date d = staff[0].getHireday();
        double tenYear = 10 * 365 * 24 * 60 * 60 * 1000;
        d.setTime(d.getTime() - (long)tenYear);



        for(Employeee e : staff)
            e.raiseSalary(5);

        for(Employeee e : staff)
            System.out.println("name=" + e.getName() + ",salary=" + e.getSalary() + ",hireDay="
                    + e.getHireday());
    }
}

class Employeee
{
    private String name;
    private double salary;
    private Date hireday;

    public Employeee(String n, double s, int year, int month, int day)
    {
        name = n;
        salary = s;
        hireday = new Date(1644896657);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getHireday() {
        return (Date)hireday.clone() ;
    }

    public void setHireday(Date hireday) {
        this.hireday = hireday;
    }

    public void raiseSalary(double byPercent)
    {
        double raise = salary * byPercent / 100;
        salary += raise;
    }
}
