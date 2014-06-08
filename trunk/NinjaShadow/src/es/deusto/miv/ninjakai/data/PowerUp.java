package es.deusto.miv.ninjakai.data;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import es.deusto.miv.ninjakai.scene.GameScene;

public class PowerUp extends Sprite {

	private int level = 1;
	private Accumulator accumulator;
	private double ttl = 0;

	public PowerUp(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			Accumulator acc) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		this.accumulator = acc;
		this.setColor(Color.RED);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	protected void preDraw(GLState pGLState, Camera pCamera) {
		super.preDraw(pGLState, pCamera);
		pGLState.enableDither();
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.isActionUp()) {
			this.setTag(-1);
			accumulator.setPowerUp(this);
		}
		return false;
	}

	public Accumulator getAccumulator() {
		return accumulator;
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		ttl += pSecondsElapsed;
		if (ttl >= GameScene.powerUpTTL) {
			this.setTag(-1);
		}
	}

	public interface TimedPowerUp {
		public boolean timePassed(float time);
	}

	public interface HitPowerUp {
		public boolean newHit();
	}
}
