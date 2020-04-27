import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class SplayTree<K extends Comparable<K>, V> implements Map<K, V> {

    class Node<K extends Comparable<K>, V> extends Association<K, V> {

        public Node(K key, V value) {
            super(key, value);
            // TODO Auto-generated constructor stub
        }

        protected Node<K, V> left, right;
        protected boolean hasParent = false;

        public ArrayList<Node<K, V>> insert(K key, V value) {
            if (getKey().compareTo(key) > 0) {
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
                if (right == null) {
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
                } else {
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
                }
            }
        }

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

        public V getValueByKey(K key) {
            if (getKey().compareTo(key) == 0) {
                return getValue();
            }
            if (getKey().compareTo(key) > 0) {
                if (left != null) {
                    return left.getValueByKey(key);
                }
            } else if (getKey().compareTo(key) < 0) {
                if (right != null) {
                    return right.getValueByKey(key);
                }
            }
            return null;
        }

        public void printInOrder() {
            if (left != null) {
                left.printInOrder();
            }
            if (right != null) {
                right.printInOrder();
            }
        }

    }

    private Node<K, V> root;

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(java.util.Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

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

	@Override
	public V put(K key, V value) {
		if (root == null) {
            root = new Node<>(key, value);
        } else {
            root.insert(key, value);
        }
        return value;
	}

	@Override
	public boolean containsKey(K key) {
		return root.containsKey(key);
	}

	@Override
	public V get(K key) {
        return root.getValueByKey(key);
	}
    
    public void printInOrder() {
        root.printInOrder();
    }

}