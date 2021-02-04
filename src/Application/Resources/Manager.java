package Application.Resources;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Manager extends Employee{
    private int admin = 0;

    private List <Employee> employees = new ArrayList<>();

    public Manager(int id, String first_name, String last_name, String username,
                   String password, String email, String mobile_number, Double salary, int admin)
    {
        this.id = id;
        this.admin = admin;
        this.Email = email;
        this.First_Name = first_name;
        this.Salary = salary;
        this.Last_Name = last_name;
        this.Password = password;
        this.UserName = username;
        this.Mobile_Number = mobile_number;
    }

    public int getAdmin() {
        return admin;
    }

    public boolean updateEmployee (Employee e, String Option, String NewValue, boolean W)
    {
        String UPDATE_QUERY;
        for (Employee X :
                employees) {
            if (e == X) {
                if (W == true || admin == 0)
                    UPDATE_QUERY = "UPDATE `waiter_Table` SET `"+ Option +"` = '"+ NewValue +"' WHERE `waiter_Table`.id = '"+ e.getId() +"'";
                else
                    UPDATE_QUERY = "UPDATE `manager_Table` SET `"+ Option +"` = '"+ NewValue+"' WHERE `manager_Table`.id = '"+ e.getId() +"'";
                MySqlConnection.MakeConnection().executeQuery(UPDATE_QUERY, "ERROR in QUERY");
                return true;
            }
        }
        return false;
    }

    public void addEmployee (Employee e) {
        employees.add(e);
    }

    public void removeEmployee (Employee e) {
        employees.remove(e);
    }

    public List <Employee> getEmployees() {
        return employees;
    }
}
