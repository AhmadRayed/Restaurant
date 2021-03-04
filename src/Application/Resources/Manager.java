package Application.Resources;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Manager extends Employee{
    private int admin;

    private List <Employee> employees = new ArrayList<>();

    public Manager(int id, String first_name, String last_name, String username,  int age, java.sql.Date birthdate,
                   String password, String mobile_number, Double salary, int admin, Blob image)
    {
        this.ID = id;
        this.Age =age;
        this.Birthdate = birthdate;
        this.Profile_Image = image;
        this.First_Name = first_name;
        this.Salary = salary;
        this.Last_Name = last_name;
        this.Password = password;
        this.Username = username;
        this.Mobile_Number = mobile_number;
        this.admin = admin;
    }

    public int getAdmin() {
        return admin;
    }

    public boolean updateEmployee (Employee e, String Option, String NewValue, boolean W)
    {
        String UPDATE_QUERY;
        String CALL_PROCEDURE;
        for (Employee X :
                employees) {
            if (e == X) {
                if (W == true || admin == 0)
                    CALL_PROCEDURE = "{CALL sp_Edit_Waiter(?,?,?)}";
//                    UPDATE_QUERY = "UPDATE `waiter_Table` SET `"+ Option +"` = '"+ NewValue +"' WHERE `waiter_Table`.id = '"+ e.getID() +"'";
                else
                    CALL_PROCEDURE = "{CALL sp_Edit_Manager(?,?,?)}";
//                    UPDATE_QUERY = "UPDATE `manager_Table` SET `"+ Option +"` = '"+ NewValue+"' WHERE `manager_Table`.id = '"+ e.getID() +"'";
//                MySqlConnection.MakeConnection().executeQuery(UPDATE_QUERY, "ERROR in QUERY");
                try {
                    CallableStatement callableStatement = MySqlConnection.MakeConnection().getConnection().prepareCall(CALL_PROCEDURE);
                    callableStatement.setInt(1, e.getID());
                    callableStatement.setString(2, Option);
                    callableStatement.setString(3, NewValue);

                    callableStatement.execute();
                } catch (SQLException err){
                    err.printStackTrace();
                    System.err.println(err.getMessage());
                }
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
