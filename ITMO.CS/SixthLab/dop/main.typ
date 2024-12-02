#set page(
  paper: "us-letter",
  numbering: "1",
  columns: 2,
)

#place(
  top + center,
  float: true,
  scope: "parent",
  clearance: 2em,
)[
  
  ]


#image("lol.png",width: 2cm)
      #align(left)[
      _С.Овчинников, И.Шарыгин_
      #text(size: 20pt)[
      = Решение неравенств с модулем  
    ]
    ]
#par(justify: true)[
#text(size: 10pt)[*В этой заметке излагается приём, который, в некотором смысле, "автоматически" сводит решение неравенств, содержащих переменную под знаком модуля, к решению _систем_ и _совокупностей_ где переменные уже свободны от знака модуля.*]

Пусть даны нексколько неравенств --- скажем, для простоты, два неравенства с одной (и той же) переменной:
#align(center, $f(x)>0$)
#align(center, $g(x)>0$)
]


#lorem(100)
#table(
  columns: (4cm,4cm),
  table.header(
    [#align(center)[Система]],[#align(center)[Совокупность]]
  ),
  [#align(center)[Пересечение]],
  [#align(center)[Объединение]],
  [#align(center)[и]],
  [#align(center)[или]]
)
#lorem(100)
$
|x| = cases(
  x  ", если" x >= 0,
  -x  ", если" x < 0, 
)
$
#lorem(130)
