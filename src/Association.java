/**
 * @author Julio Herrera 19402
 * 
 * Implementa comparable
 * Sus genericos K, V denotan la llave y el valor
 * La llave se compromete a ser comparable e implementar compareTo
 * La clase Association tambien se ccompromete a ser comparable
 * Esto para poder ser implementada y ser comparada en si misma
 */
public class Association<K extends Comparable<K>, V> implements Comparable<Association<K, V>>,
                                                                java.lang.Cloneable {

    protected K key;
    protected V value;

    /**
     * Constructor de asociacion
     * @param key llave
     * @param value valor
     */
    public Association(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @return devuelve el valor de la llave
     */
    public K getKey() {
        return key;
    }

    /**
     * @return devuelve el valor del valor (valga la redundancia)
     */
    public V getValue() {
        return value;
    }

    /**
     * @param v del tipo generico V, setea el valor
     */
    public V setValue(V v) {
        return value = v; //TODO: see if return the old or new value
    }

    /**
     * @param k del tipo generico K, setea una nueva llave
     */
    public K setKey(K k) {
        return key = k;
    }

    /**
     * Compara las llaves de ambas asociaciones
     * @param o del mismo tipo de la clase
     */
    public int compareTo(Association<K, V> o) {
        return key.compareTo(o.getKey());
    }

}