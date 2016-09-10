package study.courseproject.task3;

import android.widget.RelativeLayout;

import java.util.concurrent.ExecutorService;

class JumpObjsFact implements IJumpObjsFact{
    private IJumpTriangleFact triangleFact;
    JumpObjsFact(IJumpTriangleFact triangleFact){
        this.triangleFact=triangleFact;
    }

    @Override
    public IJumpObjsPresenter create(RelativeLayout layout) {
        JumpObjsView v=new JumpObjsView(layout);
        JumpObjsPresenter p=new JumpObjsPresenter(v, triangleFact);
        v.setPresenter(p);
        return p;
    }
}
