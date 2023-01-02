package util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Represents a hash table
 * @author Richard Lin
 * @author Allison Zheng
 * @version 2022.10.13
 */
public class HashTable<K, V> implements Map<K, V>, Iterable<K>
{
    LinkedList<HashNode<K, V>>[] buckets;
    int size;
    int numBuckets;
    /**
     * Creates a new hash table.
     *
     * @param numElements A guess at the number of elements
     *                    the hash table will eventually contain,
     *                    as a hint for improving performance.
     */
    public HashTable(int numElements)
    {
        this.numBuckets = numElements > 0 ? numElements : 1;
        this.buckets = new LinkedList[numBuckets];
        for (int i = 0; i < numBuckets; i++)
        {
            buckets[i] = new LinkedList<HashNode<K, V>>();
        }
        this.size = 0;
    }

    /**
     * Gets the number of buckets contained in this HashTable
     * @return The number of buckets contained in this HashTable
     */
    public int getNumBuckets()
    {
        return numBuckets;
    }

    /**
     * Hashes k using modular hashing
     * @param k the int to be hashed
     * @return The value of k after hashing using numBuckets
     */
    public int hash(int k)
    {
        return k % numBuckets >= 0 ? k % numBuckets : (k % numBuckets) * -1;
    }

    /**
     * Gets the size of this HashTable
     * @return size The size of this HashTable
     */
    @Override
    public int size()
    {
        return size;
    }

    /**
     * Calculates the load factor this HashTable
     * @return The load factor of this HashTable
     */
    public double loadFactor()
    {
        return numBuckets != 0 ? (double) size / (double) numBuckets : 0.0;
    }

    /**
     * Resizes this HashTable by doubling the number of buckets
     */
    public void resize()
    {
        LinkedList<HashNode<K, V>>[] newBuckets = new LinkedList[numBuckets * 2];
        for (int i = 0; i < newBuckets.length; i++)
        {
            newBuckets[i] = new LinkedList<HashNode<K, V>>();
        }

        for (K key : this.keySet())
        {
            V value = this.get(key);
            int hashedKey = key.hashCode();
            hashedKey = hashedKey % newBuckets.length;
            newBuckets[hashedKey].add(new HashNode<K, V>(key, value));
        }
        buckets = newBuckets;
        numBuckets = newBuckets.length;
    }


    /**
     * Determines whether this HashTable is empty
     * @return whether this HashTable is empty
     */
    @Override
    public boolean isEmpty()
    {
        return this.size() == 0;
    }

