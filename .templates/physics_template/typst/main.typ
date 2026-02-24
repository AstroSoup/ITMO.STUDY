#import "titlePage.typ": group, students, teacher, date_of_admission, date_of_completance, work_acceptor, lab_id, lab_title_first_row, lab_title_second_row
#set par(justify: true)
#set text(
  lang: "ru",
  size: 14pt,
  font: "New Computer Modern",
)


#show math.cases: math.display

#show link: it => underline(text(fill: dark_blue)[#it])

// Группа
#context group.update("")
// Выполнили
#context students.update("")
// Преподаватель лаб
#context teacher.update("")
// Дата выполнения лр
#context date_of_admission.update("")
// Дата сдачи
#context date_of_completance.update("")
// Работу принял
#context work_acceptor.update("")
// Номер лабораторной работы
#context lab_id.update("")
// Название лабораторной работы
#context lab_title_first_row.update("");
#context lab_title_second_row.update("")

#include "titlePage.typ"

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
