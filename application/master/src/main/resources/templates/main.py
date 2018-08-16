
# TODO - Replae with your own code

import numpy as np
import csv

x = np.linspace(-1, 1, 100)
y = x**2

# Saves the x axis values to a file named output1.csv
with open('output1.csv', 'w') as myfile:
    wr = csv.writer(myfile, quoting=csv.QUOTE_ALL)
    wr.writerow(x)