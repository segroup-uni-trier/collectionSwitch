package de.heidelberg.pvs.diego.collectionswitch.context;

import java.util.Map;

public interface MapAllocationContext  extends AllocationContextUpdatable<MapCollectionType> {
	
	public <K, V> Map<K, V> createMap();

	public <K, V> Map<K, V> createMap(int initialCapacity);
	
	public <K, V> Map<K, V> createMap(int initialCapacity, float loadFactor);

	public <K, V> Map<K, V> createMap(Map<K, V> map);
	

}
