package utilz;

import main.Game;

public class constants {
	
	public static class EnemyConstants {
		public static final int CRABBY = 0;
		
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;
		
		public static final int CRABBY_WIDTH_DEFAULT = 72;	
		public static final int CRABBY_HEIGHT_DEFAULT = 32;
		
		public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * Game.SCALE);
		public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * Game.SCALE);
		
		public static final int CRABBY_DRAWOFFSET_X = (int) (26 * Game.SCALE);
		public static final int CRABBY_DRAWOFFSET_Y = (int) (9 * Game.SCALE);
		
		public static int GetSpriteAmount(int enemyType, int enemyState) {
			switch(enemyType) {
			case CRABBY:
				switch(enemyState) {
				case IDLE:
					return 9;
				case RUNNING:
					return 6;
				case ATTACK:
					return 7;
				case HIT:
					return 4;
				case DEAD:
					return 5;
				}
			}
			return 0;
		}
	}
	
	public static class Environment {
		public static final int KONOHA_WIDTH_DEFAULT = 974;
		public static final int KONOHA_HEIGHT_DEFAULT = 214;
		public static final int CLOUD_WIDTH_DEFAULT = 74;
		public static final int CLOUD_HEIGHT_DEFAULT = 24;
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 235;
		public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
		
		public static final int KONOHA_WIDTH = (int) (KONOHA_WIDTH_DEFAULT * Game.SCALE);
		public static final int KONOHA_HEIGHT = (int) (KONOHA_HEIGHT_DEFAULT * Game.SCALE);
		public static final int CLOUD_WIDTH = (int) (CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int CLOUD_HEIGHT = (int) (CLOUD_HEIGHT_DEFAULT * Game.SCALE);
		public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
	}
	
	public static class UI{
		public static class Buttons{
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
		}
		
		public static class PauseButtons{
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
		}
		
		public static class UrmButtons{
			public static final int URM_DEFAULT_SIZE = 56;
			public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE); 
		}
		
		public static class VolumeButtons{
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;
			
			public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH =(int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
		}
	}

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 4;
		public static final int FALLING = 7;
		//public static final int GROUND = 4;
		public static final int HIT = 2;
		public static final int ATTACK_1 = 3;
		public static final int ATTACK_JUMP_1 = 5;
		public static final int ATTACK_JUMP_2 = 6;

		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			case RUNNING:
				return 6;
			case IDLE:
				return 5;
			case HIT:
				return 4;
			case JUMP:
				return 4;
			case ATTACK_1:
			case ATTACK_JUMP_1:
			case ATTACK_JUMP_2:
				return 3;
//			case GROUND:
	//			return 2;
			case FALLING:
			default:
				return 1;
			}
		}
	}

}