package util;

public class TrieNode
{
    String character;
    HashTable<String, TrieNode> children;
    boolean end;

    TrieNode(String character, boolean end)
    {
        this.character = character;
        children = new HashTable<String, TrieNode>(26);
        this.end = end;
    }

    public String getCharacter()
    {
        return character;
    }

    public HashTable<String, TrieNode> getChildren()
    {
        return children;
    }

    public boolean isEnd()
    {
        return end;
    }

    public void changeEnd(boolean newBool)
    {
        end = newBool;
    }

    public void addChild(TrieNode child)
    {
        children.put(child.getCharacter(), child);
    }

}
