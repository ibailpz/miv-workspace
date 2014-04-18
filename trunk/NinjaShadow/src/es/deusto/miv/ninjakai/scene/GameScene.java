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
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.util.debug.Debug;

import es.deusto.miv.ninjakai.GameActivity;
import es.deusto.miv.ninjakai.base.BaseScene;
import es.deusto.miv.ninjakai.manager.SceneManager;
import es.deusto.miv.ninjakai.manager.SceneManager.SceneType;

public class GameScene extends BaseScene implements IUpdateHandler {

	private static final int RESUME = 1;
	private static final int EXIT = 2;

	private HUD gameHUD;
	private Text scoreText;
	private boolean paused = false;

	private Rectangle a1;
	private Rectangle a2;
	private Rectangle a3;
	private Rectangle a4;
	private Rectangle a5;

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

		final IMenuItem btnPlay = new ScaleMenuItemDecorator(new TextMenuItem(
				RESUME, resourcesManager.fontMenuItems, "Resume", vbom), 1.2f,
				1);
		btnPlay.setPosition(GameActivity.CAM_WIDTH / 2,
				(GameActivity.CAM_HEIGHT / 2) + btnPlay.getHeight());

		final IMenuItem btnExit = new ScaleMenuItemDecorator(new TextMenuItem(
				EXIT, resourcesManager.fontMenuItems, "Exit", vbom), 1.2f, 1);
		btnExit.setPosition(GameActivity.CAM_WIDTH / 2,
				(GameActivity.CAM_HEIGHT / 2) - btnExit.getHeight());

		final Rectangle back = new Rectangle(GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2, GameActivity.CAM_WIDTH,
				GameActivity.CAM_HEIGHT, vbom);
		back.setColor(new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(),
				Color.BLACK.getBlue(), 0.5f));

		pauseGame.attachChild(back);
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
		a1 = new Rectangle(GameActivity.CAM_WIDTH / 4, GameActivity.CAM_HEIGHT
				- GameActivity.CAM_HEIGHT / 4, GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2, vbom) {
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
				if (touchEvent.isActionUp()) {
					// TODO Handle area 1
					Debug.i("Area 1");
				}
				return true;
			};
		};

		a2 = new Rectangle(3 * GameActivity.CAM_WIDTH / 4,
				GameActivity.CAM_HEIGHT - GameActivity.CAM_HEIGHT / 4,
				GameActivity.CAM_WIDTH / 2, GameActivity.CAM_HEIGHT / 2, vbom) {
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
				if (touchEvent.isActionUp()) {
					// TODO Handle area 2
					Debug.i("Area 2");
				}
				return true;
			};
		};

		a3 = new Rectangle(GameActivity.CAM_WIDTH / 6,
				GameActivity.CAM_HEIGHT / 4, GameActivity.CAM_WIDTH / 3,
				GameActivity.CAM_HEIGHT / 2, vbom) {
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
				if (touchEvent.isActionUp()) {
					// TODO Handle area 3
					Debug.i("Area 3");
				}
				return true;
			};
		};

		a4 = new Rectangle(GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 4, GameActivity.CAM_WIDTH / 3,
				GameActivity.CAM_HEIGHT / 2, vbom) {
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
				if (touchEvent.isActionUp()) {
					// TODO Handle area 4
					Debug.i("Area 4");
				}
				return true;
			};
		};

		a5 = new Rectangle(5 * GameActivity.CAM_WIDTH / 6,
				GameActivity.CAM_HEIGHT / 4, GameActivity.CAM_WIDTH / 3,
				GameActivity.CAM_HEIGHT / 2, vbom) {
			public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y) {
				if (touchEvent.isActionUp()) {
					// TODO Handle area 5
					Debug.i("Area 5");
				}
				return true;
			};
		};

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

		Sprite ninja = new Sprite(GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2, resourcesManager.ninja_region,
				vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		ninja.setScale(0.75f);
		ninja.setY(ninja.getY() + 40);

		registerTouchAreas();

		gameHUD.attachChild(a1);
		gameHUD.attachChild(a2);
		gameHUD.attachChild(a3);
		gameHUD.attachChild(a4);
		gameHUD.attachChild(a5);
		gameHUD.attachChild(ninja);
	}

	private void registerTouchAreas() {
		gameHUD.registerTouchArea(a1);
		gameHUD.registerTouchArea(a2);
		gameHUD.registerTouchArea(a3);
		gameHUD.registerTouchArea(a4);
		gameHUD.registerTouchArea(a5);
	}

	private void createTrunk() {
		final Sprite trunk = new Sprite(0, 0, resourcesManager.tronco_region,
				vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};

		trunk.setX(-trunk.getWidth());
		trunk.setY(-trunk.getHeight());
		trunk.setTag(0);

		MoveModifier modifier = new MoveModifier(1, -trunk.getWidth(),
				-trunk.getHeight(), GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.onModifierFinished(pItem);
				trunk.setTag(-1);
			}
		};
		trunk.registerEntityModifier(modifier);

		gameHUD.attachChild(trunk);
	}

	private void createShuriken() {
		final Sprite shuriken = new Sprite(GameActivity.CAM_WIDTH, 0,
				resourcesManager.shuriken_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};

		shuriken.setX(shuriken.getX() + shuriken.getWidth());
		shuriken.setY(-shuriken.getHeight());
		shuriken.setTag(0);

		MoveModifier modifier = new MoveModifier(2, GameActivity.CAM_WIDTH
				+ shuriken.getWidth(), -shuriken.getHeight(),
				GameActivity.CAM_WIDTH / 2, GameActivity.CAM_HEIGHT / 2) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.onModifierFinished(pItem);
				shuriken.setTag(-1);
			}
		};
		shuriken.registerEntityModifier(modifier);

		gameHUD.attachChild(shuriken);
	}

	private void createBomb() {
		final Sprite bomb = new Sprite(GameActivity.CAM_WIDTH,
				GameActivity.CAM_HEIGHT, resourcesManager.bomb_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		bomb.setX(bomb.getX() + bomb.getWidth());
		bomb.setY(bomb.getY() + bomb.getHeight());
		bomb.setTag(0);

		MoveModifier modifier = new MoveModifier(3, GameActivity.CAM_WIDTH
				+ bomb.getWidth(), GameActivity.CAM_HEIGHT + bomb.getHeight(),
				GameActivity.CAM_WIDTH / 2, GameActivity.CAM_HEIGHT / 2) {

			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.onModifierFinished(pItem);
				bomb.setTag(-1);
			}
		};
		bomb.registerEntityModifier(modifier);

		gameHUD.attachChild(bomb);
	}

	// Game loop
	private void loop(float pSecondsElapsed) {
		while (gameHUD.detachChild(-1) != null) {
		}
		if (Math.random() < 0.005) {
			createBomb();
		}
		if (Math.random() < 0.005) {
			createShuriken();
		}
		if (Math.random() < 0.005) {
			createTrunk();
		}
	}
}
