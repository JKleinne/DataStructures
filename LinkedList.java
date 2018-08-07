package main;

import com.google.common.math.IntMath;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A LinkedList data structure to store Employee objects sorted chronologically by employeeID
 *
 * Assignment: 1
 *
 * @author Jonnie Quezada
 * @version 1.3
 * @since January 25 2018
 */

public class HumanResources {
    private Node head;
    private int count = 0;

    private final int ONE = 1;

    private final boolean SUCCESS = true;
    private final boolean FAIL = false;

    /*
     *********************************************************
     * Implementation
     * Of
     * Node
     *********************************************************
     */
    /**
     * Each node holds an main.Employee object and an address to the next Node
     */
    private class Node {
        private Employee employee;
        private Node next;

        /**
         * No-arg constructor to set the Node's fields to null
         */
        public Node() {
            employee = null;
            next = null;
        }

        /**
         * 1-arg constructor to initially set the main.Employee field of the node
         * @param emp main.Employee object (data)
         */
        public Node(Employee emp) {
            employee = new Employee(emp);
            next = null;
        }

        /**
         * 2-arg constructor to initially set a Node's main.Employee field and link
         * @param emp main.Employee object(data)
         * @param next Link to the next node
         */
        public Node(Employee emp, Node next) {
            employee = new Employee(emp);
            this.next = next;
        }
    }
    /*
     *********************************************************
     * End
     * Of
     * Node
     * Implementation
     * ********************************************************
     */

    /*
     ********************************************************
     * START
     * OF
     * CONSTRUCTORS
     *********************************************************
     */
    /**
     * No-arg constructor to set the LinkedList's field, head, to null
     */
    public HumanResources() {
        head = null;
    }

    /**
     * Copy constructor to copy all the elements of the parameter LinkedList object
     * to this list
     * @param hr The LinkedList to copy
     */
    public HumanResources(HumanResources hr) {
        //Only copy if the parameter object is not empty
        if(!hr.isEmpty()) {
            Node present = hr.head; //Pointer for the LinkedList hr
            Node add = new Node(present.employee);

            /*
             * Make the copy of the head of the hr LinkedList to be
             * the head of this LinkedList
             */
            add.next = this.head;
            this.head = new Node(add.employee);
            Node thyList = this.head; //Pointer for this LinkedList

            //Move past the head in the hr LinkedList
            present = present.next;
            count = IntMath.checkedAdd(count, ONE);

            while(present != null) {
                add = new Node(present.employee);
                thyList.next = add;

                present = present.next; //"Iterate" present until the last element is reached
                thyList = thyList.next; //Follow present
                count = IntMath.checkedAdd(count, ONE);
            }
        }
    }

    /*
     *********************************************************
     * END
     * OF
     * CONSTRUCTORS
     *********************************************************
     */
    /**
     * A sorted addEmployee method. Assures the list stays in chronological
     * order when new items are added
     * @param emp The main.Employee object to be added
     * @return true for successful insertion, false for failed insertion
     */
    public boolean addEmployee(Employee emp) {
        Node add = new Node(emp);

        /* Add if empty or add at
         * the beginning of the list
         */
        if(head == null || head.employee.compareTo(emp) > 0) {
            add.next = head;
            head = add;
        }
        /*
         * Add at the middle
         * or at the end
         */
        else {
            Node previous = searchPrevious(emp);
            Node present = searchPresent(previous);

            // Check if duplicate
            if(present != null && present.employee.equals(emp)) {
                return FAIL;
            }

            previous.next = add;
            add.next = present;
        }
        count = IntMath.checkedAdd(count, ONE);

        return SUCCESS;
    }

    /**
     * Iterates through the list to delete the fired employee
     * from the list
     * @return true for successful deletion, false for failed deletion
     */
    public boolean deleteEmployee(String id) {
        Employee fired = new Employee(id);
       // System.out.println("ID: " + fired.getEmployeeID());

        Node previous = searchPrevious(fired);
        Node present = searchPresent(previous);
        if(present == null || ! present.employee.equals(fired))
        	return FAIL;
        /* Deletion at the
         * beginning
         */
        if(previous == null) {
            head = present.next;
        }
        /* Deletion at the middle
         * or at the end
         */
        else {
            /*
             * Move 2 Nodes away from previous such that
             * the Node to delete is sandwiched
             */
                previous.next = present.next;
        }

        count = IntMath.checkedSubtract(count, ONE);
        return SUCCESS;
    }

