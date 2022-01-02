package tests;

import core.Component;

public class MovementComponent implements Component {
	public float vx, vy;
	
	public MovementComponent(float vx, float vy) {
		this.vx = vx;
		this.vy = vy;
	}
}
