package Event;

import java.util.Objects;

public class Item {
    private Location location;
    private String name;
    private int durability;
    public Item(Location location, String name, int durability) {
        this.location = location;
        this.name = name;
        this.durability = durability;
    }
    public Location getLocation() {
        return location;
    }
    public String getName() {
        return name;
    }
    public int getDurability() {
        return durability;
    }
    protected void setLocation(Location location) {
        this.location = location;
    }
    protected void setDurability(int durability) {
        this.durability = durability;
    }

    @Override
    public String toString() {
        return "Item{" +
                "location=" + location +
                ", name='" + name + '\'' +
                ", durability=" + durability +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return getDurability() == item.getDurability() && Objects.equals(getLocation(), item.getLocation()) && Objects.equals(getName(), item.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLocation(), getName(), getDurability());
    }
}
