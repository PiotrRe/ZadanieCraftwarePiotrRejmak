import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.sql.SQLException;


@Path("/getreservation/{param1}")
public class ReservationGetter {

    @GET
    @Produces("application/JSON")
    public String getReservations(@PathParam("param1") String param1) throws SQLException {
        DbManager manager = new DbManager();

        int placeNumber = Integer.parseInt(param1);
        return manager.getReservation(placeNumber);
    }
}