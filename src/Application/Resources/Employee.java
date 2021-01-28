package Application.Resources;

public abstract class Employee {

    protected int       id;
    protected String    First_Name,
                        Last_Name,
                        UserName,
                        Password,
                        Email,
                        Mobile_Number;
    protected Double    Salary;

    public int getId () {
        return id;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public String getPassword() {
        return Password;
    }

    public String getUserName() {
        return UserName;
    }

    public Double getSalary() {
        return Salary;
    }

    public String getEmail() {
        return Email;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public String getMobile_Number() {
        return Mobile_Number;
    }

    public void setUserName (String userName) {
        UserName = userName;
    }

    public void setId (int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setMobile_Number(String mobile_Number) {
        Mobile_Number = mobile_Number;
    }

    public void setSalary(Double salary) {
        Salary = salary;
    }

    public String FullName() {
        return First_Name + " " + Last_Name;
    }
}
