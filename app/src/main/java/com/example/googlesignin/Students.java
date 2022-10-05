package com.example.googlesignin;

public class Students {

    String erp;
    String email;
    String cla;

    public Students(String erp, String email, String cla) {
        this.erp = erp;
        this.email = email;
        this.cla = cla;
    }

    public String getErp() {
        return erp;
    }

    public String getEmail() {
        return email;
    }

    public String getCla() {
        return cla;
    }
}
