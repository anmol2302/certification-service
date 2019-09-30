package org.sunbird.utilities;

import org.apache.log4j.Logger;
import org.sunbird.JsonKeys;
import org.sunbird.common.ElasticSearchHelper;
import org.sunbird.common.factory.EsClientFactory;
import org.sunbird.common.inf.ElasticSearchService;

import java.util.List;
import java.util.Map;

public class CertificateUtil {
    private static ElasticSearchService elasticSearchService= EsClientFactory.getInstance();
    static Logger logger=Logger.getLogger(CertificateUtil.class);

    public static boolean isIdPresent(String certificateId) {
        logger.info("CertificateUtil:isIdPresent:get id to search in ES:"+certificateId);
        Map<String,Object> response = (Map)ElasticSearchHelper.getResponseFromFuture(elasticSearchService.getDataByIdentifier(JsonKeys.CERT,certificateId));
        logger.info("CertificateUtil:isIdPresent:got response from ES:"+response);
        if (null != response && null != response.get(JsonKeys.RESPONSE)) {
            List responseList = (List) response.get(JsonKeys.RESPONSE);
            if (!responseList.isEmpty()) {
                return true;
            }
        }
        return false;
    }


    public static void insertRecord(Map<String,Object>certAddReqMap){
        ElasticSearchHelper.getResponseFromFuture(elasticSearchService.save(JsonKeys.CERT,(String)certAddReqMap.get(JsonKeys.ID),certAddReqMap));
        logger.info("CertificateUtil:insertRecord: record successfully inserted with id"+certAddReqMap.get(JsonKeys.ID));
    }
}
