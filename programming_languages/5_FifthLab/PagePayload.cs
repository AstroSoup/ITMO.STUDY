public class PagePayload
{
    public string? UserData { get; set; }
    public string? OrderData { get; set; }
    public string? AdData { get; set; }

    public override string ToString()
    {
        return $"--- Агрегированный результат --- \nПользователь: {UserData}\nЗаказы: {OrderData}\nРеклама: {AdData}\n-----------------";
    }
}
