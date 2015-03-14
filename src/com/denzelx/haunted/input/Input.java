package com.denzelx.haunted.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;


public class Input extends GLFWKeyCallback {

	private boolean lock = false;
	
	public static boolean[] keys = new boolean [65536];
	public static boolean[] wasTouched = new boolean[65536];
	
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key] = action != GLFW.GLFW_RELEASE;
		wasTouched[key] = action != GLFW.GLFW_RELEASE;
	}

	public static boolean isKeyDown(int keycode){
		return keys[keycode];
	}
	
	public static boolean eventAvailable(int keycode){
		if(wasTouched[keycode]){
			wasTouched[keycode] = false;
			return true;
		}
		return false;
	}
	
	public static void eraseEventBuffer(){
		for(int i = 0; i<wasTouched.length; i++){
			wasTouched[i] = false;
		}
	}
	
	
}
