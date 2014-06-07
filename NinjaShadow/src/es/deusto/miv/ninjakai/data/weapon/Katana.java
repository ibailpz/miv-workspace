package es.deusto.miv.ninjakai.data.weapon;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import es.deusto.miv.ninjakai.data.PaintPosition;
import es.deusto.miv.ninjakai.data.Weapon;

public class Katana extends Weapon{
	
	public Katana(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		setName("Katana");
		setRank(1);
		positions = new ArrayList<Integer>(1);
	}

	@Override
	public void protect(int area) {
		if (!positions.isEmpty() && !positions.contains(area)) {
			for(int i=0; i<5; i++){
				areaObservers.get(i).onAreaUnprotected();
			}
		}
		positions.clear();
		positions.add(area);
		if (areaObservers.get(area) != null) {
			areaObservers.get(area).onAreaProtected();
		}
		getPaintPosition();
	}

	@Override
	public double getSpeed(int[] from, int[] to) {
		return 1;
	}

	@Override
	public String getWeaponGraphics() {
		return "";
	}

	@Override
	public PaintPosition getPaintPosition() {
		// TODO Auto-generated method stub
		return null;
	}

}
