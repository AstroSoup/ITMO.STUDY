package pokemons;
import moves.*;
import ru.ifmo.se.pokemon.*;

public class Clefairy extends Cleffa {
    public Clefairy(String name,int lvl) {
        super(name, lvl);
        setStats(70,45,48,60,65,35);
        setType(Type.FAIRY);
        addMove(new MeteorMash());
    }
}