import yaml
import xmltodict

def yamlToXml(path, mode='r', buffering=-1, encoding="utf-8", errors=None, newline=None, closefd=True, opener=None):
    yamlDict = {}
    with open(path, mode, buffering, encoding, errors, newline, closefd, opener) as file:
        yamlDict['Root'] = yaml.safe_load(file)
    xml_str = xmltodict.unparse(yamlDict, pretty=True)
    with open('output.xml', 'w', encoding='utf-8') as file:
        file.write(xml_str)
yamlToXml('schedule.yml')
#yamlToXml(input("Введите путь до файла в формате YAML: "))