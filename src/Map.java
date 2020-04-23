public interface Map<K extends Comparable<K>, V> extends java.util.Map<K, V> {
    
    V put(K key, V value);

    boolean containsKey(Object key);

    V get(Object key);

    interface Entry<K, V> {

    }
}