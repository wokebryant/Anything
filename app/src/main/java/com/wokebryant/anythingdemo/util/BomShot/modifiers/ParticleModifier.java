package com.wokebryant.anythingdemo.util.BomShot.modifiers;

import com.wokebryant.anythingdemo.util.BomShot.Particle;

public interface ParticleModifier {

	/**
	 * modifies the specific value of a particle given the current miliseconds
	 * @param particle
	 * @param miliseconds
	 */
	void apply(Particle particle, long miliseconds);

}
