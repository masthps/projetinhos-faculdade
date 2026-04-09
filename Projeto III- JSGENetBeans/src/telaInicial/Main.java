package telaInicial;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import java.util.ArrayList;
import java.util.List;


/**
 * Modelo de projeto básico da JSGE.
 * 
 * JSGE basic project template.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Main extends EngineFrame {
    
    private List<GuiButton> menuButtons;
    private GuiButton buttonStack;
    private GuiButton buttonQueue;
    private GuiButton buttonList;
    private GuiButton buttonDeque;
    
    private Pilha stackSimulator;
    private Fila queueSimulator;
    private Lista listSimulator;
    private Deque dequeSimulator;
    
    
    private String currentScreen;
    
    public Main() {
        
        super(
            1200,                 // largura                      / width
            700,                 // algura                       / height
            "Projeto Simulador Estruturas de Dados",     
            60,                  // quadros por segundo desejado / target FPS
            true,                // suavização                   / antialiasing
            true,               // redimensionável              / resizable
            false,               // tela cheia                   / full screen
            false,               // sem decoração                / undecorated
            false,               // sempre no topo               / always on top
            false                // fundo invisível              / invisible background
        );
        
    }
    
    /**
     * Cria o mundo do jogo.
     * Esse método executa apenas uma vez durante a inicialização da engine.
     * 
     * Creates the game world.
     * This method runs just one time during engine initialization.
     */
    @Override
    public void create() {
        
        useAsDependencyForIMGUI();
        
        menuButtons = new ArrayList<>();
        
        int centerX = getScreenWidth() / 2;
        int centerY = getScreenHeight() / 2;
        int buttonWidth = 300;
        int buttonHeight = 60;
        int spacing = 80;
        
        buttonStack = new GuiButton(
            centerX - buttonWidth / 2,
            centerY - spacing - buttonHeight / 2,
            buttonWidth,
            buttonHeight,
            "PILHA"
        );
        
        buttonQueue = new GuiButton(
            centerX - buttonWidth / 2,
            centerY - buttonHeight / 2,
            buttonWidth,
            buttonHeight,
            "FILA"
        );
        
        buttonList = new GuiButton(
            centerX - buttonWidth / 2,
            centerY + spacing - buttonHeight / 2,
            buttonWidth,
            buttonHeight,
            "LISTA"
        );
        
        buttonDeque = new GuiButton(
            centerX - buttonWidth / 2,
            centerY + spacing * 2 - buttonHeight / 2, 
            buttonWidth,
            buttonHeight,
            "DEQUE"
        );

        menuButtons.add(buttonStack);
        menuButtons.add(buttonQueue);
        menuButtons.add(buttonList);
        menuButtons.add(buttonDeque);
        
        stackSimulator = new Pilha(this);
        queueSimulator = new Fila(this);
        listSimulator = new Lista(this);
        dequeSimulator = new Deque(this);
        
        currentScreen = "menu";
        
    }

    /**
     * Lê a entrada do usuário e atualiza o mundo do jogo.
     * Os métodos de entrada devem ser usados aqui.
     * Atenção: Você NÃO DEVE usar nenhum dos métodos de desenho da engine aqui.
     * 
     * 
     * Reads user input and update game world.
     * Input methods should be used here.
     * Warning: You MUST NOT use any of the engine drawing methods here.
     * 
     * @param delta O tempo passado, em segundos, de um quadro para o outro.
     * Time passed, in seconds, between frames.
     */
    @Override
    public void update( double delta ) {
        
        if (currentScreen.equals("menu")) {
            for (GuiButton button : menuButtons) {
                button.update(delta);
            }
            
            if (buttonStack.isMousePressed()) {
                currentScreen = "pilha";
                stackSimulator.reset();
            }
            
            if (buttonQueue.isMousePressed()) {
                currentScreen = "fila";
                queueSimulator.reset();
            }
            
            if (buttonList.isMousePressed()) {
                currentScreen = "lista";
                listSimulator.reset();
            }
            
            if (buttonDeque.isMousePressed()) {
                currentScreen = "deque";
                dequeSimulator.reset();
            }
            
        } else if (currentScreen.equals("pilha")) {
            stackSimulator.update(delta);
            if (isKeyPressed(KEY_ESCAPE)) {
                currentScreen = "menu";
            }
            
        } else if (currentScreen.equals("fila")) {
            queueSimulator.update(delta);
            if (isKeyPressed(KEY_ESCAPE)) {
                currentScreen = "menu";
            }
            
        } else if (currentScreen.equals("lista")) {
            listSimulator.update(delta);
            if (isKeyPressed(KEY_ESCAPE)) {
                currentScreen = "menu";
            }
        }
        
        else if (currentScreen.equals("deque")) {
            dequeSimulator.update(delta);
            if (isKeyPressed(KEY_ESCAPE)) {
                currentScreen = "menu";
            }
        }
        
    }
    
    /**
     * Desenha o mundo do jogo.
     * Todas as operações de desenho DEVEM ser feitas aqui.
     * 
     * Draws the game world.
     * All drawing related operations MUST be performed here.
     */
    @Override
    public void draw() {
        clearBackground( WHITE );

         if (currentScreen.equals("menu")) {
            drawMenu();
        } else if (currentScreen.equals("pilha")) {
            stackSimulator.draw();
        } else if (currentScreen.equals("fila")) {
            queueSimulator.draw();
        } else if (currentScreen.equals("lista")) {
            listSimulator.draw();
        } else if (currentScreen.equals("deque")) {
            dequeSimulator.draw();
        }
        
    }
    
     private void drawMenu() {
        
        // Titulo
        drawText(
            "Simulador Estrutura de Dados",
            getScreenWidth() / 2 - 300,
            100,
            40,
            BLACK
        );
        
        // Subtitulo
        drawText(
            "Selecione uma estrutura para simular",
            getScreenWidth() / 2 - 200,
            160,
            20,
            GRAY
        );
        
        for (GuiButton button : menuButtons) {
            button.draw();
        }
        
    }
    
    /**
     * Instancia a engine e a inicia.
     * 
     * Instantiates the engine and starts it.
     */
    public static void main( String[] args ) {
        new Main();
    }
    
}
