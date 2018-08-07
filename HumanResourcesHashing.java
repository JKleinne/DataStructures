/**
 * Data Structure for storing Employee objects using the concept of a hashtable, which
 * is an array that may contain, at a given index, a linked list.
 *
 * @author Jonnie Quezada
 * @version Java 10 SE
 */
public class HumanResourcesHashing {
    private static int size;
    private Node table[];

    private final boolean SUCCESS = true;
    private final boolean FAIL = false;

    /**
     * Each Node holds an Employee object and an address to the next Node
     */
    private class Node {
        Node next;
        Employee data;

        /*
         *********************************************************
         * Implementation
         * Of
         * Node
         *********************************************************
         */
        /**
         * No-arg constructor to set the Node's fields to null
         */
        public Node() {
            next = null;
            data = null;
        }

        /**
         * 1-arg constructor to initially set the Employee field of the node
         * @param emp Employee object to store in the node
         */
        public Node(Employee emp) {
            next = null;
            data = new Employee(emp);
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
     * No-arg constructor to set the default table size (300)
     */
    public HumanResourcesHashing() {
        size = 300;
        table = new Node[size];

        for(var i = 0; i < table.length; i++) {
            table[i] = null;
        }
    }

    /**
     * Constructor to set the table's size to the specified amount
     * @param sizeOfCollection The table's size
     */
    public HumanResourcesHashing(int sizeOfCollection) {
        size = sizeOfCollection;
        table = new Node[size];

        for(var i = 0; i < table.length; i++) {
            table[i] = null;
        }
    }

    /**
     * Copy constructor to copy all the elements of the parameter hashtable
     * to this hashtable
     * @param other The Hashtable to copy
     */
    public HumanResourcesHashing(HumanResourcesHashing other) {
        size = other.table.length;
        this.table = new Node[size];

        Node present;
        Node thisPresent;
        for(var i = 0; i < size; i++) {

            present = other.table[i];
            if(present == null)
                continue;

            Node add = new Node(present.data);

            this.table[i] = add;
            thisPresent = this.table[i];

            present = present.next;

            while(present != null) {
                Node copy = new Node(present.data);

                thisPresent.next = copy;

                present = present.next;
                thisPresent = thisPresent.next;
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
     * Adds an employee to the Hashtable
     *
     * @param emp The Employee object to be added
     * @return true for successful insertion, false for failed insertion
     */
    public boolean addEmployee(Employee emp) {
        int hashValue = hashFunction(emp.getEmployeeID());

        Node previous = searchPrevious(emp, hashValue);
        Node present = searchPresent(previous, hashValue);
        Node add = new Node(emp);

        if(present == null) {
            add.next = table[hashValue];
            table[hashValue] = add;
            return SUCCESS;
        }
        else
            return FAIL;

    }

    /**
     * Delete the employee with the given employeeId from the Hashtable
     * @return true for successful deletion, false for failed deletion
     */
    public boolean deleteEmployee(String employeeId) {
        Employee emp = new Employee(employeeId);
        int hashValue = hashFunction(employeeId);

        Node previous = searchPrevious(emp, hashValue);
        Node present = searchPresent(previous, hashValue);

        if(present == null) {
            return FAIL;
        }
        else {
            if(previous == null)
                table[hashValue] = present.next;
            else
                previous.next = present.next;
            return SUCCESS;
        }
    }

    /**
     * Allows to change a specific employee's department
     * @param employeeId The employee the change will apply to
     * @param department The new department of the employee
     * @return true if change is successful, false otherwise
     */
    public boolean changeDepartment(String employeeId, String department) {
        Employee emp = new Employee(employeeId);
        int hashValue = hashFunction(employeeId);

        Node previous = searchPrevious(emp, hashValue);
        Node present = searchPresent(previous, hashValue);

        if(present == null)
            return FAIL;
        else {
            present.data.setDepartment(department);
            return SUCCESS;
        }
    }

    /**
     * Increment or decrement a specific employee's salary
     *
     * @param employeeId The employee the change will apply to
     * @param adjustValue The amount by which the employee's salary
     *               will be incremented (positive value) or
     *               decremented (negative value)
     * @return true if the change was successful, false otherwise
     */
    public boolean adjustSalary(String employeeId, int adjustValue) {
        Employee emp = new Employee(employeeId);
        int hashValue = hashFunction(employeeId);

        Node previous = searchPrevious(emp, hashValue);
        Node present = searchPresent(previous, hashValue);

        if(present == null)
            return FAIL;
        else {
            present.data.setSalary(present.data.getSalary() + adjustValue);
            return SUCCESS;
        }
    }

    /**
     * Returns the employee object who's employeeId matches the parameter
     * @param employeeId The Id of the Employee you wish to find
     * @return The Employee object corresponding to the parameter String employeeID
     */
    public Employee findEmployee(String employeeId) {
        Employee emp = new Employee(employeeId);
        int hashValue = hashFunction(employeeId);

        Node previous = searchPrevious(emp, hashValue);
        Node present = searchPresent(previous, hashValue);

        if(present == null)
            return null;
        else {
            return present.data;
        }
    }

    /**
     * Returns the Node preceding the targeted Node
     * @param emp The parent of the node containing this Employee objecet
     *                 we want to return
     * @param value The index of the LinkedList containing the targeted Node
     *              in the Hashtable
     * @return The previous pointer (Node) that precedes the targeted Node
     */
    public Node searchPrevious(Employee emp, int value) {
        Node previous = null;
        Node present = table[value];

        while(present != null && present.data.compareTo(emp) != 0) {
            previous = present;
            present = present.next;
        }

        return previous;
    }

    /**
     * Returns the next Node of the parameter previous Node
     *
     * @param previous Parent Node of the Node containing the employee object
     * @param value The index of the LinkedList containing the targeted Node
     *              in the Hashtable
     * @return The present (Node) that points to the targeted or after the targeted Node
     */
    public Node searchPresent(Node previous, int value) {
        if(previous == null)
            return table[value];
        else
            return previous.next;
    }

    /**
     * Hashing algorithm that takes an employeeId and returns a hashed value corresponding to
     * a specific index in the hashtable
     * @param employeeId employeeId of an Employee object
     * @return a hashed value corresponding to
     *         a specific index in the hashtable
     */
    private int hashFunction(String employeeId) {
        return Integer.parseInt(employeeId) % size;
    }

    /**
     * Returns a String representation of all the hashtable's elements
     * @return a String representation of all the hashtable's elements
     */
    public String toString(){
        int size = 0; // to count number of employees
        Employee[] temp = new Employee[table.length]; // set up a temporary array of employees
        Node present;

        for ( int j = 0; j < table.length; j++ ){ // loop to access all employees and copy them to
            // the temporary table
            present = table[j];
            while ( present!= null ){
                temp[size] = new Employee ( present.data );
                size++;
                present = present.next;
            }
        }

        for ( int i = 0; i < size - 1; i++ )    // loop to sort the employees using the selection sort method
            for ( int j = i+1; j < size; j++ )
                if( temp[i].compareTo ( temp[j] ) > 0 ) {
                    Employee tempEmp = temp[j] ;
                    temp[j] = temp[i];
                    temp[i] = tempEmp;
                }
        String out = "";
        for ( int i = 0; i < size; i++ ) { // loop to setup the output string using toString of the
            // employee class
            out += temp[i] + "\n";
        }

        return out;
    }


}
