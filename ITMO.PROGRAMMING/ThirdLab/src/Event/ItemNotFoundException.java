package Event;

public class ItemNotFoundException extends Exception {
    public ItemNotFoundException(String name) {
        super("Предмета "+ name + " здесь нет.");
    }
    public String getMessage(){
        return "Предмет не найден";
    }
}
