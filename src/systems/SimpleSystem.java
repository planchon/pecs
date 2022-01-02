package systems;

import core.Family;
import utils.ImmutableArray;

import java.util.ArrayList;
import java.util.List;

import core.Engine;
import core.Entity;

public abstract class SimpleSystem extends ECSSystem {
	private Family family;
	
	private ImmutableArray<Entity> entities;
	
	public SimpleSystem(Family family) {
		super(0);
	}
	
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(family);
	}
	
	public void _update(double dt) {
		startProcessing();
		for (int i = 0; i < entities.size(); i++) {
			update(entities.get(i), dt);
		}
		endProcessing();
	}
	
	public abstract void update(Entity entity, double dt);
	
	public void startProcessing() {}
	public void endProcessing() {}
}
