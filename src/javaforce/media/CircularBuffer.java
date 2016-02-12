package javaforce.media;


public class CircularBuffer {
    private int maxElements;
    private short elements[];
    private int end = 0;
    private int start = 0;
    private boolean full = false;

    public CircularBuffer(int size) {
        maxElements = size;
        elements = new short[maxElements];
    }

    /**
     * Adds new elements. Discards the oldest elements when buffer is full
     */
    public void add(short in[], int pos, int len) {
        int left = maxElements - size() - len;
        if (left < 0) {
            discardNoCheck(-left);
        }
        addNoCheck(in, pos, len);
    }

    private void addNoCheck(short in[], int pos, int len) {
        int newEnd = end + len;
        boolean wrap = newEnd > maxElements;
        if (newEnd >= maxElements) {
            newEnd -= maxElements;
        }
        if (newEnd == start) {
            full = true;
        }
        if (wrap) {
            //copy 2 pieces
            int p1 = maxElements - end;
            int p2 = len - p1;
            System.arraycopy(in, pos, elements, end, p1);
            System.arraycopy(in, pos + p1, elements, 0, p2);
        } else {
            //copy 1 piece
            System.arraycopy(in, pos, elements, end, len);
        }
        end = newEnd;
    }

    public boolean get(short out[], int pos, int len) {
        if (size() < len) {
            return false;
        }
        boolean wrap = start + len > maxElements;
        if (wrap) {
            int p1 = maxElements - start;
            int p2 = len - p1;
            System.arraycopy(elements, start, out, pos, p1); //copy 2 pieces
            System.arraycopy(elements, 0, out, pos + p1, p2);
        } else {
            System.arraycopy(elements, start, out, pos, len); //copy 1 piece
        }
        discardNoCheck(len);
        return true;
    }

    private void discardNoCheck(int len) {
        int newStart = start + len;
        if (newStart >= maxElements) {
            newStart -= maxElements;
        }
        start = newStart;
        full = false;
    }

    public boolean discard(int len) {
        if (size() < len) {
            return false;
        }
        discardNoCheck(len);
        return true;
    }

    public int size() {
        if (end > start) return end - start;
        if (start == end) return full ? maxElements : 0;
        return maxElements - start + end;
    }

    public void clear() {
        full = false;
        start = end;
    }
}