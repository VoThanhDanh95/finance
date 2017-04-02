package vn.assignment.finance.base;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import vn.eazy.core.base.activity.BaseActivity;

/**
 * Created by Ray on 3/16/17.
 */

public abstract class BaseRealmAdapter<T extends RealmObject> extends RecyclerView.Adapter{

    private RealmBaseAdapter<T> realmBaseAdapter;

    public T getItem(int position){
        return realmBaseAdapter.getItem(position);
    }

    public void setList(List<T> list){
    }

    public RealmBaseAdapter<T> getRealmBaseAdapter(){
        return realmBaseAdapter;
    }

    public void setRealmAdapter(RealmBaseAdapter<T> realmAdapter){
        realmBaseAdapter = realmAdapter;
    }
}
