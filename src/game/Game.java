package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	
	
    // No Idea :?
	private static final long serialVersionUID = 1L;
	
	// Set my widths and heights
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH/12*9;
	public static final int SCALE = 3;
	public static final String NAME = "Orbital Invasion V1.0 Progress!";
	// Create the actual frame
	private JFrame frame;
	
	public boolean running = false;
	public int tickCount = 0;
	//adds an image overlay and sets it up and stuff
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	public Game() {
		// Set the sizes for the frame
		setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		// Name the frame
		frame = new JFrame(NAME);
		//Exit on close
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		// Center of screen
		frame.add(this, BorderLayout.CENTER);
		//Idk, not resizable
		frame.pack();
		frame.setResizable(false);
		//Centered location
		frame.setLocationRelativeTo(null);
		// make it visible
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		// Running when game starts
		running = true;
		// NO idea, more detail later
		new Thread(this).start();
		
	}
	
	
    public synchronized void stop() {
	    new Thread(this).start();
	    // Not running when the game stops
	    running = false;
		
    }
    // Main game loop
	public void run() {
		
		//Nanoseconds since 1970 or whatever
		long lastTime = System.nanoTime();
		//Nanoseconds per update or whatever
	    double nsPerTick = 1000000000D/60D;
	    
	    int frames = 0;
	    int ticks = 0;
	    
	    long lastTimer = System.currentTimeMillis();
	    double delta = 0;
	    
	    
		while(running) {
			//FPS stuff idk
			long now = System.nanoTime();
			delta += (now - lastTime)/nsPerTick;
			lastTime = now;
			//Start ticking and updating the game when the game starts
			
			boolean shouldRender = true;
			
			while (delta >= 1) {
				//TIck  the game, 60 fps stuff, runs until it hits 60 or something
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			//limits frames
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// limits the number of times we render
			if (shouldRender) {
				
			frames++;
			render();
			}
			
			//if the time since we just updated is greater than 1 second, update
			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				System.out.println(ticks + "Ticks" + frames + "Frames");
				frames = 0;
				ticks = 0;
				
				
			}
		}
	}
	// Updates the game logic
	public void tick() {
		
		
		for (int i=0; i<pixels.length; i++) {
			pixels[i] = i*tickCount;
			
			
		}
		
		
		tickCount++;
	}
	//Prints what tick tells it to print, essentially runs the game
	public void render() {
		//organizes what I put on the canvas
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			//we are triple buffering, higher number prevents image tears. I are doing the kind of buffering where I clear the image and redraw it, also Ryan is gay
			createBufferStrategy(3);
			return;
		}
		
		//This is my graphics object
		Graphics g = bs.getDrawGraphics();
		
		
		
		//draws the buffer image I created
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		
		
		//throws away image to free space in memory
		g.dispose();
		//show contents of buffer
		bs.show();
		
	}
	
	public static void main(String[] args) {
	new Game().start();
		
	}


}
