package com.vela.app.mobile.models.response;

public class CategoriesBaseResponse extends BaseResponse {

    private CategoriesResponse messages;

    public CategoriesResponse getMessages() {
        return messages;
    }

    public void setMessages(CategoriesResponse messages) {
        this.messages = messages;
    }
}
