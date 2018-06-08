package pt.ipg.marcaoconsultas;



public class Consultas {
    private int idConsultas;
    private String data;
    private String medico;
    private String pacintes;

    public int getIdConsultas() {
        return idConsultas;
    }

    public void setIdConsultas(int idConsultas) {
        this.idConsultas = idConsultas;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getPacintes() {
        return pacintes;
    }

    public void setPacintes(String pacintes) {
        this.pacintes = pacintes;
    }
}
