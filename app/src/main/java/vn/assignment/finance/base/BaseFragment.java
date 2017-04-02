package vn.assignment.finance.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import vn.assignment.finance.ui.activity.FinanceActivity;
import vn.eazy.core.base.activity.BaseMainActivity;
import vn.eazy.core.base.fragment.BaseMainFragment;
import vn.eazy.core.helper.FragmentHelper;

/**
 * Created by Ray on 3/15/17.
 */

public class BaseFragment extends BaseMainFragment {
    protected FinanceActivity activity;

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (FinanceActivity) getBaseActivity();
    }

    public FinanceActivity getMainActivity() {
        return activity;
    }

    public FragmentHelper getFragmentHelper() {
        return activity.fragmentHelper;
    }

    public void changeFragment(vn.eazy.core.base.fragment.BaseFragment fragment, boolean isBackStack) {
        activity.changeFragment(fragment, isBackStack);
    }

    public void setRightButtonToolbar(String text, View.OnClickListener onClickListener) {
        getMainActivity().getToolbarHelper().setRightToolbarButton(text, onClickListener);
    }

    public void setRightButtonToolbar(int iconRes, View.OnClickListener onClickListener) {
        getMainActivity().getToolbarHelper().setRightToolbarButton(iconRes, onClickListener);
    }

    public void changeToolbarColor(int color) {
//        activity.changeToolbarColor(color);
    }
}
