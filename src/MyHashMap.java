public class MyHashMap<K, V> {
    private Node<K, V>[] table = new Node[12]; //initialize
    private int size = 0;
    int capacity;
    double loadFactor;


    public  MyHashMap(int cap)
    {
        capacity = cap;
        loadFactor = 0.75;
        table = new Node[capacity];
    }
    public  MyHashMap(int cap, double load)
    {
        capacity = cap;
        loadFactor = load;
        table = new Node[capacity];
    }
    public  MyHashMap()
    {
        capacity = 12;
        loadFactor = 0.75;
        table = new Node[capacity];
    }

    int calculate(K key){
        if (key == null) return 0;
        int hashing = key.hashCode();
        return (hashing) & (capacity-1);
    }

    V put(K key, V value){ //Associates the specified value with the specified key.
        int  indexLocationToPlace = calculate(key);

        Node<K, V> current = table[indexLocationToPlace];
        while (current != null) {
            if (current.getKey().equals(key)) {
                V oldValue = current.getValue();
                current.setValue(value); // Update existing key
                return oldValue;
            }
            current = current.getNext();
        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode.setNext(table[indexLocationToPlace]);
        table[indexLocationToPlace] = newNode;
        size++;

        if (size > capacity * loadFactor) {
            resize();
        }

        return null;
    }

    V get(K key){ //Returns the value to which the specified key is mapped, or `null` if no such key exists.
        int hashing = calculate(key);

        Node<K, V> helper = table[hashing];
        while (helper != null) {
            if (helper.getKey().equals(key)) {
                return helper.getValue();
            }
            helper = helper.getNext();
        }

        return null; // Not found
    }

    V remove(K key){ //Removes the mapping for a key if it is present.
        int hashing = calculate(key);

        Node<K, V> prev = null;
        Node<K, V> current = table[hashing];

        while (current != null) {
            if (current.getKey().equals(key)) {
                if (prev == null) {
                    table[hashing] = current.getNext();
                } else {
                    prev.setNext(current.getNext());
                }
                size--;
                return current.getValue();
            }
            prev = current;
            current = current.getNext();
        }
            return null;
    }

    boolean containsKey(K key){ //Returns `true` if this map contains a mapping for the specified key.
        int hashing = calculate(key);

        Node<K, V> helper = table[hashing];
        while (helper != null) {
            if (helper.getKey().equals(key)) {
                return true;
            }
            helper = helper.getNext();
        }
        return false;
    }

    boolean containsValue(V value) { //Returns `true` if this map maps one or more keys to the specified value.
        for (int i = 0; i < table.length; i++) {
            Node<K, V> helper = table[i];
            while (helper != null) {
                if (helper.getValue().equals(value)) {
                    return true;
                }
                helper = helper.getNext();
            }
        }
        return false;
    }

    int size(){ //Returns the number of key-value mappings in this map.
        return size;
    }

    boolean isEmpty(){ //Returns `true` if this map contains no key-value mappings.
        for(int i = 0; i < table.length; i++){
            if(table[i] != null){
                return false;
            }
        }
        return true;
    }

    void clear(){ //Removes all of the mappings from this map.
        table = new Node[capacity]; //java takes care of memory
        size = 0;
    }

    V getOrDefault(K key, V defaultValue){  //Returns the value for the key, or `defaultValue` if the key is not found.
//???
        int hashing = calculate(key);
        if(table[hashing] != null)
        {
            if(table[hashing].getNext() == null && table[hashing].getKey().equals(key)){
                return table[hashing].getValue();
            }
            else{
                Node<K,V> helper = table[hashing];
                while(!helper.getKey().equals(key)){
                    helper = helper.getNext();
                    if(!helper.getKey().equals(key) && (helper.getNext()==null)){
                        return defaultValue;//is it too fast??
                    }
                }
                if(helper.getKey().equals(key)){
                    return helper.getValue();
                }
            }
        }
        return defaultValue;
    }

    V putIfAbsent(K key, V value) { //If the key is not already associated with a value, associates it and returns `null`, otherwise returns the current value.
//???
        int hashing = calculate(key);
        Node<K, V> helper = table[hashing];
        while (helper != null) {
            if (helper.getKey().equals(key)) {
                return helper.getValue();
            }
            helper = helper.getNext();
        }
        Node<K, V> newNode = new Node<>(key, value);
        newNode.setNext(table[hashing]);
        table[hashing] = newNode;
        size++;

        if (size > capacity * loadFactor) {
            resize();
        }

        return null;
    }



    boolean remove(K key, V value){ //Removes the entry for the key only if it is currently mapped to the specified value.
//??
        int hashing = calculate(key);
        Node<K, V> prev = null;
        Node<K, V> current = table[hashing];

        while (current != null) {
            if (current.getKey().equals(key)) {
                if (current.getValue().equals(value)) {
                    if (prev == null) {
                        table[hashing] = current.getNext();
                    }
                    else{
                        prev.setNext(current.getNext());
                    }
                    size--;
                    return true;
                }
                return false;
            }
            prev = current;
            current = current.getNext();
        }
        return false;
    }

    V replace(K key, V value){ //Replaces the entry for the key only if it is currently mapped to some value.
        int hashing = calculate(key);
        Node<K, V> helper = table[hashing];
        while (helper != null) {
            if (helper.getKey().equals(key)) {
                V oldValue = helper.getValue();
                helper.setValue(value);
                return oldValue;
            }
            helper = helper.getNext();
        }
        return null;
    }

    boolean replace(K key, V oldValue, V newValue){ //Replaces the entry for the key only if it is currently mapped to the specified old value.
        int hashing = calculate(key);
        Node<K, V> helper = table[hashing];
        while (helper != null) {
            if (helper.getKey().equals(key)) {
                if (helper.getValue().equals(oldValue)) {
                    helper.setValue(newValue);
                    return true;
                }
                return false;
            }
            helper = helper.getNext();
        }
        return false;
    }

void resize(){
    int newCapacity = capacity * 2; //just double
    Node<K, V>[] oldTable = table; // old table

    table = new Node[newCapacity]; //new table created
    capacity = newCapacity;
    size = 0;
    for (int i = 0; i < oldTable.length; i++) {
        Node<K, V> helper = oldTable[i];
        //no need to recalc bc put does it
        while (helper != null) {
            put(helper.getKey(), helper.getValue()); // Reinsert
            helper = helper.getNext();
        }
    }
}
}
