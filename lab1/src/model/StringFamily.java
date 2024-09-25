package model;

public abstract class StringFamily extends MusicalInstrument {
    @Override
    public String makeSound() {
        return "vibrating strings";
    }
}
