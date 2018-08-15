# -*- coding: utf-8 -*-
# TODO - Replae with your own code 
import matplotlib.pyplot as plt; plt.rcdefaults()
import numpy as np
import matplotlib.pyplot as plt
import csv

x = np.linspace(-1, 1, 100)
y = x**2
plt.plot(x,y)
plt.savefig('fig1.png') # Saves the graph to a file named fig1.png

# Saves the x axis values to a file named output1.csv
with open('output1.csv', 'w') as myfile:
    wr = csv.writer(myfile, quoting=csv.QUOTE_ALL)
    wr.writerow(x)