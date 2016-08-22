package study.courseproject;

import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

class Util {
    public static List<View> getViewsByTag(View root, String tag) {
        List<View> result = new LinkedList<>();

        if (root instanceof ViewGroup) {
            final int childCount = ((ViewGroup) root).getChildCount();
            for (int i = 0; i < childCount; i++) {
                result.addAll(getViewsByTag(((ViewGroup) root).getChildAt(i), tag));
            }
        }

        final Object rootTag = root.getTag();
        if (tag == rootTag || (tag != null && tag.equals(rootTag))) {
            result.add(root);
        }

        return result;
    }
}
