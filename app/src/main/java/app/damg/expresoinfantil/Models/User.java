package app.damg.expresoinfantil.Models;

/**
 * Created by denys on 14/03/2018.
 */

public class User {

    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getActorRole() {
        return ActorRole;
    }

    public void setActorRole(String actorRole) {
        ActorRole = actorRole;
    }

    private String cedula;
    private String ActorRole;

}
