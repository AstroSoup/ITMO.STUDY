package Event;

import java.util.ArrayList;

public interface Storage {


    default void addToInv(Item item){
        if (item == null) {
            throw new IllegalArgumentException("Нельзя добавить пустой предмет в инвентарь.");
        }
        getInventory().add(item);
    }


    default Item removeFromInv(Item item){
        if (!getInventory().contains(item)) {
            throw new IllegalArgumentException("Предмет не найден в инвентаре.");
        }
        getInventory().remove(item);
        return item;
    }

    ArrayList<Item> getInventory();
}
