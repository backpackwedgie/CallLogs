package com.leteminlockandkey.calllogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText job;
    EditText year;
    EditText make;
    EditText model;
    EditText comments;
    EditText phonenumber;
    EditText address;
    EditText requirements;
    EditText etastart;
    EditText etastarttime;
//    EditText etaend;
    Spinner status;
    EditText quote;
    EditText referred;
    Button button;
    String TempName;
    String TempJob;
    String TempYear;
    String TempMake;
    String TempModel;
    String TempComments;
    String TempPhonenumber;
    String TempAddress;
    String TempRequirements;
    String TempETAStart;
    String TempETAEnd;
    String TempStatus;
    String TempQuote;
    String TempReferred;
    ScrollView scrollerv;
    String TempDateCreated;
    String TempCreatedBy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollerv = (ScrollView)findViewById(R.id.scrollview);
        name = (EditText) findViewById(R.id.editTextName);
        job = (EditText) findViewById(R.id.editTextJob);
        year = (EditText) findViewById(R.id.editTextYear);
        make = (EditText) findViewById(R.id.editTextMake);
        model = (EditText) findViewById(R.id.editTextModel);
        comments = (EditText) findViewById(R.id.editTextComments);
        phonenumber = (EditText) findViewById(R.id.editTextPhonenumber);
        address = (EditText) findViewById(R.id.editTextAddress);
        requirements = (EditText) findViewById(R.id.editTextRequirements);
        etastart = (EditText) findViewById(R.id.editTextETAStart);
//        try {
//            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
//            Date date = inputFormat.parse(detailsOfSelectedJob.getETAStart());
//            String ETAOutput = dateFormat.format(date);
//            etastart.setText (ETAOutput);
//        }
//        catch (ParseException e) {}
        etastart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(com.leteminlockandkey.calllogs.MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        String selectedmonthtext = " ";
                        String selecteddaytext = " ";
                        String timeplaceholder = " ";
                        if (selectedmonth < 10){
                            selectedmonthtext = "0"+Integer.toString(selectedmonth);
                        } else selectedmonthtext = Integer.toString(selectedmonth);
                        if (selectedday < 10){
                            selecteddaytext = "0"+Integer.toString(selectedday);
                        } else selecteddaytext = Integer.toString(selectedday);
                        etastart.setText(selectedyear+"-"+selectedmonthtext+"-"+selecteddaytext+timeplaceholder);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
        etastarttime = (EditText) findViewById(R.id.editTextETAStartTime);
//        try {
//            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//            Date date = inputFormat.parse(detailsOfSelectedJob.getETAStart());
//            String ETAOutput = dateFormat.format(date);
//            etastarttime.setText (ETAOutput);
//        }
//        catch (ParseException e) {}
        etastarttime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mHour = mcurrentDate.get(Calendar.HOUR_OF_DAY);
                int mMinute = mcurrentDate.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(com.leteminlockandkey.calllogs.MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker timepicker, int selectedhour, int selectedminute) {
                        //boolean is24hour = true;
                        etastarttime.setText(selectedhour + ":" + selectedminute + ":00");
                    }
                }, mHour, mMinute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }});
    /*        etaend = (EditText) findViewById(R.id.editTextETAEnd);
            etaend.setText(detailsOfSelectedJob.getETAEnd());
            etaend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(com.leteminlockandkey.jobslistapp2.EditItem.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                /*      Your code   to get date and time    *//*
                        selectedmonth = selectedmonth + 1;
                        String selectedmonthtext = " ";
                        String selecteddaytext = " ";
                        String timeplaceholder = " 00:00:00";
                        if (selectedmonth < 10){
                            selectedmonthtext = "0"+Integer.toString(selectedmonth);
                        } else selectedmonthtext = Integer.toString(selectedmonth);
                        if (selectedday < 10){
                            selecteddaytext = "0"+Integer.toString(selectedday);
                        } else selecteddaytext = Integer.toString(selectedday);
                        etaend.setText(selectedyear+"-"+selectedmonthtext+"-"+selecteddaytext+timeplaceholder);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });*/
        status = (Spinner) findViewById(R.id.spinnerStatus);
        status.getOnItemSelectedListener();
        List<String> statuses = new ArrayList<String>();
        statuses.add("Choose a Status");
        statuses.add("Work Order");
        statuses.add("Quote");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statuses);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(dataAdapter);
        quote = (EditText) findViewById(R.id.editTextQuote);
        referred = (EditText) findViewById(R.id.editTextReferred);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.getSelectedItem().toString().equals("Choose a Status")){
                    Toast.makeText(getApplicationContext(), "Chose a Status", Toast.LENGTH_LONG).show();
                }else {
                    GetData();
                    InsertData(TempName, TempJob, TempYear, TempMake, TempModel, TempComments,
                            TempPhonenumber, TempAddress, TempRequirements, TempETAStart,
                            TempStatus, TempQuote, TempReferred);
                    name.setText(null);
                    job.setText(null);
                    year.setText(null);
                    make.setText(null);
                    model.setText(null);
                    comments.setText(null);
                    phonenumber.setText(null);
                    address.setText(null);
                    requirements.setText(null);
                    etastart.setText(null);
//                    etaend.setText(null);
                    status.setSelection(0);
                    quote.setText(null);
                    referred.setText(null);
                }
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, CallList.class);
                startActivity(myIntent);
            }
        });
    }

    public void GetData(){
        TempName = name.getText().toString();
        TempJob = job.getText().toString();
        TempYear = year.getText().toString();
        TempMake = make.getText().toString();
        TempModel = model.getText().toString();
        TempComments = comments.getText().toString();
        TempPhonenumber = phonenumber.getText().toString();
        TempAddress = address.getText().toString();
        TempRequirements = requirements.getText().toString();
        TempETAStart = etastart.getText().toString()+etastarttime.getText().toString();
  //      TempETAEnd = etaend.getText().toString();
        TempStatus = status.getSelectedItem().toString();
        TempQuote = quote.getText().toString();
        TempReferred = referred.getText().toString();
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
           // startETAtext = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
           // etastart
        }
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(this.getFragmentManager(), "datePicker");
    }
    public void ResetPage(){
        name.setText("");
        job.setText("");
        year.setText("");
        make.setText("");
        model.setText("");
        comments.setText("");
        phonenumber.setText("");
        address.setText("");
        requirements.setText("");
        etastart.setText("");
        etastarttime.setText("");
        status.setSelection(0);
        quote.setText("");
        referred.setText("");
        scrollerv.smoothScrollTo(0, 0);
    }
    public void InsertData(final String name, final String job, final String year, final String make,
                           final String model, final String comments, final String phonenumber,
                           final String address, final String requirements, final String etastart,
                           final String status, final String quote, final String referred){
        class SendPost extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params){
                String nameHolder = name;
                String jobHolder = job;
                String yearHolder = year;
                String makeHolder = make;
                String modelHolder = model;
                String commentsHolder = comments;
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH)+1;
                String mMonthtext;
                if (mMonth < 10){
                    mMonthtext = "0"+Integer.toString(mMonth);
                }else mMonthtext = Integer.toString(mMonth);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
                String mDaytext;
                if (mDay < 10){
                    mDaytext = "0"+Integer.toString(mDay);
                }else mDaytext = Integer.toString(mDay);
                int mHour = mcurrentDate.get(Calendar.HOUR_OF_DAY);
                String mHourtext;
                if (mHour < 10){
                    mHourtext = "0"+Integer.toString(mHour);
                }else mHourtext = Integer.toString(mHour);
                int mMinute = mcurrentDate.get(Calendar.MINUTE);
                String mMinutetext;
                if (mMinute < 10){
                    mMinutetext = "0"+Integer.toString(mMinute);
                }else mMinutetext = Integer.toString(mMinute);
                int mSecond = mcurrentDate.get(Calendar.SECOND);
                String mSecondtext;
                if (mSecond < 10){
                    mSecondtext = "0"+Integer.toString(mSecond);
                }else mSecondtext = Integer.toString(mSecond);
                String datetimeHolder = mYear+"-"+mMonthtext+"-"+mDaytext+" "+mHourtext+":"+mMinutetext+":"+mSecondtext;
                String phonenumberHolder = phonenumber;
                String addressHolder = address;
                String requirementsHolder = requirements;
                String etastartHolder = etastart;
