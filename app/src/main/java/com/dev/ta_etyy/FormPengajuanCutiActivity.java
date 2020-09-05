package com.dev.ta_etyy;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormPengajuanCutiActivity extends AppCompatActivity {

    @BindView(R.id.toolbarPengajuanCuti)
    Toolbar toolbarPengajuanCuti;
    @BindView(R.id.cutiNpk)
    TextView cutiNpk;
    @BindView(R.id.txtFormDate1)
    EditText txtFormDate1;
    @BindView(R.id.txtFormDate2)
    EditText txtFormDate2;
    @BindView(R.id.cutiLama)
    EditText cutiLama;
    @BindView(R.id.cutiKeterangan)
    EditText cutiKeterangan;
    @BindView(R.id.btnAjukan)
    Button btnAjukan;

    ApiInterface apiInterface;
    Context context;
    LoadingDialog loadingDialog;
    PrefManager prefManager;

    Calendar calendar, calender1;
    DatePickerDialog dialog, dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pengajuan_cuti);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarPengajuanCuti);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Form Pengajuan Cuti");

        context = this;
        apiInterface = UtilsApi.getApiService();
        prefManager = new PrefManager(context);
        loadingDialog = new LoadingDialog(context);

        cutiNpk.setText(prefManager.getId());

        txtFormDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int month = calendar.get(Calendar.MONTH);
                final int year = calendar.get(Calendar.YEAR);

                dialog = new DatePickerDialog(FormPengajuanCutiActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String bulan = "" + i1;
                        String tgl = "" + i2;
                        if (i1 < 10) {
                            bulan = "0" + (i1 + 1);
                        }
                        if (i2 < 10) {
                            tgl = "0" + i2;
                        }
                        txtFormDate1.setText(i + "-" + bulan + "-" + tgl);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        txtFormDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calender1 = Calendar.getInstance();
                final int day = calender1.get(Calendar.DAY_OF_MONTH);
                final int month = calender1.get(Calendar.MONTH);
                final int year = calender1.get(Calendar.YEAR);

                dialog1 = new DatePickerDialog(FormPengajuanCutiActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String bulan = "" + i1;
                        String tgl = "" + i2;
                        if (i1 < 10) {
                            bulan = "0" + (i1 + 1);
                        }
                        if (i2 < 10) {
                            tgl = "0" + i2;
                        }
                        txtFormDate2.setText(i + "-" + bulan + "-" + tgl);
                    }
                }, year, month, day);
                dialog1.show();
            }
        });

        btnAjukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtFormDate1.getText().toString().isEmpty()){
                    txtFormDate1.setError("Field cant be blank");
                }else if (txtFormDate2.getText().toString().isEmpty()){
                    txtFormDate2.setError("Field cant be blank");
                }else if (cutiLama.getText().toString().isEmpty()){
                    cutiLama.setError("Field cant be blank");
                }else if (cutiKeterangan.getText().toString().isEmpty()){
                    cutiKeterangan.setError("Field cant be blank");
                }else{
                    fetchDaftarCuti();
                }
            }
        });
    }

    private void fetchDaftarCuti() {
        loadingDialog.startLoadingDialog();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("npk", prefManager.getId());
        jsonObject.addProperty("dateOfPaidLeave", txtFormDate1.getText().toString());
        jsonObject.addProperty("dateOfEntry", txtFormDate2.getText().toString());
        jsonObject.addProperty("totalDays", cutiLama.getText().toString());
        jsonObject.addProperty("reason", cutiKeterangan.getText().toString());
        apiInterface.daftarCuti(jsonObject).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    loadingDialog.dismissLoadingDialog();
                    try {
                        JSONObject jsonObject1 = new JSONObject(response.body().string());
                            Toast.makeText(context, "" + jsonObject1.getString("responseMessage"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
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