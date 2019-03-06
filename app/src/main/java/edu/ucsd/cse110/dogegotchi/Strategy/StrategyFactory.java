package edu.ucsd.cse110.dogegotchi.Strategy;

import edu.ucsd.cse110.dogegotchi.doge.Doge;

public class StrategyFactory {

    public static Strategy createStrategy(Doge.State state){
        //TODO pass in the image
        if (state == Doge.State.HAPPY)
            return new ImageStrategy();
        else if (state == Doge.State.EATING)
            return new ImageStrategy();
        else if (state == Doge.State.SLEEPING)
            return new TextStrategy("Zzzz...");
        else if (state == Doge.State.SAD)
            return new TextStrategy("Hey!! i'm sad, gibe food pls");
        return null;
    }
}
