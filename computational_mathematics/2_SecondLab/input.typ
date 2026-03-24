#import "@preview/typcas:0.1.0"




/*
  Парсит стандартную арифметику (+, -, *, /) для переменных и численных литералов. 
  Поддерживает функции:
    x^y --- возведение в степень
    log(x), ln(x) --- натуральный логарифм
    log_y(x) --- логарифм по основанию y (АКЧУАЛЛИ НЕ УМЕЕТ В ПЕРЕМЕННОЕ ОСНОВАНИЕ)
    sin(x), cos(x), tg(x) --- трига
    arcsin(x), arccos(x), arctg(x) --- арктрига
    sqrt(x) --- квадратный корень

    Может быть дополнено.
*/
#let compile(node) = {
  if node.type == "num" {
    return (x, y) => node.val
  } else if node.type == "var" {
    if node.name == "x"{
      return (x, y) => x
    } else {
      return (x, y) => y
    }
  } else if node.type == "neg" {
    let f = compile(node.arg)
    return (x, y) => -f(x, y)
  } else if node.type == "add" {
    let fs = node.args.map(compile)
    return (x, y) => fs.fold(0, (acc, f) => acc + f(x, y))
  } else if node.type == "div" {
    let fnum = compile(node.num)
    let fden = compile(node.den)
    return (x, y) => fnum(x, y) / fden(x, y)
  } else if node.type == "pow" {
    let fbase = compile(node.base)
    let fexp = compile(node.exp)
    return (x, y) => calc.pow(fbase(x, y), fexp(x, y))
  } else if node.type == "mul" {
    let fs = node.args.map(compile)
    return (x, y) => fs.fold(1, (acc, f) => acc * f(x, y))
  } else if node.type == "log" {
    let farg = compile(node.arg)
    let fbase = compile(node.base)
    return (x, y) => calc.log(farg(x, y), base: fbase(x, y))
    // Сложные функции
  } else if node.type == "func" {
    let farg = compile(node.arg)

    if node.name == "sin" {
      return (x, y) => calc.sin(farg(x, y))
    } else if node.name == "arcsin" {
      return (x, y) => calc.asin(farg(x, y)).rad()
    } else if node.name == "tg" or node.name == "tan" {
      return (x, y) => calc.tan(farg(x, y))
    } else if node.name == "arctg"{
      return (x, y) => calc.arctg(farg(x, y)).rad()
    } else if node.name == "cos"{
      return (x, y) => calc.cos(farg(x, y))
    } else if node.name == "arccos"{
      return (x, y) => calc.acos(farg(x, y)).rad()
    } else if node.name == "ln"{
      return (x, y) => calc.ln(farg(x, y))
    } else {
      panic("Unknown function: " + node.name)
    }
  } else {
    panic("Unknown node type: " + node.type)
  }
}

#let parse(input) = {
  let casf = typcas.cas-parse(input)

  let simplified_casf = typcas.simplify(casf)
  let compiledf = compile(simplified_casf)
  return compiledf
}



// TODO: implement funciton to parse systems.
