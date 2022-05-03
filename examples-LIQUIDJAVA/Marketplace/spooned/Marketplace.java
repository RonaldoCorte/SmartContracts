

@repair.regen.specification.StateSet({ "itemAvailable", "offerPlaced", "accept" })
@repair.regen.specification.RefinementAlias("IsValid(int x) { x >= 0 }")
public class Marketplace {
    @repair.regen.specification.Refinement("IsValid(ownerId)")
    private int ownerId;

    @repair.regen.specification.Refinement("IsValid(ownerBalance)")
    private int ownerBalance;

    @repair.regen.specification.Refinement("IsValid(buyerId)")
    private int buyerId;

    @repair.regen.specification.Refinement("IsValid(buyerBalance)")
    private int buyerBalance;

    private java.lang.String item;

    private int itemValue;

    @repair.regen.specification.Refinement("offer >= 0")
    private int offer;

    private boolean isSold;

    @repair.regen.specification.StateRefinement(to = "itemAvailable(this)")
    public Marketplace(@repair.regen.specification.Refinement("IsValid(ownerId)")
    int ownerId, @repair.regen.specification.Refinement("IsValid(ownerBalance)")
    int ownerBalance, @repair.regen.specification.Refinement("IsValid(buyer)")
    int buyer, @repair.regen.specification.Refinement("IsValid(buyerBalance)")
    int buyerBalance, java.lang.String item, @repair.regen.specification.Refinement("IsValid(itemValue)")
    int itemValue) {
        this.ownerId = ownerId;
        this.ownerBalance = ownerBalance;
        this.buyerId = buyer;
        this.buyerBalance = buyerBalance;
        this.itemValue = itemValue;
        this.item = item;
        this.offer = 0;
        isSold = false;
    }

    private boolean hasAccess(@repair.regen.specification.Refinement("IsValid(credentials)")
    int credentials, boolean isOwner) {
        if (isOwner)
            return credentials == (this.ownerId);
        else
            return credentials == (this.buyerId);

    }

    @repair.regen.specification.StateRefinement(from = "itemAvailable(this)", to = "itemAvailable(this)")
    @repair.regen.specification.Refinement("_ != 0")
    public int makeOffer(@repair.regen.specification.Refinement("IsValid(buyer)")
    int buyer, @repair.regen.specification.Refinement("IsValid(offervalue)")
    int offerValue) {
        if (hasAccess(buyer, false)) {
            this.offer = offerValue;
            java.lang.System.out.println(("Offer has been made: " + offerValue));
            return 1;
        } else
            return 0;

    }

    @repair.regen.specification.StateRefinement(from = "itemAvailable(this)", to = "accept(this)")
    @repair.regen.specification.Refinement("_ >= 0")
    public int accept(@repair.regen.specification.Refinement("IsValid(ownerId)")
    int ownerId) {
        if (hasAccess(ownerId, true)) {
            isSold = true;
            ownerBalance += offer;
            buyerBalance -= offer;
            java.lang.System.out.println("Offer accepted");
            return buyerBalance;
        }
        return -1;
    }

    @repair.regen.specification.StateRefinement(from = "itemAvailable(this)", to = "itemAvailable(this)")
    @repair.regen.specification.Refinement("_ > 0")
    public int reject(@repair.regen.specification.Refinement("IsValid(ownerId)")
    int ownerId) {
        if (hasAccess(ownerId, true)) {
            isSold = false;
            java.lang.System.out.println("Offer rejected");
            return 1;
        } else
            return 0;

    }
}

