package es.deusto.miv.ninjakai.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.util.debug.Debug;

import es.deusto.miv.ninjakai.GameActivity;
import es.deusto.miv.ninjakai.base.BaseScene;
import es.deusto.miv.ninjakai.manager.SceneManager.SceneType;

public class SplashScene extends BaseScene {
	
	private Sprite splash;

	@Override
	public void createScene() {
		splash = new Sprite(0, 0, resourcesManager.splash_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};

		splash.setPosition(GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2);
		attachChild(splash);
	}

	@Override
	public void onBackKeyPressed() {
		return;
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() {
		splash.detachSelf();
		splash.dispose();
		this.detachSelf();
		this.dispose();
	}
}