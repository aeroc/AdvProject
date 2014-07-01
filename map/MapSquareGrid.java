package advproject.map;

import advproject.tile.Tile;
import advproject.unit.Unit;

public class MapSquareGrid extends Map{
	
	public MapSquareGrid( int width, int height ){
		super( width, height );
		this.getUnitList().add( new Unit( 2, 2 ) ); //delete me
	}
	@Override
	public void render(){
		for( int y = 0; y < this.getHeight(); y++ ){
			for( int x = 0; x < this.getWidth(); x++ ){
				int tileID = this.getTileID( x, y );				
				Tile.getTileArray()[tileID].render( x, this.getTileAltitude( x, y ), y );
			}
		}
		for( Unit u : this.getUnitList() ){
			u.render( this.getTileAltitude( u.getX(), u.getY() ) );
		}
	}
	@Override
	public void tick(){
		
	}

}
