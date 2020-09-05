package com.dev.ta_etyy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.ta_etyy.adapter.AdapterListCuti;
import com.dev.ta_etyy.model.ListCuti;
import com.dev.ta_etyy.utils.LoadingDialog;
import com.dev.ta_etyy.utils.PrefManager;
import com.dev.ta_etyy.utils.apihelper.ApiInterface;
import com.dev.ta_etyy.utils.apihelper.UtilsApi;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private boolean doubleBack;
    private Toast backToast;

    LoadingDialog loadingDialog;
    ApiInterface apiInterface;
    PrefManager prefManager;

    @BindView(R.id.btnLogout)
    ImageView btnLogout;
    @BindView(R.id.homeUserName)
    TextView homeUserName;
    @BindView(R.id.homeUserNPK)
    TextView homeUserNPK;
    @BindView(R.id.btnCuti)
    Button btnCuti;
    @BindView(R.id.recyclerCuti)
    RecyclerView recyclerCuti;

    List<ListCuti.ContentBean> contentBeans;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        prefManager = new PrefManager(context);
        apiInterface = UtilsApi.getApiService();
        loadingDialog = new LoadingDialog(context);

        homeUserName.setText(prefManager.getNama());
        homeUserNPK.setText(prefManager.getId());

        getDataList();

        btnCuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormPengajuanCutiActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(view.getContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                        prefManager.removeSession();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    private void getDataList() {
        loadingDialog.startLoadingDialog();
        apiInterface.getListCuti().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        loadingDialog.dismissLoadingDialog();
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("responseCode").equals("0000")){
                            JSONArray jsonArray = jsonObject.getJSONArray("content");

                            contentBeans = new ArrayList<>();
                            Gson gson = new Gson();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                ListCuti.ContentBean contentBean = gson.fromJson(jsonArray.getJSONObject(i).toString(), ListCuti.ContentBean.class);
                                contentBeans.add(contentBean);
                            }

                            AdapterListCuti adapterListCuti = new AdapterListCuti(context, contentBeans);
                            recyclerCuti.setLayoutManager(new LinearLayoutManager(context));
                            recyclerCuti.setAdapter(adapterListCuti);
                        }else{
                            Toast.makeText(context, ""+jsonObject.getString("responseMessage"), Toast.LENGTH_SHORT).show();
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
                Log.i("TAG", "onFailure: " + t.getMessage());
                loadingDialog.dismissLoadingDialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBack) {
            backToast.cancel();
            super.onBackPressed();
            moveTaskToBack(true);
        } else {
            backToast = Toast.makeText(this, "Press back againt to exit ", Toast.LENGTH_SHORT);
            backToast.show();
            doubleBack = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBack = false;
                }
            }, 2000);
        }

    }
}