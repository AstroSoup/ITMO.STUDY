package pokemons;
import moves.*;
import ru.ifmo.se.pokemon.*;
public class Clefable extends Clefairy {
    public Clefable(String name,int lvl) {
        super(name, lvl);
        setStats(95,70,73,95,90,60);
        setType(Type.FAIRY);
        addMove(new Facade());
    }
}