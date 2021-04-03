package structs;

public class Pair<K, V> {

    private K x;
    private V y;

    public Pair(K x, V y) {
        this.x = x;
        this.y = y;
    }

    public K getX() {
        return x;
    }

    public V getY() {
        return y;
    }

    @Override
    public String toString() {
        return x.toString() + ", " + y.toString();
    }
}
