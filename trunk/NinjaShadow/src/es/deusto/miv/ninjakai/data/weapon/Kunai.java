package es.deusto.miv.ninjakai.data.weapon;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import es.deusto.miv.ninjakai.data.Weapon;

public class Kunai extends Weapon {

	public Kunai(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		setName("Kunai");
		setRank(1);
		positions = new ArrayList<Integer>(1);
	}

	@Override
	public void protect(int area) {
		if (!positions.isEmpty() && areaObservers.get(positions.get(0)) != null) {
			areaObservers.get(positions.get(0)).onAreaUnprotected();
		}
		positions.clear();
		positions.add(area);
		if (areaObservers.get(area) != null) {
			areaObservers.get(area).onAreaProtected();
		}
	}

	@Override
	public double getSpeed(int[] from, int[] to) {
		return 1;
	}

	@Override
	public String getWeaponGraphics() {
		return "";
	}

}
