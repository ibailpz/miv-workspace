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
import org.andengine.input.touch.TouchEvent;
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

	private final static String KUNAI_KEY = "KUNAI_KEY";
	public final static String NUNCHAKU_KEY = "NUNCHAKU_KEY";
	public final static String KATANA_KEY = "KATANA_KEY";
	public final static String KUSARIGAMA_KEY = "KUSARIGAMA_KEY";
	public final static String STICK_KEY = "STICK_KEY";

	private final int LIFES_UNLOCK = 0;
	private final int NUNCHAKU_UNLOCK = 1;
	private final int KATANA_UNLOCK = 2;
	private final int KUSARIGAMA_UNLOCK = 3;
	private final int STICK_UNLOCK = 4;

	private final int STICK_EXP = 100;
	private final int NUNCHAKU_EXP = 400;
	private final int KUSARIGAMA_EXP = 1000;
	private final int KATANA_EXP = 2000;
	private final int LIFES_EXP = 2000;

	private final float unlockHeightFix = 30;
	private final float widthFix = 60;

	private SharedPreferences prefs;
	private WeaponType equipedType;
	private Sprite equiped;

	@Override
	public void createScene() {
		prefs = PreferenceManager.getDefaultSharedPreferences(ResourcesManager
				.getInstance().activity);
		prefs.edit().putBoolean(KUNAI_KEY, true).apply();
		equipedType = WeaponType.valueOf(prefs.getString(
				GameActivity.WEAPON_KEY, WeaponType.KUNAI.name()));
		gameHUD = new HUD();
		createBackground();
		createWeapons();
		createChildScene();
		loadSettings();
		bottomMenu();
		lifes();
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
		camera.setHUD(null);
	}

	private void lifes() {
		Sprite s = new Sprite(GameActivity.CAM_WIDTH, GameActivity.CAM_HEIGHT,
				resourcesManager.ninja_life_region, vbom);
		s.setPosition(s.getX() - s.getWidth(), s.getY() - s.getHeight());
		gameHUD.attachChild(s);

		Text t = new Text(s.getX() - 50, s.getY(),
				ResourcesManager.getInstance().fontHUD, prefs.getInt(
						GameActivity.LIFES_KEY, 3) + "X", vbom);
		gameHUD.attachChild(t);
	}

	private void bottomMenu() {
		Text t = new Text(10, 10, ResourcesManager.getInstance().fontHUD,
				"Experience: " + prefs.getInt(GameActivity.TOTAL_SCORE_KEY, 0),
				vbom);
		t.setAnchorCenter(0, 0);
		gameHUD.attachChild(t);

	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		boolean ret = true;
		Editor edit = prefs.edit();
		int total = prefs.getInt(GameActivity.TOTAL_SCORE_KEY, 0);

		switch (pMenuItem.getID()) {
		case NUNCHAKU_UNLOCK:
			if (total >= NUNCHAKU_EXP) {
				edit.putInt(GameActivity.TOTAL_SCORE_KEY, total - NUNCHAKU_EXP);
				edit.putBoolean(NUNCHAKU_KEY, true);
			}
			break;
		case KATANA_UNLOCK:
			if (total >= KATANA_EXP) {
				edit.putInt(GameActivity.TOTAL_SCORE_KEY, total - KATANA_EXP);
				edit.putBoolean(KATANA_KEY, true);
			}
			break;
		case STICK_UNLOCK:
			if (total >= STICK_EXP) {
				edit.putInt(GameActivity.TOTAL_SCORE_KEY, total - STICK_EXP);
				edit.putBoolean(STICK_KEY, true);
			}
			break;
		case KUSARIGAMA_UNLOCK:
			if (total >= KUSARIGAMA_EXP) {
				edit.putInt(GameActivity.TOTAL_SCORE_KEY, total
						- KUSARIGAMA_EXP);
				edit.putBoolean(KUSARIGAMA_KEY, true);
			}
			break;
		case LIFES_UNLOCK:
			int lifes = prefs.getInt(GameActivity.LIFES_KEY, 3);
			if (lifes < 5 && total >= LIFES_EXP) {
				edit.putInt(GameActivity.TOTAL_SCORE_KEY, total
						- LIFES_EXP);
				edit.putInt(GameActivity.LIFES_KEY, lifes + 1);
			}
			break;
		default:
			ret = false;
		}
		edit.apply();

		if (ret) {
			createScene();
		}

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

	private void equip(Sprite weapon, WeaponType type, String key) {
		Editor edit = prefs.edit();
		equiped.setAlpha(0.2f);
		if (prefs.getBoolean(key, false)) {
			edit.putString(GameActivity.WEAPON_KEY, type.name());
			equiped = weapon;
		}
		equiped.setAlpha(1);
		edit.apply();
	}

	@SuppressWarnings("deprecation")
	private void createWeapons() {
		kunai = new Sprite(GameActivity.CAM_WIDTH / 5,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.kunai_armory_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionUp()) {
					equip(kunai, WeaponType.KUNAI, KUNAI_KEY);
					return true;
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		gameHUD.registerTouchArea(kunai);

		if (equipedType == WeaponType.KUNAI) {
			equiped = kunai;
		}

		stick = new Sprite(2 * GameActivity.CAM_WIDTH / 5,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.stick_armory_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionUp()) {
					equip(stick, WeaponType.STICK, STICK_KEY);
					return true;
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		gameHUD.registerTouchArea(stick);

		if (equipedType == WeaponType.STICK) {
			equiped = stick;
		}

		nunchaku = new Sprite(3 * GameActivity.CAM_WIDTH / 5,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.nunchaku_armory_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionUp()) {
					equip(nunchaku, WeaponType.NUNCHAKUS, NUNCHAKU_KEY);
					return true;
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		gameHUD.registerTouchArea(nunchaku);

		if (equipedType == WeaponType.NUNCHAKUS) {
			equiped = nunchaku;
		}

		kusarigama = new Sprite(4 * GameActivity.CAM_WIDTH / 5,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.kusarigama_armory_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionUp()) {
					equip(kusarigama, WeaponType.KUSARIGAMA, KUSARIGAMA_KEY);
					return true;
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		gameHUD.registerTouchArea(kusarigama);

		if (equipedType == WeaponType.KUSARIGAMA) {
			equiped = kusarigama;
		}

		katana = new Sprite(GameActivity.CAM_WIDTH,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.katana_armory_region, vbom) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionUp()) {
					equip(katana, WeaponType.KATANA, KATANA_KEY);
					return true;
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}
		};
		gameHUD.registerTouchArea(katana);

		if (equipedType == WeaponType.KATANA) {
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

		final IMenuItem unlockStick = new ScaleMenuItemDecorator(
				new TextMenuItem(STICK_UNLOCK,
						resourcesManager.fontArmoryMenuItems, "Unlock\n"
								+ STICK_EXP + " exp.", vbom), 0.7f, 0.5f);
		final IMenuItem unlockNunchaku = new ScaleMenuItemDecorator(
				new TextMenuItem(NUNCHAKU_UNLOCK,
						resourcesManager.fontArmoryMenuItems, "Unlock\n"
								+ NUNCHAKU_EXP + " exp.", vbom), 0.7f, 0.5f);
		final IMenuItem unlockKusarigama = new ScaleMenuItemDecorator(
				new TextMenuItem(KUSARIGAMA_UNLOCK,
						resourcesManager.fontArmoryMenuItems, "Unlock\n"
								+ KUSARIGAMA_EXP + " exp.", vbom), 0.7f, 0.5f);
		final IMenuItem unlockKatana = new ScaleMenuItemDecorator(
				new TextMenuItem(KATANA_UNLOCK,
						resourcesManager.fontArmoryMenuItems, "Unlock\n"
								+ KATANA_EXP + " exp.", vbom), 0.7f, 0.5f);
		final IMenuItem unlockLifes = new ScaleMenuItemDecorator(
				new TextMenuItem(LIFES_UNLOCK,
						resourcesManager.fontArmoryMenuItems, "Extra life: "
								+ LIFES_EXP + " exp.", vbom), 0.7f, 0.5f);

		if (!prefs.getBoolean(STICK_KEY, false)) {
			menuChildScene.addMenuItem(unlockStick);
		}
		if (!prefs.getBoolean(NUNCHAKU_KEY, false)) {
			menuChildScene.addMenuItem(unlockNunchaku);
		}
		if (!prefs.getBoolean(KUSARIGAMA_KEY, false)) {
			menuChildScene.addMenuItem(unlockKusarigama);
		}
		if (!prefs.getBoolean(KATANA_KEY, false)) {
			menuChildScene.addMenuItem(unlockKatana);
		}
		if (prefs.getInt(GameActivity.LIFES_KEY, 3) < 5) {
			menuChildScene.addMenuItem(unlockLifes);
		}

		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);

		unlockStick.setPosition(2 * GameActivity.CAM_WIDTH / 5 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - unlockHeightFix);
		unlockNunchaku.setPosition(3 * GameActivity.CAM_WIDTH / 5 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - unlockHeightFix);
		unlockKusarigama.setPosition(4 * GameActivity.CAM_WIDTH / 5 - widthFix,
				GameActivity.CAM_HEIGHT / 3 - unlockHeightFix);
		unlockKatana.setPosition(GameActivity.CAM_WIDTH - widthFix,
				GameActivity.CAM_HEIGHT / 3 - unlockHeightFix);
		unlockLifes.setPosition(GameActivity.CAM_WIDTH - 120, 30);

		menuChildScene.setOnMenuItemClickListener(this);
		setChildScene(menuChildScene);
	}

	private void loadSettings() {
		kunai.setAlpha(0.2f);

		if (!prefs.getBoolean(STICK_KEY, false)) {
			stick.setColor(Color.BLACK);
		} else {
			stick.setAlpha(0.2f);
		}

		if (!prefs.getBoolean(NUNCHAKU_KEY, false)) {
			nunchaku.setColor(Color.BLACK);
		} else {
			nunchaku.setAlpha(0.2f);
		}

		if (!prefs.getBoolean(KUSARIGAMA_KEY, false)) {
			kusarigama.setColor(Color.BLACK);
		} else {
			kusarigama.setAlpha(0.2f);
		}

		if (!prefs.getBoolean(KATANA_KEY, false)) {
			katana.setColor(Color.BLACK);
		} else {
			katana.setAlpha(0.2f);
		}

		equiped.setAlpha(1);
	}
}
