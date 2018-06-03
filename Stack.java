import java.util.ArrayList;
import java.util.EmptyStackException;

public class Stack <T> {
    private ArrayList<T> list;

    /**
     * Constructs a Stack with no values. Internally initializes the
     * <i>list</i> global variable of type ArrayList<T>, which holds
     * the elements of the Stack.
     */
    public Stack() {
        list = new ArrayList<>();
    }

    /**
     * Copy constructor to copy the elements of this stack
     * @param stack The stack to copy
     */
    public Stack(Stack<T> stack) {
        this(); //Call the no-arg constructor to initialize list

        if(!stack.isEmpty()) {
            for (T elem : stack.list) {
                this.push(elem);
            }
        }
    }

    /**
     * Returns true if, and only if, the ArrayList<T> <i>list</i>',
     * isEmpty() method returns true ; if the ArrayList containing the Stack's
     * elements is empty.
     * @return True if the Stack is empty, false otherwise.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Add an element at the top of the stack
     * @param elem The element to add to the stack.
     */
    public void push(T elem) {
        list.add(elem);
    }

    /**
     * Returns the element at the top of the stack while removing it
     * @throws EmptyStackException if the Stack is empty while attempting
     * pop an element from the stack.
     * @return The element at the top of the stack
     */
    public T pop() {
        if(!isEmpty()) {
            return list.remove(list.size() - 1);
        }
        else
            throw new EmptyStackException();

    }

    /**
     * Returns the element at the top of the stack without removing it
     * @return The element at the top of the stack
     */
    public T lookUp() {
        T element = null;

        if(!isEmpty()) {
            element = list.get(list.size() - 1);
        }

        return element;
    }

    /*
    public String printStack() {
        String temp = "{";
        for(T elem: list) {
            temp += elem + ", ";
        }
        return temp + "}";
    }
    */
}
