using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProgLangs
{
    public class Student : Person
    {
        public Student(int id, string name) : base(id, name)
        {
        }

        public override void SendNotification(string message)
        {
            Console.WriteLine("Student: ", message);
        }
    }
}
