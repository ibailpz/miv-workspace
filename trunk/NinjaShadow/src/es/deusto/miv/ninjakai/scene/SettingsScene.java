package es.deusto.miv.ninjakai.scene;

import es.deusto.miv.ninjakai.base.BaseScene;
import es.deusto.miv.ninjakai.manager.SceneManager.SceneType;

public class SettingsScene extends BaseScene {

	@Override
	public void createScene() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_SETTINGS;
	}

	@Override
	public void disposeScene() {
		// TODO Detach and dispose all sprites
		this.detachSelf();
		this.dispose();
	}

}
