package pokemons;
import moves.*;
import ru.ifmo.se.pokemon.*;

public class Cleffa extends Pokemon {
    public Cleffa(String name,int lvl) {
        super(name, lvl);
        setStats(50,25,28,45,55,15);
        setType(Type.FAIRY);
        setMove(new Rest(),new ShadowBall());
    }
}