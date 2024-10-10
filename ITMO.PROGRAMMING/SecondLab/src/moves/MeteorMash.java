package moves;
import ru.ifmo.se.pokemon.*;

public class MeteorMash extends PhysicalMove {
    public MeteorMash() {  // конструктор класса MeteorMash в суперкласс передается тип атаки, урон и точность попадания
        super(Type.STEEL, 90, 90);
    }
    @Override
    protected void applySelfEffects(Pokemon p) {  // Увеличение модификатора характеристики ATTACK на 1 с шансом 20%
        if (Math.random() <= 0.2) {
            Effect checkAttack = new Effect();
            p.addEffect(checkAttack);
            p.setMod(Stat.ATTACK, checkAttack.stat(Stat.ATTACK) + 1);
        }
    }
    @Override
    protected String describe(){  // Сообщение выводимое при использовании атаки
        return "использует Meteor Mash";
    }
}