package core;

import systems.ECSSystem;
import utils.ImmutableArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Engine {
	private final List<Entity> entities = new ArrayList<>();
	private Set<Entity> entities_set = new HashSet();
	
	private Map<Class<? extends ECSSystem>, ECSSystem> systemByClass = new HashMap();
	private List<ECSSystem> systems = new ArrayList<>();
	private List<ECSSystem> renderSystem = new ArrayList<>();
	
	private FamilyManager fm = new FamilyManager(entities);
	
	public Engine() {}
	
	public Entity createEntity() {
		return new Entity();
	}
	
	public void addEntity(Entity e) {
		_addEntity(e);
	}
	
	public void addSystem(ECSSystem system) {
		Class<? extends ECSSystem> systemType = system.getClass();
		ECSSystem oldSystem = getSystem(systemType);

		// the system was already in the engine
		if (oldSystem != null) {
			removeSystem(oldSystem);
		}

		systems.add(system);
		system.addedToEngine(this);
		systemByClass.put(systemType, system);
	}

	public void addRenderSystem(ECSSystem system) {
		Class<? extends ECSSystem> systemType = system.getClass();
		ECSSystem oldSystem = getSystem(systemType);

		// the system was already in the engine
		if (oldSystem != null) {
			removeSystem(oldSystem);
		}

		renderSystem.add(system);
		system.addedToEngine(this);
		systemByClass.put(systemType, system);
	}
	
	public <T extends ECSSystem> T getSystem(Class<T> systemType) {
		return (T) systemByClass.get(systemType);
	}
	
	public void removeEntity(Entity e) {
		_removeEntity(e);
	}
	
	public void removeSystem(ECSSystem s) {
		systemByClass.remove(s.getClass());
		systems.remove(s);
	}
	
	public void removeAllEntities() {
		// TODO: change that, thats 99% bad i think
		entities_set = new HashSet();
	}
	
	public void removeAllSystem() {
		// TODO: change that, thats 99% bad i think
		systems = new ArrayList<>();
		systemByClass = new HashMap();
	}
	
	protected void _addEntity(Entity e) {
		if (entities_set.contains(e)) {
			throw new IllegalArgumentException("Entity is already in the engine" + e);
		}
		
		entities.add(e);
		entities_set.add(e);
		fm.updateFamilyMembership(e);
	}
	
	public void _removeEntity(Entity e) {
		boolean removed = entities_set.remove(e);
		
		if (removed) {
			entities.remove(e);
		}
	}
	
	public void update(double dt) {
		for (int i = 0; i < systems.size(); i++) {
			ECSSystem updating = systems.get(i);
			updating.update(dt);
		}
	}

	public void render(double dt) {
		for (int i = 0; i < renderSystem.size(); i++) {
			ECSSystem updating = renderSystem.get(i);
			updating.update(dt);
		}
	}

	public ImmutableArray<Entity> getEntitiesFor(Family family) {
		return fm.getEntitiesFor(family);
	}
}
