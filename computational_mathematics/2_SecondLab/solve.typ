#import "@preview/elembic:1.1.0" as e
#import "@preview/tiptoe:0.3.1"
#import "@preview/lilaq:0.5.0" as lq

#let schoolbook-style = it => {
  let filter(value, distance) = value != 0 and distance >= 5pt
  let axis-args = (position: 0, filter: filter)
  
  show: lq.set-tick(inset: 1.5pt, outset: 1.5pt, pad: 0.4em)
  show: lq.set-spine(tip: tiptoe.stealth)
  show: lq.set-grid(stroke: none)

  show: lq.set-diagram(xaxis: axis-args, yaxis: axis-args)

  show: lq.set-label(pad: none, angle: 0deg)
  show: e.show_(
    lq.label.with(kind: "y"),
    it => place(bottom + right, dy: -100% - .0em, dx: -.5em, it)
  )
  show: e.show_(
    lq.label.with(kind: "x"),
    it => place(left + top, dx: 100% + .0em, dy: .4em, it)
  )
  
  it
}
// Производная функции одной переменной
#let derivative(f, x) = {
  let h = 0.000001
  return (f(x + h) - f(x - h)) / (2*h)
}

// Округление по эпсилон
#let round(num, eps) = {
  return calc.round(num, digits: calc.ceil(calc.log(calc.ceil(1 / eps))))
}
// Необходимое условие существования корня на отрезке
#let nessecary_condition(function, a, b) = {
  return function(a) * function(b) < 0
} 
// Достаточное условие существования корня на отрезке 
#let sufficient_condition(function, a, b) = {
  let df = (x) => derivative(function, x)
  let step = 0.001
  let xl = a
  let sign
  if (df(xl) == 0) {
    sign = 1
  } else {
    sign = df(xl) / calc.abs(df(xl))
  }
  while (xl < b) {
    xl += step
    if (sign != df(xl) / calc.abs(df(xl))) {
      return false
    }
  }
  return true
}

// итеративное нахождение максимального значения функции на отрезке
#let find_max(f, a, b) = {
  let step = (b - a) / 500
  let max = 0
  let x = a

  while x <= b {
    let val = calc.abs(f(x))
    if val > max {
      max = val
    }
    x += step
  }

  return max
}

