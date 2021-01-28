package Application.Resources;

public class Waiter extends Employee {

    private int Manager_ID;

    public Waiter (int id, String first_name, String last_name, String username,
            String password, String email, String mobile_number, Double salary, int manager_id)
    {
        this.id = id;
        this.Manager_ID = manager_id;
        this.Email = email;
        this.First_Name = first_name;
        this.Salary = salary;
        this.Last_Name = last_name;
        this.Password = password;
        this.UserName = username;
        this.Mobile_Number = mobile_number;
    }

    public int getManager_ID() {
        return Manager_ID;
    }
}
