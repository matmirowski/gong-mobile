package com.example.gongmobile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gongmobile.api.model.ListedBranch;

public class BranchListAdapter extends RecyclerView.Adapter<BranchListAdapter.ViewHolder> {
    private final ListedBranch[] branches;

    private Listener listener;

    interface Listener {
        void onClick(int branchId);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public BranchListAdapter(ListedBranch[] branches) {
        this.branches = branches;
    }

    // ViewHolder jest "pojemnikiem" na nasz view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @NonNull
    @Override    // metoda wywolywana zawsze, gdy potrzebny jest nowy ViewHolder (żeby stworzyć nowy View)
    // zwraca ViewHolder z view w środku, w tym przypadku CardView, który jest powiązany z layoutem    // parametrami jest parent viewGroup (recyclerview) oraz int, którego uzywasz jesli chcesz zeby    // wyswietlal sie inny view dla roznych elementow w liscie
    public BranchListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cv = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.branch_card, viewGroup, false);
        return new ViewHolder(cv);
    }

    @Override
    // metoda wywolywana gdy RecyclerView chce wyświetlić dane w ViewHolderze
    // parametrami jest viewholder, do ktorego przypisujemy dane, i pozycja w data set
    public void onBindViewHolder(@NonNull BranchListAdapter.ViewHolder holder, int i) {
        CardView cv = holder.cardView;
        ImageView image = cv.findViewById(R.id.branch_card_image);
        TextView title = cv.findViewById(R.id.branch_card_title);
        TextView distance = cv.findViewById(R.id.branch_card_dist);
        TextView description = cv.findViewById(R.id.branch_card_desc);

        title.setText(branches[i].getName());
        description.setText(branches[i].getSlogan());
        double distanceInKilometers = branches[i].getAddress().getDistanceFromUniversity() / 1000.0;
        distance.setText(String.format("%.1f km", distanceInKilometers));

        String base64ImageData = branches[i].getImage();
        String base64Image = base64ImageData.split(",")[1];
        byte[] decodedString = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        image.setImageBitmap(decodedByte);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(branches[i].getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return branches.length;
    }
}
