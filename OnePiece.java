import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.opengl.*;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OnePiece {
	// Set this to true to force the game to exit.
	private static boolean shouldExit;

	// The previous frame's keyboard state.
	private static boolean kbPrevState[] = new boolean[256];

	// The current frame's keyboard state.
	private static boolean kbState[] = new boolean[256];

	// Position of the sprite.
	//private static float[] spritePos = new float[] { 10, 410};
	private static float[] spritePos = new float[] { 10, 500};

	// Texture for the sprite.
	private static int spriteTex;
	private static int lionTex;
	private static int jumperTex;
	private static int CPTex;
	private static int gRobotTex;
	private static int fRobotTex;
	private static int TSTex;
	private static int namiTex;
	private static int WinTex;
	
	// Size of the sprite.
	private static int[] spriteSize = new int[2];
	private static int[] jumperSize = new int[2];
	private static int[] CPSize = new int[2];
	private static int[] gRobotSize = new int[2];
	private static int[] fRobotSize = new int[2];
	private static int[] TSSize = new int[2];
	private static int[] namiSize = new int[2];
	private static int[] WinSize = new int[2];
    
    private static int[] lionSize = new int[2];
    private static int lion2HP = 3;
    
    private static int marineTex;
    private static int marineTex2;
    private static int[] marineSize = new int[2];
    private static int marineHP = 10;
    private static int marineHP2 = 10;
	
	private static int bgTex;
	private static int[] bgSize = new int[2];
	
	private static int bgTex2;
	private static int[] bgSize2 = new int[2];
	
	private static int bgTex3;
	private static int[] bgSize3 = new int[2];
	
	private static int bgTex4;
	private static int[] bgSize4 = new int[2];
	
	private static int bgTex5;
	private static int[] bgSize5 = new int[2];
	
	private static int bulletTex;
	private static int[] bulletSize = new int[2];
	
	private static int bulletTex2;
	private static int[] bulletSize2 = new int[2];
	
	private static int bhTex;
	private static int[] bhSize = new int[2];
	
	private static int eqTex;
	private static int[] eqSize = new int[2];
	
	private static int bulletTex3;
	private static int[] bulletSize3 = new int[2];
	
	private static List<Enemy> enemies = new ArrayList<Enemy>();
	private static List<AnimationData> bulletList = new ArrayList<>();
	
	private static int rightBorder = 0;
	private static int botBorder = 0;
	private static float[] tileLocation = new float[4];
	
	private static boolean jump = true;
	private static boolean fall = false;
	private static String direction = "R";
	private static int flag = 0;
	private static int trans = 0;
	private static float fallCheck = 405;
	private static boolean falling = false;
	
	private static int checkPoint = 0;
	private static boolean dead = false;
	private static boolean win = false;
	private static boolean saveNami = false;
	private static float maxX = 0;

	public static void main(String[] args) {
		GLProfile gl2Profile;

		try {
			// Make sure we have a recent version of OpenGL
			//gl2Profile = GLProfile.get(GLProfile.GL2);
			gl2Profile = GLProfile.getDefault();
			}
		catch (GLException ex) {
			System.out.println("OpenGL max supported version is too low.");
			System.exit(1);
			return;
			}

		// Create the window and OpenGL context.
		GLWindow window = GLWindow.create(new GLCapabilities(gl2Profile));
		window.setSize(800, 600);
		window.setTitle("One Piece");
		window.setVisible(true);
		window.setDefaultCloseOperation(
				WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);
		window.addKeyListener(new KeyListener() {
		    @Override
		    public void keyPressed(KeyEvent keyEvent) {
		        if (keyEvent.isAutoRepeat()) {
		            return;
		            }
		        kbState[keyEvent.getKeyCode()] = true;
		        }

		    @Override
		    public void keyReleased(KeyEvent keyEvent) {
		        if (keyEvent.isAutoRepeat()) {
		            return;
		            }
		        kbState[keyEvent.getKeyCode()] = false;
		        }
		    });

		// Setup OpenGL state.
		window.getContext().makeCurrent();
		GL2 gl = window.getGL().getGL2();
		gl.glViewport(0, 0, 800, 600);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glOrtho(0, 800, 600, 0, 0, 100);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

		// Game initialization goes here.	
		bgTex = glTexImageTGAFile(gl, "sky.tga",bgSize);
		bgTex2 = glTexImageTGAFile(gl, "grass.tga",bgSize2);
		bgTex3 = glTexImageTGAFile(gl, "rock.tga",bgSize3);
		bgTex4 = glTexImageTGAFile(gl, "box.tga",bgSize4);
		bgTex5 = glTexImageTGAFile(gl, "water.tga",bgSize5);
		jumperTex = glTexImageTGAFile(gl, "jumper.tga",jumperSize);
		CPTex = glTexImageTGAFile(gl, "sunny.tga", CPSize);
		gRobotTex = glTexImageTGAFile(gl, "gRobot.tga", gRobotSize);
		fRobotTex = glTexImageTGAFile(gl, "fRobot.tga", fRobotSize);
		spriteTex = glTexImageTGAFile(gl, "luffy0.tga", spriteSize);
		lionTex = glTexImageTGAFile(gl, "lion.tga", lionSize);
		bulletTex = glTexImageTGAFile(gl, "bullet.tga", bulletSize);
		bulletTex2 = glTexImageTGAFile(gl, "lionBullet.tga", bulletSize2);
		bhTex = glTexImageTGAFile(gl, "bh.tga", bhSize);
		eqTex = glTexImageTGAFile(gl, "eq.tga", eqSize);
		marineTex = glTexImageTGAFile(gl, "marine0.tga", marineSize);
		bulletTex3 = glTexImageTGAFile(gl, "marineBullet.tga", bulletSize3);
		TSTex = glTexImageTGAFile(gl, "TS.tga", TSSize);
		namiTex = glTexImageTGAFile(gl, "Nami.tga", namiSize);
		WinTex = glTexImageTGAFile(gl, "Win.tga", WinSize);
		bgSize[0] = 6000;
		bgSize[1] = 600;

		// Create backgroundDef
		int[] dimension = {50,50};
		BackgroundDef bgDef = new BackgroundDef(6000, 600, dimension);
		// Load the Camera
		Camera camera = new Camera(0,0,800,600);
		
		// Set up right and bottom border
		rightBorder = 800 - spriteSize[0];
		botBorder = 600 - spriteSize[1];
		
		FrameDef[] luffyMoveFrames = new FrameDef[4];
		for(int i = 0; i < 4;i++){
			float frameTime = 300;
			FrameDef frame = new FrameDef(i,frameTime);
			luffyMoveFrames[i] = frame;
			}
		AnimationDef luffy = new AnimationDef("luffy",luffyMoveFrames);
		AnimationData luffyData = new AnimationData(0, luffy, luffyMoveFrames[0].getFrameDuration(), spritePos, spriteSize);
		AABB spriteBox = new AABB(spritePos[0], spritePos[1], spriteSize[0], spriteSize[1]);
		luffyData.setShape(spriteBox);
		
		//Create AABB boxes
        AABB cameraBox = new AABB(camera.getX(), camera.getY(), 800, 600);
        AABB jumperBox = new AABB(1600, 520, jumperSize[0], jumperSize[1]);
        AABB jumperBox2 = new AABB(4850, 520, jumperSize[0], jumperSize[1]);
        AABB CPBox1 = new AABB(0, 450, CPSize[0], CPSize[1]);
        AABB CPBox2 = new AABB(2000, 450, CPSize[0], CPSize[1]);
        AABB CPBox3 = new AABB(4000, 450, CPSize[0], CPSize[1]);
        AABB TSBox = new AABB(5500, 300, CPSize[0], CPSize[1]);
        AABB namiBox = new AABB(4750, 150, namiSize[0], namiSize[1]);
        
        //Create bullet
        AnimationData myBullet;
		FrameDef[] fireFrames = new FrameDef[1];
		for(int i = 0; i < 1;i++){
			float frameTime = 300;
			FrameDef frame = new FrameDef(i,frameTime);
			fireFrames[i] = frame;
			}
		AnimationDef bullet = new AnimationDef("bullet", fireFrames);
		myBullet = new AnimationData(0, bullet, fireFrames[0].getFrameDuration(), new float[]{luffyData.getX()+50, luffyData.getY()}, bulletSize);
		myBullet.setVelocity(5);
		AABB bulletBox = new AABB(luffyData.getX()+50, luffyData.getY(), bulletSize[0], bulletSize[1]);
		myBullet.setShape(bulletBox);
		
		//Create Enemy
		FrameDef[] lionFrames = new FrameDef[3];
		for(int i = 0; i < 3;i++){
			float frameTime = 300;
			FrameDef frame = new FrameDef(i,frameTime);
			lionFrames[i] = frame;
			}
		AnimationDef lion = new AnimationDef("lion", lionFrames);
		AnimationData lionData = new AnimationData(0, lion, lionFrames[0].getFrameDuration(), new float[] { 2800, 395}, lionSize);
		AABB lionBox2 = new AABB(2800, 395, lionSize[0], lionSize[1]);
		lionData.setShape(lionBox2);
		lionData.setVelocity((float)1);
		
		Enemy lionEnemy = new Enemy(lionData);
		enemies.add(lionEnemy);
		
		FrameDef[] marineFrames = new FrameDef[2];
		for(int i = 0; i < 2;i++){
			float frameTime = 1000;
			FrameDef frame = new FrameDef(i,frameTime);
			marineFrames[i] = frame;
			}
		AnimationDef marine = new AnimationDef("marine", marineFrames);
		AnimationData marineData = new AnimationData(0, marine, marineFrames[0].getFrameDuration(), new float[] { 1500, 500}, marineSize);
		AABB marineBox = new AABB(1500, 500, marineSize[0], marineSize[1]);
		marineData.setShape(lionBox2);
		marineData.setVelocity((float)1);
		
		AnimationDef marine2 = new AnimationDef("marine", marineFrames);
		AnimationData marineData2 = new AnimationData(0, marine, marineFrames[0].getFrameDuration(), new float[] { 1000, 250}, marineSize);
		AABB marineBox2 = new AABB(1000, 250, marineSize[0], marineSize[1]);
		
		Enemy marineEnemy = new Enemy(marineData);
		Enemy marineEnemy2 = new Enemy(marineData2);
		enemies.add(marineEnemy);
		enemies.add(marineEnemy2);
		
		//Robot Enemy
		FrameDef[] robotFrames = new FrameDef[1];
		for(int i = 0; i < 1;i++){
			float frameTime = 1000;
			FrameDef frame = new FrameDef(i,frameTime);
			robotFrames[i] = frame;
			}
		AnimationDef gRobot1 = new AnimationDef("gRobot1", robotFrames);
		AnimationData gRobot1Data = new AnimationData(0, gRobot1, robotFrames[0].getFrameDuration(), new float[] { 3150, 495}, gRobotSize);
        AABB gRobotBox1 = new AABB(3150, 495, gRobotSize[0], gRobotSize[1]);
        Enemy gRobotEnemy1 = new Enemy(gRobot1Data);  
        enemies.add(gRobotEnemy1);
        
		AnimationDef gRobot2 = new AnimationDef("gRobot2", robotFrames);
		AnimationData gRobot2Data = new AnimationData(0, gRobot1, robotFrames[0].getFrameDuration(), new float[] { 3450, 495}, gRobotSize);
        AABB gRobotBox2 = new AABB(3450, 495, gRobotSize[0], gRobotSize[1]);
        Enemy gRobotEnemy2 = new Enemy(gRobot2Data);  
        enemies.add(gRobotEnemy2);
        
        AnimationDef fRobot1 = new AnimationDef("fRobot1", robotFrames);
		AnimationData fRobot1Data = new AnimationData(0, fRobot1, robotFrames[0].getFrameDuration(), new float[] { 3300, 50}, fRobotSize);
        AABB fRobotBox1 = new AABB(3300, 50, gRobotSize[0], gRobotSize[1]);
        Enemy fRobotEnemy1 = new Enemy(fRobot1Data);  
        enemies.add(fRobotEnemy1);
        
        AnimationDef fRobot2 = new AnimationDef("fRobot2", robotFrames);
		AnimationData fRobot2Data = new AnimationData(0, fRobot2, robotFrames[0].getFrameDuration(), new float[] { 3600, 50}, fRobotSize);
        AABB fRobotBox2 = new AABB(3600, 50, gRobotSize[0], gRobotSize[1]);
        Enemy fRobotEnemy2 = new Enemy(fRobot2Data);  
        enemies.add(fRobotEnemy2);
        
        AnimationDef fRobot3 = new AnimationDef("fRobot3", robotFrames);
		AnimationData fRobot3Data = new AnimationData(0, fRobot3, robotFrames[0].getFrameDuration(), new float[] { 3670, 50}, fRobotSize);
        AABB fRobotBox3 = new AABB(3670, 50, gRobotSize[0], gRobotSize[1]);
        Enemy fRobotEnemy3 = new Enemy(fRobot3Data);  
        enemies.add(fRobotEnemy3);
        
        AnimationDef fRobot4 = new AnimationDef("fRobot4", robotFrames);
		AnimationData fRobot4Data = new AnimationData(0, fRobot4, robotFrames[0].getFrameDuration(), new float[] { 4500, 150}, fRobotSize);
        AABB fRobotBox4 = new AABB(4500, 150, gRobotSize[0], gRobotSize[1]);
        Enemy fRobotEnemy4 = new Enemy(fRobot4Data);  
        enemies.add(fRobotEnemy4);
		
		long lastFrameNS;
		long curFrameNS = System.nanoTime();
		int physicsDeltaMs = 10;
		int lastPhysicsFrameMs;
		int curFrameMs = (int)(curFrameNS / 1000000);
		boolean fire = false;
		
		
		// The game loop
		while (!shouldExit ) {
			System.arraycopy(kbState, 0, kbPrevState, 0, kbState.length);
			lastFrameNS = curFrameNS;
			lastPhysicsFrameMs = (int)(lastFrameNS / 1000000);

			// Actually, this runs the entire OS message pump.
			window.display();
			if (!window.isVisible()) {
				shouldExit = true;
				break;
				}           
			//update new currentFrameMS and calculate deltaTime
			curFrameNS = System.nanoTime();
			float deltaTimeMS = (curFrameNS - lastFrameNS) / 1000000;
		
			curFrameMs = (int)(curFrameNS/1000000);
			
			// Game logic.
			do{ 
				if (kbState[KeyEvent.VK_ESCAPE]) {
					shouldExit = true;
					}
	/**
				if (kbState[KeyEvent.VK_LEFT] && catData.getX() > 0) {
					double halfScreen = 0.5*800 - (0.5)*catData.getWidth();
					if((catData.getX() < halfScreen) && camera.getX() > 0){
						camera.updateX(-catData.getVelocity());
						}
					else{
						float x = catData.getX() - catData.getVelocity();
						catData.setX(x);
						}
				   
					catData.getShape().setX(catData.getX()+camera.getX());
					}

				if (kbState[KeyEvent.VK_RIGHT] && catData.getX() < rightBorder) {
					double halfScreen = 0.5*800 - (0.5)*catData.getWidth();
					if((catData.getX() >= halfScreen) && camera.getX() < (bgSize[0]-800-5)){
						camera.updateX(catData.getVelocity());
						}
					else{
						float x = catData.getX() + catData.getVelocity();
						catData.setX(x);
						}
					catData.getShape().setX(catData.getX()+camera.getX());
					}
				
				if (kbState[KeyEvent.VK_UP] && catData.getY() > 0) {
					if((catData.getY() <= 0.5*600) && camera.getY() > 0){
						camera.updateY(-catData.getVelocity());
						}
					else{
						float y = catData.getY() - catData.getVelocity();
						catData.setY(y);
						}
					catData.getShape().setY(catData.getY()+camera.getY()); 
					}
	
				if (kbState[KeyEvent.VK_DOWN] && catData.getY() < botBorder) {
					if((catData.getY() >= 0.5*600) && camera.getY() < (bgSize[1]-600)){
						camera.updateY(catData.getVelocity());
						}
					else{
						float y = catData.getY() + catData.getVelocity();
						catData.setY(y);
						}
					
					catData.getShape().setY(catData.getY()+camera.getY());
					}
	*/			
				int start_tileX = (int) Math.floor(camera.getX() / bgDef.getTileW());
				int start_tileY = (int) Math.floor(camera.getY() / bgDef.getTileH());
				int end_tileX = (int) Math.floor((camera.getX() + 800) / bgDef.getTileW());
				int end_tileY = (int) Math.floor((camera.getY() + 600) / bgDef.getTileH());
				int flag = 0;
				float right1 = luffyData.getShape().getX() + luffyData.getShape().getW()+ 10;
				float left1 = luffyData.getShape().getX() -10 ;
				float top1 = luffyData.getShape().getY() -10;
				float bot1 = luffyData.getShape().getY() + luffyData.getShape().getH() +5;
				
				if (kbState[KeyEvent.VK_LEFT] && luffyData.getX() > 0) {
					double halfScreen = 200;
					float x = luffyData.getX() - luffyData.getVelocity();
					luffyData.setX(x);
				   
					luffyData.getShape().setX(luffyData.getX());
					//System.out.println(luffyData.getX());
					//System.out.println(luffyData.getX()- (float)halfScreen);
					if (luffyData.getX() > halfScreen && luffyData.getX() + 600 < bgSize[0]-60)
					{
						camera.setX(luffyData.getX()- (float)halfScreen);
						//System.out.println("updating");
					}
						
					direction = "L";
					
					}

				if (kbState[KeyEvent.VK_RIGHT] && luffyData.getX() < bgSize[0]-60) {
					
					/**
					AABB bgBox = new AABB(right1, top1, 50, 50);
					AABB spBox = new AABB(left1, bot1, spriteSize[0], spriteSize[1]);
					int pos = bgDef.getTile((int)catData.getX()/bgDef.getTileW(),(int)(catData.getY())/bgDef.getTileH());
					
					if(bgDef.getTileCollision(pos+1) == true && AABBcheck(bgBox, spBox)){
						System.out.println("Horizontal Collision detected");
						catData.getShape().setX(catData.getX()+camera.getX());
					}
					else 
					{
						float x = catData.getX() + catData.getVelocity();
						catData.setX(x);

						catData.getShape().setX(catData.getX());
					}
					System.out.println(catData.getShape().getX());
					System.out.println(catData.getShape().getY());
					System.out.println(bgBox.getX());
					System.out.println(bgBox.getY());
				*/
					double halfScreen = 200;
					float x = luffyData.getX() + luffyData.getVelocity();
					luffyData.setX(x);

					luffyData.getShape().setX(luffyData.getX());
					
					if (luffyData.getX() > halfScreen && luffyData.getX() + 600 < bgSize[0])
						camera.setX(luffyData.getX()- 200);

					direction = "R";
				}
				//System.out.println(luffyData.getX());
				/**
				if (kbState[KeyEvent.VK_UP] ) 
				{
					System.out.print(catData.getY());
				*/	
						/**
						float current = catData.getY();
						for (float i = current; i < current + 150 ; i = i + (float)catData.getVelocity())
						{
							float y = catData.getY() - (float)catData.getVelocity();
							catData.setY(y);
							catData.getShape().setY(catData.getY()); 
						}
						*/
				/**
					if (jump)
					{
						float deltaY;
						if(catData.getY() < 260){
							deltaY = Math.min(catData.getVelocity(), 260 - catData.getY());
						}
						else 
							deltaY = - (Math.min(catData.getVelocity(), catData.getY() - catData.getY()));
						
						//System.out.println(deltaY);
						catData.setY(catData.getY() + ( deltaY * deltaTimeMS / 10));
					}
						
					
					System.out.print(catData.getY());
					if (jump)
						System.out.print("jump\n");
					else
						System.out.print("not jump\n");
					
					if (catData.getY() < 410)
						jump = false;
				}
	
				if (kbState[KeyEvent.VK_DOWN] && catData.getY() < 2000) {
					float y = catData.getY() + catData.getVelocity();
					catData.setY(y);
					
					catData.getShape().setY(catData.getY());
					}
				*/
				
				/**
				for(int i = start_tileY; i < end_tileY;i++){
					for(int j = start_tileX; j < end_tileX; j++){
						int pos = bgDef.getTile(j,i);
			
						AABB bgBox = new AABB(j*bgDef.getTileW(), i*bgDef.getTileH(), 50, 50);
						if(bgDef.getTileCollision(pos) == true && AABBcheck(bgBox, luffyData.getShape()) && flag == 0 && luffyData.jumping()){
							System.out.println("Horizontal Collision detected");
							flag = 1;
							float right2 = bgBox.getX()+bgDef.getTileW();
							
							//System.out.println(right1);
							//System.out.println(bgBox.getX());
							if(right1 > bgBox.getX() && left1 + 30 < bgBox.getX() ){
								//System.out.println(right1);
								//System.out.println(bgBox.getX());
								luffyData.setX(luffyData.getX() - (right1 - bgBox.getX()) -5);
								//System.out.println(right1);
								//System.out.println(bgBox.getX());
								//System.out.println("back right");
							}
							
							if(left1 < right2 && right1 -30 > right2){
								luffyData.setX(luffyData.getX() + (right2 - left1) + 5);
								//System.out.println(left1);
								//System.out.println(right2);
								//System.out.println("back left");
							}
							luffyData.getShape().setX(luffyData.getX()+camera.getX());
						}
						else flag = 0;
					}
					
				}
				*/
				
				//addHorCol(luffyData, dogBox, camera);
				
				//addHorCol(luffyData, lionBox2, camera);
				
				/**
				if (kbState[KeyEvent.VK_UP] && luffyData.getY() > 0) {
					float y = luffyData.getY() - luffyData.getVelocity();
					luffyData.setY(y);

					luffyData.getShape().setY(luffyData.getY()); 
					direction = "U";
					}
					*/
				
				//Jumping
					
				//if (falling)
					//System.out.println("falling");
				//if (!falling)
					//System.out.println("falling");
				if (kbState[KeyEvent.VK_UP] && luffyData.getY() > 0 && !luffyData.jumping() && !falling) {
					//if (!falling)
						//System.out.println("jumpable");
					if((luffyData.getY() <= 0.3*600) && camera.getY() > 0){
						camera.updateY(-luffyData.getVelocity());
					}
					luffyData.setJVelocity(-60);
					luffyData.setJumping(true);
					luffyData.getShape().setY(luffyData.getY()+camera.getY());
					direction = "U"; 
				}
				
				if(!luffyData.jumping()){
					luffyData.setJVelocity(0);
					float y = luffyData.getY() + 5;
					//System.out.println(luffyData.getY());
					//System.out.println(y);
					//System.out.println("fallcheck: "+fallCheck);
					if ( luffyData.getY() > fallCheck )
					{
						falling = true;
						//System.out.println("falling");
					}
					else
					{
						falling = false;
						//System.out.println("not falling");
					}
					luffyData.setY(y);
					luffyData.getShape().setY(luffyData.getY()+camera.getY());					
				}
				//System.out.println(luffyData.getY());
				else
				{
					luffyData.setJVelocity(luffyData.getJVelocity() + luffyData.getGravity()*deltaTimeMS/100);
					if (luffyData.getJVelocity()*deltaTimeMS/100 < 5)
						luffyData.setY(luffyData.getY() + luffyData.getJVelocity()*deltaTimeMS/100);
					else
						luffyData.setY(luffyData.getY() + 5);
					//System.out.println(luffyData.getJVelocity()*deltaTimeMS/100);
					luffyData.getShape().setY(luffyData.getY()+camera.getY());
				}
				if (kbState[KeyEvent.VK_DOWN] && luffyData.getY() < 550) {
					float y = luffyData.getY() + luffyData.getVelocity();
					luffyData.setY(y);
					
					luffyData.getShape().setY(luffyData.getY());
					direction = "D";
					}
					
				
				for(int i = start_tileY; i < end_tileY;i++){
					for(int j = start_tileX; j < end_tileX; j++){
						int pos = bgDef.getTile(j,i);
			
						AABB bgBox = new AABB(j*bgDef.getTileW(), i*bgDef.getTileH(), 50, 50);
						if(bgDef.getTileCollision(pos) == true && AABBcheck(bgBox, luffyData.getShape()) && flag == 0){
							//System.out.println("Vertical Collision detected");
							flag = 1;
							float bot2 = bgBox.getY() + bgDef.getTileH();
							/**
							if(top1 < bot2 && luffyData.jumping()){
								luffyData.setY(luffyData.getY() + (bot2-top1));
							    //System.out.println(top1);
								//System.out.println(bot2);
							   	//System.out.println("back down");
							}
							*/
							//System.out.println(luffyData.getJVelocity());
							if (luffyData.getJVelocity() >= 0)
							{
								if(bot1 > bgBox.getY() && top1 +30 < bgBox.getY()){
									luffyData.setJumping(false);
									//System.out.println("Y pos " +luffyData.getY());
									fallCheck = luffyData.getY();
									luffyData.setJVelocity(0);
									luffyData.setY(luffyData.getY() - (bot1-bgBox.getY()));
									//System.out.println(bot1);
									//System.out.println(bgBox.getY());
									//System.out.println("back up");
								}
									 
								else{ 
									flag = 0;	
									if(luffyData.getJVelocity() > 0){
										luffyData.setJumping(true);
										if(luffyData.getJVelocity() > 5)
											luffyData.setJVelocity(5);
									}
								}
							
							}
							else
							{
								if(top1 < bot2 && bot1-30 > bot2  ){
									luffyData.setY(luffyData.getY() + (bot2-top1));
									luffyData.setJVelocity(0);
									float y = luffyData.getY() + luffyData.getVelocity();
									//System.out.println(luffyData.getY());
									luffyData.setY(y);
									//luffyData.setJumping(false);
								    //System.out.println(top1);
									//System.out.println(bot2);
								   	//System.out.println("back down");
								}
							}
							
							luffyData.getShape().setY(luffyData.getY()+camera.getY());
						}
						
					}
					//addVerCol(luffyData, dogBox, camera);
					
					//addVerCol(luffyData, lionBox2, camera);
					
				}
				
					
				
				if (kbState[KeyEvent.VK_SPACE ]) {
					if (!fire){
						fire = true;
						myBullet.setX(luffyData.getX()+50);
						myBullet.setY(luffyData.getY());
						myBullet.getShape().setX(luffyData.getX()+50);
						myBullet.getShape().setY(luffyData.getY());
						}
					
					}
				
				//update
				lastPhysicsFrameMs += physicsDeltaMs;
				}
			while(lastPhysicsFrameMs + physicsDeltaMs < curFrameMs);
			
			/**
			// Moving Camera
			if(kbState[KeyEvent.VK_W]){
				int temp = -5;
				float currY = camera.getY();
				if(currY > 0)
					camera.updateY(temp);
				}
			
			if(kbState[KeyEvent.VK_S]){
				int temp = 5;
				float currY = camera.getY();
				if(currY < (bgSize[1]-600))
					camera.updateY(temp);
				}
			
			if(kbState[KeyEvent.VK_A]){
				int temp = -5;
				float currX = camera.getX();
				if(currX > 0)
					camera.updateX(temp);
				}
			
			if(kbState[KeyEvent.VK_D]){
				int temp = 5;
				float currX = camera.getX();
				if(currX < (bgSize[0]-800))
					camera.updateX(temp);
				}
		
		 	*/
			luffyData.update(deltaTimeMS);
			String nameTGA = "luffy" + luffyData.getCurFrame() + ".tga";
			spriteTex = glTexImageTGAFile(gl, nameTGA, spriteSize);
			
			String nameTGA1 = "bb" + lionData.getCurFrame() + ".tga";
			
			//Update Lion
			trans ++;
			if (trans <= 500 )
			{
				lionTex = glTexImageTGAFile(gl, nameTGA1, lionSize);
			}
			else if (trans > 500 && trans < 1000)
			{
				lionTex = glTexImageTGAFile(gl, "lion.tga", lionSize);
			}
				
			else
				trans = 0;
					
			
			lionEnemy.update(deltaTimeMS, luffyData, bulletSize2, trans, 50);
			
			gl.glClearColor(0, 0, 0, 1);
			gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
			
			int startTileX = (int) Math.floor(camera.getX() / bgDef.getTileW());
			int startTileY = (int) Math.floor(camera.getY() / bgDef.getTileH());
			int endTileX = (int) Math.floor((camera.getX() + 800) / bgDef.getTileW());
			int endTileY = (int) Math.floor((camera.getY() + 600) / bgDef.getTileH());
			
			
			//Draw Background Tiles
			for(int i = startTileY ; i < endTileY;i++){
				for(int j = startTileX; j <= endTileX; j++){
					int pos = bgDef.getTile(j,i);
					if ( i < 11)
					{
						if (bgDef.getTileValue(pos) == 0)
						{
							glDrawSprite(gl, bgTex, (int)(j*bgDef.getTileW()-camera.getX()), (int)(i*bgDef.getTileH()-camera.getY()), 50, 50);
							//if (bgDef.getTileCollision(pos))
								//System.out.println("colli");
						}
						else
						{
							glDrawSprite(gl, bgTex4, (int)(j*bgDef.getTileW()-camera.getX()), (int)(i*bgDef.getTileH()-camera.getY()), 50, 50);
							//System.out.println(pos);
							//if (bgDef.getTileCollision(pos))
								//System.out.println("colli");
						}
					}
					else{
						if(bgDef.getTileValue(pos) == 1)
							glDrawSprite(gl, bgTex2, (int)(j*bgDef.getTileW()-camera.getX()), i*bgDef.getTileH(), 50, 50);
						else
							glDrawSprite(gl, bgTex5, (int)(j*bgDef.getTileW()-camera.getX()), i*bgDef.getTileH(), 50, 50);
						}
					/**
					else 
						if(bgDef.getTileValue(pos) == 1)
							glDrawSprite(gl, bgTex3, (int)(j*bgDef.getTileW()-camera.getX()), i*bgDef.getTileH(), 50, 50);
						else
							glDrawSprite(gl, bgTex5, (int)(j*bgDef.getTileW()-camera.getX()), i*bgDef.getTileH(), 50, 50);
					*/	
					}
				}

			
			
			// Draw cat sprite (with animation)
			//glDrawSprite(gl, spriteTex, (int)catData.getX()-(int)camera.getX(), (int)catData.getY()-(int)camera.getY(), catData.getWidth(), catData.getHeight());
			//System.out.println("cat : " + catData.getX());
			//System.out.println("camera : " + camera.getX());
			
			//Draw sprites with optimization
			cameraBox.setX(camera.getX());
	        cameraBox.setY(camera.getY());
	        
	        
			
	        /**
			if(AABBcheck(bulletBox, lionBox) ){
				if (lionHP > 0 ){
					myBullet.setCol(true);
					lionHP --;
				}
			}
			*/
			
			if(AABBcheck(bulletBox, lionBox2)){
				if (lion2HP > 0 ){
					myBullet.setCol(true);
					lion2HP --;
				}
			}
	        
			
			
			
			
			/**
			if(AABBcheck(cameraBox, lionBox) && lionHP > 0){
				glDrawSprite(gl, lionTex, 600-(int)camera.getX(), 500-(int)camera.getY(), lionSize[0], lionSize[1]);
			}
			*/
			//Camera check
			if(AABBcheck(cameraBox, jumperBox)){
				glDrawSprite(gl, jumperTex, 1600-(int)camera.getX(), 520-(int)camera.getY(), jumperSize[0], jumperSize[1]);
			}
			
			if(AABBcheck(cameraBox, jumperBox2)){
				glDrawSprite(gl, jumperTex, 4850-(int)camera.getX(), 520-(int)camera.getY(), jumperSize[0], jumperSize[1]);
			}
			
			if(AABBcheck(cameraBox, CPBox1)){
				glDrawSprite(gl, CPTex, 0-(int)camera.getX(), 450-(int)camera.getY(), CPSize[0], CPSize[1]);
			}
			
			if(AABBcheck(cameraBox, CPBox2)){
				glDrawSprite(gl, CPTex, 2000-(int)camera.getX(), 450-(int)camera.getY(), CPSize[0], CPSize[1]);
			}
			
			if(AABBcheck(cameraBox, CPBox3)){
				glDrawSprite(gl, CPTex, 4000-(int)camera.getX(), 450-(int)camera.getY(), CPSize[0], CPSize[1]);
			}
			
			if(AABBcheck(cameraBox, CPBox3)){
				glDrawSprite(gl, CPTex, 4000-(int)camera.getX(), 450-(int)camera.getY(), CPSize[0], CPSize[1]);
			}
			
			if(AABBcheck(cameraBox, TSBox)){
				glDrawSprite(gl, TSTex, 5500-(int)camera.getX(), 300-(int)camera.getY(), TSSize[0], TSSize[1]);
			}
			
			if(AABBcheck(cameraBox, namiBox) && !saveNami){
				glDrawSprite(gl, namiTex, 4750-(int)camera.getX(), 150-(int)camera.getY(), namiSize[0], namiSize[1]);
			}
			
			//Sprite Check
			if(AABBcheck(spriteBox, jumperBox)){
				luffyData.setJVelocity(-80);
				luffyData.getShape().setY(luffyData.getY()+camera.getY());	
			}
			
			if(AABBcheck(spriteBox, jumperBox2)){
				luffyData.setJVelocity(-80);
				luffyData.getShape().setY(luffyData.getY()+camera.getY());	
			}
			
			if(AABBcheck(spriteBox, namiBox)){
				saveNami = true;
			}
			
			if(AABBcheck(spriteBox, TSBox)){
				win = true;
			}
			
			
			//update robot
			gRobotEnemy1.update(deltaTimeMS, luffyData, bulletSize,0, 50);
			if(AABBcheck(cameraBox, gRobotBox1)){
				//System.out.println("SEE IT");
				
				glDrawSprite(gl, gRobotTex, 3150-(int)camera.getX(), 495-(int)camera.getY(), gRobotSize[0], gRobotSize[1]);
				bulletList = gRobotEnemy1.getBulletList();
				 if(!bulletList.isEmpty()){
					 //System.out.print("not empty");
				 	 for(int i = 0; i < bulletList.size(); i++){
				 		AnimationData b = bulletList.get(i);
				 		if (AABBcheck(spriteBox,b.getShape())){
				 			b.setCol(true);
				 		}

				 		if(AABBcheck(cameraBox, b.getShape())){
				 			if(b.isCol() == true){
				 				dead = true;
				 				bulletList.remove(i);
				 				i--;
				 			}
				 			else{
			         			float y = b.getY()+b.getVelocity()-30;
			             		b.setY(y);
			             		b.getShape().setY(y);
			             		//update bullet
			             		glDrawSprite(gl, bulletTex, (int)(b.getX()-camera.getX()), (int)(b.getY()-camera.getY()), bulletSize[0], bulletSize[1]);
			             				             		
				 			}
				 		}
				 		else{
				 			bulletList.remove(i);
				 			i--;
				 		}	
				 	}
				 }
			
			}
			
			gRobotEnemy2.update(deltaTimeMS, luffyData, bulletSize,0, 40);
			if(AABBcheck(cameraBox, gRobotBox2)){
				//System.out.println("SEE IT");
				
				glDrawSprite(gl, gRobotTex, 3450-(int)camera.getX(), 495-(int)camera.getY(), gRobotSize[0], gRobotSize[1]);
				bulletList = gRobotEnemy2.getBulletList();
				 if(!bulletList.isEmpty()){
					 //System.out.print("not empty");
				 	 for(int i = 0; i < bulletList.size(); i++){
				 		AnimationData b = bulletList.get(i);
				 		if (AABBcheck(spriteBox,b.getShape())){
				 			b.setCol(true);
				 		}

				 		if(AABBcheck(cameraBox, b.getShape())){
				 			if(b.isCol() == true){
				 				dead = true;
				 				bulletList.remove(i);
				 				i--;
				 			}
				 			else{
			         			float y = b.getY()+b.getVelocity()-20;
			             		b.setY(y);
			             		b.getShape().setY(y);
			             		//update bullet
			             		glDrawSprite(gl, bulletTex, (int)(b.getX()-camera.getX()), (int)(b.getY()-camera.getY()), bulletSize[0], bulletSize[1]);
			             				             		
				 			}
				 		}
				 		else{
				 			bulletList.remove(i);
				 			i--;
				 		}	
				 	}
				 }
			
			}
			
			fRobotEnemy1.update(deltaTimeMS, luffyData, bulletSize,0, 30);
			if(AABBcheck(cameraBox, fRobotBox1)){
				//System.out.println("SEE IT");
				
				glDrawSprite(gl, fRobotTex, 3300-(int)camera.getX(), 50-(int)camera.getY(), fRobotSize[0], fRobotSize[1]);
				bulletList = fRobotEnemy1.getBulletList();
				 if(!bulletList.isEmpty()){
					 //System.out.print("not empty");
				 	 for(int i = 0; i < bulletList.size(); i++){
				 		AnimationData b = bulletList.get(i);
				 		if (AABBcheck(spriteBox,b.getShape())){
				 			b.setCol(true);
				 		}

				 		if(AABBcheck(cameraBox, b.getShape())){
				 			if(b.isCol() == true){
				 				dead = true;
				 				bulletList.remove(i);
				 				i--;
				 			}
				 			else{
			         			float y = b.getY()+30;
			             		b.setY(y);
			             		b.getShape().setY(y);
			             		//update bullet
			             		glDrawSprite(gl, bulletTex, (int)(b.getX()-camera.getX()), (int)(b.getY()-camera.getY()), bulletSize[0], bulletSize[1]);
			             				             		
				 			}
				 		}
				 		else{
				 			bulletList.remove(i);
				 			i--;
				 		}	
				 	}
				 }
			
			}
			
			fRobotEnemy2.update(deltaTimeMS, luffyData, bulletSize,0, 35);
			if(AABBcheck(cameraBox, fRobotBox2)){
				//System.out.println("SEE IT");
				
				glDrawSprite(gl, fRobotTex, 3600-(int)camera.getX(), 50-(int)camera.getY(), fRobotSize[0], fRobotSize[1]);
				bulletList = fRobotEnemy2.getBulletList();
				 if(!bulletList.isEmpty()){
					 //System.out.print("not empty");
				 	 for(int i = 0; i < bulletList.size(); i++){
				 		AnimationData b = bulletList.get(i);
				 		if (AABBcheck(spriteBox,b.getShape())){
				 			b.setCol(true);
				 		}

				 		if(AABBcheck(cameraBox, b.getShape())){
				 			if(b.isCol() == true){
				 				dead = true;
				 				bulletList.remove(i);
				 				i--;
				 			}
				 			else{
			         			float y = b.getY()+7;
			             		b.setY(y);
			             		b.getShape().setY(y);
			             		//update bullet
			             		glDrawSprite(gl, bulletTex, (int)(b.getX()-camera.getX()), (int)(b.getY()-camera.getY()), bulletSize[0], bulletSize[1]);
			             				             		
				 			}
				 		}
				 		else{
				 			bulletList.remove(i);
				 			i--;
				 		}	
				 	}
				 }
			
			}
			
			fRobotEnemy3.update(deltaTimeMS, luffyData, bulletSize,0, 30);
			if(AABBcheck(cameraBox, fRobotBox3)){
				//System.out.println("SEE IT");
				
				glDrawSprite(gl, fRobotTex, 3670-(int)camera.getX(), 50-(int)camera.getY(), fRobotSize[0], fRobotSize[1]);
				bulletList = fRobotEnemy3.getBulletList();
				 if(!bulletList.isEmpty()){
					 //System.out.print("not empty");
				 	 for(int i = 0; i < bulletList.size(); i++){
				 		AnimationData b = bulletList.get(i);
				 		if (AABBcheck(spriteBox,b.getShape())){
				 			b.setCol(true);
				 		}

				 		if(AABBcheck(cameraBox, b.getShape())){
				 			if(b.isCol() == true){
				 				dead = true;
				 				bulletList.remove(i);
				 				i--;
				 			}
				 			else{
			         			float y = b.getY()+7;
			             		b.setY(y);
			             		b.getShape().setY(y);
			             		//update bullet
			             		glDrawSprite(gl, bulletTex, (int)(b.getX()-camera.getX()), (int)(b.getY()-camera.getY()), bulletSize[0], bulletSize[1]);
			             				             		
				 			}
				 		}
				 		else{
				 			bulletList.remove(i);
				 			i--;
				 		}	
				 	}
				 }
			
			}
			
			fRobotEnemy4.update(deltaTimeMS, luffyData, bulletSize,0, 30);
			if(AABBcheck(cameraBox, fRobotBox4)){
				//System.out.println("SEE IT");
				
				glDrawSprite(gl, fRobotTex, 4500-(int)camera.getX(), 150-(int)camera.getY(), fRobotSize[0], fRobotSize[1]);
				bulletList = fRobotEnemy4.getBulletList();
				 if(!bulletList.isEmpty()){
					 //System.out.print("not empty");
				 	 for(int i = 0; i < bulletList.size(); i++){
				 		AnimationData b = bulletList.get(i);
				 		if (AABBcheck(spriteBox,b.getShape())){
				 			b.setCol(true);
				 		}

				 		if(AABBcheck(cameraBox, b.getShape())){
				 			if(b.isCol() == true){
				 				dead = true;
				 				bulletList.remove(i);
				 				i--;
				 			}
				 			else{
			         			float y = b.getY()+7;
			             		b.setY(y);
			             		b.getShape().setY(y);
			             		//update bullet
			             		glDrawSprite(gl, bulletTex, (int)(b.getX()-camera.getX()), (int)(b.getY()-camera.getY()), bulletSize[0], bulletSize[1]);
			             				             		
				 			}
				 		}
				 		else{
				 			bulletList.remove(i);
				 			i--;
				 		}	
				 	}
				 }
			
			}
			
			if(AABBcheck(cameraBox, lionBox2)){
				if (lion2HP > 0)
					glDrawSprite(gl, lionTex, (int)(lionData.getX()-camera.getX()), (int)(lionData.getY()-camera.getY()), lionSize[0], lionSize[1]);
				else
					lionBox2 = new AABB(0,0,0,0);
				//lionBox2 = new AABB((int)(lionData.getX()-camera.getX()), (int)(lionData.getY()-camera.getY()), lionSize[0], lionSize[1]);
				 //lionData.setShape(lionBox2);
			 }
			
			//marineData.update(deltaTimeMS);
			String marineTGA = "marine" + marineData.getCurFrame() + ".tga";
			marineTex = glTexImageTGAFile(gl, marineTGA, marineSize);
			marineEnemy.update(deltaTimeMS, luffyData, bulletSize3,0, 70);
			marineEnemy2.update(deltaTimeMS, luffyData, bulletSize3,0, 100);
			
			if(AABBcheck(cameraBox, marineBox)){
				if (marineHP > 0)
				{
					glDrawSprite(gl, marineTex, (int)(marineData.getX()-camera.getX()), (int)(marineData.getY()-camera.getY()), marineSize[0], marineSize[1]);
					bulletList = marineEnemy.getBulletList();
					 if(!bulletList.isEmpty()){
						 //System.out.print("not empty");
					 	 for(int i = 0; i < bulletList.size(); i++){
					 		AnimationData b = bulletList.get(i);
					 		if (AABBcheck(spriteBox,b.getShape())){
					 			b.setCol(true);
					 		}

					 		if(AABBcheck(cameraBox, b.getShape())){
					 			if(b.isCol() == true){
					 				dead = true;
					 				bulletList.remove(i);
					 				i--;
					 			}
					 			else{
				         			float x = b.getX()+b.getVelocity();
				             		b.setX(x);
				             		b.getShape().setX(x);
				             		//update bullet
				             		glDrawSprite(gl, bulletTex3, (int)(b.getX()-camera.getX()), (int)(b.getY()-camera.getY()), bulletSize3[0], bulletSize3[1]);
				             		
				             		
					 			}
					 		}
					 		else{
					 			bulletList.remove(i);
					 			i--;
					 		}	
					 	}
					 }
				}
				else
					lionBox2 = new AABB(0,0,0,0);
			 }
			
			if(AABBcheck(cameraBox, marineBox2)){
				if (marineHP2 > 0)
				{
					glDrawSprite(gl, marineTex, (int)(marineData2.getX()-camera.getX()), (int)(marineData2.getY()-camera.getY()), marineSize[0], marineSize[1]);
					bulletList = marineEnemy2.getBulletList();
					 if(!bulletList.isEmpty()){
						 //System.out.print("not empty");
					 	 for(int i = 0; i < bulletList.size(); i++){
					 		AnimationData b = bulletList.get(i);
					 		if (AABBcheck(spriteBox,b.getShape())){
					 			b.setCol(true);
					 		}

					 		if(AABBcheck(cameraBox, b.getShape())){
					 			if(b.isCol() == true){
					 				dead = true;
					 				bulletList.remove(i);
					 				i--;
					 			}
					 			else{
				         			float x = b.getX()+b.getVelocity();
				             		b.setX(x);
				             		b.getShape().setX(x);
				             		//update bullet
				             		glDrawSprite(gl, bulletTex3, (int)(b.getX()-camera.getX()), (int)(b.getY()-camera.getY()), bulletSize3[0], bulletSize3[1]);
				             		
				             		
					 			}
					 		}
					 		else{
					 			bulletList.remove(i);
					 			i--;
					 		}	
					 	}
					 }
				}
				else
					lionBox2 = new AABB(0,0,0,0);
			 }
			
			if (fire){
				if(AABBcheck(cameraBox, bulletBox) && !myBullet.isCol()){
					//System.out.print("True\n");
	     			float x = myBullet.getX()+myBullet.getVelocity();
	     			myBullet.setX(x);
	     			myBullet.getShape().setX(x);
	         		glDrawSprite(gl, bulletTex, (int)(myBullet.getX()-camera.getX()), (int)(myBullet.getY()-camera.getY()), myBullet.getWidth(), myBullet.getHeight());
	         		//System.out.print(myBullet.getX()+"\n");
				}
				else{
					//System.out.print("False");
					fire = false;
					myBullet.setX(luffyData.getX()+50);
					myBullet.setY(luffyData.getY());
					myBullet.getShape().setX(luffyData.getX()+50);
					myBullet.getShape().setY(luffyData.getY());
					myBullet.setCol(false);
					//System.out.print(myBullet.getX()+"\n");
				}
				
			}
			
			//if (fire)
				//System.out.print("True");
			/**
			if (fire == true){
				
				glDrawSprite(gl, spriteTex, (int)catData.getX()-(int)camera.getX()+100, (int)catData.getY()-(int)camera.getY()+100, catData.getWidth(), catData.getHeight());
				//fire = false;
			}
			*/
			if (lion2HP > 0)
			{
				bulletList = lionEnemy.getBulletList();
				 if(!bulletList.isEmpty()){
					 //System.out.print("not empty");
				 	 for(int i = 0; i < bulletList.size(); i++){
				 		AnimationData b = bulletList.get(i);
				 		if (AABBcheck(spriteBox,b.getShape())){
				 			b.setCol(true);
				 		}

				 		if(AABBcheck(cameraBox, b.getShape())){
				 			if(b.isCol() == true){
				 				dead = true;
				 				bulletList.remove(i);
				 				i--;
				 			}
				 			else{
			         			float x = b.getX()+b.getVelocity();
			             		b.setX(x);
			             		b.getShape().setX(x);
			             		//update bullet
			             		if (b.getDef().getName() == "fire")
			             			glDrawSprite(gl, bulletTex2, (int)(b.getX()-camera.getX()), (int)(b.getY()-camera.getY()), bulletSize2[0], bulletSize2[1]);
			             		
			             		else if (b.getDef().getName() == "bh")
		             				glDrawSprite(gl, bhTex, (int)(b.getX()-camera.getX()), (int)(b.getY()-camera.getY()), bhSize[0], bhSize[1]);
		             			else if (b.getDef().getName() == "eq")
		             				glDrawSprite(gl, eqTex, (int)(b.getX()-camera.getX()), (int)(b.getY()-camera.getY()), eqSize[0], eqSize[1]);
			             		
				 			}
				 		}
				 		else{
				 			bulletList.remove(i);
				 			i--;
				 		}	
				 	}
				 }
			}
			
			glDrawSprite(gl, spriteTex, (int)luffyData.getX()-(int)camera.getX(), (int)luffyData.getY()-(int)camera.getY(), luffyData.getWidth(), luffyData.getHeight());
			
			
			//Respawn
			//System.out.println(luffyData.getY());
			//System.out.println(luffyData.getX());
			if(win && saveNami){
				glDrawSprite(gl, WinTex, 0, 0, WinSize[0], WinSize[1]);
				//System.out.println("You Win");
			}
			else
			{
				if (luffyData.getX() > maxX)
					maxX = luffyData.getX();
				if (maxX >= 2100 && maxX < 4100)
					checkPoint = 2000;
				else if (maxX >= 4100)
					checkPoint = 4000;
				//System.out.println(checkPoint);
				if (luffyData.getY() > 600 || dead)
				{
					if (checkPoint == 0)
						camera.setX(checkPoint);
					else
						camera.setX(checkPoint-200);
					luffyData.setX(checkPoint);
					luffyData.getShape().setX(luffyData.getX());
					luffyData.setY(500);
					luffyData.getShape().setY(luffyData.getY());
					dead = false;
					win = false;
					saveNami = false;
				}
			}
			
		}
	}

	// Load a file into an OpenGL texture and return that texture.
	public static int glTexImageTGAFile(GL2 gl, String filename, int[] out_size) {
		final int BPP = 4;

		DataInputStream file = null;
		try {
			// Open the file.
			file = new DataInputStream(new FileInputStream(filename));
			} 
		catch (FileNotFoundException ex) {
			System.err.format("File: %s -- Could not open for reading.", filename);
			return 0;
			}

		try {
			// Skip first two bytes of data we don't need.
			file.skipBytes(2);

			// Read in the image type.  For our purposes the image type
			// should be either a 2 or a 3.
			int imageTypeCode = file.readByte();
			if (imageTypeCode != 2 && imageTypeCode != 3) {
				file.close();
				System.err.format("File: %s -- Unsupported TGA type: %d", filename, imageTypeCode);
				return 0;
				}

			// Skip 9 bytes of data we don't need.
			file.skipBytes(9);

			int imageWidth = Short.reverseBytes(file.readShort());
			int imageHeight = Short.reverseBytes(file.readShort());
			int bitCount = file.readByte();
			file.skipBytes(1);

			// Allocate space for the image data and read it in.
			byte[] bytes = new byte[imageWidth * imageHeight * BPP];

			// Read in data.
			if (bitCount == 32) {
				for (int it = 0; it < imageWidth * imageHeight; ++it) {
					bytes[it * BPP + 0] = file.readByte();
					bytes[it * BPP + 1] = file.readByte();
					bytes[it * BPP + 2] = file.readByte();
					bytes[it * BPP + 3] = file.readByte();
					}
				} 
			else {
				for (int it = 0; it < imageWidth * imageHeight; ++it) {
					bytes[it * BPP + 0] = file.readByte();
					bytes[it * BPP + 1] = file.readByte();
					bytes[it * BPP + 2] = file.readByte();
					bytes[it * BPP + 3] = -1;
					}
				}

			file.close();

			// Load into OpenGL
			int[] texArray = new int[1];
			gl.glGenTextures(1, texArray, 0);
			int tex = texArray[0];
			gl.glBindTexture(GL2.GL_TEXTURE_2D, tex);
			gl.glTexImage2D(
					GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, imageWidth, imageHeight, 0,
					GL2.GL_BGRA, GL2.GL_UNSIGNED_BYTE, ByteBuffer.wrap(bytes));
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);

			out_size[0] = imageWidth;
			out_size[1] = imageHeight;
			return tex;
			}
		catch (IOException ex) {
			System.err.format("File: %s -- Unexpected end of file.", filename);
			return 0;
			}
		}
	
	public static void glDrawTileBackground(GL2 gl, int tex, float[] loc, int x, int y, int w, int h){
		 gl.glBindTexture(GL2.GL_TEXTURE_2D, tex);
		 gl.glBegin(GL2.GL_QUADS);
		 {
 	            gl.glColor3ub((byte)-1, (byte)-1, (byte)-1);
 	            gl.glTexCoord2f(loc[0], loc[2]);
 	            //gl.glTexCoord2f(0, 1);
 	            gl.glVertex2i(x, y);
 	            gl.glTexCoord2f(loc[0]+loc[1], loc[2]);
 	            //gl.glTexCoord2f(1, 1);
 	            gl.glVertex2i(x + w, y);
 	            gl.glTexCoord2f(loc[0]+loc[1], loc[2]-loc[3]);
 	            //gl.glTexCoord2f(1, 0);
 	            gl.glVertex2i(x + w, y + h);
 	            gl.glTexCoord2f(loc[0], loc[2]-loc[3]);
 	            //gl.glTexCoord2f(0, 0);
 	            gl.glVertex2i(x, y + h);
 	            }
		 gl.glEnd();
		 }

	public static void glDrawSprite(GL2 gl, int tex, int x, int y, int w, int h) {
		gl.glBindTexture(GL2.GL_TEXTURE_2D, tex);
		gl.glBegin(GL2.GL_QUADS);
		{
            gl.glColor3ub((byte)-1, (byte)-1, (byte)-1);
            gl.glTexCoord2f(0,1);
            gl.glVertex2i(x, y);
            gl.glTexCoord2f(1,1);
            gl.glVertex2i(x + w, y);
            gl.glTexCoord2f(1,0);
            gl.glVertex2i(x + w, y + h);
            gl.glTexCoord2f(0,0);
            gl.glVertex2i(x, y + h);
		}
		gl.glEnd();
	}
	
	//Check if sprite is on screen
    public static boolean AABBcheck(AABB box1, AABB box2){
		boolean check = true;
		// box1 to the right
		if(box1.getX() > box2.getX() + box2.getW()){
			check = false;
		}
		// box1 to the left
		if(box1.getX() + box1.getW() < box2.getX()){
			check = false;
		}
		// box1 below
		if(box1.getY() > box2.getY() + box2.getH()){
			check = false;
		}
		// box1 above
		if(box1.getY() + box1.getH() < box2.getY()){
			check = false;
		}		
		return check;
	}
    
    public static void addHorCol(AnimationData a, AABB box2, Camera b)
    {
    	/**
    	if(AABBcheck(a.getShape(), box2)){
			float left1 = a.getShape().getX();
			float right1 = a.getShape().getX() + box2.getW();
			float left2 = box2.getX();
			float right2 = box2.getX() + box2.getW();
			
			if(right1 > left2 && (direction == "R")){
				a.setX(a.getShape().getX() - (right1 - left2));	
			}
			else if(left1 < right2 && (direction == "L")){
				a.setX(a.getShape().getX() + (right2 - left1));
			}
			a.getShape().setX(a.getX()+a.getX());
			System.out.println("Saw a sprite");
		}
		*/
    	
    	if(AABBcheck(a.getShape(), box2) && flag == 0){
    		float right1 = a.getShape().getX() + a.getShape().getW();
			float left1 = a.getShape().getX() ;
			float right2 = box2.getX() + box2.getW() ;
			float left2 = box2.getX() ;
    		
			
			if(direction == "R" && right1 > left2){
				flag = 1;
				//a.setX(box2.getX() - a.getShape().getW() -1);
				a.setX(a.getX() - (right1 - left2)-1);
				}
			else if (direction == "L" && left1 < right2){
				flag = 1;
				//a.setX(box2.getX()+box2.getW()+1);
				a.setX(a.getX() + (right2 - left1) +1);
				}
			a.getShape().setX(a.getX()+b.getX());
			flag = 0;
    	}
    	
    	
    }
    public static void addVerCol(AnimationData a, AABB box2, Camera b)
    {
    	float top1 = a.getShape().getY();
		float bot1 = a.getShape().getY() + a.getShape().getH();
		float top2 = box2.getY() ;
		float bot2 = box2.getY() + box2.getH() ;
		if(AABBcheck(a.getShape(), box2) && flag == 0)
		{
			if(direction == "U" && top1 > bot2){
				flag = 1;
				//a.setY(box2.getY() + box2.getH());
				a.setY(a.getY() + (bot2-top1)+1);
				}
			else if ( direction == "D" && bot1 > top2){
				//System.out.println("Down");
				//System.out.println(a.getY());
				//System.out.println(box2.getY());
				//System.out.println(top2);
				//a.setY(box2.getY() - (bot1-top2) );
				flag = 1;
				a.setY(a.getY() - (bot1-top2)-1);
				//System.out.println("Down");
				//System.out.println(a.getY());
				//System.out.println(a.getShape().getH());
				//System.out.println(box2.getY());
				//System.out.println(top2);
				
				
				}
			a.getShape().setY(a.getY()+b.getY());
			flag = 0;
		}
		
    }
}
