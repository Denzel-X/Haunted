package com.denzelx.level;

import com.denzelx.haunted.graphics.Shader;
import static org.lwjgl.glfw.GLFW.*;
import com.denzelx.haunted.graphics.Texture;
import com.denzelx.haunted.graphics.VertexArray;
import com.denzelx.haunted.input.Input;
import com.denzelx.haunted.maths.Matrix4f;
import com.denzelx.haunted.maths.Vector3f;

public class MainMenu extends Stage{
	
	private VertexArray mainmenu_va, cursor_va;
	private Texture menu_texture, cursor_texture;
	private Vector3f position, camera, cursor_position;
	private static final float SIZE = 1.0f;
	private int selection = 0;
	
	private boolean menuRunning = true;
	
	
	
	public MainMenu(){		
		float[] vertices = new float[] {
				-10.0f, -10.0f * 9.0f / 16.0f, 0.0f, //bottom left
				-10.0f, 10.0f * 9.0f / 16.0f, 0.0f, //top left
				10.0f, 10.0f * 9.0f / 16.0f, 0.0f,  //top right
				10.0f, -10.0f * 9.0f / 16.0f, 0.0f //bottom right
				};

		byte[] indices = new byte[] {
				0, 1, 2,
				2, 3, 0

		};

		float[] tcs = new float[] { 
				0, 1, 
				0, 0,
				1, 0,
				1, 1

		};
		
		float[] vertices2 = new float[] {
				-SIZE / 2.0f, -SIZE / 2.0f, 0.1f,
				-SIZE / 2.0f, SIZE / 2.0f, 0.1f,
				SIZE / 2.0f, SIZE / 2.0f, 0.1f,
				SIZE / 2.0f, -SIZE / 2.0f, 0.1f
				};

		byte[] indices2 = new byte[] {
				0, 1, 2,
				2, 3, 0

		};

		float[] tcs2 = new float[] { 
				0, 1, 
				0, 0,
				1, 0,
				1, 1

		};
		
		
		position = new Vector3f(0.0f, 0.0f, -1.0f);
		cursor_position = new Vector3f(-2.0f, -1.0f, 0.0f);
		mainmenu_va = new VertexArray(vertices, indices, tcs);
		cursor_va = new VertexArray(vertices2, indices2, tcs2);
		menu_texture = new Texture("res/main_menu_gfx/intro.png");
		cursor_texture = new Texture("res/main_menu_gfx/cursor.png");
		
		camera = new Vector3f();
		
	}
	
	public void update(){
		
		if(menuRunning){
			if(Input.eventAvailable(GLFW_KEY_DOWN)){
				if(selection == 0){
					selection = 1;
					cursor_position.y = -2.5f;
					return;
				}
				if(selection == 1){
					selection = 0;
					cursor_position.y = -1.0f;
					return;
				}
				
			}
			if(Input.eventAvailable(GLFW_KEY_UP)){
				if(selection == 0){
					selection = 1;
					cursor_position.y = -2.7f;
					return;
				}
				if(selection == 1){
					selection = 0;
					cursor_position.y = -1.0f;
					return;
				}
				
			}
		
			if(Input.eventAvailable(GLFW_KEY_ENTER)){
				if(selection == 1) System.exit(0);
			}
		}
	}
	


	public void render(){
		if(menuRunning) renderMainMenu();

	}

	
	public void renderMainMenu(){
		Shader.STANDARD.enable();
		Shader.STANDARD.setUniformMat4f("vw_matrix", new Matrix4f().identity().translate(camera));
		Shader.STANDARD.setUniformMat4f("ml_matrix", new Matrix4f().translate(position));
		menu_texture.bind();
		mainmenu_va.render();
		Shader.STANDARD.disable();
		
		Shader.STANDARD.enable();
		Shader.STANDARD.setUniformMat4f("vw_matrix", new Matrix4f().identity());
		Shader.STANDARD.setUniformMat4f("ml_matrix", new Matrix4f().identity().multiply(Matrix4f.translate(cursor_position)));
		cursor_texture.bind();
		cursor_va.render();
		Shader.STANDARD.disable();
	}
	
	
	public boolean isMenuRunning() {
		return menuRunning;
	}

	public void setMenuRunning(boolean menuRunning) {
		this.menuRunning = menuRunning;
	}
}
