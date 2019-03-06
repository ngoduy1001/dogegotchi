package edu.ucsd.cse110.dogegotchi.Strategy;

import android.graphics.Bitmap;

import edu.ucsd.cse110.dogegotchi.doge.Doge;
import edu.ucsd.cse110.dogegotchi.sprite.Coord;

public class StrategyFactory {

    public static Strategy createStrategy(Bitmap foodSprite, Coord foodCoord){
        return new ImageStrategy(foodSprite, foodCoord);
    }

    public static Strategy createStrategy(String text, Coord textCoord){
        return new TextStrategy(text, textCoord);
    }
}
