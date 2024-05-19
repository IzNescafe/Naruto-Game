package utilz;

import java.awt.geom.Rectangle2D;

import main.Game;

public class HelpMethods {

	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
						return true;
		return false;
	}

	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return true;

		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
	}
	
	public static boolean IsTileSolid(int XTile, int YTile,int[][] lvlData) {
		int value = lvlData[YTile][XTile];

		if (value >= 48 || value < 0 || value != 11)
			return true;
		return false;
	}
	
	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		
		int currentTile = (int) hitbox.x / Game.TILES_SIZE;
//		System.out.println(currentTile); //for debugging
		if(xSpeed > 0) {
			//Right
			int tileXPos = currentTile * Game.TILES_SIZE; // 64 * 5 = 320
			int xOffset = (int) (Game.TILES_SIZE - hitbox.width); // 64 - 60 = 4
			return tileXPos + xOffset - 1; // 320 + 4 - 1 = 323
		}else {
			//Left
			return currentTile * Game.TILES_SIZE;
		}
	}
	
	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		
		int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
//		System.out.println(currentTile); // 7
		if(airSpeed > 0) {
			//falling -> touching floor
			int tileYPos = currentTile * Game.TILES_SIZE; // 7 * 64 = 448
			int yOffset = (int) ( Game.TILES_SIZE - hitbox.height); //130 - 64 = 66
			return tileYPos + yOffset - 1;//448 + 66 - 1 = 513
			}else {
			//jumping 
			return currentTile * Game.TILES_SIZE;
		}
	}
	
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		// Check the pixel below bottomleft and bottomright
		if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData)) //hitbox.height = 24
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + 1, lvlData))   //hitbox.width = 14, hitbox.height = 24
				return false;

		return true;

	}
	
	public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
		return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
	}
	
	public static boolean IsAllTileWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		for(int i = 0; i < xEnd - xStart; i++) {
			if(IsTileSolid(xStart + i, y, lvlData))
				return false;
			if(!IsTileSolid(xStart + i, y + 1, lvlData))
				return false;
		}
		return true;
	}
	
	public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
		int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
		int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);
		
		if(firstXTile > secondXTile)
			return IsAllTileWalkable(secondXTile, firstXTile, yTile, lvlData);
		else
			return IsAllTileWalkable(firstXTile, secondXTile, yTile, lvlData);
	}
}