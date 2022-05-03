import repair.regen.specification.StateRefinement;
import repair.regen.specification.StateSet;

@StateSet({"Active", "OfferPlaced", "PendingInspection", "Inspected", "Appraised", "NotionalAcceptance", "SellerAccepted", "BuyerAccepted", "Accepted", "Terminated"})
public class AssetTransfer {

  private String seller, buyer, inspector, appraiser, descripiton;
  private int askingPrice, offerPrice;
  private Status status;

  @StateRefinement( to="Active(this)")
  public AssetTransfer(String seller, String buyer,String inspector,String appraiser, String descripiton, int askingPrice){
    this.seller = seller;
    this.buyer = buyer;
    this.inspector = inspector;
    this.appraiser = appraiser;
    this.offerPrice = 0;
    this.descripiton = descripiton;
    this.askingPrice = askingPrice; 
    this.status = Status.Active;
  }

  @StateRefinement(from="Active(this)", to="Active(this)")
  public void modify(String owner,String descripiton, int price){
    if(this.seller.equals(owner)){
      this.descripiton = descripiton;
      this.offerPrice = price;
      this.status = Status.Active;
    }
  }


  @StateRefinement(from="OfferPlaced(this)", to="OfferPlaced(this)")
  public void modifyOffer(String buyer, int newPrice){
    if(this.buyer.equals(buyer)){
      this.offerPrice = newPrice;
      this.status = Status.OfferPlaced;
    }
  }

@StateRefinement(from="Active(this) ||OfferPlaced(this) || PendingInspection(this) || Inspected(this) || Appraised(this) || NotionalAcceptance(this) || SellerAccepted(this) || BuyerAccepted(this)", to="Terminated(this)")
  public void terminate(String owner){
    if(this.seller.equals(owner)){
      this.status = Status.Terminated;
    }
  }

  @StateRefinement(from="Active(this)", to="OfferPlaced(this)")
  public void makeOffer(String buyer, String inspector, String appraiser, int offerPrice){
    if( this.buyer.equals(buyer) && this.inspector.equals(inspector) && this.appraiser.equals(appraiser)) {
      this.offerPrice = offerPrice;
      this.status = Status.OfferPlaced;
    }

  }

  @StateRefinement(from="OfferPlaced(this)", to="Active(this)")
  public void rejectOffer(String seller){
     if(this.seller.equals(seller)) {
       this.status = Status.Active;
     }
  }

  @StateRefinement(from="OfferPlaced(this)", to="PendingInspection(this)")
  public void acceptOffer(String seller){
    if(this.seller.equals(seller))
      this.status = Status.PendingInspection;
  }

  @StateRefinement(from="OfferPlaced(this) || PendingInspection(this) || Inspected(this) || Appraised(this) || NotionalAcceptance(this) || SellerAccepted(this)", to="Active(this)")
  public void rescindOffer(String seller){
    if(this.seller.equals(seller)) {
      this.offerPrice = 0;
      this.status = Status.Active;
    }
  }

  @StateRefinement(from="PendingInspection(this) || Appraisedd(this)", to="Inspected(this) || NotionalAcceptance(this)")
  public void markInspected(String inspector){
    if(this.inspector.equals(inspector)){
      if(this.status.equals(Status.PendingInspection)){
        this.status = Status.Inspected;
      }
      else{
        this.status = Status.NotionalAcceptance;
      }
    }
  }

  @StateRefinement(from="PendingInspection(this) || Inspected(this)", to="Appraisedd(this) || NotionalAcceptance(this)")
  public void markAppraised(String appraiser){
    if(this.inspector.equals(inspector)){
      if(this.status.equals(Status.PendingInspection)){
        this.status = Status.Appraised;
      }
      else{
        this.status = Status.NotionalAcceptance;
      }
    }
  }

  @StateRefinement(from="NotionalAcceptance(this)", to="SellerAccepted(this)")
  public void acceptSeller(String seller){
      if(this.seller.equals(seller)){
        this.status = Status.SellerAccepted;
      }
  }

  @StateRefinement(from="NotionalAcceptance(this)", to="BuyerAccepted(this)")
  public void acceptBuyer(String buyer){
    if(this.buyer.equals(buyer)){
      this.status = Status.BuyerAccepted;
    }
  }


  @StateRefinement(from="SellerAccepted(this)", to="Accepted(this)")
  public void accept(String buyer){
    if(this.buyer.equals(buyer)){
      this.status = Status.Accepted;
    }
  }






}
