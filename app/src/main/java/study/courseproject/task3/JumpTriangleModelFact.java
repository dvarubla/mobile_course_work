package study.courseproject.task3;

import java.util.concurrent.ExecutorService;

class JumpTriangleModelFact implements IJumpTriangleModelFact{
    private IConfig config;
    JumpTriangleModelFact(IConfig config){
        this.config=config;
    }

    @Override
    public IJumpTriangleModel create(IJumpTriangleModelListener listener, ExecutorService executor){
        JumpTriangleModel m=new JumpTriangleModel(executor, config);
        m.setListener(listener);
        return m;
    }
}
