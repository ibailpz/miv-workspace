package es.deusto.miv.ninjakai.data.powerup;

import es.deusto.miv.ninjakai.data.PowerUp;

public class Backup extends PowerUp{
	
	private int blocks;

	public Backup(int level, int blocks) {
		super(level);
		this.blocks = blocks;
	}

	public int getBlocks() {
		return blocks;
	}

	public void setBlocks(int blocks) {
		this.blocks = blocks;
	}
}