// Для вычислительной части
// Метод хорд
#let chord_method(f, a, b, eps) = {
  if (not nessecary_condition(f, a, b)) {
    return [Не выполняется необходимое условие существования корня.]
  }
  if (not sufficient_condition(f, a, b)) {
    return [Не выполняется достаточное условие единственности корня.]
  }
  
  let x1
  let iterations = 1
  let bt = b
  let at = a
  let x = at
  let ans = ()
  let cur = ()
  cur.push([#iterations])
  cur.push([#round(at,eps)])
  cur.push([#round(bt,eps)])
  x1 = at - f(at) * (bt - at) / (f(bt) - f(at))
  cur.push([#round(x1, eps)])
  cur.push([#round(f(at), eps)])
  cur.push([#round(f(bt), eps)])
  cur.push([#round(f(x1), eps)])
  cur.push([#round(calc.abs(x1 - x), eps)])
  if (nessecary_condition(f, at, x1)) {
    bt = x1
  } else {
    at = x1
  }
  ans.push(cur)
  while calc.abs(f(x1)) > eps {
    x = x1
    iterations += 1
    cur = ()
    cur.push([#iterations])
    cur.push([#round(at,eps)])
    cur.push([#round(bt,eps)])
    x1 = at - f(at) * (bt - at) / (f(bt) - f(at))
    cur.push([#round(x1, eps)])
    cur.push([#round(f(at), eps)])
    cur.push([#round(f(bt), eps)])
    cur.push([#round(f(x1), eps)])
    cur.push([#round(calc.abs(x1 - x), eps)])
    if (nessecary_condition(f, at, x1)) {
      bt = x1
    } else {
      at = x1
    }

    
    if iterations > 1000 {
      return [#text(color: red)[Не получилось достичь необходимой точности.]]
    }
  }
  x = x1
  return [
    == Метод хорд
      
      #table(
        columns: (auto,1fr,1fr,1fr,1fr,1fr,1fr,auto),
        table.header([№ шага],[$a$],[$b$],[$x$],[$f(a)$],[$f(b)$],[$f(x)$],[$|x_(k+1) - x_k|$]),
        ..for (n, a, b, x, fa, fb, fx, x1x) in ans {
          (n, a, b, x, fa, fb, fx, x1x)
        }
      )
      Найден корень $x^* = #round(x, eps)$. Совершено #iterations итераций.
  ]
}
// Метод секущих
#let secant_method(f, a, b, eps) = {
  if (not nessecary_condition(f, a, b)) {
    return [Не выполняется необходимое условие существования корня.]
  }
  if (not sufficient_condition(f, a, b)) {
    return [Не выполняется достаточное условие единственности корня.]
  }
  let xl = a
  let x = b
  let x1
  let iterations = 1
  x1 = x - (x - xl) / (f(x) - f(xl)) * f(x)
  let ans = ()
  let cur = ()
  cur.push([#iterations])
  cur.push([#round(xl, eps)])
  cur.push([#round(x, eps)])
  cur.push([#round(x1, eps)])

  
  cur.push([#round(f(x1), eps)])
  cur.push([#round(calc.abs(x1 - x), eps)])
  
  ans.push(cur)
  while(calc.abs(x1 - x) > eps) {
    xl = x
    x = x1
    iterations += 1
    cur = ()
    cur.push([#iterations])
    cur.push([#round(xl, eps)])
    cur.push([#round(x, eps)])
    cur.push([#round(x1, eps)])

    x1 = x - (x - xl) / (f(x) - f(xl)) * f(x)
    cur.push([#round(f(x1), eps)])
    cur.push([#round(calc.abs(x1 - x), eps)])
    
    ans.push(cur)

    if (iterations > 100) {
      return [#text(color: red)[Не получилось достичь необходимой точности.]]
    }
  }
  let num_dots;
  if (calc.ceil(1/eps) > 1000) {
    num_dots = calc.ceil(1/eps)
  } else {
    num_dots = 1000
  }
  return [
  == Метод секущих
  #table(
  columns: (1fr,1fr,1fr,1fr,1fr,1fr),
  table.header([№ шага], [$x_(k - 1)$],[$x_k$],[$x_(k+1)$],[$f(x_(k+1))$],[$|x_(k+1) - x_k|$]),
  ..for (n, xl, x, x1, fx1, x1x) in ans {
    (n, xl, x, x1, fx1, x1x)
  }
  )
  Найден корень $x^* = #round(x, eps)$. Совершено #iterations итераций.]
}
// Для программной части

// Метод Ньютона
#let newtons_method(f, a, b, eps) = {
  if (not nessecary_condition(f, a, b)) {
    return [Не выполняется необходимое условие существования корня.]
  }
  if (not sufficient_condition(f, a, b)) {
    return [Не выполняется достаточное условие единственности корня.]
  }
  let df = (x) => derivative(f, x)
  let x = b
  let x1
  let iterations = 1
  let ans = ()
  let cur = ()
  cur.push([#iterations])
  cur.push([#round(x, eps)])
  cur.push([#round(f(x), eps)])
  cur.push([#round(df(x), eps)])

  x1 = x - f(x) / df(x)
  cur.push([#round(x1, eps)])
  cur.push([#round(calc.abs(x1 - x), eps)])
  
  ans.push(cur)
  while(calc.abs(x1 - x) > eps) {
    x = x1
    iterations += 1
    cur = ()
    cur.push([#iterations])
    cur.push([#round(x, eps)])
    cur.push([#round(f(x), eps)])
    cur.push([#round(df(x), eps)])

    x1 = x - f(x) / df(x)
    cur.push([#round(x1, eps)])
    cur.push([#round(calc.abs(x1 - x), eps)])
    
    ans.push(cur)

    if (iterations > 100) {
      return [#text(color: red)[Не получилось достичь необходимой точности.]]
    }
  }
  let num_dots;
  if (calc.ceil(1/eps) > 1000) {
    num_dots = calc.ceil(1/eps)
  } else {
    num_dots = 1000
  }
  let leftx = a
  let rightx = b
  if (rightx < 1) {
    rightx = 1
  }

  return [
  == Метод Ньютона
  #table(
  columns: (1fr,1fr,1fr,1fr,1fr,1fr),
  table.header([№ шага], [$x_k$],[$f(x_k)$],[$f'(x_k)$],[$x_(k+1)$],[$|x_(k+1) - x_k|$]),
  ..for (n, x, fx, dfx, x1, x1x) in ans {
    (n, x, fx, dfx, x1, x1x)
  }
  )
  Найден корень $x^* = #round(x, eps)$. Совершено #iterations итераций.
  #figure(
    caption: [График функции на интервале \[#a ; #b\]],
      
      lq.diagram(
        xlabel: $x$,
        ylabel: $y$,
        xlim:(leftx, rightx),
        lq.plot(
        lq.linspace(leftx * 1.2, rightx * 1.2, num: num_dots ),
        f,
        ),
        lq.plot(
          (x,),
          f,
          mark: "o",
          stroke: none,
          mark-size: 0.3em, 
          color: rgb("#000000")
          )
      )
  )
  ]
}

// Метод половинного деления
#let binary_method(f, a, b, eps) = {
  if (not nessecary_condition(f, a, b)) {
    return [Не выполняется необходимое условие существования корня.]
  }
  if (not sufficient_condition(f, a, b)) {
    return [Не выполняется достаточное условие единственности корня.]
  }
  let ans = ()
  let at = a
  let bt = b
  let c
  let iterations = 0
  while (calc.abs(bt - at) > eps) {
    iterations += 1
    c = (at + bt) / 2
    let cur = ()
    cur.push([#iterations])
    cur.push([#round(at, eps)])
    cur.push([#round(bt, eps)])
    cur.push([#round(c, eps)])
    cur.push([#round(f(at), eps)])
    cur.push([#round(f(bt), eps)])
    cur.push([#round(f(c), eps)])
    cur.push([#round(calc.abs(at - bt), eps)])

    if (f(a) * f(c) <= 0) {
      bt = c
      
    } else {
      at = c
    }
    
    ans.push(cur)
    if (iterations > 1000) {
      return [#text(color: red)[Не получилось достичь необходимой точности.]]
    }
  }
  let num_dots;
  if (calc.ceil(1/eps) > 1000) {
    num_dots = calc.ceil(1/eps)
  } else {
    num_dots = 1000
  }
  let x = c
  let leftx = a
  let rightx = b
  if (rightx < 1) {
    rightx = 1
  }
  return [
  == Метод половинного деления
    #table(
    columns: (1fr,1fr,1fr,1fr,1fr,1fr,1fr,1fr),
    table.header([№ шага],[$a$],[$b$],[$x$],[$f(a)$], [$f(b)$], [$f(x)$], [$|a - b|$]),
    ..for (n,a,b,x,fa,fb,fx,ab) in ans {
      (n,a,b,x,fa,fb,fx,ab)
    }
  )
  Найден корень $x^* = #round(x, eps)$. Совершено #iterations итераций.
  #figure(
    caption: [График функции на интервале \[#a ; #b\]],
      
      lq.diagram(
        xlabel: $x$,
        ylabel: $y$,
        xlim: (leftx, rightx),
        lq.plot(
        lq.linspace(leftx * 1.2, rightx * 1.2, num: num_dots ),
        f,
        ),
        lq.plot(
          (x,),
          f,
          mark: "o",
          stroke: none,
          mark-size: 0.3em, 
          color: rgb("#000000")
          )
      )
  )
  ]
}

// Метод простых итераций
#let simple_iteration_method(f, a, b, eps) = {

  if (not nessecary_condition(f, a, b)) {
    return [Не выполняется необходимое условие существования корня.]
  }
  if (not sufficient_condition(f, a, b)) {
    return [Не выполняется достаточное условие единственности корня.]
  }

  let max_der = find_max((x) => (derivative(f, x)), a, b)
  let phi(x) = {
    return x - (1 / (max_der)) * f(x)
  }
  

  let text = []


  let q = find_max((x) => (derivative(phi, x)), a + (b - a) * 0.2, b - (b - a) * 0.2)
  if (q > 1) {
    phi = (x) => {
    return x + (1 / (max_der)) * f(x)
    }
    q = find_max((x) => (derivative(phi, x)), a + (b - a) * 0.2, b - (b - a) * 0.2)
    text += [
    Найдена $ #sym.phi (x) = x + 1 / #round(max_der, eps) dot f(x)$,
    $q = #round(q, eps)$. \
    ]
  }else {
    text += [
    Найдена $ #sym.phi (x) = x - 1 / #round(max_der, eps) dot f(x)$,
    $q = #round(q, eps)$. \
    ]
  }
    
  if (q > 1) {
    return text + [Достаточное условие сходимости не выполняется. \ ]
  }
  let ans = ()
  let x = b
  let x1 = phi(x)
  let iterations = 1
  ans.push(([#iterations],[#round(x,eps)],[#round(x1,eps)],[#round(f(x1),eps)], [#round(calc.abs(x1 - x), eps)]))
  while (calc.abs(x1 - x) > eps) {
    x = x1
    x1 = phi(x)
    iterations += 1
    ans.push(([#iterations],[#round(x,eps)],[#round(x1,eps)],[#round(f(x1),eps)], [#round(calc.abs(x1 - x), eps)]))
    if (iterations > 1000) {
      return [#text(color: red)[Не получилось достичь необходимой точности.]]
    }
  }
  x = x1
  let num_dots;
  if (calc.ceil(1/eps) > 1000) {
    num_dots = calc.ceil(1/eps)
  } else {
    num_dots = 1000
  }
  let leftx = a
  let rightx = b
  if (rightx < 1) {
    rightx = 1
  }

  return [
  == Метод простых итераций] + text + [
  #table(
    columns:(1fr,1fr,1fr,1fr,1fr),
    table.header([№ шага], [$x_k$],[$x_(k+1)$],[$f(x_(k+1))$], [$|x_(k+1) - x_k|$]),
    .. for (n, x, x1, fx1, x1x) in ans {
      (n, x, x1, fx1, x1x)
    }
  )
  Найден корень $x^* = #round(x, eps)$. Совершено #iterations итераций.

  #figure(
    caption: [График функции на интервале \[#a ; #b\]],
      
      lq.diagram(
        xlabel: $x$,
        ylabel: $y$,
        xlim: (leftx,rightx),
        ylim: (f(a),f(b)),
        lq.plot(
        lq.linspace(leftx * 1.2, rightx, num: num_dots),
        f,
        ),
        lq.plot(
          (x,),
          f,
          mark: "o",
          stroke: none,
          mark-size: 0.3em, 
          color: rgb("#000000")
          )
      )
  )
  ]
}

// Система

#let partial_x(f, x, y) = {
  let h = 0.000001
  return (f(x + h, y) - f(x - h, y)) / (2 * h)
}

#let partial_y(f, x, y) = {
  let h = 0.000001
  return (f(x, y + h) - f(x, y - h)) / (2 * h)
}

#let find_max_system(f, a, b, c, d) = {
  let dx = (b - a) / 50
  let dy = (d - c) / 50
  let max = 0
  let x = a
  let y = c
  while x <= b {
    while y <= d {
      let val = calc.abs(f(x, y))
      if val > max {
        max = val
      }
      y += dy
    }
    y = c
    x += dx
  }

  return max
}

//TODO: add plot drawing
// Метод простых итераций для систем
#let simple_iteration_method_for_systems(f, g, a, b, c, d, eps) = {

  let text = []
  let max_df = find_max_system(
    (x, y)=> calc.abs(partial_x(f, x, y)) + calc.abs(partial_y(f, x, y)),
    a, b, c, d
  )

  let max_dg = find_max_system(
    (x, y)=> calc.abs(partial_x(g, x, y)) + calc.abs(partial_y(g, x, y)),
    a, b, c, d
  )

  let phi = (x, y) => (x - 1 / max_df * f(x, y))
  text += [
    Найдена $#sym.phi = x - 1 / #round(max_df, eps) dot f(x)$.
  ]
  let psi = (x, y) => (y - 1 / max_dg * g(x, y))
  text += [
      Найдена $#sym.psi = y - 1 / #round(max_dg, eps) dot g(x)$.
  ]
  let q1 = find_max_system((x, y)=> calc.abs(partial_x(phi, x, y)) + calc.abs(partial_y(phi, x, y)), a, b, c, d)

  let q2 = find_max_system((x, y)=> calc.abs(partial_x(psi, x, y)) + calc.abs(partial_y(psi, x, y)), a, b, c, d)
  text += [Полученное $q = #round(calc.max(q1, q2), eps).$]
  if round(q1, eps) > 1 or round(q2, eps) > 1 {
    return text + [Достаточное условие сходимости не выполняется.]
  } else {
    let iter = 1
    let x = a
    let y = c
    let x1 = phi(x, y)
    let y1 = psi(x, y)
    while (calc.abs(x1 - x) > eps or calc.abs(y1 - y) > eps) {
      x = x1
      y = y1
      x1 = phi(x, y)
      y1 = psi(x, y)
      iter += 1
      if (iter > 1000) {
        return [#text(color: red)[Не получилось достичь необходимой точности.]]
      }
    }
    let num_dots = 1000;
    let xa = lq.linspace(a * 1.2, b * 1.2, num: num_dots)
    let ya = lq.linspace(c * 1.2, d * 1.2, num: num_dots)
    let fy0 = ()
    let fx0 = ()
    let gy0 = ()
    let gx0 = ()
    for i in range(0, num_dots) {
      for j in range(0, num_dots){
      if round(f(xa.at(i), ya.at(j)), eps) == 0 {
        fx0.push(xa.at(i))
        fy0.push(ya.at(j))
      }
      if round(g(xa.at(i), ya.at(j)), eps) == 0 {
        gx0.push(xa.at(i))
        gy0.push(ya.at(j))
      }
      }

    }
    return [
      == Решение системы методом простых итераций
    ] + text + [
      Найден вектор решений: $(#round(x1, eps), #round(y1, eps))$. \
      Вектор невязок для данного решения: $(#round(calc.abs(x1 - x), eps), #round(calc.abs(y1 - y), eps))$.
      #figure(
        caption: [График функций в области $x in (#a ; #b), y in (#c ; #d)$],
        lq.diagram(
          xlabel: $x$,
          ylabel: $y$,
          xlim: (a, b),
          ylim: (c, d),
          lq.plot(
            fx0,
            fy0,
          ),
          lq.plot(
            gx0,
            gy0,
          ),
        )
      )
    ]
  }
}