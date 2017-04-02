package vn.assignment.finance.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import okhttp3.internal.http.StatusLine;
import vn.assignment.finance.R;
import vn.assignment.finance.base.ToolbarAppHelper;
import vn.assignment.finance.ui.fragment.TransferFragment;
import vn.assignment.finance.ui.fragment.TyGiaFragment;
import vn.assignment.finance.widget.WidgetUtils;
import vn.eazy.core.base.activity.BaseActivity;
import vn.eazy.core.base.activity.BaseMainActivity;
import vn.eazy.core.base.fragment.BaseFragment;
import vn.eazy.core.toolbar.OnCallBackToolbarAction;
import vn.eazy.core.toolbar.ToolbarHelper;

public class FinanceActivity extends BaseMainActivity<ToolbarAppHelper>
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        changeFragment(TyGiaFragment.newInstance(), false);

    }

    @Override
    public void setUpViewsAndData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_finance;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setTitleToolbar(String s) {
        getToolbarHelper().setTitle(s);
    }

    @Override
    public void setTitleToolbar(String s, String s1) {

    }

    @Override
    public void setTitleMainColor(int i) {

    }

    @Override
    public int onColorOfToolbar() {
        return R.color.colorPrimaryDark;
    }

    @Override
    public int onImageForLeftButtonToolbar() {
        return 0;
    }

    @Override
    public void showBackButton(boolean b) {

    }

    @Override
    public void showMenu(boolean b) {

    }

    @Override
    public boolean useFragmentState() {
        return false;
    }

    public void changeFragment(BaseFragment fragment, boolean isBackStack) {
        if (isBackStack) {
            toolbarHelper.showBackButton(true, new ClickBackButtonListener());

        } else {

            toolbarHelper.showBackButton(false, null);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }
        fragmentHelper.replaceFragment(fragment, isBackStack, R.anim.fade_in, R.anim.fade_out);
    }

    class ClickBackButtonListener implements OnCallBackToolbarAction {

        @Override
        public void onCallBackToolbar() {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        WidgetUtils.hideSoftKeyboard(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public ToolbarAppHelper getToolbarHelper() {
        try {
            return new ToolbarAppHelper(toolbar);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void showToolbar(boolean isShow) {
        if (toolbar == null)
            return;
        toolbar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void setLeftIcon(int id) {
        toolbar.setNavigationIcon(id);
    }


}
