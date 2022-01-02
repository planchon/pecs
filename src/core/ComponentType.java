package core;

import java.util.HashMap;
import java.util.Map;

import utils.Bits;

public final class ComponentType {
	private static Map<Class<? extends Component>, ComponentType> assignedComponent = new HashMap();
	private static int typeIndex = 0;
	
	private final int index;
	
	private ComponentType() {
		index = typeIndex++;
	}
	
	public int getIndex() {
		return index;
	}
	
	public static ComponentType getFor(Class<? extends Component> componentType) {
		ComponentType ctype = assignedComponent.get(componentType);
		
		if (ctype == null) {
			ctype = new ComponentType();
			assignedComponent.put(componentType, ctype);
		}
		
		return ctype;
	}
	
	public static int getIndexFor(Class<? extends Component> ctype) {
		return getFor(ctype).getIndex();
	}
	
	public static Bits getBitsFor(Class<? extends Component>... componentTypes) {
		Bits bits = new Bits();
		
		for (int i = 0; i < componentTypes.length; i++) {
			bits.set(getIndexFor(componentTypes[i]));
		}
		
		return bits;
	}
}
