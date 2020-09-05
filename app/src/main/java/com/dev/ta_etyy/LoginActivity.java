package com.dev.ta_etyy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.editNpk)
    EditText editNpk;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    ApiInterface apiInterface;
    PrefManager prefManager;
    Context context;
    LoadingDialog loadingDialog;
    @BindView(R.id.editPass)
    EditText editPass;

    private boolean doubleBack;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        apiInterface = UtilsApi.getApiService();
        context = this;
        prefManager = new PrefManager(context);
        loadingDialog = new LoadingDialog(context);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editNpk.getText().toString().isEmpty()) {
                    editNpk.setError("Field Cant Blank");
                } else if (editPass.getText().toString().isEmpty()) {
                    editPass.setError("Field Cant Blank");
                } else {
                    fetchLogin();
                }
            }
        });
    }

    private void fetchLogin() {
        loadingDialog.startLoadingDialog();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("npk", editNpk.getText().toString());
        jsonObject.addProperty("pass", editPass.getText().toString());

        apiInterface.login(jsonObject).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        loadingDialog.dismissLoadingDialog();
                        JSONObject jsonObject1 = new JSONObject(response.body().string());
                        if (jsonObject1.getString("responseCode").equals("0000")) {
                            Toast.makeText(context, "" + jsonObject1.getString("responseMessage"), Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("content");
                            prefManager.spInt(PrefManager.SP_ID, jsonObject2.getString("npk"));
                            prefManager.spNama(PrefManager.SP_NAMA, jsonObject2.getString("nama"));
                            prefManager.saveSession();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(context, "" + jsonObject1.getString("responseMessage"), Toast.LENGTH_SHORT).show();
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
                Log.i("TAG", "onFailure: " + t.getStackTrace());
                loadingDialog.dismissLoadingDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        PrefManager prefManager = new PrefManager(this);
        if (prefManager.getSession()){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
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