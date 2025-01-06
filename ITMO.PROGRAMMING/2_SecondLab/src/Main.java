import ru.ifmo.se.pokemon.*;
import pokemons.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static ArrayList<String> names = new ArrayList<>(Arrays.asList("Фродо Бэггинс", "Сэм Гэмджи", "Мерри", "Пиппин", "Гэндальф", "Радагаст", "Саруман", "Арагорн", "Гимли", "Леголас", "Боромир", "Голлум", "Саурон", "Арвен", "Древобород", "Теоден", "Трандуил", "Фарамир", "Элронд", "Бифур", "Бофур", "Бомбур", "Оин", "Глоин", "Балин", "Двалин", "Дори", "Нори", "Ори", "Фили", "Кили", "Торин"));
    public static String nameGen(){
        int ind = (int) (Math.random() * names.size());
        String generatedName = names.get(ind);
        names.remove(ind);
        return generatedName;
    }

    public static void main(String[] args) {
        Battle battle = new Battle();

        Pokemon bouffalant = new Bouffalant(nameGen(), 1);
        Pokemon lickitung = new Lickitung(nameGen(), 1);
        Pokemon lickilicky = new Lickilicky(nameGen(), 1);
        Pokemon cleffa = new Cleffa(nameGen(), 1);
        Pokemon clefairy = new Clefairy(nameGen(), 1);
        Pokemon clefable = new Clefable(nameGen(), 1);

        battle.addAlly(bouffalant);
        battle.addAlly(lickitung);
        battle.addAlly(cleffa);

        battle.addFoe(lickilicky);
        battle.addFoe(clefairy);
        battle.addFoe(clefable);

        battle.go();
    }
}