package es.deusto.miv.ninjakai.data.weapon;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import es.deusto.miv.ninjakai.GameActivity;
import es.deusto.miv.ninjakai.data.PaintPosition;
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
		int area = positions.get(0);
		switch (area) {
		case 0:
			this.setX(GameActivity.CAM_WIDTH / 2 - 80);
			this.setY(GameActivity.CAM_HEIGHT / 2 + 80);
			break;
		case 1:
			this.setX(GameActivity.CAM_WIDTH / 2 + 100);
			this.setY(GameActivity.CAM_HEIGHT / 2 + 80);
			break;
		case 2:
			this.setX(GameActivity.CAM_WIDTH / 2 - 80);
			this.setY(GameActivity.CAM_HEIGHT / 2 - 50);
			break;
		case 3:
			this.setX(GameActivity.CAM_WIDTH / 2);
			this.setY(GameActivity.CAM_HEIGHT / 2 - 100);
			break;
		case 4:
			this.setX(GameActivity.CAM_WIDTH / 2 + 80);
			this.setY(GameActivity.CAM_HEIGHT / 2 - 50);
			break;
		default:
			break;
		}
		return null;
	}

}
