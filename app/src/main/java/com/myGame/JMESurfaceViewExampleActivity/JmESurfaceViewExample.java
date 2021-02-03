package com.myGame.JMESurfaceViewExampleActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myGame.JmEGamePadExample.JmeGame;
import com.myGame.R;
import com.myGame.SystemVisibilityUI;
import com.scrappers.jmeGamePad.MixerKnob;
import com.scrappers.jmesurfaceview.Dialog.OptionPane;
import com.scrappers.jmesurfaceview.JmESurfaceView;

public class JmESurfaceViewExample extends AppCompatActivity {

    private JmESurfaceView jmESurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        final JmeGame jmeGame=new JmeGame(this);
        jmESurfaceView=findViewById(R.id.jmeSurfaceView);
        jmESurfaceView.setIgnoreAssertions(true);
        jmESurfaceView.setEglBitsPerPixel(24);
        jmESurfaceView.setEglAlphaBits(0);
        jmESurfaceView.setEglDepthBits(16);
        jmESurfaceView.setEglSamples(0);
        jmESurfaceView.setEglStencilBits(0);
        jmESurfaceView.setFrameRate(-1);

        jmESurfaceView.setJMEGame(jmeGame,JmESurfaceViewExample.this);
        jmESurfaceView.startRenderer(20);

        MixerKnob mixerKnob=this.findViewById(R.id.mixerKnob);
        mixerKnob.initializeMixerKnob(0,R.drawable.knob_circle);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
         SystemVisibilityUI systemVisibilityUI=new SystemVisibilityUI(JmESurfaceViewExample.this);
         systemVisibilityUI.setGameMode();

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBackPressed() {
       final OptionPane optionPane=new OptionPane(JmESurfaceViewExample.this);
        optionPane.showDialog(com.scrappers.jmesurfaceview.R.layout.dialog_exception, Gravity.CENTER);
        optionPane.getAlertDialog().getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), com.scrappers.jmesurfaceview.R.drawable.dialog_exception_background));
        EditText errorContainer=optionPane.getInflater().findViewById(com.scrappers.jmesurfaceview.R.id.errorText);
        errorContainer.setText("Are You sure ?");
        ((Button)optionPane.getInflater().findViewById(com.scrappers.jmesurfaceview.R.id.closeApp)).setText("yes");
        ((Button)optionPane.getInflater().findViewById(com.scrappers.jmesurfaceview.R.id.ignoreError)).setText("no");
        optionPane.getInflater().findViewById(com.scrappers.jmesurfaceview.R.id.closeApp).setOnClickListener(
            new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         optionPane.getAlertDialog().dismiss();
                         jmESurfaceView.getSimpleApplication().stop(jmESurfaceView.isGLThreadPaused());
                         jmESurfaceView.getSimpleApplication().destroy();
                         finish();
             }
         });


        optionPane.getInflater().findViewById(com.scrappers.jmesurfaceview.R.id.ignoreError).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionPane.getAlertDialog().dismiss();
            }
        });

    }
}