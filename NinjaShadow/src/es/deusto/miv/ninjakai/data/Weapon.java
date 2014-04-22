package es.deusto.miv.ninjakai.data;

import java.util.ArrayList;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.SparseArray;

public abstract class Weapon extends AnimatedSprite {

	private String name;
	private double speed[];
	protected ArrayList<Integer> positions;
	private int rank;
	protected final SparseArray<IAreaObserver> areaObservers = new SparseArray<IAreaObserver>(
			5);

	public Weapon(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}

	public Weapon(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, String name,
			double[] speed, ArrayList<Integer> positions, int rank) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.name = name;
		this.speed = speed;
		this.positions = positions;
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double[] getSpeed() {
		return speed;
	}

	public void setSpeed(double[] speed) {
		this.speed = speed;
	}

	public ArrayList<Integer> getPositions() {
		return positions;
	}

	public void setPositions(ArrayList<Integer> positions) {
		this.positions = positions;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public boolean isProtecting(int area) {
		return positions.contains(area);
	}

	public final void registerAreaObserver(int area, IAreaObserver observer) {
		areaObservers.put(area, observer);
	}

	public abstract void protect(int area);

	public abstract double getSpeed(int[] from, int[] to);
	
	public abstract String getWeaponGraphics();
	
	public static enum WeaponType {
		
		KUNAI("shuriken.png");
		
		public final String graphics;
		
		WeaponType(String graphics) {
			this.graphics = graphics;
		}
	}
}