package com.example.gongmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gongmobile.api.model.ListedBranch;
import com.example.gongmobile.api.model.ListedCoupon;
import com.example.gongmobile.api.service.GongClient;
import com.example.gongmobile.api.service.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setStatusBarColor(this.getColor(R.color.gong_blue_light));

        Retrofit retrofit = RetrofitClient.getInstance();
        GongClient client = retrofit.create(GongClient.class);
        Call<List<ListedBranch>> call = client.listBranches("");

        Spinner categorySpinner = findViewById(R.id.category_spinner);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, R.layout.spinner_item, getResources().getStringArray(R.array.categories_array)) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.spinner_dropdown_item, parent, false);
                }
                TextView item = (TextView) convertView;
                item.setText(getItem(position));
                return convertView;
            }
        };
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Call<List<ListedBranch>> call = client.listBranches(String.valueOf(position));
                call.enqueue(new Callback<List<ListedBranch>>() {
                    @Override
                    public void onResponse(Call<List<ListedBranch>> call, Response<List<ListedBranch>> response) {
                        List<ListedBranch> branches = response.body();
                        BranchListAdapter adapter = new BranchListAdapter(branches.toArray(new ListedBranch[0]));
                        adapter.setListener(((branchId) -> {
                            Intent intent = new Intent(MainActivity.this, BranchDetailsActivity.class);
                            intent.putExtra(BranchDetailsActivity.EXTRA_BRANCH_ID, branchId);
                            startActivity(intent);
                        }));

                        RecyclerView recyclerView = findViewById(R.id.branch_recycler);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    }

                    @Override
                    public void onFailure(Call<List<ListedBranch>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        this.fetchBranches(call);
    }

    private void fetchBranches(Call<List<ListedBranch>> call) {
        call.enqueue(new Callback<List<ListedBranch>>() {
            @Override
            public void onResponse(Call<List<ListedBranch>> call, Response<List<ListedBranch>> response) {
                List<ListedBranch> branches = response.body();
                BranchListAdapter adapter = new BranchListAdapter(branches.toArray(new ListedBranch[0]));
                adapter.setListener(((branchId) -> {
                    Intent intent = new Intent(MainActivity.this, BranchDetailsActivity.class);
                    intent.putExtra(BranchDetailsActivity.EXTRA_BRANCH_ID, branchId);
                    startActivity(intent);
                }));

                RecyclerView recyclerView = findViewById(R.id.branch_recycler);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onFailure(Call<List<ListedBranch>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}