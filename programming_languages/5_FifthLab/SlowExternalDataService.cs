using System.Security.Cryptography;
using System.Text;

public class SlowExternalDataService : IExternalDataService
{

    private static readonly Random random = new Random();

    private static readonly string[] Words = new[]
    {
        "apple", "banana", "carrot", "donut", "egg",
        "fish", "grape", "honey", "ice", "juice",
        "kiwi", "lemon", "mango", "nut", "orange",
        "peach", "quinoa", "rice", "strawberry", "tomato"
    };

    private static readonly string[] Adjectives = new[]
    {
        "bright", "cool", "fresh", "smart", "bold",
        "dynamic", "fast", "green", "modern", "creative"
    };

    private static readonly string[] Products = new[]
    {
        "car", "phone", "laptop", "shoe", "watch",
        "camera", "bike", "tablet", "sofa", "drone"
    };

    private static readonly string[] Campaigns = new[]
    {
        "deal", "promo", "boost", "offer", "ad",
        "campaign", "special", "event", "discount", "push"
    };


    private byte[] getHashById(int userId)
    {
        using (SHA256 sha256 = SHA256.Create())
        {
            byte[] bytes = Encoding.UTF8.GetBytes(userId.ToString());

            byte[] hashBytes = sha256.ComputeHash(bytes);

            return hashBytes;
        }
    }
    public async Task<string> GetUserDataAsync(int userId)
    {
        await Task.Delay(2000);

        byte[] hashBytes = getHashById(userId);

        StringBuilder sb = new StringBuilder();
            foreach (var b in hashBytes)
                sb.Append(b.ToString("x2"));

        return sb.ToString();
            

    }


    public async Task<string> GetUserOrdersAsync(int userId)
    {
        await Task.Delay(3000);

        byte[] hashBytes = getHashById(userId);

        int wordCount = 3;
        string[] selected = new string[wordCount];
        for (int i = 0; i < wordCount; i++)
        {
            int index = hashBytes[i] % Words.Length;
            selected[i] = Words[index];
        }

        return string.Join(" ", selected);
    }

    public async Task<string> GetAdsAsync()
    {
        await Task.Delay(1000);
        string adj = Adjectives[random.Next(Adjectives.Length)];
        string product = Products[random.Next(Products.Length)];
        string campaign = Campaigns[random.Next(Campaigns.Length)];

        return $"{adj} {product} {campaign}";
    }
}