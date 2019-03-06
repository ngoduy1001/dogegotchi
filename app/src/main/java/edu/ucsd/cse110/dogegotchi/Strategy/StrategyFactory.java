package edu.ucsd.cse110.dogegotchi.Strategy;

import edu.ucsd.cse110.dogegotchi.doge.Doge;

public class StrategyFactory {
    private Doge.State state;
    private String text;

    public StrategyFactory(Doge.State state){
        this.state = state;
    }
    public StrategyFactory(Doge.State state, String text){
        this.state = state;
        this.text = text;
    }
    public Strategy createStrategy(){
        if ((this.state == Doge.State.HAPPY) || (this.state == Doge.State.EATING))
            return new ImageStrategy();
        else
            return new TextStrategy(text);
    }
}
