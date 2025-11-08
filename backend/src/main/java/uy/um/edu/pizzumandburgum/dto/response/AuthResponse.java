package uy.um.edu.pizzumandburgum.dto.response;

public class AuthResponse {
    private String jwt;
    private String rol;
    private Object info;
    public AuthResponse(String jwt, String rol, Object info) {
        this.jwt = jwt;
        this.rol = rol;
        this.info = info;
    }
    public String getJwt() {return jwt;}
    public String getRol() {return rol;}
    public Object getInfo() {return info;}
}
