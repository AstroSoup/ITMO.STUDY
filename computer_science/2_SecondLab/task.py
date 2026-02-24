def checkInput(n):  # Функция проверки входных данных на возможные ошибки
    if not (set(n) <= {'0', '1'}):
        raise Exception('Ошибка ввода. Во входных данных обнаружен недопустимый символ. Допустимыми являются только символы 1 и 0.')
    if len(n) != 7:
        raise Exception('Ошибка ввода. Входные данные должны содержать ровно 7 цифр.')


def calcSyndrom(msgArray):  # (r3 ⊕ i2 ⊕ i3 ⊕ i4) · (r2 ⊕ i1 ⊕ i3 ⊕ i4) · (r1 ⊕ i1 ⊕ i2 ⊕ i4)
    return str(int(msgArray[3]) ^ int(msgArray[4]) ^ int(msgArray[5]) ^ int(msgArray[6])) + str(int(msgArray[1]) ^ int(msgArray[2]) ^ int(msgArray[5]) ^ int(msgArray[6])) + str(int(msgArray[0]) ^ int(msgArray[2]) ^ int(msgArray[4]) ^ int(msgArray[6]))


def bitErrorChecker(msg):  # Функция проверки и исправления ошибок в закодированном сообщении
    try:
        checkInput(msg)  # Проверка на ошибки ввода и вызов исключений
        msg = list(msg)
        bitErrorIndex = int(calcSyndrom(msg), 2)

        if bitErrorIndex:
            msg[bitErrorIndex - 1] = str((int(msg[bitErrorIndex - 1]) + 1) % 2)  # исправление ошибки в изначальном сообщении
            msg = [msg[i] for i in [2, 4, 5, 6]]  # удаление проверочных разрядов из сообщения
            errorMessage = list('Ошибка здесь:        ')
            errorMessage[len(errorMessage) - 8 + bitErrorIndex] = '↑'  # замена пробела под некорректным битом на символ стрелки
            return 'Верное сообщение (только информационные биты): ' + str("".join(msg)) + " " * 4 + "".join(errorMessage)
        else:
            msg = [msg[i] for i in [2, 4, 5, 6]]  # удаление проверочных разрядов из сообщения
            return 'Ваше сообщение не содержит ошибок. Так держать! Информационные биты сообщения: ' + "".join(msg)
    except Exception as e:
        return e


print(bitErrorChecker(input('Введите сообщение с использованием классического кода Хэмминга(7;4): ')))
