package edu.ucsd.cse110.dogegotchi;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.dogegotchi.daynightcycle.DayNightCycleMediator;
import edu.ucsd.cse110.dogegotchi.doge.DogFoodPresenter;
import edu.ucsd.cse110.dogegotchi.doge.Doge;
import edu.ucsd.cse110.dogegotchi.doge.DogeFactory;
import edu.ucsd.cse110.dogegotchi.doge.DogeView;
import edu.ucsd.cse110.dogegotchi.sprite.Coord;
import edu.ucsd.cse110.dogegotchi.ticker.AsyncTaskTicker;
import edu.ucsd.cse110.dogegotchi.ticker.ITicker;

/**
 * In reading this class observe how we use the xml resource files for
 * constants, instead of making them static pieces of code.
 */
public class MainActivity extends Activity {

    private ITicker ticker;

    private DayNightCycleMediator dayNightCycleMediator;

    private DogFoodPresenter dogFoodPresenter;

    private Doge doge;

    private DogeView dogeView;

    private MediaPlayer dayPlayer;

    private MediaPlayer nightPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make game fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // start the ticker
        this.ticker = new AsyncTaskTicker(getResources().getInteger(R.integer.tick_interval));

        this.dayPlayer = MediaPlayer.create(this, R.raw.daytime_short);
        this.nightPlayer = MediaPlayer.create(this, R.raw.night_time);

        /**
         * create day night cycle tracker
         * Note: we implemented this for you, but do read the code to understand it.
         */
        int ticksPerPeriod = getResources().getInteger(R.integer.ticks_per_period);
        this.dayNightCycleMediator = new DayNightCycleMediator(ticksPerPeriod);
        ticker.register(this.dayNightCycleMediator);

        /**
         * TODO: Exercise 1 -- Observer
         *
         * - You'll have to make doge's state change from Happy/Sad at night to Sleeping.
         * - In the morning, doge should go to happy state. See write-up.
         */
        // create the almighty doge
        //createDoge(ticksPerPeriod);
        Pair<Doge, DogeView> dogPair = DogeFactory.createDoge(this, ticksPerPeriod);
        this.doge = dogPair.first;
        this.dogeView = dogPair.second;
        ticker.register(this.doge);
        dayNightCycleMediator.register(this.doge);

        final GameView gameView = this.findViewById(R.id.GameCanvasView);
        gameView.setMedia(dayPlayer, nightPlayer);
        gameView.setSprites(Collections.singletonList(this.dogeView));

        ticker.register(gameView);
        this.dayNightCycleMediator.register(gameView);

        /**
         * TODO: Exercise 2 -- MVP
         *
         * Wire up the doge mood swings with controllers:
         *      1. Make the food menu visible.
         *          !!! Note: we have already implemented the menu for you,
         *          you just need to make it visible.
         *
         *      2. Wire up listeners on the buttons in the food menu view
         *         to record which food item was chosen.
         *
         *      3. Feed doge and update their state accordingly.
         */

        final View foodMenu = this.findViewById(R.id.FoodMenuView);
        final ImageButton hamButton       = foodMenu.findViewById(R.id.HamButton),
                          steakButton     = foodMenu.findViewById(R.id.SteakButton),
                          turkeyLegButton = foodMenu.findViewById(R.id.TurkeyLegButton);

        this.dogFoodPresenter = new DogFoodPresenter(foodMenu, doge);
        doge.register(this.dogFoodPresenter);

        //TODO set up onclicks
        // calll presenter update view
        hamButton.setOnClickListener(view -> this.dogFoodPresenter.onPlayerFeedDoge(Doge.Food.HAM));

        steakButton.setOnClickListener(view -> this.dogFoodPresenter.onPlayerFeedDoge(Doge.Food.STEAK));

        turkeyLegButton.setOnClickListener(view -> this.dogFoodPresenter.onPlayerFeedDoge(Doge.Food.TURKEY));

        /**
         * TODO: Exercise 3 -- Strategy & Factory
         *
         * Exercise 3 just build on your work for the previous two.
         *
         * Your goal is to use the Strategy pattern to enable different behaviors
         * per each one of the states the dog can take.
         *
         * The Factory should be used to instantiate the appropriate Strategy per state.
         * (A simple hint-based Factory will do, i.e. where you pass the State and the
         *  right behavior is created.) We encourage you to try using the Factory method.
         */

        /**
         * TODO: Exercise 4 -- The Hunt for Design Flaws
         *
         * Identify _ONE_ non-trivial design issue, with respect to good enough design
         * methodology (SRP, OCP, etc), and apply design pattern(s) to solve it.
         *
         * (Non-trivial means you can't simply extract an interface and change variables to
         * use the interface. There are plenty of issues in this code.)
         */

        /**
         * Good luck! Here we go...
         */
        // Action! 🎬
        ticker.start();
        try {
            dayPlayer.prepare();
            nightPlayer.prepare();
        }
        catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "Failed to prepare media player.");
        }
        Log.i("main", "Here we go...");
    }

