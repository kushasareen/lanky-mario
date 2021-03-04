
// Name:        Kusha Sareen
// Date:        June 20, 2018
// Program:     ISP
// Description: A side-scrolling Mario Game
// 
// Sprites: http://www.mariouniverse.com/sprites/nes/smb

//importing packages in order to console, output media and graphics effects

import hsa_ufa.Console;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class ISP {

	// initializing console with title and size
	static Console c = new Console(1024, 768, "Super Mario Bros. 1985");

	// initializing all variables

	// collision information
	static Boolean collisionground = false;
	static Boolean collisiontop = false;
	static Boolean collisionbottom = false;
	static Boolean collisionleft = false;
	static Boolean collisionright = false;

	// size and initial position of mario
	static int x = 0;
	static int y = 0;
	static int l = 100;
	static int w = 50;

	// gravity, jump and speed of movement of mario
	static int grav = 0;
	static int jump = 0;
	static int speed = 3;
	static int score = 0;
	// the initial x value of the image, allowsw for side scrolling
	static int xo = 0;

	// hold info about the goombas
	static int[][] goomba = new int[8][16];
	// index:
	// 1-X
	// 2-Y
	// 3-Image
	// 4-Exists
	// 5-Max
	// 6-Min
	// 7-Dir

	static int[] scoreHS = new int[500];
	static String[] nameHS = new String[500];

	static String[][] highScores = new String[2][500];

	// tells when the game/intro is running
	static boolean run = true;
	static boolean intro = true;
	static boolean win;
	static boolean controls = true;
	static boolean highscores = true;
	static boolean endscreen = true;
	static int mousex;
	static int mousey;
	static String name;

	// increases with the timer, allows for slower gravity and jump speeds
	static double counter = 0;

	// importing all images
	static Image pngground = Toolkit.getDefaultToolkit()
			.getImage(c.getClass().getClassLoader().getResource("mymedia/tiles.png"));
	static Image pngGoomba1 = Toolkit.getDefaultToolkit()
			.getImage(c.getClass().getClassLoader().getResource("mymedia/goomba1.png"));
	static Image pngGoomba2 = Toolkit.getDefaultToolkit()
			.getImage(c.getClass().getClassLoader().getResource("mymedia/goomba2.png"));
	static Image pngMarStand = Toolkit.getDefaultToolkit()
			.getImage(c.getClass().getClassLoader().getResource("mymedia/standingpockets.png"));
	static Image pngMarJump = Toolkit.getDefaultToolkit()
			.getImage(c.getClass().getClassLoader().getResource("mymedia/jump1.png"));
	static Image pngMarDuck = Toolkit.getDefaultToolkit()
			.getImage(c.getClass().getClassLoader().getResource("mymedia/ducking.png"));
	static Image pngMarWalk4 = Toolkit.getDefaultToolkit()
			.getImage(c.getClass().getClassLoader().getResource("mymedia/walking4.png"));
	static Image pngMarWalk5 = Toolkit.getDefaultToolkit()
			.getImage(c.getClass().getClassLoader().getResource("mymedia/walking5.png"));
	static Image pngMarWalk6 = Toolkit.getDefaultToolkit()
			.getImage(c.getClass().getClassLoader().getResource("mymedia/walking6.png"));
	static Image pngLevel = Toolkit.getDefaultToolkit()
			.getImage(c.getClass().getClassLoader().getResource("mymedia/1-1.png"));
	static Image pngMenu = Toolkit.getDefaultToolkit()
			.getImage(c.getClass().getClassLoader().getResource("mymedia/menu copy2.png"));
	static Image pngControls = Toolkit.getDefaultToolkit()
			.getImage(c.getClass().getClassLoader().getResource("mymedia/controlmenu.png"));
	static Image pngHighScores = Toolkit.getDefaultToolkit()
			.getImage(c.getClass().getClassLoader().getResource("mymedia/scoremenu3.png"));
	static Image pngIntro = Toolkit.getDefaultToolkit()
			.getImage(c.getClass().getClassLoader().getResource("mymedia/intro.png"));
	
	// the mario sprite that is currently displayed
	static Image Mario;

	static AudioClip music = Applet
			.newAudioClip(ISP.class.getResource("mymedia/Super Mario Bros Music - Ground Theme.wav"));

	public static void main(String[] args) throws InterruptedException, IOException {

		initializeVars();

		music.play();
		mainMenu();

	}

	public static void initializeVars() {

		x = 0;
		y = 0;
		score = 0;
		counter = 0;
		xo = 0;
		grav = 0;
		jump = 0;
		// giving all goombas an initial image, direction, makes it exist
		for (int j = 0; j <= 15; j++) {
			goomba[3][j] = 1;
			goomba[4][j] = 1;
			goomba[7][j] = 1;
		}

		// setting initial x,y and the parameters
		// (where they turn around) for all goombas
		goomba[1][0] = 1200;
		goomba[1][1] = 2300;
		goomba[1][2] = 2700;
		goomba[1][3] = 2790;
		goomba[1][4] = 5100;
		goomba[1][5] = 5190;
		goomba[1][6] = 6100;
		goomba[1][7] = 6190;
		goomba[1][8] = 6500;
		goomba[1][9] = 6590;
		goomba[1][10] = 6700;
		goomba[1][11] = 6790;
		goomba[1][12] = 9300;
		goomba[1][13] = 9390;
		goomba[1][14] = 4300;
		goomba[1][15] = 4400;

		for (int j = 0; j <= 13; j++) {
			goomba[2][j] = 658;

		}
		goomba[2][14] = 222;
		goomba[2][15] = 222;

		goomba[6][0] = 0;
		goomba[5][0] = 1450;
		goomba[6][1] = 2150;
		goomba[5][1] = 2400;
		goomba[6][2] = 2570;
		goomba[5][2] = 2990;
		goomba[6][3] = 2570;
		goomba[5][3] = 2990;

		for (int j = 4; j <= 11; j++) {
			goomba[6][j] = 4770;
			goomba[5][j] = 7130;
		}

		goomba[6][12] = 8830;
		goomba[5][12] = 9550;
		goomba[6][13] = 8830;
		goomba[5][13] = 9550;
		goomba[6][14] = 4280;
		goomba[5][14] = 4650;
		goomba[6][15] = 4280;
		goomba[5][15] = 4650;

	}

	public static void mainMenu() throws InterruptedException, IOException {
		c.enableMouse();
		c.enableMouseMotion();

		do {
			mousex = c.getMouseX();
			mousey = c.getMouseY();
			synchronized (c) {
				c.drawImage(pngMenu, 0, 0, 1024, 764);
			

			if (mousex >= 250 && mousex <= 250 + 450 && mousey >= 110 && mousey <= 110 + 230) {
				c.drawImage(pngIntro, 220, 80, 550, 290);
				if (c.getMouseClick() == 1) {
					intro = false;
				}
			} else
				c.drawImage(pngIntro, 230, 90, 500, 260);
			}
			
			if (mousex >= 270 && mousex <= 270 + 470 && mousey >= 540 && mousey <= 540 + 60 && c.getMouseClick() == 1) {
				controls = true;
				Controls();
			} else if (mousex >= 270 && mousex <= 270 + 470 && mousey >= 470 && mousey <= 470 + 60
					&& c.getMouseClick() == 1) {
				highscores = true;
				highScores();
			} else if (mousex >= 270 && mousex <= 270 + 470 && mousey >= 400 && mousey <= 400 + 60
					&& c.getMouseClick() == 1) {
				run = true;
				initializeVars();
				playGame();
			} else if (mousex >= 820 && mousex <= 820 + 200 && mousey >= 20 && mousey <= 20 + 60
					&& c.getMouseClick() == 1) {
				music.stop();
				System.exit(0);
			}

		} while (true);
	}

	public static void Controls() {
		do {
			mousex = c.getMouseX();
			mousey = c.getMouseY();
			synchronized (c) {
				c.drawImage(pngControls, 0, 0, 1024, 764);
			}

			if (mousex >= 870 && mousex <= 870 + 200 && mousey >= 20 && mousey <= 20 + 60 && c.getMouseClick() == 1) {
				controls = false;
			}

		} while (controls);
	}

	public static void highScores() throws IOException {

		c.drawImage(pngHighScores, 0, 0, 1024, 764);

		File inFile = new File("E:\\highscores.txt");
		Scanner fReader = new Scanner(inFile);
		int count = 0;
		String temp;
		int temp2;

		// assigning all names/scores to highscore array
		while (fReader.hasNextLine()) {

			highScores[0][count] = fReader.nextLine();
			highScores[1][count] = fReader.nextLine();
			count++;

		}
		// insertion sort for scores and moving names along with them
		for (int i = 1; i <= count - 1; i++) {
			for (int j = i; j > 0; j--) {
				if (Integer.parseInt(highScores[1][j]) < Integer.parseInt(highScores[1][j - 1])) {
					temp2 = Integer.parseInt(highScores[1][j]);
					highScores[1][j] = highScores[1][j - 1];
					highScores[1][j - 1] = Integer.toString(temp2);

					temp = highScores[0][j];
					highScores[0][j] = highScores[0][j - 1];
					highScores[0][j - 1] = temp;
				}
			}
		}

		c.setFont(new Font("Haettenschweiler", 10, 40));
		c.setColor(Color.WHITE);

		//printing top 10 highscores
		for (int i = count - 1; i >= 0; i--) {
			if (count - i < 11) {
			c.drawString((count - 1 - i) + 1 + ". ", 50, 175 + (count - 1 - i) * 50);
			c.drawString(highScores[0][i], 450, 175 + (count - 1 - i) * 50);
			c.drawString(highScores[1][i], 850, 175 + (count - 1 - i) * 50);
			}
		}

		do {

			mousex = c.getMouseX();
			mousey = c.getMouseY();
			System.out.println(mousex + " " + mousey);

			if (mousex >= 870 && mousex <= 870 + 200 && mousey >= 20 && mousey <= 20 + 60 && c.getMouseClick() == 1) {
				highscores = false;
			}

		} while (highscores);
	}

	public static void playGame() throws InterruptedException, IOException {
		do {
			drawScreen();
			marioMovt();
			Gravity();
			Goomba();
			totalCollision();
			if (xo < -9880) {
				score += 2000;
				win = true;
				endscreen = true;
				endScreen();
			} else if (y > 1000) {
				win = false;
				endscreen = true;
				endScreen();
			}
		} while (run);
	}

	public static void drawScreen() throws InterruptedException, IOException {
		synchronized (c) {
			c.setBackgroundColor(Color.WHITE);

			c.clear();

			// draws mario, the level, and time elapsed
			// based on xo, images move left as xo decreases
			c.drawImage(pngLevel, xo, 0, 12000, 800);

			c.drawImage(Mario, xo + x, y, w, l);

			c.setColor(Color.BLACK);
			c.println("time= " + counter / 100);
			c.println("score= " + score);

			// for all goombas that exist, an image is drawn based on
			// gImage[]
			// and its respective coordinates
			for (int j = 0; j <= 15; j++) {
				if (goomba[4][j] == 1) {
					if (goomba[3][j] == 1) {
						c.drawImage(pngGoomba1, xo + goomba[1][j], goomba[2][j], 50, 50);
					} else
						c.drawImage(pngGoomba2, xo + goomba[1][j], goomba[2][j], 50, 50);
				}
			}

		}
		// repeats once every millisecond, counter increases with sleep
		Thread.sleep(1);
		counter++;
	}

	public static void marioMovt() {
		// shift increases speed
		if (c.isKeyDown(16)) {
			speed = 4;
		} else
			speed = 3;

		// down key makes mario crouch if he is on the ground
		if (c.isKeyDown(40)) {
			if (collisiontop == true || collisionground == true) {
				if (Mario != pngMarDuck) {
					y += 57;
					l = 60;
					Mario = pngMarDuck;
				}
				// counters any motion when crouched
				if (c.isKeyDown(37)) {
					x += speed;
				}
				if (c.isKeyDown(39)) {
					x -= speed;
				}
			}
		} else {
			// else he is standing
			if (Mario == pngMarDuck) {
				y -= 57;
			}
			l = 116;
			Mario = pngMarStand;
		}

		// when left and right movement keys are pressed, the sprites cycle
		// based on the mod of the mario's x values
		if (collisiontop == true || collisionground == true) {
			if (c.isKeyDown(37) || c.isKeyDown(39)) {
				if ((x % 120) <= 40) {
					Mario = pngMarWalk4;
				} else if ((x % 120) <= 80) {
					Mario = pngMarWalk5;
				} else if ((x % 120) >= 80) {
					Mario = pngMarWalk6;
				}
			}
		} else {
			// jump sprite displayed when he is off the ground
			l = 116;
			Mario = pngMarJump;

		}

		// right key moves mario right at the given speed
		// flips sprite by setting negative width
		// when it turns, the x coordinate is moved back width for
		// a smooth transition
		if (c.isKeyDown(37)) {
			if (w > 0) {
				x += w;
			}

			x -= speed;
			w = -50;

		}

		// same code for the left key
		if (c.isKeyDown(39)) {
			if (w < 0) {
				x += w;
			}
			x += speed;
			w = 50;
		}

		// when mario is on the ground and up key is pressed, he jumps
		if (c.isKeyDown(38)) {
			if (collisiontop == true || collisionground == true) {
				jump = 22;
				// mario is initially moved up 22 pixels and then this value
				// continually
				// decreases
			}
		}

		// setting sprites for some special cases
		if ((collisiontop == true || collisionground == true) && c.isKeyDown(37) && c.isKeyDown(39)) {
			Mario = pngMarStand;

		}
		if ((collisiontop == true || collisionground == true) && c.isKeyDown(38) && c.isKeyDown(34)) {
			Mario = pngMarStand;

		}
		if ((collisiontop == true || collisionground == true) && c.isKeyDown(40)
				&& (c.isKeyDown(37) || c.isKeyDown(39))) {
			Mario = pngMarDuck;
		}

		// the image begins the move left (creating the illusion that mario
		// is moving
		// right)
		// when he moves past 700 pixels on the screen
		if (xo + x >= 700) {
			xo -= speed;
		}

		// cannot walk back to area that has been scrolled past
		if (x + xo + w < 0) {
			x = 0 - xo - w;
		}
	}

	public static void Gravity() {

		if ((counter % 2) == 0) {
			// jump always decreasing
			jump--;

			// when jump = 0, mario has reached the top of his jump and
			// begins to fall
			if (jump <= 0) {
				grav += 1;
			}
			// y increased (moved down) with grav and
			// y decreased (moved up ) with jump
			y += grav;
			y -= jump;
		}

		// jump cannot be negative
		if (jump < 0) {
			jump = 0;
		}

		if (collisiontop == true || collisionground == true) {
			grav = 0;
		}
		if (collisionbottom == true) {
			jump = 0;
		}

	}

	public static void Goomba() throws IOException {
		// index
		// 1-X
		// 2-Y
		// 3-Image
		// 4-Exists
		// 5-Max
		// 6-Min
		// 7-Dir
		// for all existing goombas,
		for (int j = 0; j <= 15; j++) {
			if (goomba[4][j] == 1) {
				// goombas move left/right based on gDir[]=1/2 respectively
				if (goomba[7][j] == 1) {
					goomba[1][j]--;
				} else
					goomba[1][j]++;

				// collision for mario with goomba
				collision(xo + x, y, w, l, xo + goomba[1][j], goomba[2][j], 50, 50, 7);

				// direction switches when parameters are reached
				for (int i = 0; i <= 15; i++) {
					if (i != j && (goomba[4][i] == 1)) {
						if ((goomba[1][j] + 50 >= (goomba[1][i] + 50 - 5) && goomba[1][j] <= (goomba[1][i] + 50 - 5) + 5
								&& goomba[2][j] + 50 >= (goomba[2][i] + 5)
								&& goomba[2][j] <= goomba[2][i] + (50 - 2 * 5))
								|| (goomba[1][j] + 50 >= goomba[1][i] && goomba[1][j] <= goomba[1][i] + 5
										&& goomba[2][j] + 50 >= (goomba[2][i] + 5)
										&& goomba[2][j] <= goomba[2][i] + (50 - 2 * 5))) {
							goomba[7][j] = goomba[7][j] * (-1);
						}
					}
				}
				if (goomba[1][j] < goomba[6][j] || goomba[1][j] > goomba[5][j]) {
					goomba[7][j] = goomba[7][j] * (-1);
				}

				// alternating sprites using mod of x coordinates
				if ((goomba[1][j] % 60) <= 30) {
					goomba[3][j] = 1;
				} else if ((goomba[1][j] % 60) >= 30)
					goomba[3][j] = 2;
			}
		}
	}

	public static void totalCollision() throws IOException {

		// resetting all collision values
		collisiontop = false;
		collisionbottom = false;
		collisionleft = false;
		collisionright = false;
		collisionground = false;

		// checking collision for mario with all blocks
		collision(xo + x, y, w, l, xo + 855, 490, 60, 60, speed + 1);

		hitboxTop(xo + x, y, w, l, xo, 708, 3700, 60, speed + 1);

		hitboxTop(xo + x, y, w, l, xo + 3800, 708, 810, 60, speed + 1);

		hitboxTop(xo + x, y, w, l, xo + 4770, 708, 3430, 60, speed + 1);

		hitboxTop(xo + x, y, w, l, xo + 4770 + 3430 + 100, 708, 3450, 60, speed + 1);

		collision(xo + x, y, w, l, xo + 855, 490, 60, 60, speed + 1);

		collision(xo + x, y, w, l, xo + 1070, 490, 270, 60, speed + 1);

		collision(xo + x, y, w, l, xo + 1070, 490, 270, 60, speed + 1);

		collision(xo + x, y, w, l, xo + 1180, 270, 60, 60, speed + 1);

		collision(xo + x, y, w, l, xo + 1510, 600, 100, 140, speed + 1);

		collision(xo + x, y, w, l, xo + 2040, 540, 100, 180, speed + 1);

		collision(xo + x, y, w, l, xo + 2470, 490, 100, 240, speed + 1);

		collision(xo + x, y, w, l, xo + 3065, 490, 100, 240, speed + 1);

		collision(xo + x, y, w, l, xo + 3420, 430, 60, 60, speed + 1);

		collision(xo + x, y, w, l, xo + 4120, 490, 170, 60, speed + 1);

		collision(xo + x, y, w, l, xo + 4280, 270, 440, 60, speed + 1);

		collision(xo + x, y, w, l, xo + 4870, 270, 220, 60, speed + 1);

		collision(xo + x, y, w, l, xo + 5030, 490, 60, 60, speed + 1);

		for (int i = 0; i < 400; i += 162) {

			collision(xo + x, y, w, l, xo + 5675 + i, 490, 60, 60, speed + 1);
		}

		collision(xo + x, y, w, l, xo + 5675 + 162, 270, 60, 60, speed + 1);

		collision(xo + x, y, w, l, xo + 6320, 490, 60, 60, speed + 1);

		collision(xo + x, y, w, l, xo + 6480, 270, 170, 60, speed + 1);

		collision(xo + x, y, w, l, xo + 6855, 270, 220, 60, speed + 1);

		collision(xo + x, y, w, l, xo + 6910, 490, 110, 60, speed + 1);

		collision(xo + x, y, w, l, xo + 8735, 600, 100, 140, speed + 1);

		for (int i = 4; i > 0; i--) {

			collision(xo + x, y, w, l, xo + 7710 - 54 * i, 708 - 55 * i, 60, 70 * i, speed + 1);

		}
		for (int i = 4; i > 0; i--) {

			collision(xo + x, y, w, l, xo + 8520 - 54 * i, 708 - 55 * i, 60, 70 * i, speed + 1);

		}

		for (int i = 4; i > 0; i--) {

			collision(xo + x, y, w, l, xo + 7870 + 54 * i, 708 - 55 * i, 60, 70 * i, speed + 1);

		}
		collision(xo + x, y, w, l, xo + 7870 + 55 * 5, 708 - 55 * 4, 60, 70 * 4, speed + 1);

		for (int i = 4; i > 0; i--) {

			collision(xo + x, y, w, l, xo + 7120 + 54 * i, 708 - 55 * i, 60, 70 * i, speed + 1);

		}

		for (int i = 8; i > 0; i--) {

			collision(xo + x, y, w, l, xo + 9640 + 54 * i, 708 - 55 * i, 60, 70 * i, speed + 1);

		}

		collision(xo + x, y, w, l, xo + 9695 + 54 * 7, 708 - 55 - 55 * 7, 115, 70 + 55 * 7, speed + 1);

		collision(xo + x, y, w, l, xo + 9000, 490, 220, 60, speed + 1);

		collision(xo + x, y, w, l, xo + 9595, 600, 100, 140, speed + 1);

		collision(xo + x, y, w, l, xo + 10600, 650, 60, 60, speed + 1);
	}

	public static void endScreen() throws IOException {

		c.setBackgroundColor(Color.BLACK);
		c.clear();

		c.setColor(Color.WHITE);
		c.setFont(new Font("Snap ITC", 10, 40));

		if (win) {
			c.drawString("You Won!", 100, 100);
		} else
			c.drawString("Try Again!", 100, 100);

		c.drawString("Your score was " + score, 400, 100);
		c.drawString("\nPlease enter a username: ", 100, 295);
		c.setCursor(16, 96);
		name = c.readLine();

		recordScores();

		initializeVars();
		endscreen = false;

	}

	public static void collision(int X, int Y, int W, int L, int X2, int Y2, int W2, int L2, int gap)
			throws IOException {

		// collision cannot handle negative width so the collision box is moved
		// over
		if (W < 0) {
			W = (-1) * W;
			X -= W;
		}

		// checking collision for all sides
		// each side has a 3/4 (depending on speed) pixel wide collision box
		hitboxTop(X, Y, W, L, X2, Y2, W2, L2, gap);
		hitboxBottom(X, Y, W, L, X2, Y2, W2, L2, gap);

		if ((collisiontop == false && collisionbottom == false) || Y2 == 222) {
			hitboxRight(X, Y, W, L, X2, Y2, W2, L2, gap);
			hitboxLeft(X, Y, W, L, X2, Y2, W2, L2, gap);
		}
	}

	public static void recordScores() throws IOException {

		File outFile = new File("E:\\highscores.txt");

		// The line below will append to the end of the file
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile, true));

		writer.write(name);
		writer.newLine();

		writer.write("" + score);
		writer.newLine();

		writer.close();
		// writes name and score in highscore file

	}

	public static void hitboxLeft(int X, int Y, int W, int L, int X2, int Y2, int W2, int L2, int gap)
			throws IOException {
		if (X + W >= X2 && X <= X2 + gap && Y + L >= (Y2 + gap) && Y <= Y2 + (L2 - 2 * gap)) {
			// if collision is w a goomba, game ends
			for (int j = 0; j <= 15; j++) {
				if (X2 == goomba[1][j] + xo && Y2 == goomba[2][j]) {
					win = false;
					endscreen = true;
					endScreen();
					run = false;
				}
			}
			// mario is moved to the left of the collision box
			collisionleft = true;
			x = X2 - W - xo;

		}
	}

	public static void hitboxRight(int X, int Y, int W, int L, int X2, int Y2, int W2, int L2, int gap)
			throws IOException {
		if (X + W >= (X2 + W2 - gap) && X <= (X2 + W2 - gap) + gap && Y + L >= (Y2 + gap) && Y <= Y2 + (L2 - 2 * gap)) {
			// if collision is w a goomba, game ends
			for (int j = 0; j <= 15; j++) {
				if (X2 == goomba[1][j] + xo && Y2 == goomba[2][j]) {
					win = false;
					endscreen = true;
					endScreen();
					run = false;
				}
			}

			collisionright = true;
			x = X2 + W2 + W - xo;
			// mario is moved to the right of the collision box

		}
	}

	public static void hitboxTop(int X, int Y, int W, int L, int X2, int Y2, int W2, int L2, int gap) {
		if (X + W >= X2 + gap && X <= X2 + W2 - 2 * gap && Y + L >= Y2 && Y <= Y2 + gap) {

			// if collision is w a goomba, goomba stops existing and mario jumps
			for (int j = 0; j <= 15; j++) {
				if (X2 == goomba[1][j] + xo && Y2 == goomba[2][j]) {
					score += 100;
					grav = 0;
					jump = 10;
					goomba[4][j] = 0;
				}
			}

			// he is on the ground if...
			if (Y2 == 708) {
				collisionground = true;
			} else
				collisiontop = true;

			// moved to the top of the block
			y = Y2 - l;
		}
	}

	public static void hitboxBottom(int X, int Y, int W, int L, int X2, int Y2, int W2, int L2, int gap) {
		if (X + W >= X2 + gap && X <= X2 + W2 - 2 * gap && Y + L >= (Y2 + L2 - gap) && Y <= (Y2 + L2 - gap) + gap) {
			collisionbottom = true;
			// moved to the bottom of the block
			y = Y2 + L2;
		}

	}

}
