package utilz;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {

        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int HIT = 2;
        public static final int ATTACK_1 = 3;
        public static final int JUMP = 4;
        public static final int ATTACK_JUMP_1 = 5;
        public static final int ATTACK_JUMP_2 = 6;
        public static final int FALLING = 7;
        public static final int GROUND = 4;

        public static final Map<Integer, Map<Integer, Integer[]>> characterAnimations = new HashMap<>();

        static {
            
            // Naruto
            Map<Integer, Integer[]> narutoAnimations = new HashMap<>();
            narutoAnimations.put(IDLE, new Integer[]{8, 6});
            narutoAnimations.put(RUNNING, new Integer[]{8, 6});
            narutoAnimations.put(HIT, new Integer[]{8, 6}); 
            narutoAnimations.put(ATTACK_1, new Integer[]{8, 5});
            narutoAnimations.put(JUMP, new Integer[]{8, 5});
            

            // Kakashi
            Map<Integer, Integer[]> kakashiAnimations = new HashMap<>();
            kakashiAnimations.put(IDLE, new Integer[]{8, 4});
            kakashiAnimations.put(RUNNING, new Integer[]{4, 6});
            kakashiAnimations.put(HIT, new Integer[]{8, 6}); 
            kakashiAnimations.put(ATTACK_1, new Integer[]{8, 6});
            kakashiAnimations.put(JUMP, new Integer[]{4, 2});
           

            // Sasuke
            Map<Integer, Integer[]> sasukeAnimations = new HashMap<>();
            sasukeAnimations.put(IDLE, new Integer[]{8, 7});
            sasukeAnimations.put(RUNNING, new Integer[]{8, 7});
            sasukeAnimations.put(HIT, new Integer[]{8, 7}); 
            sasukeAnimations.put(ATTACK_1, new Integer[]{8, 8}); 
            // Add more actions for Sasuke if needed

            // Sakura
            Map<Integer, Integer[]> sakuraAnimations = new HashMap<>();
            sakuraAnimations.put(IDLE, new Integer[]{8, 6});
            sakuraAnimations.put(RUNNING, new Integer[]{8, 6});
            sakuraAnimations.put(HIT, new Integer[]{8, 6}); 
            sakuraAnimations.put(ATTACK_1, new Integer[]{8, 8}); 
            
            //Itachi
            Map<Integer, Integer[]> itachiAnimations = new HashMap<>();
            itachiAnimations.put(IDLE, new Integer[] {8, 4});
            itachiAnimations.put(RUNNING, new Integer[] {8, 6});
            itachiAnimations.put(HIT, new Integer[] {8, 4});
            itachiAnimations.put(ATTACK_1, new Integer[] {8, 6});

            characterAnimations.put(1, narutoAnimations);
            characterAnimations.put(2, kakashiAnimations);
            characterAnimations.put(3, sasukeAnimations);
            characterAnimations.put(4, sakuraAnimations);
            characterAnimations.put(5,  itachiAnimations);
            
        }

        public static int GetSpriteAmount(int playerAction, int character, int dimension) {
            Map<Integer, Integer[]> animations = characterAnimations.get(character);
            if (animations != null) {
                Integer[] dimensions = animations.get(playerAction);
                if (dimensions != null) {
                    return dimensions[dimension];
                }
            }
            return 0;
        }
    }
}
