package es.deusto.miv.ninjakai.manager;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.graphics.Color;
import es.deusto.miv.ninjakai.GameActivity;

public class ResourcesManager {
	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------

	private static final ResourcesManager INSTANCE = new ResourcesManager();

	public Engine engine;
	public GameActivity activity;
	public BoundCamera camera;
	public VertexBufferObjectManager vbom;

	public Font fontMenuItems;
	public Font fontTitle;
	public Font fontHUD;

	// ---------------------------------------------
	// TEXTURES & TEXTURE REGIONS
	// ---------------------------------------------

	public ITextureRegion menu_background_region;
	// public ITextureRegion play_region;
	// public ITextureRegion settings_region;
	// public ITextureRegion armory_region;
	public ITextureRegion splash_region;

	// Game Texture
	public BuildableBitmapTextureAtlas gameTextureAtlas;

	public BuildableBitmapTextureAtlas settingsTextureAtlas;
	public BuildableBitmapTextureAtlas armoryTextureAtlas;
	public BitmapTextureAtlas splashTextureAtlas;

	// Game Texture Regions
	public ITextureRegion game_background_region;

	// Settings Texture Regions
	public ITextureRegion settings_background_region;

	// Armory Texture Regions
	public ITextureRegion armory_background_region;

	private BuildableBitmapTextureAtlas mainTextureAtlas;

	// Level Complete Window
	public ITextureRegion complete_window_region;
	public ITiledTextureRegion complete_stars_region;

	// ---------------------------------------------
	// CLASS LOGIC
	// ---------------------------------------------

	public void loadSplashScreen() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(
				activity.getTextureManager(), GameActivity.CAM_WIDTH,
				GameActivity.CAM_HEIGHT, TextureOptions.BILINEAR);
		splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				splashTextureAtlas, activity, "splash.png", 0, 0);
		splashTextureAtlas.load();
	}

	public void unloadSplashScreen() {
		splashTextureAtlas.unload();
		splash_region = null;
	}

	public void loadMainResources() {
		loadMainGraphics();
		loadMainAudio();
		loadMainFonts();
	}

	public void loadGameResources() {
		loadGameGraphics();
		loadGameFonts();
		loadGameAudio();
	}

	public void loadArmoryResources() {
		loadArmoryGraphics();
	}

	public void loadSettingsResources() {
		loadSettingsGraphics();
	}

	private void loadMainGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		mainTextureAtlas = new BuildableBitmapTextureAtlas(
				activity.getTextureManager(), 1024, 1024,
				TextureOptions.BILINEAR);
		menu_background_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mainTextureAtlas, activity, "background.png");

		// play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
		// mainTextureAtlas, activity, "play.png");
		// settings_region = BitmapTextureAtlasTextureRegionFactory
		// .createFromAsset(mainTextureAtlas, activity, "options.png");
		// armory_region =
		// BitmapTextureAtlasTextureRegionFactory.createFromAsset(
		// mainTextureAtlas, activity, "armory.png");

		try {
			this.mainTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.mainTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	private void loadMainAudio() {

	}

	private void loadMainFonts() {
		FontFactory.setAssetBasePath("font/");
		final ITexture hudFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		final ITexture mainFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		final ITexture titleFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		fontHUD = FontFactory.createStrokeFromAsset(activity.getFontManager(),
				hudFontTexture, activity.getAssets(), "DOMO.TTF", 40, true,
				Color.RED, 2, Color.BLACK);
		fontHUD.load();

		fontMenuItems = FontFactory.createStrokeFromAsset(
				activity.getFontManager(), mainFontTexture,
				activity.getAssets(), "DOMO.TTF", 60, true, Color.RED, 2,
				Color.BLACK);
		fontMenuItems.load();

		fontTitle = FontFactory.createStrokeFromAsset(
				activity.getFontManager(), titleFontTexture,
				activity.getAssets(), "DOMOI.TTF", 100, true, Color.RED, 2,
				Color.RED);
		fontTitle.load();
	}

	private void loadGameGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
		gameTextureAtlas = new BuildableBitmapTextureAtlas(
				activity.getTextureManager(), 2048, 2048,
				TextureOptions.BILINEAR);

		game_background_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(gameTextureAtlas, activity, "forest.png");

		try {
			this.gameTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.gameTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	private void loadGameFonts() {

	}

	private void loadGameAudio() {

	}

	private void loadArmoryGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/armory/");
		armoryTextureAtlas = new BuildableBitmapTextureAtlas(
				activity.getTextureManager(), 1024, 1024,
				TextureOptions.BILINEAR);
		armory_background_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(armoryTextureAtlas, activity,
						"armory_background.png");
		try {
			this.armoryTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.armoryTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	private void loadSettingsGraphics() {
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("gfx/settings/");
		settingsTextureAtlas = new BuildableBitmapTextureAtlas(
				activity.getTextureManager(), 1024, 1024,
				TextureOptions.BILINEAR);
		settings_background_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(settingsTextureAtlas, activity,
						"settings_background.png");
		try {
			this.settingsTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.settingsTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	public void unloadSettingsTextures() {
		settingsTextureAtlas.unload();
	}

	public void loadMainTextures() {
		mainTextureAtlas.load();
	}

	public void unloadMainTextures() {
		mainTextureAtlas.unload();
	}

	public void unloadArmoryTextures() {
		armoryTextureAtlas.unload();
	}

	public void unloadGameTextures() {
		// TODO (Since we did not create any textures for game scene yet)
		gameTextureAtlas.unload();
	}

	/**
	 * @param engine
	 * @param activity
	 * @param camera
	 * @param vbom
	 * <br>
	 * <br>
	 *            We use this method at beginning of game loading, to prepare
	 *            Resources Manager properly, setting all needed parameters, so
	 *            we can latter access them from different classes (eg. scenes)
	 */
	public static void prepareManager(Engine engine, GameActivity activity,
			BoundCamera camera, VertexBufferObjectManager vbom) {
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;
	}

	// ---------------------------------------------
	// GETTERS AND SETTERS
	// ---------------------------------------------

	public static ResourcesManager getInstance() {
		return INSTANCE;
	}
}