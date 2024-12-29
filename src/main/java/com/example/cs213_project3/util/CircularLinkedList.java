package com.example.cs213_project3.util;
import com.example.cs213_project3.Technician;

import java.util.Iterator;

/**
 * Implements a circular linked list.
 *
 * @author Dhwanit Gajjar
 */
public class CircularLinkedList<E> implements Iterable<E> {

    private Technician head;
    private Technician tail;
    private int size;
    private Technician current;

    /**
     * Creates an empty circular linked list.
     */
    public CircularLinkedList() {
        this.size=0;
    }

    /**
     * Adds the given technician to the beginning of the list and updates the tail to point to the new head.
     * If the list is empty, it sets the head and tail to the given technician.
     * It also updates the current pointer to point to the newly added technician.
     * @param technician the technician to add to the beginning of the list
     */
    public void add(Technician technician) {

        if (head == null) {
            head = tail = technician;
            head.setNext(head);
            tail.setNext(head);
            size++;
            current = head;
            return;
        }

        technician.setNext(head);
        head = technician;
        tail.setNext(head);
        current = head;
        size++;
    }

    /**
     * Returns the number of elements in the circular linked list.
     *
     * @return the number of elements in the list
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the current technician in the circular linked list.
     * @return the current technician
     */
    public Technician getCurrent() {
        return current;
    }

    /**
     * Sets the current technician in the circular linked list to the given technician.
     * This method is used to move the current pointer to a different technician in the list.
     * @param current the new current technician
     */
    public void setCurrent(Technician current) {
        this.current = current;
    }

    /**
     * Returns the head of the circular linked list.
     * @return the head technician
     */
    public Technician getHead() {
        return head;
    }

    /**
     * Checks if the circular linked list is empty.
     * 
     * This method checks if the list is empty by comparing the size of the list to 0.
     * 
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns an iterator over the elements in this circular linked list.
     * The iterator will traverse the list in a circular manner, starting from the current technician.
     * @return an iterator over the elements in the circular linked list
     */
    public Iterator<E> iterator() {
		return new ListIterator<E>();
	}

    /**
     * An inner class that implements the Iterator interface.
     * This class provides an iterator that can traverse the list in a circular manner, starting from the current technician.
     */
    private class ListIterator<E> implements Iterator<E> {

		private Technician curr = CircularLinkedList.this.current;

        /**
         * Returns true if the list has more elements, false otherwise.
         * This method is used to check if there are more elements in the list.
         * It checks if the list is empty or if the iterator has reached the end of the list.
         * @return true if the list has more elements, false otherwise
         */
		public boolean hasNext() {
			if (CircularLinkedList.this.size == 0 || curr.getNext() == current) {
				return false;
			}
			return true;
		}
		
        /**
         * Returns the next element in the circular linked list.
         * If the list is empty or the iterator has reached the end of the list, it returns null.
         * @return the next element in the circular linked list
         */
		public E next() {
			if (CircularLinkedList.this.size == 0 || curr.getNext() == current) {
				return null;
			}
            
            curr = curr.getNext();

			return (E) curr;
		} 
	}

}
