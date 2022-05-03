import jatyc.lib.Typestate;
import jatyc.lib.Nullable;
import java.util.ArrayList;
import java.util.List;


@Typestate("CalculatorProtocol")
public class Calculator{

  private String airlineRep;
  private String flyer;
  private boolean isTerminated;
  private int totalReward, rewardPerMile;

  public Calculator(String airlineRep, String flyer, int rewardPerMile){
    this.airlineRep = airlineRep;
    this.flyer = flyer;
    this.isTerminated = false;
    this.rewardPerMile = rewardPerMile;
  }

  private boolean hasAccess(String flyer){
    return this.flyer.equals(flyer);
  }

  public boolean addMiles(String flyer, int newMile){
    if(hasAccess(flyer)){
      this.totalReward += (newMile * rewardPerMile);
      return true;
    }
    return false;
  }


  public boolean terminate(String flyer){
    if(hasAccess(flyer))){
      this.isTerminated = true;
      return true;
    }
    return false;
  }

}
