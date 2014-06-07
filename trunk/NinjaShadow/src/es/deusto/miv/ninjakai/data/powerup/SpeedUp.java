package es.deusto.miv.ninjakai.data.powerup;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import es.deusto.miv.ninjakai.data.Accumulator;
import es.deusto.miv.ninjakai.data.PowerUp;
import es.deusto.miv.ninjakai.data.PowerUp.TimedPowerUp;

public class SpeedUp extends PowerUp implements TimedPowerUp {

	private float time;
	private float passedTime = 0;
	private float slowerRatio;

	public SpeedUp(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			Accumulator acc, float slowerRatio, float time) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, acc);
		setScale(0.8f);
		this.time = time;
		this.slowerRatio = slowerRatio;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}
	
	public float getSlowerRatio() {
		return slowerRatio;
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
