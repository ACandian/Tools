package tools.util;

/**
 * Represent a key/value object, or any couple of linked values.
 * 
 * @author A. CANDIAN
 *
 * @param <K>
 *            The first value (key) type.
 * @param <V>
 *            The second value (value) type.
 */
public final class Pair<K, V> {
    /**
     * The first value type (the key).
     */
    private final K key;

    /**
     * The second value (the value).
     */
    private final V value;

    /**
     * Construct a key/value pair.
     * 
     * @param key
     *            The key.
     * @param value
     *            The value.
     */
    public Pair(K key, V value) {
        super();
        this.key = key;
        this.value = value;
    }

    /**
     * 
     * @return The key of the pair.
     */
    public K getKey() {
        return key;
    }

    /**
     * 
     * @return The value of the pair.
     */
    public V getValue() {
        return value;
    }
}
