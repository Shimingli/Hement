package com.shiming.hement.ui.network;

import com.shiming.hement.data.model.TodayBean;
import com.shiming.network.IMvpView;

import java.util.ArrayList;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 17:26
 */

interface NetWorkView extends IMvpView {

    void  getDataSuccess(ArrayList<TodayBean> result);


}
