import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import net.sourceforge.jswarm_pso.Neighborhood1D;
import net.sourceforge.jswarm_pso.Swarm;

public class CW1_PSO {
	private static double R = 5.12; // # Specifies legal value -- should not modify.

	// <-- Specifies the parameters of the PSO; we are going to explore these later
	// -->
	private int numParticles; // we could increase
	private int numIters; // change later
	private double neighWeight;
	private double inertiaWeight; // c0
	private double personalWeight; // c1
	private double globalWeight; // c2
	private double maxMinVelocity; // vmax

	// <-- Function which loads the paramter of the file; we will explore these
	// later -->
	public void parseParams(String paramFile) {
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(paramFile));

			Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);

				if (key.equals("numParticles")) {
					numParticles = Integer.parseInt(value);
				} else if (key.equals("neighWeight")) {
					neighWeight = Double.parseDouble(value);
				} else if (key.equals("inertiaWeight")) {
					inertiaWeight = Double.parseDouble(value);
				} else if (key.equals("personalWeight")) {
					personalWeight = Double.parseDouble(value);
				} else if (key.equals("globalWeight")) {
					globalWeight = Double.parseDouble(value);
				} else if (key.equals("maxMinVelocity")) {
					maxMinVelocity = Double.parseDouble(value);
				} else if (key.equals("numIters")) {
					numIters = Integer.parseInt(value);
				} else {
					System.out.println("Unknown parameter " + key);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// <-- Runs the PSO, the core of the program -->
	public Fitness run() {
		// <-- Sets up the number of particles sets up the fitness function -->
		// Create a swarm (using 'MyParticle' as sample particle
		// and 'MyFitnessFunction' as finess function)
		Fitness fitness = new Fitness(numIters);
		Swarm swarm = new Swarm(numParticles, new MyParticle(), new MyFitnessFunction());
		// Set position (and velocity) constraints.
		// i.e.: where to look for solutions

		Neighborhood1D neigh = new Neighborhood1D(numParticles / 10, true);
		swarm.setNeighborhood(neigh);
		swarm.setNeighborhoodIncrement(neighWeight);

		// <-- Parameters of PSO -->
		// Set weights of velocity update formula
		swarm.setInertia(inertiaWeight); // c0
		swarm.setParticleIncrement(personalWeight); // c1
		swarm.setGlobalIncrement(globalWeight); // c2

		// Set limits to velocity value
		swarm.setMaxMinVelocity(maxMinVelocity);

		// Set max and min positions
		swarm.setMaxPosition(+R);
		swarm.setMinPosition(-R);

		// Optimize a few times
		for (int i = 0; i < numIters; i++) {
			swarm.evolve();
			fitness.best[i] = swarm.getBestFitness(); // -> Do how many iteration until it time it begins to converge

			System.out.println("Best fitness: " + swarm.getBestFitness());

			// after particles evolve set avg and worst back to 0
			double avgFitness = 0;
			double worstFitness = 0;

			// A loop to find average fitness and worst fitness
			for (int p = 0; p < numParticles; p++) {
				double particleFitness = swarm.getParticle(p).getFitness();
				avgFitness += particleFitness;
				if (particleFitness > worstFitness) {
					worstFitness = particleFitness;
				}

			}
			fitness.avg[i] = avgFitness / numParticles;
			System.out.println("Average fitness: " + fitness.avg[i]);

			fitness.worst[i] = worstFitness;
			System.out.println("Worst fitness: " + fitness.worst[i]);
		}
		System.out.println("");
		System.out.println(swarm.getBestFitness());
		System.out.println(swarm.toStringStats());

		return fitness;
	}

	public static void main(String[] args) {
		CW1_PSO alg = new CW1_PSO();
		String fname = "PSO/Scenario1.config"; // <-- change to PSO/Scenario2.config to get the file settings
		alg.parseParams(fname);
		alg.run();
	}
}
