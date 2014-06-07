package es.deusto.miv.ninjakai.data.powerup;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import es.deusto.miv.ninjakai.data.Accumulator;
import es.deusto.miv.ninjakai.data.PowerUp;
import es.deusto.miv.ninjakai.data.PowerUp.TimedPowerUp;

public class ExtraPoints extends PowerUp implements TimedPowerUp {

	private long time;
	private float passedTime = 0;
	private float multIncrease;

	public ExtraPoints(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			Accumulator acc, float multIncrease, long time) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, acc);
		setScale(0.2f);
		this.time = time;
		this.multIncrease = multIncrease;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public float getMultIncrease() {
		return multIncrease;
	}

	@Override
	public boolean timePassed(float time) {
		passedTime += time;
		if (passedTime >= this.time) {
			return true;
		}
		return false;
	}
}
