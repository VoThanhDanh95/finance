package vn.assignment.finance.ui.fragment;


import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.functions.Func1;
import vn.assignment.finance.R;
import vn.assignment.finance.adapter.ExchangeRateAdapter;
import vn.assignment.finance.base.BaseFragment;
import vn.assignment.finance.model.TransferResponse;
import vn.assignment.finance.module.ExchangePresenterImpl;
import vn.assignment.finance.widget.WidgetUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransferFragment extends BaseFragment {


    @BindView(R.id.spnOrigin)
    Spinner spnOrigin;
    @BindView(R.id.spnDestination)
    Spinner spnDestination;
    @BindView(R.id.tvConvert)
    TextView tvConvert;
    @BindView(R.id.edtOrigin)
    EditText edtOrigin;
    @BindView(R.id.edtDestination)
    EditText edtDestination;
    @BindView(R.id.tvRootName)
    TextView tvRootName;
    @BindView(R.id.tvDesName)
    TextView tvDesName;
    @BindView(R.id.container)
    RelativeLayout container;
    private ExchangePresenterImpl presenter;
    private ExchangeRateAdapter adapter;
    private DecimalFormat format;

    private List<TransferResponse> listRoot, listDestination;

    public TransferFragment() {
        // Required empty public constructor
    }

    public static TransferFragment newInstance(List<TransferResponse> list,
                                               int centerX, int centerY, int color) {
        Bundle args = new Bundle();
        args.putInt("cx", centerX);
        args.putInt("cy", centerY);
        args.putInt("color", color);
        TransferFragment fragment = new TransferFragment();
        fragment.listRoot = list;
        fragment.listDestination = list;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_transfer;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        format = new DecimalFormat("###.##");
//        presenter = new ExchangePresenterImpl(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        startAnimation();
//        presenter.bind(this);
        init();
        return rootView;
    }

    @Override
    public void onDestroyView() {super.onDestroyView();
//        presenter.unbind();
    }

    private void startAnimation(){
        rootView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

        // To run the animation as soon as the view is layout in the view hierarchy we add this
        // listener and remove it
        // as soon as it runs to prevent multiple animations if the view changes bounds
        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                                       int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                int cx = getArguments().getInt("cx");
                int cy = getArguments().getInt("cy");

                // get the hypothenuse so the radius is from one corner to the other
                int radius = (int) Math.hypot(right, bottom);

                Animator reveal = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, radius);
                reveal.setInterpolator(new DecelerateInterpolator(2f));
                reveal.setDuration(1000);
                reveal.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                reveal.start();
            }
        });
    }

    @OnClick(R.id.tvConvert)
    void convert() {
        if (edtOrigin.getText().toString().equals("")) {
            edtOrigin.setError("Vui lòng nhập giá trị");
        } else {
            TransferResponse root = (TransferResponse) spnOrigin.getSelectedItem();
            TransferResponse des = (TransferResponse) spnDestination.getSelectedItem();
            if (root == null || des == null)
                return;
            double temp = root.getTransfer() / des.getTransfer();
            double input = Double.parseDouble(edtOrigin.getText().toString());
            edtDestination.setText(format.format(input * temp));
        }
    }

    private void init() {
        if (!isAdded())
            return;
        WidgetUtils.setUpHideSoftKeyboard(getBaseActivity(), container);
        getMainActivity().showToolbar(false);
        getMainActivity().setLeftIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        getMainActivity().setTitleToolbar("Chuyển đổi");
        setRoot();
        setDestination();
    }

    private void setRoot() {
        if (spnOrigin == null)
            return;
        if (listRoot == null || listRoot.size() == 0)
            return;
        ArrayAdapter<TransferResponse> dataAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, listRoot);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnOrigin.setAdapter(dataAdapter);
        RxTextView.afterTextChangeEvents(edtOrigin)
                .map(new Func1<TextViewAfterTextChangeEvent, Double>() {
                    @Override
                    public Double call(TextViewAfterTextChangeEvent event) {
                        TransferResponse root = (TransferResponse) spnOrigin.getSelectedItem();
                        TransferResponse des = (TransferResponse) spnDestination.getSelectedItem();
                        if (root == null || des == null || event.view().getText().toString().equals(""))
                            return 0d;
                        double temp = root.getTransfer() / des.getTransfer();
                        double input = Double.parseDouble(event.view().getText().toString());
                        return input * temp;
                    }
                })
                .subscribe(new Action1<Double>() {
                    @Override
                    public void call(Double aDouble) {
                        edtDestination.setText(format.format(aDouble));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
        spnOrigin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TransferResponse item = (TransferResponse) parent.getItemAtPosition(position);
                if (item == null)
                    return;
                tvRootName.setText(item.getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TransferResponse item = (TransferResponse) parent.getItemAtPosition(position);
                if (item == null)
                    return;
                tvDesName.setText(item.getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDestination() {
        if (spnDestination == null)
            return;
        if (listDestination == null || listDestination.size() == 0)
            return;
        ArrayAdapter<TransferResponse> dataAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, listDestination);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDestination.setAdapter(dataAdapter);
    }

}
