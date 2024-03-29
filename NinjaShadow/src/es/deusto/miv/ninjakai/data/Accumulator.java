package es.deusto.miv.ninjakai.data;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

public class Accumulator extends Sprite {

	private static final Color ENABLED = Color.RED;
	private static final Color ACTIVE = Color.GREEN;

	private PowerUp powerUp = null;
	private IPowerUpActivated listener = null;

	private boolean isActive = false;

	public Accumulator(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			IPowerUpActivated listener) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		setAlpha(0);
		this.listener = listener;
	}

	public boolean isEnabled() {
		return powerUp != null;
	}

	public void setPowerUp(PowerUp pu) {
		powerUp = pu;
		if (isEnabled()) {
			this.setAlpha(1);
			this.setColor(ENABLED);
		} else {
			isActive = false;
			this.setAlpha(0);
		}
	}

	public PowerUp getPowerUp() {
		return powerUp;
	}

	@Override
	public void setColor(Color pColor) {
		super.setColor(pColor);
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (isEnabled() && !isActive) {
			isActive = true;
			listener.onPowerUpActivated(getPowerUp());
			this.setColor(ACTIVE);
		}
		return false;
	}

	@Override
	protected void preDraw(GLState pGLState, Camera pCamera) {
		super.preDraw(pGLState, pCamera);
		pGLState.enableDither();
	}
}
