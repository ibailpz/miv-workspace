package es.deusto.miv.ninjakai.data.powerup;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import es.deusto.miv.ninjakai.data.Accumulator;
import es.deusto.miv.ninjakai.data.PowerUp;

public class ExtraPoints extends PowerUp {

	private long time;

	public ExtraPoints(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			Accumulator acc, long time) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, acc);
		setScale(0.2f);
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
