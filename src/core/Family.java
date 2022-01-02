package core;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import utils.Bits;

public class Family {
	private static Map<String, Family> string_to_family = new HashMap();
	private static Map<Family, Entity[]> family_to_entity = new HashMap();
	private static int familyIndex = 0;
	private static final Query query = new Query();
	
	private final Bits all;
	private final Bits any;
	private final Bits exclude;
	private final int index;
	
	private Family(Bits all, Bits any, Bits exclude) {
		this.all = all;
		this.any = any;
		this.exclude = exclude;
		this.index = familyIndex++;
	}
	
	public int getIndex() {
		return index;
	}
	
	public boolean matches(Entity entity) {
		Bits entityComponentsBits = entity.componentBits;
		
		if (!entityComponentsBits.containsAll(all)) {
			return false;
		}
		
		if (!any.isEmpty() && !any.intersects(entityComponentsBits) ) {
			return false;
		}
		
		if (!exclude.isEmpty() && exclude.intersects(entityComponentsBits)) {
			return false;
		}
		
		return true;
	}
	
	public static class Query {
		private Bits all = new Bits();
		private Bits any = new Bits();
		private Bits exclude = new Bits();
		
		Query() {}
		
		public Query reset() {
			all = new Bits();
			any = new Bits();
			exclude = new Bits();
			return this;
		}
		
		public final Query all(Class<? extends Component>... cTypes) {
			all = ComponentType.getBitsFor(cTypes);
			return this;
		}
		
		public final Query any(Class<? extends Component>... cTypes) {
			any = ComponentType.getBitsFor(cTypes);
			return this;
		}
		
		public final Query exclude(Class<? extends Component>... cTypes) {
			exclude = ComponentType.getBitsFor(cTypes);
			return this;
		}
		
		public Family get() {
			String hash = getFamilyHash(all, any, exclude);
			Family family = string_to_family.get(hash);
			
			if (family == null) {
				family = new Family(all, any, exclude);
				string_to_family.put(hash, family);
			}
			
			return family;
		}
	}
	
	public static final Query all (Class<? extends Component>... ctype) {
		return query.reset().all(ctype);
	}

	public static final Query any (Class<? extends Component>... ctype) {
		return query.reset().any(ctype);
	}

	public static final Query exclude (Class<? extends Component>... ctype) {
		return query.reset().exclude(ctype);
	}

	
	private static String getFamilyHash (Bits all, Bits any, Bits exclude) {
		StringBuilder stringBuilder = new StringBuilder();
		if (!all.isEmpty()) {
			stringBuilder.append("{all:").append(getBitsString(all)).append("}");
		}
		if (!any.isEmpty()) {
			stringBuilder.append("{one:").append(getBitsString(any)).append("}");
		}
		if (!exclude.isEmpty()) {
			stringBuilder.append("{exclude:").append(getBitsString(exclude)).append("}");
		}
		return stringBuilder.toString();
	}

	private static String getBitsString (Bits bits) {
		StringBuilder stringBuilder = new StringBuilder();

		int numBits = bits.length();
		for (int i = 0; i < numBits; ++i) {
			stringBuilder.append(bits.get(i) ? "1" : "0");
		}

		return stringBuilder.toString();
	}
	
}
