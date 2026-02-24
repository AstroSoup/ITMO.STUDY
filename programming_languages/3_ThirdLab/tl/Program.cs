using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;

namespace SeaBattle
{
    public enum CellState { Empty, Ship, Hit, Miss }
    public enum Orientation { Horizontal, Vertical }

    public class Cell
    {
        public int X { get; }
        public int Y { get; }
        public CellState State { get; set; }

        public Cell(int x, int y)
        {
            X = x;
            Y = y;
            State = CellState.Empty;
        }
    }

    public class Ship
    {
        public List<Cell> Cells { get; }
        public bool IsSunk => Cells.TrueForAll(c => c.State == CellState.Hit);
        public Ship(List<Cell> cells) => Cells = cells;
    }

    public class Board
    {
        public const int Size = 10;
        public Cell[,] Grid { get; }
        public List<Ship> Ships { get; }

        public Board()
        {
            Grid = new Cell[Size, Size];
            Ships = new List<Ship>();
            for (int y = 0; y < Size; y++)
                for (int x = 0; x < Size; x++)
                    Grid[x, y] = new Cell(x, y);
        }

        public bool PlaceShip(int startX, int startY, int length, Orientation orientation)
        {
            var cells = new List<Cell>();
            for (int i = 0; i < length; i++)
            {
                int x = startX + (orientation == Orientation.Horizontal ? i : 0);
                int y = startY + (orientation == Orientation.Vertical ? i : 0);

                if (x >= Size || y >= Size || Grid[x, y].State != CellState.Empty)
                    return false;

                cells.Add(Grid[x, y]);
            }

            foreach (var c in cells)
                c.State = CellState.Ship;

            Ships.Add(new Ship(cells));
            return true;
        }

        public bool Attack(int x, int y)
        {
            var cell = Grid[x, y];
            if (cell.State == CellState.Hit || cell.State == CellState.Miss)
                return false;

            if (cell.State == CellState.Ship)
            {
                cell.State = CellState.Hit;
                return true;
            }

            cell.State = CellState.Miss;
            return false;
        }

        public bool AllShipsSunk() => Ships.TrueForAll(s => s.IsSunk);

        public bool RandomlyPlaceShips(int[] shipSizes)
        {
            var rand = new Random();
            foreach (int size in shipSizes)
            {
                bool placed = false;
                for (int attempt = 0; attempt < 200 && !placed; attempt++)
                {
                    int x = rand.Next(Size);
                    int y = rand.Next(Size);
                    var orientation = (Orientation)rand.Next(2);
                    placed = PlaceShip(x, y, size, orientation);
                }
                if (!placed) return false;
            }
            return true;
        }

        public string[] RenderLines(bool hideShips)
        {
            string[] lines = new string[Size + 2];
            lines[0] = "    " + string.Join(" ", Enumerable.Range(0, Size).Select(i => $"{i}"));
            for (int y = 0; y < Size; y++)
            {
                var row = $"{y,2} ";
                for (int x = 0; x < Size; x++)
                {
                    var cell = Grid[x, y];
                    char ch = cell.State switch
                    {
                        CellState.Empty => '.',
                        CellState.Ship => hideShips ? '.' : '■',
                        CellState.Hit => 'X',
                        CellState.Miss => 'O',
                        _ => '?'
                    };
                    row += $" {ch}";
                }
                lines[y + 1] = row;
            }
            lines[^1] = "";
            return lines;
        }
    }

    public abstract class Player
    {
        public string Name { get; }
        public Board Board { get; }

        protected Player(string name)
        {
            Name = name;
            Board = new Board();
        }

        public abstract bool TakeTurn(Player opponent);
    }

    public class AIPlayer : Player
    {
        private readonly Random _rand = new();
        private readonly HashSet<(int, int)> _firedCells = new();

        public AIPlayer(string name) : base(name) { }

        public override bool TakeTurn(Player opponent)
        {
            int x, y;
            do
            {
                x = _rand.Next(Board.Size);
                y = _rand.Next(Board.Size);
            } while (_firedCells.Contains((x, y)));

            _firedCells.Add((x, y));
            bool hit = opponent.Board.Attack(x, y);

            Console.WriteLine($"{Name} стреляет по ({x},{y}) -> {(hit ? "Попадание" : "Промах")}");
            if (hit)
            {
                Console.Beep();
            }
            Thread.Sleep(300);
            return hit;
        }
    }

    public class Game
    {
        private readonly AIPlayer _ai1;
        private readonly AIPlayer _ai2;
        private readonly int[] _shipSizes = { 4, 3, 3, 2, 2, 1, 1 };

        public Game()
        {
            _ai1 = new AIPlayer("Адмирал Акбар");
            _ai2 = new AIPlayer("Дарт Вейдер");
        }

        public void Start()
        {
            Console.WriteLine("=== Морской бой ===");
            _ai1.Board.RandomlyPlaceShips(_shipSizes);
            _ai2.Board.RandomlyPlaceShips(_shipSizes);
            Thread.Sleep(800);

            Player current = _ai1;
            Player opponent = _ai2;

            while (true)
            {
                Console.Clear();
                DrawBoards(_ai1, _ai2);
                Console.WriteLine($"\nХод игрока {current.Name}...");
                bool hit = current.TakeTurn(opponent);

                if (opponent.Board.AllShipsSunk())
                {
                    Console.Clear();
                    DrawBoards(_ai1, _ai2);
                    Console.WriteLine($"\n{current.Name} побеждает!");
                    break;
                }

                if (!hit)
                    (current, opponent) = (opponent, current);
            }

            Console.WriteLine("\n=== Игра окончена ===");
        }

        private void DrawBoards(AIPlayer left, AIPlayer right)
        {
            var leftLines = left.Board.RenderLines(hideShips: false);
            var rightLines = right.Board.RenderLines(hideShips: false);
            Console.WriteLine($"{left.Name, -24} | {right.Name}");
            Console.WriteLine(new string('-', 50));

            for (int i = 0; i < leftLines.Length; i++)
            {
                string l = leftLines[i].PadRight(24);
                string r = rightLines[i];
                Console.WriteLine($"{l} | {r}");
            }
        }
    }

    class Program
    {
        static void Main()
        {
            Console.OutputEncoding = System.Text.Encoding.UTF8;
            new Game().Start();
        }
    }
}
