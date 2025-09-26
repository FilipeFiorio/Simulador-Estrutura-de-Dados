package Main;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de projeto básico da JSGE.
 *
 * JSGE basic project template.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Deque extends EngineFrame {

    private int tamanho;
    private String strTamanho = "Tamanho: " + tamanho;
    private final int LIMITE_FILA = 10;
    private double timer = 0.0;
    private double tempoMax = 1.0;

    private boolean mostrarAvisoLimite = false;
    private boolean mostrarAvisoVazio = false;
    private boolean mostrarExplicacao = false;

    private GuiButton botaoEnqueue;
    private GuiButton botaoDequeue;
    private GuiButton botaoLimpar;
    private GuiButton botaoSimulacaoRapida;
    private GuiButton botaoComoFunciona;
    private GuiButton botaoFecharExplicacao;
    
    private Color corFundoEscurecido;

    private List<GuiComponent> listaBotoes;

    public Deque() {

        super(
                800, // largura                      / width
                620, // algura                       / height
                "Simulação Fila", // título                       / title
                60, // quadros por segundo desejado / target FPS
                true, // suavização                   / antialiasing
                false, // redimensionável              / resizable
                false, // tela cheia                   / full screen
                false, // sem decoração                / undecorated
                false, // sempre no topo               / always on top
                false // fundo invisível              / invisible background
        );

    }

    @Override
    public void create() {

        useAsDependencyForIMGUI();

        listaBotoes = new ArrayList<>();

        botaoEnqueue = new GuiButton(570, 200, 150, 40, "ENQUEUE");
        botaoDequeue = new GuiButton(570, 250, 150, 40, "DEQUEUE");
        botaoLimpar = new GuiButton(570, 300, 150, 40, "LIMPAR");
        botaoSimulacaoRapida = new GuiButton(570, 350, 150, 40, "SIMULAÇÃO RÁPIDA");
        botaoComoFunciona = new GuiButton(570, 400, 150, 40, "COMO FUNCIONA?");
        botaoFecharExplicacao = new GuiButton(345, 420, 100, 40, "FECHAR");
        
        corFundoEscurecido = new Color(40, 40, 40, 40);

        listaBotoes.add(botaoEnqueue);
        listaBotoes.add(botaoDequeue);
        listaBotoes.add(botaoComoFunciona);
        listaBotoes.add(botaoLimpar);
        listaBotoes.add(botaoSimulacaoRapida);

    }

    @Override
    public void update(double delta) {

        for (GuiComponent b : listaBotoes) {
            b.update(delta);
            b.setBackgroundColor(new Color(0, 128, 0));
            b.setTextColor(WHITE);
        }
        
        botaoFecharExplicacao.update(delta);
        botaoFecharExplicacao.setBackgroundColor(new Color(0, 128, 0));
        botaoFecharExplicacao.setTextColor(WHITE);

        if (botaoEnqueue.isMousePressed()) {
            enqueue();
        }

        if (botaoDequeue.isMousePressed()) {
            dequeue();
        }
        
        if(botaoLimpar.isMousePressed()) {
            limpar();
        }
        
        if (mostrarAvisoLimite) {
            botaoEnqueue.setEnabled(false);
            timer += delta;
            if (timer >= tempoMax) {
                mostrarAvisoLimite = false;
                botaoEnqueue.setEnabled(true);
            }
        }

        if (mostrarAvisoVazio) {
            botaoDequeue.setEnabled(false);
            timer += delta;
            if (timer >= tempoMax) {
                mostrarAvisoVazio = false;
                botaoDequeue.setEnabled(true);
            }
        }

        if (botaoComoFunciona.isMousePressed()) {
            botaoComoFunciona.setEnabled(false);
            mostrarExplicacao = true;
        }

        if (botaoFecharExplicacao.isMousePressed()) {
            mostrarExplicacao = false;
            botaoComoFunciona.setEnabled(true);
        }
        
    }

    @Override
    public void draw() {

        clearBackground(WHITE);

        for (GuiComponent b : listaBotoes) {
            b.draw();
        }

        drawRectangle(80, 240, 400, 144, BLACK);
        
        if (mostrarAvisoLimite) {
            fillRectangle(0, 0, 800, 620, corFundoEscurecido);
            fillRectangle(225, 140, 355, 40, GRAY);
            fillRectangle(220, 135, 355, 40, WHITE);
            drawRectangle(220, 135, 355, 40, BLACK);
            drawText("LIMITE DA FILA EXCEDIDO!", 225, 150, 24, BLACK);
        }

        if (mostrarAvisoVazio) {
            fillRectangle(0, 0, 800, 620, corFundoEscurecido);
            fillRectangle(267, 140, 272, 40, GRAY);
            fillRectangle(262, 135, 272, 40, WHITE);
            drawRectangle(262, 135, 272, 40, BLACK);
            drawText("A FILA ESTÁ VAZIA!", 267, 150, 24, BLACK);
        }

        if (mostrarExplicacao) {
            fillRectangle(0, 0, 800, 620, corFundoEscurecido);
            fillRectangle(220, 185, 350, 300, GRAY);
            fillRectangle(215, 180, 350, 300, WHITE);
            drawRectangle(215, 180, 350, 300, BLACK);

            drawText(
                    """
                    A  fila  é  uma  estrutura  de 
                    dados onde o primeiro elemento 
                    a entrar é sempre  o  primeiro 
                    a  sair.  Esse   comportamento 
                    é chamado de "First In,  First
                    Out"  (FIFO),  em   português: 
                    Primeiro  a  entrar,  primeiro
                    a sair.
                    """,
                    227,
                    210,
                    18,
                    BLACK
            );
            
            botaoFecharExplicacao.draw();

        }
        
        drawText(strTamanho, 585, 100, 20, BLACK);
        
    }
    
    private void enqueue() {
        if(tamanho >= LIMITE_FILA) {
            mostrarAvisoLimite = true;
            timer = 0.0;
            return;
        }
        
        strTamanho = "Tamanho: " + ++tamanho;
        
    }

    private void dequeue() {
        if(tamanho < 1) {
            mostrarAvisoVazio = true;
            timer = 0.0;
            return;
        }
        
        strTamanho = "Tamanho: " + --tamanho;
    
    }
    
    private void limpar() {
        while(tamanho > 0) {
            dequeue();
        }
    }

    public static void main(String[] args) {
        new Deque();
    }

}
