package pokemons;
import moves.*;
import ru.ifmo.se.pokemon.*;

public class Lickitung extends Pokemon {
    public Lickitung(String name,int lvl) {
        super(name, lvl);
        setStats(90,55,75,60,75,30);
        setType(Type.NORMAL);
        setMove(new DoubleTeam(),new Facade(),new Confide());
    }
}