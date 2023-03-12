package com.example.th1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.th1.apdapter.JobAdapter;
import com.example.th1.common.Constant;
import com.example.th1.custom.ClickSingleItem;
import com.example.th1.model.Job;
import com.example.th1.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    RecyclerView view=null;
    JobAdapter jobAdapter=null;
    List<Job> jobs=data();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view=findViewById(R.id.recycle);
        jobAdapter=new JobAdapter(this,jobs);
        jobAdapter.setClickSingleItem(position -> {
            Job job=jobs.get(position);
            EditText name=findViewById(R.id.name);
            name.setText(job.getName());
            EditText des=findViewById(R.id.mo_ta);
            des.setText(job.getDes());

            RadioButton male=findViewById(R.id.radio_male);
            RadioButton female=findViewById(R.id.radio_female);
            if(job.getFors().equals(Constant.MALE))
                male.setChecked(true);
            else
                female.setChecked(true);
            TextView dateWork=findViewById(R.id.date_work);
            SimpleDateFormat simple=new SimpleDateFormat("dd/MM/yyyy");
            dateWork.setText(simple.format(job.getDate()));
            System.out.println(job);
            onUpdateJob(position);
        });
        LinearLayoutManager manager=new LinearLayoutManager(this);
        view.setAdapter(jobAdapter);
        view.setLayoutManager(manager);
        onDatePicker();
        onAddJob();
        search();

    }

    private List<Job> data(){
        List<Job> jobs=new ArrayList<>();
        jobs.add(new Job("Player","ST","male",new Date()));
        jobs.add(new Job("Cooking","bake","female",new Date()));
        jobs.add(new Job("Doctor","header","male",new Date()));
        return  jobs;
    }
    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId", "SimpleDateFormat"})
    private void onAddJob(){
        final String[] gender = {null};
        Button button=findViewById(R.id.btn_add);
        RadioGroup radioGroup=  findViewById(R.id.option);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.radio_male:
                    gender[0] =Constant.MALE;
                    return;
                case R.id.radio_female:
                    gender[0] =Constant.FEMALE;
                    break;
                default:
                    gender[0] =Constant.FEMALE;
                    break;
            }

        });
        button.setOnClickListener(v -> {
            Job job=new Job();
           EditText name= findViewById(R.id.name);
           EditText des= findViewById(R.id.mo_ta);
           job.setName(name.getText().toString());
           job.setDes(des.getText().toString());
           TextView dates= findViewById(R.id.date_work);
            try {
                job.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(dates.getText().toString().toLowerCase()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            job.setFors(gender[0]);
            jobs.add(job);
            System.out.println(jobs);
           jobAdapter.notifyDataSetChanged();
        });
    }

    private void onDatePicker(){
        ImageButton imageButton=findViewById(R.id.calendar);
        TextView textView=findViewById(R.id.date_work);

        final Calendar newCalendar = Calendar.getInstance();

        final DatePickerDialog StartTime = new DatePickerDialog(MainActivity.this, (view1, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            textView.setText(new SimpleDateFormat("dd/MM/yyyy").format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime.show();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private  void onUpdateJob(int position) {
        final String[] gender = {null};
        Button button = findViewById(R.id.btn_update);
        RadioGroup radioGroup = findViewById(R.id.option);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_male:
                    gender[0] = Constant.MALE;
                    return;
                case R.id.radio_female:
                    gender[0] = Constant.FEMALE;
                    break;
                default:
                    gender[0] = Constant.FEMALE;
                    break;
            }

        });
        button.setOnClickListener(v -> {
            Job job=new Job();
            EditText name= findViewById(R.id.name);
            EditText des= findViewById(R.id.mo_ta);
            job.setName(name.getText().toString());
            job.setDes(des.getText().toString());
            TextView dates= findViewById(R.id.date_work);
            try {
                job.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(dates.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            job.setFors(gender[0]);
            jobs.set(position,job);
            System.out.println(jobs);
            jobAdapter.notifyDataSetChanged();
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    private void search(){
        Button button=findViewById(R.id.btn_search);
        button.setOnClickListener(v -> {
            TextView input=findViewById(R.id.input);
            if(input.getText().toString().isEmpty()) {
                view.setAdapter(jobAdapter);
                return;
            }
            List<Job> searchList= Utils.search(Job.class,jobs,input.getText().toString());
            JobAdapter jobAdapterSearch=new JobAdapter(MainActivity.this,searchList);
            view.setAdapter(jobAdapterSearch);
            jobAdapter.notifyDataSetChanged();
        });

    }
}