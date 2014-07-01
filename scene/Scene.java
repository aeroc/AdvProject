package advproject.scene;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.awt.Window;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import advproject.AdvProject;
import advproject.map.Map;
import advproject.map.MapHexSquareGrid;
import advproject.map.MapSquareGrid;
import advproject.tile.Tile;
import advproject.unit.Unit;

public class Scene {
	
	private AdvProject advProject;
	
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f movement;
	
	private Map map;
	
//	private float maxLookDown = -90f;
//	private float maxLookUp = 90f;
//	private float mouseSpeed = 2.0f;
	
	private static IntBuffer viewport = BufferUtils.createIntBuffer(16);
	private static FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
	private static FloatBuffer projection = BufferUtils.createFloatBuffer(16);
	private static FloatBuffer winZ = BufferUtils.createFloatBuffer(20);
	private static FloatBuffer positionFB = BufferUtils.createFloatBuffer(3);

	public Scene( AdvProject advProject ){
		
		this.advProject = advProject;
		
		this.setPosition( new Vector3f( -5f, -15f, -11f ) );
		this.setRotation( new Vector3f( 70f, 0f, 0f ) );
		this.setMovement( new Vector3f( 0f, 0f, 0f ) );
		
		this.map = new MapSquareGrid( 30, 20 );
	}
	public void render(){
		//If the camera moved, this is where the position/rotation is changed
		glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
		glMatrixMode( GL_MODELVIEW );
		glLoadIdentity();
		glRotatef( rotation.x, 1, 0, 0 );
		glRotatef( rotation.y, 0, 1, 0 );
		glRotatef( rotation.z, 0, 0, 1 );
		glTranslatef( position.x, position.y, position.z );
				
		//Render Map
		if( this.map != null ){
			map.render();
		}
		
		Scene.make2D(); //Make 2D for GUI
		
		GL11.glColor3f( 1.0f, 1.0f, 1.0f );
		GL11.glBegin( GL11.GL_QUADS );
		GL11.glVertex2f(100, 100);
		GL11.glVertex2f(100 + 200, 100);
		GL11.glVertex2f(100 + 200, 100 + 200);
		GL11.glVertex2f(100, 100 + 200);
		GL11.glEnd();
		
		Scene.make3D(); //Turn back to 3D for next render call
	}
	public AdvProject getAdvProject(){
		return this.advProject;
	}
	public void setPosition( Vector3f position ){
		this.position = position;
	}
	public Vector3f getPosition(){
		return this.position;
	}
	public void setRotation( Vector3f rotation ){
		this.rotation = rotation;
	}
	public Vector3f getRotation(){
		return this.rotation;
	}
	public void setMovement( Vector3f movement ){
		this.movement = movement;
	}
	public Vector3f getMovement(){
		return this.movement;
	}
	public void tick(){
		this.position.setX( this.position.getX() + this.movement.x );
		this.position.setY( this.position.getY() + this.movement.y );
		this.position.setZ( this.position.getZ() + this.movement.z );
		
		this.map.tick();
		
		Vector3f mouseCoord =  getMousePositionIn3dCoords( Mouse.getX(), Mouse.getY() );
		if( mouseCoord.x >= 0 && mouseCoord.x < 10.5 && mouseCoord.z >= 0 && mouseCoord.z < 10 ){
			float mouseX = mouseCoord.x;
			float mouseZ = mouseCoord.z;
			if( ( (int) mouseZ ) % 2 == 1 ){ mouseX -= 0.5f; }
			Tile.getTileArray()[this.map.getTileID( (int) mouseX, (int) mouseZ )].tick();
		}
		
		if( Keyboard.isKeyDown( Keyboard.KEY_ESCAPE ) ){
			Mouse.setGrabbed( false );
		}
		if( Keyboard.isKeyDown( Keyboard.KEY_W ) ){
			this.movement.z += 0.005f;
		}
		else if( Keyboard.isKeyDown( Keyboard.KEY_S ) ){
			this.movement.z -= 0.005f;
		}
		else if( this.movement.z < 0 ){
			this.movement.z += 0.2f;
			if( this.movement.z > 0 ){
				this.movement.z = 0;
			}
		}
		else if( this.movement.z > 0 ){
			this.movement.z -= 0.2f;
			if( this.movement.z < 0 ){
				this.movement.z = 0;
			}
		}
		if( Keyboard.isKeyDown( Keyboard.KEY_A ) ){
			this.movement.x += 0.005f;
		}
		else if( Keyboard.isKeyDown( Keyboard.KEY_D ) ){
			this.movement.x -= 0.005f;
		}
		else if( this.movement.x < 0 ){
			this.movement.x += 0.2f;
			if( this.movement.x > 0 ){
				this.movement.x = 0;
			}
		}
		else if( this.movement.x > 0 ){
			this.movement.x -= 0.2f;
			if( this.movement.x < 0 ){
				this.movement.x = 0;
			}
		}
		if( Keyboard.isKeyDown( Keyboard.KEY_LSHIFT ) ){
			this.movement.y += 0.005f;
		}
		else if( Keyboard.isKeyDown( Keyboard.KEY_SPACE ) ){
			this.movement.y -= 0.005f;
		}
		else if( this.movement.y < 0 ){
			this.movement.y += 0.2f;
			if( this.movement.y > 0 ){
				this.movement.y = 0;
			}
		}
		else if( this.movement.y > 0 ){
			this.movement.y -= 0.2f;
			if( this.movement.y < 0 ){
				this.movement.y = 0;
			}
		}
		if( Mouse.isButtonDown(0) ){
			Mouse.setGrabbed( true );
		}
		if( Mouse.isGrabbed() ){
//			float mouseDX = Mouse.getDX() * 2.0f * 0.016f;
//			float mouseDY = Mouse.getDY() * 2.0f * 0.016f;
//			if( rotation.y + mouseDX >= 360 ){
//				rotation.y = rotation.y + mouseDX - 360;
//			}
//			else if( rotation.y + mouseDX < 0 ){
//				rotation.y = rotation.y + mouseDX + 360;
//			}
//			else{
//				rotation.y += mouseDX;
//			}
//			if( rotation.x - mouseDY >= -85 && rotation.x - mouseDY <= 85 ){
//				rotation.x -= mouseDY;
//			}
//			else if( rotation.x - mouseDY < -85 ){
//				rotation.x = -85;
//			}
//			else if( rotation.x - mouseDY > 85 ){
//				rotation.x = 85;
//			}
//			float mouseDX = Mouse.getDX() * mouseSpeed * 0.16f;
//            float mouseDY = Mouse.getDY() * mouseSpeed * 0.16f;
//            if (rotation.y + mouseDX >= 360) {
//                rotation.y = rotation.y + mouseDX - 360;
//            } else if (rotation.y + mouseDX < 0) {
//                rotation.y = 360 - rotation.y + mouseDX;
//            } else {
//                rotation.y += mouseDX;
//            }
//            if (rotation.x - mouseDY >= maxLookDown && rotation.x - mouseDY <= maxLookUp) {
//                rotation.x += -mouseDY;
//            } else if (rotation.x - mouseDY < maxLookDown) {
//                rotation.x = maxLookDown;
//            } else if (rotation.x - mouseDY > maxLookUp) {
//                rotation.x = maxLookUp;
//            }
		}
	}
	public Vector3f getMousePositionIn3dCoords( int mouseX, int mouseY ){
		viewport.clear();
	    modelview.clear();
	    projection.clear();
	    winZ.clear();;
	    positionFB.clear();
	    float winX, winY;
	
	
	    GL11.glGetFloat( GL11.GL_MODELVIEW_MATRIX, modelview );
	    GL11.glGetFloat( GL11.GL_PROJECTION_MATRIX, projection );
	    GL11.glGetInteger( GL11.GL_VIEWPORT, viewport );
	
	    winX = (float)mouseX;
	    winY = /* (float)viewport.get(3) -  */  //Uncomment this if you invert Y
	    (float)mouseY;
	
	    GL11.glReadPixels(mouseX, (int)winY, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, winZ);

	    float zz = winZ.get();

	    GLU.gluUnProject( winX, winY, zz, modelview, projection, viewport, positionFB );

	    Vector3f v = new Vector3f (positionFB.get(0),positionFB.get(1),positionFB.get(2));

	    return v ;
	}
	protected static void make2D() {
		//Remove the Z axis
//	    GL11.glDisable(GL11.GL_LIGHTING);
	    GL11.glDisable(GL11.GL_DEPTH_TEST);
	    GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glPushMatrix();
	    GL11.glLoadIdentity();
	    GL11.glOrtho(0, 1200, 0, 800, -1, 1);
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    GL11.glPushMatrix();
	    GL11.glLoadIdentity();
	}

	protected static void make3D() {
	    //Restore the Z axis
	    GL11.glMatrixMode(GL11.GL_PROJECTION);
	    GL11.glPopMatrix();
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    GL11.glPopMatrix();
	    GL11.glEnable(GL11.GL_DEPTH_TEST);
//	    GL11.glEnable(GL11.GL_LIGHTING);
	}
}
