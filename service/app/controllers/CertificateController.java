package controllers;

import org.sunbird.JsonKeys;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

/**
 * this controller will help you in understanding the process of passing request to Actors with operation.
 */
public class CertificateController extends BaseController {

    /**
     * this action method will be called for adding certificate
     * @return CompletionStage of Result
     */
    public CompletionStage<Result> add()
    {
        return handleRequest(request(),null, JsonKeys.CERT_ADD);
    }
}
