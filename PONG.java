import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.*;

import javax.swing.JFrame;

public class PONG extends GameDriverV4 {

	private static final Font Font = null;

	//variable
	Rectangle r1;
	Rectangle paddle1;
	Rectangle paddle2;
	double Xspeed = 7;
	double Yspeed = 1;
	int score1 = 0;
	int score2 = 0;
	int padSpeed = 5;
	int pad1 = 1;
	int pad2 = 1;
	int gameState = 0;

	public PONG() {
		this.setBackGroundColor(Color.black);
		r1 = new Rectangle(350, 250, 10, 10);
		paddle1 = new Rectangle(0, 250, 10, 70);
		paddle2 = new Rectangle(680, 250, 10, 70);
	}

	public static void main(String[] args) {
		PONG p1 = new PONG();
		p1.start();
	}

	public void paintComponent(Graphics g) {
		if (gameState == 0) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.GREEN);
			Font font = new Font("ARIAL", Font.BOLD, 20);
			g.setFont(font);
			g.drawString("P1: " + score1, 270, 30);
			g.drawString("P2: " + score2, 370, 30);
			g.drawLine(350, 0, 350, 500);
		}
	}

	public void reset(Graphics2D win) {
		Xspeed = 5;
		Yspeed = 1;
		score1 = 0;
		score2 = 0;
		padSpeed = 5;
		gameState = 0;
	}

	public void ballMovement(Graphics2D win) throws InterruptedException {

		if (gameState == 1) {
			r1.translate((int) Xspeed, (int)Yspeed);
		}
		if (gameState == 2) {
			r1.translate((int) Xspeed, (int)Yspeed);
		}

		win.setColor(Color.GREEN);
		win.fill(paddle1);
		win.fill(paddle2);
		win.fill(r1);

		// hits border
		if ((r1.getY() > 475) || (r1.getY() < 0)) {
			Yspeed = Yspeed * -1;
		}

		if ((r1.getX() > 700)) {
			Xspeed = Xspeed * -1;
			score1++;
			r1.setLocation(350, 250);
			paddle1.setLocation(0, 250);
			paddle2.setLocation(680, 250);
			Xspeed = 5;
			Yspeed = 1;
			Thread.sleep(1000);
		}

		if ((r1.getX() < -5)) {
			Xspeed = Xspeed * -1;
			score2++;
			r1.setLocation(350, 250);
			paddle1.setLocation(0, 250);
			paddle2.setLocation(680, 250);
			Xspeed = 5;
			Yspeed = 1;
			Thread.sleep(1000);
		}

		// score to game over
		if ((score1 == 5) || (score2 == 5)) {
			gameState = 3;
		}

		// hits paddle
		if (r1.intersects(paddle2)) {
			Xspeed *= -1;
			win.setColor(Color.white);
			win.fill(paddle2);
			
			//smash ball
			if (Yspeed < 0 && padSpeed > 0 && gameState == 1) {
				Xspeed = Xspeed * 2;
				Yspeed = Yspeed * 1.25;
				win.setColor(Color.RED);
				win.fill(r1);
				win.setColor(Color.ORANGE);
				win.fill(r1);
			}
			if (Yspeed > 0 && padSpeed < 0 && gameState == 1) {
				Xspeed = Xspeed * 2;
				Yspeed = Yspeed * 1.25;
				win.setColor(Color.RED);
				win.fill(r1);
				win.setColor(Color.ORANGE);
				win.fill(r1);
			}
		}

		if (r1.intersects(paddle1)) {
			Xspeed *= -1;
			Xspeed = 5;
			win.setColor(Color.white);
			win.fill(paddle1);
		}
	}

	public void paddleMovement(Graphics2D win) {
		if (gameState == 0) {
			win.setColor(Color.BLACK);
			win.fill(r1);
		}

		// paddle1 auto
		if (gameState == 1) {
			int destinationY = (int) r1.getY();
			if(destinationY > paddle1.getY()) {
				paddle1.translate(0, 4);
			} else {
				paddle1.translate(0,-4);
			}
			//make bounce
			if(paddle1.getY() + 90 > 500) {
				paddle1.translate(0,(int) (500 - (paddle1.getY()+90)));
			}
			if(paddle1.getY() < 0) {
				paddle1.translate(0, (int) -paddle1.getY());
			}
		}

		// paddle1 player
		if (this.Keys[KeyEvent.VK_S] && paddle1.getY() > 0 && gameState ==2) {
			padSpeed = -5;
			paddle1.translate(0, padSpeed);
		}
		if (this.Keys[KeyEvent.VK_X] && paddle1.getY() < 409 && gameState==2) {
			padSpeed = 5;
			paddle1.translate(0, padSpeed);
		}

		// paddle2 player
		if (this.Keys[KeyEvent.VK_UP] && paddle2.getY() > 0) {
			padSpeed = -5;
			paddle2.translate(0, padSpeed);
		}
		if (this.Keys[KeyEvent.VK_DOWN] && paddle2.getY() < 409) {
			padSpeed = 5;
			paddle2.translate(0, padSpeed);
		}
	}

	public void splashpage(Graphics g) {
		// fonts
		g.setColor(Color.WHITE);
		Font font1 = new Font("COURIER", Font.BOLD, 46);
		g.setFont(font1);
		g.drawString("WARREN PING PONG", 140, 100);

		Font font2 = new Font("COURIER", Font.PLAIN, 24);
		g.setFont(font2);
		g.drawString("Press 1 for single player mode: ", 140, 200);
		g.drawString("Press 2 for two player mode: ", 140, 250);
		g.drawString("HOW TO PLAY:", 140, 350);

		Font font3 = new Font("COURIER", Font.PLAIN, 14);
		g.setFont(font3);
		g.drawString("press up & down to move right paddle", 140, 220);
		g.drawString("press up & down to move right paddle and press s and x", 140, 270);
		g.drawString("to move left paddle ", 140, 290);
		g.drawString("Hit the ball to the opposing side for a point. First", 140, 370);
		g.drawString("player to 5 points wins the match.", 140, 390);

		// change game state
		if (GameDriverV4.Keys[KeyEvent.VK_1]) {
			gameState = 1;
		}
		if (GameDriverV4.Keys[KeyEvent.VK_2]) {
			gameState = 2;
		}
	}

	public void gameOver(Graphics g) {
		// stops motion
		Yspeed = 0;
		Xspeed = 0;
		padSpeed = 0;

		// prints game over
		g.setColor(Color.RED);
		Font font = new Font("ARIAL", Font.BOLD, 30);
		g.setFont(font);
		if (score1 > score2) {
			g.drawString("GAMEOVER! P1 WINS!", 200, 200);
		}
		if (score2 > score1) {
			g.drawString("GAMEOVER! P2 WINS", 200, 200);
		}

		g.setColor(Color.WHITE);
		Font font5 = new Font("COURIER", Font.PLAIN, 20);
		g.setFont(font5);
		g.drawString("press enter to play again", 210, 220);

		if (this.Keys[KeyEvent.VK_ENTER]) {
			score1 = 0;
			score2 = 0;
			gameState = 0;
		}
	}

	public void draw(Graphics2D win) {

		if (gameState == 0) {
			this.reset(win);
			this.splashpage(win);
			this.paddleMovement(win);
		}
		if (gameState == 3) {
			this.gameOver(win);
		}

		// ball movement
		try {
			this.ballMovement(win);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// paddle movement
		this.paddleMovement(win);

		// score
		this.paintComponent(win);
	}
}
