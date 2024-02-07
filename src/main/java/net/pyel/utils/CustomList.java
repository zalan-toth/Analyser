package net.pyel.utils;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * Custom List
 *
 * @author Zalán Tóth
 */


public class CustomList<F> implements Iterable<F>, List<F> {
	public CustomNode<F> first = null;
	public CustomNode<F> last = null;
	public int size = 0;

	public int getSize() {
		return size;
	}


	@Override
	public boolean add(Object element) {
		CustomNode<F> data = new CustomNode<>();
		data.setContents((F) element);
		//data.next = first;
		//first = data;
		if (first == null)
			first = last = data;
		else {
			last.next = data;
			last = data; //connect
		}
		size++;
		return false;
	}


	@Override
	public F remove(int position) {
		int index = position; //It is here because previously the list was in reverse order!
		if (!isValidIndex(index)) {
			return null;
		}

		if (index == 0) {
			first = first.next;
			size--;
			return null;
		}

		CustomNode<F> current = first;
		CustomNode<F> previous = null;
		int currentIndex = 0;

		while (current != null) {
			if (currentIndex == index) { //found the node to delete
				previous.next = current.next; // unlink the item
				size--;
				return current.getContents();
			}
			previous = current;
			current = current.next;
			currentIndex++;
		}

		return null;
	}

	@Override
	public boolean isEmpty() {
		return (first == null);
	}

	@Override
	public boolean contains(Object o) {
		return false;
	}

	@Override
	public int indexOf(Object o) {
		int index = 0;
		for (F current : this) {
			if (o.equals(current)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		return 0;
	}

	@Override
	public ListIterator<F> listIterator() {
		return new CustomListIterator<>(first, 0);
	}

	@Override
	public ListIterator<F> listIterator(int index) {
		return new CustomListIterator<>(first, index);
	}

	@Override
	public List<F> subList(int fromIndex, int toIndex) {
		return null;
	}

	//ModifiableObservableListBase methods begin
	@Override
	public int size() {
		return size;
	}

	protected void doAdd(int index, F element) {
		this.add(index, element);
	}

	protected F doSet(int index, F element) {
		F oldVal = this.get(index);
		this.remove(index);
		this.add(index, element);
		return oldVal;
	}

	protected F doRemove(int index) {
		return this.remove(index);
	}

	//ModifiableObservableListBase methods end
	@Override
	public void clear() {
		first = null;
	}

	public void removeAll() {
		first = null;
	}

	public F getContents() {
		if (first != null) {
			return first.getContents();
		}
		return null;
	}

	public boolean isValidIndex(int index) {
		return ((index < size) && (index >= 0));
	}

	@Override
	public F get(int index) {
		/*if (isValidIndex(index)) {
			int position = 0;
			for (int i = 0; i < size; i++) { //Object o : this
				if (position == index) {
					return (F) this.get(i);
				}
				position++;
			}

		}
		return null;*/
		if (!isValidIndex(index)) {
			return null;
		}

		CustomNode<F> current = first;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}

		return current.getContents();
	}

	@Override
	public F set(int index, F element) {
		if (!isValidIndex(index)) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}

		CustomNode<F> current = first;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}

		F oldValue = current.getContents();
		current.setContents(element);
		return oldValue;
	}

	@Override
	public void add(int index, Object element) {

	}

	@Override
	public Iterator<F> iterator() {
		return new CustomIterator<F>(first);
	}

	@Override
	public Object[] toArray() {
		return new Object[0];
	}


	@Override
	public boolean remove(Object o) {
		if (first == null) {
			return false;
		}

		if (o.equals(first.getContents())) {
			first = first.next;
			if (first == null) { // If list becomes empty
				last = null;
			}
			size--;
			return true;
		}

		CustomNode<F> current = first;
		CustomNode<F> previous = null;

		while (current != null) {
			if (o.equals(current.getContents())) {
				previous.next = current.next;
				if (current == last) { // If the removed node is the last node
					last = previous;
				}
				size--;
				return true;
			}
			previous = current;
			current = current.next;
		}

		return false; // Element not found
	}

	@Override
	public boolean addAll(Collection c) {
		boolean modified = false;
		for (Object item : c) {
			if (add(item)) { // Using the add method
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public boolean addAll(int index, Collection c) {
		return false;
	}

	@Override
	public void replaceAll(UnaryOperator operator) {

	}

	@Override
	public void sort(Comparator c) {

	}

	@Override
	public boolean retainAll(Collection c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection c) {
		return false;
	}

	@Override
	public boolean containsAll(Collection c) {
		return false;
	}

	@Override
	public Object[] toArray(Object[] a) {
		return new Object[0];
	}


}