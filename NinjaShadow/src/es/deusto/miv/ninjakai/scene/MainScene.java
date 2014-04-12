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

import es.deusto.miv.ninjakai.GameActivity;
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

		Text t = new Text(GameActivity.CAM_WIDTH / 2,
				4 * GameActivity.CAM_HEIGHT / 5, resourcesManager.fontTitle,
				"Ninja Kai", vbom);
		t.getTextOptions().setHorizontalAlign(HorizontalAlign.CENTER);
		attachChild(t);
	}

	public void createChildScene() {
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(0, 0);

		// final IMenuItem playMenuItem = new ScaleMenuItemDecorator(
		// new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region,
		// vbom), 1.2f, 1);
		// final IMenuItem settingsMenuItem = new ScaleMenuItemDecorator(
		// new SpriteMenuItem(MENU_SETTINGS,
		// resourcesManager.settings_region, vbom), 1.2f, 1);

		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(
				new TextMenuItem(MENU_PLAY, resourcesManager.fontMenuItems,
						"Play", vbom), 1.2f, 1);
		final IMenuItem settingsMenuItem = new ScaleMenuItemDecorator(
				new TextMenuItem(MENU_SETTINGS, resourcesManager.fontMenuItems,
						"Options", vbom), 1.2f, 1);

		final IMenuItem armoryMenuItem = new ScaleMenuItemDecorator(
				new TextMenuItem(MENU_SETTINGS, resourcesManager.fontMenuItems,
						"Armory", vbom), 1.2f, 1);

		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(settingsMenuItem);
		menuChildScene.addMenuItem(armoryMenuItem);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);

		playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY() - 50);
		settingsMenuItem.setPosition(settingsMenuItem.getX(),
				settingsMenuItem.getY() - 50);
		armoryMenuItem.setPosition(armoryMenuItem.getX(),
				armoryMenuItem.getY() - 50);

		menuChildScene.setOnMenuItemClickListener(this);

		setChildScene(menuChildScene);
	}
}
