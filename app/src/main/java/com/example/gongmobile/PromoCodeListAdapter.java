package com.example.gongmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gongmobile.api.model.ListedCoupon;

public class PromoCodeListAdapter extends RecyclerView.Adapter<PromoCodeListAdapter.ViewHolder> {
    private final ListedCoupon[] codes;
    private PromoCodeListAdapter.Listener listener;

    interface Listener {
        void onClick(ListedCoupon code);
    }

    public void setListener(PromoCodeListAdapter.Listener listener) {
        this.listener = listener;
    }

    public PromoCodeListAdapter(ListedCoupon[] codes) {
        this.codes = codes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @NonNull
    @Override
    public PromoCodeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cv = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.promo_code_card, viewGroup, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoCodeListAdapter.ViewHolder holder, int i) {
        CardView cv = holder.cardView;
        TextView title = cv.findViewById(R.id.promo_code_card_title);
        TextView description = cv.findViewById(R.id.promo_code_card_desc);

        title.setText(codes[i].getTitle());
        description.setText(codes[i].getDescription());

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(codes[i]);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return codes.length;
    }
}
