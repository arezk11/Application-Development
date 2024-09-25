package model;

public class Xylophone extends PercussionFamily {
    @Override
    public String howToPlay() {
        return "with two mallets";
    }

    @Override
    public String howToFix() {
        return "replace bars";
    }

    @Override
    public String getPitchType() {
        return "Each bar produces different pitch";
    }

    @Override
    public String makeSound() {
        return "through resonators";
    }

    @Override
    public String toString() {
        return "Xylophone";
    }
}
