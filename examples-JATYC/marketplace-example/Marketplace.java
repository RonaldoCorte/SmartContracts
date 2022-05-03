import jatyc.lib.Typestate;
import jatyc.lib.Nullable;


@Typestate("MarketplaceProtocol")
public class Marketplace {
  private String ownerId;
  private int ownerBalance;
  private String buyerId;
  private int buyerBalance;

  private String item;
  private int itemValue;
  private int offer;
  private boolean isSold;


  public Marketplace(String ownerId, int ownerBalance, String buyerId, int buyerBalance, String item, int itemValue ){
    this.ownerId = ownerId;
    this.ownerBalance = ownerBalance;
    this.buyerId = buyerId;
    this.buyerBalance= buyerBalance;
    this.itemValue = itemValue;
    this.item = item;
    this.offer = 0;
    isSold = false;
  }

  private boolean hasAccess(String credentials, boolean isOwner){
    if(isOwner)
      return credentials.equals(this.ownerId);
    else
      return credentials.equals(this.buyerId);
  }

  public boolean makeOffer(String buyerId, int offerValue){
    if(hasAccess(buyerId,false) && buyerBalance >= offerValue){
      this.offer = offerValue;
      System.out.println("Offer has been made: " +  offerValue);
      return true;
    }
    System.out.println("Invalid offer");
    return false;
  }

  public boolean accept(String ownerId){
    if(hasAccess(ownerId,true)){
      isSold = true;
      ownerBalance += offer;
      buyerBalance -= offer;
      System.out.println("Offer accepted");
      return true;
    }
    System.out.println("Invalid access");
    return false;

  }

  public boolean reject(String ownerId){
    if(hasAccess(ownerId,true)){
      isSold = false;
      System.out.println("Offer rejected");
      return true;
    }
    System.out.println("Invalid access");
    return false;

  }


}
