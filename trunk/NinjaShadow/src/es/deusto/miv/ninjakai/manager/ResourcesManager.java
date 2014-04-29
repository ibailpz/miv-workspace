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
	public ITextureRegion splash_region;

	// Game Texture
	public BuildableBitmapTextureAtlas gameTextureAtlas;

	public BuildableBitmapTextureAtlas settingsTextureAtlas;
	public BuildableBitmapTextureAtlas armoryTextureAtlas;
	public BitmapTextureAtlas splashTextureAtlas;

	// Game Texture Regions
	public ITextureRegion game_background_region;
	public ITextureRegion ninja_region;
	public ITextureRegion trunk_region;
	public ITextureRegion shuriken_region;
	public ITextureRegion bomb_region;
	public ITextureRegion ninja_life_region;
	public ITextureRegion speedUp_region;
	public ITextureRegion aura_region;
	public ITextureRegion backup_region;
	public ITextureRegion extraPoints_region;	

	public ITiledTextureRegion weapon_region;

	// Settings Texture Regions
	public ITextureRegion settings_background_region;

	// Armory Texture Regions
	public ITextureRegion armory_background_region;
	public ITextureRegion kunai_armory_region;
	public ITextureRegion stick_armory_region;
	public ITextureRegion nunchaku_armory_region;
	public ITextureRegion manriki_armory_region;
	public ITextureRegion kusarigama_armory_region;
	public ITextureRegion katana_armory_region;

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
		loadMainFonts();
		loadMainAudio();
	}

	public void loadGameResources(String weapon) {
		loadGameGraphics(weapon);
		loadMainFonts();
		loadGameAudio();
	}

	public void loadArmoryResources() {
		loadArmoryGraphics();
		loadMainFonts();
		loadMainAudio();
	}

	public void loadSettingsResources() {
		loadSettingsGraphics();
		loadMainFonts();
		loadMainAudio();
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
		// TODO General audio
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

	private void loadGameGraphics(String weapon) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
		gameTextureAtlas = new BuildableBitmapTextureAtlas(
				activity.getTextureManager(), 2048, 2048,
				TextureOptions.BILINEAR);

		game_background_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(gameTextureAtlas, activity, "forest.png");

		ninja_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				gameTextureAtlas, activity, "ninja.png");

		trunk_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				gameTextureAtlas, activity, "tronco.png");

		shuriken_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(gameTextureAtlas, activity, "shuriken.png");

		bomb_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				gameTextureAtlas, activity, "bomb.png");

		ninja_life_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(gameTextureAtlas, activity,
						"ninja-life-new.png");

		weapon_region = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(gameTextureAtlas, activity, weapon, 1, 1);

		speedUp_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(gameTextureAtlas, activity, "speedup.png");

		aura_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				gameTextureAtlas, activity, "aura.png");

		backup_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				gameTextureAtlas, activity, "backup.png");

		extraPoints_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(gameTextureAtlas, activity, "extrapoints.png");

		try {
			this.gameTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.gameTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	private void loadGameAudio() {
		// TODO Game audio
	}

	private void loadArmoryGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/armory/");
		armoryTextureAtlas = new BuildableBitmapTextureAtlas(
				activity.getTextureManager(), 1024, 1024,
				TextureOptions.BILINEAR);

		armory_background_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(armoryTextureAtlas, activity, "background.png");
		kunai_armory_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(armoryTextureAtlas, activity, "kunai.png");
		katana_armory_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(armoryTextureAtlas, activity, "katana.png");
		kusarigama_armory_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(armoryTextureAtlas, activity, "kusarigama.png");
		manriki_armory_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(armoryTextureAtlas, activity, "manriki.png");
		nunchaku_armory_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(armoryTextureAtlas, activity, "nunchaku.png");
		stick_armory_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(armoryTextureAtlas, activity, "stick.png");

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
						"background.png");
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