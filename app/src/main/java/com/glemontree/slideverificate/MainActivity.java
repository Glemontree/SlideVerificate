package com.glemontree.slideverificate;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SlideVerificateView.VerificateResult{

    SlideVerificateView slideView;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slideView = (SlideVerificateView) findViewById(R.id.slideVerificate);
        slideView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.picture));
        slideView.setAccuracy(0.02);
        slideView.setVerficateResult(this);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                slideView.setMove(progress * 0.01);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                slideView.isTheRightRange();
            }
        });
    }

    @Override
    public void onSuucess() {
        seekBar.setProgress(0);
        slideView.setRedraw();
        Toast.makeText(MainActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError() {
        seekBar.setProgress(0);
        slideView.setRedraw();
        Toast.makeText(MainActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
    }
}
