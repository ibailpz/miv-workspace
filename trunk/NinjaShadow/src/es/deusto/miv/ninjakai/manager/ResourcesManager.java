package es.deusto.miv.ninjakai.manager;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
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

	// ---------------------------------------------
	// FONTS
	// ---------------------------------------------

	public Font fontMenuItems;
	public Font fontArmoryMenuItems;
	public Font fontTitle;
	public Font fontScore;
	public Font fontHUD;
	public Font fontArmoryBottom;

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
	public ITextureRegion kunai_game_region;
	public ITextureRegion stick_game_region;
	public ITextureRegion nunchaku_game_region;
	public ITextureRegion manriki_game_region;
	public ITextureRegion kusarigama_game_region;
	public ITextureRegion katana_game_region;

	public ITiledTextureRegion weapon_region;
	public ITextureRegion aura_protection_region;
	public ITextureRegion backup_protection_region;

	// Settings Texture Regions
	public ITextureRegion settings_background_region;

	// Armory Texture Regions
	public ITextureRegion armory_background_region;
	public ITextureRegion kunai_armory_region;
	public ITextureRegion stick_armory_region;
	public ITextureRegion nunchaku_armory_region;
	public ITextureRegion kusarigama_armory_region;
	public ITextureRegion katana_armory_region;

	private BuildableBitmapTextureAtlas mainTextureAtlas;

	// Level Complete Window
	public ITextureRegion complete_window_region;
	public ITiledTextureRegion complete_stars_region;

	// ---------------------------------------------
	// SOUNDS
	// ---------------------------------------------

	public Music currentSound;
	public Music splashSound;
	public Music punchSound;
	public Music gameOverSound;
	public Music blockSound;
	public Music mainSound;
	public Music gameSound;

	// ---------------------------------------------
	// CLASS LOGIC
	// ---------------------------------------------

	public void loadSplashScreen() {
		try {
			splashSound = MusicFactory.createMusicFromAsset(
					engine.getMusicManager(), activity, "mfx/ninjaKai.mp3");
		} catch (IllegalStateException e) {
			Debug.e(e);
		} catch (IOException e) {
			Debug.e(e);
		}

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(
				activity.getTextureManager(), GameActivity.CAM_WIDTH,
				GameActivity.CAM_HEIGHT, TextureOptions.BILINEAR);
		splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				splashTextureAtlas, activity, "splash.png", 0, 0);
		splashTextureAtlas.load();
	}

	public void unloadSplashScreen() {
		splashSound.stop();
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
		loadGameAudio();
		loadGameFonts();
	}

	public void loadArmoryResources() {
		loadArmoryGraphics();
		loadArmoryFonts();
	}

	public void loadSettingsResources() {
		loadSettingsGraphics();
		loadSettingsFonts();
	}

	private void loadMainGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		mainTextureAtlas = new BuildableBitmapTextureAtlas(
				activity.getTextureManager(), 1024, 1024,
				TextureOptions.BILINEAR);
		menu_background_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mainTextureAtlas, activity, "background.png");

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
		try {
			mainSound = MusicFactory.createMusicFromAsset(
					engine.getMusicManager(), activity, "mfx/mainSound.mid");
			mainSound.setLooping(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadMainFonts() {
		FontFactory.setAssetBasePath("font/");

		final ITexture mainFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		final ITexture titleFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		fontMenuItems = FontFactory.createStrokeFromAsset(
				activity.getFontManager(), mainFontTexture,
				activity.getAssets(), "DOMO.TTF", 50, true, Color.RED, 2,
				Color.BLACK);
		fontMenuItems.load();

		fontTitle = FontFactory.createStrokeFromAsset(
				activity.getFontManager(), titleFontTexture,
				activity.getAssets(), "DOMOI.TTF", 80, true, Color.RED, 2,
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

		aura_protection_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(gameTextureAtlas, activity,
						"aura-protection.png");

		backup_protection_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(gameTextureAtlas, activity, "backup.png");

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
		try {
			punchSound = MusicFactory.createMusicFromAsset(
					engine.getMusicManager(), activity, "mfx/punchSound.mp3");
			gameOverSound = MusicFactory
					.createMusicFromAsset(engine.getMusicManager(), activity,
							"mfx/gameOverSound.mp3");
			blockSound = MusicFactory.createMusicFromAsset(
					engine.getMusicManager(), activity, "mfx/blockSound.mp3");

			gameSound = MusicFactory.createMusicFromAsset(
					engine.getMusicManager(), activity, "mfx/gameSound.mid");
			gameSound.setLooping(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadGameFonts() {
		FontFactory.setAssetBasePath("font/");
		final ITexture hudFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		final ITexture armoryBottomFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		final ITexture mainFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		final ITexture mainArmoryFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		final ITexture titleFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		final ITexture scoreFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		fontHUD = FontFactory.createStrokeFromAsset(activity.getFontManager(),
				hudFontTexture, activity.getAssets(), "DOMO.TTF", 40, true,
				Color.RED, 2, Color.BLACK);
		fontHUD.load();

		fontArmoryBottom = FontFactory.createStrokeFromAsset(
				activity.getFontManager(), armoryBottomFontTexture,
				activity.getAssets(), "DOMO.TTF", 30, true, Color.RED, 2,
				Color.BLACK);
		fontArmoryBottom.load();

		fontMenuItems = FontFactory.createStrokeFromAsset(
				activity.getFontManager(), mainFontTexture,
				activity.getAssets(), "DOMO.TTF", 50, true, Color.RED, 2,
				Color.BLACK);
		fontMenuItems.load();

		fontArmoryMenuItems = FontFactory.createStrokeFromAsset(
				activity.getFontManager(), mainArmoryFontTexture,
				activity.getAssets(), "DOMO.TTF", 50, true, Color.RED, 2,
				Color.BLACK);
		fontArmoryMenuItems.load();

		fontTitle = FontFactory.createStrokeFromAsset(
				activity.getFontManager(), titleFontTexture,
				activity.getAssets(), "DOMOI.TTF", 80, true, Color.RED, 2,
				Color.RED);
		fontTitle.load();

		fontScore = FontFactory.createStrokeFromAsset(
				activity.getFontManager(), scoreFontTexture,
				activity.getAssets(), "DOMO.TTF", 40, true, Color.RED, 2,
				Color.BLACK);
		fontScore.load();
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
		nunchaku_armory_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(armoryTextureAtlas, activity, "nunchaku.png");
		stick_armory_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(armoryTextureAtlas, activity, "stick.png");

		ninja_life_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(armoryTextureAtlas, activity,
						"ninja-life-new.png");

		try {
			this.armoryTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.armoryTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}

	private void loadArmoryFonts() {
		FontFactory.setAssetBasePath("font/");

		final ITexture hudFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		final ITexture armoryBottomFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		final ITexture mainFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		final ITexture mainArmoryFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		final ITexture titleFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		fontArmoryBottom = FontFactory.createStrokeFromAsset(
				activity.getFontManager(), armoryBottomFontTexture,
				activity.getAssets(), "DOMO.TTF", 30, true, Color.RED, 2,
				Color.BLACK);
		fontArmoryBottom.load();

		fontMenuItems = FontFactory.createStrokeFromAsset(
				activity.getFontManager(), mainFontTexture,
				activity.getAssets(), "DOMO.TTF", 50, true, Color.RED, 2,
				Color.BLACK);
		fontMenuItems.load();

		fontArmoryMenuItems = FontFactory.createStrokeFromAsset(
				activity.getFontManager(), mainArmoryFontTexture,
				activity.getAssets(), "DOMO.TTF", 50, true, Color.RED, 2,
				Color.BLACK);
		fontArmoryMenuItems.load();

		fontTitle = FontFactory.createStrokeFromAsset(
				activity.getFontManager(), titleFontTexture,
				activity.getAssets(), "DOMOI.TTF", 80, true, Color.RED, 2,
				Color.RED);
		fontTitle.load();

		fontHUD = FontFactory.createStrokeFromAsset(activity.getFontManager(),
				hudFontTexture, activity.getAssets(), "DOMO.TTF", 40, true,
				Color.RED, 2, Color.BLACK);
		fontHUD.load();
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

	private void loadSettingsFonts() {
		FontFactory.setAssetBasePath("font/");

		final ITexture mainFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		final ITexture titleFontTexture = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		fontMenuItems = FontFactory.createStrokeFromAsset(
				activity.getFontManager(), mainFontTexture,
				activity.getAssets(), "DOMO.TTF", 50, true, Color.RED, 2,
				Color.BLACK);
		fontMenuItems.load();

		fontTitle = FontFactory.createStrokeFromAsset(
				activity.getFontManager(), titleFontTexture,
				activity.getAssets(), "DOMOI.TTF", 80, true, Color.RED, 2,
				Color.RED);
		fontTitle.load();
	}

	public void unloadSettings() {
		unloadSettingsTextures();
	}

	public void unloadArmory() {
		unloadArmoryTextures();
	}

	public void unloadGame() {
		unloadGameTextures();
		unloadGameAudio();
	}

	public void unloadMain(boolean stopSound) {
		if (stopSound)
			unloadMainAudio();
	}

	private void unloadSettingsTextures() {
		settingsTextureAtlas.unload();
	}

	private void unloadArmoryTextures() {
		armoryTextureAtlas.unload();
	}

	private void unloadGameTextures() {
		gameTextureAtlas.unload();
	}

	private void unloadGameAudio() {
		if (gameSound.isPlaying()) {
			gameSound.pause();
			gameSound.seekTo(0);
		}

	}

	private void unloadMainAudio() {
		if (mainSound.isPlaying()) {
			mainSound.pause();
			mainSound.seekTo(0);
		}
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