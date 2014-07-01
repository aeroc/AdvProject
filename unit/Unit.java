package advproject.unit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Unit {

	private int x, y;
	private Texture texture;
	
	public Unit( int x, int y ){
		this.x = x;
		this.y = y;
		this.setTexture( "res/unit/infantry.png" );
	}
	public void setX( int x ){
		this.x = x;
	}
	public int getX(){
		return this.x;
	}
	public void setY( int y ){
		this.y = y;
	}
	public int getY(){
		return this.y;
	}
	public void render( float height ){
		height += 1.0f; //Tiles are measured from 1.0f below the surface, so we add this to place the unit on the surface
		float angle = 0.7f;	//radians
		GL11.glColor3f( 1.0f, 1.0f, 1.0f );
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable( GL11.GL_TEXTURE_2D );
		GL11.glBindTexture( GL11.GL_TEXTURE_2D, texture.getTextureID() );
		GL11.glBegin( GL11.GL_QUADS );
		GL11.glTexCoord2f( 0.0f, 1.0f );
		GL11.glVertex3f( this.x + 0.2f, height, this.y + 0.7f );
		GL11.glTexCoord2f( 0.0f, 0.0f );
		GL11.glVertex3f( this.x + 0.2f, height + 0.8f * (float) Math.cos( angle ), this.y + 0.7f - 0.8f * (float) Math.sin( angle ) );
		GL11.glTexCoord2f( 1.0f, 0.0f );
		GL11.glVertex3f( this.x + 0.8f, height + 0.8f * (float) Math.cos( angle ), this.y + 0.7f - 0.8f * (float) Math.sin( angle ) );
		GL11.glTexCoord2f( 1.0f, 1.0f );
		GL11.glVertex3f( this.x + 0.8f, height, this.y + 0.7f );
		GL11.glEnd();
		GL11.glBindTexture( GL11.GL_TEXTURE_2D, 0 ); //unbind
		GL11.glDisable( GL11.GL_TEXTURE_2D );
		GL11.glDisable( GL11.GL_BLEND );
	}
	public void setTexture( String resourceFileName ){
		try{
			this.texture =  TextureLoader.getTexture( "PNG", new FileInputStream( new File( resourceFileName ) ), GL11.GL_NEAREST );
		}
		catch( FileNotFoundException e1 ){
			e1.printStackTrace();
		}
		catch( IOException e1 ){
			e1.printStackTrace();
		}
		
	}
}
