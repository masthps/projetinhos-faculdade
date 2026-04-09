package aesd.draw;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.GRAY;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.KEY_Q;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.WHITE;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiTheme; 
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bastards
 */
public class Main extends EngineFrame {
    
    private List<GuiButton> menuButtons;
    private GuiButton buttonBST;
    private GuiButton buttonAVL;
    private GuiButton buttonRBT;
    
    private SimuladorBB simuladorBB;
    private SimuladorAVL simuladorAVL; 
    private SimuladorAVP simuladorAVP;
    
    private String currentScreen;
    private GuiTheme darkTheme; 
    
    public Main() {
        super(
            1200,                 
            700,                  
            "Projeto IV - ESDD",     
            60,                   
            true,                 
            true,                 
            false,                
            false,                
            false,                
            false                 
        );
    }
    
    @Override
    public void create() {
        
        useAsDependencyForIMGUI();
        
        darkTheme = GuiTheme.buildDarkTheme();
        darkTheme.install();
        
        menuButtons = new ArrayList<>();
        
        int centerX = getScreenWidth() / 2;
        int centerY = getScreenHeight() / 2;
        int buttonWidth = 300;
        int buttonHeight = 60;
        int spacing = 80;
        
        buttonBST = new GuiButton(
            centerX - buttonWidth / 2,
            centerY - spacing,
            buttonWidth,
            buttonHeight,
            "Árvore Binária de Busca (BST)"
        );
        
        buttonAVL = new GuiButton(
            centerX - buttonWidth / 2,
            centerY,
            buttonWidth,
            buttonHeight,
            "Árvore AVL"
        );
        
        buttonRBT = new GuiButton(
            centerX - buttonWidth / 2,
            centerY + spacing,
            buttonWidth,
            buttonHeight,
            "Árvore Vermelho-Preto (AVP)" 
        );
        
        buttonAVL.setEnabled(true); 
        buttonRBT.setEnabled(true); 

        menuButtons.add(buttonBST);
        menuButtons.add(buttonAVL);
        menuButtons.add(buttonRBT);
        
        simuladorBB = new SimuladorBB(this);
        simuladorAVL = new SimuladorAVL(this); 
        simuladorAVP = new SimuladorAVP(this);
        
        currentScreen = "menu";
    }

    @Override
    public void update(double delta) {
        
        if (currentScreen.equals("menu")) {
            for (GuiButton button : menuButtons) {
                button.update(delta);
            }
            
            if (buttonBST.isMousePressed()) {
                currentScreen = "ArvoreBB";
                simuladorBB.reset();
            }
            
            if (buttonAVL.isMousePressed()) {
                currentScreen = "ArvoreAVL";
                simuladorAVL.reset();
            }
            
            if (buttonRBT.isMousePressed()) {
                currentScreen = "ArvoreAVP";
                simuladorAVP.reset();
            }
            
        } else if (currentScreen.equals("ArvoreBB")) {
            simuladorBB.update(delta);
            if (isKeyPressed(KEY_Q)) {
                currentScreen = "menu";
            }
        } else if (currentScreen.equals("ArvoreAVL")) { 
            simuladorAVL.update(delta);
            if (isKeyPressed(KEY_Q)) {
                currentScreen = "menu";
            }
        } else if (currentScreen.equals("ArvoreAVP")) { 
            simuladorAVP.update(delta);
            if (isKeyPressed(KEY_Q)) {
                currentScreen = "menu";
            }
        }
    }
    
    @Override
    public void draw() {
        clearBackground(darkTheme.containerBackgroundColor);

        if (currentScreen.equals("menu")) {
            drawMenu();
        } else if (currentScreen.equals("ArvoreBB")) {
            simuladorBB.draw();
        } else if (currentScreen.equals("ArvoreAVL")) {
            simuladorAVL.draw();
        } else if (currentScreen.equals("ArvoreAVP")) {
            simuladorAVP.draw();
        }
    }
    
    private void drawMenu() {
        
        drawText(
            "Simulador de Árvores Binárias - ESDD",
            getScreenWidth() / 2 - 425,
            100,
            40,
            WHITE 
        );
        
        drawText(
            "Selecione uma árvore para simular",
            getScreenWidth() / 2 - 200,
            160,
            20,
            GRAY
        );
        
        for (GuiButton button : menuButtons) {
            button.draw();
        }
    }
    
    public static void main(String[] args) {
        new Main();
    }
}