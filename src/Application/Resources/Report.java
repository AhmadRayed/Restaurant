package Application.Resources;

public class Report {
    private int ID;
    private String Activity;

    public Report(int ID, String Activity) {
        this.ID = ID;
        this.Activity = Activity;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }
}
