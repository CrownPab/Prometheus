package prometheus.maps;

import static prometheus.constants.GlobalConstants.SCENE_HEIGHT;
import static prometheus.constants.GlobalConstants.SCENE_WIDTH;

import java.util.ArrayList;
import java.util.Random;

import prometheus.enemies.Runner;
import prometheus.enemies.Wizard;
import prometheus.entity.staticobjects.AcidPool;
import prometheus.entity.staticobjects.Wall;
import prometheus.scenes.Sandbox;

public class Map
{
	public static int mobCount = 0;
	public static int wave = 1;
	public static int lvl = 1;
	static int[][] mapLayout = new int[20][20];
	
	public static void Map1(){
		mobCount = 0;
		wave = 1;
		lvl = 1;
		ArrayList<Wall> walls = new ArrayList<Wall>();
		mapLayout = new int[20][20];
		
		//Make new player in safe location
		Sandbox.sandboxPlayer.init(340,340);
		
    	//Border
		for(int i = 0; i < SCENE_WIDTH; i += 32){
    		for(int j = 0; j < SCENE_HEIGHT; j += 32){
    			if(i == 0 || i + 33 > SCENE_HEIGHT || j == 0 || j + 33 > SCENE_WIDTH) {
    				walls.add(new Wall(i, j));
    				if(i==0 && j == 0)
    				{
    					mapLayout[0][0] = 1;
    				}else if(i ==0)
    				{
    					mapLayout[0][j/32] = 1;
    				}else if(j == 0)
    				{
    					mapLayout[i/32][0] = 1;
    				}
    			}
    		}
    	}
		//Checkerboard
		for(int i = 48; i < SCENE_WIDTH-32; i += 128){
    		for(int j = 48; j < SCENE_HEIGHT-32; j += 128){
    				walls.add(new Wall(i, j));
    				mapLayout[i/32][j/32] = 1;
    		}
    	}
		
    	for(Wall wall : walls) {
    		Sandbox.addEntityToGame(wall);
    	}
	}
	public static void Map2() {
		ArrayList<Wall> walls = new ArrayList<Wall>();
    	ArrayList<AcidPool> pools = new ArrayList<AcidPool>();
    	mapLayout = new int[20][20];
    	mobCount = 0;
		wave = 1;
		lvl = 2;
    	
		//Clear Map
		for(int i = Sandbox.getEntities().size()-1;i>=1;i--)
		{
			Sandbox.getEntities().remove(i);
		}
		
		//Make player in safe location
		Sandbox.sandboxPlayer.init(320,320);
		
    	//Border
    	for(int i = 0; i < SCENE_WIDTH; i += 32){
    	    for(int j = 0; j < SCENE_HEIGHT; j += 32){
    	    	if(i == 0 || i + 33 > SCENE_HEIGHT || j == 0 || j + 33 > SCENE_WIDTH) {
    	    		walls.add(new Wall(i, j));
    				if(i==0 && j == 0)
    				{
    					mapLayout[0][0] = 1;
    				}else if(i ==0)
    				{
    					mapLayout[0][j/32] = 1;
    				}else if(j == 0)
    				{
    					mapLayout[i/32][0] = 1;
    				}
    	    	}
    	    }
    	 }
    	//Wall blocks
    	for(int i = 96; i < 192; i += 32){
    		for(int j = 96; j < 192; j += 32){
    				walls.add(new Wall(i, j));
    				mapLayout[i/32][j/32] = 1;
    		}
    	}
    	for(int i = 448	; i < 544; i += 32){
    		for(int j = 448; j < 544; j += 32){
    				walls.add(new Wall(i, j));
    				mapLayout[i/32][j/32] = 1;
    		}
    	}
    	for(int i = 448; i < 544; i += 32){
    		for(int j = 96; j < 192; j += 32){
    				walls.add(new Wall(i, j));
    				mapLayout[i/32][j/32] = 1;
    		}
    	}
    	for(int i = 96; i < 192; i += 32){
    		for(int j = 448; j < 544; j += 32){
    				walls.add(new Wall(i, j));
    				mapLayout[i/32][j/32] = 1;
    		}
    	}
    	//Middle Acid
    	for(int i = 256; i < 384; i += 32){
    		for(int j = 256; j < 384; j += 32){
    				if(!(i>287&&i<351&&(j>287&&j<351)))
    				{
    					pools.add(new AcidPool(i, j));
    					mapLayout[i/32][j/32] = 1;
    				}
    		}
    	}
    	for(Wall wall : walls) {
    		Sandbox.addEntityToGame(wall);
    	}
    	
    	for(AcidPool wall : pools) {
    		Sandbox.addEntityToGame(wall);
    	}
		
	}
	public static void Map3() {
		ArrayList<Wall> walls = new ArrayList<Wall>();
    	ArrayList<AcidPool> pools = new ArrayList<AcidPool>();
    	mapLayout = new int[20][20];
    	mobCount = 0;
		wave = 1;
		lvl = 3;
    	
		//Make player in safe location
		Sandbox.sandboxPlayer.init(320,200);
				
		
    	//Clear Map
    	System.out.println(Sandbox.getEntities().size());
    	for(int i = Sandbox.getEntities().size()-1;i>=1;i--)
    	{
    		Sandbox.getEntities().remove(i);
    	}
    	//Border
    	for(int i = 0; i < SCENE_WIDTH; i += 32){
    	    for(int j = 0; j < SCENE_HEIGHT; j += 32){
    	    	if(i == 0 || i + 33 > SCENE_HEIGHT || j == 0 || j + 33 > SCENE_WIDTH) {
    	    		walls.add(new Wall(i, j));
    				if(i==0 && j == 0)
    				{
    					mapLayout[0][0] = 1;
    				}else if(i ==0)
    				{
    					mapLayout[0][j/32] = 1;
    				}else if(j == 0)
    				{
    					mapLayout[i/32][0] = 1;
    				}
    	    	}
    	    }
    	 }
    	//Wall blocks
    	for(int i = 160; i < 224; i += 32){
    		for(int j = 32; j < 608; j += 32){
    			if(!(j==288 || j==320))
    			{
    				walls.add(new Wall(i, j));
    				mapLayout[i/32][j/32] = 1;
    			}
    		}
    	}
    	//Wall blocks
    	for(int i = 416; i < 480; i += 32){
    		for(int j = 32; j < 608; j += 32){
    			if(!(j==288 || j==320))
    			{
    				walls.add(new Wall(i, j));
    				mapLayout[i/32][j/32] = 1;
    			}
    		}
    	}
    	//Acid blocks
    	for(int i = 288; i < 352; i += 32){
    		for(int j = 288; j < 352; j += 32){
    				walls.add(new Wall(i, j));
    				mapLayout[i/32][j/32] = 1;
    		}
    	}
    	for(int i = 32; i < 608; i += 32){
    		for(int j = 32; j < 608; j += 32){
    			if(i == 32||i==576) {
    				pools.add(new AcidPool(i, j));
    				mapLayout[i/32][j/32] = 1;
    			}
    			if((i == 128||i==224||i == 384||i==480) && !(j==288 || j==320)) {
    				pools.add(new AcidPool(i, j));
    				mapLayout[i/32][j/32] = 1;
    			}
    			if((i == 64||i==96||i == 256||i==288||i == 320||i==352||i == 512||i==544) && (j==32 || j==576)) {
    				pools.add(new AcidPool(i,j));
    				mapLayout[i/32][j/32] = 1;
    			}
    		}
    	}
    	for(Wall wall : walls) {
    		Sandbox.addEntityToGame(wall);
    	}
    	for(AcidPool wall : pools) {
    		Sandbox.addEntityToGame(wall);
    	}
    	
    	
	}
	public static void Map4() {
		ArrayList<Wall> walls = new ArrayList<Wall>();
    	ArrayList<AcidPool> pools = new ArrayList<AcidPool>();
    	mapLayout = new int[20][20];
    	mobCount = 0;
		wave = 1;
		lvl = 4;
    	
		//Make player in safe location
		Sandbox.sandboxPlayer.init(320,320);
		
    	//Clear Map
    	System.out.println(Sandbox.getEntities().size());
    	for(int i = Sandbox.getEntities().size()-1;i>=1;i--)
    	{
    		Sandbox.getEntities().remove(i);
    	}
    	//Border
    	for(int i = 0; i < SCENE_WIDTH; i += 32){
    	    for(int j = 0; j < SCENE_HEIGHT; j += 32){
    	    	if(i == 0 || i + 33 > SCENE_HEIGHT || j == 0 || j + 33 > SCENE_WIDTH) {
    	    		walls.add(new Wall(i, j));
    				if(i==0 && j == 0)
    				{
    					mapLayout[0][0] = 1;
    				}else if(i ==0)
    				{
    					mapLayout[0][j/32] = 1;
    				}else if(j == 0)
    				{
    					mapLayout[i/32][0] = 1;
    				}
    	    	}
    	    }
    	 }
    	
    	//Middle Acid
    	for(int i = 32; i < 608; i += 32){
    		for(int j = 32; j < 608; j += 32){
    				if(!(i==32||i==288||i==320||i==576) && !(j==32||j==288||j==320||j==576))
    				{
    					pools.add(new AcidPool(i, j));
    					mapLayout[i/32][j/32] = 1;
    				}
    		}
    	}
    	
    	for(Wall wall : walls) {
    		Sandbox.addEntityToGame(wall);
    	}
    	for(AcidPool wall : pools) {
    		Sandbox.addEntityToGame(wall);
    	}
    	
    	
	}
	public static void Map5() {
		ArrayList<Wall> walls = new ArrayList<Wall>();
    	ArrayList<AcidPool> pools = new ArrayList<AcidPool>();
    	mapLayout = new int[20][20];
    	mobCount = 0;
		wave = 1;
		lvl = 5;
    	
		//Make player in safe location
		Sandbox.sandboxPlayer.init(320,320);
		
    	//Clear Map
    	System.out.println(Sandbox.getEntities().size());
    	for(int i = Sandbox.getEntities().size()-1;i>=1;i--)
    	{
    		Sandbox.getEntities().remove(i);
    	}
    	
    	
    	//Border
    	for(int i = 0; i < SCENE_WIDTH; i += 32){
    	    for(int j = 0; j < SCENE_HEIGHT; j += 32){
    	    	if(i == 0 || i + 33 > SCENE_HEIGHT || j == 0 || j + 33 > SCENE_WIDTH) {
    	    	walls.add(new Wall(i, j));
				if(i==0 && j == 0)
				{
					mapLayout[0][0] = 1;
				}else if(i ==0)
				{
					mapLayout[0][j/32] = 1;
				}else if(j == 0)
				{
					mapLayout[i/32][0] = 1;
				}
    	    	}
    	    }
    	 }
    	
    	//Middle Walls
    	for(int i = 96; i <= 512; i += 32){
    		if(i == 96||i == 512)
    		{
	    		for(int j = 96; j <= 512; j += 32){
	    			if(!(j == 288 || j==320))
	    				walls.add(new Wall(i, j));
	    				mapLayout[i/32][j/32] = 1;
	    		}
    		}else
    		{
    			for(int j = 96; j <= 512; j += 32)
    			{
    				if(j == 96||j == 512) 
    				{
    					walls.add(new Wall(i, j));
    					mapLayout[i/32][j/32] = 1;
    				}
    			}
    		}	
    	}
    	
    	for(int i = 224; i <= 384; i += 32){
    		if(i == 224||i == 384)
    		{
	    		for(int j = 224; j <= 384; j += 32){
	    			walls.add(new Wall(i, j));
	    			mapLayout[i/32][j/32] = 1;
	    		}
    		}else
    		{
    			for(int j = 224; j <= 384; j += 32)
    			{
    				if(j == 224 || j == 384) 
    				{
    					if(!(i == 288 || i==320))
    						walls.add(new Wall(i, j));
    					mapLayout[i/32][j/32] = 1;
    				}
    			}
    		}	
    	}
    	
    	//Acid Pools
    	for(int i = 32; i <= 576; i += 32){
    		if(i == 32||i == 576)
    		{
	    		for(int j = 32; j <= 576; j += 32){
	    			pools.add(new AcidPool(i, j));
	    			mapLayout[i/32][j/32] = 1;
	    		}
    		}else
    		{
    			for(int j = 32; j <= 576; j += 32)
    			{
    				if(j == 32||j == 576) 
    				{
    					pools.add(new AcidPool(i, j));
    					mapLayout[i/32][j/32] = 1;
    				}
    			}
    		}	
    	}
    	for(int i = 128; i <= 480; i += 32){
    		if(i == 128||i == 480)
    		{
	    		for(int j = 128; j <= 480; j += 32){
	    			if(!(j == 288 || j==320))
	    				pools.add(new AcidPool(i, j));
	    				mapLayout[i/32][j/32] = 1;
	    		}
    		}else
    		{
    			for(int j = 128; j <= 480; j += 32)
    			{
    				if(j == 128||j == 480) 
    				{
    					pools.add(new AcidPool(i, j));
    					mapLayout[i/32][j/32] = 1;
    				}
    			}
    		}	
    	}
    	
    	
    	for(Wall wall : walls) {
    		Sandbox.addEntityToGame(wall);
    	}
    	for(AcidPool wall : pools) {
    		Sandbox.addEntityToGame(wall);
    	}
    	
    	
	}
	
