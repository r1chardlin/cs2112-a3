package util;

import java.util.Collection;

/**
 * Represents a collection of elements of type E for which the only operation is a probabilistic membership
 * test.
 * @author Richard Lin
 * @author Allison Zheng
 * @version 2022.10.13
 */
public class BloomFilter<E>
{
    byte[] bits;
    int[] hashAddends;
    int numHashFunctions;

    /**
     * Create a new Bloom filter with {@code elems} inside. The bit array is of length 8 * numBytes.
     * The Bloom filter uses the specified number of hash functions.
     *
     * @param elems The collection of elements to be added to this filter
     * @param numBytes The length of the byte array representing bit array
     * @param numHashFunctions The number of hash functions to be used in this filter
     */
    public BloomFilter(Collection<E> elems, int numBytes, int numHashFunctions)
    {
        bits = new byte[numBytes];
        this.numHashFunctions = numHashFunctions > 0 ? numHashFunctions : 1;
        this.hashAddends = new int[numHashFunctions];
        hashAddends[0] = 0;
        for (int i = 1; i < hashAddends.length; i++)
        {
            hashAddends[i] = (int) (Math.random() * (bits.length * 8 - 1) + 1);
        }
        if (elems != null)
        {
            for (E elem : elems)
            {
                this.insert(elem);
            }
        }

    }

    /**
     * Calculates the modular hash for k given m
     * @param k The number to be hashed
     * @param m The number to mod with
     * @return
     */
    public int hash(int k, int m)
    {
        return k % m >= 0 ? k % m : (k % m) * -1;
    }

    /**
     * Add {@code elem} to the Bloom filter.
     */
    public void insert(E elem)
    {
        int hashedElem = elem.hashCode();
        for (int i = 0; i < numHashFunctions; i++)
        {
            hashedElem = hash(hashedElem + hashAddends[i], bits.length * 8);
            int arrIndex = hashedElem / 8;
            int bitIndex = hashedElem % 8;
            int bitShift = 7 - bitIndex;
            byte newByte = bits[arrIndex] |= (1 << bitShift);
            bits[arrIndex] = newByte;
        }
    }

    /**
     * Check whether {@code elem} might be in the collection.
     */
    public boolean mightContain(E elem)
    {
        int hashedElem = elem.hashCode();
        for (int i = 0; i < numHashFunctions; i++)
        {
            hashedElem = hash(hashedElem + hashAddends[i], bits.length * 8);
            int arrIndex = hashedElem / 8;
            int bitIndex = hashedElem % 8;
            int bitShift = 7 - bitIndex;
            int value = (bits[arrIndex] >>> bitShift) & 1;
            if (value == 0)
            {
                return false;
            }
        }
        return true;
    }
}
