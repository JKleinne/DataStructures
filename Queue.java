package DataStructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The Queue class defines a first-in first-out type data structure. It is quite literally
 * an implementation of a queue (i.e. single-file waiting lines). It uses an inner-class
 * definition of a <i>Node</i> to implement a first-in first-out fashion, LinkedList style.
 * Thus, every element in the Queue is placed inside a Node object. It contains the following
 * properties: <i>head</i> of type Node pointing to the first Node of the queue,
 * <i>tail</i> of type Node pointing to the last Node of the queue and <i>count</i>
 * of type int indicating the number of elements (or Nodes) in the queue. The Queue class
 * also implements the Iterable interface, useful for iterating through the collection. Thus,
 * it must provide its own definition of the <i>iterator</i> method returning an <i>Iterator<T></i>.
 *
 * @param <T> The type of the Generic Queue
 *
 * @author Jonnie Quezada
 * @version 1
 * @since March 2018
 */
public class Queue<T> implements Iterable<T> {
    private Node head; //Points to the first Node
    private Node tail; //Points to the last Node

    private int count;

    /**
     * The Node class serves as the basis or the skeleton for the Queue's elements. The Queue
     * is filled and only filled with <i>Node</i> objects. The Node class contains the
     * following properties: <i>data</i> of type T holding the element or data, <i>next</i>
     * of type <i>Node</i> which is a pointer to the Node that follows the current Node.
     */
    private class Node {
        private T data;
        private Node next;

        public Node() {
            data = null;
            next = null;
        }

        public Node(T elem) {
            data = elem;
            next = null;
        }
    }

    /**
     * Implementation of the abstract method <i>Iterator<T> iterator();</i> from the
     * Iterable<T> interface. It serves as a tool for iterating through the Queue
     * and retrieving data from it without destroying it. It returns an Iterator<T>
     * object, which is an implementation of the Iterator<T> interface, containing the following
     * methods: <i>hasNext</i> returning a boolean returning true if the Queue still has Nodes
     * to process or iterate to and false otherwise, <i>next</i> returning an object of type T
     * which is the <i>data</i> of the current <i>Node</i>.
     * @return an Iterator<T> object, which is an implementation of the Iterator<T> interface
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node current = head;

            /**
             * Returns true if the Queue still has Node to process or iterate to and false otherwise
             * @return true if the Queue still has Node to process or iterate to and false otherwise
             */
            @Override
            public boolean hasNext() {
                return current != null;
            }

            /**
             * Returns an object of type T which is the <i>data</i> of the current <i>Node</i>
             * @return an object of type T which is the <i>data</i> of the current <i>Node</i>
             */
            @Override
            public T next() {
                T elem;

                if(current == null)
                    throw new NoSuchElementException();
                else {
                    elem = current.data;
                    current = current.next;
                }

                return elem;
            }
        };
    }

    /**
     * Constructs a Queue with no values. Internally sets the <i>head</i> and <i>tail</i>
     * to <b>null</b> and sets the <i>count</i> to <b>0</b>
     */
    public Queue() {
        head = null;
        tail = null;

        count = 0;
    }

    /**
     * Add an element at the end of the Queue
     *
     * @param elem The element to add at the end of the Queue
     */
    public void addToQueue(T elem) {
        Node add = new Node(elem);

        if(count == 0) {
            head = add;
            tail = add;
        }
        else {
            tail.next = add;
            tail = tail.next;
        }

        count++;
    }

    /**
     * Delete the first-in or the first element of the Queue
     *
     * @return The element deleted from the Queue
     */
    public T deleteFromQueue() {
        if(!isEmpty()) {
            T elem = head.data;
            head = head.next;

            count--;

            return elem;
        }
        else
            throw new NoSuchElementException();
    }

    /**
     * Returns true if the Queue is empty, false otherwise.
     * Internally checks if <i>count</i> is <b>0</b>.
     *
     * @return true if the Queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns the size of the Queue (the value of <i>count</i>).
     * @return the size of the Queue
     */
    public int size() {
        return count;
    }

    /**
     * Returns the first element of the Queue without destroying it
     * @return the first element of the Queue without destroying it
     */
    public T lookup() {
        if(!isEmpty())
            return head.data;
        else
            throw new NoSuchElementException();
    }

    /**
     * Clear the Queue of its elements, making it empty. Internally sets
     * the <i>head</i> and <i>tail</i> to <b>null</b> and sets the <i>count</i>
     * to <b>0</b>
     */
    public void clearQueue() {
        head = null;
        tail = null;

        count = 0;
    }

    /**
     * Returns a String representation of the Queue, that is, a String containing
     * describing the Queue, or a String representation of all the elements in
     * the Queue.
     *
     * @return a String representation of the Queue
     */
   public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("{");

        /*
        Iterator<T> iterator = iterator();
        while(iterator.hasNext()) {
            builder.append(iterator.next() + ", ");
        }
        */

       this.forEach(element -> builder.append(element + ", "));
        /*
         * For cosmetic reasons: toString() as such: {1, 2, 3}
         * and not {1, 2, 3, }
         */
        builder.deleteCharAt(builder.length() - 2)
                .deleteCharAt(builder.length() - 1)
                .append("}");

        return builder.toString();
   }

}
