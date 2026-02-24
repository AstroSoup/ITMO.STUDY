package pokemons;
import moves.*;
import ru.ifmo.se.pokemon.*;

public class Bouffalant extends Pokemon{
    public Bouffalant(String name,int lvl){
        super(name,lvl);
        setStats(95,110,95,40,95,55);
        setType(Type.NORMAL);
        setMove(new AerialAce(),new SmartStrike(),new RockSlide(),new Rest());
    }
}