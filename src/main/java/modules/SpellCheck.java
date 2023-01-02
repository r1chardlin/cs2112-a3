package modules;

import util.BloomFilter;

import java.util.Collection;

/**
 * Simulates a SpellCheck
 * @author Richard Lin
 * @author Allison Zheng
 * @version 2022.10.13
 */
public class SpellCheck implements SpellCheckModule
{
    private BloomFilter<String> dict;

    /**
     * Creates a SpellCheck
     */
    SpellCheck()
    {
        dict = new BloomFilter<String>(null, 8888, 8);
    }

    /**
     * Adds {@code word} to the set of words that can be returned by {@link #isValidWord(String)}.
     */
    public void addWord(String word)
    {
        dict.insert(word);
    }

    /**
     * Returns true if {@code word} has been added to the list of known words through a call to
     * {@link #addWord(String)}.
     */
    public boolean isValidWord(String word)
    {
        return dict.mightContain(word);
    }
}
