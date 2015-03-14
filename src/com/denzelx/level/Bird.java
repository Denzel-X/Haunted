package com.denzelx.level;

import org.lwjgl.glfw.GLFW;

import com.denzelx.haunted.graphics.Shader;
import com.denzelx.haunted.graphics.Texture;
import com.denzelx.haunted.graphics.VertexArray;
import com.denzelx.haunted.input.Input;
import com.denzelx.haunted.maths.Matrix4f;
import com.denzelx.haunted.maths.Vector3f;

public class Bird {

	private float SIZE = 1.0f;
	private VertexArray mesh;
	private Texture texture;
	private boolean birdAlive = true;
	
	private Vector3f position = new Vector3f();
	
	private float rot;
	private float delta = 0.0f;
	
	public Bird(){
		float[] vertices = new float[] {
				-SIZE / 2.0f, -SIZE / 2.0f, 0.1f,
				-SIZE / 2.0f, SIZE / 2.0f, 0.1f,
				SIZE / 2.0f, SIZE / 2.0f, 0.1f,
				SIZE / 2.0f, -SIZE / 2.0f, 0.1f
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
		
		mesh = new VertexArray(vertices, indices, tcs);
		texture = new Texture("res/bird.png");
		position.x = -2.0f;
	}
	
	public void update(){
		position.y -= delta;
		if(Input.isKeyDown(GLFW.GLFW_KEY_SPACE) && birdAlive)
			delta = -0.15f;
		else
			delta += 0.01f;
			
		rot = -delta * 90.0f;
		
	}
	
	public void fall(){
		delta = -0.15f;
		birdAlive = false;
	}
	
	public void render(){
		Shader.BIRD.enable();
		Shader.BIRD.setUniformMat4f("ml_matrix", Matrix4f.translate(position).multiply(Matrix4f.rotate(rot)));
		texture.bind();
		mesh.render();
		Shader.BIRD.disable();
	}
	
	public float getY(){
		return position.y;
	}
	
	public float getX(){
		return position.x;
	}
	
	public float getSize(){
		return SIZE;
	}
	
	public void setBirdAlive(boolean status){
		birdAlive = status;
	}
	
}
