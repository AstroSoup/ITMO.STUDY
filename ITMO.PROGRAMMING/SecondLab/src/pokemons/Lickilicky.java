package pokemons;
import moves.*;
import ru.ifmo.se.pokemon.*;

public class Lickilicky extends Lickitung {
    public Lickilicky(String name,int lvl) {
        super(name, lvl);
        setStats(110,85,95,80,95,50);
        setType(Type.NORMAL);
        addMove(new FocusBlast());
    }
}