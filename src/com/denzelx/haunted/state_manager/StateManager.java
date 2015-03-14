package com.denzelx.haunted.state_manager;

import com.denzelx.level.GFX_Template;
import com.denzelx.level.Stage;
import com.denzelx.level.MainMenu;

public class StateManager implements GFX_Template {
	
	private static int actualState;
	private Stage stage;
	
	public StateManager(int actualState){
		StateManager.actualState = actualState;
		stage = new MainMenu();
	}
	
	public void update(){
		stage.update();
	}

	
	public void render() {	
		stage.render();
	}
	
	public static void setState(int newState){
		actualState = newState;
	}
	
	public static int getState(){
		return actualState;
	}
	
}
