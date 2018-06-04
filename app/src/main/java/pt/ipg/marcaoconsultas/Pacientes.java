package pt.ipg.marcaoconsultas;

public class Pacientes {

    private int idPacinte;
    private String nome;
    private String sexo;
    private String email;
    private int telemovel;
    private int nascimento;

    public int getIdPacinte() {
        return idPacinte;
    }

    public void setIdPacinte(int idPacinte) {
        this.idPacinte = idPacinte;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(int telemovel) {
        this.telemovel = telemovel;
    }

    public int getNascimento() {
        return nascimento;
    }

    public void setNascimento(int nascimento) {
        this.nascimento = nascimento;
    }
}
