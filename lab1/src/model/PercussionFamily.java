package model;

public abstract class PercussionFamily extends MusicalInstrument {
    @Override
    public String makeSound() {
        return "vibrating stretched membrane or resonators";
    }
}
