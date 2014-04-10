package es.deusto.miv.ninjakai.old;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import es.deusto.miv.ninjakai.scene.GameScene;

import android.content.Intent;

public class CopyOfMainScene extends SimpleBaseGameActivity {
	private static int CAMERA_WIDTH = 800;
	private static int CAMERA_HEIGHT = 480;
	private ITextureRegion mBackgroundTextureRegion, newGameTextureRegion,
			armoryTextureRegion, settingsTextureRegion;
	private Sprite newGame, armory, settings;

	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	@Override
	protected void onCreateResources() {
		try {
			ITexture backgroundTexture = new BitmapTexture(
					this.getTextureManager(), new IInputStreamOpener() {
						@Override
						public InputStream open() throws IOException {
							return getAssets().open("gfx/black-background.jpg");
						}
					});
			ITexture newGameTexture = new BitmapTexture(
					this.getTextureManager(), new IInputStreamOpener() {
						@Override
						public InputStream open() throws IOException {
							return getAssets().open("gfx/green-box.png");
						}
					});
			ITexture armoryTexture = new BitmapTexture(
					this.getTextureManager(), new IInputStreamOpener() {
						@Override
						public InputStream open() throws IOException {
							return getAssets().open("gfx/yellow-box.png");
						}
					});
			ITexture settingsTexture = new BitmapTexture(
					this.getTextureManager(), new IInputStreamOpener() {
						@Override
						public InputStream open() throws IOException {
							return getAssets().open("gfx/blue-box.png");
						}
					});
			backgroundTexture.load();
			newGameTexture.load();
			armoryTexture.load();
			settingsTexture.load();
			this.mBackgroundTextureRegion = TextureRegionFactory
					.extractFromTexture(backgroundTexture);
			this.newGameTextureRegion = TextureRegionFactory
					.extractFromTexture(newGameTexture);
			this.armoryTextureRegion = TextureRegionFactory
					.extractFromTexture(armoryTexture);
			this.settingsTextureRegion = TextureRegionFactory
					.extractFromTexture(settingsTexture);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Scene onCreateScene() {
		final Scene hud = new Scene();
		Sprite backgroundSprite = new Sprite(0, 0,
				this.mBackgroundTextureRegion, getVertexBufferObjectManager());
		hud.attachChild(backgroundSprite);

		newGame = new Sprite(192, 63, this.newGameTextureRegion,
				getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				Intent gameIntent = new Intent(CopyOfMainScene.this, GameScene.class);
				startActivity(gameIntent);
				return true;
			}
		};
		armory = new Sprite(400, 63, this.armoryTextureRegion,
				getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				return true;
			}
		};
		settings = new Sprite(604, 63, this.settingsTextureRegion,
				getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				return true;
			}
		};
		hud.attachChild(newGame);
		hud.attachChild(armory);
		hud.attachChild(settings);

		hud.setTouchAreaBindingOnActionDownEnabled(true);
		return hud;
	}

}
