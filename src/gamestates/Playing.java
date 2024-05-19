package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.PausedOverlay;
import utilz.LoadSave;
import static utilz.constants.Environment.*;

public class Playing extends State implements Statemethods {
	
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private PausedOverlay pauseOverlay;
	private boolean paused = false;
	
	private int xlvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
	private int maxTilesOffset = lvlTilesWide * Game.TILES_IN_WIDTH;
	private int maxlvlOffsetX = maxTilesOffset - Game.TILES_SIZE;
	
	private BufferedImage backgroundImg, konoha, cloud;
	private int[] cloudPos;
	private Random rand = new Random();
	
	public Playing(Game game) {
		super(game);
		initClasses();
		
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMAGE);
		konoha = LoadSave.GetSpriteAtlas(LoadSave.KONOHA_HOUSES);
		cloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
		
		cloudPos = new int[8];
		for(int i = 0; i < cloudPos.length; i++) {
			cloudPos[i] = rand.nextInt((int) (100 * Game.SCALE));
		}
//		cloudPos1 = new int[8];
//		for(int i = 0; i < cloudPos1.length; i++) {
//			cloudPos1[i] = rand.nextInt((int) (130 * Game.SCALE));
//		}
	}
	
	private void initClasses() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		player = new Player(200, 200, (int) (100 *Game.SCALE), (int) (100 * Game.SCALE));
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		pauseOverlay = new PausedOverlay(this);

	}

	@Override
	public void update() {
		
		if(!paused) {
			levelManager.update();
			player.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
			CheckCloseToBorder();
		}else {
			pauseOverlay.update();
		}
	}

	private void CheckCloseToBorder() {
		int playerX = (int) player.getHitbox().x;
		int diff = playerX - xlvlOffset;

		if (diff > rightBorder)
			xlvlOffset += diff - rightBorder;
		else if (diff < leftBorder)
			xlvlOffset += diff - leftBorder;

		if (xlvlOffset > maxlvlOffsetX)
			xlvlOffset = maxlvlOffsetX;
		else if (xlvlOffset < 0)
			xlvlOffset = 0;

	}


	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0,Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		
		drawBackground(g);
		
		levelManager.draw(g, xlvlOffset);
		player.render(g, xlvlOffset);
		enemyManager.draw(g, xlvlOffset);

		if (paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		}
	}
	
	private void drawBackground(Graphics g) {
		
		for(int i = 0; i < 3; i++) {
			g.drawImage(konoha, 0 + i * KONOHA_WIDTH - (int) (xlvlOffset * 0.3), (int) (110 * Game.SCALE), KONOHA_WIDTH, KONOHA_HEIGHT, null);
		}
		
//		for(int i = 0; i < 3; i++) {
//			g.drawImage(bigcloud,CLOUD_WIDTH  * 3 * i, cloudPos[i], BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
//		}
		
		for(int i = 0; i < cloudPos.length; i++) {
			g.drawImage(cloud, CLOUD_WIDTH * 3 * i - (int) (xlvlOffset * 0.7), cloudPos[i], CLOUD_WIDTH * 2 , CLOUD_HEIGHT * 2,  null);
		}
//		for(int i = 0; i < cloudPos.length; i++) {
//			g.drawImage(cloud, CLOUD_WIDTH * 2 * i, cloudPos1[i], CLOUD_WIDTH * 3 , CLOUD_HEIGHT * 3,  null);
//		}
		
	}

	public void mouseDragged(MouseEvent e) {
		if(paused) {
			pauseOverlay.mouseDragged(e);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			player.setAttacking(true);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(paused) {
			pauseOverlay.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(paused) {
			pauseOverlay.mouseReleased(e);
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(paused) {
			pauseOverlay.mouseMoved(e);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			player.setLeft(true);
			break;
		case KeyEvent.VK_D:
			player.setRight(true);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(true);
			break;
		case KeyEvent.VK_ESCAPE:
			paused = !paused;
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {

		case KeyEvent.VK_A:
			player.setLeft(false);

		case KeyEvent.VK_D:
			player.setRight(false);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(false);
			break;
		}
		
	}
	
	public void unpauseGame() {
		paused = false;
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}

}
