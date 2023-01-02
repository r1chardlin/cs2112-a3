package util;

public class HashNode<K, V>
{
    private K key;
    private V value;

    HashNode(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    public K getKey()
    {
        return key;
    }

    public V getValue()
    {
        return value;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof HashNode)
        {
            return this.getKey().equals(((HashNode) o).getKey());
        }
        return false;
    }
}