//    /**
//     * Creational logic for Doge and DogeView.
//     *
//     * Refactor {@link Doge} and/or {@link DogeView} accordingly using the Observer pattern
//     * so that our doge goes to sleep at night. When waking up in the morning, the Doge should
//     * be {@link edu.ucsd.cse110.dogegotchi.doge.Doge.State#HAPPY}, regardless of previous state.
//     *
//     * @param ticksPerPeriod Number of ticks per {@link edu.ucsd.cse110.dogegotchi.daynightcycle.IDayNightCycleObserver.Period} period.
//     */
//    private void createDoge(final int ticksPerPeriod) {
//        // create Doge model
//        int ticksPerMoodSwing = ticksPerPeriod/getResources().getInteger(R.integer.mood_swings_per_period);
//        double moodSwingProbability = getResources().getInteger(R.integer.mood_swing_probability)/100.0;
//        this.doge = new Doge(ticksPerMoodSwing, moodSwingProbability);
//
//        // create Doge view
//        Map<Doge.State, Bitmap> stateBitmaps = new HashMap<>();
//        Map<Doge.State, Coord > stateCoords  = new HashMap<>();
//        //TODO For the items
//        Map<Doge.Food, Bitmap> foodBitmaps = new HashMap<>();
//        Map<Doge.Food, Coord > foodCoords  = new HashMap<>();
//
//        // Setup views and coords per state.
//        /**
//         * TODO: Set up {@link Doge.State.SAD} and {@link Doge.State.EATING}. Be sure to
//         *       go to res/values/doge.xml and enter {x,y} coordinate values there as we
//         *       did for you for HAPPY and SLEEPING states.
//         */
//        stateBitmaps.put(Doge.State.HAPPY,
//                         BitmapFactory.decodeResource(getResources(), R.drawable.happy_2x));
//        stateCoords.put(Doge.State.HAPPY,
//                        new Coord(getResources().getInteger(R.integer.happy_x),
//                                  getResources().getInteger(R.integer.happy_y)));
//
//        // TODO: Exercise 1 - set up sprite and coords for SAD state.
//        stateBitmaps.put(Doge.State.SLEEPING,
//                         BitmapFactory.decodeResource(getResources(), R.drawable.sleeping_2x));
//        stateCoords.put(Doge.State.SLEEPING,
//                        new Coord(getResources().getInteger(R.integer.sleeping_x),
//                                  getResources().getInteger(R.integer.sleeping_y)));
//
//        stateBitmaps.put(Doge.State.SAD,
//                BitmapFactory.decodeResource(getResources(), R.drawable.sad_2x));
//        stateCoords.put(Doge.State.SAD,
//                new Coord(getResources().getInteger(R.integer.sad_x),
//                        getResources().getInteger(R.integer.sad_y)));
//
//        // TODO: Exercise 2 - Set up sprite and coords for EATING state.
//        stateBitmaps.put(Doge.State.EATING,
//                BitmapFactory.decodeResource(getResources(), R.drawable.eating_2x));
//        stateCoords.put(Doge.State.EATING,
//                new Coord(getResources().getInteger(R.integer.eating_x),
//                        getResources().getInteger(R.integer.eating_y)));
//
//        foodBitmaps.put(Doge.Food.BONE,
//                BitmapFactory.decodeResource(getResources(), R.drawable.dogbone_2x));
//        foodCoords.put(Doge.Food.BONE,
//                //TODO Change these Coord
//                new Coord(getResources().getInteger(R.integer.happy_x) + 0,
//                        getResources().getInteger(R.integer.happy_y) + 200 ));
//
//        foodBitmaps.put(Doge.Food.HAM,
//                BitmapFactory.decodeResource(getResources(), R.drawable.ham_2x));
//        foodCoords.put(Doge.Food.HAM,
//                //TODO Change these Coord
//                new Coord(getResources().getInteger(R.integer.eating_x) + 0,
//                        getResources().getInteger(R.integer.eating_y) + 150 ));
//
//        foodBitmaps.put(Doge.Food.TURKEY,
//                BitmapFactory.decodeResource(getResources(), R.drawable.turkey_leg_2x));
//        foodCoords.put(Doge.Food.TURKEY,
//                //TODO Change these Coord
//                new Coord(getResources().getInteger(R.integer.eating_x) + 0,
//                        getResources().getInteger(R.integer.eating_y) + 150 ));
//
//        foodBitmaps.put(Doge.Food.STEAK,
//                BitmapFactory.decodeResource(getResources(), R.drawable.steak_2x));
//        foodCoords.put(Doge.Food.STEAK,
//                //TODO Change these Coord
//                new Coord(getResources().getInteger(R.integer.eating_x) + 0,
//                        getResources().getInteger(R.integer.eating_y) + 150 ));
//
//        // TODO: Exercise 3 - You may need to create the Factory of Strategies here
//        this.dogeView = new DogeView(this, Doge.State.HAPPY, stateBitmaps, stateCoords, foodBitmaps, foodCoords);
//
//        // make the doge view observe doge's mood swings
//        this.doge.register(this.dogeView);
//
//    }

    @Override
    public void onDestroy() {
        if (this.dayPlayer.isPlaying()) {
            this.dayPlayer.stop();
        }
        if (this.nightPlayer.isPlaying()) {
            this.nightPlayer.stop();
        }
        this.ticker.stop();
        super.onDestroy();
    }
}
