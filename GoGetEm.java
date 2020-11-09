// Salma Hashem    Gotcha Multi disk    
package gotcha_pkg;

import java.util.ArrayList;


import processing.core.PApplet;

import processing.core.PImage;   // For background image


public class GoGetEm extends PApplet {
	// Timer
	int timer;
	// game length-- 20 seconds
	int gameDuration= 20*1000;

    // Keeps track of current score
    int score = 0;

    // Canvas size
    final int canvasWidth  = 500;
    final int canvasHeight = 500;
  //create ArrayList to hold disks
    ArrayList<Disk> disks = new ArrayList<>();
    
    float[] y = {50, 150, 250};
    float[] x = {100, 200, 300};
    int[] pointValue = {10, 20, 30};
    
    PImage img;
    PImage bg;
    PImage end;
 // Set up Strings with image file names 
    String s1 = "gotcha_pkg/img.png";
    String s2 = "gotcha_pkg/img3.png";
    String s3 = "gotcha_pkg/img2.png";
    
    public static void main(String[] args){
        PApplet.main("gotcha_pkg.GoGetEm");
    }
    
    public void settings() {
        size(canvasWidth, canvasHeight);
        smooth();
    }

    // setup() runs one time at the beginning of your program
    @Override
    public void setup() {
    		bg= loadImage("gotcha_pkg/Game_background.png");
    		
    		background(bg);
    		img= loadImage("gotcha_pkg/img.png");
    	
    	
        // Create disks
    		
    		//set timer now
    		timer= millis() +gameDuration;
    		disks.add(new Disk(s1,  random(0, 255), y[0], 2+(2*0), pointValue[0] ));
    	    disks.add(new Disk(s2, random(0, 255), y[1], 2+(2*1), pointValue[1]));
    	    disks.add(new Disk(s3, random(0, 255), y[2], 2+(2*2), pointValue[2]));
    	    
    		
       
    }

    // draw() is called repeatedly in an infinite loop.
    // By default, it is called about 60 times per second.
    public void draw() {
        // Erase the background, if you don't, the previous shape(s) will 
        // still be displayed
        eraseBackground();

        for(int j = 0; j<disks.size(); j++) {
    		// Move the shape, i.e. calculate the next x and y position
        // where the shape will be drawn.
    		disks.get(j).calcCoords();
    		// Draw the shape
    		disks.get(j).drawShape();
    		// Display point value on the shape
    		disks.get(j).displayPointValue();
    }
        
        textSize(20);
        fill(225,0,50);
        textAlign(CENTER);
        text("Score: " + this.score, 250, 400);
        
        if(millis()>= timer) {
        		
        		end= loadImage("gotcha_pkg/Game_background.png");
        		background(end);
        		// output the final score
        		textSize(30);
        		fill(0,0,0);
        		textAlign(CENTER);
        		text("Great job!", 250, 200);
        		text("Your final score is: " + score, 250, 250);
        		
        		// Let the user click when finished reading score 
        		textSize(20);
        		fill(255, 255, 255);
        		text(" Click mouse to exit", 240, 400);
        		if (this.mousePressed) {
        			//Exit
        			System.exit(0);
        		}
        }
    }

    public void eraseBackground() {      
        
    		background(bg);
    }

    // mousePressed() is a PApplet method that I override.
    // This method is called from PApplet one time when the mouse is pressed.
    @Override
    public void mousePressed() {
        // Draw a circle wherever the mouse is
        int mouseWidth  = 20;
        int mouseHeight = 20;
        fill(0, 255, 0);
        ellipse(mouseX, mouseY, mouseWidth, mouseHeight);
        // Check whether the click occurred within range of the shape
        for (int i =0; i<3; i++) {
	        if ((this.mouseX >= (disks.get(i).x+60 - disks.get(i).targetRange)) &&
	        	    (this.mouseX <= (disks.get(i).x+60 + disks.get(i).targetRange)) && 
	        	    (this.mouseY >= (disks.get(i).y+60 - disks.get(i).targetRange)) &&
	        	    (this.mouseY <= (disks.get(i).y+60 + disks.get(i).targetRange))) {
	        	
	        		// Update score:
	        	    score= score + disks.get(i).pointValue;
	    
	            System.out.println("DBG:  HIT!");
	        }
        }
    }

    // Creating a Disk class to create one or more disks with each
    // disk having a color, speed, position, etc.
    class Disk {
        // Size of disk
    	
    		final int shapeWidth= 120;
    		final int shapeHeight = 80;
    	
        // Point value of disk
        int pointValue;

        // Position of disk - keep track of x and y position of disk
        float x ;
        
        float y ;
        
        PImage img;
        
        // Horizontal speed of disk
        int xSpeed;
        
        // It's hard to click a precise pixel on a disk, to make it easier I 
        // allow the user to click somewhere on the disk.
        //  the scoring space is a rectangle fitted tightly
        // to the disk - it's easier than calculating a rounded boundary.
        int targetRange = Math.round((min(shapeWidth, shapeHeight)) / 2);

        Disk(String img, float x, float y, int xSpeed, int pointValue) {
        		this.img = loadImage(img);
        		this.x = x;
        		this.y = y;
        		this.xSpeed = xSpeed;
        		this.pointValue = pointValue;
        
            
        }

        public void calcCoords() {      
            // Computes the x position where the shape will be drawn
            
            this.x += this.xSpeed; 
            // If the x position is off right side of the canvas, reverse direction of 
            // movement:
            
            if (this.x > canvasWidth) {
        	
                // Log a debug message:
                System.out.println("DBG:  <---  Change direction, go left because x = " + this.x);

                // Recalculate:
                this.xSpeed = -1 * this.xSpeed;             }

            // If the x position is off left side of the canvas, reverse direction of 
            // movement:
            if (this.x < 0) {
                // Log a debug message:
                System.out.println("DBG:      ---> Change direction, go right because x = " + this.x + "\n");
               
                // Recalculate:
                this.xSpeed = -1 * this.xSpeed;
            } 
        }

        public void drawShape() {
            //draw the shape at computed x, y location
            image(img, this.x, this.y);
            ellipseMode(CORNER);
            noFill();
            stroke(255);

        }

        public void displayPointValue() {
            // Draw the text at computed x, y location
            
            textSize(25);
            fill(255, 255, 255);
            
            textAlign(CENTER);
            
            text(pointValue, this.x+60, this.y+60); 
        }
    }
}