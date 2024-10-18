package moves;
import ru.ifmo.se.pokemon.*;

public class Confide extends StatusMove{
    public Confide(){  // конструктор класса Confide в суперкласс передается тип атаки, урон и точность попадания
        super(Type.NORMAL,0,0);
    }
    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def) {  // Атака игнорирует значения Evasion и Accuracy тем самым попадая всегда
        return true;
    }
    @Override
    protected void applyOppEffects(Pokemon p) {  // Уменьшение модификатора характеристики SPECIAL_ATTACK на 1
        Effect checkSpecialAttack = new Effect();
        p.addEffect(checkSpecialAttack);
        p.setMod(Stat.SPECIAL_ATTACK, checkSpecialAttack.stat(Stat.SPECIAL_ATTACK) - 1);
    }
    @Override
    protected String describe(){  // Сообщение выводимое при использовании атаки
        return "использует Confide";
    }
}