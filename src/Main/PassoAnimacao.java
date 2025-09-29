package Main;

/**
 *
 * @author filipe
 */

public class PassoAnimacao {
    
    private Peca peca;
    
    private double xIni; 
    private double yIni;
    private double xFinal;
    private double yFinal;
    
    private double tempo;
    private double tempoAtual;
    
    private boolean finalizada = false;

    public PassoAnimacao(Peca peca, double xIni, double yIni, double xFinal, double yFinal, double tempo) {
        this.peca = peca;
        this.xIni = xIni;
        this.yIni = yIni;
        this.xFinal = xFinal;
        this.yFinal = yFinal;
        this.tempo = tempo;
        this.tempoAtual = 0.0;
    }

    void atualizar(double delta) {
        if (finalizada) return;

        tempoAtual += delta;
        double t = Math.min(1.0, tempoAtual / tempo);

        double xNovo = xIni + (xFinal - xIni) * t;
        double yNovo = yIni + (yFinal - yIni) * t;

        peca.setxIni(xNovo);
        peca.setyIni(yNovo);

        if (t >= 1.0) {
            finalizada = true;
        }
    }

    boolean isFinalizada() {
        return finalizada;
    }

    Peca getPeca() {
        return peca;
    }

    public double getxIni() {
        return xIni;
    }

    public void setxIni(double xIni) {
        this.xIni = xIni;
    }

    public double getyIni() {
        return yIni;
    }

    public void setyIni(double yIni) {
        this.yIni = yIni;
    }

    public double getxFinal() {
        return xFinal;
    }

    public void setxFinal(double xFinal) {
        this.xFinal = xFinal;
    }

    public double getyFinal() {
        return yFinal;
    }

    public void setyFinal(double yFinal) {
        this.yFinal = yFinal;
    }

    public double getTempo() {
        return tempo;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public double getTempoAtual() {
        return tempoAtual;
    }

    public void setTempoAtual(double tempoAtual) {
        this.tempoAtual = tempoAtual;
    }
    
}
