package model;

public abstract class MusicalInstrument implements IFixable, IPlayable, Comparable<MusicalInstrument> {
    protected double price;

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    // The compareTo method to compare by price
    @Override
    public int compareTo(MusicalInstrument other) {
        return Double.compare(other.price, this.price);
    }

    // Abstract method for getting the name of the instrument
    public abstract String toString();
}
