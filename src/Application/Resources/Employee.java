package Application.Resources;

import java.sql.Blob;
import java.sql.Date;

public abstract class Employee {

    protected int           ID,
                            Age;
    protected Blob          Profile_Image;
    protected String        First_Name,
                            Last_Name,
                            Username,
                            Password,
                            Mobile_Number;
    protected Double        Salary;
    protected java.sql.Date Birthdate;

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setSalary(Double salary) {
        Salary = salary;
    }

    public void setAge(int age) {
        Age = age;
    }

    public void setMobile_Number(String mobile_Number) {
        Mobile_Number = mobile_Number;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setBirthdate(Date birthdate) {
        Birthdate = birthdate;
    }

    public void setProfile_Image(Blob profile_Image) {
        Profile_Image = profile_Image;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public int getID() {
        return ID;
    }

    public String getMobile_Number() {
        return Mobile_Number;
    }

    public int getAge() {
        return Age;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public Double getSalary() {
        return Salary;
    }

    public String getPassword() {
        return Password;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public Blob getProfile_Image() {
        return Profile_Image;
    }

    public Date getBirthdate() {
        return Birthdate;
    }

    public String getUsername() {
        return Username;
    }

    public String FullName() {
        return First_Name + " " + Last_Name;
    }
}
