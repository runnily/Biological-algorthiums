
/*
 * This specifies the fitness function; this is predefined
 * by the fitness function.
 */
import net.sourceforge.jswarm_pso.Particle;

public class MyParticle extends Particle {
	private static final int N = 10;

	// Create a 2-dimentional particle
	public MyParticle() {
		super(N);
	}
}
