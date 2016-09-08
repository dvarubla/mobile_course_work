package study.courseproject.task3;

import android.content.Context;

class JumpTriangleFact implements IJumpTriangleFact {
    private Context context;
    JumpTriangleFact(Context context){
        this.context=context;
    }
    @Override
    public IJumpTrianglePresenter create() {
        JumpTriangleView v=new JumpTriangleView(context);
        JumpTrianglePresenter p=new JumpTrianglePresenter(v);
        v.setPresenter(p);
        return p;
    }
}
