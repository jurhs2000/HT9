/**
 * Binary Search Tree - SplayTree
 * Define los metodos Zig, Zag, ZigZag, ZagZig, ZigZig y ZagZag.
 * Implementa los metodos de la clase Map para ser parte de ellos y ser usado en el Factory.
 * Contiene un nodo Root que deriva los demas nodos left, right.
 * 
 * Referencias de:
 * Bailey Duane A. (2007). Data Structures in Java for the Principled Programmer. Williams College
 * 
 * Josh Israel. SplayBST.java from: https://algs4.cs.princeton.edu/33balanced/SplayBST.java.html
 * 
 * Sanfoundry. Java Program to Implement Splay Tree.
 * from: https://www.sanfoundry.com/java-program-implement-splay-tree/
 * 
 * Danny Sleator. Implements a top-down splay tree.
 * from: https://www.link.cs.cmu.edu/splay/download/SplayTree.java
 * 
 * @author Julio Herrera
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class SplayTree<K extends Comparable<K>, V> implements Map<K, V> {

    /**
     * El nodo extiende de la Associacion no por necesida, sino por conveniencia
     * Para obtener todos los metodos de get y set de llave y valor.
     * @param <K> Llave
     * @param <V> Valor
     */
    class Node<K extends Comparable<K>, V> extends Association<K, V> {

        public Node(K key, V value) {
            super(key, value);
            // TODO Auto-generated constructor stub
        }

        protected Node<K, V> left, right;
        protected boolean hasParent = false;

        /**
         * Decide a que lado del nodo va a insertar, derecha o izquierda
         * asi como los metodos zig zag que se pueden dar.
         * 
         * @param key
         * @param value
         * @return
         */
        public ArrayList<Node<K, V>> insert(K key, V value) {
            if (getKey().compareTo(key) > 0 && left != null) {
                if (left == null) {
                    left = new Node<K, V>(key, value);
                    left.hasParent = true;
                    if (comprobeLeftOperation(null, null)) {
                        ArrayList<Node<K, V>> nodes = new ArrayList<>();
                        nodes.add(this);
                        nodes.add(left);
                        return nodes;
                    } else {
                        return new ArrayList<Node<K, V>>();
                    }
                } else {
                    ArrayList<Node<K, V>> childToComprobe = left.insert(key, value);
                    if (childToComprobe.size() != 0) {
                        if (left == childToComprobe.get(0)) {
                            comprobeLeftOperation(childToComprobe.get(0), childToComprobe.get(1));
                        } else {
                            comprobeRightOperation(childToComprobe.get(0), childToComprobe.get(1));
                        }
                        return new ArrayList<Node<K, V>>();
                    } else {
                        if (comprobeLeftOperation(null, null)) {
                            return new ArrayList<Node<K, V>>();
                        } else {
                            return new ArrayList<Node<K, V>>();
                        }
                    }
                }
            } else {
                if (right == null && right != null) {
                    right = new Node<K, V>(key, value);
                    right.hasParent = true;
                    if (comprobeRightOperation(null, null)) {
                        ArrayList<Node<K, V>> nodes = new ArrayList<>();
                        nodes.add(this);
                        nodes.add(right);
                        return nodes;
                    } else {
                        return new ArrayList<Node<K, V>>();
                    }
                } else if (left != null) {
                    ArrayList<Node<K, V>> childToComprobe = right.insert(key, value);
                    if (childToComprobe.size() != 0) {
                        if (left == childToComprobe.get(0)) {
                            comprobeLeftOperation(childToComprobe.get(0), childToComprobe.get(1));
                        } else {
                            comprobeRightOperation(childToComprobe.get(0), childToComprobe.get(1));
                        }
                        return new ArrayList<Node<K, V>>();
                    } else {
                        if (comprobeRightOperation(null, null)) {
                            return new ArrayList<Node<K, V>>();
                        } else {
                            return new ArrayList<Node<K, V>>();
                        }
                    }
                } else {
                    return new ArrayList<Node<K, V>>();
                }
            }
        }

        /**
         * Comprueba cual hijo es el que se comprueba para saber que zig zag hacer.
         * Determina si un hijo tiene padre para regresar y comprobar de nuevo mediante el.
         * @param childToComprobe hijo de un nodo para comprobar
         * @param childChildToComprobe nieto de un nodo para comprobar
         */
        private boolean comprobeLeftOperation(Node<K, V> childToComprobe, Node<K, V> childChildToComprobe) {
            if (childToComprobe != null) {
                if (childToComprobe.left != null) {
                    if (childToComprobe.left.equals(childChildToComprobe)) {
                        zigzig();
                        return false;
                    } else {
                        zigzag();
                        return false;
                    }
                } else {
                    zigzag();
                    return false;
                }
            }
            if (hasParent) {
                return true;
            } else {
                zig();
                return false;
            }
        }

        /**
         * Comprueba cual hijo es el que se comprueba para saber que zig zag hacer.
         * Determina si un hijo tiene padre para regresar y comprobar de nuevo mediante el.
         * @param childToComprobe hijo de un nodo para comprobar
         * @param childChildToComprobe nieto de un nodo para comprobar
         */
        private boolean comprobeRightOperation(Node<K, V> childToComprobe, Node<K, V> childChildToComprobe) {
            if (childToComprobe != null) {
                if (childToComprobe.left != null) {
                    if (childToComprobe.left.equals(childChildToComprobe)) {
                        zagzig();
                        return false;
                    } else {
                        zagzag();
                        return false;
                    }
                } else {
                    zagzag();
                    return false;
                }
            }
            if (hasParent) {
                return true;
            } else {
                zag();
                return false;
            }
        }

        /**
         * Metodo de la rotacion Zig
         */
        public void zig() {
            if (right == null) {
                right = new Node<>(getKey(), getValue());
                right.left = left.right;
                right.hasParent = true;
            } else {
                Node<K, V> toSet = new Node<>(null, null);
                try {
                    toSet = (Node<K, V>) (right.clone());
                } catch (CloneNotSupportedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                right.right = toSet; // Este orden si importa!!!
                right.left = left.right;
                right.setKey(getKey());
                right.setValue(getValue());
            }
            setKey(left.getKey()); // Este orden tambien importa
            setValue(left.getValue());
            left = left.left;
        }

        /**
         * Metodo de la rotacion Zag
         */
        public void zag() {
            if (left == null) {
                left = new Node<>(getKey(), getValue());
                left.right = right.left;
                left.hasParent = true;
            } else {
                Node<K, V> toSet = new Node<>(null, null);
                try {
                    toSet = (Node<K, V>) (left.clone());
                } catch (CloneNotSupportedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                left.left = toSet; // Este orden si importa!!!
                left.right = right.left;
                left.setKey(getKey());
                left.setValue(getValue());
            }
            setKey(right.getKey()); // Este orden tambien importa
            setValue(right.getValue());
            right = right.right;
        }

        /**
         * Metodo de la rotacion ZigZag
         */
        public void zigzag() {
            if (right == null) {
                right = new Node<>(getKey(), getValue());
                right.left = left.right.right;
                right.hasParent = true;
            } else {
                Node<K, V> toSet = new Node<>(null, null);
                try {
                    toSet = (Node<K, V>) (right.clone());
                } catch (CloneNotSupportedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                right.right = toSet;
                right.left = left.right.right;
                right.setKey(getKey());
                right.setValue(getValue());
            }
            setKey(left.right.getKey());
            setValue(left.right.getValue());
            left.right = left.right.left;
        }

        /**
         * Metodo de la rotacion ZagZig
         */
        public void zagzig() {
            if (left == null) {
                left = new Node<>(getKey(), getValue());
                left.right = right.left.left;
                left.hasParent = true;
            } else {
                Node<K, V> toSet = new Node<>(null, null);
                try {
                    toSet = (Node<K, V>) (left.clone());
                } catch (CloneNotSupportedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                left.left = toSet;
                left.right = right.left.left;
                left.setKey(getKey());
                left.setValue(getValue());
            }
            setKey(right.left.getKey());
            setValue(right.left.getValue());
            right.left = right.left.right;
        }

        /**
         * Metodo de la rotacion ZigZig
         */
        public void zigzig() {
            if (right == null) {
                right = new Node<>(left.getKey(), left.getValue());
                right.left = left.left.right;
                right.right = new Node<>(getKey(), getValue());
                right.right.left = left.right;
                right.hasParent = true;
                right.right.hasParent = true;
            } else if (right.right == null) {
                Node<K, V> redToSetRight = new Node<>(null, null);  
				try {
					redToSetRight = (Node<K,V>)(right.clone());
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                right.right = new Node<>(getKey(), getValue());
                right.right.right = redToSetRight;
                right.right.left = left.right;
                right.setKey(left.getKey());
                right.setValue(left.getValue());
                right.left = left.left.right;
                right.right.hasParent = true;
            } else {
                Node<K, V> toSet = new Node<>(null, null);
                try {
                    toSet = (Node<K, V>) (left.clone());
                } catch (CloneNotSupportedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                right.right.right = toSet;
                right.right.left = left.right;
                right.right.setKey(getKey());
                right.right.setValue(getValue());
                right.setKey(left.getKey());
                right.setValue(left.getValue());
                right.left = left.left.right;
            }
            setKey(left.left.getKey());
            setValue(left.left.getValue());
            left = left.left.left;
        }

        /**
         * Metodo de la rotacion ZagZag
         */
        public void zagzag() {
            if (left == null) {
                left = new Node<>(right.getKey(), right.getValue());
                left.right = right.right.left;
                left.left = new Node<>(getKey(), getValue());
                left.left.right = right.left;
                left.hasParent = true;
                left.left.hasParent = true;
            } else if (left.left == null) {
                Node<K, V> redToSetleft = new Node<>(null, null);
                try {
                    redToSetleft = (Node<K, V>) (left.clone());
                } catch (CloneNotSupportedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                left.left = new Node<>(getKey(), getValue());
                left.left.left = redToSetleft;
                left.left.right = right.left;
                left.setKey(right.getKey());
                left.setValue(right.getValue());
                left.right = right.right.left;
                left.left.hasParent = true;
            } else {
                Node<K, V> toSet = new Node<>(null, null);
				try { 
					toSet = (Node<K, V>) (left.clone());
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                left.left.left = toSet;
                left.left.right = right.left;
                left.left.setKey(getKey());
                left.left.setValue(getValue());
                left.setKey(right.getKey());
                left.setValue(right.getValue());
                left.right = right.right.left;
            }
            setKey(right.right.getKey());
            setValue(right.right.getValue());
            right = right.right.right;
        }

        /**
         * Comprueba dentro si se contiene la llave
         * @param key llave
         * @return true si existe
         */
        public boolean containsKey(K key) {
            if (getKey().compareTo(key) == 0) {
                return true;
            }
            if (getKey().compareTo(key) > 0) {
                if (left != null) {
                    return left.containsKey(key);
                }
            } else if (getKey().compareTo(key) < 0) {
                if (right != null) {
                    return right.containsKey(key);
                }
            }
            return false;
        }

        /**
         * Devuelve el valor de la llave seleccionada
         * @param key Llave
         * @return El objeto de tivo V
         */
        public V get(K key) {
            if (getKey().compareTo(key) == 0) {
                return getValue();
            }
            if (getKey().compareTo(key) > 0) {
                if (left != null) {
                    return left.get(key);
                }
            } else if (getKey().compareTo(key) < 0) {
                if (right != null) {
                    return right.get(key);
                }
            }
            return null;
        }

    }

    /**
     * Nodo raiz
     */
    private Node<K, V> root;

    /**
     * Para obtener el tamaño del mapa
     */
	@Override
	public int size() {
        return map.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

    /**
     * Para verificar si el mapa contiene la llave
     */
	@Override
	public boolean containsKey(Object key) {
        return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

    /**
     * Para obtener el valor de una llave
     */
	@Override
	public V get(Object key) {
        return map.get(key);
	}

	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
    }

	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
    }

    java.util.Map<K, V> map = new java.util.TreeMap<K, V>();

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
    }

    /**
     * Inserta en el nodo
     */
	@Override
	public V put(K key, V value) {
		if (root == null) {
            root = new Node<>(key, value);
        } else {
            root.insert(key, value);
        }
        return map.put(key, value);
	}

    /**
     * Para verificar si el mapa contiene un llave
     */
	@Override
	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

    /**
     * Para obtener el valor de una llave
     */
	@Override
	public V get(K key) {
        return map.get(key);
    }
    

	@Override
	public void putAll(java.util.Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

}