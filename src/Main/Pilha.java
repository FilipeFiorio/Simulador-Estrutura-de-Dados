package Main;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
import br.com.davidbuzatto.jsge.imgui.GuiLabel;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de projeto básico da JSGE.
 *
 * JSGE basic project template.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Pilha extends EngineFrame {

    private int tamanho;
    private final int LIMITE_PILHA = 10;
    private double timer = 0.0;
    private final double tempoMax = 1.0;
    private boolean mostrarAvisoLimite = false;
    private boolean mostrarAvisoVazio = false;
    private boolean mostrarLabelExplicacao = false;

    private GuiButton botaoPop;
    private GuiButton botaoPush;
    private GuiButton botaoLimpar;
    private GuiButton botaoSimulacaoRapida;
    private GuiButton botaoComoFunciona;
    private GuiButton botaoFecharExplicacao;

    private GuiLabel labelTamanho;

    private List<Peca> pecas;
    private List<GuiComponent> guiComponentes;

    public Pilha() {

        super(
                800, // largura                      / width
                620, // algura                       / height
                "Simulação Pilha", // título                       / title
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

        guiComponentes = new ArrayList<>();
        pecas = new ArrayList<>();

        botaoPush = new GuiButton(570, 200, 150, 40, "PUSH");
        botaoPop = new GuiButton(570, 250, 150, 40, "POP");
        botaoLimpar = new GuiButton(570, 300, 150, 40, "LIMPAR");
        botaoSimulacaoRapida = new GuiButton(570, 350, 150, 40, "SIMULAÇÃO RÁPIDA");
        botaoComoFunciona = new GuiButton(570, 400, 150, 40, "COMO FUNCIONA?");
        botaoFecharExplicacao = new GuiButton(345, 420, 100, 40, "FECHAR");

        labelTamanho = new GuiLabel(500, 100, 100, 40, "Tamanho: " + String.valueOf(tamanho));

        guiComponentes.add(botaoPop);
        guiComponentes.add(botaoPush);
        guiComponentes.add(botaoLimpar);
        guiComponentes.add(botaoComoFunciona);
        guiComponentes.add(botaoSimulacaoRapida);

        guiComponentes.add(labelTamanho);
    }

    @Override
    public void update(double delta) {

        for (GuiComponent g : guiComponentes) {
            g.update(delta);
        }

        botaoFecharExplicacao.update(delta);

        if (botaoPush.isMousePressed()) {
            push();
        }

        if (botaoPop.isMousePressed()) {
            pop();
        }

        if (mostrarAvisoLimite) {
            timer += delta;
            if (timer >= tempoMax) {
                mostrarAvisoLimite = false;
            }
        }

        if (mostrarAvisoVazio) {
            timer += delta;
            if (timer >= tempoMax) {
                mostrarAvisoVazio = false;
            }
        }

        if (botaoComoFunciona.isMousePressed()) {
            botaoComoFunciona.setEnabled(false);
            mostrarLabelExplicacao = true;
            mostrarBotaoFechar = true;
        }

        if (botaoFecharExplicacao.isMousePressed()) {
            mostrarLabelExplicacao = false;
            mostrarBotaoFechar = false;
            botaoComoFunciona.setEnabled(true);
        }
    }

    @Override
    public void draw() {

        clearBackground(WHITE);

        for (GuiComponent g : guiComponentes) {
            g.draw();
        }

        drawRectangle(140, 160, 144, 340, BLACK);

        if (mostrarAvisoLimite) {
            fillRectangle(225, 140, 355, 40, GRAY);
            fillRectangle(220, 135, 355, 40, WHITE);
            drawRectangle(220, 135, 355, 40, BLACK);
            drawText("LIMITE DA PILHA EXCEDIDO!", 225, 150, 24, BLACK);
        }

        if (mostrarAvisoVazio) {
            fillRectangle(267, 140, 272, 40, GRAY);
            fillRectangle(262, 135, 272, 40, WHITE);
            drawRectangle(262, 135, 272, 40, BLACK);
            drawText("A PILHA ESTÁ VAZIA!", 267, 150, 24, BLACK);
        }

        if (mostrarLabelExplicacao) {
            fillRectangle(225, 185, 340, 300, GRAY);
            fillRectangle(220, 180, 340, 300, WHITE);
            drawRectangle(220, 180, 340, 300, BLACK);

            drawText(
                    """
                A pilha é uma estrutura de 
                dados onde o último elemento 
                a entrar é sempre o primeiro 
                a sair. Esse comportamento 
                é chamado de "Last In, First
                Out" (LIFO), em português: 
                Último a entrar, primeiro
                a sair.
                """,
                    235,
                    210,
                    18,
                    BLACK
            );

            botaoFecharExplicacao.draw();

        }
    }

    private void pop() {
        if (tamanho < 1) {
            mostrarAvisoVazio = true;
            timer = 0.0;
            return;
        }

        labelTamanho.setText("Tamanho: " + String.valueOf(--tamanho));
    }

    private void push() {
        if (tamanho >= LIMITE_PILHA) {
            mostrarAvisoLimite = true;
            timer = 0.0;
            return;
        }

        labelTamanho.setText("Tamnaho: " + String.valueOf(++tamanho));

    }

    public static void main(String[] args) {
        new Pilha();

    }

}
