package Main;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.BLACK;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.FONT_BOLD;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.FONT_SANS_SERIF;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.GRAY;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.WHITE;
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
public class Deque extends EngineFrame {

    private int tamanho = 0;
    private int contadorAcoes = 0;
    private final int LIMITE_DEQUE = 10;

    private String strTamanho = "Tamanho: " + tamanho;

    private double timerAviso = 0.0;
    private double timerSimulacao = 0.0;
    private final double tempoMax = 1.0;
    private final double tempoAnimacao = 0.5;

    private boolean mostrarAvisoLimite = false;
    private boolean mostrarAvisoVazio = false;
    private boolean mostrarExplicacao = false;

    private GuiButton botaoAddFirst;
    private GuiButton botaoAddLast;
    private GuiButton botaoRemoveFirst;
    private GuiButton botaoRemoveLast;
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

    public Deque() {

        super(
                800, // largura                      / width
                620, // algura                       / height
                "Simulação Deque", // título                       / title
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

        botaoAddFirst = new GuiButton(85, 420, 150, 40, "ADD FIRST");
        botaoAddLast = new GuiButton(245, 420, 150, 40, "ADD LAST");
        botaoRemoveFirst = new GuiButton(405, 420, 150, 40, "REMOVE FIRST");
        botaoRemoveLast = new GuiButton(565, 420, 150, 40, "REMOVE LAST");
        botaoLimpar = new GuiButton(165, 470, 150, 40, "LIMPAR");
        botaoSimulacaoRapida = new GuiButton(325, 470, 150, 40, "SIMULAÇÃO RÁPIDA");
        botaoComoFunciona = new GuiButton(485, 470, 150, 40, "COMO FUNCIONA?");
        botaoFecharExplicacao = new GuiButton(345, 420, 100, 40, "FECHAR");

        listaBotoes.add(botaoAddFirst);
        listaBotoes.add(botaoAddLast);
        listaBotoes.add(botaoRemoveFirst);
        listaBotoes.add(botaoRemoveLast);
        listaBotoes.add(botaoComoFunciona);
        listaBotoes.add(botaoLimpar);
        listaBotoes.add(botaoSimulacaoRapida);

    }

    @Override
    public void update(double delta) {

        for (GuiComponent b : listaBotoes) {
            b.update(delta);
            b.setBackgroundColor(corBotao);
            b.setTextColor(WHITE);
        }

        botaoFecharExplicacao.update(delta);
        botaoFecharExplicacao.setBackgroundColor(corBotao);
        botaoFecharExplicacao.setTextColor(WHITE);

        if (botaoAddFirst.isMousePressed()) {
            addFirst();
        }

        if (botaoAddLast.isMousePressed()) {
            addLast();
        }

        if (botaoRemoveFirst.isMousePressed()) {
            removeFirst();
        }

        if (botaoRemoveLast.isMousePressed()) {
            removeLast();
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

        if (mostrarAvisoLimite) {
            botaoAddFirst.setEnabled(false);
            botaoAddLast.setEnabled(false);
            timerAviso += delta;
            if (timerAviso >= tempoMax) {
                mostrarAvisoLimite = false;
                botaoAddFirst.setEnabled(true);
                botaoAddLast.setEnabled(true);
            }
        }

        if (mostrarAvisoVazio) {
            botaoRemoveFirst.setEnabled(false);
            botaoRemoveLast.setEnabled(false);
            timerAviso += delta;
            if (timerAviso >= tempoMax) {
                mostrarAvisoVazio = false;
                botaoRemoveFirst.setEnabled(true);
                botaoRemoveLast.setEnabled(true);
            }
        }

        if (botaoComoFunciona.isMousePressed()) {
            mostrarExplicacao = true;
            for (GuiComponent botoes : listaBotoes) {
                botoes.setEnabled(false);
            }
        }

        if (botaoFecharExplicacao.isMousePressed()) {
            mostrarExplicacao = false;

            for (GuiComponent botoes : listaBotoes) {
                botoes.setEnabled(true);
            }

        }

        if (!chamadasMetodos.isEmpty()) {
            timerSimulacao += delta;
            if (timerSimulacao >= tempoMax) {
                String string = chamadasMetodos.remove(0);
                Random random = new Random();
                if ("ADD".equals(string)) {
                    if(random.nextBoolean()) {
                        addFirst();
                    } else {
                        addLast();
                    }
                } else {
                    if(random.nextBoolean()) {
                        removeFirst();
                    } else {
                        removeLast();
                    }
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

            botaoAddLast.setEnabled(false);
            botaoAddFirst.setEnabled(false);
            botaoRemoveFirst.setEnabled(false);
            botaoRemoveLast.setEnabled(false);

            PassoAnimacao passoAnimacao = animacoes.get(i);
            passoAnimacao.atualizar(delta);

            if (passoAnimacao.isFinalizada()) {
                if (pecas.contains(passoAnimacao.getPeca())
                        && (passoAnimacao.getxFinal() == 60 || passoAnimacao.getxFinal() == 700)) {
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

        drawText(strTamanho, 585, 100, 20, BLACK);

        for (GuiComponent b : listaBotoes) {
            b.draw();
        }

        drawRectangle(200, 200, 400, 144, BLACK);

        for (int i = 0; i < pecas.size(); i++) {
            Peca p = pecas.get(i);
            p.desenhar(this);
        }

        if (mostrarAvisoLimite) {
            fillRectangle(0, 0, 800, 620, corFundoEscurecido);
            fillRectangle(203.7, 140, 403, 40, GRAY);
            fillRectangle(198.7, 135, 403, 40, WHITE);
            drawRectangle(198.7, 135, 403, 40, BLACK);
            drawText("LIMITE DO DEQUE EXCEDIDO!", 203.7, 150, 24, BLACK);
        }

        if (mostrarAvisoVazio) {
            fillRectangle(0, 0, 800, 620, corFundoEscurecido);
            fillRectangle(251, 140, 308, 40, GRAY);
            fillRectangle(246, 135, 308, 40, WHITE);
            drawRectangle(246, 135, 308, 40, BLACK);
            drawText("O DEQUE ESTÁ VAZIO!", 251, 150, 24, BLACK);
        }

        if (mostrarExplicacao) {
            fillRectangle(0, 0, 800, 620, corFundoEscurecido);
            fillRectangle(220, 185, 350, 300, GRAY);
            fillRectangle(215, 180, 350, 300, WHITE);
            drawRectangle(215, 180, 350, 300, BLACK);

            drawText(
                    """
                    O deque é uma estrutura de 
                    dados que permite inserções 
                    e remoções tanto no início 
                    quanto no fim. Seu nome vem 
                    de "Double Ended Queue", em 
                    português: fila de duas pontas.
                    """,
                    227,
                    210,
                    18,
                    BLACK
            );

            botaoFecharExplicacao.draw();

        }
        
        if (tamanho > 0) {
            drawText("↑Início", 200, 360, 12, BLACK);
            drawText("↓Final", 160 + tamanho * 40, 184, 12, BLACK);
        }

    }

    private void addFirst() {
        if (tamanho >= LIMITE_DEQUE) {
            mostrarAvisoLimite = true;
            timerAviso = 0.0;
            return;
        }

        contadorAcoes++;
        strTamanho = "Tamanho: " + ++tamanho;

        Peca pAdiciona = new Peca(20, 200, 40, 144, gerarCor(), gerarNumero());
        pecas.add(0, pAdiciona);

        for (int i = 1; i < pecas.size(); i++) {
            Peca pMover = pecas.get(i);
            double xAtual = pMover.getxIni();
            PassoAnimacao animacaoMover = new PassoAnimacao( pMover, xAtual, 200, xAtual + 40, 200, tempoAnimacao / 2);
            animacoes.add(animacaoMover);
        }

        PassoAnimacao animacaoAdicionar = new PassoAnimacao(pAdiciona, 60, 200, 200, 200, tempoAnimacao);
        animacoes.add(animacaoAdicionar);
    }

    private void addLast() {
        if (tamanho >= LIMITE_DEQUE) {
            mostrarAvisoLimite = true;
            timerAviso = 0.0;
            return;
        }

        contadorAcoes++;
        strTamanho = "Tamanho: " + ++tamanho;

        double xDestino = 160 + tamanho * 40;

        Peca pAdiciona = new Peca(700, 200, 40, 144, gerarCor(), gerarNumero());
        pecas.add(pAdiciona);

        PassoAnimacao animacaoAdicionar = new PassoAnimacao(pAdiciona, 700, 200, xDestino, 200, tempoAnimacao);
        animacoes.add(animacaoAdicionar);
    }

    private void removeFirst() {
        if (tamanho < 1) {
            mostrarAvisoVazio = true;
            timerAviso = 0.0;
            return;
        }

        contadorAcoes++;
        strTamanho = "Tamanho: " + --tamanho;

        Peca pRemovida = pecas.get(0);
        double xAtual = pRemovida.getxIni();

        PassoAnimacao animacaoRemover = new PassoAnimacao(pRemovida, xAtual, 200, 60, 200, tempoAnimacao
        );
        animacoes.add(animacaoRemover);

        for (int i = 1; i < pecas.size(); i++) {
            Peca pMover = pecas.get(i);
            double xAtualMover = pMover.getxIni();
            PassoAnimacao animacaoMover = new PassoAnimacao(pMover, xAtualMover, 200, xAtualMover - 40, 200, tempoAnimacao / 2);
            animacoes.add(animacaoMover);
        }

    }

    private void removeLast() {
        if (tamanho < 1) {
            mostrarAvisoVazio = true;
            timerAviso = 0.0;
            return;
        }

        contadorAcoes++;
        strTamanho = "Tamanho: " + --tamanho;

        Peca pRemovida = pecas.get(pecas.size() - 1);
        double xAtual = pRemovida.getxIni();

        PassoAnimacao animacaoRemover = new PassoAnimacao(pRemovida, xAtual, 200, 700, 200, tempoAnimacao);
        animacoes.add(animacaoRemover);

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

        int paresEnqueueDequeue = 10 + r.nextInt(10);

        int addPermitido = paresEnqueueDequeue;
        int removePermitido = paresEnqueueDequeue;
        int tamAtual = 0;

        chamadasMetodos.add("ADD");
        addPermitido--;
        tamAtual++;

        while (addPermitido > 0 || removePermitido > 0) {
            boolean pushPermitido = addPermitido > 0;
            boolean popPermitido = removePermitido > 0 && tamAtual > 0;

            if (pushPermitido && popPermitido) {
                if (r.nextBoolean()) {
                    chamadasMetodos.add("ADD");
                    addPermitido--;
                    tamAtual++;
                } else {
                    chamadasMetodos.add("REMOVE");
                    removePermitido--;
                    tamAtual--;
                }
            } else if (pushPermitido) {
                chamadasMetodos.add("ADD");
                addPermitido--;
                tamAtual++;
            } else if (popPermitido) {
                chamadasMetodos.add("REMOVE");
                removePermitido--;
                tamAtual--;
            }
        }

        if (!"REMOVE".equals(chamadasMetodos.get(chamadasMetodos.size() - 1))) {
            chamadasMetodos.set(chamadasMetodos.size() - 1, "REMOVE");
        }

    }

    public static void main(String[] args) {
        new Deque();
    }

    private Color gerarCor() {

        double matiz = contadorAcoes * 0.618;

        return Color.getHSBColor((float) matiz, 1, 1);

    }

    private int gerarNumero() {
        Random r = new Random();

        return r.nextInt(100);
    }

}
