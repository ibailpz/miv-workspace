package es.deusto.miv.ninjakai.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;

import es.deusto.miv.ninjakai.GameActivity;
import es.deusto.miv.ninjakai.base.BaseScene;
import es.deusto.miv.ninjakai.data.IAreaObserver;
import es.deusto.miv.ninjakai.data.Ninja;
import es.deusto.miv.ninjakai.data.Weapon;
import es.deusto.miv.ninjakai.manager.SceneManager;
import es.deusto.miv.ninjakai.manager.SceneManager.SceneType;

public class GameScene extends BaseScene implements IUpdateHandler {

	private static final int RESUME = 1;
	private static final int EXIT = 2;

	private HUD gameHUD;
	private Text scoreText;
	private boolean paused = false;

	private Ninja ninja;
	private Weapon weapon;

	private Area a1;
	private Area a2;
	private Area a3;
	private Area a4;
	private Area a5;

	public GameScene(Weapon weapon) {
		this.weapon = weapon;
		registerTouchAreas();
	}

	@Override
	public void createScene() {
		gameHUD = new HUD();
		gameHUD.setIgnoreUpdate(false);
		gameHUD.registerUpdateHandler(new IUpdateHandler() {

			@Override
			public void reset() {
			}

			@Override
			public void onUpdate(float pSecondsElapsed) {
				loop(pSecondsElapsed);
			}
		});
		createBackground();
		createHUDTexts();
		createTouchAreas();
		camera.setHUD(gameHUD);
	}

	@Override
	public void onBackKeyPressed() {
		if (!paused) {
			paused = true;
			gameHUD.setChildScene(pauseScene(), false, true, true);
		}
	}

	private MenuScene pauseScene() {
		final MenuScene pauseGame = new MenuScene(camera);
		int heightFix = 10;

		final Rectangle back = new Rectangle(GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2, GameActivity.CAM_WIDTH,
				GameActivity.CAM_HEIGHT, vbom);
		back.setColor(new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(),
				Color.BLACK.getBlue(), 0.5f));

		Text t = new Text(GameActivity.CAM_WIDTH / 2, GameActivity.CAM_HEIGHT,
				resourcesManager.fontTitle, "Pause", vbom);
		t.setY(t.getY() - heightFix - t.getHeight() / 2);

		final IMenuItem btnPlay = new ScaleMenuItemDecorator(new TextMenuItem(
				RESUME, resourcesManager.fontMenuItems, "Resume", vbom), 1.2f,
				1);
		btnPlay.setPosition(GameActivity.CAM_WIDTH / 2,
				(GameActivity.CAM_HEIGHT / 2) + btnPlay.getHeight() - heightFix
						- t.getHeight() / 2);

		final IMenuItem btnExit = new ScaleMenuItemDecorator(new TextMenuItem(
				EXIT, resourcesManager.fontMenuItems, "Exit", vbom), 1.2f, 1);
		btnExit.setPosition(GameActivity.CAM_WIDTH / 2,
				(GameActivity.CAM_HEIGHT / 2) - btnExit.getHeight() - heightFix
						- t.getHeight() / 2);

		pauseGame.attachChild(back);
		pauseGame.attachChild(t);
		pauseGame.addMenuItem(btnPlay);
		pauseGame.addMenuItem(btnExit);

