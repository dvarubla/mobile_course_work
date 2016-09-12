package study.courseproject.task4;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import study.courseproject.R;

class SoundPlayer implements ISoundPlayer{
    private SoundPool pool;
    private int soundId;
    private int MAX_STREAMS=6;

    SoundPlayer(Context context){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            createPoolLollipop();
        } else {
            createPoolOld();
        }
        soundId=pool.load(context, R.raw.bell, 1);
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
        pool.play(soundId, 1.0f, 1.0f, 1, 0, 1f);
    }
}
