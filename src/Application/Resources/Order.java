package Application.Resources;

import java.sql.Date;
import java.sql.Time;

public class Order {
    private int     order_ID,
                    table_ID,
                    waiter_ID,
                    current;
    private double  Total;

    private java.sql.Date createdDate;
    private java.sql.Time createdTime;

    public Order (int order_ID, int table_ID, int waiter_ID, int current,
                 java.sql.Date createdDate, java.sql.Time createdTime){

        this.order_ID = order_ID;
        this.table_ID = table_ID;
        this.waiter_ID = waiter_ID;
        this.current = current;
        this.createdTime = createdTime;
        this.createdDate = createdDate;
    }

    public void setOrder_ID(int order_ID) {
        this.order_ID = order_ID;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setTable_ID(int table_ID) {
        this.table_ID = table_ID;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setWaiter_ID(int waiter_ID) {
        this.waiter_ID = waiter_ID;
    }

    public void setCreatedTime(Time createdTime) {
        this.createdTime = createdTime;
    }

    public int getCurrent() {
        return current;
    }

    public int getOrder_ID() {
        return order_ID;
    }

    public int getTable_ID() {
        return table_ID;
    }

    public int getWaiter_ID() {
        return waiter_ID;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Time getCreatedTime() {
        return createdTime;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public double getTotal() {
        return Total;
    }
}