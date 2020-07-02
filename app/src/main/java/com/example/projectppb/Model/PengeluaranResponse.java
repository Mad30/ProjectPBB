package com.example.projectppb.Model;

public class PengeluaranResponse {
    private boolean error;
    private String message;
    private Pengeluaran data;

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Pengeluaran getData() {
        return data;
    }
}
