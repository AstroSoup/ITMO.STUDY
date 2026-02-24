package client.utility;


/**
 * Класс для вывода информации в консоль.
 *
 * @author AstroSoup
 */
public class TextUI {

    public TextUI() {
    }

    /**
     * Метод для вывода информации с переносом строки в конце.
     * @param s строка выводимая на экран
     */
    public void outputLn(String s) {
        System.out.println(s);
    }

    /**
     * Метод для вывода информации без переноса строки в конце.
     * @param s строка выводимая на экран
     */
    public void output(String s){
        System.out.print(s);
    }
}
