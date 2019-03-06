package edu.ucsd.cse110.dogegotchi.doge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

import edu.ucsd.cse110.dogegotchi.Strategy.Strategy;
import edu.ucsd.cse110.dogegotchi.Strategy.StrategyFactory;
import edu.ucsd.cse110.dogegotchi.sprite.BitmapSprite;
import edu.ucsd.cse110.dogegotchi.sprite.Coord;

/**
 * Displays the appropriate sprite for doge, given its swings. To do so
 * it listens to mood updates, switching to that mood's sprite accordingly.
 */
public class DogeView
        extends BitmapSprite
        implements IDogeObserver {
    private static final String LOOKUP_TABLE_PRECONDITION_ERROR_MSG =
            "No %s configured for state %s in lookup table %s.";

    /**
     * Lookup table for each state's bitmap.
     */
    private ImmutableMap<Doge.State, Bitmap> viewsPerState;

    /**
     * Lookup table for the coordinates for each state.
     */
    private ImmutableMap<Doge.State, Coord> coordsPerState;

    /**
     * Lookup table for each food's bitmap.
     */
    private ImmutableMap<Doge.Food, Bitmap> viewsPerFood;

    /**
     * Lookup table for the coordinates for each food.
     */
    private ImmutableMap<Doge.Food, Coord> coordsPerFood;

    private Strategy strategy;

    public Bitmap foodSprite = null;

    public Coord foodCoord = null;


    public DogeView(final Context context,
                    final Doge.State initialState,
                    final Map<Doge.State, Bitmap> viewsPerState,
                    final Map<Doge.State, Coord> coordsPerState,
                    final Map<Doge.Food, Bitmap> viewsPerFood,
                    final Map<Doge.Food, Coord> coordsPerFood) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(viewsPerState);
        Preconditions.checkNotNull(coordsPerState);
        Preconditions.checkNotNull(viewsPerFood);
        Preconditions.checkNotNull(coordsPerFood);


        this.viewsPerState  = ImmutableMap.copyOf(viewsPerState);
        this.coordsPerState = ImmutableMap.copyOf(coordsPerState);
        this.viewsPerFood = ImmutableMap.copyOf(viewsPerFood);
        this.coordsPerFood = ImmutableMap.copyOf(coordsPerFood);

        // load sprite for initial state
        onStateChange(initialState);
    }

    /**
     * Updates the doge's sprite and its coordinates when the doge's state changes.
     *
     * @param newState New state of the doge.
     */
    @Override
    public void onStateChange(Doge.State newState) {
        checkState(newState); // x-cutting concern

        // update super sprite
        this.setSprite(this.viewsPerState.get(newState));
        this.setCoord(this.coordsPerState.get(newState));
        // TODO FACTORY create here then save the strategy
        if (newState == Doge.State.EATING)
            this.strategy = StrategyFactory.createStrategy(this.foodSprite, this.foodCoord);
        else if (newState == Doge.State.HAPPY)
            this.strategy = StrategyFactory.createStrategy(this.viewsPerFood.get(Doge.Food.BONE), this.coordsPerFood.get(Doge.Food.BONE));
        else if (newState == Doge.State.SLEEPING)
            this.strategy = StrategyFactory.createStrategy("Zzz...", this.coord);
        else if (newState == Doge.State.SAD)
            this.strategy = StrategyFactory.createStrategy("Hey!! i'm sad, gibe food pls", this.coord);
    }

    public void onFoodChange(Doge.Food newFood){
        this.setFoodSprite(this.viewsPerFood.get(newFood));
        this.setFoodCoord(this.coordsPerFood.get(newFood));
    }

    public void setFoodSprite(final Bitmap foodSprite) {
        Preconditions.checkNotNull(sprite);
        this.foodSprite = foodSprite;
    }
    public void setFoodCoord(final Coord coord) {
        Preconditions.checkNotNull(coord);
        this.foodCoord = coord;
    }

    /**
     * Make sure that a sprite and coordinates were registered to the given state.
     *
     * @param state State to be checked.
     */
    private void checkState(Doge.State state) {
        Preconditions.checkArgument(this.viewsPerState.containsKey(state),
                String.format(LOOKUP_TABLE_PRECONDITION_ERROR_MSG,
                        "bitmap", state.toString(), "viewsPerState"));

        Preconditions.checkArgument(this.coordsPerState.containsKey(state),
                String.format(LOOKUP_TABLE_PRECONDITION_ERROR_MSG,
                        "coordinates", state.toString(), "coordsPerState"));
    }

    @Override
    public void draw(Canvas canvas) {
        Preconditions.checkNotNull(sprite);
        Preconditions.checkArgument(coord.getX()>=0, String.format(PRECONDITION_ERROR_MESSAGE, "x"));
        Preconditions.checkArgument(coord.getY()>=0, String.format(PRECONDITION_ERROR_MESSAGE, "y"));

        canvas.drawBitmap(sprite, coord.getX(), coord.getY(), null);

        this.strategy.drawToCanvas(canvas);
    }
}
