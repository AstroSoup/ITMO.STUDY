using System;
using System.Collections.Generic;
using System.Collections.Immutable;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSConsoleApp
{
    public class MovieCredit
    {
        public int MovieId { get; set; }
        public string Title { get; set; }

        // Используем IReadOnlyList для публичного API, чтобы скрыть 
        // конкретную реализацию иммутабельной коллекции.
        public IReadOnlyList<CastMember> Cast { get; set; } = ImmutableList<CastMember>.Empty;
        public IReadOnlyList<CrewMember> Crew { get; set; } = ImmutableList<CrewMember>.Empty;
    }
}
