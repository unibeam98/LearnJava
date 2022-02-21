package CJV.v1ch09.map;

import java.util.HashMap;

public class MapTest {
    public static void main(String[] args)
    {
        var staff = new HashMap<Integer, Employee>();
        staff.put(1, new Employee("Amy Lee"));
        staff.put(2, new Employee("Harry Hacker"));
        staff.put(3, new Employee("Gary Cooper"));
        staff.put(4, new Employee("Francesca Cruz"));

        System.out.println(staff);

        staff.remove(2);

        staff.put(4, new Employee("Francesca Miller"));

        System.out.println(staff.get(3));

        staff.forEach((k, v) ->
                System.out.println("key=" + k + ", value=" + v));
    }
}
