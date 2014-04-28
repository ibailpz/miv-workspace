package es.deusto.miv.ninjakai.data.powerup;

import es.deusto.miv.ninjakai.data.PowerUp;

public class Aura extends PowerUp{

	private int hits;

	public Aura(int level, int hits) {
		super(level);
		this.hits = hits;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}
}
