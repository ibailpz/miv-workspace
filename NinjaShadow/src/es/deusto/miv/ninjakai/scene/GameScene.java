package es.deusto.miv.ninjakai.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import es.deusto.miv.ninjakai.base.BaseScene;
import es.deusto.miv.ninjakai.manager.SceneManager;
import es.deusto.miv.ninjakai.manager.SceneManager.SceneType;

public class GameScene extends BaseScene {

	@Override
	public void createScene() {
		createBackground();
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadMainScene(engine);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		// TODO Detach and dispose all sprites, hud, etc.
		
	}
	
	public void createBackground() {
		attachChild(new Sprite(400, 240, resourcesManager.game_background_region, vbom){
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		});
	}

}
