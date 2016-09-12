package study.courseproject.task3;

import android.content.Context;

import java.util.concurrent.ExecutorService;

public class JumpTriangleFact implements IJumpTriangleFact {
    private Context context;
    private IConfig config;
    private IJumpTriangleModelFact modelFact;
    public JumpTriangleFact(Context context, IConfig config, IJumpTriangleModelFact modelFact){
        this.context=context;
        this.config=config;
        this.modelFact=modelFact;
    }

    @Override
    public IJumpTrianglePresenter create(IJumpObjsContainer container, ExecutorService executor) {
        JumpTriangleView v=new JumpTriangleView(context);
        v.setConfig(config);
        JumpTrianglePresenter p=new JumpTrianglePresenter(v);
        IJumpTriangleModel m=modelFact.create(p, executor);
        p.setModel(m);
        v.setPresenter(p);
        p.setParent(container);
        return p;
    }
}
