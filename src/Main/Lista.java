package Main;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.BLACK;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.FONT_BOLD;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.FONT_SANS_SERIF;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.GRAY;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.WHITE;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
import br.com.davidbuzatto.jsge.imgui.GuiInputDialog;
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
public class Lista extends EngineFrame {

    private int tamanho = 0;
    private int contadorAcoes = 0;
    private final int LIMITE_LISTA = 10;

    private String strTamanho = "Tamanho: " + tamanho;

    private double timerAviso = 0.0;
    private double timerSimulacao = 0.0;
    private final double tempoMax = 1.0;
    private final double tempoAnimacao = 0.5;

    private boolean mostrarAvisoLimite = false;
    private boolean mostrarAvisoVazio = false;
    private boolean mostrarExplicacao = false;
    private boolean mostrarAvisoIndex = false;
    private boolean adicionarValor = false;

    private GuiButton botaoAdd;
    private GuiButton botaoAddIndex;
    private GuiButton botaoRemove;
    private GuiButton botaoLimpar;
    private GuiButton botaoSimulacaoRapida;
    private GuiButton botaoComoFunciona;
    private GuiButton botaoFecharExplicacao;

    private GuiInputDialog entradaIndex;

    private final Color corFundoEscurecido = new Color(40, 40, 40, 40);
    private final Color corBotao = new Color(0, 128, 0);

    private List<GuiComponent> listaBotoes;
    private List<Peca> pecas;
    private List<String> chamadasMetodos;
    private List<PassoAnimacao> animacoes;

