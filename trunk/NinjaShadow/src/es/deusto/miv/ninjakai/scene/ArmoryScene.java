package es.deusto.miv.ninjakai.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import es.deusto.miv.ninjakai.GameActivity;
import es.deusto.miv.ninjakai.base.BaseScene;
import es.deusto.miv.ninjakai.manager.SceneManager;
import es.deusto.miv.ninjakai.manager.SceneManager.SceneType;

public class ArmoryScene extends BaseScene implements IOnMenuItemClickListener {

	private HUD gameHUD;
	private Sprite kunai, nunchaku, katana, kusarigama, manriki, stick;

	private MenuScene menuChildScene;

	private final int KUNAI_UNLOCK = 0;
	private final int KUNAI_EQUIP = 1;
	private final int NUNCHAKU_UNLOCK = 2;
	private final int NUNCHAKU_EQUIP = 3;
	private final int KATANA_UNLOCK = 4;
	private final int KATANA_EQUIP = 5;
	private final int KUSARIGAMA_UNLOCK = 6;
	private final int KUSARIGAMA_EQUIP = 7;
	private final int MANRIKI_UNLOCK = 8;
	private final int MANRIKI_EQUIP = 9;
	private final int STICK_UNLOCK = 10;
	private final int STICK_EQUIP = 11;

	private final float unlockHeightFix = 30;
	private final float equipHeightFix = 80;
	private final float widthFix = 60;

	@Override
	public void createScene() {
		gameHUD = new HUD();
		createBackground();
		createWeapons();
		createChildScene();
		loadSettings();
		camera.setHUD(gameHUD);
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadMainScene(engine);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_ARMORY;
	}

	@Override
	public void disposeScene() {
		// TODO Detach and dispose all sprites
		camera.setHUD(null);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Set weapon menu items
		switch (pMenuItem.getID()) {
		case KUNAI_UNLOCK:

			return true;
		case KUNAI_EQUIP:

			return true;
		case NUNCHAKU_UNLOCK:

			return true;
		case NUNCHAKU_EQUIP:

			return true;
		case KATANA_UNLOCK:

			return true;
		case KATANA_EQUIP:

			return true;
		case STICK_UNLOCK:

			return true;
		case STICK_EQUIP:

			return true;
		case KUSARIGAMA_UNLOCK:

			return true;
		case KUSARIGAMA_EQUIP:

			return true;
		case MANRIKI_UNLOCK:

			return true;
		case MANRIKI_EQUIP:

			return true;
		default:
			return false;
		}
	}

	public void createBackground() {
		attachChild(new Sprite(GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.armory_background_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		});

		Text t = new Text(GameActivity.CAM_WIDTH / 2,
				4 * GameActivity.CAM_HEIGHT / 5, resourcesManager.fontTitle,
				"Armory", vbom);
		t.getTextOptions().setHorizontalAlign(HorizontalAlign.CENTER);
		attachChild(t);
	}

	@SuppressWarnings("deprecation")
	public void createWeapons() {
		kunai = new Sprite(GameActivity.CAM_WIDTH / 6,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.kunai_armory_region, vbom);
		stick = new Sprite(2 * GameActivity.CAM_WIDTH / 6,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.stick_armory_region, vbom);
		nunchaku = new Sprite(3 * GameActivity.CAM_WIDTH / 6,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.nunchaku_armory_region, vbom);
		manriki = new Sprite(4 * GameActivity.CAM_WIDTH / 6,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.manriki_armory_region, vbom);
		kusarigama = new Sprite(5 * GameActivity.CAM_WIDTH / 6,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.kusarigama_armory_region, vbom);
		katana = new Sprite(GameActivity.CAM_WIDTH,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.katana_armory_region, vbom);

		kunai.setX(kunai.getX() - kunai.getWidthScaled() / 2);
		stick.setScale(0.8f);
		stick.setX(stick.getX() - stick.getWidthScaled() / 2);
		nunchaku.setScale(0.5f);
		nunchaku.setX(nunchaku.getX() - nunchaku.getWidthScaled() / 2);
		manriki.setScale(0.5f);
		manriki.setX(manriki.getX() - manriki.getWidthScaled() / 2);
		kusarigama.setScale(0.8f);
		kusarigama.setX(kusarigama.getX() - kusarigama.getWidthScaled() / 2);
		katana.setScale(0.7f);
		katana.setRotation(250);
		katana.setX(katana.getX() - katana.getWidthScaled() / 2);

		gameHUD.attachChild(kunai);
		gameHUD.attachChild(kusarigama);
		gameHUD.attachChild(katana);
		gameHUD.attachChild(stick);
		gameHUD.attachChild(nunchaku);
		gameHUD.attachChild(manriki);
	}

