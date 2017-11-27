

public class AnimationData {
	AnimationDef def;
	int curFrame;
	float secsUntilNextFrame;
	float velocity;
	float acc;
	float x;
	float y;
	AABB shape;
	int w;
	int h;
	boolean col;
	boolean jump;
	float jVelocity;
	float gravity = (float)9.8;
	
	
	public AnimationData(int startFrame, AnimationDef def, float secs, float pos[], int size[]){
		this.def = def;
		curFrame = startFrame;
		secsUntilNextFrame = secs;
		velocity = 3;
		x = pos[0];
		y = pos[1];
		w = size[0];
		h = size[1];
		jump = false;
		jVelocity = 0;
		}
	
	public void update(float deltaTime){
		if(secsUntilNextFrame - deltaTime <= 0){
			// update curFrame to nextFrame
			// update time of the nextFrame
			if(curFrame < def.getFrames().length - 1)
				curFrame++;
			else curFrame = 0;
			secsUntilNextFrame = def.getFrames()[curFrame].getFrameDuration();		
			}
		else
			// update secs until next frame
			secsUntilNextFrame -= deltaTime;
		}
	
	public int getCurFrame(){return curFrame;}
	public float getSecsUntilNextFrame(){return secsUntilNextFrame;}
	public float getVelocity(){return velocity;}
	public float getAcc(){return acc;}
	public float getX(){return x;}
	public float getY(){return y;}
	public int getWidth(){return w;}
	public int getHeight(){return h;}
	public AABB getShape(){return shape;}
	public float[] getShootingPosition(){float arr[] = {x,y+50}; return arr;}
	public AnimationDef getDef(){return def;}
	public void setX(float x){this.x = x;}
	public void setY(float y){this.y = y;}
	public void setWidth(int w){this.w = w;}
	public void setHeight(int h){this.h = h;}
	public void setVelocity(float v){velocity = v;}
	public void setShape(AABB s){shape = s;}
	public void setCol(boolean c){col = c;}
	public boolean isCol(){return col;}
	public void setJumping(boolean b){jump = b;}
	public boolean jumping(){return jump;}
	public void setJVelocity(float value){jVelocity = value;}
	public float getJVelocity(){return jVelocity;}
	public float getGravity(){return gravity;}

}

