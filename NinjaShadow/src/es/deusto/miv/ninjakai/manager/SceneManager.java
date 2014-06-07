package es.deusto.miv.ninjakai.manager;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import es.deusto.miv.ninjakai.GameActivity;
import es.deusto.miv.ninjakai.base.BaseScene;
import es.deusto.miv.ninjakai.data.Weapon;
import es.deusto.miv.ninjakai.data.Weapon.WeaponType;
import es.deusto.miv.ninjakai.scene.ArmoryScene;
import es.deusto.miv.ninjakai.scene.GameScene;
import es.deusto.miv.ninjakai.scene.LoadingScene;
import es.deusto.miv.ninjakai.scene.MainScene;
import es.deusto.miv.ninjakai.scene.SettingsScene;
import es.deusto.miv.ninjakai.scene.SplashScene;

public class SceneManager {
	// ---------------------------------------------
	// SCENES
	// ---------------------------------------------

	private BaseScene loadingScene;
	private BaseScene mainScene;
	private BaseScene gameScene;
	private BaseScene settingsScene;
	private BaseScene armoryScene;
	private BaseScene splashScene;

	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------

	private static final SceneManager INSTANCE = new SceneManager();

	private SceneType currentSceneType = SceneType.SCENE_SPLASH;

	private BaseScene currentScene;

	private Engine engine = ResourcesManager.getInstance().engine;

	public enum SceneType {
		SCENE_MAIN, SCENE_GAME, SCENE_LOADING, SCENE_ARMORY, SCENE_SETTINGS, SCENE_SPLASH
	}

	// ---------------------------------------------
	// CLASS LOGIC
	// ---------------------------------------------

	public void setScene(BaseScene scene) {
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}

	public void setScene(SceneType sceneType) {
		switch (sceneType) {
		case SCENE_MAIN:
			setScene(mainScene);
			break;
		case SCENE_GAME:
			setScene(gameScene);
			break;
		case SCENE_LOADING:
			setScene(loadingScene);
			break;
		case SCENE_ARMORY:
			setScene(armoryScene);
			break;
		case SCENE_SETTINGS:
			setScene(settingsScene);
			break;
		default:
			break;
		}
	}

	private void unloadCurrent() {
		switch (currentSceneType) {
		case SCENE_MAIN:
			ResourcesManager.getInstance().unloadMain();
			break;
		case SCENE_GAME:
			ResourcesManager.getInstance().unloadGame();
			break;
		case SCENE_LOADING:
			break;
		case SCENE_ARMORY:
			ResourcesManager.getInstance().unloadArmory();
			break;
		case SCENE_SETTINGS:
			ResourcesManager.getInstance().unloadSettings();
			break;
		default:
			break;
		}
	}

	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		ResourcesManager.getInstance().loadSplashScreen();
		splashScene = new SplashScene();
		currentScene = splashScene;
		ResourcesManager.getInstance().splashSound.play();
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}

	private void disposeSplashScene() {
		ResourcesManager.getInstance().unloadSplashScreen();
		splashScene.disposeScene();
		splashScene = null;
	}

	public void createMainScene() {
		ResourcesManager.getInstance().loadMainResources();
		mainScene = new MainScene();
		loadingScene = new LoadingScene();
		SceneManager.getInstance().setScene(mainScene);
		disposeSplashScene();
	}

	public void loadMainScene(final Engine mEngine) {
		currentScene.disposeScene();

		unloadCurrent();

		setScene(mainScene);
		mEngine.registerUpdateHandler(new TimerHandler(0.1f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						setScene(mainScene);
					}
				}));
	}

	public void loadGameScene(final Engine mEngine) {
		unloadCurrent();
		setScene(loadingScene);
		mEngine.registerUpdateHandler(new TimerHandler(0.1f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						SharedPreferences prefs = PreferenceManager
								.getDefaultSharedPreferences(ResourcesManager
										.getInstance().activity);
						String weaponStr = prefs.getString(
								GameActivity.WEAPON_KEY,
								WeaponType.KUNAI.name());
						WeaponType weapon = WeaponType.valueOf(weaponStr);
						ResourcesManager.getInstance().loadGameResources(
								weapon.graphics);
						Weapon s = Weapon.createSprite(weapon);
						gameScene = new GameScene(s);
						setScene(gameScene);
					}
				}));
	}

	public void loadArmoryScene(final Engine mEngine) {
		unloadCurrent();
		setScene(loadingScene);
		mEngine.registerUpdateHandler(new TimerHandler(0.1f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						ResourcesManager.getInstance().loadArmoryResources();
						armoryScene = new ArmoryScene();
						setScene(armoryScene);
					}
				}));
	}

	public void loadSettingsScene(final Engine mEngine) {
		unloadCurrent();
		setScene(loadingScene);
		mEngine.registerUpdateHandler(new TimerHandler(0.1f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						ResourcesManager.getInstance().loadSettingsResources();
						settingsScene = new SettingsScene();
						setScene(settingsScene);
					}
				}));
	}

	// ---------------------------------------------
	// GETTERS AND SETTERS
	// ---------------------------------------------

	public static SceneManager getInstance() {
		return INSTANCE;
	}

	public SceneType getCurrentSceneType() {
		return currentSceneType;
	}

	public BaseScene getCurrentScene() {
		return currentScene;
	}
}