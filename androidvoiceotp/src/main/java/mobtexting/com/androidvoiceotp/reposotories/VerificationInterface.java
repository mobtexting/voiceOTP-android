package mobtexting.com.androidvoiceotp.reposotories;

import mobtexting.com.androidvoiceotp.model.ServerResponse;

public interface VerificationInterface {
    void onSuccesResponse(ServerResponse successResponse);
    void onErrorResponse(ServerResponse errorResponse);
}
