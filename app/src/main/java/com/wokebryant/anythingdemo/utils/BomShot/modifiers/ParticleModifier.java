package com.wokebryant.anythingdemo.utils.BomShot.modifiers;

import com.wokebryant.anythingdemo.utils.BomShot.Particle;

public interface ParticleModifier {

	/**
	 * modifies the specific value of a particle given the current miliseconds
	 * @param particle
	 * @param miliseconds
	 */
	void apply(Particle particle, long miliseconds);

}
