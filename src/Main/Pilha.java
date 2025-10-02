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
    private int contadorAcoes = 0;
    private final int LIMITE_PILHA = 10;

    private String strTamanho = "Tamanho: " + tamanho;

    private double timerAviso = 0.0;
    private double timerSimulacao = 0.0;
    private final double tempoMax = 1.0;
    private final double tempoAnimacao = 0.5;

    private boolean mostrarAvisoLimite = false;
    private boolean mostrarAvisoVazio = false;
    private boolean mostrarExplicacao = false;

    private GuiButton botaoPop;
    private GuiButton botaoPush;
    private GuiButton botaoLimpar;
    private GuiButton botaoSimulacaoRapida;
    private GuiButton botaoComoFunciona;
    private GuiButton botaoFecharExplicacao;

    private final Color corFundoEscurecido = new Color(40, 40, 40, 40);
    private final Color corBotao = new Color(0, 128, 0);

    private List<GuiComponent> listaBotoes;
    private List<Peca> pecas;
    private List<String> chamadasMetodos;
    private List<PassoAnimacao> animacoes;

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
        animacoes = new ArrayList<>();

        botaoPush = new GuiButton(570, 200, 150, 40, "PUSH");
        botaoPop = new GuiButton(570, 250, 150, 40, "POP");
        botaoLimpar = new GuiButton(570, 300, 150, 40, "LIMPAR");
        botaoSimulacaoRapida = new GuiButton(570, 350, 150, 40, "SIMULAÇÃO RÁPIDA");
        botaoComoFunciona = new GuiButton(570, 400, 150, 40, "COMO FUNCIONA?");
        botaoFecharExplicacao = new GuiButton(345, 420, 100, 40, "FECHAR");

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
            b.setBackgroundColor(corBotao);

        }

        botaoFecharExplicacao.update(delta);
        botaoFecharExplicacao.setBackgroundColor(corBotao);
        botaoFecharExplicacao.setTextColor(WHITE);

        if (botaoPush.isMousePressed()) {
            push();
        }

        if (botaoPop.isMousePressed()) {
            pop();
        }

        if (mostrarAvisoLimite) {
            botaoPush.setEnabled(false);
            timerAviso += delta;
            if (timerAviso >= tempoMax) {
                mostrarAvisoLimite = false;
                botaoPush.setEnabled(true);
            }
        }

        if (mostrarAvisoVazio) {
            botaoPop.setEnabled(false);
            timerAviso += delta;
            if (timerAviso >= tempoMax) {
                mostrarAvisoVazio = false;
                botaoPop.setEnabled(true);
            }
        }

        if (botaoComoFunciona.isMousePressed()) {
            for (GuiComponent b : listaBotoes) {
                b.setEnabled(false);
            }
            mostrarExplicacao = true;
        }

        if (botaoFecharExplicacao.isMousePressed()) {
            mostrarExplicacao = false;
            for (GuiComponent b : listaBotoes) {
                b.setEnabled(true);
            }
        }

        if (botaoLimpar.isMousePressed()) {
            limpar();
        }

        if (botaoSimulacaoRapida.isMousePressed()) {
            for (GuiComponent b : listaBotoes) {
                b.setEnabled(false);
            }
            limpar();
            simulacaoRapida();
        }

        if (!chamadasMetodos.isEmpty()) {
            timerSimulacao += delta;
            if (timerSimulacao >= tempoMax) {
                String string = chamadasMetodos.remove(0);
                if ("PUSH".equals(string)) {
                    push();
                } else {
                    pop();
                }
                timerSimulacao = 0.0;
            }
        }

        if (chamadasMetodos.isEmpty() && !listaBotoes.get(0).isEnabled() && !mostrarExplicacao) {
            for (GuiComponent b : listaBotoes) {
                b.setEnabled(true);
            }
        }

        for (int i = 0; i < animacoes.size(); i++) {

            botaoPop.setEnabled(false);
            botaoPush.setEnabled(false);

            PassoAnimacao passoAnimacao = animacoes.get(i);
            passoAnimacao.atualizar(delta);

            if (passoAnimacao.isFinalizada()) {
                if (pecas.contains(passoAnimacao.getPeca()) && passoAnimacao.getPeca().getyIni() == 50) {
                    pecas.remove(passoAnimacao.getPeca());
                }
                animacoes.remove(i);
                i--;
            }
        }

    }

    @Override
    public void draw() {

        clearBackground(WHITE);

        setFontName(FONT_SANS_SERIF);
        setFontStyle(FONT_BOLD);

        drawText(strTamanho, 577.5, 100, 20, BLACK);

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
            fillRectangle(209, 140, 392, 40, GRAY);
            fillRectangle(204, 135, 392, 40, WHITE);
            drawRectangle(204, 135, 392, 40, BLACK);
            drawText("LIMITE DA PILHA EXCEDIDO!", 209, 150, 24, BLACK);
        }

        if (mostrarAvisoVazio) {
            fillRectangle(0, 0, 800, 620, corFundoEscurecido);
            fillRectangle(257, 140, 292, 40, GRAY);
            fillRectangle(252, 135, 292, 40, WHITE);
            drawRectangle(252, 135, 292, 40, BLACK);
            drawText("A PILHA ESTÁ VAZIA!", 257, 150, 24, BLACK);
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

        if (tamanho > 0) {
            drawText("Topo ->", 70, 540 - tamanho * 40, 14, BLACK);
        }

    }

    private void pop() {
        if (tamanho < 1) {
            mostrarAvisoVazio = true;
            timerAviso = 0.0;
            return;
        }

        contadorAcoes++;

        double yAtual = 520 - tamanho * 40;

        strTamanho = "Tamanho: " + --tamanho;

        Peca pRemovida = pecas.get(pecas.size() - 1);

        PassoAnimacao animacaoRomever = new PassoAnimacao(pRemovida, 140, yAtual, 140, 50, tempoAnimacao);
        animacoes.add(animacaoRomever);

    }

    private void push() {
        if (tamanho >= LIMITE_PILHA) {
            mostrarAvisoLimite = true;
            timerAviso = 0.0;
            return;
        }

        contadorAcoes++;

        strTamanho = "Tamanho: " + ++tamanho;

        double yDestino = 520 - tamanho * 40;

        Peca pAcionada = new Peca(140, 50, 144, 40, gerarCor(), gerarNumero());
        pecas.add(pAcionada);

        PassoAnimacao animacaoAdicionar = new PassoAnimacao(pAcionada, 140, 50, 140, yDestino, tempoAnimacao);
        animacoes.add(animacaoAdicionar);

    }

    private void limpar() {
        contadorAcoes %= 10;
        animacoes.clear();
        pecas.clear();
        tamanho = 0;
        strTamanho = "Tamanho: " + tamanho;
    }

    private void simulacaoRapida() {
        chamadasMetodos.clear();
        Random r = new Random();

        int paresPushPop = 10 + r.nextInt(10);

        int pushRestantes = paresPushPop;
        int popRestantes = paresPushPop;
        int tamAtual = 0;

        chamadasMetodos.add("PUSH");
        pushRestantes--;
        tamAtual++;

        while (pushRestantes > 0 || popRestantes > 0) {
            boolean pushPermitido = pushRestantes > 0;
            boolean popPermitido = popRestantes > 0 && tamAtual > 0;

            if (pushPermitido && popPermitido) {
                if (r.nextBoolean()) {
                    chamadasMetodos.add("PUSH");
                    pushRestantes--;
                    tamAtual++;
                } else {
                    chamadasMetodos.add("POP");
                    popRestantes--;
                    tamAtual--;
                }
            } else if (pushPermitido) {
                chamadasMetodos.add("PUSH");
                pushRestantes--;
                tamAtual++;
            } else if (popPermitido) {
                chamadasMetodos.add("POP");
                popRestantes--;
                tamAtual--;
            }
        }

        if (!"POP".equals(chamadasMetodos.get(chamadasMetodos.size() - 1))) {
            chamadasMetodos.set(chamadasMetodos.size() - 1, "POP");
        }

    }

    private Color gerarCor() {

        double matiz = contadorAcoes * 0.618;

        return Color.getHSBColor((float) matiz, 1, 1);

    }

    private int gerarNumero() {
        Random r = new Random();

        return r.nextInt(100);
    }

    public static void main(String[] args) {
        new Pilha();

    }

}
