import re
testTasks = ["Высокоскоростной поезд Сапсан отправляется в 15:20 пробудет в пути 4 часа и прибудет в Москву не позднее 19:20:01.",
             "Игра началась в 11:00 и закончилась в 12:52:59 со счетом 32:11.",
             "Мы хотели встретиться в 16:00",
             "Мои часы до сих пор показывают 21:20:31, хотя уже 22:00",
             "Уважаемые студенты! В эту субботу в 15:00 планируется доп. занятие на 2 часа. То есть в 17:00:01 оно уже точно кончится."]
#   Высокоскоростной поезд Сапсан отправляется в (TBD) пробудет в пути 4 часа и прибудет в Москву не позднее (TBD).
#   Игра началась в (TBD) и закончилась в (TBD) со счетом 32:11.
#   Мы хотели встретиться в (TBD)
#   Мои часы до сих пор показывают (TBD), хотя уже (TBD)
#   Уважаемые студенты! В эту субботу в (TBD) планируется доп. занятие на 2 часа. То есть в (TBD) оно уже точно кончится.


def checkInput(n):  # Функция проверки входных данных на возможные ошибки
    if n == "":
        raise Exception('Ошибка ввода. Кажется вы ничего не ввели =-(.')


def timeManagement(txt):  # Функция для замены времени в сообщении на (TBD)
    try:
        checkInput(txt)
        return re.sub("((?:2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9])|(?<!:)((?:2[0-3]|[01][0-9]):[0-5][0-9])", "(TBD)", txt, count=0)

    except Exception as e:
        return e


for txt in testTasks:
    print("Введите текст для замены времени на (TBD): " + txt)
    print(timeManagement(txt))

print(timeManagement(input("Введите текст для замены времени на (TBD): ")))
