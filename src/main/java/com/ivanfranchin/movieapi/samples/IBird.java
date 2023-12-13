package com.ivanfranchin.movieapi.samples;

public sealed interface IBird permits IChicken, IFlyBird {

	/**
	 * getScores
	 *
	 * @return
	 */
	int getScores();

	default double getDistance() {
		return 0;
	}
}
