import repair.regen.specification.Refinement;
import repair.regen.specification.RefinementAlias;
import repair.regen.specification.StateRefinement;
import repair.regen.specification.StateSet;

@StateSet({"Requested", "DocumentReview", "AvailableToShare", "SharingRequestPending", "SharingWithThirdParty", "Terminated"})
@RefinementAlias("IsValid(int x) { x >= 0 }")
public class DigitalLocker {

  private String document, status;
  @Refinement("IsValid(owner)") 
  private int  owner;
  @Refinement("IsValid(bankAgendId)") 
  private int bankAgendId;
  @Refinement("IsValid(thirdPartyRequestor)") 
  private int thirdPartyRequestor;
  @Refinement("IsValid(currentAuthorizedUser)") 
  private int currentAuthorizedUser;
  private boolean isReviewComplete, isDocumentUploaded;


  @StateRefinement( to="Requested(this)")
  public DigitalLocker(String document, @Refinement("IsValid(owner)") int owner, @Refinement("IsValid(bankAgendId)") int bankAgendId){
    this.owner = owner;
    this.bankAgendId = bankAgendId;
    this.thirdPartyRequestor = 0;
    this.currentAuthorizedUser = owner;
    this.document = document;
    this.isReviewComplete = false;
    this.isDocumentUploaded = false;
    this.status = "not_shared";
  }

  @StateRefinement(from="Requested(this)", to="DocumentReview(this)")
  public void beginReviewProcess(  @Refinement("IsValid(owner)") int owner,   @Refinement("IsValid(bankAgendId)") int bankAgendId){
    if(this.owner == (owner) && this.bankAgendId == (bankAgendId)) {
      this.isReviewComplete = true;
    }
  }

  @StateRefinement(from="DocumentReview(this)", to="AvailableToShare(this)")
  public void uploadDocuments( @Refinement("IsValid(bankAgendId)") int bankAgendId){
    if(this.bankAgendId == (bankAgendId))
      this.isDocumentUploaded = true;
  }


  @StateRefinement(from="AvailableToShare(this)", to="SharingRequestPending(this)")
  public void  requestLockerAccess( @Refinement("IsValid(thirdPartyRequestor)") int thirdPartyRequestor){
    this.thirdPartyRequestor = thirdPartyRequestor;
  }

  @StateRefinement(from="SharingRequestPending(this)", to="AvailableToShare(this)")
  public void rejectSharingRequest( @Refinement("IsValid(owner)") int owner){
    this.thirdPartyRequestor = 0;
    this.currentAuthorizedUser = 0;
    this.status = "not_shared";
  }

  @StateRefinement(from="SharingRequestPending(this)", to="SharingWithThirdParty(this)")
  public void acceptSharingRequest( @Refinement("IsValid(owner)") int owner){
    if(this.owner == (owner)) {
      this.currentAuthorizedUser = thirdPartyRequestor;
    }
  }

  @StateRefinement(from="SharingRequestPending(this)", to="SharingWithThirdParty(this)")
  public void sharingWithThirdParty( @Refinement("IsValid(thirdPartyRequestor)") int thirdPartyRequestor){
    this.thirdPartyRequestor = thirdPartyRequestor;
    this.currentAuthorizedUser = thirdPartyRequestor;
    this.status = "shared";

  }

  @StateRefinement(from="SharingWithThirdParty(this)", to="AvailableToShare(this)")
  public void releaseLockerAccess( @Refinement("IsValid(thirdPartyRequestor)") int thirdPartyRequestor){
    if(this.thirdPartyRequestor == (thirdPartyRequestor)) {
      this.status = "not_shared";
      this.currentAuthorizedUser = 0;
    }
  }


  @StateRefinement(from="AvailableToShare(this) || SharingWithThirdParty(this) || SharingRequestPending(this)", to="Terminated(this)")
  public void terminate(){
    this.isDocumentUploaded = false;
    this.currentAuthorizedUser = 0;
  }

}
