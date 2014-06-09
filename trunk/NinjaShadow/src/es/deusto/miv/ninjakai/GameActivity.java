package es.deusto.miv.ninjakai;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;
import es.deusto.miv.ninjakai.manager.ResourcesManager;
import es.deusto.miv.ninjakai.manager.SceneManager;

public class GameActivity extends BaseGameActivity {

	public static final int CAM_WIDTH = 800;
	public static final int CAM_HEIGHT = 480;
	
	public static final String WEAPON_KEY = "WeaponKey";
	public static final String HIGH_SCORE_KEY = "HighScoreKey";
	public static final String TOTAL_SCORE_KEY = "TotalScoreKey";
	public static final String SOUND_KEY = "SoundKey";
	public static final String LIFES_KEY = "LifesKey";

	private BoundCamera camera;

	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new LimitedFPSEngine(pEngineOptions, 60);
	}

	public EngineOptions onCreateEngineOptions() {
		camera = new BoundCamera(0, 0, CAM_WIDTH, CAM_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(),
				this.camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		// engineOptions.getRenderOptions().setMultiSampling(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		return engineOptions;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
		}
		return false;
	}

	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws IOException {
		ResourcesManager.prepareManager(mEngine, this, camera,
				getVertexBufferObjectManager());
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws IOException {
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
	}

	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {

		mEngine.registerUpdateHandler(new TimerHandler(2.5f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						SceneManager.getInstance().createMainScene();
					}
				}));

		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// System.exit(0);
	}
}