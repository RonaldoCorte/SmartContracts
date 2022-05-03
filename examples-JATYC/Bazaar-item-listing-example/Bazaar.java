import jatyc.lib.Typestate;
import jatyc.lib.Nullable;
import jatyc.lib.Ensures;

@Typestate("BazaarProtocol")
public class Bazaar {

  private String partyA, partyB;
  private int partyABalance, partyBBalance;

  public Bazaar(String partyA, String partyB, int partyABalance, int partyBBalance) {
    this.partyA = partyA;
    this.partyB = partyB;
    this.partyABalance = partyABalance;
    this.partyBBalance = partyBBalance;
  }

  private boolean hasAccess(String credentials, boolean isPartyA){
    if(isPartyA)
      return this.partyA.equals(credentials);
    else
      return this.partyB.equals(credentials);
  }

  public void updateBalance(String seller, String buyer, int newSellerBalance, int newBuyerBalance) {
    if(hasAccess(seller,true)){
      this.partyABalance = newSellerBalance;
      this.partyBBalance = newBuyerBalance;
    }
    else if(hasAccess(seller,false)){
      this.partyABalance = newBuyerBalance;
      this.partyBBalance = newSellerBalance;

    }
  }

  public @Ensures("ItemAvailable") Item listItem(String itemName, int itemPrice, String currentSeller) {
    if(hasAccess(currentSeller,true))
      return new Item(itemName, itemPrice, partyA, partyB, partyABalance, partyBBalance);
    else
      return new Item(itemName, itemPrice, partyB, partyA, partyBBalance, partyABalance);
  }

  public void close(){
    this.partyA = "";
    this.partyB = "";
    this.partyABalance = 0;
    this.partyBBalance = 0;
  }

}
