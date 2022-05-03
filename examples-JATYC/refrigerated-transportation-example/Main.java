public class Main {
  public static void main(String[] args) {
   RefrigeratedTransportation r = new RefrigeratedTransportation("initiatingCounterParty", "device", "owner", 30, -10, 60, 0, 40);
    r.transferResponsability("responsible");
    if(r.ingestTelemetry("device",10,10))
      r.complete("owner");


    //error try to complete without transfer responsability
   /* RefrigeratedTransportation r = new RefrigeratedTransportation("initiatingCounterParty", "device", "owner");
    r.ingestTelemetry("device");
    r.complete("owner");*/
  }
}
