package com.tibiagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class SoundManager {
    public Sound hitSound;
    public Sound attackSound;
    public Sound deathSound;
    public Sound levelUpSound;
    private FileHandle tempDir;

    public SoundManager() {
        tempDir = Gdx.files.local(".tibia_sounds");
        tempDir.mkdirs();
        hitSound = generateWav(220, 0.15f, 0.3f, "hit.wav");
        attackSound = generateWav(440, 0.1f, 0.5f, "attack.wav");
        deathSound = generateWav(120, 0.3f, 0.6f, "death.wav");
        levelUpSound = generateChime();
    }

    public void playHit() { if (hitSound != null) hitSound.play(0.5f); }
    public void playAttack() { if (attackSound != null) attackSound.play(0.4f); }
    public void playDeath() { if (deathSound != null) deathSound.play(0.6f); }
    public void playLevelUp() { if (levelUpSound != null) levelUpSound.play(0.7f); }

    private Sound generateWav(float freqHz, float durationSec, float volume, String name) {
        try {
            int sampleRate = 22050;
            int numSamples = (int)(sampleRate * durationSec);
            short[] samples = new short[numSamples];
            for (int i = 0; i < numSamples; i++) {
                float t = (float)i / sampleRate;
                float env = 1.0f - (t / durationSec);
                double sample = Math.sin(2 * Math.PI * freqHz * t) * volume * env;
                sample = Math.max(-1.0, Math.min(1.0, sample));
                samples[i] = (short)(sample * Short.MAX_VALUE);
            }
            byte[] wav = buildWav(samples, sampleRate);
            FileHandle f = tempDir.child(name);
            f.writeBytes(wav, false);
            return Gdx.audio.newSound(f);
        } catch (Exception e) {
            return null;
        }
    }

    private Sound generateChime() {
        try {
            int sampleRate = 22050;
            float duration = 0.4f;
            int numSamples = (int)(sampleRate * duration);
            short[] samples = new short[numSamples];
            float[] freqs = {523, 659, 784};
            for (int i = 0; i < numSamples; i++) {
                float t = (float)i / sampleRate;
                float env = Math.max(0, 1.0f - (t / duration));
                double sample = 0;
                for (float f : freqs) {
                    sample += Math.sin(2 * Math.PI * f * t) * 0.3 * env;
                }
                sample = Math.max(-1.0, Math.min(1.0, sample));
                samples[i] = (short)(sample * Short.MAX_VALUE);
            }
            byte[] wav = buildWav(samples, sampleRate);
            FileHandle f = tempDir.child("levelup.wav");
            f.writeBytes(wav, false);
            return Gdx.audio.newSound(f);
        } catch (Exception e) {
            return null;
        }
    }

    private byte[] buildWav(short[] samples, int sampleRate) throws Exception {
        int dataSize = samples.length * 2;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(44 + dataSize);
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeBytes("RIFF");
        writeInt(dos, 36 + dataSize);
        dos.writeBytes("WAVE");
        dos.writeBytes("fmt ");
        writeInt(dos, 16);
        writeShort(dos, 1);
        writeShort(dos, 1);
        writeInt(dos, sampleRate);
        writeInt(dos, sampleRate * 2);
        writeShort(dos, 2);
        writeShort(dos, 16);
        dos.writeBytes("data");
        writeInt(dos, dataSize);
        for (short s : samples) writeShort(dos, s);

        dos.close();
        return baos.toByteArray();
    }

    private void writeInt(DataOutputStream dos, int v) throws Exception {
        dos.write(v & 0xff);
        dos.write((v >> 8) & 0xff);
        dos.write((v >> 16) & 0xff);
        dos.write((v >> 24) & 0xff);
    }

    private void writeShort(DataOutputStream dos, int v) throws Exception {
        dos.write(v & 0xff);
        dos.write((v >> 8) & 0xff);
    }

    public void dispose() {
        if (hitSound != null) hitSound.dispose();
        if (attackSound != null) attackSound.dispose();
        if (deathSound != null) deathSound.dispose();
        if (levelUpSound != null) levelUpSound.dispose();
        if (tempDir != null && tempDir.isDirectory()) {
            for (FileHandle child : tempDir.list()) child.delete();
            tempDir.delete();
        }
    }
}
