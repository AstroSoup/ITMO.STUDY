package moves;
import ru.ifmo.se.pokemon.*;

public class ShadowBall extends SpecialMove{
    public ShadowBall() {  // конструктор класса ShadowBall в суперкласс передается тип атаки, урон и точность попадания
        super(Type.GHOST, 80, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {  // Cнижение характеристики Special Defence с шансом 20%
        if (Math.random() <= 0.2) {
            Effect checkSpecialDefence = new Effect();
            p.addEffect(checkSpecialDefence);
            p.setMod(Stat.SPECIAL_DEFENSE, checkSpecialDefence.stat(Stat.SPECIAL_DEFENSE) - 1);
        }
    }

    @Override
    protected String describe(){  // Сообщение выводимое при использовании атаки
        return "использует Shadow Ball";
    }
}