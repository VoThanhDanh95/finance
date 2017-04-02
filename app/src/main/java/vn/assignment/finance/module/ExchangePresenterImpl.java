package vn.assignment.finance.module;

import android.content.Context;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import vn.assignment.finance.model.ExchangeResponse;
import vn.assignment.finance.util.Parser;
import vn.eazy.architect.mvp.base.BasePresenter;

/**
 * Created by Ray on 3/15/17.
 */

public class ExchangePresenterImpl extends BasePresenter<AppPresenter.ExchangeView>
        implements AppPresenter.ExchangePresenter {
    public ExchangePresenterImpl(Context context) {
        super(context);
    }

    @Override
    public void getData(InputStream inputStream) {
        Observable.just(inputStream)
                .subscribeOn(Schedulers.io())
                .map(new Func1<InputStream, List<ExchangeResponse>>() {
                    @Override
                    public List<ExchangeResponse> call(InputStream inputStream) {
                        List<ExchangeResponse> list = new ArrayList<ExchangeResponse>();
                        try {
                            list = Parser.getInstance().parse(inputStream);
                            // Makes sure that the InputStream is closed after the app is
                            // finished using it.
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ExchangeResponse>>() {
                    @Override
                    public void call(List<ExchangeResponse> list) {
                        if (isAttached())
                            getView().getDataSuccess(list);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (isAttached())
                            throwable.printStackTrace();
                            getView().error(throwable.getLocalizedMessage());
                    }
                });
    }
}
