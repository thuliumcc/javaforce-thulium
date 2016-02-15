package javaforce.media;


public interface AudioBuffer {
    void add(int seqNum, short samples[]);

    boolean get(short out[]);
}
