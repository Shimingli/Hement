package com.shiming.hement.ui.life_cycle_demo;

/**
 * <p>
 *  事件的累计
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/12/4 20:23
 */

public class ExtendEvents<T> {

    private int code;

    private T content;

    private int mflagChoose;

    public ExtendEvents(int code, T content) {
        this.code = code;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
    public int getMflagChoose() {
        return mflagChoose;
    }

    public void setMflagChoose(int mflagChoose) {
        this.mflagChoose = mflagChoose;
    }

    @Override
    public String toString() {
        return "ExtendEvents 接受的="+code;
    }
}
