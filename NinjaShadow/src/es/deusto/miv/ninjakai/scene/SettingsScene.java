package es.deusto.miv.ninjakai.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import es.deusto.miv.ninjakai.GameActivity;
import es.deusto.miv.ninjakai.base.BaseScene;
import es.deusto.miv.ninjakai.manager.ResourcesManager;
import es.deusto.miv.ninjakai.manager.SceneManager;
import es.deusto.miv.ninjakai.manager.SceneManager.SceneType;

public class SettingsScene extends BaseScene implements
		IOnMenuItemClickListener {

	private MenuScene menuChildScene;

	private final int MENU_SOUND = 0;
	private final int MENU_RESET = 1;

	private SharedPreferences prefs;

	@Override
	public void createScene() {
		prefs = PreferenceManager.getDefaultSharedPreferences(ResourcesManager
				.getInstance().activity);
		createBackground();
		createChildScene();
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

	public void createChildScene() {
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(0, 0);

		final IMenuItem totalScoreMenuItem = new TextMenuItem(MENU_SOUND,
				resourcesManager.fontMenuItems, "Experience: "
						+ prefs.getInt(GameActivity.TOTAL_SCORE_KEY, 0), vbom);
		final IMenuItem highScoreMenuItem = new TextMenuItem(MENU_RESET,
				resourcesManager.fontMenuItems, "High Score: "
						+ prefs.getInt(GameActivity.HIGH_SCORE_KEY, 0), vbom);
		final IMenuItem soundMenuItem = new ScaleMenuItemDecorator(
				new TextMenuItem(MENU_SOUND, resourcesManager.fontMenuItems,
						"Sound", vbom), 1.2f, 1);
		final IMenuItem resetMenuItem = new ScaleMenuItemDecorator(
				new TextMenuItem(MENU_RESET, resourcesManager.fontMenuItems,
						"Reset", vbom), 1.2f, 1);

		menuChildScene.addMenuItem(totalScoreMenuItem);
		menuChildScene.addMenuItem(highScoreMenuItem);
		menuChildScene.addMenuItem(soundMenuItem);
		menuChildScene.addMenuItem(resetMenuItem);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);

		totalScoreMenuItem.setPosition(totalScoreMenuItem.getX(),
				totalScoreMenuItem.getY() - 50);
		highScoreMenuItem.setPosition(highScoreMenuItem.getX(),
				highScoreMenuItem.getY() - 50);
		soundMenuItem.setPosition(soundMenuItem.getX(),
				soundMenuItem.getY() - 50);
		resetMenuItem.setPosition(resetMenuItem.getX(),
				resetMenuItem.getY() - 50);

		menuChildScene.setOnMenuItemClickListener(this);

		setChildScene(menuChildScene);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		Editor edit = prefs.edit();

		switch (pMenuItem.getID()) {
		case MENU_SOUND:
			edit.putBoolean(GameActivity.SOUND_KEY, !prefs.getBoolean(GameActivity.SOUND_KEY, true));
			createScene();
		case MENU_RESET:
			edit.putInt(GameActivity.TOTAL_SCORE_KEY, 0);
			edit.putInt(GameActivity.HIGH_SCORE_KEY, 0);
			edit.apply();
			createScene();
		default:
			return false;
		}
	}
}
