using System.Runtime.InteropServices;

namespace CSConsoleApp
{
    public static class Program
    {
        public static void Main()
        {
            var currentDirectory = System.IO.Directory.GetCurrentDirectory();
            var filePath = System.IO.Directory.GetFiles(currentDirectory, "*.csv").First();

            IReadOnlyList<MovieCredit> movieCredits = null;
            try
            {
                var parser = new MovieCreditsParser(filePath);
                movieCredits = parser.Parse(); // Тип переменной теперь IReadOnlyList<MovieCredit>
            }
            catch (Exception exc)
            {
                Console.WriteLine("Не удалось распарсить csv");
                Environment.Exit(1);
            }

            FirstTask(movieCredits);
            SecondTask(movieCredits);
            ThirdTask(movieCredits);
            FourthTask(movieCredits);
            FifthTask(movieCredits);
            SixthTask(movieCredits);
            SeventhTask(movieCredits);
            EigthTask(movieCredits);
            NinthTask(movieCredits);
            TenthTask(movieCredits);
            EleventhTask(movieCredits);
            TwelfthTask(movieCredits);
            ThirteenthTask(movieCredits);
            FourteenthTask(movieCredits);
            FifteenthTask(movieCredits);
            SixteenthTask(movieCredits);
            SeventeenthTask(movieCredits);
            EighteenthTask(movieCredits);
            NineteenthTask(movieCredits);
            TwentiethTask(movieCredits);
        }

        // Найти все фильмы, снятые режиссером "Steven Spielberg".
        static void FirstTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var spielberg_films = movieCredits
            .Where(m => m.Crew.Any(c => c.Job == "Director" && c.Name == "Steven Spielberg"));

            File.WriteAllText("./answers/task_1.txt", string.Join(Environment.NewLine, spielberg_films.Select(m => m.Title)));

        }

        // Получить список всех персонажей, которых сыграл актер "Tom Hanks".
        static void SecondTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var hanks_characters = movieCredits
            .SelectMany(m => m.Cast)
            .Where(a => a.Name == "Tom Hanks");

