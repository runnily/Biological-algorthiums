
// 	<--- Class imports --- >

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static java.lang.Math.PI;
import static java.lang.Math.cos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import io.jenetics.DoubleGene;
import io.jenetics.EliteSelector;
import io.jenetics.MeanAlterer;
import io.jenetics.Optimize;
import io.jenetics.Phenotype;
import io.jenetics.SwapMutator;
import io.jenetics.TournamentSelector;
import io.jenetics.UniformCrossover;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.stat.DoubleMomentStatistics;
import io.jenetics.util.DoubleRange;

public class CW1_GA {
	// <--- Parameters of the fitness function --- >
	private static final double A = 10;
	private static final double R = 5.12;
	private static final int N = 10;

	// <--- Variables of the GA --- >
	private int popSize;
	private int numSurvivors;
	private int tournamentSize;
	private double probMutation;
	private double probCrossover;
	private int numIters;

	// <--- Rastringin Function the fitness function code --- >
	private static double fitness(final double[] x) {
		double value = A * N;
		for (int i = 0; i < N; ++i) {
			value += x[i] * x[i] - A * cos(2.0 * PI * x[i]);
		}

		return value;
	}

	// <--- loads values from the parameters file; you can explore this function ---
	// >
	public void parseParams(String paramFile) {
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(paramFile));

			Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);

				if (key.equals("popSize")) {
					popSize = Integer.parseInt(value);
				} else if (key.equals("numSurvivors")) {
					numSurvivors = Integer.parseInt(value);
				} else if (key.equals("tournamentSize")) {
					tournamentSize = Integer.parseInt(value);
				} else if (key.equals("probMutation")) {
					probMutation = Double.parseDouble(value);
				} else if (key.equals("probCrossover")) {
					probCrossover = Double.parseDouble(value);
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

	// for each run 30.
	// get generation
	// if generation is greater than previous generation replace
	// else dont.

	// <--- This sets up the GA --- >
	public Fitness run() {
		Fitness fitness = new Fitness(numIters);
		Phenotype<DoubleGene, Double>[] population;
		// < --- specifying the operators for our GA. --- >
		// Engine defines the cycle of our GA, builder would define the population
		final Engine<DoubleGene, Double> engine = Engine.builder(CW1_GA::fitness,
				// Codec for 'x' vector.
				Codecs.ofVector(DoubleRange.of(-R, R), N)).populationSize(popSize).optimize(Optimize.MINIMUM)
				.survivorsSize(numSurvivors).survivorsSelector(new EliteSelector<>(numSurvivors))
				.offspringSelector(new TournamentSelector<>(tournamentSize)).alterers(
						// new Mutator<>(probMutation),
						// new GaussianMutator<>(probMutation),
						// new UniformCrossover<>(probCrossover))
						new SwapMutator<>(probMutation), new UniformCrossover<>(probCrossover),
						new MeanAlterer<>(probMutation))
				.build();

		// <--- This is specifying the numbers of runs of GA -->
		final EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();
		// readline, get number add it
		// override it in cvs file.
		final Phenotype<DoubleGene, Double> result = engine.stream().limit(numIters).peek(statistics)
				.peek(r -> System.out.println("Generation : " + r.getGeneration() + " avg: "
						+ ((DoubleMomentStatistics) statistics.getFitness()).getMean() + " best " + r.getBestFitness()
						+ " worst: " + r.getWorstFitness()))
				.peek(r -> fitness.best[(int) (r.getGeneration() - 1)] = r.getBestFitness())
				.peek(r -> fitness.avg[(int) (r.getGeneration() - 1)] = ((DoubleMomentStatistics) statistics
						.getFitness()).getMean())
				.peek(r -> fitness.worst[(int) (r.getGeneration() - 1)] = r.getWorstFitness())
				// Uncomment the following line to get updates at each iteration
				// .peek(r -> System.out.println(statistics))
				.collect(toBestPhenotype());

		System.out.println(statistics);

		System.out.println(result);
		return fitness;
	}

	public static void main(String[] args) {
		CW1_GA alg = new CW1_GA();
		String fname = "GA/Scenario1.config"; // <-- change to GA/Scenario2.config to get the file settings
		alg.parseParams(fname);
		alg.run();
	}
}
