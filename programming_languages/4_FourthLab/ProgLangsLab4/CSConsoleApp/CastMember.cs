using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSConsoleApp
{
    public record CastMember(
        [property: JsonProperty("cast_id")] int CastId,
        [property: JsonProperty("character")] string Character,
        [property: JsonProperty("credit_id")] string CreditId,
        [property: JsonProperty("gender")] int Gender,
        [property: JsonProperty("id")] int Id,
        [property: JsonProperty("name")] string Name,
        [property: JsonProperty("order")] int Order
    );
}
