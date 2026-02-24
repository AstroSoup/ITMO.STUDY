using System.Collections;
using System.Collections.Generic;
using System.Security.AccessControl;

namespace ProgLangs
{
    public class Program
    {
        public delegate bool IntComparer(int a, int b);

        static void Main(string[] args)
        {
            SortArray(new[] { 1, 2, 3, 4, 5 }, (a, b) =>
            {
                return a > b;
            });

            SortArray(new[] { 1, 2 }, delegate (int a, int b)
            {
                return a > b;
            });


            var random = new Random();
            var list = Enumerable.Range(0, 100)
                                    .Select(x => new
                                    {
                                        i = x,
                                        val = random.Next(-50, 50)
                                    })
                                    .ToArray();

            
        }

        public static bool ALessThanB(int a, int b)
        {
            return a > b;
        }

        public static bool BLessThanA(int a, int b)
        {
            return a < b;
        }

        public static int[] SortArray(int[] ints, IntComparer comparer)
        {
            var n = ints.Length;

            for (int i = 0; i < n - 1; i++)
            {
                for (int j = 0; j < n - i - 1; j++)
                {
                    if (comparer(ints[j], ints[j + 1]))
                    {
                        var tempVar = ints[j];
                        ints[j] = ints[j + 1];
                        ints[j + 1] = tempVar;
                    }
                }
            }
            return ints;
        }
    }
}