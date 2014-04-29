package es.deusto.miv.ninjakai.data.powerup;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import es.deusto.miv.ninjakai.data.PowerUp;

public class Backup extends PowerUp {

	private int blocks;

	public Backup(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			boolean enabled, int blocks) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, enabled);
		this.blocks = blocks;
	}

	public Backup(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, int blocks) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		this.blocks = blocks;
	}

	public int getBlocks() {
		return blocks;
	}

	public void setBlocks(int blocks) {
		this.blocks = blocks;
	}
}
