package com.example.gongmobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gongmobile.api.model.Address;
import com.example.gongmobile.api.model.BranchDetails;
import com.example.gongmobile.api.service.GongClient;
import com.example.gongmobile.api.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BranchDetailsActivity extends AppCompatActivity {
    public static String EXTRA_BRANCH_ID = "ExtraBranchId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_branch_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setStatusBarColor(this.getColor(R.color.gong_blue_light));

        ImageView imageView = findViewById(R.id.branch_details_image);
        TextView titleTextView = findViewById(R.id.branch_details_title);
        TextView subtitleTextView = findViewById(R.id.branch_details_subtitle);
        TextView descriptionTextView = findViewById(R.id.branch_details_desc);
        TextView distanceTextView = findViewById(R.id.branch_details_distance);
        TextView addressTextView = findViewById(R.id.branch_details_location);
        TextView priceRangeTextView = findViewById(R.id.branch_details_discount);
        TextView openingTimeTextView = findViewById(R.id.branch_details_time);
        TextView phoneTextView = findViewById(R.id.branch_details_phone);

        Intent i = getIntent();
        int branchId = i.getIntExtra(EXTRA_BRANCH_ID, -1);

        Retrofit retrofit = RetrofitClient.getInstance();
        GongClient client = retrofit.create(GongClient.class);
        Call<BranchDetails> call = client.fetchBranchDetails(branchId);

        call.enqueue(new Callback<BranchDetails>() {
            @Override
            public void onResponse(Call<BranchDetails> call, Response<BranchDetails> response) {
                BranchDetails branch = response.body();
                titleTextView.setText(branch.getName());
                subtitleTextView.setText(branch.getSlogan());
                descriptionTextView.setText(branch.getDescription());
                phoneTextView.setText(branch.getPhoneNumber());
                openingTimeTextView.setText(branch.getOpeningTime() + " - " + branch.getClosingTime());
                priceRangeTextView.setText(branch.getPriceLow() + " - " + branch.getPriceHigh() + " zł");
                Address address = branch.getAddress();
                String addressString = address.getStreet() + " " + address.getBuildingNumber() + ", " + address.getCity();
                addressTextView.setText(addressString);
                double distanceInKilometers = address.getDistanceFromUniversity() / 1000.0;
                distanceTextView.setText(String.format("%.1f km", distanceInKilometers));

                String base64ImageData = branch.getImage();
                String base64Image = base64ImageData.split(",")[1];
                byte[] decodedString = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);
            }

            @Override
            public void onFailure(Call<BranchDetails> call, Throwable t) {
                Toast.makeText(BranchDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton backButton = findViewById(R.id.branch_details_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });


        ImageButton seeCodesButton = findViewById(R.id.branch_details_see_codes_button);
        seeCodesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PromoCodesActivity.class);
                intent.putExtra(PromoCodesActivity.EXTRA_BRANCH_ID, branchId);
                startActivity(intent);
            }
        });
    }
}