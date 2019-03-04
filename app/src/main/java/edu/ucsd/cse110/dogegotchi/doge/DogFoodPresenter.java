package edu.ucsd.cse110.dogegotchi.doge;
import edu.ucsd.cse110.dogegotchi.*;

public class DogFoodPresenter implements IDogeObserver {
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
}
