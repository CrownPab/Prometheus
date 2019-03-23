package prometheus.maps;

import static prometheus.constants.GlobalConstants.SCENE_HEIGHT;
import static prometheus.constants.GlobalConstants.SCENE_WIDTH;

import java.util.ArrayList;

import javax.swing.text.html.parser.Entity;

import prometheus.entity.staticobjects.AcidPool;
import prometheus.entity.staticobjects.Wall;
import prometheus.scenes.Sandbox;

public class Map
{
	static int numEntities = 0;
	static int mobCount = 0;
	Map()
	{
		
	}
	public static void Map1(){
		numEntities = 0;
		mobCount = 0;
		ArrayList<Wall> walls = new ArrayList<Wall>();
    	ArrayList<AcidPool> pools = new ArrayList<AcidPool>();
    	ArrayList<Entity> mobs = new ArrayList<Entity>();
		
    	//Border
		for(int i = 0; i < SCENE_WIDTH; i += 32){
    		for(int j = 0; j < SCENE_HEIGHT; j += 32){
    			if(i == 0 || i + 33 > SCENE_HEIGHT || j == 0 || j + 33 > SCENE_WIDTH) {
    				walls.add(new Wall(i, j));
    			}
    		}
    	}
		//Checkerboard
		for(int i = 48; i < SCENE_WIDTH-32; i += 128){
    		for(int j = 48; j < SCENE_HEIGHT-32; j += 128){
    				walls.add(new Wall(i, j));
    		}
    	}
		
    	for(Wall wall : walls) {
    		Sandbox.addEntityToGame(wall);
    		numEntities++;
    	}
    	
    	for(AcidPool wall : pools) {
    		Sandbox.addEntityToGame(wall);
    		numEntities++;
    	}
	}
	
	public static void Map2() {
		ArrayList<Wall> walls = new ArrayList<Wall>();
    	ArrayList<AcidPool> pools = new ArrayList<AcidPool>();
    	ArrayList<Entity> mobs = new ArrayList<Entity>();
    	
    	//Border
    	for(int i = 0; i < SCENE_WIDTH; i += 32){
    	    for(int j = 0; j < SCENE_HEIGHT; j += 32){
    	    	if(i == 0 || i + 33 > SCENE_HEIGHT || j == 0 || j + 33 > SCENE_WIDTH) {
    	    		walls.add(new Wall(i, j));
    	    	}
    	    }
    	 }
    	//Wall blocks
    	for(int i = 96; i < 192; i += 32){
    		for(int j = 96; j < 192; j += 32){
    				walls.add(new Wall(i, j));
    		}
    	}
    	for(int i = 448	; i < 544; i += 32){
    		for(int j = 448; j < 544; j += 32){
    				walls.add(new Wall(i, j));
    		}
    	}
    	for(int i = 448; i < 544; i += 32){
    		for(int j = 96; j < 192; j += 32){
    				walls.add(new Wall(i, j));
    		}
    	}
    	for(int i = 96; i < 192; i += 32){
    		for(int j = 448; j < 544; j += 32){
    				walls.add(new Wall(i, j));
    		}
    	}
    	//Middle Acid
    	for(int i = 256; i < 384; i += 32){
    		for(int j = 256; j < 384; j += 32){
    				if(!(i>287&&i<351&&(j>287&&j<351)))
    				{
    					pools.add(new AcidPool(i, j));
    				}
    		}
    	}
    	for(Wall wall : walls) {
    		Sandbox.addEntityToGame(wall);
    		numEntities++;
    	}
    	
    	for(AcidPool wall : pools) {
    		Sandbox.addEntityToGame(wall);
    		numEntities++;
    	}
		
	}
	public void Map3() {
		ArrayList<Wall> walls = new ArrayList<Wall>();
    	ArrayList<AcidPool> pools = new ArrayList<AcidPool>();
    	ArrayList<Entity> mobs = new ArrayList<Entity>();
	}
	
	public static void checkLevelEnd()
	{
		
	}
}