package study.courseproject.task4;

import study.courseproject.task3.*;

import java.util.concurrent.ExecutorService;

class JumpTriangleSoundModelFact implements IJumpTriangleModelFact {
    private IConfig config;
    private ISoundPlayer player;
    JumpTriangleSoundModelFact(IConfig config, ISoundPlayer player){
        this.config=config;
        this.player=player;
    }

    @Override
    public IJumpTriangleModel create(IJumpTriangleModelListener listener, ExecutorService executor) {
        JumpTriangleModel m=new JumpTriangleModel(executor, config);
        m.setListener(new JumpTriangleSoundModelListener(listener, player));
        return m;
    }
}