    public Lista() {

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

        botaoAdd = new GuiButton(160, 420, 150, 40, "ADD");
        botaoAddIndex = new GuiButton(320, 420, 150, 40, "ADD + INDEX");
        botaoRemove = new GuiButton(480, 420, 150, 40, "REMOVE");
        botaoLimpar = new GuiButton(160, 470, 150, 40, "LIMPAR");
        botaoSimulacaoRapida = new GuiButton(320, 470, 150, 40, "SIMULAÇÃO RÁPIDA");
        botaoComoFunciona = new GuiButton(480, 470, 150, 40, "COMO FUNCIONA?");
        botaoFecharExplicacao = new GuiButton(345, 420, 100, 40, "FECHAR");

        entradaIndex = new GuiInputDialog("INDEX", "Entre com uma posição (0 a 9)", true);

        listaBotoes.add(botaoAdd);
        listaBotoes.add(botaoAddIndex);
        listaBotoes.add(botaoRemove);
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

        entradaIndex.update(delta);

        if (adicionarValor) {
            entradaIndex.setText("ADD na Lista");
        } else {
            entradaIndex.setText("REMOVE na Lista");
        }

        if (botaoAdd.isMousePressed()) {
            add();
        }

        if (botaoAddIndex.isMousePressed()) {
            entradaIndex.show();
            adicionarValor = true;
        }

        if (botaoRemove.isMousePressed()) {
            entradaIndex.show();
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
            botaoAdd.setEnabled(false);
            botaoAddIndex.setEnabled(false);
            timerAviso += delta;
            if (timerAviso >= tempoMax) {
                mostrarAvisoLimite = false;
                botaoAdd.setEnabled(true);
                botaoAddIndex.setEnabled(true);
            }
        }

        if (mostrarAvisoVazio) {
            botaoRemove.setEnabled(false);
            timerAviso += delta;
            if (timerAviso >= tempoMax) {
                mostrarAvisoVazio = false;
                botaoRemove.setEnabled(true);
            }
        }

        if (mostrarAvisoIndex) {
            botaoAdd.setEnabled(false);
            botaoAddIndex.setEnabled(false);
            timerAviso += delta;
            if (timerAviso >= tempoMax) {
                mostrarAvisoIndex = false;
                botaoAdd.setEnabled(true);
                botaoAddIndex.setEnabled(true);
            }
        }

        if (entradaIndex.isOkButtonPressed() || entradaIndex.isEnterKeyPressed()) {
            entradaIndex.hide();
            if (checarInteiro(entradaIndex.getValue())) {
                if (adicionarValor) {
                    addIndex(Integer.parseInt(entradaIndex.getValue()));
                } else {
                    removeIdex(Integer.parseInt(entradaIndex.getValue()));
                }
            } else {
                timerAviso = 0.0;
                mostrarAvisoIndex = true;
            }

            adicionarValor = false;
        }

        if (entradaIndex.isCancelButtonPressed() || entradaIndex.isCloseButtonPressed()) {
            entradaIndex.hide();
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
                    if(tamanho == 0) {
                        add();
                    }
                    else if (random.nextBoolean()) {
                        add();
                    } else {
                        addIndex(random.nextInt(0, tamanho));
                    }
                } else {
                    removeIdex(random.nextInt(0, tamanho));
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

            botaoAddIndex.setEnabled(false);
            botaoAdd.setEnabled(false);
            botaoRemove.setEnabled(false);

            PassoAnimacao passoAnimacao = animacoes.get(i);
            passoAnimacao.atualizar(delta);

            if (passoAnimacao.isFinalizada()) {
                if (pecas.contains(passoAnimacao.getPeca())
                        && (passoAnimacao.getxFinal() == 60 || passoAnimacao.getxFinal() == 700 || passoAnimacao.getyFinal() == 50)) {
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
            fillRectangle(211.5, 140, 387, 40, GRAY);
            fillRectangle(206.5, 135, 387, 40, WHITE);
            drawRectangle(206.5, 135, 387, 40, BLACK);
            drawText("LIMITE DA LISTA EXCEDIDO!", 211.5, 150, 24, BLACK);
        }

        if (mostrarAvisoIndex) {
            fillRectangle(0, 0, 800, 620, corFundoEscurecido);
            fillRectangle(279, 140, 252, 40, GRAY);
            fillRectangle(274, 135, 252, 40, WHITE);
            drawRectangle(274, 135, 252, 40, BLACK);
            drawText("ÍNDICE INVÁLIDO!", 279, 150, 24, BLACK);
        }

        if (mostrarAvisoVazio) {
            fillRectangle(0, 0, 800, 620, corFundoEscurecido);
            fillRectangle(259.5, 140, 291, 40, GRAY);
            fillRectangle(254.5, 135, 291, 40, WHITE);
            drawRectangle(254.5, 135, 291, 40, BLACK);
            drawText("A LISTA ESTÁ VAZIA!", 254.5, 150, 24, BLACK);
        }

        if (mostrarExplicacao) {
            fillRectangle(0, 0, 800, 620, corFundoEscurecido);
            fillRectangle(220, 185, 350, 300, GRAY);
            fillRectangle(215, 180, 350, 300, WHITE);
            drawRectangle(215, 180, 350, 300, BLACK);

            drawText(
                    """
                    A lista é uma estrutura de 
                    dados que organiza elementos 
                    de forma sequencial. Ela pode 
                    permitir inserções, buscas e 
                    remoções em qualquer posição, 
                    diferente da pilha e da fila.
                    """,
                    227,
                    210,
                    18,
                    BLACK
            );

            botaoFecharExplicacao.draw();

        }
        
        System.out.println(measureText("LIMITE DA LISTA EXCEDIDO!", 24));

        for (int i = 0; i < 10; i++) {
            drawText(String.valueOf(i), 217.5 + i * 40, 360, 12, BLACK);
        }

        entradaIndex.draw();
    }

    private void add() {
        if (tamanho >= LIMITE_LISTA) {
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

    private void addIndex(int index) {

       
        if (tamanho >= LIMITE_LISTA) {
            mostrarAvisoLimite = true;
            timerAviso = 0.0;
            return;
        } else if (index > tamanho) {
            mostrarAvisoIndex = true;
            timerAviso = 0;
            return;
        }

        contadorAcoes++;
        strTamanho = "Tamanho: " + ++tamanho;

        double xDestino = 160 + (index + 1) * 40;

        Peca pAdicionada = new Peca(700, 100, 40, 144, gerarCor(), gerarNumero());
        pecas.add(index, pAdicionada);

        for (int i = index; i < pecas.size(); i++) {
            Peca pMover = pecas.get(i);
            PassoAnimacao passoMover = new PassoAnimacao(pMover, pMover.getxIni(), 200, pMover.getxIni() + 40, 200, tempoAnimacao / 2);
            animacoes.add(passoMover);
        }

        PassoAnimacao adicionar = new PassoAnimacao(pAdicionada, xDestino, 51, xDestino, 200, tempoAnimacao);
        animacoes.add(adicionar);

    }

    private void removeIdex(int index) {
        if (tamanho < 1) {
            mostrarAvisoVazio = true;
            timerAviso = 0.0;
            return;
        } else if (index > tamanho - 1) {
            mostrarAvisoIndex = true;
            timerAviso = 0;
            return;
        }

        contadorAcoes++;
        strTamanho = "Tamanho: " + --tamanho;

        Peca pRemovida = pecas.get(index);
        double xAtual = pRemovida.getxIni();

        PassoAnimacao animacaoRemover = new PassoAnimacao(pRemovida, xAtual, 200, xAtual, 50, tempoAnimacao);
        animacoes.add(animacaoRemover);

        for (int i = index + 1; i < pecas.size(); i++) {
            Peca pMover = pecas.get(i);
            double xAtualMover = pMover.getxIni();
            PassoAnimacao animacaoMover = new PassoAnimacao(pMover, xAtualMover, 200, xAtualMover - 40, 200, tempoAnimacao / 2);
            animacoes.add(animacaoMover);
        }

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
        new Lista();
    }

    private Color gerarCor() {

        double matiz = contadorAcoes * 0.618;

        return Color.getHSBColor((float) matiz, 1, 1);

    }

    private int gerarNumero() {
        Random r = new Random();

        return r.nextInt(100);
    }

    private boolean checarInteiro(String str) {
        try {
            Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
