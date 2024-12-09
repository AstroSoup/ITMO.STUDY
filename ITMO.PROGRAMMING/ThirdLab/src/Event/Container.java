package Event;

import java.util.ArrayList;

public class Container extends Item implements Storage{
    protected ArrayList<Item> inventory = new ArrayList<>();
    public Container(Location location, String name, int durability){
        super(location, name, durability);
    }
    public Container(Location location, String name, int durability, ArrayList<Item> inventory){
        super(location, name, durability);
        this.inventory = inventory;
    }
    public ArrayList<Item> getInventory() {
        return inventory;
    }
}
