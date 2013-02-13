package gwt.client.main;

import gwt.client.main.base.LivingBeing;
import gwt.client.main.base.OObject;
import gwt.client.map.MapData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class MList<T> extends MapData implements List<T>{

	List<T> list = new ArrayList<T>();

	
	public MList(List<T> people) {
		list = people;
	}
	public MList() {
		// TODO Auto-generated constructor stub
	}
	public boolean add(T a){
		return list.add(a);
		
	}
	@Override
	public String getValue() {
		return null;
	}
	@Override
	public void add(int index, T element) {
		list.add(index, element);
	}
	@Override
	public boolean addAll(Collection<? extends T> c) {
		
		return list.addAll(c);
	}
	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return list.addAll(c);
	}
	@Override
	public void clear() {
		list.clear();
	}
	@Override
	public boolean contains(Object o) {
	
		return list.contains(o);
	}
	@Override
	public boolean containsAll(Collection<?> c) {

		return list.containsAll(c);
	}
	@Override
	public T get(int index) {
		
		return list.get(index);
	}
	@Override
	public int indexOf(Object o) {
		
		return list.indexOf(o);
	}
	@Override
	public boolean isEmpty() {
		// 
		return list.isEmpty();
	}
	@Override
	public Iterator<T> iterator() {

		return list.iterator();
	}
	@Override
	public int lastIndexOf(Object o) {

		return list.lastIndexOf(o);
	}
	@Override
	public ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		return list.listIterator();
	}
	@Override
	public ListIterator<T> listIterator(int index) {
		// TODO Auto-generated method stub
		return list.listIterator(index);
	}
	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return list.remove(o);
	}
	@Override
	public T remove(int index) {
		// TODO Auto-generated method stub
		return list.remove(index);
	}
	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return list.removeAll(c);
	}
	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return list.retainAll(c);
	}
	@Override
	public T set(int index, T element) {
		// TODO Auto-generated method stub
		return list.set(index, element);
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return list.size();
	}
	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return list.subList(fromIndex, toIndex);
	}
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return list.toArray();
	}
	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return list.toArray(a);
	}
//	@Override
//	public String toString() {
//		// TODO Auto-generated method stub
//		return list.toString();
//	}
	public T getRandom() {
		return list.get(VConstants.getRandom().nextInt(size()));
	}
	@Override
	public String toString() {
		
		return ""+list;
	}
}

