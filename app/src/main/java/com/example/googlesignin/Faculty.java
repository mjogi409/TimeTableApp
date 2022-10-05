package com.example.googlesignin;

public class Faculty {
    String erp;
    String email;
    String sittings;

    public Faculty(String erp, String email, String sittings) {
        this.erp = erp;
        this.email = email;
        this.sittings = sittings;
    }

    public String getErp() {
        return erp;
    }

    public String getEmail() {
        return email;
    }

    public String getSittings() {
        return sittings;
    }
}
