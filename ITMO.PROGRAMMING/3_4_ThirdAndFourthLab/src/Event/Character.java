package Event;

import java.util.ArrayList;

abstract public class Character {
    protected Location location;
    protected int age;
    protected String name;
    protected int energy = (int)(Math.random()*10)+1;
    protected int strength = (int)(Math.random()*10)+1;
    protected double luck = Math.random();
    protected ArrayList<Item> inventory = new ArrayList<>();
    public Character(Location location, int age, String name, int energy, int strength, double luck) {
        this.location = location;
        this.age = age;
        this.name = name;
        this.energy = energy;
        this.strength = strength;
        this.luck = luck;
    }
    public Character(Location location, int age, String name) {
        this.location = location;
        this.age = age;
        this.name = name;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public Location getLocation() {
        return location;
    }
    protected void setLocation(Location location) {
        this.location = location;
    }
    public int getAge() {
        return age;
    }
    protected void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    protected void setName(String name) {
        this.name = name;
    }
    public int getEnergy() {
        return energy;
    }
    protected void setEnergy(int energy) {
        this.energy = energy;
    }
    public int getStrength() {
        return strength;
    }
    protected void setStrength(int strength) {
        this.strength = strength;
    }
    public double getLuck() {
        return luck;
    }
    protected void setLuck(double luck) {
        this.luck = luck;
    }
    public boolean move(Location location){
        PathResult path = PathFinder.findShortestPath(this.getLocation(), location);
        if (path.totalCost() != -1 && path.totalCost() <= this.getEnergy()) {
            this.setLocation(path.path().get(path.path().size() - 1));
            this.setEnergy(this.getEnergy() - path.totalCost());
            return true;
        }else{
            return false;
        }
    }
    public boolean move(){
        PathResult path = PathFinder.findLongestPath(this.getLocation(),this.getEnergy());
        this.setLocation(path.path().get(path.path().size() - 1));
        this.setEnergy(this.getEnergy() - (path.totalCost() - path.path().get(0).getEnergyCost()));
        return true;
    }
}
