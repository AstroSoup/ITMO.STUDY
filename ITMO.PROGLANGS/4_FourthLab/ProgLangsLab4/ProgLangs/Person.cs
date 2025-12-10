using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProgLangs
{
    public interface IPerson
    {
        string Name { get; }

        void SendNotification(string message);
    }

    public class Person : IPerson
    {
        public Person(int id, string name = "item")
        {
            Id = id;
            
            Name = name;
        }

        public int Id { get; private set; }

        public string Name { get; set; }

        public virtual void SendNotification(string message)
        {
            Console.WriteLine(message);
        }
    }
}
