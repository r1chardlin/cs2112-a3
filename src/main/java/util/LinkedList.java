package util;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.NoSuchElementException;

/**
 * Represents a Basic LinkedList<E>
 * Some overridden methods are left blank because they are not needed
 * @param <E> The Object type to be stored
 */
public class LinkedList<E> implements Iterable<E>, Collection<E>, Queue<E>
{
    /**
     * Represents a node which is stored in this LinkedList
     * @param <E> The Object type of this LinkedList
     */
    class Node<E>
    {
        private E data;
        private Node next;

        public Node(E data)
        {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    /**
     * Constructs a LinkedList
     */
    public LinkedList()
    {
        head = tail = null;
        size = 0;
    }

    /**
     * Retrieves the size of the LinkedList
     * @return
     */
    @Override
    public int size()
    {
        return size;
    }

    /**
     * Determines whether this LinkedList is Empty
     * @return whether this LinkedList is Empty
     */
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Determines whether this LinkedList contains o
     * @param o element whose presence in this collection is to be tested
     * @return whether this LinkedList contains o
     */
    @Override
    public boolean contains(Object o)
    {
        Node curr = head;
        while (curr != null)
        {
            if (o.equals(curr.data))
            {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    /**
     * Creates an Iterator<E> that iterates through the nodes of this LinkedList
     * @return the Iterator<E> for this LinkedList
     */
    @Override
    public Iterator<E> iterator()
    {
        return new Iterator<E>() {
            Node curr = head;

            /**
             * Determines whether there is another node to iterate through
             * @return whether there is another node to iterate through
             */
            @Override
            public boolean hasNext()
            {
                return curr != null;
            }

            /**
             * Retrieves the next node to iterate through
             * @return the next node to iterate through
             */
            @Override
            public E next()
            {
                if (hasNext())
                {
                    E data = (E) curr.data;
                    curr = curr.next;
                    return data;
                }
                return null;
            }

            /**
             * Not implemented
             */
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException("remove() not implemented.");
            }
        };
    }

    /**
     * Creates an array representation of the data in the nodes in this LinkedList
     * @return The array of the data in the nodes in this LinkedList
     */
    @Override
    public Object[] toArray()
    {
        Object[] ret = new Object[size];
        Node curr = head;
        int i = 0;
        while (curr != null)
        {
            ret[i] = curr.data;
            curr = curr.next;
            i++;
        }
        return ret;
    }

    /**
     * Not implemented
     */
    @Override
    public <T> T[] toArray(T[] a)
    {
        throw new UnsupportedOperationException("toArray(T[] a) not implemented");
    }

    /**
     * Adds e in the form of a node to the end of this LinkedList
     * @param e element whose presence in this collection is to be ensured
     * @return Whether e has been added
     */
    public boolean add(E e)
    {
        if (e != null)
        {
            if (tail == null)
            {
                head = tail = new Node(e);
            }
            else
            {
                tail.next = new Node(e);
                tail = tail.next;
            }
            size++;
            return true;
        }
        return false;
    }

    /**
     * Removes the element o in this LinkedList if present
     * @param o element to be removed from this collection, if present
     * @return Whether o has been removed
     */
    @Override
    public boolean remove(Object o)
    {
        Node prev = null;
        Node curr = this.head;
        // use .equals() to compare
        while (curr != null)
        {
            if (o.equals(curr.data))
            {
                if (curr == head)
                {
                    head = head.next;
                    if (curr == tail)
                    {
                        tail = tail.next;
                    }
                }
                else
                {
                    prev.next = curr.next;
                }
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    /**
     * Determines whether this LinkedList contains every element in c
     * @param c collection to be checked for containment in this collection
     * @return whether this LinkedList contains every element in c
     */
    @Override
    public boolean containsAll(Collection<?> c)
    {
        for (Object elem : c)
        {
            if(!this.contains(elem))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds all elements in c to this LinkedList
     * @param c collection containing elements to be added to this collection
     * @return Whether the elements in c have been added to this LinkedList
     */
    @Override
    public boolean addAll(Collection<? extends E> c)
    {
        if (c != null)
        {
            for (E elem : c)
            {
                this.add(elem);
            }
            return true;
        }
        return false;
    }

    /**
     * Removes all elements in c from this LinkedList
     * @param c collection containing elements to be removed from this collection
     * @return Whether all the elements in c have been removed from this LinkedList
     */
    @Override
    public boolean removeAll(Collection<?> c)
    {
        if (c != null)
        {
            Node curr = head;
            while (curr != null)
            {
                if (c.contains(curr.data))
                {
                    this.remove(curr);
                    curr = curr.next;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Removes all elements from this LinkedList not contained in c
     * @param c collection containing elements to be retained in this collection
     * @return Whether all elements not contained in c have been removed from this LinkedList
     */
    @Override
    public boolean retainAll(Collection<?> c)
    {
        if (c != null)
        {
            Node curr = head;
            while (curr != null)
            {
                if (!c.contains(curr.data))
                {
                    this.remove(curr);
                    curr = curr.next;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Removes all elements from this LinkedList
     */
    @Override
    public void clear()
    {
        while(head != null)
        {
            this.poll();
        }
    }

    /**
     * Adds e to the end of this Queue
     * @param e the element to add
     * @return Whether the e has been added
     */
    @Override
    public boolean offer(E e)
    {
        return this.add(e);
    }

    /**
     * Removes the head of this Queue
     * @return The removed element
     */
    public E remove()
    {
        if (head == null)
        {
            throw new NoSuchElementException();
        }
        E removed = (E) head.data;
        head = head.next;
        tail = head == null ? tail.next : tail;
        return removed;
    }

    /**
     * Removes the head of this Queue
     * @return The removed element
     */
    @Override
    public E poll()
    {
        if (head == null)
        {
            return null;
        }
        E removed = (E) head.data;
        head = head.next;
        tail = head == null ? tail.next : tail;
        return removed;
    }

    /**
     * Retrieves the head of this Queue
     * @return The head of this Queue
     */
    @Override
    public E element()
    {
        if (head == null)
        {
            throw new NoSuchElementException();
        }
        return (E) head.data;
    }

    /**
     * Retrieves the head of this Queue
     * @return The head of this Queue
     */
    @Override
    public E peek()
    {
        if (head == null)
        {
            return null;
        }
        return (E) head.data;
    }
}


