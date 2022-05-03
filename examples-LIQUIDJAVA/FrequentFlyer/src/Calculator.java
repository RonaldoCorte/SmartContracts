import repair.regen.specification.Refinement;
import repair.regen.specification.RefinementAlias;
import repair.regen.specification.StateRefinement;
import repair.regen.specification.StateSet;

@StateSet({"SetFlyerAndReward", "MilesAdded", "Terminated"})
@RefinementAlias("IsValid(int x) { x >= 0 }")
public class Calculator{

  @Refinement("IsValid(airlineRep)")
  private int airlineRep;
  @Refinement("IsValid(flyer)")
  private int flyer;
  private boolean isTerminated;
  private int totalReward;
  @Refinement("IsValid(rewardPerMile)") 
  private int rewardPerMile;

  
  @StateRefinement(to="SetFlyerAndReward(this)")
  public Calculator(  @Refinement("IsValid(airlineRep)") int airlineRep,   @Refinement("IsValid(flyer)") int flyer,   @Refinement("IsValid(rewardPerMile)") int rewardPerMile){
    this.airlineRep = airlineRep;
    this.flyer = flyer;
    this.isTerminated = false;
    this.rewardPerMile = rewardPerMile;
  }

  @StateRefinement(from="SetFlyerAndReward(this) || MilesAdded(this)", to="MilesAdded(this)")
  @Refinement("_ >= newMile")
  public int addMiles(  @Refinement("IsValid(flyer)") int flyer,  @Refinement("IsValid(newMile)") int newMile){
    if(this.flyer == (flyer)){
      this.totalReward += (newMile * rewardPerMile);
      return totalReward;
    }
    return 0;
  }

  @StateRefinement(from="MilesAdded(this)", to="Terminated(this)")
  public void terminate(){
    this.isTerminated = true;
  }

}
