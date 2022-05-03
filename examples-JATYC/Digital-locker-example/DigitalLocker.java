import jatyc.lib.Typestate;
import jatyc.lib.Nullable;


@Typestate("DigitalLockerProtocol")
public class DigitalLocker {

  private final int OWNER = 1;
  private final int BANK_AGENT = 2;
  private final int THIRD_PARTY_REQUESTOR = 3;


  private String document, owner, bankAgendId, thirdPartyRequestor, currentAuthorizedUser, status;
  private boolean isReviewComplete, isDocumentUploaded;


  public DigitalLocker(String document, String owner, String bankAgendId){
    this.owner = owner;
    this.bankAgendId = bankAgendId;
    this.thirdPartyRequestor = "";
    this.currentAuthorizedUser = owner;
    this.document = document;
    this.isReviewComplete = false;
    this.isDocumentUploaded = false;
    this.status = "not_shared";
  }

  private boolean hasAccess(String credentials, int who){
    switch(who){
      case OWNER:
        return this.owner.equals(credentials);
      case BANK_AGENT:
        return this.bankAgendId.equals(credentials);
      case THIRD_PARTY_REQUESTOR:
        return this.thirdPartyRequestor.equals(credentials);
      default: return false;
    }
  }

  public boolean beginReviewProcess(String owner, String bankAgendId){
    if(hasAccess(owner,OWNER) && hasAccess(bankAgendId,BANK_AGENT)) {
      this.isReviewComplete = true;
      return true;
    }
    else return false;
  }

  public boolean uploadDocuments(String bankAgendId){
    if(hasAccess(bankAgendId,BANK_AGENT)) {
      this.isDocumentUploaded = true;
      return true;
    }
    else return false;
  }

  public void terminate(){
    this.isDocumentUploaded = false;
    this.currentAuthorizedUser = "";
  }

  public void  requestLockerAccess(String thirdPartyRequestor){
    this.thirdPartyRequestor = thirdPartyRequestor;
  }

  public boolean rejectSharingRequest(String owner){
    if(hasAccess(owner,OWNER)) {
      this.thirdPartyRequestor = "";
      this.currentAuthorizedUser = "";
      this.status = "not_shared";
      return true;
    }
    else return false;
  }

  public boolean acceptSharingRequest(String owner){
    if(hasAccess(owner,OWNER)) {
      this.currentAuthorizedUser = thirdPartyRequestor;
      return true;
    }
    else return false;
  }

  public void sharingWithThirdParty(String thirdPartyRequestor){
    this.thirdPartyRequestor = thirdPartyRequestor;
    this.currentAuthorizedUser = thirdPartyRequestor;
    this.status = "shared";

  }

  public boolean releaseLockerAccess(String thirdPartyRequestor){
    if(hasAccess(thirdPartyRequestor,THIRD_PARTY_REQUESTOR)) {
      this.status = "not_shared";
      this.currentAuthorizedUser = "";
      return true;
    }
    else return false;
  }



}
