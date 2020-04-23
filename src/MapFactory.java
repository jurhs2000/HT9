/**
 * UVG - ADT - HT8
 * 
 * Implementacion de patron Factory para decidir si utilizar el 
 * Mapeo de HashMap o el SplayTree de TreeMap.
 * 
 * @author Julio Herrera
 * @author Daliena Batz
 */

public class MapFactory<K extends Comparable<K>, V> {
    /**
     * Devuelve el map elegido segun el parametro.
     * 1. SplayTree
     * 2. HashMap
     * 
     * @param mapType el numero elegido
     * @return Un Map ya que es la interfaz comun entre estas clases
     */
    public java.util.Map<K, V> getMap(int mapType) {
        switch (mapType) {
            case 1:
                return new SplayTree<K, V>();
            case 2:
                return new HashMap<K, V>();
            case 3:
                return new java.util.TreeMap<K, V>();
            case 4:
                return new java.util.HashMap<K, V>();
            default:
                return null;
        }
    }
}