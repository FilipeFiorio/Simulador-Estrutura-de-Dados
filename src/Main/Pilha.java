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
    
    private int topo = 0;
    private int max = 10;
    
    private GuiButton botaoPop;
    private GuiButton botaoPush;
    private GuiButton botaoLimpar;
    
    private GuiLabel labelTopo;
    
    private List<Peca> pecas;
    private List<GuiComponent> guiComponentes;
    
    public Pilha() {
        
        super(
            800,                 // largura                      / width
            620,                 // algura                       / height
            "Simulação Pilha",      // título                       / title
            60,                  // quadros por segundo desejado / target FPS
            true,                // suavização                   / antialiasing
            false,               // redimensionável              / resizable
            false,               // tela cheia                   / full screen
            false,               // sem decoração                / undecorated
            false,               // sempre no topo               / always on top
            false                // fundo invisível              / invisible background
        );
        
    }
    
    @Override
    public void create() {
        
        useAsDependencyForIMGUI();
        
        guiComponentes = new ArrayList<>();
        pecas = new ArrayList<>();
   
        botaoPop = new GuiButton(560, 300, 100, 40, "POP");
        botaoPush = new GuiButton(560, 200, 100, 40, "PUSH");
        botaoLimpar = new GuiButton(560, 400, 100, 40, "LIMPAR");
        
        labelTopo = new GuiLabel(500, 100, 100, 40, String.valueOf(topo));
        
        guiComponentes.add(botaoPop);
        guiComponentes.add(botaoPush);
        guiComponentes.add(botaoLimpar);
        
        guiComponentes.add(labelTopo);
    }

    @Override
    public void update( double delta ) {
        
        for(GuiComponent g : guiComponentes) {
            g.update(delta);
        }
        
        if(botaoPush.isMousePressed()) {
            push();
        }
        
        if(botaoPop.isMousePressed()) {
            pop();
        }
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        for(GuiComponent g : guiComponentes) {
            g.draw();
        }
        
        drawRectangle(140, 160, 144, 340, BLACK);
        
        System.out.println(topo);
    
    }
    
    private void pop() {
        if(topo <= 0) {
            return;
        }
        
        topo--;
        
    }
    
    private void push() {
        
        topo++;
//        
//        int xIni = 140;
//        int yIni;
//        
//        if(topo > max) {
//            topo--;
//            return;
//        }
//        
//        new Peca();
    }

    public static void main( String[] args ) {
        new Pilha();
        
    }
    
    
}
