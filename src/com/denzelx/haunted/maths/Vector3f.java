package com.denzelx.haunted.maths;

public class Vector3f {

	public float x, y, z;
	
	public Vector3f() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}
	
	public Vector3f(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static Vector3f normalize(Vector3f a){
		Vector3f result = new Vector3f();
		float vecLength = (float) Math.abs(Math.sqrt((a.x)*(a.x) + (a.y)*(a.y) + (a.z)*(a.z)));	
		result.x = (1.0f/vecLength) * a.x;
		result.y = (1.0f/vecLength) * a.y;
		result.z = (1.0f/vecLength) * a.z;	
		return result;
	}
	
	public static Vector3f cross(Vector3f a, Vector3f b){
		Vector3f result = new Vector3f();
		
		result.x = (a.y * b.z) - (a.z * b.y);
		result.y = (a.z * b.x) - (a.x * b.z);
		result.z = (a.x * b.y) - (a.y * b.x);
		
		return result;
	}
	
}
