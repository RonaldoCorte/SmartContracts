import jatyc.lib.Typestate;
import jatyc.lib.Nullable;


@Typestate("AssetTransferProtocol")
public class AssetTransfer {

  private final int SELLER = 0;
  private final int BUYER = 1;
  private final int INSPECTOR = 2;
  private final int APPRAISER = 3;

  private String seller, buyer, inspector, appraiser, descripiton;
  private int askingPrice, offerPrice;
  private Status status;

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

  private boolean hasAccess(String credentials, int who) {
    switch (who) {
      case SELLER:
        return this.seller.equals(credentials);
      case BUYER:
        return this.buyer.equals(credentials);
      case INSPECTOR:
        return this.inspector.equals(credentials);
      case APPRAISER:
        return this.appraiser.equals(credentials);
      default:
        return false;
    }
  }

  public void modify(String owner,String descripiton, int price){
    if(hasAccess(owner,SELLER)){
      this.descripiton = descripiton;
      this.offerPrice = price;
      this.status = Status.Active;
    }
  }

  public void modifyOffer(String buyer, int newPrice){
    if(hasAccess(buyer,BUYER)){
      this.offerPrice = newPrice;
      this.status = Status.OfferPlaced;
    }
  }

  public boolean terminate(String owner){
    if(hasAccess(owner,SELLER)){
      this.status = Status.Terminated;
      return true;
    }
    else return false;
  }

  public boolean makeOffer(String buyer, String inspector, String appraiser, int offerPrice){
    if(hasAccess(buyer,BUYER) && hasAccess(inspector,INSPECTOR) && hasAccess(appraiser,APPRAISER)){
      this.offerPrice = offerPrice;
      this.status = Status.OfferPlaced;
      return true;
    }
    else return false;

  }

  public boolean rejectOffer(String seller){
     if( hasAccess(seller,SELLER)) {
       this.status = Status.Active;
       return true;
     }
     else return false;
  }

  public boolean acceptOffer(String seller){
    if(hasAccess(seller,SELLER)){
      this.status = Status.PendingInspection;
      return true;
    } else return false;
  }

  public boolean rescindOffer(String seller){
    if(hasAccess(seller,SELLER)) {
      this.offerPrice = 0;
      this.status = Status.Active;
      return true;
    }
    else return false;
  }

  public boolean markInspected(String inspector){
    if(hasAccess(inspector,INSPECTOR)){
      if(this.status.equals(Status.PendingInspection)){
        this.status = Status.Inspected;
      }
      else{
        this.status = Status.NotionalAcceptance;
      }
      return true;
    }
    else return false;
  }

  public boolean markAppraised(String appraiser){
    if(hasAccess(appraiser,APPRAISER)){
      if(this.status.equals(Status.PendingInspection)){
        this.status = Status.Appraised;
      }
      else{
        this.status = Status.NotionalAcceptance;
      }
      return true;
    }
    else return false;
  }

  public boolean acceptSeller(String seller){
      if(hasAccess(seller,SELLER)){
        this.status = Status.SellerAccepted;
        return true;
      }
      else return false;
  }

  public boolean acceptBuyer(String buyer){
    if(hasAccess(buyer,BUYER)){
      this.status = Status.BuyerAccepted;
      return true;
    }
    else
      return false;
  }

  public boolean accept(String buyer){
    if(hasAccess(buyer,BUYER)){
      this.status = Status.Accepted;
      return true;
    }
    else return false;
  }






}
