package dateTest;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author FeniksBV
 */
public class DateTest {

    public static void main(String[] args) {
        Date newDate = new Date();
        System.out.println("Datum = " + newDate.toString());
        System.out.println("SQL = " + getSQLDate(newDate));
    }

    protected static Timestamp getSQLDate(java.util.Date date) {
        return new java.sql.Timestamp(date.getTime());

    }
}
