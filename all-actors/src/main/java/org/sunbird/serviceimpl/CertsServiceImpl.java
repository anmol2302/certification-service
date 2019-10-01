package org.sunbird.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.sunbird.BaseException;
import org.sunbird.JsonKeys;
import org.sunbird.builders.Certificate;
import org.sunbird.builders.Course;
import org.sunbird.message.IResponseMessage;
import org.sunbird.message.ResponseCode;
import org.sunbird.request.Request;
import org.sunbird.response.Response;
import org.sunbird.service.ICertService;
import org.sunbird.utilities.CertificateUtil;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class CertsServiceImpl implements ICertService {
    static Logger logger = Logger.getLogger(CertsServiceImpl.class);
    private static ObjectMapper requestMapper = new ObjectMapper();

    @Override
    public String add(Request request) throws BaseException {
        Map<String, Object> certAddReqMap = new HashMap<>();
        certAddReqMap = request.getRequest();
        assureUniqueCertId((String) certAddReqMap.get(JsonKeys.ID));
        processRecord(certAddReqMap);
        logger.info("CertsServiceImpl:add:record successfully processed with request:"+certAddReqMap);
        return (String)certAddReqMap.get(JsonKeys.ID);
    }

    private void assureUniqueCertId(String certificatedId) throws BaseException {
        if (CertificateUtil.isIdPresent(certificatedId)) {
            logger.error(
                    "CertificateActor:addCertificate:provided certificateId exists in record:" + certificatedId);
            throw new BaseException(IResponseMessage.INVALID_REQUESTED_DATA, IResponseMessage.ID_ALREADY_EXISTS, ResponseCode.CLIENT_ERROR.getCode());
        }
        logger.info("CertificateActor:addCertificate:successfully certId not found in records creating new record");
    }


    private void processRecord(Map<String, Object> certReqAddMap){
        Certificate certificate=getCertificate(certReqAddMap);
        Map<String,Object>recordMap= requestMapper.convertValue(certificate,Map.class);
        recordMap.put(JsonKeys.CREATED_AT,System.currentTimeMillis());
        recordMap.put(JsonKeys.UPDATED_AT,null);
        CertificateUtil.insertRecord(recordMap);
    }
    private Certificate getCertificate(Map<String, Object> certReqAddMap) {
        Certificate certificate = new Certificate.CertificateBuilder()
                .setId((String) certReqAddMap.get(JsonKeys.ID))
                .setCertData(getCertData(certReqAddMap))
                .setPdfUrl((String)certReqAddMap.get(JsonKeys.PDF_URL))
                .setRevoked(false)
                .setAccessCode((String)certReqAddMap.get(JsonKeys.ACCESS_CODE))
                .setJsonUrl((String)certReqAddMap.get(JsonKeys.JSON_URL))
                .setRecipientId((String)certReqAddMap.get(JsonKeys.USER_ID))
                .setCourse(getCompositeCourseObject(certReqAddMap))
                .build();
        return certificate;

    }

    private Course getCompositeCourseObject(Map<String, Object> certAddRequestMap){
        Course course= new Course.CourseBuilder()
                .setBatchId((String)certAddRequestMap.get(JsonKeys.BATCH_ID))
                .setId((String)certAddRequestMap.get(JsonKeys.COURSE_ID))
                .setUserId((String)certAddRequestMap.get(JsonKeys.USER_ID))
                .setCompletionUrl((String)certAddRequestMap.get(JsonKeys.COMPLETION_URL))
                .setIntroUrl((String)certAddRequestMap.get(JsonKeys.INTRO_URL))
                .build();
        return course;
    }

    private Map<String, Object> getCertData(Map<String, Object> certAddRequestMap) {
        return (Map) certAddRequestMap.get(JsonKeys.JSON_DATA);
    }

    @Override
    public Response validate(Request request) throws BaseException {
        Map<String,Object> valCertReq = request.getRequest();
        String certificatedId = (String) valCertReq.get(JsonKeys.CERT_ID);
        String accessCode = (String) valCertReq.get(JsonKeys.ACCESS_CODE);
        Map<String,Object>esCertData=CertificateUtil.getCertificate(certificatedId);
        if (MapUtils.isNotEmpty(esCertData) && StringUtils.equalsIgnoreCase((String)esCertData.get(JsonKeys.ACCESS_CODE),accessCode)) {
            Certificate certificate=getCertObject(esCertData);
            Map<String,Object>responseMap=new HashMap<>();
            responseMap.put(JsonKeys.JSON,certificate.getCertData());
            responseMap.put(JsonKeys.PDF,certificate.getPdfUrl());
            responseMap.put(JsonKeys.COURSE_ID,certificate.getCourse().getId());
            responseMap.put(JsonKeys.BATCH_ID,certificate.getCourse().getBatchId());
            Response response=new Response();
            response.put(JsonKeys.RESPONSE,responseMap);
            return response;
        }
        else{
            logger.error("NO valid record found with provided certificate Id and accessCode respectively:"+certificatedId+":"+accessCode);
            throw new BaseException(IResponseMessage.INVALID_REQUESTED_DATA, MessageFormat.format(IResponseMessage.INVALID_ID_PROVIDED,certificatedId,accessCode), ResponseCode.CLIENT_ERROR.getCode());
        }

    }

    private Certificate getCertObject(Map<String,Object>esCertMap){
        Certificate certificate=requestMapper.convertValue(esCertMap,Certificate.class);
        return certificate;
    }
}
