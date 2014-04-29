package es.deusto.miv.ninjakai.data.powerup;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import es.deusto.miv.ninjakai.data.PowerUp;

public class Aura extends PowerUp {

	private int hits;

	public Aura(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, int hits) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		this.hits = hits;
	}

	public Aura(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			boolean enabled, int hits) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, enabled);
		this.hits = hits;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}
}
