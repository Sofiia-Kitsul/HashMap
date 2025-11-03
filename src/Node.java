public class Node<K, V> {
    K key;
    V value;
    int keyInteger = 0;
    Node next;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public Node(int keyInteger) {
        this.keyInteger = keyInteger;
    }

    public Node(Node next) {
        this.next = next;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public int getKeyInteger() {
        return keyInteger;
    }

    public void setKeyInteger(int keyInteger) {
        this.keyInteger = keyInteger;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
