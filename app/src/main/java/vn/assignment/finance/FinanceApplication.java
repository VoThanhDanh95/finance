package vn.assignment.finance;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Ray on 3/16/17.
 */

public class FinanceApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realm = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realm);
    }
}
