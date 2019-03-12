package com.shiming.base.widget.touchfixtextview;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2019/3/12 14:19
 */

public interface ISpanTouchFix {
    /**
     * 记录当前 Touch 事件对应的点是不是点在了 span 上面
     */
    void setTouchSpanHit(boolean hit);
}
