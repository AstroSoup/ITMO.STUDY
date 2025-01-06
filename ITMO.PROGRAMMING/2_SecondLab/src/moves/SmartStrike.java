package moves;
import ru.ifmo.se.pokemon.*;


public class SmartStrike extends PhysicalMove{
    public SmartStrike() {  // конструктор класса SmartStrike в суперкласс передается тип атаки, урон и точность попадания
        super(Type.STEEL,70, Long.MAX_VALUE);
    }
    @Override
    protected boolean checkAccuracy(Pokemon att,Pokemon def){  // Атака игнорирует значения Evasion и Accuracy тем самым попадая всегда
        return true;
    }
    @Override
    protected String describe(){  // Сообщение выводимое при использовании атаки
        return "использует Smart Strike";
    }
}