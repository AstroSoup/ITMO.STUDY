import Event.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Location start = new Location("Комната",1);
        Location verevka = new Location("Верёвка", 1);
        Location stairs = new Location("Лестница", 2);
        Location dvor = new Location("Двор", 3);
        Location dvor2 = new Location("Проходной двор", 3);
        Location dvor3 = new Location("Проспект лунатиков", 3);
        Location street = new Location("Улица сыра", 4);
        Container shkaf = new Container(start, "Стеклянный шкаф",1);
        SmallItem spacesuit = new SmallItem(start, "Незнайкин скафандр", 1);
        shkaf.addToInv(spacesuit);
        start.addNeighbor(verevka);
        start.addNeighbor(stairs);
        start.addNeighbor(dvor);
        verevka.addNeighbor(dvor);
        dvor.addNeighbor(dvor2);
        dvor2.addNeighbor(dvor3);
        dvor3.addNeighbor(street);
        start.addToInv(shkaf);
        ArrayList<Shorty> shorties = new ArrayList<>();
        Shorty lulich = new Shorty(start,11,"Коротышка");

        shorties.add(lulich);
        MainCharacter Neznayka = new MainCharacter(dvor,10,"Незнайка");
        MainCharacter Kozlik = new MainCharacter(dvor,10,"Козлик");
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(start);
        Story str = new Story(shorties,Neznayka,Kozlik,locations);
        str.go();
    }
}