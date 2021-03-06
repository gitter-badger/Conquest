package tk.hes.conquest.particle;

import me.nibby.pix.RenderContext;

import java.util.ArrayList;

/**
 *
 * @author Kevin Yang
 */
public class ParticleManager {

	private static final ParticleManager instance = new ParticleManager();

	private ArrayList<Particle> particles = new ArrayList<>();

	public void render(RenderContext c) {
		for(Particle p : particles) {
			p.render(c);
		}
	}

	public void update(double delta) {
		for(int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			p.update(delta);
			if(p.isRemoved()) {
				particles.remove(p);
				i--;
			}
		}
	}

	public void spawn(Particle p) {
		p.onSpawn();
		particles.add(p);
	}

	public static ParticleManager get() {
		return instance;
	}

	private ParticleManager() {}

}
