package edu.ucsd.cse110.dogegotchi.Strategy;

import android.graphics.Canvas;

import edu.ucsd.cse110.dogegotchi.doge.DogeView;

public class TextStrategy implements Strategy {

    private String text;

    public TextStrategy(String text){
        this.text = text;
    }

    public void drawToCanvas(){
        /*
        Paint paint = new Paint();
        canvas.drawPaint(paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(16);
        canvas.drawText("My Text", x, y, paint);
*/
    }
}

