package javaforce.media;


public class AudioBufferImpl implements IAudioBuffer {
    private final CircularBuffer samplesBuffer;

    public AudioBufferImpl(int freq, int chs, int seconds) {
        this.samplesBuffer = new CircularBuffer(freq * chs * seconds);
    }

    @Override
    public synchronized void add(int seqnum, short[] in, int pos, int len) {
        samplesBuffer.add(in, pos, len);
    }

    @Override
    public synchronized boolean get(short[] out, int pos, int len) {
        return samplesBuffer.get(out, pos, len);
    }
}
