#import "title.typ": * 

#set text(
  lang: "ru",
  size: 14pt,
  font: "New Computer Modern",
)




#show math.cases: math.display


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

// headings
#show heading: set align(left)
#show heading: set text(size: 14pt)
#set heading(numbering: "1.",)
#show heading: pad.with(left: 1.25cm)


// default enums
#set enum(numbering: "1)")

// paragraph
#set par(
  first-line-indent: (all: true, amount: 1.25cm),
  justify: true,
)
// Links
#show link: it => if type(it.dest) == str { text(underline(it), fill: blue) } else { it }

// figure 
#set figure.caption(separator: [ --- ])


// listings
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


// refs to itemized lists (enums)
#show ref: it => {
  let el = it.element
  if el != none and el.func() == enum.item {
    let sup = it.supplement
    let (text, style) = if sup == auto {
      ([Элемент~], "1")
    } else if sup.func() == text {
      ([#sup.text~], "1")
    } else {
      (sup.children.slice(0, -1).join(), sup.children.at(-1).text)
    }
    // color the whole link (supplement + number)
    link(el.location(), std.text(fill: black, [#text#numbering(style, el.number)]))
  } else {
    it
  }
}

#include "content.typ"

