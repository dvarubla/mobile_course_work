package study.courseproject.task3;

import android.content.Context;

import java.util.concurrent.ExecutorService;

class JumpTriangleFact implements IJumpTriangleFact {
    private Context context;
    private IConfig config;
    JumpTriangleFact(Context context, IConfig config){
        this.context=context;
        this.config=config;
    }

    @Override
    public IJumpTrianglePresenter create(IJumpObjsContainer container, ExecutorService executor) {
        JumpTriangleView v=new JumpTriangleView(context);
        v.setConfig(config);
        JumpTriangleModel m=new JumpTriangleModel(executor, config);
        JumpTrianglePresenter p=new JumpTrianglePresenter(v, m);
        m.setListener(p);
        v.setPresenter(p);
        p.setParent(container);
        return p;
    }
}
