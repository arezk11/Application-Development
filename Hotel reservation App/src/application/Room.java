package application;

import javafx.beans.property.*;

public class Room {
    private final IntegerProperty roomId;
    private final StringProperty roomType;
    private final DoubleProperty rate;
    private final BooleanProperty available;

    public Room(int roomId, String roomType, double rate, boolean available) {
        this.roomId = new SimpleIntegerProperty(roomId);
        this.roomType = new SimpleStringProperty(roomType);
        this.rate = new SimpleDoubleProperty(rate);
        this.available = new SimpleBooleanProperty(available);
    }

    public int getRoomId() {
        return roomId.get();
    }

    public void setRoomId(int roomId) {
        this.roomId.set(roomId);
    }

    public IntegerProperty roomIdProperty() {
        return roomId;
    }

    public String getRoomType() {
        return roomType.get();
    }

    public void setRoomType(String roomType) {
        this.roomType.set(roomType);
    }

    public StringProperty roomTypeProperty() {
        return roomType;
    }

    public double getRate() {
        return rate.get();
    }

    public void setRate(double rate) {
        this.rate.set(rate);
    }

    public DoubleProperty rateProperty() { 
        return rate;
    }

    public boolean isAvailable() {
        return available.get();
    }

    public void setAvailable(boolean available) {
        this.available.set(available);
    }

    public BooleanProperty availableProperty() {
        return available;
    }
}
