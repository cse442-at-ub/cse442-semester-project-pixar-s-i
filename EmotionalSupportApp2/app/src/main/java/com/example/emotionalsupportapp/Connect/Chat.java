package com.example.emotionalsupportapp.Connect;

public class Chat {
    private String senderId;
    private String senderName;
    private String receiverId;
    private String receiverName;
    private String message;
    private String hasSeen;

    public Chat(String senderId, String senderName, String receiverId, String receiverName, String message, String hasSeen) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.message = message;
        this.hasSeen = hasSeen;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String sender) {
        this.senderId = sender;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setHasSeen(String hasSeen) {
        this.hasSeen = hasSeen;
    }

    public String getHasSeen() {
        return hasSeen;
    }
}