    /**
     * Determines whether this HashTable contains the given key
     * @param key key whose presence in this map is to be tested
     * @return Whether this HashTable contains the given key
     */
    @Override
    public boolean containsKey(Object key)
    {
        if (key != null)
        {
            int hashedKey = hash(key.hashCode());
            LinkedList<HashNode<K, V>> bucket = buckets[hashedKey];
            for (HashNode<K, V> node : bucket)
            {
                if (node.getKey().equals(key))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines whether this HashTable contains the given value
     * @param value value whose presence in this map is to be tested
     * @return Whether this HashTable contains the given value
     */
    @Override
    public boolean containsValue(Object value)
    {
        for(LinkedList<HashNode<K, V>> bucket : buckets)
        {
            for (HashNode<K, V> node : bucket)
            {
                if (node.getValue().equals(value))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retrieves the key that the given value is mapped to
     * @param key the key whose associated value is to be returned
     * @return The key that the given value is mapped to
     */
    @Override
    public V get(Object key)
    {
        if (key != null)
        {
            int hashedKey = hash(key.hashCode());
            LinkedList<HashNode<K, V>> bucket = buckets[hashedKey];
            for (HashNode<K, V> node : bucket) {
                if (node.getKey().equals(key)) {
                    return node.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Puts the key-value pair into this HashTable
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return The previous value that the given key was mapped to if there was one. Otherwise returns null
     */
    @Override
    public V put(K key, V value)
    {
        if (key == null || value == null)
        {
            throw new NullPointerException();
        }
        V oldValue = this.remove(key);
        int hashedKey = hash(key.hashCode());
        buckets[hashedKey].add(new HashNode<K, V>(key, value));
        size++;
        if (this.loadFactor() > 1.0)
        {
            this.resize();
        }
        return oldValue;
    }

    /**
     * Removes the key-value pair from the map
     * @param key key whose mapping is to be removed from the map
     * @return The value that the given key was mapped to before removing
     */
    @Override
    public V remove(Object key)
    {
        if (key == null)
        {
            throw new NullPointerException();
        }
        int hashedKey = hash(key.hashCode());
        V ret = this.get(key);
        boolean removed = false;
        for (HashNode<K, V> node : buckets[hashedKey])
        {
            if (key.equals(node.getKey()))
            {
                removed = buckets[hashedKey].remove(new HashNode<K, V>(node.getKey(), node.getValue()));
            }
        }
        size = removed ? size - 1 : size;
        return ret;
    }

    /**
     * Puts all the mappings in m into this HashTable
     * @param m mappings to be stored in this map
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        for (K key : m.keySet())
        {
            this.put(key, m.get(key));
        }
    }

    /**
     * Removes every mapping in this HashTable
     */
    @Override
    public void clear()
    {
        // METHOD 1
        for (K key : this.keySet())
        {
            this.remove(key);
        }

        // METHOD 2
        // LinkedList<HashNode<K,V>>[] temp = new LinkedList[numBuckets];
        // for (int i = 0; i < numBuckets; i++)
        // {
            // buckets[i] = new LinkedList<HashNode<K, V>>();
        // }
        // buckets = temp;
        // size = 0;
    }

    /**
     * Creates an Iterator<K> that iterates through the keys of this HashTable
     * @return The Iterator<K> for this HashTable
     */
    @Override
    public Iterator<K> iterator()
    {
        return new Iterator<K>()
        {
            int index = 0;
            LinkedList<HashNode<K, V>> curr = buckets[index];
            Iterator<HashNode<K, V>> iter = curr.iterator();

            /**
             * Determines whether this Iterator has another key to iterate through
             * @return Whether this Iterator has another key to iterate through
             */
            @Override
            public boolean hasNext()
            {
                boolean hasNonEmptyBuckets = false;
                for(int i = index + 1; i < buckets.length; i++)
                {
                    if (!(buckets[i].isEmpty()))
                    {
                        hasNonEmptyBuckets = true;
                        break;
                    }
                }
                return iter.hasNext() || hasNonEmptyBuckets;
            }

            /**
             * Retrieves the next key to iterate through
             * @return The next key to iterate through
             */
            @Override
            public K next()
            {
                if (hasNext())
                {
                    while (!(iter.hasNext()) && index != numBuckets - 1)
                    {
                        index++;
                        curr = buckets[index];
                        iter = curr.iterator();
                    }
                    HashNode<K, V> next = iter.next();
                    return next != null ? next.getKey() : null;
                }
                return null;
            }

            /**
             * Not implemented, will throw an exception if called
             */
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException("remove() not implemented.");
            }
        };
    }

    /**
     * Puts the keys of this HashTable and puts them into an array
     * @return ret The array of keys of this HashTable
     */
    public Object[] toArray()
    {
        Object[] ret = new Object[size];
        int retIndex = 0;
        for (int i = 0; i < numBuckets; i++)
        {
            for (HashNode<K, V> node : buckets[i])
            {
                ret[retIndex] = node.getKey();
                retIndex++;
            }
        }
        return ret;
    }

    /**
     * Represent the set of keys in this HashTable
     * Any change to KeySet is reflected in HashTable and vice versa
     */
    class KeySet implements Set<K>
    {
        /**
         * Gets the size of the set of keys of this HashTable
         * @return The size of the set of keys of This HashTable
         */
        @Override
        public int size()
        {
            return HashTable.this.size();
        }

        /**
         * Determines whether the set of keys of this HashTable is empty
         * @return whether the set of keys of this HashTable is empty
         */
        @Override
        public boolean isEmpty()
        {
            return HashTable.this.isEmpty();
        }

        /**
         * Determines whether o is a key in this HashTable
         * @param o element whose presence in this set is to be tested
         * @return Whether o is a key in this HashTable
         */
        @Override
        public boolean contains(Object o)
        {
            return HashTable.this.containsKey(o);
        }

        /**
         * Creates an Iterator<K> that iterates through the keys of this HashTable
         * @return The Iterator<K> for the set of keys of this HashTable
         */
        @Override
        public Iterator<K> iterator()
        {
            return HashTable.this.iterator();
        }

        /**
         * Puts the keys of this HashTable and puts them into an array
         * @return ret The array of keys of this HashTable
         */
        @Override
        public Object[] toArray()
        {
            return HashTable.this.toArray();
        }


        /**
         * Not implemented
         */
        @Override
        public <T> T[] toArray(T[] a)
        {
            throw new UnsupportedOperationException("toArray(T[] a) not implemented.");
        }

        /**
         * Not supported
         */
        @Override
        public boolean add(K k)
        {
            throw new UnsupportedOperationException("add(K k) not implemented.");
        }

        /**
         * Removes o from the set of keys of this HashTable
         * @param o key to be removed from this set, if present
         * @return Whether the key has been removed
         */
        @Override
        public boolean remove(Object o)
        {
            return HashTable.this.remove(o) != null;
        }

        /**
         * Not implemented
         */
        @Override
        public boolean containsAll(Collection<?> c)
        {
            throw new UnsupportedOperationException("containsAll(Collection<?> c) not implemented.");
        }

        /**
         * Not implemented
         */
        @Override
        public boolean addAll(Collection<? extends K> c)
        {
            throw new UnsupportedOperationException("addAll(Collection<? extends K> c) not implemented.");
        }

        /**
         * Not implemented
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("retainAll(Collection<?> c) not implemented.");
        }

        /**
         * Not implemented
         */
        @Override
        public boolean removeAll(Collection<?> c)
        {
            throw new UnsupportedOperationException("removeAll(Collection<?> c) not implemented.");
        }

        /**
         * Not implemented
         */
        @Override
        public void clear()
        {
            throw new UnsupportedOperationException("clear() not implemented.");
        }
    }


    /**
     * Retrieves the set of keys of this HashTable
     * @return the set of keys of this HashTable
     */
    @Override
    public Set<K> keySet()
    {
        return new KeySet();
    }

    /*
     * You are not required to implement the values() or entrySet() operations.
     */
    @Override
    public Collection<V> values()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet()
    {
        throw new UnsupportedOperationException();
    }
}
