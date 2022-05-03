import jatyc.lib.Typestate;
import jatyc.lib.Nullable;
import jatyc.lib.Requires;
import jatyc.lib.Ensures;

@Typestate("ItemProtocol")
public class Item {

  private String itemName, seller, buyer;
  private int itemPrice, sellerBalance, buyerBalance;
  private boolean isAvailable;

  public Item(String itemName, int itemPrice, String seller, String buyer, int sellerBalance, int buyerBalance) {
    this.itemName = itemName;
    this.itemPrice = itemPrice;
    this.seller = seller;
    this.buyer = buyer;
    this.sellerBalance = sellerBalance;
    this.buyerBalance = buyerBalance;
    
  }

  private boolean hasBalance(){
    return this.buyerBalance >= this.itemPrice;
  }

  public boolean buyItem(@Requires("ItemListed") @Ensures({"CurrentSaleFinalized"}) final Bazaar bazaar) {
    if(hasBalance()) {
      this.sellerBalance += this.itemPrice;
      this.buyerBalance -= this.itemPrice;
      bazaar.updateBalance(seller, buyer, this.sellerBalance, this.buyerBalance);
      System.out.println("Success");
      return true;
    }
    else {
      bazaar.updateBalance(seller, buyer, this.sellerBalance, this.buyerBalance);
      System.out.println("Fail");
      return false;
    }
  }

}
