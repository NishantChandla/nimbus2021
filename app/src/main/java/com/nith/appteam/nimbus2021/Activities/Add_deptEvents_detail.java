package com.nith.appteam.nimbus2021.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.nith.appteam.nimbus2021.Models.departmentEvent;
import com.nith.appteam.nimbus2021.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class Add_deptEvents_detail extends AppCompatActivity {
    private departmentEvent dept;
    private TextView nameDetEventsD, infoDetEventsD, venueDetEventsD, dateDetEventsD;
    private CardView regDetEventsD, absEventD;
    private ImageView imgDetEventsD;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private TextView abstractDet;
    private WebView webView;
    private String myPdfUrl;
    private ProgressDialog pd;
//    private ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dept_events_detail);

        webView = findViewById(R.id.webView);
//        progressBar = findViewById(R.id.progressBarAbstract);
//        progressBar.setMax(100);

        ImageView round_big = findViewById(R.id.e_n);
        ImageView round_small = findViewById(R.id.e_k);
        imgDetEventsD = findViewById(R.id.EventDImageIDDetail);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fast_anim_v1);
        Random rand = new Random();
        animation.setDuration(rand.nextInt(2000) + 2000);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slow_anim_v1);
        animation1.setDuration(rand.nextInt(2000) + 2000);
        round_big.startAnimation(animation1);
        round_small.startAnimation(animation);
        pd = new ProgressDialog(Add_deptEvents_detail.this);
        pd.setMessage("Please wait...");
        pd.setCanceledOnTouchOutside(false);


        TextView back;
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });

        dept = (departmentEvent) getIntent().getSerializableExtra("departmentEvents");
        setUpUI();


        myPdfUrl = dept.getAbstractDEVE();

        Log.e("before", dept.getAbstractDEVE());

//        if (myPdfUrl != null && !myPdfUrl.isEmpty()) {
//            String substr = myPdfUrl.substring(myPdfUrl.length() - 4);
//            if (!substr.equals("view")) myPdfUrl = myPdfUrl + "/view";
//
//            Log.e("after", myPdfUrl);
//        }

        CardView abstractButton = findViewById(R.id.abstractIDDetEventsD);
        abstractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();

                try {
                    webView.setVisibility(View.VISIBLE);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            super.onPageStarted(view, url, favicon);
//                            progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view,
                                WebResourceRequest request) {
                            view.loadUrl(myPdfUrl);

                            if (!pd.isShowing()) {
                                pd.show();
                            }

                            return true;
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);
                            if (pd.isShowing()) {
                                pd.dismiss();
                            }
//                            progressBar.setVisibility(View.GONE);
                        }
                    });
                    webView.clearCache(true);
                    webView.clearHistory();
                    webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" +myPdfUrl);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        // abstractTV.setText(dept.getAbstractDEVE());
        getMovDetails();
        regDetEventsD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oprnURLExh(dept.getRegURLDEVE());
            }
        });
//        absEventD.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openDialogBox();
//            }
//        });


    }

//    private void openDialogBox() {
//        alertDialogBuilder = new AlertDialog.Builder(this);
//        View view = getLayoutInflater().inflate(R.layout.abstract_dialog_box, null);
//        abstractDet = view.findViewById(R.id.abstract_dialogText);
//        abstractDet.setText(dept.getAbstractDEVE());
//        Button submit = view.findViewById(R.id.CloseButton);
//        alertDialogBuilder.setView(view);
//        dialog = alertDialogBuilder.create();
//        dialog.show();
//
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "exit", Toast.LENGTH_SHORT).show();
//
//                dialog.dismiss();
//            }
//        });
//    }


    private void oprnURLExh(String regURL) {
        Intent intent = new Intent(Add_deptEvents_detail.this, Web.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("url", regURL);
        getApplicationContext().startActivity(intent);

    }


    private void getMovDetails() {
        if (dept != null) {
            nameDetEventsD.setText(dept.getNameDEVE());
            infoDetEventsD.setText(dept.getInfoDEVE());
            venueDetEventsD.setText(dept.getVenueDEVE());
            dateDetEventsD.setText(dept.getDateDEVE());
            //  typeWo.setText(workshopModel.getTypeWor());
            Log.d("TAG", "getMovDetails: "+dept.getImageDEVE());
             Picasso.with(getApplicationContext()).load(dept.getImageDEVE().replace("http://", "https://"))
                     .into(imgDetEventsD, new Callback() {
                         @Override
                         public void onSuccess() {

                         }

                         @Override
                         public void onError() {
                            imgDetEventsD.setImageResource(R.drawable.nimbus_logo);
                         }
                     });
        }


    }

    private void setUpUI() {

        nameDetEventsD = findViewById(R.id.NameIDDetEventsD);
        infoDetEventsD = findViewById(R.id.InfoIDDetEventsD);
        venueDetEventsD = findViewById(R.id.VenueIDDetEventsD);
        dateDetEventsD = findViewById(R.id.DateDetEventsD);
        regDetEventsD = findViewById(R.id.registerDetEventsD);
        //absEventD = findViewById(R.id.abstractDEventsDet);
        // imgDetEventsD = findViewById(R.id.ImgDetEventsD);

        //tupeWor=findViewById(R.id.workshopTypeIDDet);
    }

    @Override
    public void onBackPressed() {
        webView.setVisibility(View.GONE);
        super.onBackPressed();
    }
}
