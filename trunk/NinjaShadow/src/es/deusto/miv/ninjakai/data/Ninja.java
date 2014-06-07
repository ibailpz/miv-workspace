package es.deusto.miv.ninjakai.data;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import es.deusto.miv.ninjakai.data.powerup.Aura;
import es.deusto.miv.ninjakai.data.powerup.ExtraPoints;
import es.deusto.miv.ninjakai.data.powerup.SpeedUp;

public class Ninja extends Sprite {

	private Weapon weapon;
	private int lifes;
	private float points;
	private int bonus;

	private Aura a;
	private ExtraPoints ep;
	private SpeedUp su;

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
			Weapon weapon, int lifes, float points, int bonus) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.weapon = weapon;
		this.lifes = lifes;
		this.points = points;
		this.bonus = bonus;
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

	public void addPowerUp(PowerUp pu) {
		if (pu instanceof Aura) {
			a = (Aura) pu;
		} else if (pu instanceof ExtraPoints) {
			ep = (ExtraPoints) pu;
		} else if (pu instanceof SpeedUp) {
			su = (SpeedUp) pu;
		}
	}

	public void removePowerUp(PowerUp pu) {
		if (pu instanceof Aura) {
			a = null;
		} else if (pu instanceof ExtraPoints) {
			ep = null;
		} else if (pu instanceof SpeedUp) {
			su = null;
		}
		pu.getAccumulator().setPowerUp(null);
	}
	
	public Aura getAura() {
		return a;
	}
	
	public ExtraPoints getExtraPoints() {
		return ep;
	}
	
	public SpeedUp getSpeedUp() {
		return su;
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