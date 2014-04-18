package es.deusto.miv.ninjakai.data;

import java.util.ArrayList;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Ninja extends Sprite {

	private Weapon weapon;
	private int lifes;
	private float points;
	private int bonus;
	private ArrayList<PowerUp> powerUps;

	public Ninja(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
	}

	public Ninja(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}

	public Ninja(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			Weapon weapon, int lifes, float points, int bonus,
			ArrayList<PowerUp> powerUps) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.weapon = weapon;
		this.lifes = lifes;
		this.points = points;
		this.bonus = bonus;
		this.powerUps = powerUps;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public int getLifes() {
		return lifes;
	}

	public void setLifes(int lifes) {
		this.lifes = lifes;
	}

	public float getPoints() {
		return points;
	}

	public void setPoints(float points) {
		this.points = points;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public ArrayList<PowerUp> getPowerUps() {
		return powerUps;
	}

	public void setPowerUps(ArrayList<PowerUp> powerUps) {
		this.powerUps = powerUps;
	}

	public void protect(int area) {
		if (weapon != null) {
			weapon.protect(area);
		}
	}

	public boolean isProtecting(int area) {
		if (weapon != null) {
			return weapon.isProtecting(area);
		}
		return false;
	}
}