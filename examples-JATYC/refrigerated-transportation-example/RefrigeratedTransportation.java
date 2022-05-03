import jatyc.lib.Typestate;
import jatyc.lib.Nullable;


@Typestate("RefrigeratedTransportationProtocol")
public class RefrigeratedTransportation {

  private String currentCounterParty, device, owner;
  private boolean isOutOfCompliance;
  private boolean isComplete;
  private double minTemperature, maxTemperature, minHumidity, maxHumidity;


  public RefrigeratedTransportation(String initiatingCounterParty, String device, String owner, double initialTemp, double minTemperature, double maxTemperature, double minHumidity, double maxHumidity){
    this.currentCounterParty = initiatingCounterParty;
    this.device = device;
    this.owner = owner;
    this.isOutOfCompliance = false;
    this.isComplete = false;
    this.minTemperature = minTemperature;
    this.maxTemperature = maxTemperature;
    this.minHumidity = minHumidity;
    this.maxHumidity = maxHumidity;
  }

  private boolean hasAccess(String credentials, boolean isOwner){
    if(isOwner)
      return this.owner.equals(credentials);
    else
      return this.device.equals(credentials);
  }

  public boolean ingestTelemetry(String device, double temperature, double humidity) {
    if(this.device.equals(device)) {
      if(temperature >= minTemperature && temperature <= maxTemperature &&  humidity >= minHumidity && humidity <= maxHumidity)
      this.isOutOfCompliance = false;
      return true;
    }
    else{
      this.isOutOfCompliance = true;
      return false;
    }

  }

  public void transferResponsability(String currentCounterParty){
    this.currentCounterParty = currentCounterParty;
  }

  public void complete(String owner){
    if(this.owner.equals(owner))
      this.isComplete = true;
  }


}
