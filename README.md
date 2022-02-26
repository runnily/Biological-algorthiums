# Biologically-inspired computing for optimisation

This was made as part of CSC3423 coursework

The objective of this coursework is to use biologically-inspired computing for optimisation. 
A (Java) source code is provided of two biologically-inspired algorithms, 
namely a genetic algorithm (GA) and a particle swarm optimisation (PSO) algorithm that 
have already set up to solve a specific optimisation problem, used frequently as a benchmark 
of optimisation algorithms: the 10-dimensional Rastringin function 
(https://en.wikipedia.org/wiki/Rastrigin_function). This is a minimisation function (the lower 
the value of the fitness function, the better). Some basic instructions on how to run these 
programs are at the bottom of this document.
Your task will be to 
1. select and adjust the operators (choice of operators and their 
parameters) of these two algorithms to improve their performance at the given task. That is, 
improve the fitness value of the best solution found by the algorithms, and 
2. critically  assess and compare the suitability of these methods for this particular problem. 
In the process of improving the algorithms you have to deal with two different scenarios with 
different budget of number of fitness calculations. You will notice that the current settings of 
the provided code will call the fitness function for a million times (1000 iterations x 1000 
individuals/particles). Change the settings so you use at most 10000 evaluations (the balance 
of individuals/iterations is up to you) and optimise the parameters again to see what method 
works best with a limited budget of fitness calculations.
Notes:

- There will be a lecture early in the module’s schedule explaining in-depth the coursework. 
- Likewise, the two practical sessions dedicated to GAs and PSO will focus on explaining in 
detail the code you have been provided with and how to use it.
- The parameters used in provided code are specified in a configuration file. You can 
optimise the methods by hand using a trial-and-error procedure, or you can systematically 
explore the range of values for the different parameters. 
