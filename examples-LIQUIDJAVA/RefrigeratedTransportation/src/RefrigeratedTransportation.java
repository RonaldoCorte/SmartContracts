import repair.regen.specification.StateRefinement;
import repair.regen.specification.StateSet;

@StateSet({"Created", "InTransit", "OutOfCompliance", "Completed"})
public class RefrigeratedTransportation {

  private String currentCounterParty, device, owner, observer;
  private boolean isOutOfCompliance;
  private boolean isComplete;


  @StateRefinement(to="Created(this)")
  public RefrigeratedTransportation(String initiatingCounterParty, String device, String owner){
    this.currentCounterParty = initiatingCounterParty;
    this.device = device;
    this.owner = owner;
    this.isOutOfCompliance = false;
    this.observer = null;
    this.isComplete = false;
  }

  @StateRefinement(from="Created(this)", to="Created(this) || InTransit(this) || OutOfCompliance(this)")
  public void ingestTelemetry(String device) {
    if(this.device.equals(device)) {
      this.isOutOfCompliance = false;
    }
  }


  @StateRefinement(from="Created(this)", to="OutOfCompliance(this)")
  public void ingestTelemetryOutOfCompliance(String device) {
    if(this.device.equals(device)) {
      this.isOutOfCompliance = true;
    }
  }


  @StateRefinement(from="Created(this) || InTransit(this)", to="InTransit(this)")
  public void transferResponsability(String currentCounterParty){
    this.currentCounterParty = currentCounterParty;
  }

  @StateRefinement(from="InTransit(this)", to="Completed(this)")

  public void complete(String owner){
    if(this.owner.equals(owner))
      this.isComplete = true;
  }


}
