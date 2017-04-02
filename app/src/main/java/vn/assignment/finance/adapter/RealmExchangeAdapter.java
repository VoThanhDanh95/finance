package vn.assignment.finance.adapter;

import android.content.Context;

import io.realm.RealmResults;
import vn.assignment.finance.model.ExchangeResponse;

/**
 * Created by Ray on 3/16/17.
 */

public class RealmExchangeAdapter extends RealmModelAdapter<ExchangeResponse> {
    public RealmExchangeAdapter(Context context, RealmResults<ExchangeResponse> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
}
