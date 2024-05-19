package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import utilz.LoadSave;
import static utilz.constants.EnemyConstants.*;

public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[][] CrabbyArr;
	private ArrayList<Crabby> crabbies = new ArrayList<>(); 

	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
		addEnemies();
	}
	
	private void addEnemies() {
		crabbies = LoadSave.GetCrabs();
		System.out.println("size of crabs: "+ crabbies.size());
	}



	public void update(int[][] lvlData, Player player) {
		for(Crabby c : crabbies) {
			c.update(lvlData, player);
		}
	}
	
	public void draw(Graphics g, int xlvlOffset) {
		drawCrabs(g, xlvlOffset);
	}

	private void drawCrabs(Graphics g, int xlvlOffset) {
		for(Crabby c: crabbies) {
			g.drawImage(CrabbyArr[c.getEnemyState()][c.aniIndex], (int) c.getHitbox().x - xlvlOffset, (int) c.getHitbox().y, CRABBY_WIDTH, CRABBY_HEIGHT, null);
		}
		
	}

	private void loadEnemyImgs() {
		CrabbyArr = new BufferedImage[5][9];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE);
		for (int j = 0; j < CrabbyArr.length; j++)
			for (int i = 0; i < CrabbyArr[j].length; i++)
				CrabbyArr[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
	}
}
