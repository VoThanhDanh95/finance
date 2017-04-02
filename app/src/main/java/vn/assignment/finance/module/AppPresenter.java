package vn.assignment.finance.module;

import java.io.InputStream;
import java.util.List;

import vn.assignment.finance.model.ExchangeResponse;
import vn.eazy.architect.mvp.base.BasePresenter;

/**
 * Created by Ray on 3/15/17.
 */

public interface AppPresenter {

    //Exchange
    interface ExchangeView extends BasePresenter.View{
        void getDataSuccess(List<ExchangeResponse> list);
        void error(String msg);
    }
    interface ExchangePresenter{
        void getData(InputStream inputStream);
    }
}
