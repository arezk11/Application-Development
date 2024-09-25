package model;

public class Flute extends WoodwindFamily {
    @Override
    public String howToPlay() {
        return "by blowing into the flute";
    }

    @Override
    public String howToFix() {
        return "N/A: It cannot be fixed";
    }

    @Override
    public String getPitchType() {
        return "Fundamental pitch is middle C";
    }

    @Override
    public String makeSound() {
        return "guiding a stream of air";
    }
    
    @Override
    public String toString() {
        return "Flute";
    }
}
