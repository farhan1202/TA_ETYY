package com.dev.ta_etyy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.ta_etyy.DetailCutiActivity;
import com.dev.ta_etyy.R;
import com.dev.ta_etyy.model.ListCuti;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterListCuti extends RecyclerView.Adapter<AdapterListCuti.ViewHolder> {

    private Context context;
    private List<ListCuti.ContentBean> contentBeans;

    public AdapterListCuti(Context context, List<ListCuti.ContentBean> contentBeans) {
        this.context = context;
        this.contentBeans = contentBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_list_cuti, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rowNum.setText((position + 1) + "");
        holder.rowNpk.setText(contentBeans.get(position).getNpk());
        holder.rowDate.setText(contentBeans.get(position).getTgl_cuti());
        holder.rowKeterangan.setText(contentBeans.get(position).getKeterangan());
        holder.rowPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), DetailCutiActivity.class);
                intent.putExtra("extra_npk", contentBeans.get(position).getNpk());
                intent.putExtra("extra_id", contentBeans.get(position).getId_pengajuan());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (contentBeans != null) ? contentBeans.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rowNpk)
        TextView rowNpk;
        @BindView(R.id.rowDate)
        TextView rowDate;
        @BindView(R.id.rowKeterangan)
        TextView rowKeterangan;
        @BindView(R.id.rowNum)
        TextView rowNum;
        @BindView(R.id.rowPush)
        LinearLayout rowPush;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
