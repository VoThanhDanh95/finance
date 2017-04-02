package vn.assignment.finance.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import vn.assignment.finance.R;
import vn.assignment.finance.adapter.ExchangeRateAdapter;
import vn.assignment.finance.adapter.RealmExchangeAdapter;
import vn.assignment.finance.base.BaseFragment;
import vn.assignment.finance.base.Constant;
import vn.assignment.finance.db.RealmController;
import vn.assignment.finance.model.ExchangeResponse;
import vn.assignment.finance.model.TransferResponse;
import vn.assignment.finance.module.AppPresenter;
import vn.assignment.finance.module.ExchangePresenterImpl;
import vn.assignment.finance.util.ConnectUtils;
import vn.assignment.finance.util.LoaderUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class TyGiaFragment extends BaseFragment implements AppPresenter.ExchangeView {


    @BindView(R.id.rvData)
    RecyclerView rvData;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ExchangePresenterImpl presenter;
    private ExchangeRateAdapter adapter;
    private List<TransferResponse> list;
    private Realm realm;

    public TyGiaFragment() {
        // Required empty public constructor
    }

    public static TyGiaFragment newInstance() {
        TyGiaFragment fragment = new TyGiaFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ty_gia;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = RealmController.with(getMainActivity()).getRealm();
        presenter = new ExchangePresenterImpl(getContext());
        adapter = new ExchangeRateAdapter(getBaseActivity());
        list = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        presenter.bind(this);
        init();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unbind();
    }

    @OnClick(R.id.fab)
    void onClick() {
        int x = fab.getLeft() + ((fab.getRight() - fab.getLeft()) / 2);
        int y = fab.getTop() + ((fab.getBottom() - fab.getTop()) / 2);
        list.add(0, new TransferResponse("VND", "VIETNAM DONG", 1d, 1d, 1d));
        changeFragment(TransferFragment.newInstance(list, x, y, R.color.colorAccent), true);
    }

    private void init() {
        if (!isAdded())
            return;
        getMainActivity().showToolbar(true);
        getMainActivity().setLeftIcon(R.drawable.ic_menu_white_24dp);
        getMainActivity().setTitleToolbar("Tỷ giá");
        RealmController.with(getMainActivity()).refresh();
        execute();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                execute();
            }
        });
        rvData.setNestedScrollingEnabled(false);
        rvData.setLayoutManager(new LinearLayoutManager(getContext()));
        rvData.setAdapter(adapter);
    }

    private void execute() {
        try {
            LoaderUtils.show(getContext());
            presenter.getData(ConnectUtils.getInstance().downloadUrl(Constant.BASE_URL.URL));
        } catch (IOException e) {
            LoaderUtils.hide();
            swipeRefresh.setRefreshing(false);
            Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void setRealmAdapter(RealmResults<ExchangeResponse> obj) {
        RealmExchangeAdapter exchangeAdapter = new RealmExchangeAdapter(getContext(), obj, true);
        adapter.setRealmAdapter(exchangeAdapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getDataSuccess(List<ExchangeResponse> list) {
        LoaderUtils.hide();
        swipeRefresh.setRefreshing(false);
//        Toast.makeText(getContext(), list.size() + "", Toast.LENGTH_LONG).show();
        Observable.just(list)
                .map(new Func1<List<ExchangeResponse>, RealmResults<ExchangeResponse>>() {
                    @Override
                    public RealmResults<ExchangeResponse> call(List<ExchangeResponse> list) {
                        for (ExchangeResponse ex : list) {
                            TyGiaFragment.this.list.add(new TransferResponse(ex.getCode(), ex.getName(),
                                    ex.getBuy(), ex.getTransfer(), ex.getSell()));
                            if (!RealmController.getInstance().hasObject(ExchangeResponse.class)) {
                                realm.beginTransaction();
                                realm.copyToRealm(ex);
                                realm.commitTransaction();
                            } else {
                                realm.beginTransaction();
                                realm.copyToRealmOrUpdate(ex);
                                realm.commitTransaction();
                            }
                        }

                        return RealmController.with(getMainActivity()).getAllExchangeRate();
                    }
                })
                .subscribe(new Action1<RealmResults<ExchangeResponse>>() {
                    @Override
                    public void call(RealmResults<ExchangeResponse> results) {
                        setRealmAdapter(results);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void error(String msg) {
        LoaderUtils.hide();

        swipeRefresh.setRefreshing(false);
        Toast.makeText(getContext(), msg + "", Toast.LENGTH_LONG).show();
    }
}
