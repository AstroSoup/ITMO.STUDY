public class PageAggregatorService : IPageAggregator
{
    private IExternalDataService Service { get; set; }
    public PageAggregatorService(IExternalDataService service)
    {
        Service = service;
    }

    /// <summary>
    /// Метод 1: Последовательная стратегия.
    /// Выполняет запросы к IExternalDataService строго последовательно,
    /// ожидая завершения каждого предыдущего запроса.
    /// </summary>
    public async Task<PagePayload> LoadPageDataSequentialAsync(int userId)
    {
        PagePayload page = new PagePayload();
        page.UserData = await Service.GetUserDataAsync(userId);
        page.OrderData = await Service.GetUserOrdersAsync(userId);
        page.AdData = await Service.GetAdsAsync();
        return page;
    }

    /// <summary>
    /// Метод 2: Параллельная стратегия.
    /// Инициирует все запросы одновременно и асинхронно
    /// ожидает их общего завершения (c использованием Task.WhenAll).
    /// </summary>
    public async Task<PagePayload> LoadPageDataParallelAsync(int userId)
    {
        PagePayload page = new PagePayload();
        Task<string> user = Service.GetUserDataAsync(userId);
        Task<string> order = Service.GetUserOrdersAsync(userId);
        Task<string> ads = Service.GetAdsAsync();
        await Task.WhenAll(user, order, ads);
        page.UserData = user.Result;
        page.OrderData = order.Result;
        page.AdData = ads.Result;
        return page;
    }
}