	private void createChildScene() {
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(0, 0);

		final IMenuItem unlockKunai = new ScaleMenuItemDecorator(
				new TextMenuItem(KUNAI_UNLOCK, resourcesManager.fontMenuItems,
						"Unlock1", vbom), 0.7f, 0.5f);
		final IMenuItem equipKunai = new ScaleMenuItemDecorator(
				new TextMenuItem(KUNAI_EQUIP, resourcesManager.fontMenuItems,
						"Equip1", vbom), 0.7f, 0.5f);
		final IMenuItem unlockStick = new ScaleMenuItemDecorator(
				new TextMenuItem(STICK_UNLOCK, resourcesManager.fontMenuItems,
						"Unlock2", vbom), 0.7f, 0.5f);
		final IMenuItem equipStick = new ScaleMenuItemDecorator(
				new TextMenuItem(STICK_EQUIP, resourcesManager.fontMenuItems,
						"Equip2", vbom), 0.7f, 0.5f);
		final IMenuItem unlockNunchaku = new ScaleMenuItemDecorator(
				new TextMenuItem(NUNCHAKU_UNLOCK,
						resourcesManager.fontMenuItems, "Unlock3", vbom), 0.7f,
				0.5f);
		final IMenuItem equipNunchaku = new ScaleMenuItemDecorator(
				new TextMenuItem(NUNCHAKU_EQUIP,
						resourcesManager.fontMenuItems, "Equip3", vbom), 0.7f,
				0.5f);
		final IMenuItem unlockManriki = new ScaleMenuItemDecorator(
				new TextMenuItem(MANRIKI_UNLOCK,
						resourcesManager.fontMenuItems, "Unlock4", vbom), 0.7f,
				0.5f);
		final IMenuItem equipManriki = new ScaleMenuItemDecorator(
				new TextMenuItem(MANRIKI_EQUIP, resourcesManager.fontMenuItems,
						"Equip4", vbom), 0.7f, 0.5f);
		final IMenuItem unlockKusarigama = new ScaleMenuItemDecorator(
				new TextMenuItem(KUSARIGAMA_UNLOCK,
						resourcesManager.fontMenuItems, "Unlock5", vbom), 0.7f,
				0.5f);
		final IMenuItem equipKusarigama = new ScaleMenuItemDecorator(
				new TextMenuItem(KUSARIGAMA_EQUIP,
						resourcesManager.fontMenuItems, "Equip5", vbom), 0.7f,
				0.5f);
		final IMenuItem unlockKatana = new ScaleMenuItemDecorator(
				new TextMenuItem(KATANA_UNLOCK, resourcesManager.fontMenuItems,
						"Unlock6", vbom), 0.7f, 0.5f);
		final IMenuItem equipKatana = new ScaleMenuItemDecorator(
				new TextMenuItem(KATANA_EQUIP, resourcesManager.fontMenuItems,
						"Equip6", vbom), 0.7f, 0.5f);

		menuChildScene.addMenuItem(unlockKunai);
		menuChildScene.addMenuItem(equipKunai);
		menuChildScene.addMenuItem(unlockStick);
		menuChildScene.addMenuItem(equipStick);
		menuChildScene.addMenuItem(unlockNunchaku);
		menuChildScene.addMenuItem(equipNunchaku);
		menuChildScene.addMenuItem(unlockManriki);
		menuChildScene.addMenuItem(equipManriki);
		menuChildScene.addMenuItem(unlockKusarigama);
		menuChildScene.addMenuItem(equipKusarigama);
		menuChildScene.addMenuItem(unlockKatana);
		menuChildScene.addMenuItem(equipKatana);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);

		unlockKunai.setPosition(GameActivity.CAM_WIDTH / 6 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - unlockHeightFix);
		equipKunai.setPosition(GameActivity.CAM_WIDTH / 6 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - equipHeightFix);
		unlockStick.setPosition(2 * GameActivity.CAM_WIDTH / 6 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - unlockHeightFix);
		equipStick.setPosition(2 * GameActivity.CAM_WIDTH / 6 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - equipHeightFix);
		unlockNunchaku.setPosition(3 * GameActivity.CAM_WIDTH / 6 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - unlockHeightFix);
		equipNunchaku.setPosition(3 * GameActivity.CAM_WIDTH / 6 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - equipHeightFix);
		unlockManriki.setPosition(4 * GameActivity.CAM_WIDTH / 6 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - unlockHeightFix);
		equipManriki.setPosition(4 * GameActivity.CAM_WIDTH / 6 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - equipHeightFix);
		unlockKusarigama.setPosition(5 * GameActivity.CAM_WIDTH / 6 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - unlockHeightFix);
		equipKusarigama.setPosition(5 * GameActivity.CAM_WIDTH / 6 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - equipHeightFix);
		unlockKatana.setPosition(6 * GameActivity.CAM_WIDTH / 6 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - unlockHeightFix);
		equipKatana.setPosition(6 * GameActivity.CAM_WIDTH / 6 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - equipHeightFix);

		menuChildScene.setOnMenuItemClickListener(this);
		setChildScene(menuChildScene);
	}
	
	private void loadSettings() {
		// TODO Set hidden the locked ones
		stick.setColor(Color.BLACK);
		nunchaku.setColor(Color.BLACK);
		manriki.setColor(Color.BLACK);
		kusarigama.setColor(Color.BLACK);
		katana.setColor(Color.BLACK);
	}
}
