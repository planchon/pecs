package systems;

import core.Engine;
import core.Entity;

public abstract class ECSSystem {
	public int priority;
	private Engine engine;
	
	public ECSSystem() {
		this(0);
	}
	
	public ECSSystem(int priority) {
		this.priority = priority;
	}
	
	public void addedToEngine(Engine engine) {}
	
	public void removedFromEngine(Engine engine) {}
	
	public void update(double dt) {}
	
	public void update(Entity e, double dt) {}
	
	final void _addedToEngine(Engine engine) {
		this.engine = engine;
		addedToEngine(engine);
	}
	
	final void _removeFromEngine(Engine engine) {
		this.engine = null;
		removedFromEngine(engine);
	}
}