//                String etaendHolder = etaend;
  //              String starttimemilHolder = starttimemil;
    //            String endtimemilHolder = endtimemil;
                String statusHolder = status;
                String quoteHolder = quote;
                String referredHolder = referred;
                String datecreated = datetimeHolder;
                String createdby = "Twist Cell";
//                String text = "";
                try {
                    URL url = new URL ("http://www.leteminlockandkey.com/AppsFolder/insert.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    OutputStream os = con.getOutputStream();
                    String data = URLEncoder.encode("Name","UTF-8")+"="+URLEncoder.encode(nameHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Job", "UTF-8")+"="+URLEncoder.encode(jobHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Year","UTF-8")+"="+URLEncoder.encode(yearHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Make","UTF-8")+"="+URLEncoder.encode(makeHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Model","UTF-8")+"="+URLEncoder.encode(modelHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Comments","UTF-8")+"="+URLEncoder.encode(commentsHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Datetime","UTF-8")+"="+URLEncoder.encode(datetimeHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Phonenumber","UTF-8")+"="+URLEncoder.encode(phonenumberHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Address","UTF-8")+"="+URLEncoder.encode(addressHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Requirements","UTF-8")+"="+URLEncoder.encode(requirementsHolder,"UTF-8")+"&"+
                            URLEncoder.encode("ETAStart","UTF-8")+"="+URLEncoder.encode(etastartHolder,"UTF-8")+"&"+
//                            URLEncoder.encode("ETAEnd","UTF-8")+"="+URLEncoder.encode(etaendHolder,"UTF-8")+"&"+
                //            URLEncoder.encode("Starttimemil","UTF-8")+"="+URLEncoder.encode(starttimemilHolder,"UTF-8")+"&"+
                  //          URLEncoder.encode("Endtimemeil","UTF-8")+"="+URLEncoder.encode(endtimemilHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Status","UTF-8")+"="+URLEncoder.encode(statusHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Quote","UTF-8")+"="+URLEncoder.encode(quoteHolder,"UTF-8")+"&"+
                            URLEncoder.encode("Referred","UTF-8")+"="+URLEncoder.encode(referredHolder,"UTF-8")+"&"+
                            URLEncoder.encode("DateCreated","UTF-8")+"="+URLEncoder.encode(datecreated,"UTF-8")+"&"+
                            URLEncoder.encode("CreatedBy","UTF-8")+"="+URLEncoder.encode(createdby,"UTF-8");
                    BufferedWriter bW=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                    bW.write(data);
                    bW.flush();
                    int statusCode = con.getResponseCode();
                    if (statusCode == 200) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
 //                       Log.d("degub",data);
                        while ((line = reader.readLine()) != null)
                            sb.append(line).append("\n");
                        String text = "";
                        text = sb.toString();
                        bW.close();
                    }

                }catch (Exception e){Log.d("ok","error");}
                return "Success";
            }
            protected void onPostExecute(String result){
                super.onPostExecute(result);
                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
                ResetPage();
            }
        }
        SendPost sendPost = new SendPost();
        sendPost.execute(name);
    }
}