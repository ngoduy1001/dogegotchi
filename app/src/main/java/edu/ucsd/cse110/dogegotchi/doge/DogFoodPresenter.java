package edu.ucsd.cse110.dogegotchi.doge;
import android.view.View;

import edu.ucsd.cse110.dogegotchi.*;

public class DogFoodPresenter implements IDogeObserver {
    private View view;
    private Doge doge;

    public DogFoodPresenter(View view, Doge doge) {
        this.view = view;
        this.doge = doge;
    }

    /**
     * If dog is sad, reveal food menu
     *
     * @param newState New state of the doge.
     */
    @Override
    public void onStateChange(Doge.State newState) {
        if (newState == Doge.State.SAD){
            view.setVisibility(View.VISIBLE);
        }
        else if ((newState == Doge.State.EATING) || (newState == Doge.State.SLEEPING))
            view.setVisibility(View.GONE);
    }
    public void onPlayerFeedDoge(){
        doge.feed();
    }
}
