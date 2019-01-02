package com.shiming.hement.lifecycle;

import com.shiming.hement.utils.Events;



/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/12/19 14:52
 */

public class SyncResponse {
    public final SyncResponseEventType eventType;
    public final Events comment;

    public SyncResponse(SyncResponseEventType eventType, Events comment) {
        this.eventType = eventType;
        this.comment = comment;
    }
}