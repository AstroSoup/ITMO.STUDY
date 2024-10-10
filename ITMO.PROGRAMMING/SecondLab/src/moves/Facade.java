package moves;
import ru.ifmo.se.pokemon.*;

public class Facade extends PhysicalMove{
    private boolean isDamageDoubled;
    public Facade() {  // конструктор класса Facade в суперкласс передается тип атаки, урон и точность попадания
        super(Type.NORMAL, 70, 100);
    }
    @Override
    protected void applySelfEffects(Pokemon p) {  // Проверка на то горит/отравлен/парализован ли атакующий покемон
        if (p.getCondition() == Status.BURN || p.getCondition() == Status.PARALYZE || p.getCondition() == Status.POISON) {
            this.isDamageDoubled = true;
        }
    }

    @Override
    protected void applyOppDamage(Pokemon def, double damage) {  // Если атакующий покемон горит/отравлен/парализован урон от атаки увеличивается в 2 раза
        if (isDamageDoubled) def.setMod(Stat.HP, (int) Math.round(damage * 2));
        else def.setMod(Stat.HP, (int) Math.round(damage));
    }
    @Override
    protected String describe(){  // Сообщение выводимое при использовании атаки
        return "использует Facade";
    }
}