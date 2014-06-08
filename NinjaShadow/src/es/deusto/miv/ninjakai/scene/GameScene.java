package es.deusto.miv.ninjakai.scene;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
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
import es.deusto.miv.ninjakai.data.Accumulator;
import es.deusto.miv.ninjakai.data.IAreaObserver;
import es.deusto.miv.ninjakai.data.IPowerUpActivated;
import es.deusto.miv.ninjakai.data.Ninja;
import es.deusto.miv.ninjakai.data.PowerUp;
import es.deusto.miv.ninjakai.data.PowerUp.HitPowerUp;
import es.deusto.miv.ninjakai.data.PowerUp.TimedPowerUp;
import es.deusto.miv.ninjakai.data.Weapon;
import es.deusto.miv.ninjakai.data.powerup.Aura;
import es.deusto.miv.ninjakai.data.powerup.Backup;
import es.deusto.miv.ninjakai.data.powerup.ExtraPoints;
import es.deusto.miv.ninjakai.data.powerup.SpeedUp;
import es.deusto.miv.ninjakai.manager.ResourcesManager;
import es.deusto.miv.ninjakai.manager.SceneManager;
import es.deusto.miv.ninjakai.manager.SceneManager.SceneType;

public class GameScene extends BaseScene implements IUpdateHandler,
		IOnMenuItemClickListener, IPowerUpActivated {

	private static final int RESUME = 1;
	private static final int RESTART = 2;
	private static final int EXIT = 3;

	private HUD gameHUD;
	private Text scoreText;
	private boolean paused = false;
	private boolean finished = false;

	private Ninja ninja;

	private Sprite[] lifes;

	private Accumulator speedUpAcumulator, auraAcumulator, backupAcumulator,
			extraPointsAcumulator;

	private Area a1, a2, a3, a4, a5;

	private Rectangle flash;
	private AlphaModifier flashModifier = new AlphaModifier(0.1f, 0, 0.2f) {
		@Override
		protected void onModifierFinished(IEntity pItem) {
			super.onModifierFinished(pItem);
			AlphaModifier m = new AlphaModifier(0.05f, 0.2f, 0);
			flash.registerEntityModifier(m);
		}
	};

	private final String scoreString = "Score: %d%nx%.1f";
	private final double pointsPerBlock = 100;
	private final double minMult = 1.0;
	private final double maxMult = 10.0;
	private final double multInc = 0.1;
	private double mult = minMult;
	private double score = 0;

	public static final double powerUpTTL = 5;
	private final double powerUpDiff = 30;
	private double powerUpTime = 0.0;

	private ArrayList<TimedPowerUp> timedPowerUps = new ArrayList<PowerUp.TimedPowerUp>();
	private ArrayList<HitPowerUp> hitPowerUps = new ArrayList<PowerUp.HitPowerUp>();

	private Sprite aura_protection;
	private Sprite backup;

	public GameScene(Weapon weapon) {
		ninja.setWeapon(weapon);
		gameHUD.attachChild(ninja.getWeapon());
		registerWeaponAreas();
		scoreText.setText(String.format(scoreString, (int) score, mult));
	}

	@Override
	public void onBackKeyPressed() {
		if (!paused && !finished) {
			paused = true;
			gameHUD.setChildScene(pauseScene(), false, true, true);
		} else if (finished) {
			SceneManager.getInstance().loadMainScene(engine);
		}
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case RESUME:
			gameHUD.clearChildScene();
			GameScene.this.paused = false;
			return true;
		case RESTART:
			SceneManager.getInstance().loadGameScene(engine);
			return true;
		case EXIT:
			SceneManager.getInstance().loadMainScene(engine);
			return true;
		default:
			return false;
		}
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		camera.setHUD(null);
	}

	@Override
	public void createScene() {
		init();
		createBackground();
		createHUDTexts();
		createTouchAreas();
		createFlash();
		createPowerUpsAcumulator();

		gameHUD.attachChild(backup);
		gameHUD.attachChild(ninja);
		gameHUD.attachChild(aura_protection);
		camera.setHUD(gameHUD);
	}

	private void init() {
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
		// TODO Load lifes from preferences
		ninja.setLifes(3);

		aura_protection = new Sprite(ninja.getX(), ninja.getY(),
				ResourcesManager.getInstance().aura_protection_region, vbom);
		aura_protection.setScale(0.5f);
		aura_protection.setAlpha(0);

		backup = new Sprite(ninja.getX(), ninja.getY(),
				ResourcesManager.getInstance().backup_protection_region, vbom);
		backup.setColor(Color.BLACK);
		backup.setScale(0.7f);
		backup.setAlpha(0);
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
				(GameActivity.CAM_HEIGHT / 2) + btnPlay.getHeight() - heightFix);

		final IMenuItem btnRestart = new ScaleMenuItemDecorator(
				new TextMenuItem(RESTART, resourcesManager.fontMenuItems,
						"Restart", vbom), 1.2f, 1);
		btnRestart.setPosition(GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2 - heightFix - btnPlay.getHeight()
						/ 2);

		final IMenuItem btnExit = new ScaleMenuItemDecorator(new TextMenuItem(
				EXIT, resourcesManager.fontMenuItems, "Exit", vbom), 1.2f, 1);
		btnExit.setPosition(GameActivity.CAM_WIDTH / 2,
				(GameActivity.CAM_HEIGHT / 2) - btnExit.getHeight() - heightFix
						- btnPlay.getHeight() / 2 - btnRestart.getHeight() / 2);

		pauseGame.attachChild(back);
		pauseGame.attachChild(t);
		pauseGame.addMenuItem(btnPlay);
		pauseGame.addMenuItem(btnRestart);
		pauseGame.addMenuItem(btnExit);

		pauseGame.setBackgroundEnabled(false);
		pauseGame.setOnMenuItemClickListener(this);
		return pauseGame;
	}

	private MenuScene gameOverScene() {
		final MenuScene gameOverScene = new MenuScene(camera);
		int heightFix = 10;

		final Rectangle back = new Rectangle(GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2, GameActivity.CAM_WIDTH,
				GameActivity.CAM_HEIGHT, vbom);

		final Text t = new Text(GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT, resourcesManager.fontTitle,
				"Game Over", vbom);
		t.setY(t.getY() - heightFix - t.getHeight() / 2);
		t.setAlpha(0);
		t.setVisible(false);

		final TextMenuItem scoreText = new TextMenuItem(0,
				resourcesManager.fontScore, "Score: " + score, vbom);
		scoreText.setPosition(GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2 + 75);
		scoreText.setAlpha(0);
		scoreText.setVisible(false);

		final IMenuItem btnRestart = new ScaleMenuItemDecorator(
				new TextMenuItem(RESTART, resourcesManager.fontMenuItems,
						"Restart", vbom), 1.2f, 1);
		btnRestart.setPosition(GameActivity.CAM_WIDTH / 2,
				(GameActivity.CAM_HEIGHT / 2) + btnRestart.getHeight()
						- heightFix - t.getHeight() / 2);
		btnRestart.setAlpha(0);
		btnRestart.setVisible(false);

		final IMenuItem btnExit = new ScaleMenuItemDecorator(new TextMenuItem(
				EXIT, resourcesManager.fontMenuItems, "Exit", vbom), 1.2f, 1);
		btnExit.setPosition(GameActivity.CAM_WIDTH / 2,
				(GameActivity.CAM_HEIGHT / 2) - btnExit.getHeight() - heightFix
						- t.getHeight() / 2);
		btnExit.setAlpha(0);
		btnExit.setVisible(false);

		back.setAlpha(0);
		back.setColor(new Color(Color.BLACK.getRed(), Color.BLACK.getGreen(),
				Color.BLACK.getBlue(), 0.5f));
		AlphaModifier m = new AlphaModifier(1, 0, 0.5f) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.onModifierFinished(pItem);
				AlphaModifier m = new AlphaModifier(1, 0, 1);
				t.setVisible(true);
				scoreText.setVisible(true);
				btnRestart.setVisible(true);
				btnExit.setVisible(true);
				t.registerEntityModifier(m);
				btnRestart.registerEntityModifier(m);
				btnExit.registerEntityModifier(m);
			}
		};
		back.registerEntityModifier(m);

		gameOverScene.attachChild(back);
		gameOverScene.attachChild(t);
		gameOverScene.attachChild(scoreText);
		gameOverScene.addMenuItem(btnRestart);
		gameOverScene.addMenuItem(btnExit);

		gameOverScene.setBackgroundEnabled(false);
		gameOverScene.setOnMenuItemClickListener(this);
		return gameOverScene;
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
		scoreText = new Text(0, GameActivity.CAM_HEIGHT,
				resourcesManager.fontHUD, "Score: 0123456789\nx99.9",
				new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setPosition(scoreText.getX() + 10, scoreText.getY()
				- scoreText.getHeight() - 10);
		scoreText.setAnchorCenter(0, 0);
		gameHUD.attachChild(scoreText);

		lifes = new Sprite[ninja.getLifes()];

		for (int i = 0; i < lifes.length; i++) {
			lifes[i] = new Sprite(GameActivity.CAM_WIDTH,
					GameActivity.CAM_HEIGHT,
					resourcesManager.ninja_life_region, vbom);
			lifes[i].setPosition(lifes[i].getX() - (lifes.length - i)
					* lifes[i].getWidth(),
					lifes[i].getY() - lifes[i].getHeight());
			lifes[i].setTag(4);
			gameHUD.attachChild(lifes[i]);
		}
	}

	private void createTouchAreas() {
		a1 = new Area(GameActivity.CAM_WIDTH / 4, GameActivity.CAM_HEIGHT
				- GameActivity.CAM_HEIGHT / 4, GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2, vbom, 0);

		a2 = new Area(3 * GameActivity.CAM_WIDTH / 4, GameActivity.CAM_HEIGHT
				- GameActivity.CAM_HEIGHT / 4, GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2, vbom, 1);

		a3 = new Area(GameActivity.CAM_WIDTH / 5, GameActivity.CAM_HEIGHT / 4,
				2 * GameActivity.CAM_WIDTH / 5, GameActivity.CAM_HEIGHT / 2,
				vbom, 2);

		a4 = new Area(GameActivity.CAM_WIDTH / 2, GameActivity.CAM_HEIGHT / 5,
				GameActivity.CAM_WIDTH / 5, 2 * GameActivity.CAM_HEIGHT / 5,
				vbom, 3);

		a5 = new Area(4 * GameActivity.CAM_WIDTH / 5,
				GameActivity.CAM_HEIGHT / 4 + 30,
				2 * GameActivity.CAM_WIDTH / 5,
				GameActivity.CAM_HEIGHT / 2 - 60, vbom, 4);

		a1.attachChild(new Text(GameActivity.CAM_WIDTH / 4,
				GameActivity.CAM_HEIGHT / 4, resourcesManager.fontHUD, "1",
				new TextOptions(HorizontalAlign.LEFT), vbom));

		a2.attachChild(new Text(GameActivity.CAM_WIDTH / 4,
				GameActivity.CAM_HEIGHT / 4, resourcesManager.fontHUD, "2",
				new TextOptions(HorizontalAlign.LEFT), vbom));

		a3.attachChild(new Text(GameActivity.CAM_WIDTH / 5,
				GameActivity.CAM_HEIGHT / 4, resourcesManager.fontHUD, "3",
				new TextOptions(HorizontalAlign.LEFT), vbom));

		a4.attachChild(new Text(GameActivity.CAM_WIDTH / 10,
				GameActivity.CAM_HEIGHT / 4, resourcesManager.fontHUD, "4",
				new TextOptions(HorizontalAlign.LEFT), vbom));

		a5.attachChild(new Text(GameActivity.CAM_WIDTH / 5,
				GameActivity.CAM_HEIGHT / 4, resourcesManager.fontHUD, "5",
				new TextOptions(HorizontalAlign.LEFT), vbom));

		a1.setAlpha(0);
		a2.setAlpha(0);
		a3.setAlpha(0);
		a4.setAlpha(0);
		a5.setAlpha(0);

		gameHUD.attachChild(a1);
		gameHUD.attachChild(a2);
		gameHUD.attachChild(a3);
		gameHUD.attachChild(a4);
		gameHUD.attachChild(a5);

		registerTouchAreas();
	}

	private void registerTouchAreas() {
		gameHUD.registerTouchArea(a1);
		gameHUD.registerTouchArea(a2);
		gameHUD.registerTouchArea(a3);
		gameHUD.registerTouchArea(a4);
		gameHUD.registerTouchArea(a5);
	}

	private void registerWeaponAreas() {
		ninja.getWeapon().registerAreaObserver(0, a1);
		ninja.getWeapon().registerAreaObserver(1, a2);
		ninja.getWeapon().registerAreaObserver(2, a3);
		ninja.getWeapon().registerAreaObserver(3, a4);
		ninja.getWeapon().registerAreaObserver(4, a5);
	}

	@SuppressWarnings("deprecation")
	private void createPowerUpsAcumulator() {
		speedUpAcumulator = new Accumulator(0, 0,
				resourcesManager.speedUp_region, vbom, this);

		auraAcumulator = new Accumulator(0, 0, resourcesManager.aura_region,
				vbom, this);

		backupAcumulator = new Accumulator(0, 0,
				resourcesManager.backup_region, vbom, this);

		extraPointsAcumulator = new Accumulator(0, 0,
				resourcesManager.extraPoints_region, vbom, this);

		speedUpAcumulator.setScale(0.8f);
		auraAcumulator.setScale(0.1f);
		backupAcumulator.setScale(0.2f);
		extraPointsAcumulator.setScale(0.2f);

		ArrayList<Sprite> powerUps = new ArrayList<Sprite>(4);
		powerUps.add(speedUpAcumulator);
		powerUps.add(auraAcumulator);
		powerUps.add(backupAcumulator);
		powerUps.add(extraPointsAcumulator);

		int heightFix = 15;

		Sprite s;
		for (int i = 0; i < powerUps.size(); i++) {
			int widthFix = heightFix * (i + 1);
			s = powerUps.get(i);
			for (int j = 0; j < i; j++) {
				widthFix += powerUps.get(i).getWidthScaled();
			}
			s.setPosition(GameActivity.CAM_WIDTH - s.getWidthScaled() / 2
					- widthFix, s.getHeightScaled() / 2 + heightFix);
			gameHUD.registerTouchArea(s);
			gameHUD.attachChild(s);
		}
	}

	private PowerUp createSpeedUp() {
		SpeedUp s = new SpeedUp(0, 0, resourcesManager.speedUp_region, vbom,
				speedUpAcumulator, 1.5f, 5);
		return s;
	}

	private PowerUp createAura() {
		Aura a = new Aura(0, 0, resourcesManager.aura_region, vbom,
				auraAcumulator, 5);
		return a;
	}

	private PowerUp createBackup() {
		Backup b = new Backup(0, 0, resourcesManager.backup_region, vbom,
				backupAcumulator, 3);
		return b;
	}

	private PowerUp createExtraPoints() {
		ExtraPoints ep = new ExtraPoints(0, 0,
				resourcesManager.extraPoints_region, vbom,
				extraPointsAcumulator, 1, 5);
		return ep;
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

		RotationModifier rm = new RotationModifier(0.75f, 0, 360) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.onModifierFinished(pItem);
				this.reset();
			}
		};
		shuriken.registerEntityModifier(rm);

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

	private void setObjectMovement(final Sprite obj, final int area, int speed,
			final boolean hits) {
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

		if (ninja.getSpeedUp() != null) {
			speed *= ninja.getSpeedUp().getSlowerRatio();
		}

		MoveModifier modifier = new MoveModifier(speed, fromx, fromy,
				GameActivity.CAM_WIDTH / 2 + 50 * directionX,
				GameActivity.CAM_HEIGHT / 2 + 25 * directionY) {

			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.onModifierFinished(pItem);
				if (!hits) {
					return;
				}

				double m = mult;
				if (ninja.getExtraPoints() != null) {
					m += ninja.getExtraPoints().getMultIncrease();
				}

				boolean blocked = false;

				if (!ninja.getWeapon().isProtecting(area)) {
					Debug.i("HIT!!");
					if (ninja.getBackup() != null) {
						Debug.i("BACKUP!!");
						blocked = true;
						addPoints(pointsPerBlock / 2, m);
						switch (area) {
						case 0:
							backup.setX(GameActivity.CAM_WIDTH / 2 - 80);
							backup.setY(GameActivity.CAM_HEIGHT / 2 + 80);
							break;
						case 1:
							backup.setX(GameActivity.CAM_WIDTH / 2 + 50);
							backup.setY(GameActivity.CAM_HEIGHT / 2 + 80);
							break;
						case 2:
							backup.setX(GameActivity.CAM_WIDTH / 2 - 80);
							backup.setY(GameActivity.CAM_HEIGHT / 2 - 50);
							break;
						case 3:
							backup.setX(GameActivity.CAM_WIDTH / 2);
							backup.setY(GameActivity.CAM_HEIGHT / 2 - 100);
							break;
						case 4:
							backup.setX(GameActivity.CAM_WIDTH / 2 + 50);
							backup.setY(GameActivity.CAM_HEIGHT / 2 - 50);
							break;
						}
						AlphaModifier am = new AlphaModifier(0.5f, 0.5f, 0);
						backup.registerEntityModifier(am);
					} else if (ninja.getAura() == null) {
						obj.setTag(-1);
						ninja.setLifes(ninja.getLifes() - 1);

						for (int i = 0; i < lifes.length; i++) {
							if (lifes[i].hasParent()) {
								lifes[i].setTag(-1);
								flash();
								mult = minMult;
								Debug.i("Life " + (i + 1));
								break;
							}
						}

						System.out.println("Left " + ninja.getLifes());
						if (ninja.getLifes() == 0) {
							Debug.i("DEAD");
							finished = true;
							gameHUD.setChildScene(gameOverScene(), false, true,
									true);
							flash.setAlpha(0);
						}
					} else {
						Debug.i("PREVENTED!!");
						blocked = true;
						AlphaModifier am = new AlphaModifier(0.5f, 0.5f, 0);
						aura_protection.registerEntityModifier(am);
					}
					for (int i = 0; i < hitPowerUps.size(); i++) {
						if (hitPowerUps.get(i).newHit()) {
							Debug.i("Hit power up " + hitPowerUps.get(i)
									+ " finished");
							ninja.removePowerUp((PowerUp) hitPowerUps.get(i));
							hitPowerUps.remove(i);
							i--;
						}
					}
				} else {
					blocked = true;
					addPoints(pointsPerBlock, m);
				}
				if (blocked) {
					ScaleModifier sm = new ScaleModifier(0.2f, 1, 0) {

						protected void onModifierFinished(IEntity pItem) {
							obj.setTag(-1);
						};
					};
					obj.registerEntityModifier(sm);
				}
				scoreText.setText(String.format(scoreString, (int) score, m));
			}
		};

		obj.registerEntityModifier(modifier);
	}
	
	private void addPoints(double pointsPerBlock, double m) {
		score += pointsPerBlock * m;
		if (mult < maxMult) {
			mult += multInc;
		}
	}

	private void throwObject(int area) {
		int objType = (int) (Math.random() * 3);
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

		setObjectMovement(obj, area, speed, true);

		gameHUD.attachChild(obj);
	}

	private void createPowerUp() {
		int objType = (int) (Math.random() * 4);

		PowerUp pu;
		Accumulator acc = null;

		switch (objType) {
		case 0:
			acc = speedUpAcumulator;
			pu = createSpeedUp();
			break;
		case 1:
			acc = auraAcumulator;
			pu = createAura();
			break;
		case 2:
			acc = backupAcumulator;
			pu = createBackup();
			break;
		case 3:
			acc = extraPointsAcumulator;
			pu = createExtraPoints();
			break;
		default:
			return;
		}
		if (!acc.isEnabled()) {
			throwPowerUp(pu);
		}
	}

	private void throwPowerUp(PowerUp obj) {
		gameHUD.registerTouchArea(obj);

		int powerUpPosition = (int) (Math.random() * 10);
		switch (powerUpPosition) {
		case 0:
			obj.setX(GameActivity.CAM_WIDTH / 4 - 100);
			obj.setY(GameActivity.CAM_HEIGHT - GameActivity.CAM_HEIGHT / 4);
			break;
		case 1:
			obj.setX(3 * GameActivity.CAM_WIDTH / 4 - 100);
			obj.setY(GameActivity.CAM_HEIGHT - GameActivity.CAM_HEIGHT / 4);
			break;
		case 2:
			obj.setX(GameActivity.CAM_WIDTH / 5 - 100);
			obj.setY(GameActivity.CAM_HEIGHT / 4);
			break;
		case 3:
			obj.setX(GameActivity.CAM_WIDTH / 2 - 50);
			obj.setY(GameActivity.CAM_HEIGHT / 5);
			break;
		case 4:
			obj.setX(4 * GameActivity.CAM_WIDTH / 5 - 50);
			obj.setY(GameActivity.CAM_HEIGHT / 4);
			break;
		case 5:
			obj.setX(GameActivity.CAM_WIDTH / 4 + 100);
			obj.setY(GameActivity.CAM_HEIGHT - GameActivity.CAM_HEIGHT / 4);
			break;
		case 6:
			obj.setX(3 * GameActivity.CAM_WIDTH / 4 + 100);
			obj.setY(GameActivity.CAM_HEIGHT - GameActivity.CAM_HEIGHT / 4);
			break;
		case 7:
			obj.setX(GameActivity.CAM_WIDTH / 5 + 50);
			obj.setY(GameActivity.CAM_HEIGHT / 4);
			break;
		case 8:
			obj.setX(GameActivity.CAM_WIDTH / 2 + 50);
			obj.setY(GameActivity.CAM_HEIGHT / 5);
			break;
		case 9:
			obj.setX(4 * GameActivity.CAM_WIDTH / 5 + 100);
			obj.setY(GameActivity.CAM_HEIGHT / 4);
			break;
		default:
			return;
		}

		obj.setTag(0);

		gameHUD.attachChild(obj);

	}

	private void createFlash() {
		flash = new Rectangle(GameActivity.CAM_WIDTH / 2,
				GameActivity.CAM_HEIGHT / 2, GameActivity.CAM_WIDTH,
				GameActivity.CAM_HEIGHT, vbom);
		flash.setColor(new Color(Color.RED.getRed(), Color.RED.getGreen(),
				Color.RED.getBlue(), 0.4f));
		flash.setAlpha(0);

		gameHUD.attachChild(flash);
	}

	private void flash() {
		flash.registerEntityModifier(flashModifier);
		flashModifier.reset();
	}

	// Game loop
	private void loop(float pSecondsElapsed) {
		// For each possibly generated object, remove an old one
		gameHUD.detachChild(-1);

		for (int i = 0; i < timedPowerUps.size(); i++) {
			if (timedPowerUps.get(i).timePassed(pSecondsElapsed)) {
				Debug.i("Timed power up " + timedPowerUps.get(i) + " finished");
				ninja.removePowerUp((PowerUp) timedPowerUps.get(i));
				timedPowerUps.remove(i);
				i--;
			}
		}

		int area = (int) (Math.random() * 5);
		if (Math.random() < 0.01) {
			throwObject(area);
		}
		powerUpTime += pSecondsElapsed;
		if (powerUpTime >= powerUpDiff) {
			powerUpTime = 0;
			createPowerUp();
		}
	}

	@Override
	public void onPowerUpActivated(PowerUp pu) {
		if (pu instanceof HitPowerUp) {
			hitPowerUps.add((HitPowerUp) pu);
		} else if (pu instanceof TimedPowerUp) {
			timedPowerUps.add((TimedPowerUp) pu);
		}
		ninja.addPowerUp(pu);
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
			return false;
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
