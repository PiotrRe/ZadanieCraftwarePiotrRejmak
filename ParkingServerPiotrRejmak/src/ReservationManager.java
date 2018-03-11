import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationManager {
    ReservationManager() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean addReservation(Reservation reservation) throws SQLException {

        Connection connection = (Connection) DriverManager
                .getConnection("jdbc:mysql://isp.linux.pl:3306/c428_rejmak", "c428_rejmak", "testtest1234");

        ResultSet resultSet = getReservationsDateForPlaceNumber(reservation.getPlaceNumber(), connection);

        while (resultSet.next()) {
            Date startDateFromDB = Date.valueOf(resultSet.getString("START_DATE"));
            Date endDateFromDB = Date.valueOf(resultSet.getString("END_DATE"));

            if(startDateFromDB.compareTo(reservation.getEndDate())<=0 && endDateFromDB.compareTo(reservation.getStartDate())>=0 ){
                //dates overlap
                return false;
            }

        }

        insertToDatabase(reservation, connection);

        connection.close();
        return true;
    }

    private ResultSet getReservationsDateForPlaceNumber(int placeNumber, Connection connection) throws SQLException {
        String checkQuery = String.join("\n",
                "SELECT START_DATE, END_DATE FROM SPOT_RESERVATION",
                "INNER JOIN PARKING_SPOT ON SPOT_RESERVATION.PARKING_SPOT_ID = PARKING_SPOT.PARKING_SPOT_ID",
                "WHERE PARKING_SPOT.SPOT_NUMBER = ?");
        PreparedStatement statement = (PreparedStatement) connection.prepareStatement(checkQuery);
        statement.setInt(1, placeNumber);
        return statement.executeQuery();
    }

    private void insertToDatabase(Reservation reservation, Connection connection) throws SQLException {

        String insertQuery = String.join("\n",
                "INSERT INTO SPOT_RESERVATION(FIRSTNAME, LASTNAME, START_DATE, END_DATE,PHONE_NUMBER, PARKING_SPOT_ID)",
                "VALUES(?,?,?,?,?,(SELECT PARKING_SPOT_ID FROM PARKING_SPOT where SPOT_NUMBER = ?))");

        PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(insertQuery);
        preparedStatement.setString (1,  reservation.getFirstName());
        preparedStatement.setString (2,  reservation.getLastName());
        preparedStatement.setDate   (3,  reservation.getStartDate());
        preparedStatement.setDate   (4,  reservation.getEndDate());
        preparedStatement.setInt    (5,  reservation.getPhoneNumber());
        preparedStatement.setInt    (6,  reservation.getPlaceNumber());

        preparedStatement.execute();
    }
}