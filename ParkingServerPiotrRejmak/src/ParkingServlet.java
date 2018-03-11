import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "ParkingServlet")
public class ParkingServlet extends HttpServlet {
    private ReservationManager reservationManager = new ReservationManager();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        JsonParser parser = new JsonParser();
        JsonObject jsonobject = (JsonObject) parser.parse(sb.toString());

        String firstName = jsonobject.get("firstname").getAsString();
        String lastName = jsonobject.get("lastname").getAsString();
        Date startDate = Date.valueOf(jsonobject.get("startdate").getAsString());
        Date endDate = Date.valueOf(jsonobject.get("enddate").getAsString());
        int phoneNumber = jsonobject.get("phonenumber").getAsInt();
        int placeNumber = jsonobject.get("placenumber").getAsInt();


        Reservation reservation = new Reservation(firstName, lastName, startDate, endDate, placeNumber, phoneNumber);


        int code = 200;
        String message = "";

        try {
            boolean success = reservationManager.addReservation(reservation);
            if (!success) {
                code = 500;
                message = "Miejsce parkingowe dla tej daty jest już zajęte. Proszę wprowadzić inną datę lub wybrać inne miejsce.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            code = 500;
            message = "Błąd bazy danych.";
        }


        response.setStatus(code);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{ \"message\": \"" + message + "\" }");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
