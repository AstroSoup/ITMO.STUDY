package moves;
import ru.ifmo.se.pokemon.*;

public class FocusBlast extends PhysicalMove {
    public FocusBlast() {  // конструктор класса FocusBlast в суперкласс передается тип атаки, урон и точность попадания
        super(Type.FIGHTING, 120, 70);
    }
    @Override
    protected void applyOppEffects(Pokemon p) {  // Уменьшение модификатора характеристики SPECIAL_DEFENCE на 1 с шансом 10%
        if (Math.random() <= 0.1) {
            Effect checkSpecialDefence = new Effect();
            p.addEffect(checkSpecialDefence);
            p.setMod(Stat.SPECIAL_DEFENSE, checkSpecialDefence.stat(Stat.SPECIAL_DEFENSE) - 1);
        }
    }
    @Override
    protected String describe(){  // Сообщение выводимое при использовании атаки
        return "использует Focus Blast";
    }
}