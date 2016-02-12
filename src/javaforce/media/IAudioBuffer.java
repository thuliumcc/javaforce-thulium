package javaforce.media;


public interface IAudioBuffer {
    void add(int seqnum, short in[], int pos, int len);
    boolean get(short out[], int pos, int len);
}
