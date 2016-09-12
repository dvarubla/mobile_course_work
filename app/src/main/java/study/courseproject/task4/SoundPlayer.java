package study.courseproject.task4;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import study.courseproject.R;
import study.courseproject.task3.IConfig;

import java.util.ArrayList;

class SoundPlayer implements ISoundPlayer{
    private SoundPool pool;
    private IConfig config;
    private int soundId;
    private boolean stopped;
    private ArrayList<Integer> streams;
    private static int MAX_STREAMS=6;

    SoundPlayer(Context context, IConfig config){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            createPoolLollipop();
        } else {
            createPoolOld();
        }
        soundId=pool.load(context, R.raw.bell, 1);
        this.config=config;
        stopped=false;
        streams = new ArrayList<>();
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
    public synchronized void play(){
        if(!stopped) {
            float volume = (float) config.<Double>getValue(Task4ConfigName.SOUND_VOLUME).doubleValue();
            streams.add(pool.play(soundId, volume, volume, 1, 0, 1f));
        }
    }

    @Override
    public synchronized void stop() {
        stopped=true;
        for(Integer stream : streams) {
            pool.stop(stream);
        }
        streams.clear();
    }

    @Override
    public synchronized void resume() {
        stopped=false;
    }
}
