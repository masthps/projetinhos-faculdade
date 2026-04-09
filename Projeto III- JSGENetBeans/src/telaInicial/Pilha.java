package telaInicial;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiTextField;
import java.util.Stack;

/**
 *
 * @author Mthss
 */
public class Pilha {
    
    private EngineFrame engine;
    private Stack<String> stack;
    private int maxSize;
    
    private GuiTextField inputField;
    private GuiTextField sizeField;
    private GuiButton addButton;
    private GuiButton removeButton;
    private GuiButton randomButton;
    private GuiButton clearButton;
    
    public Pilha(EngineFrame engine) {
        this.engine = engine;
        this.stack = new Stack<>();
        this.maxSize = 10;
        
        createComponents();
    }
    
    private void createComponents() {
        int x = 50;
        int y = 620;
        
        inputField = new GuiTextField(x, y, 200, 30, "");
        sizeField = new GuiTextField(x + 220, y, 200, 30, String.valueOf(maxSize));
        
        addButton = new GuiButton(x + 440, y, 150, 40, "Adicionar");
        removeButton = new GuiButton(x + 600, y, 150, 40, "Remover");
        randomButton = new GuiButton(x + 760, y, 150, 40, "Aleatório");
        clearButton = new GuiButton(x + 920, y, 150, 40, "Limpar");
    }
    
    public void reset() {
        stack.clear();
        inputField.setValue("");
        sizeField.setValue(String.valueOf(maxSize));
    }
    
    public void update(double delta) {
        inputField.update(delta);
        sizeField.update(delta);
        addButton.update(delta);
        removeButton.update(delta);
        randomButton.update(delta);
        clearButton.update(delta);
        
        // Atualiza tamanho máximo
        try {
            int newSize = Integer.parseInt(sizeField.getValue());
            if (newSize > 0) {
                maxSize = newSize;
            }
        } catch (NumberFormatException e) {
            // Ignora entradas inválidas
        }
        
        // Adicionar elemento
        if (addButton.isMousePressed()) {
            String value = inputField.getValue().trim();
            
            if (value.isEmpty()) {
                System.out.println("Valor nao valido.");
            } else if (stack.size() >= maxSize) {
                System.out.println("Pilha cheia.");
            } else {
                stack.push(value);
                inputField.setValue("");
            }
        }
        
        // Remover elemento
        if (removeButton.isMousePressed()) {
            if (stack.isEmpty()) {
                System.out.println("Pilha vazia.");
            } else {
                stack.pop();
            }
        }
        
        // Adicionar aleatório
        if (randomButton.isMousePressed()) {
            if (stack.size() >= maxSize) {
                System.out.println("Pilha cheia.");
            } else {
                int random = (int) (Math.random() * 100) + 1;
                stack.push(String.valueOf(random));
            }
        }
        
         // Limpar fila
        if (clearButton.isMousePressed()) {
            stack.clear();
        }
    }
    
    public void draw() {
        
        engine.drawText("Valor:", 50, 600, 18, EngineFrame.BLACK);
        inputField.draw();
        
        engine.drawText("Tamanho máximo:", 270, 600, 18, EngineFrame.BLACK);
        sizeField.draw();
        
        addButton.draw();
        removeButton.draw();
        randomButton.draw();
        clearButton.draw();
        
        // Desenha a pilha
        int stackX = 490;
        int stackY = 100; 
        int boxWidth = 150;
        int boxHeight = 40;
        
        engine.drawText("PILHA", stackX + 45, stackY - 60, 20, EngineFrame.DARKGRAY);
        
        for (int i = stack.size() - 1; i >= 0; i--) {
            int yPos = stackY + (stack.size() - 1 - i) * (boxHeight + 5);

            engine.fillRectangle(stackX, yPos, boxWidth, boxHeight, EngineFrame.SKYBLUE);
            engine.drawRectangle(stackX, yPos, boxWidth, boxHeight, EngineFrame.DARKBLUE);
            engine.drawText(
                stack.get(i), 
                stackX + boxWidth / 2 - 9, 
                yPos + 12, 
                18, 
                EngineFrame.BLACK
            );
        }
        
        // Info
        engine.drawText(
            "Elementos: " + stack.size() + " / " + maxSize, 
            stackX, 
            stackY - 30, 
            16, 
            EngineFrame.DARKGRAY
        );
        
    }
    
}