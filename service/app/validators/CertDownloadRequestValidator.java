package validators;

import org.apache.commons.lang3.StringUtils;
import org.sunbird.BaseException;
import org.sunbird.JsonKeys;
import org.sunbird.message.IResponseMessage;
import org.sunbird.message.ResponseCode;
import org.sunbird.request.Request;

import java.text.MessageFormat;

public class CertDownloadRequestValidator implements IRequestValidator {

    private Request request;
    @Override
    public void validate(Request request) throws BaseException {
        this.request=request;
        validateDownlaodFileData();

    }


    public void validateDownlaodFileData() throws BaseException {
        if (StringUtils.isBlank((String)this.request.getRequest().get("pdfUrl"))) {
            throw new BaseException(IResponseMessage.INVALID_REQUESTED_DATA, MessageFormat.format(IResponseMessage.MISSING_MANADATORY_PARAMS, JsonKeys.PDF_URL), ResponseCode.CLIENT_ERROR.getCode());
        }
    }


}
