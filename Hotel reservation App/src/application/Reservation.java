package application;

import java.util.Date;

public class Reservation {
    private int bookingID;
    private int guestID;
    private int roomID;
    private Date checkIn;
    private Date checkOut;

    public Reservation(int bookingID, int guestID, int roomID, Date checkIn, Date checkOut) {
        this.bookingID = bookingID;
        this.guestID = guestID;
        this.roomID = roomID;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    // Getters and setters
    public int getBookingID() { return bookingID; }
    public void setBookingID(int bookingID) { this.bookingID = bookingID; }
    public int getGuestID() { return guestID; }
    public void setGuestID(int guestID) { this.guestID = guestID; }
    public int getRoomID() { return roomID; }
    public void setRoomID(int roomID) { this.roomID = roomID; }
    public Date getCheckIn() { return checkIn; }
    public void setCheckIn(Date checkIn) { this.checkIn = checkIn; }
    public Date getCheckOut() { return checkOut; }
    public void setCheckOut(Date checkOut) { this.checkOut = checkOut; }
}
