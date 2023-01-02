package modules;

/**
 * Simulates a Search
 * @author Richard Lin
 * @author Allison Zheng
 * @version 2022.13.10
 */
public class Search implements SearchModule
{
    /**
     * Returns the starting location of the first occurrence of {@code query} in {@code text} or -1
     * if the query does not appear in the target text.
     */
    public int find(String query, String text)
    {
        return text.indexOf((query));
    }
}
