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

    private Strategy strategy;

    public DogeView(final Context context,
                    final Doge.State initialState,
                    final Map<Doge.State, Bitmap> viewsPerState,
                    final Map<Doge.State, Coord> coordsPerState) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(viewsPerState);
        Preconditions.checkNotNull(coordsPerState);

        this.viewsPerState  = ImmutableMap.copyOf(viewsPerState);
        this.coordsPerState = ImmutableMap.copyOf(coordsPerState);

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
        this.strategy = StrategyFactory.createStrategy(newState);
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
