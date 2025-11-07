package uy.um.edu.pizzumandburgum.dto.response;

public class AuthResponse {
    private String jwt;
    private String rol;
    public AuthResponse(String jwt, String rol) {
        this.jwt = jwt;
        this.rol = rol;
    }
    public String getJwt() {return jwt;}
    public String getRol() {return rol;}
}
