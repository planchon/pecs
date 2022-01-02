package core;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.ImmutableArray;
import utils.Bits;

import javax.swing.border.EtchedBorder;

public class FamilyManager {
	List<Entity> entities;
	private Map<Family, ImmutableArray<Entity>> immutable_families = new HashMap();
	private Map<Family, ArrayList<Entity>> families = new HashMap();
	private Map<Family, Bits> family_to_bits = new HashMap();
	
	public FamilyManager(List<Entity> entities) {
		this.entities = entities;
	}
	
	public ImmutableArray<Entity> getEntitiesFor(Family family) {
		return registerFamily(family);
	}
	
	public void updateFamilyMembership(Entity e) {
		for (Family f : immutable_families.keySet()) {
			final int familyIndex = f.getIndex();
			final Bits familyBits = e.familyBits;
			
			boolean belongToFamily = familyBits.get(familyIndex);
			boolean matches = f.matches(e);
			
			// check if mistake where make
			if (belongToFamily != matches) {
				final ImmutableArray<Entity> familyEntities = immutable_families.get(f);
				if (matches) {
					familyEntities.add(e);
					familyBits.set(familyIndex);
				} else {
					familyEntities.remove(e);
					familyBits.clear(familyIndex);
				}
			}
		}
	}
	
	private ImmutableArray<Entity> registerFamily(Family family) {
		ImmutableArray<Entity> entitiesInFamily = immutable_families.get(family);
		
		if (entitiesInFamily == null) {
			ArrayList<Entity> familyEntities = new ArrayList<Entity>();
			entitiesInFamily = new ImmutableArray<Entity>(familyEntities);
			families.put(family, familyEntities);
			immutable_families.put(family, entitiesInFamily);
			
			for (Entity e : entities) {
				updateFamilyMembership(e);
			}
		}
		
		return entitiesInFamily;
	}
}
