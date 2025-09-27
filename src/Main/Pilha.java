package Main;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Modelo de projeto básico da JSGE.
 *
 * JSGE basic project template.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Pilha extends EngineFrame {

    private int tamanho = 0;
    private String strTamanho = "Tamanho: " + tamanho;
    private final int LIMITE_PILHA = 10;
    private double timer = 0.0;
    private final double tempoMax = 1.0;
    private boolean mostrarAvisoLimite = false;
    private boolean mostrarAvisoVazio = false;
    private boolean mostrarExplicacao = false;

    private GuiButton botaoPop;
    private GuiButton botaoPush;
    private GuiButton botaoLimpar;
    private GuiButton botaoSimulacaoRapida;
    private GuiButton botaoComoFunciona;
    private GuiButton botaoFecharExplicacao;

    private Color corFundoEscurecido;

    private List<GuiComponent> listaBotoes;
    private List<Peca> pecas;
    private List<Operacao> chamadasMetodos;

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

        listaBotoes = new ArrayList<>();
        pecas = new ArrayList<>();
        chamadasMetodos = new ArrayList<>();

        botaoPush = new GuiButton(570, 200, 150, 40, "PUSH");
        botaoPop = new GuiButton(570, 250, 150, 40, "POP");
        botaoLimpar = new GuiButton(570, 300, 150, 40, "LIMPAR");
        botaoSimulacaoRapida = new GuiButton(570, 350, 150, 40, "SIMULAÇÃO RÁPIDA");
        botaoComoFunciona = new GuiButton(570, 400, 150, 40, "COMO FUNCIONA?");
        botaoFecharExplicacao = new GuiButton(345, 420, 100, 40, "FECHAR");

        corFundoEscurecido = new Color(40, 40, 40, 40);

        listaBotoes.add(botaoPop);
        listaBotoes.add(botaoPush);
        listaBotoes.add(botaoLimpar);
        listaBotoes.add(botaoComoFunciona);
        listaBotoes.add(botaoSimulacaoRapida);

    }

    @Override
    public void update(double delta) {

            for (GuiComponent b : listaBotoes) {
                b.update(delta);
                b.setTextColor(WHITE);
                b.setBackgroundColor(new Color(0, 128, 0));

            }

        botaoFecharExplicacao.update(delta);
        botaoFecharExplicacao.setBackgroundColor(new Color(0, 128, 0));
        botaoFecharExplicacao.setTextColor(WHITE);

        if (botaoPush.isMousePressed()) {
            push();
        }

        if (botaoPop.isMousePressed()) {
            pop();
        }

        if (mostrarAvisoLimite) {
            botaoPush.setEnabled(false);
            timer += delta;
            if (timer >= tempoMax) {
                mostrarAvisoLimite = false;
                botaoPush.setEnabled(true);
            }
        }

        if (mostrarAvisoVazio) {
            botaoPop.setEnabled(false);
            timer += delta;
            if (timer >= tempoMax) {
                mostrarAvisoVazio = false;
                botaoPop.setEnabled(true);
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

        if (botaoLimpar.isMousePressed()) {
            botaoLimpar.setEnabled(false);
            limpar();
            botaoLimpar.setEnabled(true);
        }

        if (botaoSimulacaoRapida.isMousePressed()) {
            for(GuiComponent b : listaBotoes) {
                b.setEnabled(false);
            }
            limpar();
            simulacaoRapida();
        }

        if (!chamadasMetodos.isEmpty()) {
            timer += delta;
            if (timer >= tempoMax / 2.0) {
                Operacao o = chamadasMetodos.remove(0);
                if (o == Operacao.PUSH) {
                    push();
                } else {
                    pop();
                }
                timer = 0.0;
            }

        } else {
            timer = 0.0;
            for(GuiComponent b : listaBotoes) {
                b.setEnabled(true);
            }
        }
    }

    @Override
    public void draw() {

        clearBackground(WHITE);

        for (GuiComponent b : listaBotoes) {
            b.draw();
        }

        drawRectangle(140, 120, 144, 400, BLACK);

        for (int i = 0; i < pecas.size(); i++) {
            Peca p = pecas.get(i);
            p.desenhar(this);
        }

        if (mostrarAvisoLimite) {
            fillRectangle(0, 0, 800, 620, corFundoEscurecido);
            fillRectangle(225, 140, 355, 40, GRAY);
            fillRectangle(220, 135, 355, 40, WHITE);
            drawRectangle(220, 135, 355, 40, BLACK);
            drawText("LIMITE DA PILHA EXCEDIDO!", 225, 150, 24, BLACK);
        }

        if (mostrarAvisoVazio) {
            fillRectangle(0, 0, 800, 620, corFundoEscurecido);
            fillRectangle(267, 140, 272, 40, GRAY);
            fillRectangle(262, 135, 272, 40, WHITE);
            drawRectangle(262, 135, 272, 40, BLACK);
            drawText("A PILHA ESTÁ VAZIA!", 267, 150, 24, BLACK);
        }

        if (mostrarExplicacao) {
            fillRectangle(0, 0, 800, 620, corFundoEscurecido);
            fillRectangle(225, 185, 340, 300, GRAY);
            fillRectangle(220, 180, 340, 300, WHITE);
            drawRectangle(220, 180, 340, 300, BLACK);
            drawText(
                    """
                A pilha  é uma  estrutura de 
                dados onde o último elemento 
                a entrar é sempre o primeiro 
                a sair.  Esse  comportamento 
                é chamado de "Last In, First
                Out"  (LIFO),  em português: 
                Último  a  entrar,  primeiro
                a sair.
                """,
                    235,
                    210,
                    18,
                    BLACK
            );

            botaoFecharExplicacao.draw();

        }

        drawText(strTamanho, 585, 100, 20, BLACK);

    }

    private void pop() {
        if (tamanho < 1) {
            mostrarAvisoVazio = true;
            timer = 0.0;
            return;
        }

        strTamanho = "Tamanho: " + --tamanho;
        pecas.remove(pecas.size() - 1);
    }

    private void push() {
        if (tamanho >= LIMITE_PILHA) {
            mostrarAvisoLimite = true;
            timer = 0.0;
            return;
        }

        strTamanho = "Tamanho: " + ++tamanho;
        pecas.add(new Peca(140, 520 - tamanho * 40, 144, 40, gerarCor(), gerarNumero()));

    }

    private void limpar() {
        while (tamanho > 0) {
            pop();
        }
    }

    private void simulacaoRapida() {
        chamadasMetodos.clear();
        Random r = new Random();

        int paresPushPop = 10 + r.nextInt(10);

        int pushRestantes = paresPushPop;
        int popRestantes = paresPushPop;
        int tamAtual = 0;

        chamadasMetodos.add(Operacao.PUSH);
        pushRestantes--;
        tamAtual++;

        while(pushRestantes > 0 || popRestantes > 0) {
            boolean pushPermitido = pushRestantes > 0;
            boolean popPermitido = popRestantes > 0 && tamAtual > 0;

            if(pushPermitido && popPermitido) {
                if(r.nextBoolean()) {
                    chamadasMetodos.add(Operacao.PUSH);
                    pushRestantes--;
                    tamAtual++;
                } else {
                    chamadasMetodos.add(Operacao.POP);
                    popRestantes--;
                    tamAtual--;
                }
            } else if(pushPermitido) {
                chamadasMetodos.add(Operacao.PUSH);
                pushRestantes--;
                tamAtual++;
            } else if(popPermitido) {
                chamadasMetodos.add(Operacao.POP);
                popRestantes--;
                tamAtual--;
            }
        }

        if(chamadasMetodos.get(chamadasMetodos.size() - 1) != Operacao.POP) {
            chamadasMetodos.set(chamadasMetodos.size() - 1, Operacao.POP);
        }
        
    }

    private Color gerarCor() {

        Random r = new Random(tamanho * 1000);

        int vermelho = r.nextInt(256);
        int verde = r.nextInt(256);
        int azul = r.nextInt(256);

        return new Color(vermelho, verde, azul, 200);
        
    }

    private int gerarNumero() {
        Random r = new Random();

        return r.nextInt(100);
    }

    public static void main(String[] args) {
        new Pilha();

    }

    private enum Operacao {
        PUSH,
        POP
    }

}
