import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A Binary Tree data structure to store Employee objects sorted chronologically by employeeID
 *
 * Assignment: 4
 *
 * @author Jonnie Quezada
 *
 */
public class HumanResourcesTree {
    private Node root;

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
     * Each node holds an .Employee object and an address to the right and left Node
     */
    private class Node{
        Employee data;

        Node right;
        Node left;

        /**
         * No-arg constructor to set the Node's fields to null
         */
        public Node(){
            data = null;
            right = null;
            left  = null;
        }

        /**
         * 1-arg constructor to initially set the Employee field of the node
         * @param data Employee object to store in the node
         */
        public Node(Employee data){
            this.data = new Employee(data);
            left = null;
            right = null;
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

    public static void main(String[] args) throws IOException {
        HumanResourcesTree tree = new HumanResourcesTree();

        String fileName = "/Users/jonniequezada/IdeaProjects/Tree/src/Employee.txt";

        String line = null;


        FileReader fileReader = new FileReader(fileName);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while((line = bufferedReader.readLine()) != null) {
            String[] tokens = line.split("\\s+");

            tree.addEmployee(
                    new Employee(tokens[0],
                            tokens[2] + " " + tokens[1],
                            tokens[3],
                            Double.parseDouble(tokens[4])));
        }


        bufferedReader.close();

        System.out.println(tree);

        HumanResourcesTree copyTree = new HumanResourcesTree(tree);

        System.out.println(tree.deleteEmployee("54325"));
        System.out.println(copyTree);
    }

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
    public HumanResourcesTree() {
        root = null;
    }

    /**
     * Copy constructor to copy all the elements of the parameter LinkedList object
     * to this list
     * @param tree The Tree to copy
     */
    public HumanResourcesTree (HumanResourcesTree tree){
        if (tree.root == null)
            root = null;
        else
        {
            root = new Node();
            copy(root, tree.root);
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
     * Adds an employee to the Tree
     *
     * @param emp The Employee object to be added
     * @return true for successful insertion, false for failed insertion
     */
    public boolean addEmployee(Employee emp){
        Node add = new Node(emp);
        Node parent = searchParent(emp);
        Node present = searchPresent(parent, emp);

        if (isEmpty()){
            root = add;
        }
        else {
            if(present != null){
                return FAIL;
            }

            if(parent.data.compareTo(add.data) < 0 ){
                parent.right = add;

            }
            else{
                parent.left = add;

            }
        }

        return SUCCESS;
    }

    /**
     * Delete the employee with the given employeeId from the Tree
     * @return true for successful deletion, false for failed deletion
     */
    public boolean deleteEmployee(String employeeId){
        Employee emp = new Employee(employeeId);
        Node parent = searchParent(emp);
        Node present =  searchPresent(parent,emp);

        if(present == null){
            return false;
        }

        //If node to delete is a leaf (Case 1)
        if(present.right == null && present.left == null){

            if(parent == null){
                root = null;
                return true;
            }

            else if(present.data.compareTo(parent.data) > 0){
                parent.right = null;
            }
            else {
                present.left = null;
            }
        }

        //If node to delete has a right but not a left (Case 2)
        else if(present.left == null && present.right != null){

            if(parent == null){
                root = present.right;
            }

            else{

                if(present.data.compareTo(parent.data) > 0){

                    parent.right = present.right;
                }
                else{

                    parent.left = present.left;
                }

            }
        }

        //If node to delete have a left and it doesn't have a right (Case 3)
        else if(present.left != null && present.left.right == null){

            if(parent == null){
                root = present.left;
            }
            else
            {
                if(present.data.compareTo(parent.data) < 0){

                    parent.left = present.left;
                }
                else{
                    parent.right = present.right;
                }
            }
            present.left.right = present.right;
        }
        else
        {
            Node tempParent = present.left;
            Node tempPresent = tempParent.right;

            while(tempPresent.right != null){
                tempParent = tempPresent;
                tempPresent = tempPresent.right;
            }

            present.data = tempPresent.data;

            tempParent.right = tempPresent.left;
        }

        return SUCCESS;
    }

    /**
     * Allows to change a specific employee's department
     * @param employeeId The employee the change will apply to
     * @param department The new department of the employee
     * @return Boolean indicating if the change was successful
     */
    public boolean changeDepartment(String employeeId, String department){
        Employee emp = findEmployee(employeeId);

        if(emp == null)
            return FAIL;

        emp.setDepartment(department);

        return SUCCESS;
    }
    
    /**
     * Increment or decrement a specific employee's salary
     *
     * @param employeeId The employee the change will apply to
     * @param salary The amount by which the employee's salary
     *               will be incremented (positive value) or
     *               decremented (negative value)
     * @return Boolean indicating if the change was successful
     */
    public boolean adjustSalary(String employeeId, double salary) {
        Employee emp = findEmployee(employeeId);

        if(emp == null)
            return FAIL;

        emp.setSalary(salary + emp.getSalary());

        return SUCCESS;
    }

    /**
     * Returns the employee object who's employeeId matches the parameter
     * @param employeeId The Id of the Employee you wish to find
     * @return The Employee object corresponding to the parameter String employeeID
     */
    public Employee findEmployee(String employeeId){
        Employee find = new Employee(employeeId);

        Node parent = searchParent(find);
        Node present =  searchPresent(parent, find);

        if(present == null)
            return null;

        return present.data;
    }

    /**
     * Method for indicating whether this list has items or not
     * @return true for empty, false for has items
     */
    public boolean isEmpty(){
        return root == null;
    }

    /**
     * Returns a string representation of all the tree's elements
     *
     * @return A String containing all the data for each main.Employee object in the list
     */
    public String toString() {
        return toString(root);
    }

    /**
     * Returns the parent Node of the targeted Node
     * @param employee The parent of the node containing this Employee objecet
     *                 we want to return
     * @return The previous pointer (Node) that precedes the targeted Node
     */
    private Node searchParent(Employee employee){
        Node present = root;
        Node parent = null;

        while(present != null && !(present.data.equals(employee))){

            if(present.data.compareTo(employee) > 0){
                parent = present;
                present = present.left;
            }
            else {
                parent = present;
                present = present.right;

            }
        }
        return parent;
    }

    /**
     * Returns the child of the parent node that contaings the employee object
     *
     * @param parent Parent Node of the Node containing the employee object
     * @param employee The employee object used to find the node containing this object
     * @return The present (Node) that points to the targeted or after the targeted Node
     */
    private Node searchPresent(Node parent, Employee employee){
        if(parent == null){
            return root;
        }

        if(parent.data.compareTo(employee) > 0){
            return parent.left;
        }
        else{
            return parent.right;
        }

    }

    /**
     * Mainly called by the copy constructor to copy another Binary Search
     * Tree's elements. Similar to an inorder traversal in which the left subtrees
     * are copied first followed by the right but instead of starting to copy
     * as soon as the left-most child is reached and work its way back up, it starts
     * to copy from the root downwards.
     *
     * @param thisPresent "this" binary tree's current or present node
     * @param otherPresent the tree to copy's current or present node
     */
    private void copy(Node thisPresent, Node otherPresent) {
        if(otherPresent == null)
            return;

        if(thisPresent == null) {
            thisPresent = new Node();

            thisPresent.data = otherPresent.data;
            thisPresent.right = otherPresent.right;
            thisPresent.left = otherPresent.left;
        }

        copy(thisPresent.left, otherPresent.left);

        thisPresent.data = otherPresent.data;
        thisPresent.right = otherPresent.right;
        thisPresent.left = otherPresent.left;

        copy(thisPresent.right, otherPresent.right);
    }

    /**
     * Mainly called by the no-arg toString method to
     * print the elements of the Binary Search Tree
     * using the inorder traversal
     * @param current The current node
     * @return The String representation of the tree's elements
     */
    private String toString(Node current){
        if(current == null)
            return "";
        else
            return toString(current.left) + current.data + toString(current.right);
    }

}
