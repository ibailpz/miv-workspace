package es.deusto.miv.ninjakai.data;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

public class PowerUp extends Sprite {

	private static final Color ENABLED = new Color(1, 1, 1, 1);
	private static final Color DISABLED = Color.BLACK;

	private int level = 1;
	private boolean enabled = true;

	public PowerUp(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
	}

	public PowerUp(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			boolean enabled) {
		this(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		setEnabled(enabled);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if (enabled) {
			this.setColor(ENABLED);
		} else {
			this.setColor(DISABLED);
		}
	}

	@Override
	protected void preDraw(GLState pGLState, Camera pCamera) {
		super.preDraw(pGLState, pCamera);
		pGLState.enableDither();
	}
}
