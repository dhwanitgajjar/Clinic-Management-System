package com.example.cs213_project3.util;
import java.util.Iterator;

/**
 * An abstract class for implementing lists.
 * 
 * @author Harb Chauhan
 */
public class List<E> implements Iterable<E> {
	
	private E[] objects;
	private int size;
	
	/**
	 * Default constructor with an initial capacity of 4.
	 */
	public List() {
		this.objects = (E[]) new Object[4];
		this.size = 0;
	} 
	/**
	 * Finds the index of the given element in the list if it exists.
	 * Returns -1 if the element is not found.
	 * 
	 * @param e the element to find
	 * @return the index of the element if found, -1 if not found
	 */
	private int find(E e) {
		for (int i = 0; i < size; i++) {
			if (e.equals(objects[i])) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Increases the size of the array by 4 when the list is full.
	 * 
	 * This method is used internally to increase the size of the array
	 * when the list is full. It is not intended to be used by the user.
	 */
	private void grow() {
		E[] new_list = (E[]) new Object[this.size+4];

		for (int i = 0; i < this.size; i++) {
			new_list[i] = this.objects[i];
		}

		this.objects = new_list;
	}
	/**
	 * Checks if the given element is in the list.
	 * 
	 * @param e the element to check for
	 * @return true if the element is in the list, false if not
	 */
	public boolean contains(E e) {
		if (this.size <= 0) {
			return false;
		}

		for (int i = 0; i < this.size; i++) {
			if (e.equals(objects[i])) {
				return true;
			}
		}

		return false;
	}
	/**
	 * Adds the given element to the end of the list.
	 * 
	 * This method adds the given element to the end of the list. If the list
	 * is full, it will increase the size of the list by 4 before adding the
	 * element. The element will be added to the end of the list, making it the
	 * most recently added element.
	 * 
	 * @param e the element to add
	 */
	public void add(E e) {
		if (this.size == this.objects.length) {
			this.grow();
		}

		this.objects[this.size] = e;
		this.size++;
	}

	/**
	 * Removes the given element from the list if it exists.
	 * 
	 * This method removes the given element from the list if it exists. If the
	 * element is found, it is removed and the size of the list is decreased by
	 * one. If the element is not found, the method does nothing.
	 * 
	 * @param e the element to remove
	 */
	public void remove(E e) {
		int index = this.find(e);
		if (index == -1) {
			return;
		}
		this.objects[index] = null;
		for (int i = index; i < this.size - 1; i++) {
			this.objects[i] = this.objects[i + 1];
		}
		this.size--;
	}

	/**
	 * Checks if the list is empty.
	 * 
	 * This method checks if the list is empty by comparing the size of the list to 0.
	 * 
	 * @return true if the list is empty, false otherwise
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Returns the number of elements in the list.
	 * 
	 * This method returns the current size of the list.
	 * 
	 * @return the number of elements in the list
	 */
	public int size() {
		return this.size;
	}

	
	/**
	 * Returns an iterator over the elements in this list in proper sequence.
	 * The iterator is fail-fast, meaning it will throw a ConcurrentModificationException
	 * if the list is modified while iterating over it.
	 * 
	 * @return an iterator over the elements in this list
	 */
	public Iterator<E> iterator() {
		return new ListIterator<E>();
	}

	/**
	 * Returns the element at the specified position in this list.
	 * 
	 * @param index index of the element to return
	 * @return the element at the specified position in this list, or null if the index is out of range
	 */
	public E get(int index) {
		if (index > this.size-1 || index < 0) {
			return null;
		}

		return this.objects[index];
	} 

	/**
	 * Replaces the element at the specified position in this list with the specified element.
	 * 
	 * If the element is null, it is removed from the list.
	 * If the index is out of range, the method does nothing.
	 * 
	 * @param index index of the element to replace
	 * @param element element to be stored at the specified position
	 */
	public void set(int index, E e) {

		if (index<0 || index>=this.size) {
			return;
		}
		if (e==null) {
			this.size--;
		}
		this.objects[index] = e;
	} 

	/**
	 * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element.
	 * 
	 * @param e element to search for
	 * @return the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element
	 */
	public int indexOf(E e) {
		return this.find(e);
	} 

	/**
	 * Returns the index of the last occurrence of the specified element in this list, or -1 if this list does not contain the element.
	 */
	private class ListIterator<E> implements Iterator<E> {

		private int index = 0;

		/**
		 * Returns true if the iteration has more elements.
		 * 
		 * @return true if the iteration has more elements
		 */
		public boolean hasNext() {
			return index < size && size >= 0;
		}
		
		/**
		 * Returns the next element in the iteration. If the iteration has no more elements, returns null.
		 * 
		 * @return the next element in the iteration, or null if the iteration has no more elements
		 */
		public E next() {
			if (List.this.size == 0 || index >= size) {
				return null;
			}
			return (E) List.this.objects[index++];
		} 
	}

}
