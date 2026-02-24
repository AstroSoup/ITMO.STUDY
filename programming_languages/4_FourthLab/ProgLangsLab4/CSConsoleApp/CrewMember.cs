using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSConsoleApp
{
    public record CrewMember(
        [property: JsonProperty("credit_id")] string CreditId,
        [property: JsonProperty("department")] string Department,
        [property: JsonProperty("gender")] int Gender,
        [property: JsonProperty("id")] int Id,
        [property: JsonProperty("job")] string Job,
        [property: JsonProperty("name")] string Name
    );
}
