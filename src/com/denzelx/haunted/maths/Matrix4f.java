package com.denzelx.haunted.maths;

import java.nio.FloatBuffer;
import com.denzelx.haunted.utils.BufferUtils;



public class Matrix4f {

	public static final int SIZE = 4 * 4;
	public float[] elements = new float[SIZE];
	
	public Matrix4f(){
		
	}
	
	public static Matrix4f identity(){
		Matrix4f result = new Matrix4f();
		for(int i = 0; i<SIZE; i++){
			result.elements[i] = 0.0f;
		}
		result.elements[0 + 0 * 4] = 1.0f;
		result.elements[1 + 1 * 4] = 1.0f;
		result.elements[2 + 2 * 4] = 1.0f;
		result.elements[3 + 3 * 4] = 1.0f;
		return result;
	}
	
	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far){
		Matrix4f result = identity();
		
		result.elements[0 + 0 * 4] = 2.0f / (right - left);
		result.elements[1 + 1 * 4] = 2.0f / (top - bottom);
		result.elements[2 + 2 * 4] = 2.0f / (near - far);
		
		result.elements[0 + 3 * 4] = (left + right) / (left - right);
		result.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
		result.elements[2 + 3 * 4] = (far + near) / (far - near);
		
		return result;
	}
	
	public static Matrix4f translate(Vector3f vector){
		Matrix4f result = identity();
		result.elements[0 + 3 * 4] = vector.x;
		result.elements[1 + 3 * 4] = vector.y;
		result.elements[2 + 3 * 4] = vector.z;
		return result;
	}
	
	public static Matrix4f scale(Vector3f vector){
		Matrix4f result = identity();
		result.elements[0 + 0 * 4] = vector.x;
		result.elements[1 + 1 * 4] = vector.y;
		result.elements[2 + 2 * 4] = vector.z;
		return result;
	}
	
	public static Matrix4f rotate(float angle){
		Matrix4f result = identity();
		float r = (float) Math.toRadians(angle);
		float cos = (float) Math.cos(r);
		float sin = (float) Math.sin(r);
		
		result.elements[0 + 0 * 4] = cos;
		result.elements[1 + 0 * 4] = sin;
		
		result.elements[0 + 1 * 4] = -sin;
		result.elements[1 + 1 * 4] = cos;
		
		return result;
	}
	
	public Matrix4f multiply(Matrix4f matrix){
		Matrix4f result = new Matrix4f();
		for(int y = 0; y < 4; y++){
			for(int x = 0; x < 4; x++){
				float sum = 0;
				for(int e = 0; e < 4; e++){
					sum += this.elements[x + e * 4] * matrix.elements[e + y * 4];
				}
				result.elements[x + y * 4] = sum;
			}
		}
		return result;
	}
	
	
	public Matrix4f lookAt(Vector3f eye, Vector3f up, Vector3f view){
		Matrix4f result = new Matrix4f();
		Matrix4f leftHand = new Matrix4f();
		Matrix4f rightHand = new Matrix4f();
		Vector3f right = new Vector3f();
		
		eye = Vector3f.normalize(eye);
		up = Vector3f.normalize(up);
		view = Vector3f.normalize(view);
		
		right = Vector3f.cross(view, up);
		
		leftHand.elements[0] = right.x;
		leftHand.elements[1] = right.y;
		leftHand.elements[2] = right.z;
		leftHand.elements[3] = 0.0f;
		
		leftHand.elements[4] = up.x;
		leftHand.elements[5] = up.y;
		leftHand.elements[6] = up.z;
		leftHand.elements[7] = 0.0f;
		
		leftHand.elements[8] = -view.x;
		leftHand.elements[9] = -view.y;
		leftHand.elements[10] = -view.z;
		leftHand.elements[11] = 0.0f;
		
		leftHand.elements[12] = 0.0f;
		leftHand.elements[13] = 0.0f;
		leftHand.elements[14] = 0.0f;
		leftHand.elements[15] = 1.0f;
		
		rightHand = identity();
		rightHand.elements[3] = -eye.x;
		rightHand.elements[7] = -eye.y;
		rightHand.elements[11] = -eye.z;
		
		result = leftHand.multiply(rightHand);
		
		return result;
	}
	
	
	public FloatBuffer toFloatBuffer(){
		return BufferUtils.createFloatBuffer(elements);
	}
	
}
