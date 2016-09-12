package study.courseproject.task4;

import study.courseproject.task3.IJumpTriangleModelListener;

class JumpTriangleSoundModelListener implements IJumpTriangleModelListener {
    private IJumpTriangleModelListener listener;
    private ISoundPlayer player;
    JumpTriangleSoundModelListener(IJumpTriangleModelListener listener, ISoundPlayer player){
        this.listener=listener;
        this.player=player;
    }

    @Override
    public void setCoords(float x, float y) {
        listener.setCoords(x, y);
    }

    @Override
    public void notifyStop() {
        listener.notifyStop();
    }

    @Override
    public void notifyCollide() {
        player.play();
        listener.notifyCollide();
    }
}
