package utils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class ImmutableArray<T> implements Iterable<T> {
	private final ArrayList<T> data; // this ref cannot change

	public ImmutableArray(ArrayList<T> array) {
		this.data = array;
	}
	
	public ImmutableArray() {
		data = new ArrayList<T>();
	}

	public void add(T value) {
		data.add(value);
	}
	
	public T get(int index) {
		return data.get(index);
	}

	public int size() {
		return data.size();
	}
	
	public void set(int index, T value) {
		data.set(index, value);
	}
	
	public boolean remove(T value) {
		return data.remove(value);
	}
	
	public T remove(int index) {
		return data.remove(index);
	}

	@Override
	public Iterator<T> iterator() {
		return data.iterator();
	}
}
