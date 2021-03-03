package Application.Resources;

import Application.Model.ManagerDashBoardModel;
import Application.Model.WaiterDashBoardModel;

import java.sql.*;

public class Waiter extends Employee {

    private int Manager_ID;

    public Waiter (int id, String first_name, String last_name, String username, int age, java.sql.Date birthdate,
                   String password, String mobile_number, Double salary, int manager_id, Blob image)
    {
        this.ID = id;
        this.Age =age;
        this.Birthdate = birthdate;
        this.Manager_ID = manager_id;
        this.Profile_Image = image;
        this.First_Name = first_name;
        this.Salary = salary;
        this.Last_Name = last_name;
        this.Password = password;
        this.Username = username;
        this.Mobile_Number = mobile_number;
    }

    public int getManager_ID() {
        return Manager_ID;
    }
}
