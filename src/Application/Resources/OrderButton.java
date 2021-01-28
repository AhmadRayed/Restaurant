package Application.Resources;

public class OrderButton {

    private Table table;
    private int current;

    public OrderButton(Table table, int current)
    {
        this.current = current;
        this.table = table;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getCurrent() {
        return current;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }
}
