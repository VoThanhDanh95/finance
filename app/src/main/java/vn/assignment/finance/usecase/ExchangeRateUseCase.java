package vn.assignment.finance.usecase;

import android.content.Context;

import io.reactivex.Flowable;
import vn.assignment.finance.base.BaseResponseObject;
import vn.assignment.finance.model.ExchangeResponse;
import vn.assignment.finance.network.ApiHelper;
import vn.eazy.architect.mvp.usecase.BaseUseCase;
import vn.eazy.architect.mvp.usecase.action.StringParamRequestUseCase;

/**
 * Created by Ray on 3/15/17.
 */

public class ExchangeRateUseCase extends BaseUseCase
        implements StringParamRequestUseCase<BaseResponseObject<ExchangeResponse>>{
    public ExchangeRateUseCase(Context context) {
        super(context);
    }

    @Override
    public Flowable<BaseResponseObject<ExchangeResponse>> request(String... strings) {
        return ApiHelper.getInstance().getApiService().getExchange(strings[0]);
    }
}
