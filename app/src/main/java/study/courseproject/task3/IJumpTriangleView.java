package study.courseproject.task3;

import android.view.View;

interface IJumpTriangleView {

    DisplayLimits getLimits();
    View getRealView();
    void setCoords(float x, float y);
    void setMaxCoords(float x, float y);
}
