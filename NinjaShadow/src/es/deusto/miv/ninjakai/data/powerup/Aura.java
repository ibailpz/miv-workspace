package es.deusto.miv.ninjakai.data.powerup;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import es.deusto.miv.ninjakai.data.Accumulator;
import es.deusto.miv.ninjakai.data.PowerUp;
import es.deusto.miv.ninjakai.data.PowerUp.HitPowerUp;

public class Aura extends PowerUp implements HitPowerUp {

	private int hits;

	public Aura(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			Accumulator acc, int hits) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, acc);
		setScale(0.1f);
		this.hits = hits;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	@Override
	public boolean newHit() {
		hits--;
		if (hits <= 0) {
			return true;
		}
		return false;
	}
}
