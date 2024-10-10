package moves;
import ru.ifmo.se.pokemon.*;

public class AerialAce extends PhysicalMove{
	public AerialAce(){  // конструктор класса AerialAce в суперкласс передается тип атаки, урон и точность попадания
		super(Type.FLYING, 60,Long.MAX_VALUE);
	}
	@Override
	protected boolean checkAccuracy(Pokemon att,Pokemon def){  // Атака игнорирует значения Evasion и Accuracy тем самым попадая всегда
		return true;
	}
	@Override
	protected String describe(){  // Сообщение выводимое при использовании атаки
		return "использует Aerial Ace";
	}
}