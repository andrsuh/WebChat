package ru.andrey.Domain;


public class Message {
    private Integer id;
    private String content;
    private Integer srcUserID;
    private Integer dstUserID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSrcUserID() {
        return srcUserID;
    }

    public void setSrcUserID(Integer srcUserID) {
        this.srcUserID = srcUserID;
    }

    public Integer getDstUserID() {
        return dstUserID;
    }

    public void setDstUserID(Integer dstUserID) {
        this.dstUserID = dstUserID;
    }
}
