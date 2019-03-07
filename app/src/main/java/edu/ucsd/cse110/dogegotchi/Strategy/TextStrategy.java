package edu.ucsd.cse110.dogegotchi.Strategy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import edu.ucsd.cse110.dogegotchi.doge.DogeView;
import edu.ucsd.cse110.dogegotchi.sprite.Coord;

public class TextStrategy implements Strategy {

    private String text;
    private Coord textCoord;

    public TextStrategy(String text, Coord textCoord){
        this.text = text;
        this.textCoord = textCoord;
    }

    public void drawToCanvas(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(this.textCoord.getX() - 200, this.textCoord.getY() + 200, this.textCoord.getX() + 200, this.textCoord.getY() - 200, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        canvas.drawText(this.text, this.textCoord.getX() + 20, this.textCoord.getY() - 20, paint);
    }
}

