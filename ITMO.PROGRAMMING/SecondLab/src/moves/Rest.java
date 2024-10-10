package moves;
import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove {
    public Rest() {  // конструктор класса Rest в суперкласс передается тип атаки, урон и точность попадания
        super(Type.PSYCHIC, 0, 0);
    }
    protected boolean checkAccuracy(Pokemon att, Pokemon def) {  // Атака не наносит урона, но всегда накладывает эффект на атакующего покемона
        return true;
    }
    @Override
    protected void applySelfEffects(Pokemon p){  // Полное восстановление здоровья покемона
        Effect.sleep(p);

        p.setMod(Stat.HP, (int)Math.ceil(p.getHP() - p.getStat(Stat.HP)));

    }
    protected String describe(){  // Сообщение выводимое при использовании атаки
        return "использует Rest";
    }
}