import java.io.FileWriter;
import java.io.IOException;

/*
 * Name: Adanna Obibuaku
 * Purpose: The purpose of this class is to run the GA
 * 			with the magic number off 30 and average the best fitness for 
 * 			each run corresponding to a particular generation.
 */
public class AvgGA {

	public static Fitness avg(String fname, String cname, int numIters) throws IOException {
		/*
		 * Purpose: The purpose of this function is to run the GA 30's to get the
		 * average best fit of each generation
		 */
		Fitness averages = new Fitness(numIters);

		CW1_GA alg = new CW1_GA();
		alg.parseParams(cname);
		for (int runs = 0; runs < 30; runs++) {
			Fitness fitness = alg.run(); // provides the fitness
			for (int i = 0; i < averages.length; i++) {
				try {
					averages.best[i] += fitness.best[i]; // Increment fitness
					averages.avg[i] += fitness.avg[i]; //
					averages.worst[i] += fitness.worst[i];
				} catch (Exception e) {
					// when array is not initialised
					averages.best[i] = fitness.best[i];
					averages.avg[i] = fitness.avg[i];
					averages.worst[i] = fitness.worst[i];
				}
			}
		}

		FileWriter writer = new FileWriter(fname, false);
		writer.write(String.format("%s,%s,%s,%s\n", "generation", "bestFit", "avgFit", "worstFit"));

		for (int i = 0; i < averages.length; i++) {
			averages.best[i] = averages.best[i] / 30;
			averages.avg[i] = averages.avg[i] / 30;
			averages.worst[i] = averages.worst[i] / 30;
			// average results

			writer.write(i + 1 + "," + averages.best[i] + "," + averages.avg[i] + "," + averages.worst[i] + "\n");
		}

		writer.close();

		return averages;
	}

	/// GATSIZE
	public static void main(String[] args) {
		String fname = "bestFits/bestFitsPop/";
		String pname = "parameters/parametersPops/";
		try {
			avg(fname + "data_1.csv", pname + "GA1.config", 1000);
			avg(fname + "data_2.csv", pname + "GA2.config", 100);
			avg(fname + "data_3.csv", pname + "GA3.config", 10);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
