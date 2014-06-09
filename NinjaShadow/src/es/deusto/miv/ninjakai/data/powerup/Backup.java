package es.deusto.miv.ninjakai.data.powerup;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import es.deusto.miv.ninjakai.data.Accumulator;
import es.deusto.miv.ninjakai.data.PowerUp;
import es.deusto.miv.ninjakai.data.PowerUp.HitPowerUp;

public class Backup extends PowerUp implements HitPowerUp {

	private int blocks;

	public Backup(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			Accumulator acc, int blocks) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager, acc);
		setScale(0.2f);
		this.blocks = blocks;
	}

	public int getBlocks() {
		return blocks;
	}

	public void setBlocks(int blocks) {
		this.blocks = blocks;
	}

	@Override
	public boolean newHit() {
		blocks--;
		if (blocks <= 0) {
			return true;
		}
		return false;
	}
}
