using CsvHelper;
using System;
using System.Collections.Generic;
using System.Collections.Immutable;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSConsoleApp
{
    public class MovieCreditsParser
    {
        private readonly string _filePath;

        public MovieCreditsParser(string filePath)
        {
            _filePath = filePath;
        }

        // Метод теперь возвращает IReadOnlyList для согласованности.
        public IReadOnlyList<MovieCredit> Parse()
        {
            using (var reader = new StreamReader(_filePath, Encoding.UTF8))
            using (var csv = new CsvReader(reader, CultureInfo.InvariantCulture))
            {
                csv.Context.RegisterClassMap<MovieCreditMap>();

                // Сразу материализуем результат в иммутабельный список.
                var records = csv.GetRecords<MovieCredit>().ToImmutableList();
                return records;
            }
        }
    }
}
