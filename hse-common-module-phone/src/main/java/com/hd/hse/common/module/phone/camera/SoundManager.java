package com.hd.hse.common.module.phone.camera;

import com.hd.hse.common.module.phone.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

@SuppressWarnings("all")
public class SoundManager {
    public final static int SOUND_SHUTTER = 0;
    private final static int SOUND_MAX = 1;

    private static SoundManager mSingleton;

    public static SoundManager getSingleton() {
        if (mSingleton == null) {
            mSingleton = new SoundManager();
        }

        return mSingleton;
    }

    private SoundPool mSoundPool;
    private int[] mSoundsFD = new int[SOUND_MAX];

    private SoundManager() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_NOTIFICATION, 0);
    }

    public void preload(Context ctx) {
        mSoundsFD[SOUND_SHUTTER] = mSoundPool.load(ctx, R.raw.snd_capture, 1);
    }

    public void play(int sound) {
        mSoundPool.play(mSoundsFD[sound], 1.0f, 1.0f, 0, 0, 1.0f);
    }
}
