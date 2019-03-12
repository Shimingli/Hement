package com.shiming.base.widget.touchfixtextview;

import android.view.View;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2019/3/12 14:22
 */

public interface ITouchableSpan {
    void setPressed(boolean pressed);
    void onClick(View widget);
}