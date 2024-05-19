package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State implements Statemethods {
	
	private MenuButton[] button = new MenuButton[3];
	private BufferedImage backgroundImg, backgroundTheme;
	private int menuX , menuY, menuWidth, menuHeight;
	

	public Menu(Game game) {
		super(game);
		loadButtons();
		loadBackground();
		backgroundTheme = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMAGE);
	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
		menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
		menuX = Game.GAME_WIDTH/2 - menuWidth / 2;
		menuY = (int) ( 45 * Game.SCALE);
	}

	private void loadButtons() {
		button[0] = new MenuButton(Game.GAME_WIDTH/2, (int) (150 * Game.SCALE) , 0, Gamestate.PLAYING );
		button[1] = new MenuButton(Game.GAME_WIDTH/2, (int) (220 * Game.SCALE) , 1, Gamestate.OPTIONS );
		button[2] = new MenuButton(Game.GAME_WIDTH/2, (int) (290 * Game.SCALE) , 2, Gamestate.QUIT );
		
	}

	@Override
	public void update() {
		for(MenuButton mb : button) {
			mb.update();
		}
		
	}

	@Override
	public void draw(Graphics g) {
		
//		g.setColor(new Color(0, 0, 0, 150));
//		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		g.drawImage(backgroundTheme, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT,null);	
		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
		for(MenuButton mb : button) {
			mb.draw(g);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(MenuButton mb : button) {
			if(isIn(e, mb)) {
				mb.setMousePressed(true);
				break;
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(MenuButton mb : button) {
			if(isIn(e, mb)) {
				if(mb.isMousePressed()) {
					mb.applyGamestate();
				}
				break;
			}
		}
		resetButtons();	
	}

	private void resetButtons() {
		for(MenuButton mb: button)
			mb.resetBools();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb: button)
			mb.setMouseOver(false);
		
		for(MenuButton mb: button)
			if(isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			Gamestate.state = Gamestate.PLAYING;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
