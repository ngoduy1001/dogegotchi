package edu.ucsd.cse110.dogegotchi.doge;

import android.util.Log;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import edu.ucsd.cse110.dogegotchi.R;
import edu.ucsd.cse110.dogegotchi.daynightcycle.IDayNightCycleObserver;
import edu.ucsd.cse110.dogegotchi.observer.ISubject;
import edu.ucsd.cse110.dogegotchi.ticker.ITickerObserver;

/**
 * Logic for our friendly, sophisticated doge.
 *
 * TODO: Exercise 1 -- add support for {@link State#SLEEPING}.
 *
 * TODO: Exercise 2 -- enable {@link State#SAD} mood, and add support for {@link State#EATING} behavior.
 */
public class Doge implements ISubject<IDogeObserver>, ITickerObserver, IDayNightCycleObserver {
    /**
     * Current number of ticks. Reset after every potential mood swing.
     */
    int numTicks;

    /**
     * How many ticks before we toss a multi-sided die to check mood swing.
     */
    final int numTicksBeforeMoodSwing;

    /**
     * Probability of a mood swing every {@link #numTicksBeforeMoodSwing}.
     */
    final double moodSwingProbability;

    /**
     * State of doge.
     */
    State state;
    Food food;
    private Collection<IDogeObserver> observers;

    /**
     * Constructor.
     *
     * @param numTicksBeforeMoodSwing Number of ticks before checking for mood swing.
     * @param moodSwingProbability Probability of a mood swing every {@link #numTicksBeforeMoodSwing}.
     */
    public Doge(final int numTicksBeforeMoodSwing, final double moodSwingProbability) {
        Preconditions.checkArgument(
                0.0 <= moodSwingProbability && moodSwingProbability < 1.0f,
                "Mood swing probability must be in range [0,1).");

        this.numTicks = 0;
        this.numTicksBeforeMoodSwing = numTicksBeforeMoodSwing;
        this.moodSwingProbability = moodSwingProbability;
        this.state = State.HAPPY;
        this.observers = new ArrayList<>();
        Log.i(this.getClass().getSimpleName(), String.format(
                "Creating Doge with initial state %s, with mood swing prob %.2f"
                + "and num ticks before each swing attempt %d",
                this.state, this.moodSwingProbability, this.numTicksBeforeMoodSwing));
    }

    @Override
    public void onTick() {
        this.numTicks++;

        if (this.numTicks > 0
            && (this.numTicks % this.numTicksBeforeMoodSwing) == 0) {
            tryRandomMoodSwing();
            doneEating();
            this.numTicks = 0;
        }
    }

    @Override
    public void onPeriodChange(Period newPeriod) {
        if ((newPeriod == Period.NIGHT) && (this.state != State.EATING))
            setState(State.SLEEPING);
        else if (newPeriod == Period.DAY)
            setState(State.HAPPY);
    }

    /**
     * TODO: Exercise 1 -- Fill in this method to randomly make doge sad with probability {@link #moodSwingProbability}.
     *
     * **Strictly follow** the Finite State Machine in the write-up.
     */
    private void tryRandomMoodSwing() {
        // TODO: Exercise 1 -- Implement this method...
        if (this.state == State.HAPPY){
            //if (new Random().nextDouble() <= moodSwingProbability)
                setState(State.SAD);
        }
    }

    @Override
    public void register(IDogeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(IDogeObserver observer) {
        observers.remove(observer);
    }

    /**
     * Updates the state of our friendly doge and updates all observers.
     *
     * Note: observe how by using a setter we guarantee that side effects of
     *       an update occur, namely notifying the observers. And it's unused
     *       right now, hm...
     */

    //TODO have notify observers method not do 2 in one
    private void setState(final Doge.State newState) {
        this.state = newState;
        Log.i(this.getClass().getSimpleName(), "Doge state changed to: " + newState);
        for (IDogeObserver observer : this.observers) {
            observer.onStateChange(newState);

        }
    }
    private void setFood(final Doge.Food food){
        this.food = food;
        for (IDogeObserver observer : this.observers) {
            observer.onFoodChange(food);
        }
    }
    public void feed(Food food){
        this.setFood(food);
        this.setState(State.EATING);
    }
    private void doneEating(){
        if (this.state == State.EATING){
            setState(State.HAPPY);
        }
    }
    /**
     * Moods and actions for our doge.
     */
    public enum State {
        HAPPY,
        SAD,
        // TODO: Implement asleep and eating states, and transitions between all states.
        SLEEPING,
        EATING
    }
    public enum Food {
        HAM,
        STEAK,
        TURKEY,
        BONE
    }
}
