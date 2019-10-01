package org.sunbird.utilities;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.sunbird.JsonKeys;
import org.sunbird.builders.Certificate;
import org.sunbird.common.ElasticSearchHelper;
import org.sunbird.common.factory.EsClientFactory;
import org.sunbird.common.inf.ElasticSearchService;
import org.sunbird.dto.SearchDTO;
import org.sunbird.response.Response;

import java.util.Map;

public class CertificateUtil {
    private static ElasticSearchService elasticSearchService= EsClientFactory.getInstance();
    static Logger logger=Logger.getLogger(CertificateUtil.class);

    public static boolean isIdPresent(String certificateId) {
        logger.info("CertificateUtil:isIdPresent:get id to search in ES:"+certificateId);
        Map<String,Object> response = (Map)ElasticSearchHelper.getResponseFromFuture(elasticSearchService.getDataByIdentifier(JsonKeys.CERT,certificateId));
        logger.info("CertificateUtil:isIdPresent:got response from ES:"+response);
        if (MapUtils.isNotEmpty(response)) {
                return true;
        }
        return false;
    }


    public static void insertRecord(Map<String,Object>certAddReqMap){
        ElasticSearchHelper.getResponseFromFuture(elasticSearchService.save(JsonKeys.CERT,(String)certAddReqMap.get(JsonKeys.ID),certAddReqMap));
        logger.info("CertificateUtil:insertRecord: record successfully inserted with id"+certAddReqMap.get(JsonKeys.ID));
    }


    public static  Map<String,Object> getCertificate(SearchDTO searchDTO) {
        logger.info("CertificateUtil:isIdPresent:get id to search in ES:"+searchDTO);
        Map<String,Object> response = (Map)ElasticSearchHelper.getResponseFromFuture(elasticSearchService.search(searchDTO,JsonKeys.CERT));
        logger.info("CertificateUtil:isIdPresent:got response from ES:"+response);
        return response;
    }
    public static  Map<String,Object> getCertificate(String certificateId) {
        logger.info("CertificateUtil:isIdPresent:get id to search in ES:"+certificateId);
        Map<String,Object> response = (Map)ElasticSearchHelper.getResponseFromFuture(elasticSearchService.getDataByIdentifier(JsonKeys.CERT,certificateId));
        logger.info("CertificateUtil:isIdPresent:got response from ES:"+response);
        return response;
    }
}
