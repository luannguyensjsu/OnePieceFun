import java.util.ArrayList;
import java.util.Random;


public class Enemy {
	AnimationData a;
	AnimationData target;
	AnimationData bullet;
	AnimationData bBullet;
	AnimationData eBullet;
	int ftime;
	ArrayList<AnimationData> bulletList = new ArrayList<AnimationData>();
	int speed;
	int count = 0;
	
	
	public Enemy(AnimationData x){
		a = x;
	}
	
	public void update(float deltaTime, AnimationData target, int[] bulletSize, int time , int sSpeed){
		a.update(deltaTime);
		this.target = target;
		
		//Logic
		/**
		float deltaY;
		if(a.getY() < target.getY()){
			deltaY = Math.min(a.getVelocity(), target.getY() - a.getY());
		}
		else 
			deltaY = - (Math.min(a.getVelocity(), a.getY() - target.getY()));
		
		//System.out.println(deltaY);
		a.setY(a.getY() + ( deltaY * deltaTime / 10));		
		//System.out.print(a.getCurFrame()+"\n");
		*/
		count++;
		if (count > sSpeed)
		{
			createBullet(bulletSize);
			createBhBullet(bulletSize);
			createEqBullet(bulletSize);
			if ( time <= 500)
			{
				Random rand = new Random();
     			int chance = rand.nextInt()%2;
     			//System.out.println(chance);
     			if (chance == 0)
     				bulletList.add(bBullet);
     			else
     				bulletList.add(eBullet);
			}
			else
			{
				bulletList.add(bullet);
			}
			count = 0;
		}
		//System.out.print("Create Bullet");
		 
	}

	
	public void createBullet(int [] bulletSize){
		 FrameDef[] fireFrames = new FrameDef[1];
		 float frameTime = 10000;
		 FrameDef frame = new FrameDef(0, frameTime);
		 fireFrames[0] = frame;
		 AnimationDef fire = new AnimationDef("fire", fireFrames);
		 bullet = new AnimationData(0, fire, fireFrames[0].getFrameDuration(), new float[]{a.getX(), a.getY()}, bulletSize);
		 bullet.setVelocity(-4);
		 AABB fireBox = new AABB(bullet.getX(), bullet.getY(), bulletSize[0], bulletSize[1]);
		 bullet.setShape(fireBox);
	}
	
	public void createBhBullet(int[] bSize){
		 FrameDef[] bFrames = new FrameDef[1];
		 float frameTime = 10000;
		 FrameDef frame = new FrameDef(0, frameTime);
		 bFrames[0] = frame;
		 AnimationDef b = new AnimationDef("bh", bFrames);
		 bBullet = new AnimationData(0, b , bFrames[0].getFrameDuration(), new float[]{a.getX(), a.getY()+10}, bSize);
		 bBullet.setVelocity(-4);
		 AABB bhBox = new AABB(bBullet.getX(), bBullet.getY(), bSize[0], bSize[1]);
		 bBullet.setShape(bhBox);
	}
	
	public void createEqBullet(int[] eSize){
		 FrameDef[] eFrames = new FrameDef[1];
		 float frameTime = 10000;
		 FrameDef frame = new FrameDef(0, frameTime);
		 eFrames[0] = frame;
		 AnimationDef e = new AnimationDef("eq", eFrames);
		 eBullet = new AnimationData(0, e , eFrames[0].getFrameDuration(), new float[]{a.getX(), a.getY()+10}, eSize);
		 eBullet.setVelocity(-4);
		 AABB eqBox = new AABB(eBullet.getX(), eBullet.getY(), eSize[0], eSize[1]);
		 eBullet.setShape(eqBox);
	}
	
	public int getSpeed(){ return speed;}
	public AnimationData getTarget(){return target;}
	public AnimationData getAnim(){return a;}
	public ArrayList<AnimationData> getBulletList(){return bulletList;}
	
	
	public void setSpeed(int speed){this.speed = speed;}
	public void setTarget(AnimationData target){this.target = target;}
	public void setBulletList(ArrayList<AnimationData> list){bulletList = list;}
}
