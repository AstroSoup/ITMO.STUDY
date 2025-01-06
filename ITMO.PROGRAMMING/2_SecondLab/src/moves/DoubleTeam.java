package moves;
import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove {
    public DoubleTeam() {  // конструктор класса DoubleTeam в суперкласс передается тип атаки, урон и точность попадания
        super(Type.NORMAL, 0, 0);
    }

    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def) {  // Атака не наносит урона, но всегда накладывает эффект на атакующего покемона
        return true;
    }

    @Override
    protected void applySelfEffects(Pokemon p) {  // Увеличение модификатора характеристики Evasion на 1
        Effect checkEvasion = new Effect();
        p.addEffect(checkEvasion);
        p.setMod(Stat.EVASION,checkEvasion.stat(Stat.EVASION) + 1);
    }

    @Override
    protected String describe(){  // Сообщение выводимое при использовании атаки
        return "использует Double Team";
    }
}