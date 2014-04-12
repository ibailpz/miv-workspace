package es.deusto.miv.ninjakai.manager;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import es.deusto.miv.ninjakai.base.BaseScene;
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
//			ResourcesManager.getInstance().unloadMainTextures();
			break;
		case SCENE_GAME:
			ResourcesManager.getInstance().unloadGameTextures();
			break;
		case SCENE_LOADING:
			// ResourcesManager.getInstance().unloadLoadingScreen();
			break;
		case SCENE_ARMORY:
			ResourcesManager.getInstance().unloadArmoryTextures();
			break;
		case SCENE_SETTINGS:
			ResourcesManager.getInstance().unloadSettingsTextures();
			break;
		default:
			break;
		}
	}

	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		ResourcesManager.getInstance().loadSplashScreen();
		splashScene = new SplashScene();
		currentScene = splashScene;
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}

	private void disposeSplashScene() {
		ResourcesManager.getInstance().unloadSplashScreen();
		splashScene.disposeScene();
		splashScene = null;
	}

	// public void createMainScene(OnCreateSceneCallback pOnCreateSceneCallback)
	// {
	public void createMainScene() {
		ResourcesManager.getInstance().loadMainResources();
		ResourcesManager.getInstance().loadMainTextures();
		mainScene = new MainScene();
		loadingScene = new LoadingScene();
		SceneManager.getInstance().setScene(mainScene);
		// pOnCreateSceneCallback.onCreateSceneFinished(mainScene);
		disposeSplashScene();
	}

	public void loadMainScene(final Engine mEngine) {
		currentScene.disposeScene();
		// gameScene.disposeScene();

		unloadCurrent();
		// ResourcesManager.getInstance().unloadGameTextures();

		setScene(mainScene);
		mEngine.registerUpdateHandler(new TimerHandler(0.1f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
//						ResourcesManager.getInstance().loadMainTextures();
						setScene(mainScene);
					}
				}));
	}

	public void loadGameScene(final Engine mEngine) {
		unloadCurrent();
		setScene(loadingScene);
		// ResourcesManager.getInstance().unloadMainTextures();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						ResourcesManager.getInstance().loadGameResources();
						gameScene = new GameScene();
						setScene(gameScene);
					}
				}));
	}

	public void loadArmoryScene(final Engine mEngine) {
		unloadCurrent();
		setScene(loadingScene);
		// ResourcesManager.getInstance().unloadMainTextures();
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
		// ResourcesManager.getInstance().unloadMainTextures();
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