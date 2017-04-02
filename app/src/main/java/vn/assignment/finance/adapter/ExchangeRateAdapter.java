package vn.assignment.finance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import vn.assignment.finance.R;
import vn.assignment.finance.base.BaseAdapter;
import vn.assignment.finance.base.BaseRealmAdapter;
import vn.assignment.finance.db.RealmController;
import vn.assignment.finance.factory.DataFactory;
import vn.assignment.finance.model.ExchangeResponse;
import vn.eazy.core.base.activity.BaseActivity;

/**
 * Created by Ray on 3/15/17.
 */

public class ExchangeRateAdapter extends BaseRealmAdapter<ExchangeResponse> {

    private Context context;
    private Realm realm;

    public ExchangeRateAdapter(Context context){
        this.context = context;
    }

    @Override
    public ExchangeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExchangeViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exchange_rate, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ExchangeViewHolder exchangeViewHolder = (ExchangeViewHolder) holder;
        exchangeViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (getRealmBaseAdapter() != null)
            return getRealmBaseAdapter().getCount();
        return 0;
    }

    class ExchangeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.profile_image)
        CircleImageView profileImage;
        @BindView(R.id.tvBuy)
        TextView tvBuy;
        @BindView(R.id.tvTransfer)
        TextView tvTransfer;
        @BindView(R.id.tvSell)
        TextView tvSell;

        public ExchangeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(int position) {
            realm = RealmController.getInstance().getRealm();
            ExchangeResponse model = getItem(position);
            if (model == null)
                return;
            Glide.with(context).load(DataFactory.getInstance().getFlag(model.getCode())).into(profileImage);
            tvTitle.setText(model.getCode() + " (" + model.getName() + ")");
            tvBuy.setText(model.getBuy() + "");
            tvTransfer.setText(model.getTransfer() + "");
            tvSell.setText(model.getSell() + "");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
