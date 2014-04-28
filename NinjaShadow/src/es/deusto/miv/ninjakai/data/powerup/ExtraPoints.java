package es.deusto.miv.ninjakai.data.powerup;

import es.deusto.miv.ninjakai.data.PowerUp;

public class ExtraPoints extends PowerUp{
	
	private long time;

	public ExtraPoints(int level, long time) {
		super(level);
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
