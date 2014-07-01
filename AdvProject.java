package advproject;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import advproject.scene.Scene;

public class AdvProject {
	
	private Scene activeScene;

	public AdvProject(){
		this.initDisplay();
		this.initOpenGL();
		this.setActiveScene( new Scene( this ) );
		
		while( !Display.isCloseRequested() ){
			this.activeScene.render();
			this.activeScene.tick();

			Display.update();
			Display.sync( 60 );
		}
		Display.destroy();
	}
	public static void main(String[] args) {
		new AdvProject();
	}
	public void initDisplay(){
		try{
			Display.setDisplayMode( new DisplayMode( 1920, 1080 ) );
			Display.setTitle( "Test!" );
			Display.create();
		}
		catch( LWJGLException e ){
			e.printStackTrace();
		}
	}
	public void initOpenGL(){
		//Initialization code OpenGL
		GL11.glMatrixMode( GL_PROJECTION );
		GL11.glLoadIdentity();
		GLU.gluPerspective( 45f, 1200f / 800f, 0.1f, 1000f );
		//fovy - Specifies the field of view angle, in degrees, in the y direction.
		//aspect -  Specifies the aspect ratio that determines the field of view in the x direction. The aspect ratio is the ratio of x (width) to y (height).
		//zNear - Specifies the distance from the viewer to the near clipping plane (always positive).
		//zFar - Specifies the distance from the viewer to the far clipping plane (always positive). 
		GL11.glEnable( GL11.GL_DEPTH_TEST );	//Prevents objects behind other objects appearing in front
	}
	public void setActiveScene( Scene scene ){
		this.activeScene = scene;
	}
	public Scene getActiveScene(){
		return this.activeScene;
	}
}
