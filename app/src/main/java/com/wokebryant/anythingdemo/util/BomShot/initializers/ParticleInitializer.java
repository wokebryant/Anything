package com.wokebryant.anythingdemo.util.BomShot.initializers;


import com.wokebryant.anythingdemo.util.BomShot.Particle;

import java.util.Random;

public interface ParticleInitializer {

	void initParticle(Particle p, Random r);

}
