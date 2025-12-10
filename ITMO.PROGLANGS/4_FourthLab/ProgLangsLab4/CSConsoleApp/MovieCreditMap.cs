using CsvHelper.Configuration;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Collections.Immutable;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSConsoleApp
{
    public sealed class MovieCreditMap : ClassMap<MovieCredit>
    {
        public MovieCreditMap()
        {
            Map(m => m.MovieId).Name("movie_id");
            Map(m => m.Title).Name("title");

            Map(m => m.Cast).Name("cast").Convert(row =>
            {
                var castField = row.Row.GetField("cast");
                if (string.IsNullOrWhiteSpace(castField))
                {
                    return ImmutableList<CastMember>.Empty;
                }

                var list = JsonConvert.DeserializeObject<List<CastMember>>(castField);
                // Преобразуем в иммутабельную коллекцию
                return list?.ToImmutableList() ?? ImmutableList<CastMember>.Empty;
            });

            Map(m => m.Crew).Name("crew").Convert(row =>
            {
                var crewField = row.Row.GetField("crew");
                if (string.IsNullOrWhiteSpace(crewField))
                {
                    return ImmutableList<CrewMember>.Empty;
                }

                var list = JsonConvert.DeserializeObject<List<CrewMember>>(crewField);
                // И здесь тоже
                return list?.ToImmutableList() ?? ImmutableList<CrewMember>.Empty;
            });
        }
    }
}
