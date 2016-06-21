package com.resume.po.model.rule;

public class HrRules {
    private Long id;

    private Long fromId;

    private String fileType;

    private String matchType;

    private String matchField;

    private String matchPatten;

    private String samplePath;

    private Long adminId;

    private Integer totalMatch;

    private Long addTime;

    private String sampleSrc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType == null ? null : matchType.trim();
    }

    public String getMatchField() {
        return matchField;
    }

    public void setMatchField(String matchField) {
        this.matchField = matchField == null ? null : matchField.trim();
    }

    public String getMatchPatten() {
        return matchPatten;
    }

    public void setMatchPatten(String matchPatten) {
        this.matchPatten = matchPatten == null ? null : matchPatten.trim();
    }

    public String getSamplePath() {
        return samplePath;
    }

    public void setSamplePath(String samplePath) {
        this.samplePath = samplePath == null ? null : samplePath.trim();
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Integer getTotalMatch() {
        return totalMatch;
    }

    public void setTotalMatch(Integer totalMatch) {
        this.totalMatch = totalMatch;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public String getSampleSrc() {
        return sampleSrc;
    }

    public void setSampleSrc(String sampleSrc) {
        this.sampleSrc = sampleSrc == null ? null : sampleSrc.trim();
    }
}