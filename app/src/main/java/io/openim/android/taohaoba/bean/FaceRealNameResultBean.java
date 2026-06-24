package io.openim.android.taohaoba.bean;

public class FaceRealNameResultBean {
    private String taskId;
    private int status;
    private Double similarityScore;
    private int faceMatched;
    private int isPayed;
    private String note;
    private ExtInfo extInfo;

    public class ExtInfo{
        private boolean suspectedNonageFlag;

        public boolean isSuspectedNonageFlag() {
            return suspectedNonageFlag;
        }

        public void setSuspectedNonageFlag(boolean suspectedNonageFlag) {
            this.suspectedNonageFlag = suspectedNonageFlag;
        }

        @Override
        public String toString() {
            return "ExtInfo{" +
                    "suspectedNonageFlag=" + suspectedNonageFlag +
                    '}';
        }
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Double getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(Double similarityScore) {
        this.similarityScore = similarityScore;
    }

    public int getFaceMatched() {
        return faceMatched;
    }

    public void setFaceMatched(int faceMatched) {
        this.faceMatched = faceMatched;
    }

    public int getIsPayed() {
        return isPayed;
    }

    public void setIsPayed(int isPayed) {
        this.isPayed = isPayed;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ExtInfo getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(ExtInfo extInfo) {
        this.extInfo = extInfo;
    }

    @Override
    public String toString() {
        return "FaceRealNameResultBean{" +
                "taskId='" + taskId + '\'' +
                ", status=" + status +
                ", similarityScore=" + similarityScore +
                ", faceMatched=" + faceMatched +
                ", isPayed=" + isPayed +
                ", note='" + note + '\'' +
                ", extInfo=" + extInfo +
                '}';
    }
}
