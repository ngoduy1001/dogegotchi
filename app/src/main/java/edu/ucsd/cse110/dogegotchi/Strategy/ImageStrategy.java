package edu.ucsd.cse110.dogegotchi.Strategy;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import edu.ucsd.cse110.dogegotchi.sprite.Coord;

public class ImageStrategy implements Strategy {
    private Bitmap foodSprite;
    private Coord foodCoord;

    // Pass in an image
    public ImageStrategy(Bitmap foodSprite, Coord foodCoord){
        this.foodSprite = foodSprite;
        this.foodCoord = foodCoord;
    }

    public void drawToCanvas(Canvas canvas){
        canvas.drawBitmap(foodSprite, foodCoord.getX(), foodCoord.getY(), null);
    }
}