    /**
     * Method allowing the retrieval of a specific main.Employee object
     * @param employeeID The ID of the main.Employee you wish to find
     * @return The main.Employee object corresponding to the parameter String employeeID
     */
    public Employee findEmployee(String employeeID) {
        /* Roll through the list until employee is found
         * or until present reaches the end of the list (null)
         */
        Employee emp = new Employee(employeeID);

        Node previous = searchPrevious(emp);
        Node present = searchPresent(previous);

        return (present == null || !present.employee.equals(emp)) ? null :
                 present.employee;
    }

    /**
     * Allows to change a specific employee's department
     * @param employeeID The employee the change will apply to
     * @param department The new department of the employee
     * @return Boolean indicating if the change was successful
     */
    public boolean changeDepartment(String employeeID, String department) {
        Employee emp = findEmployee(employeeID);
        
        if (emp != null ){
       		 emp.setDepartment(department);
       		 return SUCCESS;
		 }

        return FAIL;
    }

    /**
     * Allows to change a specific employee's salary
     * @param employeeID The employee the change will apply to
     * @param salary The adjusted salary of the employee
     * @return Boolean indicating if the change was successful
     */
    public boolean adjustSalary(String employeeID, double salary) {
        Employee emp = findEmployee(employeeID);
        if (emp != null ){
        	emp.setSalary(emp.getSalary() + salary);
        	return SUCCESS;
		}
		else
        	return FAIL;
    }

    /**
     * Calls the String:descending(Node) private function in order to get
     * a String representation of the LinkedList in decreasing order
     * @return A String representation of the LinkedList in decreasing order
     */
    public String decreasingOrder() {
        return descending(head);
    }

    /**
     * Method to return the number of employees in this list
     * @return The number of employees in this list
     */
    public int size() {
        return this.count;
    }

    /**
     * Method for indicating whether this list has items or not
     * @return true for empty, false for has items
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * The toString method is a no-arg method returning the data for one employee.
     * Note: StringBuilder is used as opposed to String concatenation for time
     * and memory purposes.
     * @return A String containing all the data for each main.Employee object in the list
     */
    public String toString() {
        StringBuilder temp = new StringBuilder("|\tID\t|\t\t   Name   \t\t|  Department  |  Salary \n" +
                "----------------------------------------------------------------------\n");

        Node present = head;

        while(present != null) {
            temp.append(present.employee + "\n");
            present = present.next;
        }

        return temp.toString();
    }

    /*
     ******************************************
     * Private
     * Methods
     ******************************************
     */

    /**
     * Iterates and returns a pointer that precedes the targeted Node
     * @param emp The target. This Employee object is used to compare with the
     *            present Node's aggregate main.Employee object for iteration purposes
     *            in order for the previous pointer to point to the Node preceding
     *            the targeted Node
     * @return The previous pointer (Node) that precedes the targeted Node
     */
    private Node searchPrevious(Employee emp) {
        Node present = head;
        Node previous = null;

        while(present != null && present.employee.compareTo(emp) < 0) {
            previous = present;
            present = present.next;
        }

        return previous;
    }

    /**
     * Returns the value of present (Node) based on the value of previous (Node)
     * @param previous The pointer (Node) that points to the node preceding the
     *                 targeted Node
     * @return The present (Node) that points to the targeted or after the targeted Node
     */
    @Contract(pure = true)
    private Node searchPresent(Node previous) {
        return (previous == null) ? head : previous.next;
    }

    /**
     * A recursive method for printing this LinkedList
     * in descending order, order by employeeID
     * @param pointer Points to the current Node
     * @return A String representation of the List
     * in descending order
     */
    @NotNull
    private String descending(Node pointer) {
        if(pointer == null)
            return "";

        return descending(pointer.next) + pointer.employee + "\n";
    }

    /**
     * Returns a boolean indicating whether or not the employeeID respects the constraints
     * @param employeeID The String to validate
     * @return A boolean indicating whether or not the employeeID respects the constraints
     */
    private boolean isInvalid(String employeeID) {
        return employeeID.equals("") || employeeID.length() != Employee.REQ_ID_SIZE;
    }

}
