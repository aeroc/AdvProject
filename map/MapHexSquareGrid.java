package advproject.map;

import advproject.tile.Tile;
import advproject.unit.Unit;

public class MapHexSquareGrid extends Map{
	
	public MapHexSquareGrid( int width, int height ){
		super( width, height );
		this.getUnitList().add( new Unit( 2, 2 ) ); //delete me
	}

	@Override
	public void render(){
		for( int y = 0; y < this.getHeight(); y++ ){
			for( int x = 0; x < this.getWidth(); x++ ){
				int tileID = this.getTileID( x, y );
				if( y % 2 >= 1 ){	//if this is an odd row, then offset the y position by 0.5f
					Tile.getTileArray()[tileID].render( x + 0.5f, this.getTileAltitude( x, y ), y );
				}
				else{
					Tile.getTileArray()[tileID].render( x, this.getTileAltitude( x, y ), y );
				}
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
