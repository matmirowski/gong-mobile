package com.example.gongmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gongmobile.api.model.ListedCoupon;
import com.example.gongmobile.api.service.GongClient;
import com.example.gongmobile.api.service.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PromoCodesActivity extends AppCompatActivity {
    public static String EXTRA_BRANCH_ID = "ExtraBranchId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_promo_codes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setStatusBarColor(this.getColor(R.color.gong_blue_light));

        ImageButton backButton = findViewById(R.id.promo_codes_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        Intent i = getIntent();
        int branchId = i.getIntExtra(EXTRA_BRANCH_ID, -1);

        Retrofit retrofit = RetrofitClient.getInstance();
        GongClient client = retrofit.create(GongClient.class);
        Call<List<ListedCoupon>> call = client.listCoupons(branchId);

        call.enqueue(new Callback<List<ListedCoupon>>() {
            @Override
            public void onResponse(Call<List<ListedCoupon>> call, Response<List<ListedCoupon>> response) {
                List<ListedCoupon> promoCodes = response.body();
                PromoCodeListAdapter adapter = new PromoCodeListAdapter(promoCodes.toArray(new ListedCoupon[0]));
                adapter.setListener(((code) -> {
                    Intent intent = new Intent(PromoCodesActivity.this, PromoCodeDetailsActivity.class);
                    intent.putExtra(PromoCodeDetailsActivity.EXTRA_BRANCH_ID, branchId);
                    intent.putExtra(PromoCodeDetailsActivity.EXTRA_CODE_ID, code.getId());
                    intent.putExtra(PromoCodeDetailsActivity.EXTRA_CODE_TITLE, code.getTitle());
                    intent.putExtra(PromoCodeDetailsActivity.EXTRA_CODE_DESCRIPTION, code.getDescription());
                    startActivity(intent);
                }));

                RecyclerView recyclerView = findViewById(R.id.codes_recycler);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(PromoCodesActivity.this));
            }

            @Override
            public void onFailure(Call<List<ListedCoupon>> call, Throwable t) {
                Toast.makeText(PromoCodesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}