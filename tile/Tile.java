package advproject.tile;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;


public class Tile {
	
	private static final Tile[] tileArray = new Tile[256];
	
	private static final Tile tilePlain = new TilePlain( 0 );
	private static final Tile tileForest = new TileForest( 1 );
	private static final Tile tileSwamp = new TileSwamp( 2 );
	
	private boolean visible = true;
	private float size = 1.0f;
	private Texture texture;
	
	public Tile( int tileID ){
		if( tileArray[tileID] == null ){
			tileArray[tileID] = this;
        }
		else{
            throw new IllegalArgumentException( "An attempt to add a tile with the same ID occurred: " + tileID );
		}
	}
	public Tile(){
		throw new IllegalArgumentException( "Cannot use Tile() constructor" );
	}
	public void render( float x, float y, float z ){
		renderTop( x, y, z );
		glColor3f( 0.1f, 0.1f, 0.1f );
		renderBottom( x, y, z );
		renderLeft( x, y, z );
		renderRight( x, y, z );
		renderFront( x, y, z );
		renderBack( x, y, z );
	}
	public void tick(){
		
	}
	public void setVisible( boolean visible ){
		this.visible = visible;
	}
	public boolean isVisible(){
		return this.visible;
	}
	public void renderTop( float x, float y, float z ){
		glColor3f( 1.0f, 1.0f, 1.0f );
		GL11.glEnable( GL11.GL_TEXTURE_2D );
		GL11.glBindTexture( GL11.GL_TEXTURE_2D, texture.getTextureID() );
		glBegin( GL11.GL_QUADS );
		GL11.glTexCoord2f( 0.0f, 0.0f );
		glVertex3f( x, y + this.getSize(), z );
		GL11.glTexCoord2f( 1.0f, 0.0f );
		glVertex3f( x, y + this.getSize(), z + this.getSize() );
		GL11.glTexCoord2f( 1.0f, 1.0f );
		glVertex3f( x + this.getSize(), y + this.getSize(), z + this.getSize() );
		GL11.glTexCoord2f( 0.0f, 1.0f );
		glVertex3f( x + this.getSize(), y + this.getSize(), z );
		glEnd();
		GL11.glBindTexture( GL11.GL_TEXTURE_2D, 0 ); //unbind
		GL11.glDisable( GL11.GL_TEXTURE_2D );
	}
	public void renderBottom( float x, float y, float z ){
		glBegin( GL11.GL_QUADS );
		glVertex3f( x, y, z );
		glVertex3f( x + this.getSize(), y, z );
		glVertex3f( x + this.getSize(), y, z + this.getSize() );
		glVertex3f( x, y, z + this.getSize() );
		glEnd();
	}
	public void renderLeft( float x, float y, float z ){
		glBegin( GL11.GL_QUADS );
		glVertex3f( x, y, z );
		glVertex3f( x, y, z + this.getSize() );
		glVertex3f( x, y + this.getSize(), z + this.getSize() );
		glVertex3f( x, y + this.getSize(), z );
		glEnd();
	}
	public void renderRight( float x, float y, float z ){
		glBegin( GL11.GL_QUADS );
		glVertex3f( x + this.getSize(), y, z );
		glVertex3f( x + this.getSize(), y, z + this.getSize() );
		glVertex3f( x + this.getSize(), y + this.getSize(), z + this.getSize() );
		glVertex3f( x + this.getSize(), y + this.getSize(), z );
		glEnd();
	}
	public void renderFront( float x, float y, float z ){
		glBegin( GL11.GL_QUADS );
		glVertex3f( x, y, z );
		glVertex3f( x + this.getSize(), y, z );
		glVertex3f( x + this.getSize(), y + this.getSize(), z );
		glVertex3f( x, y + this.getSize(), z );
		glEnd();
	}
	public void renderBack( float x, float y, float z ){
		glBegin( GL11.GL_QUADS );
		glVertex3f( x, y, z + this.getSize() );
		glVertex3f( x + this.getSize(), y, z + this.getSize() );
		glVertex3f( x + this.getSize(), y + this.getSize(), z + this.getSize() );
		glVertex3f( x, y + this.getSize(), z + this.getSize() );
		glEnd();
	}
	public float getSize(){
		return this.size;
	}
	public void setTexture( String resourceFileName ){
		try{
			this.texture = TextureLoader.getTexture( "PNG", new FileInputStream( new File( resourceFileName ) ), GL11.GL_NEAREST );
		}
		catch( FileNotFoundException e1 ){
			e1.printStackTrace();
		}
		catch( IOException e1 ){
			e1.printStackTrace();
		}
	}
	public static Tile[] getTileArray(){
		return Tile.tileArray;
	}
	public Texture getTexture(){
		return this.texture;
	}
}