		pauseGame.setBackgroundEnabled(false);
		pauseGame.setOnMenuItemClickListener(new IOnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1,
					float arg2, float arg3) {
				switch (arg1.getID()) {
				case RESUME:
					gameHUD.clearChildScene();
					GameScene.this.paused = false;
					return true;
				case EXIT:
					SceneManager.getInstance().loadMainScene(engine);
					return true;
				default:
					return false;
				}
			}
		});
		return pauseGame;
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		// TODO Detach and dispose all sprites, hud, etc.
		camera.setHUD(null);
	}

	public void createBackground() {
		gameHUD.attachChild(new Sprite(GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2,
				resourcesManager.game_background_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		});
	}

	private void createHUDTexts() {
		scoreText = new Text(20, 420, resourcesManager.fontHUD,
				"Score: 0123456789", new TextOptions(HorizontalAlign.LEFT),
				vbom);
		scoreText.setAnchorCenter(0, 0);
		scoreText.setText("Score: 0");
		gameHUD.attachChild(scoreText);
	}

	private void createTouchAreas() {
		a1 = new Area(GameActivity.CAM_WIDTH / 4, GameActivity.CAM_HEIGHT
				- GameActivity.CAM_HEIGHT / 4, GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2, vbom, 0);

		a2 = new Area(3 * GameActivity.CAM_WIDTH / 4, GameActivity.CAM_HEIGHT
				- GameActivity.CAM_HEIGHT / 4, GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2, vbom, 1);

		a3 = new Area(GameActivity.CAM_WIDTH / 6, GameActivity.CAM_HEIGHT / 4,
				GameActivity.CAM_WIDTH / 3, GameActivity.CAM_HEIGHT / 2, vbom,
				2);

		a4 = new Area(GameActivity.CAM_WIDTH / 2, GameActivity.CAM_HEIGHT / 5,
				GameActivity.CAM_WIDTH / 3, 2 * GameActivity.CAM_HEIGHT / 5,
				vbom, 3);

		a5 = new Area(5 * GameActivity.CAM_WIDTH / 6,
				GameActivity.CAM_HEIGHT / 4, GameActivity.CAM_WIDTH / 3,
				GameActivity.CAM_HEIGHT / 2, vbom, 4);

		a1.attachChild(new Text(GameActivity.CAM_WIDTH / 4,
				GameActivity.CAM_HEIGHT / 4, resourcesManager.fontHUD, "1",
				new TextOptions(HorizontalAlign.LEFT), vbom));

		a2.attachChild(new Text(GameActivity.CAM_WIDTH / 4,
				GameActivity.CAM_HEIGHT / 4, resourcesManager.fontHUD, "2",
				new TextOptions(HorizontalAlign.LEFT), vbom));

		a3.attachChild(new Text(GameActivity.CAM_WIDTH / 6,
				GameActivity.CAM_HEIGHT / 4, resourcesManager.fontHUD, "3",
				new TextOptions(HorizontalAlign.LEFT), vbom));

		a4.attachChild(new Text(GameActivity.CAM_WIDTH / 6,
				GameActivity.CAM_HEIGHT / 4, resourcesManager.fontHUD, "4",
				new TextOptions(HorizontalAlign.LEFT), vbom));

		a5.attachChild(new Text(GameActivity.CAM_WIDTH / 6,
				GameActivity.CAM_HEIGHT / 4, resourcesManager.fontHUD, "5",
				new TextOptions(HorizontalAlign.LEFT), vbom));

		a1.setAlpha(0);
		a2.setAlpha(0);
		a3.setAlpha(0);
		a4.setAlpha(0);
		a5.setAlpha(0);

		ninja = new Ninja(GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2, resourcesManager.ninja_region,
				vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		ninja.setScale(0.9f);
		ninja.setY(ninja.getY() + 40);
		// ninja.setWeapon(weapon);

		// registerTouchAreas();

		gameHUD.attachChild(a1);
		gameHUD.attachChild(a2);
		gameHUD.attachChild(a3);
		gameHUD.attachChild(a4);
		gameHUD.attachChild(a5);
		gameHUD.attachChild(ninja);
	}

	private void registerTouchAreas() {
		ninja.setWeapon(weapon);

		gameHUD.registerTouchArea(a1);
		gameHUD.registerTouchArea(a2);
		gameHUD.registerTouchArea(a3);
		gameHUD.registerTouchArea(a4);
		gameHUD.registerTouchArea(a5);

		ninja.getWeapon().registerAreaObserver(0, a1);
		ninja.getWeapon().registerAreaObserver(1, a2);
		ninja.getWeapon().registerAreaObserver(2, a3);
		ninja.getWeapon().registerAreaObserver(3, a4);
		ninja.getWeapon().registerAreaObserver(4, a5);
	}

	private Sprite createTrunk(int area) {
		final Sprite trunk = new Sprite(0, 0, resourcesManager.trunk_region,
				vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		switch (area) {
		case 0:
			trunk.setRotation(65);
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			trunk.setRotation(-62);
			break;
		case 4:
			trunk.setRotation(-118);
			break;
		default:
			break;
		}

		return trunk;
	}

	private Sprite createShuriken(int area) {
		final Sprite shuriken = new Sprite(0, 0,
				resourcesManager.shuriken_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};

		return shuriken;
	}

	private Sprite createBomb(int area) {
		final Sprite bomb = new Sprite(0, 0, resourcesManager.bomb_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		return bomb;
	}

	private void setObjectMovement(final Sprite obj, final int area, int speed) {
		float fromx;
		float fromy;
		int directionX = 1;
		int directionY = 1;

		switch (area) {
		case 0:
			fromx = -obj.getWidth();
			fromy = GameActivity.CAM_HEIGHT + obj.getHeight();
			directionX = -1;
			break;
		case 1:
			fromx = GameActivity.CAM_WIDTH + obj.getWidth();
			fromy = GameActivity.CAM_HEIGHT + obj.getHeight();
			break;
		case 2:
			fromx = -obj.getWidth();
			fromy = GameActivity.CAM_WIDTH / 4 - obj.getHeight();
			directionY = -1;
			directionX = -1;
			break;
		case 3:
			fromx = GameActivity.CAM_WIDTH / 2;
			fromy = -obj.getHeight();
			directionY = -1;
			directionX = 0;
			break;
		case 4:
		default:
			fromx = GameActivity.CAM_WIDTH + obj.getWidth();
			fromy = GameActivity.CAM_WIDTH / 4 - obj.getHeight();
			directionY = -1;
			break;
		}

		MoveModifier modifier = new MoveModifier(speed, fromx, fromy,
				GameActivity.CAM_WIDTH / 2 + 50 * directionX,
				GameActivity.CAM_HEIGHT / 2 + 25 * directionY) {

			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.onModifierFinished(pItem);
				obj.setTag(-1);
				if (!weapon.isProtecting(area)) {
					Debug.i("HIT!!");
					// TODO If weapon not protecting ninja, hit
				}
			}
		};

		obj.registerEntityModifier(modifier);
	}

	private void throwObject(int area) {
		int objType = (int) (Math.random() * 3); // TODO Change to number of
													// objects
		Sprite obj;
		int speed;
		switch (objType) {
		case 0:
			obj = createTrunk(area);
			speed = 1;
			break;
		case 1:
			obj = createShuriken(area);
			speed = 2;
			break;
		case 2:
			obj = createBomb(area);
			speed = 3;
			break;
		default:
			return;
		}

		obj.setX(obj.getX() + obj.getWidth());
		obj.setY(obj.getY() + obj.getHeight());
		obj.setTag(0);

		setObjectMovement(obj, area, speed);

		gameHUD.attachChild(obj);
	}

	// Game loop
	private void loop(float pSecondsElapsed) {
		// For each possibly generated object, remove an old one
		gameHUD.detachChild(-1);

		int area = (int) (Math.random() * 5);
		if (Math.random() < 0.01) {
			throwObject(area);
		}
	}

	private class Area extends Rectangle implements IAreaObserver {

		private final int area;

		public Area(float pX, float pY, float pWidth, float pHeight,
				VertexBufferObjectManager pVertexBufferObjectManger, int area) {
			super(pX, pY, pWidth, pHeight, pVertexBufferObjectManger);
			this.area = area;
			this.setColor(Color.WHITE);
			this.setAlpha(0);
		}

		public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
			if (touchEvent.isActionUp()) {
				Debug.i("Area " + (area + 1));
				ninja.protect(area);
			}
			return true;
		}

		@Override
		public void onAreaProtected() {
			this.setAlpha(0.1f);
		}

		@Override
		public void onAreaUnprotected() {
			this.setAlpha(0);
		}

	}
}
