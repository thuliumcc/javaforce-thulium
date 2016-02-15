package javaforce.media;


public class DefaultAudioBufferFactory implements AudioBufferFactory {
    @Override
    public AudioBuffer create(int sampleRate) {
        return new AudioBufferImpl(sampleRate, 1, 1);
    }
}
