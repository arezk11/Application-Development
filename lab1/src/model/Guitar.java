package model;

public class Guitar extends StringFamily {
    @Override
    public String howToPlay() {
        return "by strumming the strings";
    }

    @Override
    public String howToFix() {
        return "replace the strings";
    }

    @Override
    public String makeSound() {
        return "vibrating strings";
    }
    
    @Override
    public String getPitchType() {
        return "low to high pitch";
    }

    @Override
    public String toString() {
        return "Guitar";
    }
}
