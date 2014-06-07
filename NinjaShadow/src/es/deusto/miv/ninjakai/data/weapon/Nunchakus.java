package es.deusto.miv.ninjakai.data.weapon;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import es.deusto.miv.ninjakai.GameActivity;
import es.deusto.miv.ninjakai.data.PaintPosition;
import es.deusto.miv.ninjakai.data.Weapon;

public class Nunchakus extends Weapon {

	public Nunchakus(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		setName("Nunchakus");
		setRank(1);
		positions = new ArrayList<Integer>(1);
		setScale(0.35f);
	}

	@Override
	public void protect(int area) {
		if (!positions.isEmpty() && !positions.contains(area)) {
			for (int i = 0; i < 5; i++) {
				areaObservers.get(i).onAreaUnprotected();
			}
		}
		positions.clear();

		if (area == 0 || area == 1) {
			positions.add(0);
			positions.add(1);
		} else if (area == 2 || area == 4) {
			positions.add(2);
			positions.add(4);
		} else
			positions.add(3);

		if (areaObservers.get(area) != null) {
			for (Integer i : positions)
				areaObservers.get(i).onAreaProtected();
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
		case 1:
			this.setX(GameActivity.CAM_WIDTH / 2);
			this.setY(GameActivity.CAM_HEIGHT / 2 + 50);
			this.setRotation(0);
			break;
		case 2:
		case 4:
			this.setX(GameActivity.CAM_WIDTH / 2);
			this.setY(GameActivity.CAM_HEIGHT / 2 - 35);
			this.setRotation(0);
			break;
		case 3:
			this.setX(GameActivity.CAM_WIDTH / 2);
			this.setY(GameActivity.CAM_HEIGHT / 2 - 225);
			this.setRotation(90);
			break;
		default:
			break;
		}
		return null;
	}
}
