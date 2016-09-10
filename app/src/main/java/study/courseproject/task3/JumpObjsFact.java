package study.courseproject.task3;

import android.widget.RelativeLayout;

import java.util.concurrent.ExecutorService;

class JumpObjsFact implements IJumpObjsFact{
    private IJumpTriangleFact triangleFact;
    private IConfig config;
    JumpObjsFact(IJumpTriangleFact triangleFact, IConfig config){
        this.triangleFact=triangleFact;
        this.config=config;
    }

    @Override
    public IJumpObjsPresenter create(RelativeLayout layout) {
        JumpObjsView v=new JumpObjsView(layout, config);
        JumpObjsPresenter p=new JumpObjsPresenter(v, triangleFact);
        v.setPresenter(p);
        return p;
    }
}
