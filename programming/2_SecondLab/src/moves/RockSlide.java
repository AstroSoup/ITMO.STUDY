package moves;
import ru.ifmo.se.pokemon.*;

public class RockSlide extends PhysicalMove {
    public RockSlide() {  // конструктор класса RockSlide в суперкласс передается тип атаки, урон и точность попадания
        super(Type.ROCK, 75, 90);
    }
    @Override
    protected void applyOppEffects(Pokemon p){  // накладывает эффект страха(flinch) на противника с шансом 30%
        if (Math.random() <= 0.3)   Effect.flinch(p);
    }
    @Override
    protected String describe(){  // Сообщение выводимое при использовании атаки
        return "использует Rock Slide";
    }
}