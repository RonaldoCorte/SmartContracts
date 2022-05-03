import repair.regen.specification.Ghost;
import repair.regen.specification.Refinement;
import repair.regen.specification.RefinementAlias;
import repair.regen.specification.StateRefinement;
import repair.regen.specification.StateSet;

@StateSet({"itemAvailable", "offerPlaced", "accept"})
@RefinementAlias("IsValid(int x) { x >= 0 }")
public class Marketplace {

  @Refinement("IsValid(ownerId)")
    private int ownerId;
    @Refinement("IsValid(ownerBalance)")
    private int ownerBalance;
    @Refinement("IsValid(buyerId)")
    private int buyerId;
    @Refinement("IsValid(buyerBalance)")
    private int buyerBalance;
  
    private String item;
    private int itemValue;
    @Refinement("offer >= 0")
    private int offer;
    private boolean isSold;
  
  
    @StateRefinement(to="itemAvailable(this)")
    public Marketplace(@Refinement("IsValid(ownerId)") int ownerId, @Refinement("IsValid(ownerBalance)")  int ownerBalance, @Refinement("IsValid(buyer)") int buyer, @Refinement("IsValid(buyerBalance)")  int buyerBalance, String item,  @Refinement("IsValid(itemValue)") int itemValue ){
      this.ownerId = ownerId;
      this.ownerBalance = ownerBalance;
      this.buyerId = buyer;
      this.buyerBalance= buyerBalance;
      this.itemValue = itemValue;
      this.item = item;
      this.offer = 0;
      isSold = false;
    }

    private boolean hasAccess(@Refinement("IsValid(credentials)") int credentials, boolean isOwner){
      if(isOwner)
        return credentials == (this.ownerId);
      else
        return credentials == (this.buyerId);
    }
    
    @StateRefinement(from = "itemAvailable(this)", to = "itemAvailable(this)")
    @Refinement("_ != 0")
    public int makeOffer(@Refinement("IsValid(buyer)") int buyer, @Refinement("IsValid(offervalue)") int offerValue){
      if(hasAccess(buyer, false)){
        this.offer = offerValue;
        System.out.println("Offer has been made: " +  offerValue);
        return 1;
      }
      else 
        return 0;
    }

 
  
    @StateRefinement(from = "itemAvailable(this)", to = "accept(this)")
    @Refinement("_ >= 0")
    public int accept(@Refinement("IsValid(ownerId)") int ownerId){
      if(hasAccess(ownerId, true)){
        isSold = true;
        ownerBalance += offer;
        buyerBalance -= offer;
        System.out.println("Offer accepted");
        return buyerBalance;
      }
      return -1;
    }
  
    @StateRefinement(from = "itemAvailable(this)", to = "itemAvailable(this)")
    @Refinement("_ > 0")
    public int reject(@Refinement("IsValid(ownerId)") int ownerId){
      if(hasAccess(ownerId, true)){
        isSold = false;
        System.out.println("Offer rejected");
        return 1;
      }
      else
        return 0;
      
    }
    

  
    
}
