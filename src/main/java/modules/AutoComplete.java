package modules;

import util.Trie;

/**
 * Simulates an AutoComplete
 * @author Richard Lin
 * @author Allison Zheng
 * @version 2022.10.13
 */
public class AutoComplete implements AutoCompleteModule
{
    private Trie dict;

    /**
     * Creates an AutoComplete
     */
    public AutoComplete()
    {
        dict = new Trie();
    }

    /**
     * Adds {@code word} to the set of words that can be returned by {@link
     * #getWordForPrefix(String)}.
     */
    @Override
    public void addWord(String word)
    {
        dict.insert(word);
    }

    /**
     * Returns a word of minimal length that has {@code prefix} as a (not necessarily proper)
     * prefix.
     */
    public String getWordForPrefix(String prefix)
    {
        return dict.closestWordToPrefix(prefix);
    }

}
