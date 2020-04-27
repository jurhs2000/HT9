/**
 * Interfaz Map que define los metodos para los mapas y arbolesbinarios
 * 
 * @author Julio Herrera;
 * 
 * @param <K> Llave
 * @param <V> Valor
 */
public interface Map<K extends Comparable<K>, V> extends java.util.Map<K, V> {
    
    /**
     * Para insertar elementos
     */
    V put(K key, V value);

    /**
     * Para saber si existen los elementos
     * @param key Llave
     * @return true si existe
     */
    boolean containsKey(K key);

    /**
     * Para obtener un valor mediante la llave
     * @param key Llave
     * @return  el valor
     */
    V get(K key);
}