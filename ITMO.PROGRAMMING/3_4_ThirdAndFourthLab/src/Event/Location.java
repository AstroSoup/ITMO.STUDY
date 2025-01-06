package Event;

import java.util.ArrayList;

public class Location implements Storage{
    protected String name;
    protected int energyCost;
    protected ArrayList<Item> inventory = new ArrayList<>();
    protected ArrayList<Location> neighbors = new ArrayList<>();
    public Location(String name, int energyCost, ArrayList<Item> inventory, ArrayList<Location> neighbors) {
        this.name = name;
        this.energyCost = energyCost;
        this.inventory = inventory;
        this.neighbors = neighbors;
    }
    public Location(String name, int energyCost) {
        this.name = name;
        this.energyCost = energyCost;
    }
    public void addToInv(Item item) {
        inventory.add(item);
    }
    public Item removeFromInv(Item item) {
        inventory.remove(item);
        return item;
    }
    public void addNeighbor(Location neighbor) {
        neighbors.add(neighbor);
    }
    public Location removeNeighbor(Location neighbor) {
        neighbors.remove(neighbor);
        return neighbor;
    }
    public String getName() {
        return name;
    }
    public int getEnergyCost() {
        return energyCost;
    }
    public ArrayList<Item> getInventory() {
        return inventory;
    }
    public ArrayList<Location> getNeighbors() {
        return neighbors;
    }
}
