package javaforce.media;


public class AudioBufferImpl implements AudioBuffer {
    private final CircularBuffer samplesBuffer;

    public AudioBufferImpl(int freq, int chs, int seconds) {
        this.samplesBuffer = new CircularBuffer(freq * chs * seconds);
    }

    @Override
    public synchronized void add(int seqNum, short[] samples) {
        samplesBuffer.add(samples, 0, samples.length);
    }

    @Override
    public synchronized boolean get(short[] out) {
        return samplesBuffer.get(out, 0, out.length);
    }
}
