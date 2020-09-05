package com.dev.ta_etyy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dev.ta_etyy.utils.LoadingDialog;
import com.dev.ta_etyy.utils.PrefManager;
import com.dev.ta_etyy.utils.apihelper.ApiInterface;
import com.dev.ta_etyy.utils.apihelper.UtilsApi;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCutiActivity extends AppCompatActivity {

    @BindView(R.id.toolbarCuti)
    Toolbar toolbarCuti;

    ApiInterface apiInterface;
    Context context;
    LoadingDialog loadingDialog;
    PrefManager prefManager;
    @BindView(R.id.detailID)
    TextView detailID;
    @BindView(R.id.detailNpk)
    TextView detailNpk;
    @BindView(R.id.detailNama)
    TextView detailNama;
    @BindView(R.id.detailTglCuti)
    TextView detailTglCuti;
    @BindView(R.id.detailTglMasuk)
    TextView detailTglMasuk;
    @BindView(R.id.detailKeterangan)
    TextView detailKeterangan;
    @BindView(R.id.detailLama)
    TextView detailLama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cuti);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarCuti);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail");

        context = this;
        apiInterface = UtilsApi.getApiService();
        prefManager = new PrefManager(context);
        loadingDialog = new LoadingDialog(context);

        fetchDataDetail();

    }

    private void fetchDataDetail() {
        Intent intent = getIntent();
        detailNpk.setText(intent.getStringExtra("extra_npk"));
        detailID.setText(intent.getStringExtra("extra_id"));
        loadingDialog.startLoadingDialog();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("npk", intent.getStringExtra("extra_npk"));
        jsonObject.addProperty("id_pengajuan", intent.getStringExtra("extra_id"));
        apiInterface.getDetailCuti(jsonObject).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        loadingDialog.dismissLoadingDialog();
                        JSONObject jsonObject1 = new JSONObject(response.body().string());
                        if (jsonObject1.getString("responseCode").equals("0000")) {
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("content");
                            detailNama.setText(jsonObject2.getString("name"));
                            detailKeterangan.setText(jsonObject2.getString("reason"));
                            detailLama.setText(jsonObject2.getString("totalDays") + " Hari");
                            detailTglCuti.setText(jsonObject2.getString("dateOfPaidLeave"));
                            detailTglMasuk.setText(jsonObject2.getString("dateOfEntry"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Something when wrong", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissLoadingDialog();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}