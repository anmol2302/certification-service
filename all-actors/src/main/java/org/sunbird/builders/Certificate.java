package org.sunbird.builders;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

/**
 * this is a builder class to build a Certificate object needs to be inserted into ES
 * @author anmolgupta
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Certificate {

    private String id;
    private String pdfUrl;
    private String jsonUrl;
    private String recipientId;
    private Map<String, Object> certData;
    private Course course;
    private String createdBy;
    private String updatedBy;
    private boolean isRevoked;
    private String accessCode;

    public Certificate(){}

    private Certificate(CertificateBuilder certificateBuilder) {
        this.id = certificateBuilder.id;
        this.pdfUrl = certificateBuilder.pdfUrl;
        this.jsonUrl = certificateBuilder.jsonUrl;
        this.recipientId = certificateBuilder.recipientId;
        this.certData = certificateBuilder.certData;
        this.course = certificateBuilder.course;
        this.createdBy = certificateBuilder.createdBy;
        this.updatedBy = certificateBuilder.updatedBy;
        this.isRevoked = certificateBuilder.isRevoked;
        this.accessCode=certificateBuilder.accessCode;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public String getId() {
        return id;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public String getJsonUrl() {
        return jsonUrl;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public Map<String, Object> getCertData() {
        return certData;
    }

    public Course getCourse() {
        return course;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public boolean isRevoked() {
        return isRevoked;
    }

    public static class CertificateBuilder{


        private String id;
        private String pdfUrl;
        private String jsonUrl;
        private String recipientId;
        private Map<String, Object> certData;
        private Course course;
        private String createdBy;
        private String updatedBy;
        private boolean isRevoked;
        private String accessCode;

        public CertificateBuilder setAccessCode(String accessCode) {
            this.accessCode = accessCode;
            return this;
        }

        public CertificateBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public CertificateBuilder setPdfUrl(String pdfUrl) {
            this.pdfUrl = pdfUrl;
            return this;

        }

        public CertificateBuilder setJsonUrl(String jsonUrl) {
            this.jsonUrl = jsonUrl;
            return this;

        }

        public CertificateBuilder setRecipientId(String recipientId) {
            this.recipientId = recipientId;
            return this;

        }

        public CertificateBuilder setCertData(Map<String, Object> certData) {
            this.certData = certData;
            return this;

        }

        public CertificateBuilder setCourse(Course course) {
            this.course = course;
            return this;

        }

        public CertificateBuilder setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
            return this;

        }

        public CertificateBuilder setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
            return this;

        }


        public CertificateBuilder setRevoked(boolean revoked) {
            isRevoked = revoked;
            return this;

        }
        public Certificate build(){
            Certificate certificate=new Certificate(this);
            return certificate;

        }
    }




}
