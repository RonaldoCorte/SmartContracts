

@repair.regen.specification.StateSet({ "Requested", "DocumentReview", "AvailableToShare", "SharingRequestPending", "SharingWithThirdParty", "Terminated" })
public class DigitalLocker {
    private java.lang.String document;

    private java.lang.String owner;

    private java.lang.String bankAgendId;

    private java.lang.String thirdPartyRequestor;

    private java.lang.String currentAuthorizedUser;

    private java.lang.String status;

    private boolean isReviewComplete;

    private boolean isDocumentUploaded;

    @repair.regen.specification.StateRefinement(to = "Requested(this)")
    public DigitalLocker(java.lang.String document, java.lang.String owner, java.lang.String bankAgendId) {
        this.owner = owner;
        this.bankAgendId = bankAgendId;
        this.thirdPartyRequestor = null;
        this.currentAuthorizedUser = owner;
        this.document = document;
        this.isReviewComplete = false;
        this.isDocumentUploaded = false;
        this.status = "not_shared";
    }

    @repair.regen.specification.StateRefinement(from = "Requested(this)", to = "DocumentReview(this)")
    public void beginReviewProcess(java.lang.String owner, java.lang.String bankAgendId) {
        if ((this.owner.equals(owner)) && (this.bankAgendId.equals(bankAgendId))) {
            this.isReviewComplete = true;
        }
    }

    @repair.regen.specification.StateRefinement(from = "DocumentReview(this)", to = "AvailableToShare(this)")
    public void uploadDocuments(java.lang.String bankAgendId) {
        if (this.bankAgendId.equals(bankAgendId))
            this.isDocumentUploaded = true;

    }

    @repair.regen.specification.StateRefinement(from = "AvailableToShare(this)", to = "SharingRequestPending(this)")
    public void requestLockerAccess(java.lang.String thirdPartyRequestor) {
        this.thirdPartyRequestor = thirdPartyRequestor;
    }

    @repair.regen.specification.StateRefinement(from = "SharingRequestPending(this)", to = "AvailableToShare(this)")
    public void rejectSharingRequest(java.lang.String owner) {
        this.thirdPartyRequestor = null;
        this.currentAuthorizedUser = null;
        this.status = "not_shared";
    }

    @repair.regen.specification.StateRefinement(from = "SharingRequestPending(this)", to = "SharingWithThirdParty(this)")
    public void acceptSharingRequest(java.lang.String owner) {
        if (this.owner.equals(owner)) {
            this.currentAuthorizedUser = thirdPartyRequestor;
        }
    }

    @repair.regen.specification.StateRefinement(from = "SharingRequestPending(this)", to = "SharingWithThirdParty(this)")
    public void sharingWithThirdParty(java.lang.String thirdPartyRequestor) {
        this.thirdPartyRequestor = thirdPartyRequestor;
        this.currentAuthorizedUser = thirdPartyRequestor;
        this.status = "shared";
    }

    @repair.regen.specification.StateRefinement(from = "SharingWithThirdParty(this)", to = "AvailableToShare(this)")
    public void releaseLockerAccess(java.lang.String thirdPartyRequestor) {
        if (this.thirdPartyRequestor.equals(thirdPartyRequestor)) {
            this.status = "not_shared";
            this.currentAuthorizedUser = null;
        }
    }

    @repair.regen.specification.StateRefinement(from = "AvailableToShare(this) || SharingWithThirdParty(this) || SharingRequestPending(this)", to = "Terminated(this)")
    public void terminate() {
        this.isDocumentUploaded = false;
        this.currentAuthorizedUser = null;
    }
}

