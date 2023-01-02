package util;

import javax.swing.text.DefaultStyledDocument;
import java.util.Set;

/**
 * Represents a mutable collection of strings stored in the form of a k-ary search tree
 * @author Richard Lin
 * @author Allison Zheng
 * @version 2022.10.13
 * */
public class Trie
{
    TrieNode root;

    /**
     * Create an empty trie.
     */
    public Trie()
    {
        root = new TrieNode("", false);
    }

    /**
     * Add {@code elem} to the collection.
     */
    public void insert(String elem)
    {
        TrieNode curr = root;
        for (int i = 0; i < elem.length(); i++)
        {
            HashTable<String, TrieNode> children = curr.getChildren();
            String character = String.valueOf(elem.charAt(i));
            if (!(children.containsKey(character)))
            {
                TrieNode child = new TrieNode(character, false);
                children.put(character, child);
            }
            curr = children.get(character);
        }
        curr.changeEnd(true);
    }

    /**
     * Remove {@code elem} from the collection, if it is there.
     */
    public void delete(String elem)
    {
        if (this.contains(elem))
        {
            TrieNode curr = root;
            TrieNode lastUsed = root;
            String nextChar = "";
            for (int i = 0; i < elem.length(); i++)
            {
                String character = String.valueOf(elem.charAt(i));
                HashTable<String, TrieNode> children = curr.getChildren();
                if (children.size() > 1)
                {
                    lastUsed = curr;
                    nextChar = character;
                }
                curr = children.get(character);
            }
            lastUsed.getChildren().remove(nextChar);
        }
    }

    /**
     * Return true if this trie contains {@code elem}, false otherwise.
     */
    public boolean contains(String elem)
    {
        if (elem == null)
        {
            return false;
        }
        TrieNode curr = root;
        for (int i = 0; i < elem.length(); i++)
        {
            HashTable<String, TrieNode> children = curr.getChildren();
            String character = String.valueOf(elem.charAt(i));
            if (!(children.containsKey(character)))
            {
                return false;
            }
            curr = children.get(character);
        }
        return curr.isEnd();
    }

    /**
     * Return a word contained in the trie of minimal length with {@code prefix}. If no such word
     * exists, return null.
     */
    public String closestWordToPrefix(String prefix)
    {
        if (prefix == null)
        {
            return null;
        }
        TrieNode curr = root;
        for (int i = 0; i < prefix.length(); i++)
        {
            HashTable<String, TrieNode> children = curr.getChildren();
            String character = String.valueOf(prefix.charAt(i));
            curr = children.get(character);
            if (curr == null)
            {
                return null;
            }
        }
        if (curr.isEnd())
        {
            return prefix;
        }
        LinkedList<TrieNode> queue = new LinkedList<TrieNode>();
        HashTable<TrieNode, TrieNode> parentMap = new HashTable<TrieNode, TrieNode>(10);
        HashTable<String, TrieNode> children = curr.getChildren();
        for (String key : children.keySet())
        {
            TrieNode child  = children.get(key);
            queue.add(child);
            parentMap.put(child, curr);
        }
        while(!queue.isEmpty())
        {
            TrieNode parent = queue.poll();
            if (parent.isEnd())
            {
                String ret = prefix;
                String temp = "";
                TrieNode bottom = parent;
                while (bottom != curr)
                {
                    temp = bottom.getCharacter() + temp;
                    bottom = parentMap.get(bottom);
                }
                ret += temp;
                return ret;
            }
            HashTable<String, TrieNode> removedChildren = parent.getChildren();
            for (String key: removedChildren.keySet())
            {
                TrieNode child = removedChildren.get(key);
                queue.add(child);
                parentMap.put(child, parent);
            }
        }
        return null;
    }
}
