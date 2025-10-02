package Main;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author filipe
 */
public class JanelaMenu extends EngineFrame {
    
    private GuiButton botaoPilha;
    private GuiButton botaoFila;
    private GuiButton botaoDeque;
    private GuiButton botaoLista;
    
    private List<GuiComponent> listaBotoes;
    
    private final Color corBotao = new Color(0, 128, 0);
   
    public JanelaMenu() {

        super(
                800, // largura                      / width
                620, // algura                       / height
                "Simulador Estrutura de Dados", // título                       / title
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
        
        botaoPilha = new GuiButton(325, 250, 150, 60, "PILHA" );
        botaoFila = new GuiButton(325, 320, 150, 60, "FILA" );
        botaoDeque = new GuiButton(325, 390, 150, 60, "DEQUE" );
        botaoLista = new GuiButton(325, 460, 150, 60, "LISTA" );
        
        listaBotoes = new ArrayList<>();
        
        listaBotoes.add(botaoPilha);
        listaBotoes.add(botaoFila);
        listaBotoes.add(botaoDeque);
        listaBotoes.add(botaoLista);
        
    }
    
    @Override
    public void update(double delta) {
        
        for(GuiComponent botoes : listaBotoes) {
            botoes.update(delta);
            botoes.setBackgroundColor(corBotao);
            botoes.setTextColor(WHITE);
        }
        
        if(botaoPilha.isMousePressed()) {
            inicializarSimulacao(new Pilha());
        }
        
        if(botaoFila.isMousePressed()) {
            inicializarSimulacao(new Fila());
        }
        
        if(botaoDeque.isMousePressed()) {
            inicializarSimulacao(new Deque());
        }
        
        if(botaoLista.isMousePressed()) {
            inicializarSimulacao(new Lista());
        }
        
    }
    
    @Override
    public void draw() {
        
        
        clearBackground(WHITE);
        
        setFontName(FONT_SANS_SERIF);
        setFontStyle(FONT_BOLD);
       
        for(GuiComponent botoes : listaBotoes) {
            botoes.draw();
        }
        
        drawText("SIMULADOR ESTRUTURA DE DADOS", 118.5, 128, 28, BLACK);
        drawText("Desenvolvido por Filipe Roma Fiorio - Versão 1.0", 10, 607, 12, BLACK);
       
    }
    
    private void inicializarSimulacao(EngineFrame simulacao) {
        simulacao.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        new JanelaMenu();
    }
    
}
