/**
 * 
 * @author Adanna
 * @purpose This class is to store fitness
 *
 */
public class Fitness {
	Double[] avg;
	Double[] best;
	Double[] worst;
	int length;

	Fitness(int numIters) {
		avg = new Double[numIters];
		best = new Double[numIters];
		worst = new Double[numIters];
		length = numIters;
	}

}
