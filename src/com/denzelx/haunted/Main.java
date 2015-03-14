package com.denzelx.haunted;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import com.denzelx.haunted.graphics.Shader;
import com.denzelx.haunted.input.Input;
import com.denzelx.haunted.maths.Matrix4f;
import com.denzelx.haunted.state_manager.StateManager;
import com.denzelx.level.Level;
import com.denzelx.level.Level1;
import com.denzelx.level.Stage;
import com.denzelx.level.MainMenu;

public class Main implements Runnable {

	private int width = 1280;
	private int height = 720;

	private Thread thread;
	private boolean running = false;
	private long window;
	private Level level;
	private MainMenu testgl;
	private Stage stage;
	
	private StateManager statemanager;
	
	private GLFWKeyCallback keyCallback;

	public void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	private void init() {
		if (glfwInit() == GL_FALSE) {
			System.exit(-1);
		}
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		window = glfwCreateWindow(width, height, "Alex Walender's Super Nico Adventure", NULL, NULL);
		if (window == NULL) {
			return;
		}
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2, (GLFWvidmode.height(vidmode) - height) / 2);
		glfwSetKeyCallback(window, keyCallback = new Input());
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
		GLContext.createFromCurrent();
		glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE1);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		System.out.println("OpenGL: " + glGetString(GL_VERSION));

		Shader.loadAll();

		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -3.0f, 3.0f);
		
		Shader.STANDARD.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.STANDARD.setUniform1i("tex", 1);
		
		/*
		Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BG.setUniform1i("tex", 1);
		
		Shader.BIRD.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BIRD.setUniform1i("tex", 1);
		
		Shader.PIPE.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.PIPE.setUniform1i("tex", 1);
		*/
		


		statemanager = new StateManager(0);
		
	}

	public void run() {
		init();
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1.0) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				String title = ("Nico Adventure v0.00001 " + updates + " ups, " + frames + " fps");
				glfwSetWindowTitle(window, title );
				updates = 0;
				frames = 0;
			}
			if (glfwWindowShouldClose(window) == GL_TRUE)
				running = false;
		}
		keyCallback.release();
		glfwDestroyWindow(window);
		glfwTerminate();
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		statemanager.render();
		glfwSwapBuffers(window);
	}

	private void update() {
		glfwPollEvents();
		statemanager.update();


	}

	public static void main(String[] args) {
		new Main().start();
	}

}
