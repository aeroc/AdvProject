package advproject.tile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureLoader;

public class TileSwamp extends Tile{

	public TileSwamp( int tileID ){
		super( tileID );
		this.setTexture( "res/tile/swamp.png" );
	}
}
