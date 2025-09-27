package Main; 

import java.awt.Color;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;

/**
 *
 * @author filipe
 */
public class Peca {
    
    private double xIni;
    private double yIni;
    private double largura;
    private double altura;
    private int numero;
 
    private Color cor;
    
    public Peca(double xIni, double yIni, double largura, double altura, Color cor, int numero) {
        this.xIni = xIni;
        this.yIni = yIni;
        this.largura = largura;
        this.altura = altura;
        this.cor = cor;
        this.numero = numero;
    }
    
    public void desenhar(EngineFrame e) {
        e.fillRectangle(xIni, yIni, largura, altura, cor);
        e.drawRectangle(xIni, yIni, largura, altura, e.BLACK);
        e.drawText(String.valueOf(numero) ,xIni + largura / 2 - e.measureText(String.valueOf(numero)) / 2, yIni + altura / 2, 12, e.BLACK );
    }
  
    public double getxIni() {
        return xIni;
    }

    public void setxIni(int xIni) {
        this.xIni = xIni;
    }

    public double getyIni() {
        return yIni;
    }

    public void setyIni(int yIni) {
        this.yIni = yIni;
    }

    public double getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }
    
    

}
