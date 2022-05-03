

@repair.regen.specification.StateSet({ "Created", "InTransit", "OutOfCompliance", "Completed" })
public class RefrigeratedTransportation {
    private java.lang.String currentCounterParty;

    private java.lang.String device;

    private java.lang.String owner;

    private java.lang.String observer;

    private boolean isOutOfCompliance;

    private boolean isComplete;

    @repair.regen.specification.StateRefinement(to = "Created(this)")
    public RefrigeratedTransportation(java.lang.String initiatingCounterParty, java.lang.String device, java.lang.String owner) {
        this.currentCounterParty = initiatingCounterParty;
        this.device = device;
        this.owner = owner;
        this.isOutOfCompliance = false;
        this.observer = null;
        this.isComplete = false;
    }

    @repair.regen.specification.StateRefinement(from = "Created(this)", to = "Created(this) || InTransit(this) || OutOfCompliance(this)")
    public void ingestTelemetry(java.lang.String device) {
        if (this.device.equals(device)) {
            this.isOutOfCompliance = false;
        }
    }

    @repair.regen.specification.StateRefinement(from = "Created(this)", to = "OutOfCompliance(this)")
    public void ingestTelemetryOutOfCompliance(java.lang.String device) {
        if (this.device.equals(device)) {
            this.isOutOfCompliance = true;
        }
    }

    @repair.regen.specification.StateRefinement(from = "Created(this) || InTransit(this)", to = "InTransit(this)")
    public void transferResponsability(java.lang.String currentCounterParty) {
        this.currentCounterParty = currentCounterParty;
    }

    @repair.regen.specification.StateRefinement(from = "InTransit(this)", to = "Completed(this)")
    public void complete(java.lang.String owner) {
        if (this.owner.equals(owner))
            this.isComplete = true;

    }
}

