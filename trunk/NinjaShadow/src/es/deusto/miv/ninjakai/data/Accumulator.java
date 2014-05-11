package es.deusto.miv.ninjakai.data;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;


public class Accumulator extends Sprite {

	private static final Color ENABLED = new Color(1, 1, 1, 1);
	private static final Color DISABLED = Color.BLACK;

	private PowerUp powerUp = null;

	public Accumulator(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		setColor(DISABLED);
	}

	public boolean isEnabled() {
		return powerUp != null;
	}

	public void setPowerUp(PowerUp pu) {
		powerUp = pu;
		if (isEnabled()) {
			this.setColor(ENABLED);
		} else {
			this.setColor(DISABLED);
		}
	}
	
	public PowerUp getPowerUp() {
		return powerUp;
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (isEnabled()) {
			// TODO Do effect this.getPowerUp()...
			setPowerUp(null);
		}
		return false;
	}

	@Override
	protected void preDraw(GLState pGLState, Camera pCamera) {
		super.preDraw(pGLState, pCamera);
		pGLState.enableDither();
	}
}
