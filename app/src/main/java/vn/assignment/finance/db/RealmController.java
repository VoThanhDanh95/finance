package vn.assignment.finance.db;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import vn.assignment.finance.model.ExchangeResponse;

/**
 * Created by Ray on 3/16/17.
 */

public class RealmController {
    private static RealmController instance = null;
    private Realm realm = null;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment){
        if (instance == null)
            instance = new RealmController(fragment.getActivity().getApplication());
        return instance;
    }

    public static RealmController with(Activity activity){
        if (instance == null)
            instance = new RealmController(activity.getApplication());
        return instance;
    }

    public static RealmController with(Application application){
        if (instance == null)
            instance = new RealmController(application);
        return instance;
    }

    public static RealmController getInstance(){
        return instance;
    }

    public Realm getRealm(){
        return realm;
    }

    public void refresh(){
        realm.refresh();
    }

    public void clearAll(Class<? extends RealmObject> cls){
        if (realm == null)
            return;
        realm.beginTransaction();
        realm.clear(cls);
        realm.commitTransaction();
    }

    public RealmResults<ExchangeResponse> getAllExchangeRate(){
        return realm.where(ExchangeResponse.class).findAll();
    }

    public ExchangeResponse getExchange(String code){
        return realm.where(ExchangeResponse.class).equalTo("code", code).findFirst();
    }

    public Boolean hasObject(Class<? extends RealmObject> cls){
        return !realm.allObjects(cls).isEmpty();
    }
}
