package Event;

import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.Objects;

public class MainCharacter extends Character implements Storage{
    public MainCharacter(Location location, int age, String name, int energy, int strength, double luck){
        super(location, age, name, energy, strength, luck);
    }
    public MainCharacter(Location location, int age, String name){
        super(location, age, name);
        this.setEnergy(this.getEnergy()*3);
    }

    @Override
    public String toString() {
        return "MainCharacter{" +
                "location=" + location +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", luck=" + luck +
                ", inventory=" + inventory +
                ", strength=" + strength +
                ", energy=" + energy +
                "} ";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MainCharacter mc = (MainCharacter) o;
        return mc.getName().equals(this.getName());
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}
