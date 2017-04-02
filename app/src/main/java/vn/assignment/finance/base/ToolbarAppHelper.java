package vn.assignment.finance.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import vn.assignment.finance.R;
import vn.assignment.finance.listener.OnToolbarAppAction;
import vn.eazy.core.toolbar.ToolbarHelper;

/**
 * Created by Ray on 3/15/17.
 */

public class ToolbarAppHelper  extends ToolbarHelper implements OnToolbarAppAction {
    private Context context;
    public ToolbarAppHelper(Toolbar toolbar) throws IllegalAccessException {
        super(toolbar);
        context = toolbar.getContext();
    }


    @Override
    public void showSearchView(boolean isShow) {
        if(isShow){
            tvTitle.setVisibility(View.GONE);
//            edtSearch.setVisibility(View.VISIBLE);
        }else {
//            edtSearch.setVisibility(View.GONE);
            tvTitle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setTitle(@NonNull String title) {
        if(TextUtils.isEmpty(title)){
            showSearchView(true);
        }
        else {
            showSearchView(false);
            tvTitle.setText(title);
        }
    }
}
