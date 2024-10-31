import timeit

tasksList = ['MainTaskAndAdditionalTask3','AdditionalTask1','AdditionalTask2']

for name in tasksList:
    print(name + ": ", timeit.timeit(f'import {name};{name}.yamlToXml("schedule.yml")',number=100))