using System.Diagnostics;

namespace ConsoleApp
{
    internal class Program
    {
        static async Task Main(string[] args)
        {
            IExternalDataService service = new SlowExternalDataService();
            IPageAggregator aggregator = new PageAggregatorService(service);
            var sw = Stopwatch.StartNew();

            PagePayload page1 = await aggregator.LoadPageDataSequentialAsync(1);
            Console.WriteLine(page1.ToString());

            sw.Stop();
            Console.WriteLine($"Последовательный вызов: {sw.ElapsedMilliseconds} мс.");

            sw = Stopwatch.StartNew();

            PagePayload page2 = await aggregator.LoadPageDataParallelAsync(2);
            Console.WriteLine(page2.ToString());

            sw.Stop();
            Console.WriteLine($"Параллельный вызов: {sw.ElapsedMilliseconds} мс.");
        }

    }
}
