package advproject.map;

import java.util.ArrayList;
import java.util.Random;

import advproject.unit.Unit;

public class Map {
	
	private static final float ALTITUDE_BYTE_DENONIMATOR = 512f;
	
	private int width, height;
	private byte[] tileMap;
	private byte[] altitudeMap;
	private ArrayList<Unit> unitList;
	
	public Map(){
		this( 20, 20 );
	}
	public Map( int width, int height ){
		this.width = width;
		this.height = height;
		this.tileMap = new byte[width*height];
		this.zeroTheMap(); //probably redundant
		this.altitudeMap = new byte[width*height];
		this.randomizeAltitudeMap();
		this.unitList = new ArrayList<Unit>();
	}
	public int getWidth(){
		return this.width;
	}
	public int getHeight(){
		return this.height;
	}
	public int getTileID( int x, int y ){
		return (int) this.tileMap[x + y * this.height];
	}
	public float getTileAltitude( int x, int y ){
		return (float) this.altitudeMap[x + y * this.height] / Map.ALTITUDE_BYTE_DENONIMATOR;
	}
	public void zeroTheMap(){	//Sets the tiles to tileID 0
		if( this.tileMap != null ){
			for( int i = 0; i < this.tileMap.length; i++ ){
				this.tileMap[i] = 0;
			}
		}
	}
	public void zeroTheAltitudeMap(){
		if( this.altitudeMap != null ){
			for( int i = 0; i < this.altitudeMap.length; i++ ){
				this.altitudeMap[i] = 0;
			}
		}
	}
	public void randomizeAltitudeMap(){
		Random random = new Random();
		for( int i = 0; i < this.altitudeMap.length; i++ ){
			this.altitudeMap[i] = Byte.parseByte( "" + ( random.nextInt( 256 ) - 128 ) );
		}
	}
	public ArrayList<Unit> getUnitList(){
		return this.unitList;
	}
	public void render(){
		
	}
	public void tick(){
		
	}
}
