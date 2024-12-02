import csv
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

with open("SecondTask.csv", encoding='utf-8') as file:
    file.readline()
    fileReader = csv.reader(file, delimiter=";")
    arr = [[], [], [], []]
    col = ["Open", "High", "Low", "Close"]
    dates = ['12.09.2018', '12.10.2018', '12.11.2018', '12.12.2018']
    count = 0
    for row in fileReader:
        if row[0] == '12.09.2018':
            arr[0].append([int(row[1]), int(row[2]), int(row[3]), int(row[4])])
        elif row[0] == '12.10.2018':
            arr[1].append([int(row[1]), int(row[2]), int(row[3]), int(row[4])])
        elif row[0] == '12.11.2018':
            arr[2].append([int(row[1]), int(row[2]), int(row[3]), int(row[4])])
        elif row[0] == '12.12.2018':
            arr[3].append([int(row[1]), int(row[2]), int(row[3]), int(row[4])])
        count += 1

    for i in range(4):
        data = pd.DataFrame(arr[i], columns=col)
        plt.subplot(1, 4, i + 1)
        sns.boxplot(data)
        plt.title(dates[i])

    plt.subplots_adjust(wspace=0.5)
    plt.show()
