package modules;

public interface SearchModule {

    /**
     * Returns the starting location of the first occurrence of {@code query} in {@code text} or -1
     * if the query does not appear in the target text.
     */
    public int find(String query, String text);
}
