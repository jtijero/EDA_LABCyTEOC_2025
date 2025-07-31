public class Moneda {
    private int cod;
    private String nom;
    private String sigla;
    private String estado;

    public Moneda() {}

    public Moneda(int cod, String nom, String sigla, String estado) {
        this.cod = cod;
        this.nom = nom;
        this.sigla = sigla;
        this.estado = estado;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return sigla + " - " + nom;
    }
}
