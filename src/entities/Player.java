package entities;

import static utilz.constants.PlayerConstants.*;
import static utilz.HelpMethods.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Scanner;

import main.Game;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 25;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 1.0f * Game.SCALE;
	private int[][] lvlData;
	private float xDrawOffset = 36 * Game.SCALE;
	private float yDrawOffset = 59 * Game.SCALE;
	private static Scanner input = new Scanner(System.in);
	private static int num;
	private static String source = null;
	private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.3f * Game.SCALE;
	private boolean inAir = false;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitBoxes(x, y,(int)( 30 * Game.SCALE),(int) (22 * Game.SCALE));

	}
	
	public static String ChooseCharacter() {
		System.out.println("Choose the player: \n1)Uzumaki Naruto\n2)Uchiha Sasuke\n3)Sakura Haruno\n4)Hatake Kakashi");
		num = input.nextInt();
		switch(num) {
		case 1:
			source = "sprite1.png";
			break;
		case 2:
			source = "sprite2.png";
			break;
		case 3:
			source = "sprite3.png";
			break;
		case 4:
			source = "sprite4.png";
		}
		return source;
	}
	
	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
	}

	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x  - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), width, height , null);
		//drawHitBoxes(g, lvlOffset);
	}

	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}

		}

	}

	private void setAnimation() {
		int startAni = playerAction;

		if (moving)
			playerAction = RUNNING;
		else
			playerAction = IDLE;

		if (attacking)
			playerAction = HIT;
		
		if(jump)
			playerAction = JUMP;

		if (startAni != playerAction)
			resetAniTick();
	}

	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;

		if (jump)
			jump();
//		if (!left && !right && !inAir)
//			return;
		
		if(!inAir) {
			if((!left && !right) || (right && left)) {
				return ;
			}
		}

		float xSpeed = 0;

		if (left)
			xSpeed -= playerSpeed;
		if (right)
			xSpeed += playerSpeed;

		if (!inAir)
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;

		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData) && CanMoveHere(bottomHitbox.x, bottomHitbox.y + airSpeed, bottomHitbox.width, bottomHitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				topHitBox.y += airSpeed;
				bottomHitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}

		} else
			updateXPos(xSpeed);
		
		hitbox.x = bottomHitbox.x;
        hitbox.y = bottomHitbox.y + bottomHitbox.height;
        bottomHitbox.x = topHitBox.x;
        bottomHitbox.y = topHitBox.y + topHitBox.height;
		moving = true;
	} 

	private void jump() {
		if(inAir) {
			return;
		}
		inAir = true;
		airSpeed = jumpSpeed;
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
		
	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData) && CanMoveHere(topHitBox.x + xSpeed, topHitBox.y, topHitBox.width, topHitBox.height, lvlData) && CanMoveHere(bottomHitbox.x + xSpeed, bottomHitbox.y, bottomHitbox.width, bottomHitbox.height, lvlData)) {
			hitbox.x += xSpeed;
			topHitBox.x += xSpeed;
			bottomHitbox.x += xSpeed;
			moving = true;
		}else {
			topHitBox.x = GetEntityXPosNextToWall(topHitBox, xSpeed);
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
			bottomHitbox.x = GetEntityXPosNextToWall(bottomHitbox, xSpeed);
		}
		
	}

	private void loadAnimations() {

		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

		animations = new BufferedImage[8][6];
		for (int j = 0; j < animations.length; j++)
			for (int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i * 100, j * 100, 100, 100);

	}

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void setJump(boolean jump) {
		this.jump = jump;
		
	}

}