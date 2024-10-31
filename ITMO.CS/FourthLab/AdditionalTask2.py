import sys
import yaml
import re


def countEnclosure(line):
    return len(re.match(r'^\s*', line).group())


def isYamlValid(file):
    try:
        yaml.safe_load(file)
    except yaml.YAMLError as e:
        print(f"YAML Validation Error: {e}")
        sys.exit()


def yamlToPyDict(file):
    parsedYaml = {}
    anchors = {}
    currentDict = parsedYaml
    currentAnchor = anchors
    dictStack = []
    anchorStack = []
    currentList = []
    lastAnchorEnclosure = -1
    lastEnclosure = -1
    isAnchor = False
    isStringArray = False
    isString = False

    for line in file:
        line = line.rstrip()
        if not line or line.startswith("#"):
            continue

        enclosure = countEnclosure(line)

        matchKeyValue = re.match(r'^\s*(\-)?\s*([^:]+)\s*:\s*(.*)', line)
        matchAnchor = re.match(r'^\s*([^:]+)\s*:\s*&(\S+)', line)
        matchArrayItem = re.match(r'^\s*-\s*(.*)', line)

        if enclosure < lastEnclosure and isStringArray:
            isStringArray = False

        if isStringArray and matchArrayItem:
            currentList.append(matchArrayItem.group(1))
            continue

        if currentList:
            if isAnchor:
                currentAnchor = anchorStack[-1][0]
                currentAnchor[key] = currentList
            else:
                currentDict = dictStack[-1][0]
                currentDict[key] = ' '.join(currentList) if isString else currentList
            currentList = []

        if matchAnchor:
            key, anchorName = matchAnchor.groups()
            isAnchor = True
            key = key.strip()
            if enclosure > lastAnchorEnclosure:
                anchorStack.append([currentAnchor, enclosure])
            elif enclosure < lastAnchorEnclosure:
                while enclosure != anchorStack[-1][1]:
                    anchorStack.pop()
                currentAnchor = anchorStack[-1][0]
            currentAnchor[key] = {}
            currentAnchor = currentAnchor[key]
            lastAnchorEnclosure = enclosure
            continue

        if matchKeyValue:
            key, value = matchKeyValue.groups()[1], matchKeyValue.groups()[2]
            key = key.strip()
            value = value.strip()

            if isAnchor:
                isAnchor = False
                currentAnchor[key] = value if value else {}
                if not value:
                    currentAnchor = currentAnchor[key]
            else:
                if enclosure > lastEnclosure:
                    dictStack.append([currentDict, enclosure])
                elif enclosure < lastEnclosure:
                    while enclosure != dictStack[-1][1]:
                        dictStack.pop()
                    currentDict = dictStack[-1][0]
                currentDict[key] = value if value else {}
                if not value:
                    currentDict = currentDict[key]
                lastEnclosure = enclosure

    return parsedYaml


def traverseDict(dictionary, xmlString='<?xml version="1.0" encoding="UTF-8" ?>\n<Root>\n', openedKeys=[], currentDepth=1):
    for key, value in dictionary.items():
        if isinstance(value, dict):
            xmlString += ' ' * 4 * currentDepth + f'<{key}>\n'
            openedKeys.append(' ' * 4 * currentDepth + f'</{key}>\n')
            xmlString = traverseDict(value, xmlString, openedKeys, currentDepth + 1)
        else:
            xmlString += ' ' * 4 * currentDepth + f'<{key}>{value}</{key}>\n'
    xmlString += openedKeys.pop() if openedKeys else ''
    return xmlString


def pyDictToXml(pyDict):
    return traverseDict(pyDict) + '</Root>\n'


def yamlToXml(path):
    with open(path, 'r', encoding="utf-8") as file:
        isYamlValid(file)

    with open(path, 'r', encoding="utf-8") as file:
        yamlDict = yamlToPyDict(file)

    with open('AdditionalTask2Output.xml', 'w', encoding='utf-8') as file:
        file.write(pyDictToXml(yamlDict))


yamlToXml("schedule.yml")
