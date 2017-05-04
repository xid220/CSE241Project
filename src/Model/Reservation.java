package Model;

import java.sql.ResultSet;

/**
 * Created by MarkSchatzman on 4/29/17.
 */
public class Reservation {
    private String cust_id;
    private String startDate;
    private String endDate;
    private String vin;

    public Reservation(String cust_id, String startDate, String endDate, String vin){
        this.cust_id = cust_id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.vin = vin;
    }

    public void insert() {
        DBConnection.insertTuple("insert into reservation(id, start_date, end_date, vin) values('" + getCust_id() +
                "', "  + "to_date('" + getStartDate() + "', 'yyyy/mm/dd'), " +
                "to_date('" + getEndDate() + "', 'yyyy/mm/dd'), " + getVin() + ")");
    }

    public static void showCustomerHistory(String id){
        String pickup;
        String dropoff;
        String year;
        String make;
        String model;
        ResultSet rs = DBConnection.getTuple("select start_date, end_date, year, make, model from customer natural join vehicle natural join reservation " +
                "where id = " + id);
        try {
            if (!rs.isBeforeFirst()) {
                System.out.println("You have not made a reservation with us before. You're missing out on a painfully bad experience!");
                return;
            }
            System.out.printf("%-8s%-15s%-15s%-8s%-15s%-15s\n", "ResNo", "Pickup Date", "Dropoff Date", "Year", "Make", "Model");
            System.out.printf("%-8s%-15s%-15s%-8s%-15s%-15s\n", "-----", "-----------", "------------ ", "----", "----", "-----");
            int i = 1;
            while (rs.next()) {
                pickup = rs.getString("start_date");
                dropoff = rs.getString("end_date");
                year = rs.getString("year");
                make = rs.getString("make");
                model = rs.getString("model");
                System.out.printf("%-8s%-15s%-15s%-8s%-15s%-15s\n", i++ + ": ", pickup.split(" ")[0], dropoff.split(" ")[0], year, make, model);
            }
        }
        catch(java.sql.SQLException ex){
            DBConnection.close();
        }
    }

    public String getCust_id() {
        return cust_id;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getVin() {
        return vin;
    }
}