	public static void spawnWizard() {
		Random r = new Random();
		int i = (r.nextInt(18) + 1) * 32;
		int j = (r.nextInt(18) + 1) * 32;
		while(mapLayout[i/32][j/32] == 1)
		{
			i = (r.nextInt(18) + 1) * 32;
			j = (r.nextInt(18) + 1) * 32;
		}
    	Sandbox.addEntityToGame(new Wizard(i,j));
    	mobCount++;
	}
	
	public static void spawnRunner() {
		Random r = new Random();
		int i = (r.nextInt(18) + 1) * 32;
		int j = (r.nextInt(18) + 1) * 32;
		while(mapLayout[i/32][j/32] == 1)
		{
			i = (r.nextInt(18) + 1) * 32;
			j = (r.nextInt(18) + 1) * 32;
		}
    	Sandbox.addEntityToGame(new Runner(i, j));
    	mobCount++;
	}
	
	public static void waveProgress()
	{
		lvl1Progress();
		lvl2Progress();
		lvl3Progress();
		lvl4Progress();
		lvl5Progress();
	}
	
	public static void lvl1Progress()
	{
		if(lvl == 1 && wave == 1 && mobCount == 0)
        {
        	for(int i = 0;i<1 ;i++)
        	{
        		spawnWizard();
        		spawnRunner();
        	}
        	wave++;
        }
        if(lvl == 1 && wave == 2 && mobCount == 0)
        {
        	//Spawn in mobs here
        	for(int i = 0;i<1;i++)
        	{
        		spawnWizard();
        	}
        	wave++;
        }
        if(lvl == 1 && wave == 3 && mobCount == 0)
        {
        	for(int i = 0;i<1;i++)
        	{
        	 spawnWizard();
        	}
        	wave++;
        }	
        if(lvl == 1 && wave == 4 && mobCount == 0)
        {
        	Map2();
        }
	}
	public static void lvl2Progress()
	{
		if(lvl == 2 && wave == 1 && mobCount == 0)
        {
        	for(int i = 0;i<1 ;i++)
        	{
        		spawnWizard();
        		spawnRunner();
        	}
        	wave++;
        }
        if(lvl == 2 && wave == 2 && mobCount == 0)
        {
        	//Spawn in mobs here
        	for(int i = 0;i<1;i++)
        	{
        		spawnWizard();
        		spawnRunner();
        	}
        	wave++;
        }
        if(lvl == 2 && wave == 3 && mobCount == 0)
        {
        	for(int i = 0;i<1;i++)
        	{
        		spawnWizard();
        		spawnRunner();
        	}
        	wave++;
        }	
        if(lvl == 2 && wave == 4 && mobCount == 0)
        {
        	Map3();
        }
	}
	public static void lvl3Progress()
	{
		if(lvl == 3 && wave == 1 && mobCount == 0)
        {
        	for(int i = 0;i<1 ;i++)
        	{
        		spawnWizard();
        		spawnRunner();
        	}
        	wave++;
        }
        if(lvl == 3 && wave == 2 && mobCount == 0)
        {
        	//Spawn in mobs here
        	for(int i = 0;i<1;i++)
        	{
        		spawnWizard();
        		spawnRunner();
        	}
        	wave++;
        }
        if(lvl == 3 && wave == 3 && mobCount == 0)
        {
        	for(int i = 0;i<1;i++)
        	{
        		spawnWizard();
        		spawnRunner();
        	}
        	wave++;
        }	
        if(lvl == 3 && wave == 4 && mobCount == 0)
        {
        	Map4();
        }
	}
	public static void lvl4Progress()
	{
		if(lvl == 4 && wave == 1 && mobCount == 0)
        {
        	for(int i = 0;i<1 ;i++)
        	{
        		spawnWizard();
        		spawnRunner();
        	}
        	wave++;
        }
        if(lvl == 4 && wave == 2 && mobCount == 0)
        {
        	//Spawn in mobs here
        	for(int i = 0;i<1;i++)
        	{
        		spawnWizard();
        		spawnRunner();
        	}
        	wave++;
        }
        if(lvl == 4 && wave == 3 && mobCount == 0)
        {
        	for(int i = 0;i<1;i++)
        	{
        		spawnWizard();
        		spawnRunner();
        	}
        	wave++;
        }	
        if(lvl == 4 && wave == 4 && mobCount == 0)
        {
        	Map5();
        }
	}
	public static void lvl5Progress()
	{
		if(lvl == 5 && wave == 1 && mobCount == 0)
        {
        	for(int i = 0;i<1 ;i++)
        	{
        		spawnWizard();
        		spawnRunner();
        	}
        	wave++;
        }
        if(lvl == 5 && wave == 2 && mobCount == 0)
        {
        	//Spawn in mobs here
        	for(int i = 0;i<1;i++)
        	{
        		spawnWizard();
        		spawnRunner();
        	}
        	wave++;
        }
        if(lvl == 5 && wave == 3 && mobCount == 0)
        {
        	for(int i = 0;i<1;i++)
        	{
        		spawnWizard();
        		spawnRunner();
        	}
        	wave++;
        }	
        if(lvl == 5 && wave == 4 && mobCount == 0)
        {
        	Sandbox.stopGame(true);
        }
	}
}