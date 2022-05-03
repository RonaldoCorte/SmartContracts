public class Main {
  public static void main(String[] args) { // error: [iterator] did not complete its protocol
    DigitalLocker dl = new DigitalLocker( "document", "owner", "bankAgendId");
    if(dl.beginReviewProcess("owner", "bankAgendId")){
      if( dl.uploadDocuments("bankAgendId")){
        dl.requestLockerAccess("thirdPartyRequestor");
       if(dl.acceptSharingRequest("owner"))
          dl.releaseLockerAccess("thirdPartyRequestor");

        dl.terminate();

      }
      else {
        dl.terminate();
      }
    }
    else {
      dl.terminate();
    }




    //error doesnt upload documents
    /*DigitalLocker dl = new DigitalLocker( "document", "owner", "bankAgendId");
    dl.beginReviewProcess("owner", "bankAgendId");
    dl.requestLockerAccess("thirdPartyRequestor");
    dl.terminate();*/


  }
}
