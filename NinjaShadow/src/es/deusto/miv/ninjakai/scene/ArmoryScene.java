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

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import es.deusto.miv.ninjakai.GameActivity;
import es.deusto.miv.ninjakai.base.BaseScene;
import es.deusto.miv.ninjakai.data.Weapon.WeaponType;
import es.deusto.miv.ninjakai.manager.ResourcesManager;
import es.deusto.miv.ninjakai.manager.SceneManager;
import es.deusto.miv.ninjakai.manager.SceneManager.SceneType;

public class ArmoryScene extends BaseScene implements IOnMenuItemClickListener {

	private HUD gameHUD;
	private Sprite kunai, nunchaku, katana, kusarigama, stick;

	private MenuScene menuChildScene;

	private final int KUNAI_UNLOCK = 0;
	private final int KUNAI_EQUIP = 1;
	private final int NUNCHAKU_UNLOCK = 2;
	private final int NUNCHAKU_EQUIP = 3;
	private final int KATANA_UNLOCK = 4;
	private final int KATANA_EQUIP = 5;
	private final int KUSARIGAMA_UNLOCK = 6;
	private final int KUSARIGAMA_EQUIP = 7;
	private final int STICK_UNLOCK = 8;
	private final int STICK_EQUIP = 9;

	private final float unlockHeightFix = 30;
	private final float equipHeightFix = 80;
	private final float widthFix = 60;

	private SharedPreferences prefs;
	private WeaponType equipedType;
	private Sprite equiped;

	@Override
	public void createScene() {
		prefs = PreferenceManager.getDefaultSharedPreferences(ResourcesManager
				.getInstance().activity);
		equipedType = WeaponType.valueOf(prefs.getString(
				GameActivity.WEAPON_KEY, WeaponType.KUNAI.name()));
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
		Editor edit = prefs.edit();
		boolean ret = true;

		equiped.setAlpha(0.2f);

		switch (pMenuItem.getID()) {
		case KUNAI_UNLOCK:

			break;
		case KUNAI_EQUIP:
			if (!kunai.getColor().equals(Color.BLACK)) {
				edit.putString(GameActivity.WEAPON_KEY, WeaponType.KUNAI.name());
				equiped = kunai;
			}
			break;
		case NUNCHAKU_UNLOCK:

			break;
		case NUNCHAKU_EQUIP:
			if (!nunchaku.getColor().equals(Color.BLACK)) {
				edit.putString(GameActivity.WEAPON_KEY,
						WeaponType.NUNCHAKUS.name());
				equiped = nunchaku;
			}
			break;
		case KATANA_UNLOCK:

			break;
		case KATANA_EQUIP:
			if (!katana.getColor().equals(Color.BLACK)) {
				edit.putString(GameActivity.WEAPON_KEY,
						WeaponType.KATANA.name());
				equiped = katana;
			}
			break;
		case STICK_UNLOCK:

			break;
		case STICK_EQUIP:
			if (!stick.getColor().equals(Color.BLACK)) {
				edit.putString(GameActivity.WEAPON_KEY, WeaponType.STICK.name());
				equiped = stick;
			}
			break;
		case KUSARIGAMA_UNLOCK:

			break;
		case KUSARIGAMA_EQUIP:
			if (!kusarigama.getColor().equals(Color.BLACK)) {
				edit.putString(GameActivity.WEAPON_KEY,
						WeaponType.KUSARIGAMA.name());
				equiped = kusarigama;
			}
			break;
		default:
			ret = false;
		}

		equiped.setAlpha(1);

		edit.apply();
		return ret;
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
		kunai = new Sprite(GameActivity.CAM_WIDTH / 5,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.kunai_armory_region, vbom);
		if (equipedType != WeaponType.KUNAI) {
			kunai.setAlpha(0.2f);
		} else {
			equiped = kunai;
		}
		stick = new Sprite(2 * GameActivity.CAM_WIDTH / 5,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.stick_armory_region, vbom);
		if (equipedType != WeaponType.STICK) {
			stick.setAlpha(0.2f);
		} else {
			equiped = stick;
		}
		nunchaku = new Sprite(3 * GameActivity.CAM_WIDTH / 5,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.nunchaku_armory_region, vbom);
		if (equipedType != WeaponType.NUNCHAKUS) {
			nunchaku.setAlpha(0.2f);
		} else {
			equiped = nunchaku;
		}
		kusarigama = new Sprite(4 * GameActivity.CAM_WIDTH / 5,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.kusarigama_armory_region, vbom);
		if (equipedType != WeaponType.KUSARIGAMA) {
			kusarigama.setAlpha(0.2f);
		} else {
			equiped = kusarigama;
		}
		katana = new Sprite(GameActivity.CAM_WIDTH,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.katana_armory_region, vbom);
		if (equipedType != WeaponType.KATANA) {
			katana.setAlpha(0.2f);
		} else {
			equiped = katana;
		}

		kunai.setX(kunai.getX() - kunai.getWidthScaled() / 2);
		stick.setScale(0.8f);
		stick.setX(stick.getX() - stick.getWidthScaled() / 2);
		nunchaku.setScale(0.5f);
		nunchaku.setX(nunchaku.getX() - nunchaku.getWidthScaled() / 2);
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
	}

