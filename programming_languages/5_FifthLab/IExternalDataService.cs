public interface IExternalDataService
{
    /// <summary>
    /// Имитирует асинхронный запрос к API пользователя.
    /// Требуемая имитация задержки: 2000 мс.
    /// </summary>
    Task<string> GetUserDataAsync(int userId);

    /// <summary>
    /// Имитирует асинхронный запрос к API заказов.
    /// Требуемая имитация задержки: 3000 мс.
    /// </summary>
    Task<string> GetUserOrdersAsync(int userId);

    /// <summary>
    /// Имитирует асинхронный запрос к API рекламного контента.
    /// Требуемая имитация задержки: 1000 мс.
    /// </summary>
    Task<string> GetAdsAsync();
}