#import "title.typ": * 

#set text(
  lang: "ru",
  size: 14pt,
  font: "New Computer Modern",
)


#show math.cases: math.display

#show link: it => underline(text(fill: blue)[#it])
// Номер лабораторной работы
#context id.update("")
// Название лабораторной работы
#context name.update("")
// Группа
#context group.update("")
// Студент
#context student.update("")
// Преподаватель
#context teacher.update("")
// Вариант ЛР
#context variant.update("")

#context year.update(datetime.today().year())



#include "title.typ"

#set page(
  margin: 1.5cm,
  numbering: ("1")
)

#include "table_of_contents.typ"

#pagebreak()

#show heading: set align(left)
#show heading: set text(size: 14pt)
#set heading(numbering: "1.",)
#set enum(numbering: "  1)")
#set par(
  first-line-indent: (all: true, amount: 1em),
  hanging-indent: 1em,
  justify: true,
)
#set figure.caption(separator: [ --- ])

#let style-number(number) = text(gray)[#number:]

#show raw.where(block: true): it => grid(
  columns: 2,
  align: (right, left),
  gutter: 0.5em,
  ..it.lines
    .enumerate()
    .map(((i, line)) => (style-number(i + 1), line))
    .flatten()
)

#include "content.typ"
