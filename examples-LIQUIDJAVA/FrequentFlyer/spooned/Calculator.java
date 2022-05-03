

@repair.regen.specification.StateSet({ "SetFlyerAndReward", "MilesAdded", "Terminated" })
public class Calculator {
    private java.lang.String airlineRep;

    private java.lang.String flyer;

    private boolean isTerminated;

    private int totalReward;

    private int rewardPerMile;

    @repair.regen.specification.StateRefinement(to = "SetFlyerAndReward(this)")
    public Calculator(java.lang.String airlineRep, java.lang.String flyer, int rewardPerMile) {
        this.airlineRep = airlineRep;
        this.flyer = flyer;
        this.isTerminated = false;
        this.rewardPerMile = rewardPerMile;
    }

    @repair.regen.specification.StateRefinement(from = "SetFlyerAndReward(this) || MilesAdded(this)", to = "MilesAdded(this)")
    public void addMiles(java.lang.String flyer, int newMile) {
        if (this.flyer.equals(flyer)) {
            this.totalReward += newMile * (rewardPerMile);
        }
    }

    @repair.regen.specification.StateRefinement(from = "MilesAdded(this)", to = "Terminated(this)")
    public void terminate() {
        this.isTerminated = true;
    }
}

