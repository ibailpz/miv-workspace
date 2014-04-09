package es.deusto.miv.ninjakai.scene;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import es.deusto.miv.ninjakai.data.Ninja;

public class CopyOfGameScene extends SimpleBaseGameActivity {
	private static int CAMERA_WIDTH = 800;
	private static int CAMERA_HEIGHT = 480;
	private ITextureRegion mBackgroundTextureRegion;
	private ITiledTextureRegion mNinja;
	private Ninja ninja;

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
							return getAssets().open("gfx/forest.png");
						}
					});
			ITexture ninja = new BitmapTexture(this.getTextureManager(),
					new IInputStreamOpener() {
						@Override
						public InputStream open() throws IOException {
							return getAssets().open("gfx/ninja.png");
						}
					});
			backgroundTexture.load();
			ninja.load();
			this.mBackgroundTextureRegion = TextureRegionFactory
					.extractFromTexture(backgroundTexture);
			this.mNinja = TextureRegionFactory.extractTiledFromTexture(ninja, 1, 1);
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
		
		ninja = new Ninja(400, 63, mNinja, getVertexBufferObjectManager(), null, 3, 0, 0, null);
		hud.attachChild(ninja);
		
		hud.setTouchAreaBindingOnActionDownEnabled(true);
		return hud;
	}

}
