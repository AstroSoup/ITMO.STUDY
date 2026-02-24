import Event.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Location start = new Location("Комната",1);
        Location rope = new Location("Верёвка", 1);
        Location stairs = new Location("Лестница", 2);
        Location yard = new Location("Детская площадка", 3);
        Location throughfare = new Location("двор, на их счастье оказавшийся проходным", 3);
        Location avenue = new Location("Проспект лунатиков", 3);
        Location street = new Location("Улица сыра", 4);
        Container wardrobe = new Container(start, "Стеклянный шкаф",1);
        SmallItem spacesuit = new SmallItem(start, "Незнайкин скафандр", 1);
        wardrobe.addToInv(spacesuit);
        start.addNeighbor(rope);
        start.addNeighbor(stairs);
        rope.addNeighbor(yard);
        stairs.addNeighbor(yard);
        yard.addNeighbor(throughfare);
        throughfare.addNeighbor(avenue);
        avenue.addNeighbor(street);
        start.addToInv(wardrobe);
        ArrayList<Shorty> shorties = new ArrayList<>();
        Shorty shorty = new Shorty(start,11,"Коротышка");

        shorties.add(shorty);
        MainCharacter Neznayka = new MainCharacter(yard,10,"Незнайка");
        MainCharacter Kozlik = new MainCharacter(yard,10,"Козлик");
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(start);
        Story str = new Story(shorties,Neznayka,Kozlik,locations);
        str.go();
    }
}