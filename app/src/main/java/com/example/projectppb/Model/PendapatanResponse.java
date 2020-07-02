package com.example.projectppb.Model;

public class PendapatanResponse {
    private boolean error;
    private String message;
    private Pendapatan data;
    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Pendapatan getData() {
        return data;
    }



}
