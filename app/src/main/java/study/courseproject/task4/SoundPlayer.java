package study.courseproject.task4;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import study.courseproject.R;
import study.courseproject.task3.IConfig;

class SoundPlayer implements ISoundPlayer{
    private SoundPool pool;
    private IConfig config;
    private int soundId;
    private int MAX_STREAMS=6;

    SoundPlayer(Context context, IConfig config){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            createPoolLollipop();
        } else {
            createPoolOld();
        }
        soundId=pool.load(context, R.raw.bell, 1);
        this.config=config;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createPoolLollipop(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        pool=new SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(MAX_STREAMS).build();
    }

    @SuppressWarnings("deprecation")
    private void createPoolOld(){
        pool=new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public void play(){
        float volume=(float)config.<Double>getValue(ITask4ConfigName.SOUND_VOLUME).doubleValue();
        pool.play(soundId, volume, volume, 1, 0, 1f);
    }
}
