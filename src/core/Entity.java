package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Bits;

public class Entity {
	private Map<Integer, Component> components;
	public Bits componentBits;
	public Bits familyBits;
	
	public Entity() {
		components = new HashMap();
		componentBits = new Bits();
		familyBits = new Bits();
	}

	public boolean addComponent(Component c) {
		Class<? extends Component> componentClass = c.getClass();
		Component oldComponent = getComponent(c.getClass());
		
		// component already added
		if (c == oldComponent) {
			return false;
		}
		
		// component type already in entity
		// we remove it
		if (oldComponent != null) {
			_removeComponent(oldComponent.getClass());
		}

		ComponentType ctype = ComponentType.getFor(componentClass);
		components.put(ctype.getIndex(), c);
		componentBits.set(ctype.getIndex());
		
		return true;
	}
	
	public <T extends Component> T getComponent(Class<T> componentClass) {
		int index = ComponentType.getIndexFor(componentClass);
		return (T) components.get(index);
	}
	
	Component _removeComponent(Class<? extends Component> componentClass) {
		int index = ComponentType.getIndexFor(componentClass);
		Component old = components.get(index);
		
		if (old != null) {
			components.remove(old);
			componentBits.clear(index);
			
			return old;
		}
		
		return null;
	}
	
	public void removeAll() {
		// TODO: pas bon ca
		components = new HashMap();
		componentBits = new Bits();
	}
}
