import sys
import yaml


def countEnclosure(string):  # Функция подсчета вложенности данных в формате YAML
    Enclosure = 0
    for char in string:
        if char == " ":
            Enclosure += 1
        else:
            return Enclosure


def isYamlValid(file):
    try:
        yaml.safe_load(file)  # Загрузка YAML и проверка синтаксиса
    except yaml.YAMLError as e:
        print(f"YAML Validation Error: {e}")  # Вывод ошибок, если они есть
        sys.exit()


def yamlToPyDict(file):  # Функция для парсинга YAML - файлов в python - словарь
    parsedYaml = {}
    anchors = {}
    currentDict = parsedYaml
    currentAnchor = anchors
    currentList = []
    dictStack = []
    anchorStack = []
    lastAnchorEnclosure = -1
    lastEnclosure = -1
    isStringArray = False
    isString = False
    isAnchor = False  # Начало ужасных макарон...
    for line in file:

        if not line.strip() or line.strip().startswith("#"):  # Пропуск пустых строк и комментариев
            continue

        if line.strip().startswith("- ") and ':' in line:  # Приведение словарей к виду key:value
            line = line.replace('-', '', 1)

        enclosure = countEnclosure(line)  # подсчет отступов для определения вложенности

        if enclosure < lastEnclosure:  # Закрытие блока ввода строк
            if isStringArray:
                isStringArray = False

        if (line.strip().startswith('- ') and ':' not in line) or isStringArray:  # Добавление строк или элементов последовательности в массив
            currentList.append(line.lstrip(" -").rstrip())
            continue

        if currentList:  # Если массив элементов не пуст записать его в предыдущий блок
            if isAnchor:  # Если блок - якорь добавить в словарь якорей
                currentAnchor = anchorStack[-1][0]
                currentAnchor[key] = currentList
            else:
                currentDict = dictStack[-1][0]
                if isString:  # Если строка(>), то ввод списка через ' '
                    currentDict[key] = ' '.join(currentList)
                else:
                    currentDict[key] = currentList
            currentList = []

        if line.strip().split(":", 1)[1].strip().startswith('&'):  # Установка флага якоря
            isAnchor = True

        key, value = line.strip().split(":", 1)
        key = key.strip()
        value = value.strip()

        if enclosure < lastAnchorEnclosure:  # если блок закончился - проверка и снятие флагов
            if isAnchor:
                isAnchor = False
            if isString:
                isString = False

        if isAnchor:  # Обработка якорей

            key = key.strip()
            if value.strip().startswith("&"):
                key = value.strip().lstrip('& ')
                value = ''

            if enclosure > lastAnchorEnclosure:
                anchorStack.append([currentAnchor, enclosure])
            elif enclosure < lastAnchorEnclosure:
                while enclosure != anchorStack[-1][1]:
                    anchorStack.pop()
                currentAnchor = anchorStack[-1][0]

            if value:

                if value == '|' or value == '>':
                    isStringArray = True
                    currentAnchor[key] = {}
                    currentAnchor = currentAnchor[key]
                    if value == '>':
                        isString = True
                else:
                    if key == '<<' and value.lstrip('*') in anchors.keys():
                        for anchorKey, anchorValue in anchors[value.lstrip('*')].items():
                            currentAnchor[anchorKey] = anchorValue
                    else:
                        currentAnchor[key] = value
            else:
                currentAnchor[key] = {}
                currentAnchor = currentAnchor[key]
            lastAnchorEnclosure = enclosure

        else:  # Обработка обычных блоков

            if enclosure > lastEnclosure:
                dictStack.append([currentDict, enclosure])
            elif enclosure < lastEnclosure:
                while enclosure != dictStack[-1][1]:
                    dictStack.pop()
                currentDict = dictStack[-1][0]

            lastEnclosure = enclosure

            if value:

                if value == '|' or value == '>':

                    isStringArray = True
                    currentDict[key] = {}
                    currentDict = currentDict[key]
                    if value == '>':
                        isString = True
                else:

                    if key == '<<' and value.lstrip('*') in anchors.keys():
                        for anchorKey, anchorValue in anchors[value.lstrip('*')].items():
                            currentDict[anchorKey] = anchorValue
                    else:
                        currentDict[key] = value

            else:
                currentDict[key] = {}
                currentDict = currentDict[key]

    return parsedYaml


def traverseDict(dictionary, string=f'<?xml version="1.0" encoding="UTF-8" ?>\n<Root>\n', openedKeys=[], currentDepth=1):
    for key, value in dictionary.items():
        if isinstance(value, dict):
            string += 4*currentDepth * ' ' + f'<{key}>\n'
            openedKeys.append(4*currentDepth * ' ' + f'</{key}>\n')
            string = traverseDict(dictionary[key], string, openedKeys, currentDepth + 1)
        else:
            string += 4*currentDepth * ' ' + f'<{key}>{value}</{key}>\n'
            continue
        string += openedKeys.pop()
    return string


def pyDictToXml(pyDict):
    return traverseDict(pyDict) + f'</Root>\n'


def yamlToXml(path, mode='r', buffering=-1, encoding="utf-8", errors=None, newline=None, closefd=True, opener=None):

    #with open(path, mode, buffering, encoding, errors, newline, closefd, opener) as file:
        #isYamlValid(file)
    with open(path, mode, buffering, encoding, errors, newline, closefd, opener) as file:
        yamlDict = yamlToPyDict(file)

    with open('output.xml', 'w', encoding='utf-8') as file:
        file.write(pyDictToXml(yamlDict))


yamlToXml("schedule.yml")
#yamlToXml(input("Введите путь до файла в формате YAML: "))