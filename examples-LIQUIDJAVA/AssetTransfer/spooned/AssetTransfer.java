

@repair.regen.specification.StateSet({ "Active", "OfferPlaced", "PendingInspection", "Inspected", "Appraised", "NotionalAcceptance", "SellerAccepted", "BuyerAccepted", "Accepted", "Terminated" })
public class AssetTransfer {
    private java.lang.String seller;

    private java.lang.String buyer;

    private java.lang.String inspector;

    private java.lang.String appraiser;

    private java.lang.String descripiton;

    private int askingPrice;

    private int offerPrice;

    private Status status;

    @repair.regen.specification.StateRefinement(to = "Active(this)")
    public AssetTransfer(java.lang.String seller, java.lang.String buyer, java.lang.String inspector, java.lang.String appraiser, java.lang.String descripiton, int askingPrice) {
        this.seller = seller;
        this.buyer = buyer;
        this.inspector = inspector;
        this.appraiser = appraiser;
        this.offerPrice = 0;
        this.descripiton = descripiton;
        this.askingPrice = askingPrice;// Meter status e nao variaveis booleanas

        this.status = Status.Active;
    }

    @repair.regen.specification.StateRefinement(from = "Active(this)", to = "Active(this)")
    public void modify(java.lang.String owner, java.lang.String descripiton, int price) {
        if (this.seller.equals(owner)) {
            this.descripiton = descripiton;
            this.offerPrice = price;
            this.status = Status.Active;
        }
    }

    @repair.regen.specification.StateRefinement(from = "OfferPlaced(this)", to = "OfferPlaced(this)")
    public void modifyOffer(java.lang.String buyer, int newPrice) {
        if (this.buyer.equals(buyer)) {
            this.offerPrice = newPrice;
            this.status = Status.OfferPlaced;
        }
    }

    @repair.regen.specification.StateRefinement(from = "Active(this) ||OfferPlaced(this) || PendingInspection(this) || Inspected(this) || Appraised(this) || NotionalAcceptance(this) || SellerAccepted(this) || BuyerAccepted(this)", to = "Terminated(this)")
    public void terminate(java.lang.String owner) {
        if (this.seller.equals(owner)) {
            this.status = Status.Terminated;
        }
    }

    @repair.regen.specification.StateRefinement(from = "Active(this)", to = "OfferPlaced(this)")
    public void makeOffer(java.lang.String buyer, java.lang.String inspector, java.lang.String appraiser, int offerPrice) {
        if (((this.buyer.equals(buyer)) && (this.inspector.equals(inspector))) && (this.appraiser.equals(appraiser))) {
            this.offerPrice = offerPrice;
            this.status = Status.OfferPlaced;
        }
    }

    @repair.regen.specification.StateRefinement(from = "OfferPlaced(this)", to = "Active(this)")
    public void rejectOffer(java.lang.String seller) {
        if (this.seller.equals(seller)) {
            this.status = Status.Active;
        }
    }

    @repair.regen.specification.StateRefinement(from = "OfferPlaced(this)", to = "PendingInspection(this)")
    public void acceptOffer(java.lang.String seller) {
        if (this.seller.equals(seller))
            this.status = Status.PendingInspection;

    }

    @repair.regen.specification.StateRefinement(from = "OfferPlaced(this) || PendingInspection(this) || Inspected(this) || Appraised(this) || NotionalAcceptance(this) || SellerAccepted(this)", to = "Active(this)")
    public void rescindOffer(java.lang.String seller) {
        if (this.seller.equals(seller)) {
            this.offerPrice = 0;
            this.status = Status.Active;
        }
    }

    @repair.regen.specification.StateRefinement(from = "PendingInspection(this) || Appraisedd(this)", to = "Inspected(this) || NotionalAcceptance(this)")
    public void markInspected(java.lang.String inspector) {
        if (this.inspector.equals(inspector)) {
            if (this.status.equals(Status.PendingInspection)) {
                this.status = Status.Inspected;
            } else {
                this.status = Status.NotionalAcceptance;
            }
        }
    }

    @repair.regen.specification.StateRefinement(from = "PendingInspection(this) || Inspected(this)", to = "Appraisedd(this) || NotionalAcceptance(this)")
    public void markAppraised(java.lang.String appraiser) {
        if (this.inspector.equals(inspector)) {
            if (this.status.equals(Status.PendingInspection)) {
                this.status = Status.Appraised;
            } else {
                this.status = Status.NotionalAcceptance;
            }
        }
    }

    @repair.regen.specification.StateRefinement(from = "NotionalAcceptance(this)", to = "SellerAccepted(this)")
    public void acceptSeller(java.lang.String seller) {
        if (this.seller.equals(seller)) {
            this.status = Status.SellerAccepted;
        }
    }

    @repair.regen.specification.StateRefinement(from = "NotionalAcceptance(this)", to = "BuyerAccepted(this)")
    public void acceptBuyer(java.lang.String buyer) {
        if (this.buyer.equals(buyer)) {
            this.status = Status.BuyerAccepted;
        }
    }

    @repair.regen.specification.StateRefinement(from = "SellerAccepted(this)", to = "Accepted(this)")
    public void accept(java.lang.String buyer) {
        if (this.buyer.equals(buyer)) {
            this.status = Status.Accepted;
        }
    }
}

