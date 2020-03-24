package com.ywcjxf.mall.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("ali.message")
public class AliMessageProperties {
    private String regionId;

    private String accessKeyId;
    private String secret;

    private String domain;
    private String version;

    private String action;

    private String templateCode;
    private String signName;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    @Override
    public String toString() {
        return "AliMessageProperties{" +
                "regionId='" + regionId + '\'' +
                ", accessKeyId='" + accessKeyId + '\'' +
                ", secret='" + secret + '\'' +
                ", domain='" + domain + '\'' +
                ", version='" + version + '\'' +
                ", action='" + action + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", signName='" + signName + '\'' +
                '}';
    }
}
