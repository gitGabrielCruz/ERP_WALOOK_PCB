package com.omcgc.erp.model;

public class SmtpConfig {
    private String proveedor; // gmail, hotmail, custom
    private String host;
    private int port;
    private String username;
    private String password;
    private boolean auth;
    private boolean starttls;

    // Constructor vacío
    public SmtpConfig() {
    }

    // Constructor completo
    public SmtpConfig(String proveedor, String host, int port, String username, String password, boolean auth,
            boolean starttls) {
        this.proveedor = proveedor;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.auth = auth;
        this.starttls = starttls;
    }

    // Getters y Setters
    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public boolean isStarttls() {
        return starttls;
    }

    public void setStarttls(boolean starttls) {
        this.starttls = starttls;
    }
}
