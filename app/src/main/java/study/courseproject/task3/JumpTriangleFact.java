package study.courseproject.task3;

import android.content.Context;

import java.util.concurrent.ExecutorService;

class JumpTriangleFact implements IJumpTriangleFact {
    private Context context;
    JumpTriangleFact(Context context){
        this.context=context;
    }

    @Override
    public IJumpTrianglePresenter create(IJumpObjsContainer container, ExecutorService executor) {
        JumpTriangleView v=new JumpTriangleView(context);
        JumpTriangleModel m=new JumpTriangleModel(executor);
        JumpTrianglePresenter p=new JumpTrianglePresenter(v, m);
        m.setListener(p);
        v.setPresenter(p);
        p.setParent(container);
        return p;
    }
}
