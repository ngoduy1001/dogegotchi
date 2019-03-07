package edu.ucsd.cse110.dogegotchi.doge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;

import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.dogegotchi.MainActivity;
import edu.ucsd.cse110.dogegotchi.R;
import edu.ucsd.cse110.dogegotchi.sprite.Coord;

public class DogeFactory {
    public static Pair<Doge, DogeView> createDoge(Context context, final int ticksPerPeriod) {
        // create Doge model
        int ticksPerMoodSwing = ticksPerPeriod/ context.getResources().getInteger(R.integer.mood_swings_per_period);
        double moodSwingProbability = context.getResources().getInteger(R.integer.mood_swing_probability)/100.0;
        Doge doge = new Doge(ticksPerMoodSwing, moodSwingProbability);

        // create Doge view
        Map<Doge.State, Bitmap> stateBitmaps = new HashMap<>();
        Map<Doge.State, Coord> stateCoords  = new HashMap<>();
        //TODO For the items
        Map<Doge.Food, Bitmap> foodBitmaps = new HashMap<>();
        Map<Doge.Food, Coord > foodCoords  = new HashMap<>();

        // Setup views and coords per state.
        /**
         * TODO: Set up {@link Doge.State.SAD} and {@link Doge.State.EATING}. Be sure to
         *       go to res/values/doge.xml and enter {x,y} coordinate values there as we
         *       did for you for HAPPY and SLEEPING states.
         */
        stateBitmaps.put(Doge.State.HAPPY,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.happy_2x));
        stateCoords.put(Doge.State.HAPPY,
                new Coord(context.getResources().getInteger(R.integer.happy_x),
                        context.getResources().getInteger(R.integer.happy_y)));

        // TODO: Exercise 1 - set up sprite and coords for SAD state.
        stateBitmaps.put(Doge.State.SLEEPING,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.sleeping_2x));
        stateCoords.put(Doge.State.SLEEPING,
                new Coord(context.getResources().getInteger(R.integer.sleeping_x),
                        context.getResources().getInteger(R.integer.sleeping_y)));

        stateBitmaps.put(Doge.State.SAD,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.sad_2x));
        stateCoords.put(Doge.State.SAD,
                new Coord(context.getResources().getInteger(R.integer.sad_x),
                        context.getResources().getInteger(R.integer.sad_y)));

        // TODO: Exercise 2 - Set up sprite and coords for EATING state.
        stateBitmaps.put(Doge.State.EATING,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.eating_2x));
        stateCoords.put(Doge.State.EATING,
                new Coord(context.getResources().getInteger(R.integer.eating_x),
                        context.getResources().getInteger(R.integer.eating_y)));

        foodBitmaps.put(Doge.Food.BONE,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.dogbone_2x));
        foodCoords.put(Doge.Food.BONE,
                //TODO Change these Coord
                new Coord(context.getResources().getInteger(R.integer.happy_x) + 0,
                        context.getResources().getInteger(R.integer.happy_y) + 200 ));

        foodBitmaps.put(Doge.Food.HAM,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ham_2x));
        foodCoords.put(Doge.Food.HAM,
                //TODO Change these Coord
                new Coord(context.getResources().getInteger(R.integer.eating_x) + 0,
                        context.getResources().getInteger(R.integer.eating_y) + 150 ));

        foodBitmaps.put(Doge.Food.TURKEY,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.turkey_leg_2x));
        foodCoords.put(Doge.Food.TURKEY,
                //TODO Change these Coord
                new Coord(context.getResources().getInteger(R.integer.eating_x) + 0,
                        context.getResources().getInteger(R.integer.eating_y) + 150 ));

        foodBitmaps.put(Doge.Food.STEAK,
                BitmapFactory.decodeResource(context.getResources(), R.drawable.steak_2x));
        foodCoords.put(Doge.Food.STEAK,
                //TODO Change these Coord
                new Coord(context.getResources().getInteger(R.integer.eating_x) + 0,
                        context.getResources().getInteger(R.integer.eating_y) + 150 ));

        // TODO: Exercise 3 - You may need to create the Factory of Strategies here
        DogeView dogeView = new DogeView(context, Doge.State.HAPPY, stateBitmaps, stateCoords, foodBitmaps, foodCoords);

        // make the doge view observe doge's mood swings
        doge.register(dogeView);
        return new Pair<>(doge, dogeView);
    }
}