	private void createChildScene() {
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(0, 0);

		final IMenuItem unlockKunai = new ScaleMenuItemDecorator(
				new TextMenuItem(KUNAI_UNLOCK,
						resourcesManager.fontArmoryMenuItems, "Unlock", vbom),
				0.7f, 0.5f);
		final IMenuItem equipKunai = new ScaleMenuItemDecorator(
				new TextMenuItem(KUNAI_EQUIP,
						resourcesManager.fontArmoryMenuItems, "Equip", vbom),
				0.7f, 0.5f);
		final IMenuItem unlockStick = new ScaleMenuItemDecorator(
				new TextMenuItem(STICK_UNLOCK,
						resourcesManager.fontArmoryMenuItems, "Unlock", vbom),
				0.7f, 0.5f);
		final IMenuItem equipStick = new ScaleMenuItemDecorator(
				new TextMenuItem(STICK_EQUIP,
						resourcesManager.fontArmoryMenuItems, "Equip", vbom),
				0.7f, 0.5f);
		final IMenuItem unlockNunchaku = new ScaleMenuItemDecorator(
				new TextMenuItem(NUNCHAKU_UNLOCK,
						resourcesManager.fontArmoryMenuItems, "Unlock", vbom),
				0.7f, 0.5f);
		final IMenuItem equipNunchaku = new ScaleMenuItemDecorator(
				new TextMenuItem(NUNCHAKU_EQUIP,
						resourcesManager.fontArmoryMenuItems, "Equip", vbom),
				0.7f, 0.5f);
		final IMenuItem unlockKusarigama = new ScaleMenuItemDecorator(
				new TextMenuItem(KUSARIGAMA_UNLOCK,
						resourcesManager.fontArmoryMenuItems, "Unlock", vbom),
				0.7f, 0.5f);
		final IMenuItem equipKusarigama = new ScaleMenuItemDecorator(
				new TextMenuItem(KUSARIGAMA_EQUIP,
						resourcesManager.fontArmoryMenuItems, "Equip", vbom),
				0.7f, 0.5f);
		final IMenuItem unlockKatana = new ScaleMenuItemDecorator(
				new TextMenuItem(KATANA_UNLOCK,
						resourcesManager.fontArmoryMenuItems, "Unlock", vbom),
				0.7f, 0.5f);
		final IMenuItem equipKatana = new ScaleMenuItemDecorator(
				new TextMenuItem(KATANA_EQUIP,
						resourcesManager.fontArmoryMenuItems, "Equip", vbom),
				0.7f, 0.5f);

		menuChildScene.addMenuItem(unlockKunai);
		menuChildScene.addMenuItem(equipKunai);
		menuChildScene.addMenuItem(unlockStick);
		menuChildScene.addMenuItem(equipStick);
		menuChildScene.addMenuItem(unlockNunchaku);
		menuChildScene.addMenuItem(equipNunchaku);
		menuChildScene.addMenuItem(unlockKusarigama);
		menuChildScene.addMenuItem(equipKusarigama);
		menuChildScene.addMenuItem(unlockKatana);
		menuChildScene.addMenuItem(equipKatana);

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);

		equipKunai.setPosition(GameActivity.CAM_WIDTH / 5 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - unlockHeightFix);
		unlockKunai.setPosition(GameActivity.CAM_WIDTH / 5 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - equipHeightFix);
		equipStick.setPosition(2 * GameActivity.CAM_WIDTH / 5 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - unlockHeightFix);
		unlockStick.setPosition(2 * GameActivity.CAM_WIDTH / 5 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - equipHeightFix);
		equipNunchaku.setPosition(3 * GameActivity.CAM_WIDTH / 5 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - unlockHeightFix);
		unlockNunchaku.setPosition(3 * GameActivity.CAM_WIDTH / 5 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - equipHeightFix);
		equipKusarigama.setPosition(4 * GameActivity.CAM_WIDTH / 5 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - unlockHeightFix);
		unlockKusarigama.setPosition(4 * GameActivity.CAM_WIDTH / 5 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - equipHeightFix);
		equipKatana.setPosition(GameActivity.CAM_WIDTH - widthFix,
				GameActivity.CAM_HEIGHT / 3 - unlockHeightFix);
		unlockKatana.setPosition(GameActivity.CAM_WIDTH - widthFix,
				GameActivity.CAM_HEIGHT / 3 - equipHeightFix);

		menuChildScene.setOnMenuItemClickListener(this);
		setChildScene(menuChildScene);
	}

	private void loadSettings() {
		// TODO Set hidden the locked ones
		// stick.setColor(Color.BLACK);
		// nunchaku.setColor(Color.BLACK);
		// manriki.setColor(Color.BLACK);
		// kusarigama.setColor(Color.BLACK);
		katana.setColor(Color.BLACK);
	}
}
