package vn.assignment.finance.api;

import io.reactivex.Flowable;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import vn.assignment.finance.base.BaseResponseObject;
import vn.assignment.finance.model.ExchangeResponse;

/**
 * Created by Ray on 3/15/17.
 */

public interface ApiService {
    @PUT("latest")
    Flowable<BaseResponseObject<ExchangeResponse>> getExchange(@Query("base") String base);
}
