package com.example.gongmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gongmobile.api.model.Address;
import com.example.gongmobile.api.model.BranchDetails;
import com.example.gongmobile.api.model.PromoCode;
import com.example.gongmobile.api.service.GongClient;
import com.example.gongmobile.api.service.RetrofitClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PromoCodeDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_BRANCH_ID = "ExtraBranchId";
    public static final String EXTRA_CODE_ID = "ExtraCodeId";
    public static final String EXTRA_CODE_TITLE = "ExtraCodeTitle";
    public static final String EXTRA_CODE_DESCRIPTION = "ExtraCodeDescription";
    private final int POLISH_TIME_ZONE_OFFSET_IN_HOURS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_promo_code_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setStatusBarColor(this.getColor(R.color.gong_blue_light));

        ImageButton backButton = findViewById(R.id.promo_code_details_back_button);
        ImageButton activateButton = findViewById(R.id.promo_code_details_activate);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        Intent i = getIntent();
        int codeId = i.getIntExtra(EXTRA_CODE_ID, -1);
        int branchId = i.getIntExtra(EXTRA_BRANCH_ID, -1);
        String codeTitle = i.getStringExtra(EXTRA_CODE_TITLE);
        String codeDescription = i.getStringExtra(EXTRA_CODE_DESCRIPTION);

        TextView titleText = findViewById(R.id.promo_code_details_title);
        TextView descriptionText = findViewById(R.id.promo_code_details_desc);
        TextView activateText = findViewById(R.id.promo_code_details_activate_text);
        TextView expirationText = findViewById(R.id.promo_code_details_expiration_time);

        titleText.setText(codeTitle);
        descriptionText.setText(codeDescription);

        Retrofit retrofit = RetrofitClient.getInstance();
        GongClient client = retrofit.create(GongClient.class);

        activateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<PromoCode> call = client.generatePromoCode(branchId, codeId);

                call.enqueue(new Callback<PromoCode>() {
                    @Override
                    public void onResponse(Call<PromoCode> call, Response<PromoCode> response) {
                        PromoCode code = response.body();
                        activateText.setText(code.getCode());
                        LocalDateTime dateTime = LocalDateTime.parse(code.getExpiresAt(), DateTimeFormatter.ISO_DATE_TIME);
                        LocalDateTime polishDateTime = dateTime.plusHours(POLISH_TIME_ZONE_OFFSET_IN_HOURS);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        String formattedDate = polishDateTime.format(formatter);
                        expirationText.setText("WAŻNOŚĆ " + formattedDate);
                        activateButton.setClickable(false);
                    }

                    @Override
                    public void onFailure(Call<PromoCode> call, Throwable t) {
                        Toast.makeText(PromoCodeDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}