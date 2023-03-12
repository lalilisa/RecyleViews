package com.example.th1.apdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th1.R;
import com.example.th1.custom.ClickSingleItem;
import com.example.th1.model.Job;
import java.text.SimpleDateFormat;
import java.util.List;


public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobHolder> {

    ClickSingleItem clickSingleItem;
    private final Context context;
    private final List<Job> jobs;

    public JobAdapter(Context context, List<Job> jobs) {
        this.context = context;
        this.jobs = jobs;
    }

    @NonNull
    @Override
    public JobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.item,parent,false);
        return new JobHolder(view);
    }

    public ClickSingleItem getClickSingleItem() {
        return clickSingleItem;
    }

    public void setClickSingleItem(ClickSingleItem clickSingleItem) {
        this.clickSingleItem = clickSingleItem;
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull JobHolder holder, int position) {
            Job job=jobs.get(position);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            holder.content.setText(job.getName()+"-"+simpleDateFormat.format(job.getDate()));
            int imageId=getMipmapResIdByName(job.getFors());
            holder.imageView.setImageResource(imageId);
            holder.delete.setOnClickListener(v -> {
                jobs.remove(job);
                notifyDataSetChanged();
            });
            holder.constraintLayout.setOnClickListener(v -> {
                clickSingleItem.onClickSingleItem(position);
            });

    }


    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public static class JobHolder extends RecyclerView.ViewHolder {
        Button delete;
        ImageView imageView;
        TextView content;
        ConstraintLayout constraintLayout;
        public JobHolder(@NonNull View itemView) {
            super(itemView);
            delete=itemView.findViewById(R.id.btn_delete);
            imageView=itemView.findViewById(R.id.image_gender);
            content=itemView.findViewById(R.id.content);
            constraintLayout=itemView.findViewById(R.id.constraints);
        }

    }
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        @SuppressLint("DiscouragedApi") int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }
}
