package es.deusto.miv.ninjakai.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;

import es.deusto.miv.ninjakai.GameActivity;
import es.deusto.miv.ninjakai.base.BaseScene;
import es.deusto.miv.ninjakai.manager.SceneManager;
import es.deusto.miv.ninjakai.manager.SceneManager.SceneType;

public class SettingsScene extends BaseScene {

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
		return SceneType.SCENE_SETTINGS;
	}

	@Override
	public void disposeScene() {
		// TODO Detach and dispose all sprites
	}
	
	public void createBackground() {
		attachChild(new Sprite(GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.settings_background_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		});
		
		Text t = new Text(GameActivity.CAM_WIDTH / 2,
				4 * GameActivity.CAM_HEIGHT / 5, resourcesManager.fontTitle,
				"Settings", vbom);
		t.getTextOptions().setHorizontalAlign(HorizontalAlign.CENTER);
		attachChild(t);
	}

}
