import java.sql.Date;

public class Reservation {

    private String firstName;
    private String lastName;
    private int phoneNumber;
    private Date startDate;
    private Date endDate;
    private int placeNumber;

    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public int getPhoneNumber()  { return phoneNumber; }
    public Date getStartDate()   { return startDate; }
    public Date getEndDate()     { return endDate; }
    public int getPlaceNumber()  { return placeNumber; }

    public Reservation(String firstNameParam, String lastNameParam, Date startDateParam, Date endDateParam, int placeNumberParam, int phoneNumberParam){
        firstName = firstNameParam;
        lastName = lastNameParam;
        startDate = startDateParam;
        endDate = endDateParam;
        placeNumber = placeNumberParam;
        phoneNumber = phoneNumberParam;
    }
}
