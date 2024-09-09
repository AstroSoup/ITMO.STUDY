def checkInput(n): # Функция проверяет входные данные на возможные ошибки
    if not (set(n) <= {'0','1'}):
        raise Exception('Ошибка. Во входных данных обнаружен недопустимый символ. Число в Фибоначчиевой системе счисления состоит только из 1 и 0.')
    elif '11' in n:
        raise Exception('Ошибка. Число в Фибоначчиевой системе счисления не может иметь 2 единиц идущих подряд.')
    elif n == '':
        raise Exception('Ошибка. Введена пустая строка.')

def fibGenerate(n): # Функция для генерации последовательности Фибоначчи
    fib = [1,2]
    while len(fib) < n:
        fib.append(fib[-1] + fib[-2])
    return fib

def fibToDec(number): # Функция перевода числа из Фибоначчиевой системы счисления в десятичную
    try:
        isNegative = False # Флаг показывающий отрицательно ли введенное число
        number = number.replace(' ','')
        if number[0] == '-':
            number = number.lstrip('-')
            isNegative = True
        checkInput(number) # Проверка на ошибки ввода и вызов исключений
        ans = 0
        number = number.lstrip('0') # Удаление ведущих нулей в числе(если таковые имеются), для уменьшения времени вычисления последовательности Фибоначчи
        for k,v in zip(list(number[::-1]),fibGenerate(len(number))):
            ans += int(k) * v # Сумма чисел последовательности по индексу которых в введенном числе стоит 1
        if isNegative:
            return -ans
        else:
            return ans
    except Exception as e:
        return e

print(fibToDec(input('Введите число в Фибоначчиевой системе счисления: ')))



