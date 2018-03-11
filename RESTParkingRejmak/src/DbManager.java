import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbManager {

    DbManager() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getReservation(int placeNumber) throws SQLException {
        Connection connection = (Connection) DriverManager
                .getConnection("jdbc:mysql://isp.linux.pl:3306/c428_rejmak", "c428_rejmak", "testtest1234");

        String checkQuery = String.join("\n",
                "SELECT START_DATE, END_DATE, PHONE_NUMBER, FIRSTNAME, LASTNAME FROM SPOT_RESERVATION",
                "INNER JOIN PARKING_SPOT ON SPOT_RESERVATION.PARKING_SPOT_ID = PARKING_SPOT.PARKING_SPOT_ID",
                "WHERE PARKING_SPOT.SPOT_NUMBER = ?");
        PreparedStatement statement = (PreparedStatement) connection.prepareStatement(checkQuery);
        statement.setInt(1, placeNumber);

        ResultSet resultSet = statement.executeQuery();
        ResultSetMetaData md = (ResultSetMetaData) resultSet.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();

        while (resultSet.next()) {


            Map<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), resultSet.getObject(i));
            }
            rows.add(row);
        }

        String data;
        data = rows.toString();

        connection.close();

        return data;
    }
}
