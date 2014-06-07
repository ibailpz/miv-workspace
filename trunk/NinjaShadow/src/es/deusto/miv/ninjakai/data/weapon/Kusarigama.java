package es.deusto.miv.ninjakai.data.weapon;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import es.deusto.miv.ninjakai.GameActivity;
import es.deusto.miv.ninjakai.data.PaintPosition;
import es.deusto.miv.ninjakai.data.Weapon;

public class Kusarigama extends Weapon{

	public Kusarigama(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		setName("Kusarigama");
		setRank(1);
		positions = new ArrayList<Integer>(1);
	}

	@Override
	public void protect(int area) {
		if (!positions.isEmpty()) {
			for(int i=0; i<5; i++){
				areaObservers.get(i).onAreaUnprotected();
			}
		}
		positions.clear();
		
		switch (area) {
		case 0:
			positions.add(0);
			positions.add(1);
			break;
		case 1:
			positions.add(1);
			positions.add(4);
			break;
		case 2:
			positions.add(2);
			positions.add(0);
			break;
		case 3:
			positions.add(3);
			positions.add(2);
			break;
		case 4:
			positions.add(4);
			positions.add(3);
			break;
		default:
			break;
		}
		
		if (areaObservers.get(area) != null) {
			for(Integer i: positions)
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
		this.setRotation(0);
		switch (area) {
		case 0:
			this.setX(GameActivity.CAM_WIDTH / 2);
			this.setY(GameActivity.CAM_HEIGHT / 2 + 150);
			this.setRotation(45);
			break;
		case 1:
			this.setX(GameActivity.CAM_WIDTH / 2 + 150);
			this.setY(GameActivity.CAM_HEIGHT / 2);
			break;
		case 2:
			this.setX(GameActivity.CAM_WIDTH / 2 - 150);
			this.setY(GameActivity.CAM_HEIGHT / 2);
			break;
		case 3:
			this.setX(GameActivity.CAM_WIDTH / 2 - 100);
			this.setY(GameActivity.CAM_HEIGHT / 2 - 100);
			break;
		case 4:
			this.setX(GameActivity.CAM_WIDTH / 2 + 100);
			this.setY(GameActivity.CAM_HEIGHT / 2 - 100);
			break;
		default:
			break;
		}
		return null;
	}
}
