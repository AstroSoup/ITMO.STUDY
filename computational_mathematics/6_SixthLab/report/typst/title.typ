#set page(margin: (top: 3%, bottom: 3%))

#let id = state("id", "0");
#let name = state("name", "Оптимизация длины макарон");
#let variant = state("variant", "0");
#let student = state("student", "Иванов Иван Иванович")
#let group = state("group", "PXXXX");
#let teacher = state("teacher", "Клименков Сергей Викторович")
#let year = state("year", "1970")
// HEADER
#block()[
  #align(center)[
    #set text(size: 12pt)
    *Федеральное государственное автономное образовательное учреждение высшего образования "Национальный исследовательский университет ИТМО"*
  
    *Факультет Программной Инженерии и Компьютерной Техники*
  ]
]



// TITLE
#v(25%)
#align(center)[
  #set text(size: 22pt)
  Лабораторная работа №#context id.get() \
  "#context name.get()" \
  Вариант №#context variant.get()
]

//STUDENT INFO
#v(20%)
#align(left)[
  Группа  #box(baseline: 3pt)[#place(dy:-13pt)[#context group.get()] #line(end:(268pt, 0pt)) ]  \ \
        Студент #box(baseline: 3pt)[#place(dy:-13pt)[#context student.get()] #line(end:(263pt, 0pt))] \ \
        Преподаватель #box(baseline: 3pt)[#place(dy:-13pt)[#context teacher.get()] #line(end:(218pt, 0pt))] \ \  
]

//FOOTER
#align(bottom + center)[
  Санкт-Петербург, #context year.get()
]