package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import utilz.Constants;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Direction.*;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    Scanner input = new Scanner(System.in);
    private float charX = 100, charY = 100;
    private String source = null;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 15;
    private int playerAction ;
    private int playerDir = -1;
    private boolean moving = false;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        source = chooseCharacter(); 
        importImage();
        loadAnimations();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    public String chooseCharacter() {
        System.out.println("Welcome to our game!\nChoose a character: \n1.Uzumaki Naruto\n2.Hatake Kakashi\n3.Uchiha Sasuke\n4.Sakura Haruno\n5.Uchiha Itachi");
        int num = input.nextInt();
        switch (num) {
            case 1:
                source = "/UzumakiNaruto.png";
                charX = 100;
                charY = 100;
                break;
            case 2:
                source = "/HatakeKakashi.png";
                charX = 100;
                charY = 100;
                break;
            case 3:
                source = "/UchihaSasuke.png";
                charX = 100;
                charY = 100;
                break;
            case 4:
                source = "/SakuraHaruno.png";
                charX = 100;
                charY = 100;
                break;
            case 5:
            	source = "/itachi.png";
            	charX = 100;
            	charY = 100;
            	break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        return source;
    }

    private void loadAnimations() {
        Map<Integer, Integer[]> characterAnimations = Constants.PlayerConstants.characterAnimations.get(getPlayerNum());
        if (characterAnimations != null) {
            Integer[] dimensions = characterAnimations.get(playerAction);
            if (dimensions != null) {
                animations = new BufferedImage[dimensions[0]][dimensions[1]];
                for (int j = 0; j < dimensions[0]; j++) {
                    for (int i = 0; i < dimensions[1]; i++) {
                        animations[j][i] = img.getSubimage(i * (int) charX, j * (int) charY, (int) charX, (int) charY);
                    }
                }
            }
        }
    }

    private void importImage() {
        InputStream is = getClass().getResourceAsStream(source);
        try {
            if (is != null) {
                img = ImageIO.read(is);
            } else {
                System.out.println("Could not load image: " + source);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1000, 600);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    private void setAnimation() {
        if (moving) {
            if (playerDir == LEFT || playerDir == RIGHT || playerDir == UP || playerDir == DOWN) {
                playerAction = RUNNING;
            }
        } else {
            playerAction = IDLE;
        }
    }

    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void updatePos() {
        if (moving) {
            switch (playerDir) {
                case LEFT:
                    charX -= 5;
                    break;
                case UP:
                    charY -= 5;
                    break;
                case RIGHT:
                    charX += 5;
                    break;
                case DOWN:
                    charY += 5;
                    break;
            }
        }
    }


    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction, getPlayerNum(), 1)) {
                aniIndex = 0;
            }
        }
    }

    private int getPlayerNum() {
        switch (source) {
            case "/UzumakiNaruto.png":
                return 1;
            case "/HatakeKakashi.png":
                return 2;
            case "/UchihaSasuke.png":
                return 3;
            case "/SakuraHaruno.png":
                return 4;
            case "/itachi.png":
            	return 5;
            default:
                return -1;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        updatePos();
        setAnimation();
        updateAnimationTick();
        g.drawImage(animations[playerAction][aniIndex], (int) charX, (int) charY, 100, 100, null);
    }
}