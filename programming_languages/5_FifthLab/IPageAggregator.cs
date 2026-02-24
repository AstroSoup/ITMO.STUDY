public interface IPageAggregator
{
    /// <summary>
    /// Метод 1: Последовательная стратегия.
    /// Выполняет запросы к IExternalDataService строго последовательно,
    /// ожидая завершения каждого предыдущего запроса.
    /// </summary>
    Task<PagePayload> LoadPageDataSequentialAsync(int userId);

    /// <summary>
    /// Метод 2: Параллельная стратегия.
    /// Инициирует все запросы одновременно и асинхронно
    /// ожидает их общего завершения (c использованием Task.WhenAll).
    /// </summary>
    Task<PagePayload> LoadPageDataParallelAsync(int userId);
}