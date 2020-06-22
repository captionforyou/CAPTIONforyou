package com.example.captionforu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Study extends Fragment {
    View rootview;
    EditText studytext;
    Button studybutton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview= inflater.inflate(R.layout.fragment_study, container, false);

        studytext =(EditText)rootview.findViewById(R.id.studyeditText);
        studybutton= (Button) rootview.findViewById(R.id.studybutton);

        studybutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                downloadcaption(studytext.getText().toString());
            }
        });;


        return rootview;
    }

    public void downloadcaption(String ID){

        String URL = "https://video.google.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService server = retrofit.create(ApiService.class);


        Call<ResponseBody> call =  server.get_caption("ko",ID);
        call.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try
                {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
                    int pageCount = 1;

                    PdfDocument myPdfDocument = new PdfDocument();
                    PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(300,600,pageCount).create();
                    PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);


                    Paint myPaint = new Paint();
                    int x= 10,y = 25;

                    String wholeCaption = response.body().string();
                    for(int i = 0; i<2; i++) {
                        wholeCaption = wholeCaption.substring(wholeCaption.indexOf(">") + 1);
                    }

                    String[] array = wholeCaption.split("</text>");
                    String timestamp = null;

                    for(int i=0;i<array.length;i++) {


                        String[] subarray = array[i].split(">");

                        if(subarray[0].equalsIgnoreCase("</transcript"))break;
                        else{
                            String[] timearray = subarray[0].split("\"");

                            float starttime = Float.parseFloat(timearray[1]);
                            // float durationtime = Float.parseFloat(timearray[3]);
                            // float endtime = starttime+durationtime;

                            starttime = (float) (Math.round(starttime*100)/100.0);
                            // endtime = (float) (Math.round(endtime*100)/100.0);

                            int min = (int) (starttime/60);
                            int hour = min/60;
                            min = min % 60;
                            int sec = (int) (starttime%60);

                            if(hour!=0) {
                                timestamp = String.format("%02d",hour) + ":" + String.format("%02d",min) + ":"
                                        + String.format("%02d",sec);
                            }
                            else if(hour==0){
                                timestamp = String.format("%02d",min) + ":"
                                        + String.format("%02d",sec);
                            }
                        }
                        String outputCaption = timestamp + "  "+ subarray[1];

                        myPage.getCanvas().drawText(outputCaption,x,y,myPaint);
                        y+=myPaint.descent() - myPaint.ascent();
                        System.out.println(y);

                        if(y==487){
                            myPdfDocument.finishPage(myPage);
                            myPageInfo = new PdfDocument.PageInfo.Builder(300,500,++pageCount).create();
                            myPage = myPdfDocument.startPage(myPageInfo);
                            y=25;
                        }
                    }

                    Log.e("succ",response.body().string());

                    myPdfDocument.finishPage(myPage);

                    String myFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/captionforyouFile.pdf";
                    File myFile = new File(myFilePath);

                    try{
                        myPdfDocument.writeTo(new FileOutputStream(myFile));
                        Toast.makeText(getActivity(), "성공적으로 자막을 다운로드했습니다.", Toast.LENGTH_SHORT).show();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    //이거 닫을때 에러생김 왜지이
                    myPdfDocument.close();
                }
                catch (Exception e) {
                    Log.e("captionfail","db 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });

    }
}
