#https://matplotlib.org/tutorials/introductory/pyplot.html
#https://numpy.org/doc/stable/reference/generated/numpy.genfromtxt.html
"""
Created on Oct 12 21:53:59 2020
@author: Adanna Obibuaku
This is purpose of this file, is used to illustrate plots based
on the data from PSO and GA.
"""

import matplotlib.pyplot as plt
import numpy as np

def plots(*args):
    """
        Description:
            This function is used for drawing the plots on the graphs
        Args:
            *args (str): This takes an array of arugments of csv files
     """
    for arg in args:
        generation = np.genfromtxt(arg[0], delimiter=",", skip_header=1, usecols=0)
        bestFits = np.genfromtxt(arg[0], delimiter=",", skip_header=1, usecols=1)
        plt.plot(generation, bestFits, label=arg[1])
    #plt.axis([100,400,0,0.8])
    plt.legend()
    plt.xlabel("Generation")
    plt.ylabel("Avg Best Fit")
    plt.show()

def threePlots(*args):
    """
        Description:
            This function is used for drawing plots on graphs. This would be used
            to illutrsates the plots of best fitness, average fitness and worst fitness
        Args:
            *args (str): This takes an array of arugments of csv files
     """
    fig,a =  plt.subplots(1,3, figsize=(20,5))
    for arg in args:
        generation = np.genfromtxt(arg[0], delimiter=",", skip_header=1, usecols=0)
        bestFits = np.genfromtxt(arg[0], delimiter=",", skip_header=1, usecols=1)
        avgFits = np.genfromtxt(arg[0], delimiter=",", skip_header=1, usecols=2)
        worstFits = np.genfromtxt(arg[0], delimiter=",", skip_header=1, usecols=3)

        a[0].plot(generation, bestFits, label=arg[1])
        a[0].set_xlabel("Generation")
        a[0].set_ylabel("Avg Best Fit")
        a[0].set_title("Best Fits")

        a[1].plot(generation, avgFits, label=arg[1])
        a[1].set_xlabel("Generation")
        a[1].set_ylabel("Avg mean Fit")
        a[1].set_title("Average Fits")

        a[2].plot(generation, worstFits, label=arg[1])
        a[2].set_xlabel("Generation")
        a[2].set_ylabel("Avg Worst Fit")
        a[2].set_title("Worst Fits")

    plt.legend()
    plt.show()


if __name__ == "__main__":
    fname = "PSO/2/velocity/data_"
    para = "velocity: "
    threePlots(

        (fname+"1.csv", para + "1"),
        (fname+"2.csv", para + "2"),
        (fname+"3.csv", para + "3"),
        (fname+"4.csv", para + "4"),
        (fname+"5.csv", para + "5"),
        (fname+"6.csv", para + "6"),
        (fname+"7.csv", para + "7"),
        (fname+"8.csv", para + "8"),
        (fname+"9.csv", para + "9"),
        (fname+"10.csv", para + "10"),
    )


    
   