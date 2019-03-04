package edu.ucsd.cse110.dogegotchi.doge;
import android.view.View;

import edu.ucsd.cse110.dogegotchi.*;

public class DogFoodPresenter implements IDogeObserver {
    private View view;

    public DogFoodPresenter(View view) {
        this.view = view;
    }

    /**
     * If dog is sad, reveal food menu
     *
     * @param newState New state of the doge.
     */
    @Override
    public void onStateChange(Doge.State newState) {
        if (newState == Doge.State.SAD){
            return;
        }
    }
    public void updateView(){
        view.setVisibility(View.VISIBLE);
    }
}
