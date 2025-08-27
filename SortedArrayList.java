package comprehensive;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class represents an array that maintains its elements
 * to remain in sorted order.
 * @param <Type> the type of the elements stored in the array
 */
public class SortedArrayList<Type extends Comparable<? super Type>> implements Iterable<Type> {

    private Comparator<? super Type> cmp;
    private Object[] array;
    private int size;

    /**
     * Constructor for a new SortedArrayList object that
     * initializes the backing array.
     */
    public SortedArrayList() {
        this.array = new Object[16];
    }

    /**
     * Removes all items from this array. The array will be empty after this method
     * call.
     */
    public void clear() {
        this.array = new Object[16];
        this.size = 0;
    }

    /**
     * Determines if there is an item in this array that is equal to the specified
     * item.
     *
     * @param element - the item sought in this array
     * @return true if there is an item in this array that is equal to the input item;
     *         otherwise, returns false
     */
    public boolean contains(Type element) {
    	if(size == 0)
        {
    		return false;
    	}
        if (getItemAtIndex(binarySearch(element)) == null) {
            return false;
        }
        else {
            return compare(getItemAtIndex(binarySearch(element)), element) == 0;
        }

    }

    /**
     * Ensures that this array contains the specified item.
     *
     * @param element - the item whose presence is ensured in this array
     * @return true if this array changed as a result of this method call (that is, if
     *         the input item was actually inserted); otherwise, returns false
     */
    public void insert(Type element) {
    	int insertIndex = 0;
    	if(size > 0)
    	{
    		insertIndex = this.binarySearch(element);
    	} 
        size++;

        if (size > array.length) {

            Object[] newArray = new Object[array.length * 2];
            for (int i = 0; i < this.array.length; i++) {
                newArray[i] = this.array[i];
            }

            this.array = newArray;

        }
        for (int i = size - 1; i > insertIndex; i--) {
            this.array[i] = this.array[i - 1];
        }

        this.array[insertIndex] = element;

    }

    /**
     * Returns true if this array contains no items.
     */
    public boolean isEmpty() {
    	for (Object o: this.array) {
            if (o != null) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the largest item in this array.
     *
     * @throws NoSuchElementException if the array is empty
     */
    public Type max() throws NoSuchElementException {
    	if(size == 0)
    	{
    		throw new NoSuchElementException();
    	}
    	
        return getItemAtIndex(size - 1);
    }

    /**
     * Returns the smallest item in this array.
     *
     * @throws NoSuchElementException if the array is empty
     */
    public Type min() throws NoSuchElementException {
    	if(size == 0)
    	{
    		throw new NoSuchElementException();
    	}
    	
        return getItemAtIndex(0);
    }

    /**
     * Returns the number of items in this array.
     */
    public int size() {
        return size;
    }

    /**
     * Returns an array containing all the items in this array, in sorted
     * order.
     */
    public Object[] toArray() {
        Object[] returnArray = new Object[size];
        
        for(int index = 0; index < size; index ++)
        {
        	returnArray[index] = getItemAtIndex(index);
        }
        
        return returnArray;
    }

    /**
     * Runs binary search on the backing array in order
     * to locate the proper index for the passed in goal.
     *
     * @param goal the item being binary searched for
     * @return the index of the goal in the array, if it is in the array
     *         the index for where the goal should be in the array, if it
     *         is not currently in the array.
     */
    private int binarySearch(Type goal) {
    	if(size == 0)
    	{
    		return -1;
    	}
    	
      if (this.compare(goal, getItemAtIndex(size - 1)) > 0) {
            return size;
        }
        
        int low = 0, high = size - 1, mid = 0;
        while(low <= high) {
            mid = (low + high) / 2;

            if(this.compare(goal, getItemAtIndex(mid)) == 0)
                break;
            else if(this.compare(goal, getItemAtIndex(mid)) < 0)
                high = mid - 1;
            else
                low = mid + 1;

            if ((mid >= 1) && this.compare(goal, getItemAtIndex(mid - 1)) > 0
            && (this.compare(goal, getItemAtIndex(mid)) < 0)) {
                return mid;
            }

        }
        return mid;
    }

    /**
     * Compare two objects of the Type that this backing array stores
     * @param o1 the first object
     * @param o2 the second object
     * @return a numerical value that represents which object should canonically
     * come first.
     */
    private int compare(Type o1, Type o2) {

        boolean useComparator = (this.cmp != null);

        if (useComparator) {
            return this.cmp.compare(o1, o2);
        }
        else {
            return o1.compareTo(o2);
        }

    }

    /**
     * Private helper method that gets the Object from the backing array
     * at a given index, with the correct Type.
     * @param index the index of the item to locate
     * @return the item to be located, with the correct Type (not Object)
     */
    @SuppressWarnings("unchecked")
    private Type getItemAtIndex(int index) {
        if (this.isEmpty() || index < 0 || index > this.size - 1) {
            return null;
        }
        else {
            return (Type) (array[index]);
        }
    }

    public Type remove(int index)
    {
        Object removed = array[index];
        if(removed == null)
        {
            return null;
        }
        array[index] = null;

        for(int i = index + 1; i < size; i ++)
        {
            array[i - 1] = array[i];
        }

        size--;
        return (Type) removed;
    }

    @Override
    public Iterator<Type> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Type>
    {
        int index = 0;
        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public Type next() {
            Type next = (Type) array[index];
            index++;
            return next;
        }
    }
}
