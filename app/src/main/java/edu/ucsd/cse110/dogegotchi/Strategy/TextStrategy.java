package edu.ucsd.cse110.dogegotchi.Strategy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import edu.ucsd.cse110.dogegotchi.doge.DogeView;

public class TextStrategy implements Strategy {

    private String text;

    private TextStrategy(String text){
        this.text = text;
    }

    public void drawToCanvas(Canvas canvas,){
        Paint paint = new Paint();
        canvas.drawPaint(paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(16);
        canvas.drawText(this.text, x, y, paint);
    }
}

