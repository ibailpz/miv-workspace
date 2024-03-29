package es.deusto.miv.ninjakai.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;

import es.deusto.miv.ninjakai.GameActivity;
import es.deusto.miv.ninjakai.base.BaseScene;
import es.deusto.miv.ninjakai.manager.SceneManager.SceneType;

public class LoadingScene extends BaseScene {

	@Override
	public void createScene() {
		// setBackground(new Background(Color.BLACK));

		attachChild(new Sprite(GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.menu_background_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		});

		attachChild(new Text(GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2, resourcesManager.fontMenuItems,
				"Loading...", vbom));
	}

	@Override
	public void onBackKeyPressed() {
		return;
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_LOADING;
	}

	@Override
	public void disposeScene() {

	}
}