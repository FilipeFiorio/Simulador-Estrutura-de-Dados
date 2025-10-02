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
public class Fila extends EngineFrame {

    private int tamanho = 0;
    private int contadorAcoes = 0;
    private final int LIMITE_FILA = 10;
    
    private String strTamanho = "Tamanho: " + tamanho;
    
    private double timerAviso = 0.0;
    private double timerSimulacao = 0.0;
    private final double tempoMax = 1.0;
    private final double tempoAnimacao = 0.5;

    private boolean mostrarAvisoLimite = false;
    private boolean mostrarAvisoVazio = false;
    private boolean mostrarExplicacao = false;

    private GuiButton botaoEnqueue;
    private GuiButton botaoDequeue;
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

    public Fila() {

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
        pecas = new ArrayList<>();
        chamadasMetodos = new ArrayList<>();
        animacoes = new ArrayList<>();

        botaoEnqueue = new GuiButton(160, 420, 150, 40, "ENQUEUE");
        botaoDequeue = new GuiButton(320, 420, 150, 40, "DEQUEUE");
        botaoLimpar = new GuiButton(480, 420, 150, 40, "LIMPAR");
        botaoSimulacaoRapida = new GuiButton(245, 470, 150, 40, "SIMULAÇÃO RÁPIDA");
        botaoComoFunciona = new GuiButton(405, 470, 150, 40, "COMO FUNCIONA?");
        botaoFecharExplicacao = new GuiButton(345, 420, 100, 40, "FECHAR");

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
            b.setBackgroundColor(corBotao);
            b.setTextColor(WHITE);
        }
        
        botaoFecharExplicacao.update(delta);
        botaoFecharExplicacao.setBackgroundColor(corBotao);
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
        
        if (botaoSimulacaoRapida.isMousePressed()) {
            for (GuiComponent b : listaBotoes) {
                b.setEnabled(false);
            }
            limpar();
            simulacaoRapida();
        }
        
        if (mostrarAvisoLimite) {
            botaoEnqueue.setEnabled(false);
            timerAviso  += delta;
            if (timerAviso  >= tempoMax) {
                mostrarAvisoLimite = false;
                botaoEnqueue.setEnabled(true);
            }
        }

        if (mostrarAvisoVazio) {
            botaoDequeue.setEnabled(false);
            timerAviso  += delta;
            if (timerAviso  >= tempoMax) {
                mostrarAvisoVazio = false;
                botaoDequeue.setEnabled(true);
            }
        }

        if (botaoComoFunciona.isMousePressed()) {
            mostrarExplicacao = true;
            for(GuiComponent botoes : listaBotoes) {
                botoes.setEnabled(false);
            }
        }

        if (botaoFecharExplicacao.isMousePressed()) {
            mostrarExplicacao = false;
           
            for(GuiComponent botoes : listaBotoes) {
                botoes.setEnabled(true);
            }
            
        }
        
        if (!chamadasMetodos.isEmpty()) {
            timerSimulacao += delta;
            if (timerSimulacao >= tempoMax) {
                String string = chamadasMetodos.remove(0);
                if ("ENQUEUE".equals(string)) {
                    enqueue();
                } else {
                    dequeue();
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
            
            botaoDequeue.setEnabled(false);
            botaoEnqueue.setEnabled(false);
            
            PassoAnimacao passoAnimacao = animacoes.get(i);
            passoAnimacao.atualizar(delta);

            if (passoAnimacao.isFinalizada()) {
                if (pecas.contains(passoAnimacao.getPeca()) && passoAnimacao.getPeca().getxIni() == 60) {
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
            fillRectangle(220, 140, 367, 40, GRAY);
            fillRectangle(215, 135, 367, 40, WHITE);
            drawRectangle(215, 135, 367, 40, BLACK);
            drawText("LIMITE DA FILA EXCEDIDO!", 220, 150, 24, BLACK);
        }

        if (mostrarAvisoVazio) {
            fillRectangle(0, 0, 800, 620, corFundoEscurecido);
            fillRectangle(267, 140, 272, 40, GRAY);
            fillRectangle(262, 135, 272, 40, WHITE);
            drawRectangle(262, 135, 272, 40, BLACK);
            drawText("A FILA ESTÁ VAZIA!", 268, 150, 24, BLACK);
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
        
        System.out.println(measureText("LIMITE DA FILA EXCEDIDO!", 24));
        
        if(tamanho > 0) {
            drawText("↑Início", 200, 360, 12, BLACK);
            drawText("↓Final", 160 + tamanho * 40, 184, 12, BLACK);
        }
        
        
    }
    
    private void enqueue() {
        if(tamanho >= LIMITE_FILA) {
            mostrarAvisoLimite = true;
            timerAviso  = 0.0;
            return;
        }
        
        contadorAcoes++;
        
        strTamanho = "Tamanho: " + ++tamanho;
        
        double xDestino = 160 + tamanho * 40;
        
        Peca pAdiociona = new Peca(700, 200, 40, 144, gerarCor(), gerarNumero() );
        pecas.add(pAdiociona);
        
        PassoAnimacao animacaoAdicionar = new PassoAnimacao(pAdiociona, 700, 200, xDestino, 200, tempoMax);
        animacoes.add(animacaoAdicionar);
                
    }

    private void dequeue() {
        if(tamanho < 1) {
            mostrarAvisoVazio = true;
            timerAviso  = 0.0;
            return;
        }
        
        contadorAcoes++;
        
        strTamanho = "Tamanho: " + --tamanho;
        
        Peca pRemovida = pecas.get(0);
        
        PassoAnimacao animacaoRomever = new PassoAnimacao(pRemovida, 200, 200 , 60,  200, tempoAnimacao);
        animacoes.add(animacaoRomever);
        
        for(int i = 1; i < pecas.size(); i++) {
            Peca pMover = pecas.get(i);
            
            PassoAnimacao animacaoMover = new PassoAnimacao(pMover, pMover.getxIni(), 200, pMover.getxIni() - 40, 200, tempoAnimacao / 2);
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

        int enqueueRestantes = paresEnqueueDequeue;
        int dequeueRestantes = paresEnqueueDequeue;
        int tamAtual = 0;

        chamadasMetodos.add("ENQUEUE");
        enqueueRestantes--;
        tamAtual++;

        while (enqueueRestantes > 0 || dequeueRestantes > 0) {
            boolean pushPermitido = enqueueRestantes > 0;
            boolean popPermitido = dequeueRestantes > 0 && tamAtual > 0;
            
            if (pushPermitido && popPermitido) {
                if (r.nextBoolean()) {
                    chamadasMetodos.add("ENQUEUE");
                    enqueueRestantes--;
                    tamAtual++;
                } else {
                    chamadasMetodos.add("DEQUEUE");
                    dequeueRestantes--;
                    tamAtual--;
                }
            } else if (pushPermitido) {
                chamadasMetodos.add("ENQUEUE");
                enqueueRestantes--;
                tamAtual++;
            } else if (popPermitido) {
                chamadasMetodos.add("DEQUEUE");
                dequeueRestantes--;
                tamAtual--;
            }
        }

        if (!"DEQUEUE".equals(chamadasMetodos.get(chamadasMetodos.size() - 1))) {
            chamadasMetodos.set(chamadasMetodos.size() - 1, "DEQUEUE");
        }

    }

    public static void main(String[] args) {
        new Fila();
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