            File.WriteAllText("./answers/task_2.txt", string.Join(Environment.NewLine, hanks_characters.Select(a => a.Character)));
        }

        // Найти 5 фильмов с самым большим количеством актеров в составе.
        static void ThirdTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var top_5_crouded_films = movieCredits
            .OrderByDescending(m => m.Cast.Count)
            .Take(5);
            File.WriteAllText("./answers/task_3.txt", string.Join(Environment.NewLine, top_5_crouded_films.Select(m => $"{m.Title} : {m.Cast.Count}")));
        }

        // Найти топ-10 самых востребованных актеров (по количеству фильмов).
        static void FourthTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var top_10_actors = movieCredits
            .SelectMany(m => m.Cast)
            .GroupBy(a => a.Name)
            .Select(g => new
            {
                Name = g.Key,
                Count = g.Count()
            })
            .OrderByDescending(a => a.Count)
            .Take(10);


            File.WriteAllText("./answers/task_4.txt", string.Join(Environment.NewLine, top_10_actors.Select(a => $"{a.Name} : {a.Count}")));
        }


        // Получить список всех уникальных департаментов (department) съемочной группы.

        static void FifthTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var unique_crew_departments = movieCredits
            .SelectMany(m => m.Crew)
            .Select(c => c.Department)
            .Distinct();
            File.WriteAllText("./answers/task_5.txt", string.Join(Environment.NewLine, unique_crew_departments));
        }

        // Найти все фильмы, где "Hans Zimmer" был композитором (Original Music Composer).
        static void SixthTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var zimmer_films = movieCredits
            .Where(m => m.Crew.Any(c => c.Name == "Hans Zimmer" && c.Job == "Original Music Composer"));

            File.WriteAllText("./answers/task_6.txt", string.Join(Environment.NewLine, zimmer_films.Select(m => m.Title)));
        }

        // Создать словарь, где ключ — ID фильма, а значение — имя режиссера.
        static void SeventhTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            Dictionary<int, String> dict = movieCredits.ToDictionary(m => m.MovieId,
             m => m.Crew.FirstOrDefault(c => c.Job == "Director")?.Name);

            File.WriteAllText("./answers/task_7.txt", "{" + string.Join(", " + Environment.NewLine, dict.Select(kv => $"{kv.Key}=\"{kv.Value}\"")) + "}");
        }

        // Найти фильмы, где в актерском составе есть и "Brad Pitt", и "George Clooney".
        static void EigthTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var pitt_clooney_duet_films = movieCredits
            .Where(m => m.Cast.Any(a => a.Name == "Brad Pitt"))
            .Where(m => m.Cast.Any(a => a.Name == "George Clooney"));

            File.WriteAllText("./answers/task_8.txt", string.Join(Environment.NewLine, pitt_clooney_duet_films.Select(m => m.Title)));

        }

        // Посчитать, сколько всего человек работает в департаменте "Camera" по всем фильмам.

        static void NinthTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var camera_crew_count = movieCredits
            .Select(m => m.Crew.Where(c => c.Department == "Camera").Count()
            ).Sum();

            File.WriteAllText("./answers/task_9.txt", string.Join(Environment.NewLine, camera_crew_count));

        }

        // Найти всех людей, которые в фильме "Titanic" были одновременно и в съемочной группе, и в списке актеров.
        static void TenthTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var titanic_movie = movieCredits.First(m => m.Title == "Titanic");

            var crew_and_cast_titanic = titanic_movie.Cast
            .Where(a => titanic_movie.Crew.Select(c => c.Name).Contains(a.Name))
            .Select(a => new
            {
                Name = a.Name,
                Character = a.Character,
                Job = titanic_movie.Crew.First(c => c.Name == a.Name).Job
            });

            File.WriteAllText("./answers/task_10.txt", string.Join(Environment.NewLine, crew_and_cast_titanic.Select(o => $"{o.Job} {o.Name} played {o.Character} in Titanic")));
        }

        // Найти "внутренний круг" режиссера: Для режиссера "Quentin Tarantino" найти топ-5 членов съемочной группы (не актеров), которые работали с ним над наибольшим количеством фильмов.
        static void EleventhTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var tarantino_5 = movieCredits
            .Where(m => m.Crew.Any(c => c.Job == "Director" && c.Name == "Quentin Tarantino"))
            .SelectMany(m => m.Crew)
            .GroupBy(c => c.Name)
            .Select(g => new
            {
                Name = g.Key,
                Count = g.Count()
            })
            .OrderByDescending(o => o.Count)
            .Skip(1) // Тарантино
            .Take(5);
            File.WriteAllText("./answers/task_11.txt", string.Join(Environment.NewLine, tarantino_5.Select(o => $"{o.Name} : {o.Count}")));

        }

        // Определить экранные "дуэты": Найти 10 пар актеров, которые чаще всего снимались вместе в одних и тех же фильмах.
        static void TwelfthTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var duets = movieCredits
            .SelectMany(movie => movie.Cast.Distinct()
                .SelectMany((actor1, i) => movie.Cast.Distinct()
                    .Skip(i + 1)
                    .Select(actor2 => new { Actor1 = actor1.Name, Actor2 = actor2.Name })))
                    .Where(d => d.Actor1 != d.Actor2);



            var top_10_duets = duets
            .GroupBy(d => new
            {
                A = d.Actor1.CompareTo(d.Actor2) < 0 ? d.Actor1 : d.Actor2,
                B = d.Actor1.CompareTo(d.Actor2) < 0 ? d.Actor2 : d.Actor1
            })
            .Select(g => new
            {
                Actor1 = g.Key.A,
                Actor2 = g.Key.B,
                Count = g.Count()
            })
            .OrderByDescending(x => x.Count)
            .Take(10);
            File.WriteAllText("./answers/task_12.txt", string.Join(Environment.NewLine, top_10_duets.Select(d => $"{d.Actor1} : {d.Actor2} : {d.Count}")));
            
        }

        // Вычислить "индекс разнообразия" для карьеры: Найти 5 членов съемочной группы, которые поработали в наибольшем количестве различных департаментов за свою карьеру.
        static void ThirteenthTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var wandering_cast = movieCredits
            .SelectMany(m => m.Crew)
            .GroupBy(c => c.Name)
            .Select(g => new
            {
                Name = g.Key,
                Count = g.SelectMany(c => c.Department).Distinct().Count()
            })
            .OrderByDescending(o => o.Count)
            .Take(5);
            File.WriteAllText("./answers/task_13.txt", string.Join(Environment.NewLine, wandering_cast.Select(o => $"{o.Name} : {o.Count}")));
        }

        // Найти "творческие трио": Найти фильмы, где один и тот же человек выполнял роли режиссера (Director), сценариста (Writer) и продюсера (Producer).
        static void FourteenthTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var trio_people = movieCredits
            .Where(m => m.Crew
                .GroupBy(c => c.Name)
                .Where(g => g.Select(c => c.Job).Contains("Director")
                && g.Select(c => c.Job).Contains("Writer")
                && g.Select(c => c.Job).Contains("Producer")).Count() > 0
            ).Select(m => m.Title);
            
            File.WriteAllText("./answers/task_14.txt", string.Join(Environment.NewLine, trio_people));
            
        }

        // Два шага до Кевина Бейкона: Найти всех актеров, которые снимались в одном фильме с актером, который, в свою очередь, снимался в одном фильме с "Kevin Bacon".
        static void FifteenthTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var bacon_films = movieCredits
            .Where(m => m.Cast.Any(a => a.Name == "Kevin Bacon"))
            .ToList();

            var actors_step = bacon_films
            .SelectMany(m => m.Cast)
            .Select(a => a.Name)
            .Where(name => name != "Kevin Bacon")
            .Distinct()
            .ToList();

            var actors_step_films = movieCredits
            .Where(m => !bacon_films.Any(b => b.Title == m.Title))
            .Where(m => m.Cast.Any(a => actors_step.Contains(a.Name)))
            .ToList();

            var actors_two_steps = actors_step_films
            .SelectMany(m => m.Cast)
            .Select(a => a.Name)
            .Where(name => !actors_step.Contains(name))
            .Distinct()
            .ToList();

            File.WriteAllText("./answers/task_15.txt", string.Join(Environment.NewLine, actors_two_steps));
        }

        // Проанализировать "командную работу": Сгруппировать фильмы по режиссеру и для каждого из них найти средний размер как актерского состава (Cast), так и съемочной группы (Crew).
        static void SixteenthTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var means = movieCredits
            .Where(m => m.Crew.Any(c => c.Job == "Director"))
            .GroupBy(m => m.Crew.First(c => c.Job == "Director").Name)
            .Select(g => new
            {
                Dir = g.Key,
                Mean_cast = (float)g.SelectMany(m => m.Cast).Count() / (float)g.Count(),
                Mean_crew = (float)g.SelectMany(m => m.Crew).Count() / (float)g.Count()
            });

            File.WriteAllText("./answers/task_16.txt", string.Join(Environment.NewLine, means.Select(m => $"{m.Dir}: Mean_cast={m.Mean_cast}, Mean_crew={m.Mean_crew}")));
        }

        // Определить карьерный путь "универсалов": Для каждого человека, который был и актером, и членом съемочной группы (в целом по датасету), определить департамент, в котором он работал чаще всего.
        static void SeventeenthTask(IReadOnlyList<MovieCredit> movieCredits)
        {

            var crew_names = movieCredits.SelectMany(m => m.Crew).Select(c => c.Name).ToHashSet();
            var cast_names = movieCredits.SelectMany(m => m.Cast).Select(c => c.Name).ToHashSet();

            var universal_people_names = crew_names
            .Where(c => cast_names.Contains(c))
            .ToHashSet();
            var universal_people_deps = movieCredits
            .SelectMany(m => m.Crew)
            .Where(c => universal_people_names.Contains(c.Name))
            .GroupBy(c => c.Name)
            .Select(g => new
            {
                Name = g.Key,
                Dep = g.GroupBy(c => c.Department).Select(g1 => new { D = g1.Key, Cnt = g1.Count() }).OrderByDescending(o => o.Cnt).FirstOrDefault().D
            });
            File.WriteAllText("./answers/task_17.txt", string.Join(Environment.NewLine, universal_people_deps.Select(d => $"{d.Name}: {d.Dep}")));

        }

        // Найти пересечение "элитных клубов": Найти людей, которые работали и с режиссером "Martin Scorsese", и с режиссером "Christopher Nolan".
        static void EighteenthTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var nolan_films = movieCredits
            .Where(m => m.Crew.Any(c => c.Job == "Director" && c.Name == "Christopher Nolan"));
            var scorsese_films = movieCredits
            .Where(m => m.Crew.Any(c => c.Job == "Director" && c.Name == "Martin Scorsese"));

            var nolan_cast = nolan_films
            .SelectMany(m => m.Cast)
            .Select(a => a.Name).Distinct();

            var scorsese_cast = scorsese_films
            .SelectMany(m => m.Cast)
            .Select(a => a.Name).Distinct();

            var nolan_crew = nolan_films
            .SelectMany(m => m.Crew)
            .Select(a => a.Name).Distinct();

            var scorsese_crew = scorsese_films
            .SelectMany(m => m.Crew)
            .Select(a => a.Name).Distinct();

            var elites = scorsese_cast.Concat(scorsese_crew).Intersect(nolan_cast.Concat(nolan_crew)).Distinct();
            File.WriteAllText("./answers/task_18.txt", string.Join(Environment.NewLine, elites));
        }

        // Выявить "скрытое влияние": Ранжировать все департаменты по среднему количеству актеров в тех фильмах, над которыми они работали (чтобы проверить, коррелирует ли работа определенного департамента с масштабом актерского состава).
        static void NineteenthTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var a = movieCredits
            .SelectMany(m => m.Crew
                .Select(c => new { Movie = m, Department = c.Department }))
                    .GroupBy(x => x.Department)
                    .Select(g => new
                    {
                        Dep = g.Key,
                        Count = g.SelectMany(x => x.Movie.Crew).Select(x => x.Name).Distinct().Count()
                    }).OrderByDescending(x => x.Count);

            File.WriteAllText("./answers/task_19.txt", string.Join(Environment.NewLine, a.Select(o => $"{o.Dep}: {o.Count}")));
        }
        
        // Проанализировать "архетипы" персонажей: Для актера "Johnny Depp" сгруппировать его роли по первому слову в имени персонажа (например, "Captain", "Jack", "Willy") и посчитать частоту каждого такого "архетипа".
        static void TwentiethTask(IReadOnlyList<MovieCredit> movieCredits)
        {
            var depp_characters = movieCredits
            .SelectMany(m => m.Cast).Where(a => a.Name == "Johnny Depp").Select(a => a.Character);

            var freq = depp_characters
            .GroupBy(c => c.Split(" ")[0])
            .Select(g => new { Type = g.Key, Cnt = g.Count() }).OrderByDescending(o => o.Cnt);

            File.WriteAllText("./answers/task_20.txt", string.Join(Environment.NewLine, freq.Select(o => $"{o.Type}: {o.Cnt}")));

        }



    }
}