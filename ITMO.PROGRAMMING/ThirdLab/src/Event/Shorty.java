package Event;

import java.util.ArrayList;
import java.util.Objects;

import static Event.RageLvl.CALM;

public class Shorty extends Character implements Storage{
    protected RageLvl calmness = CALM;
    public Shorty(Location location, int age, String name, int energy, int strength, double luck){
        super(location, age, name, energy, strength, luck);
    }
    public Shorty(Location location, int age, String name){
        super(location, age, name);
    }
    public boolean searchForItem(Item item){
        ArrayList<Item> inv = this.getLocation().getInventory();
        if (inv.contains(item)){
            return true;
        }else{
            this.setCalmness(RageLvl.toRageLvl(this.getCalmness().toNum()+1));
            this.setEnergy(this.getEnergy()*this.getCalmness().toNum());
            this.setStrength(this.getStrength()*this.getCalmness().toNum());
            return false;
        }
    }

    public void take(Location container, Item item) throws ItemNotFoundException{
        if (container.getInventory().contains(item)){
            container.removeFromInv(item);
            this.addToInv(item);

        }else{
            throw new ItemNotFoundException(item.getName());
        }
    }

    public void destroy(Item item) throws ItemNotFoundException{
        if (this.getLocation().getInventory().contains(item)) {
            item.setDurability(item.getDurability() - this.getStrength());
            if (item.getDurability() <= 0){
                System.out.println(this.getName() + " сломал " + item.getName() + (this.getCalmness().equals(CALM) ? "" : " в ярости"));
                if (item instanceof Container container){
                    for (Item elem : container.getInventory()) {
                        System.out.println(this.getName() + " вытащил " + elem.getName());
                        this.getLocation().addToInv(elem);
                    }
                }
                this.getLocation().removeFromInv(item);
            }
        }else{
            throw new ItemNotFoundException(item.getName());
        }
    }

    public RageLvl getCalmness() {
        return calmness;
    }
    protected void setCalmness(RageLvl calmness) {
        this.calmness = calmness;
    }

    @Override
    public String toString() {
        return "Shorty{" +
                "location=" + location.getName() +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", energy=" + energy +
                ", strength=" + strength +
                ", luck=" + luck +
                ", inventory=" + inventory +
                ", calmness=" + calmness +
                "} ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shorty shorty = (Shorty) o;
        return getCalmness() == shorty.getCalmness() && shorty.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCalmness()) + Objects.hashCode(getName());
    }
}
