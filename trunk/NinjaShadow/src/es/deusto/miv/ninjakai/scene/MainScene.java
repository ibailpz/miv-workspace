package es.deusto.miv.ninjakai.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import es.deusto.miv.ninjakai.base.BaseScene;
import es.deusto.miv.ninjakai.manager.SceneManager;
import es.deusto.miv.ninjakai.manager.SceneManager.SceneType;

public class MainScene extends BaseScene implements IOnMenuItemClickListener {

	private MenuScene menuChildScene;

	private final int MENU_PLAY = 0;
	private final int MENU_SETTINGS = 1;
	private final int MENU_ARMORY = 2;

	@Override
	public void createScene() {
		createBackground();
		createChildScene();
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_MAIN;
	}

	@Override
	public void disposeScene() {
		// TODO Detach and dispose all sprites
		this.detachSelf();
		this.dispose();
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		System.out.println("In");
		switch (pMenuItem.getID()) {
		case MENU_PLAY:
			SceneManager.getInstance().loadGameScene(engine);
			return true;
		case MENU_SETTINGS:
			SceneManager.getInstance().loadSettingsScene(engine);
			return true;
		case MENU_ARMORY:
			SceneManager.getInstance().loadArmoryScene(engine);
			return true;
		default:
			return false;
		}
	}

	public void createBackground() {
		attachChild(new Sprite(400, 240,
				resourcesManager.menu_background_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		});
	}

	public void createChildScene() {
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(0, 0);

		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region,
						vbom), 2f, 1);
		final IMenuItem settingsMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_SETTINGS,
						resourcesManager.settings_region, vbom), 2f, 1);
		/*
		 * final IMenuItem armoryMenuItem = new ScaleMenuItemDecorator( new
		 * SpriteMenuItem(MENU_ARMORY, resourcesManager.armory_region, vbom),
		 * 1.2f, 1);
		 */

		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(settingsMenuItem);
		// menuChildScene.addMenuItem(armoryMenuItem);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);

		/*playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY() + 10);
		settingsMenuItem.setPosition(settingsMenuItem.getX(),
				settingsMenuItem.getY() - 110);*/
		// playMenuItem.setPosition(0, 10f);
		// settingsMenuItem.setPosition(0, -110f);
		/*
		 * armoryMenuItem .setPosition(armoryMenuItem.getX(),
		 * armoryMenuItem.getY() - 200);
		 */

		menuChildScene.setOnMenuItemClickListener(this);

		setChildScene(menuChildScene);
	}
}
