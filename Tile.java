
public class Tile {
	int image;
	boolean col;
	
	public Tile(){
		image = 0;
		col = false;
		}
	
	public Tile(int image, boolean col){
		this.image = image;
		this.col = col;
		}
	
	public int getImage(){
		return image;
		}
	
	public void setImage(int img){
		image = img;
		}
	
	public boolean isCol(){
		return col;
		}
	
	public void setCol(boolean c){
		col = c;
		}
}
