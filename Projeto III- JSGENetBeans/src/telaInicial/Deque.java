package telaInicial;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiTextField;
import java.util.ArrayDeque;

/**
 *
 * @author Mthss
 */
public class Deque {
    
    private EngineFrame engine;
    private ArrayDeque<String> deque;
    private int maxSize;
    
    private GuiTextField inputField;
    private GuiTextField sizeField;
    private GuiButton addFirstButton;
    private GuiButton addLastButton;
    private GuiButton removeFirstButton;
    private GuiButton removeLastButton;
    private GuiButton randomFirstButton;
    private GuiButton randomLastButton;
    private GuiButton clearButton;
    
    public Deque(EngineFrame engine) {
        this.engine = engine;
        this.deque = new ArrayDeque<>();
        this.maxSize = 10;
        
        createComponents();
    }
    
    private void createComponents() {
        int x = 50;
        int y = 620;
        
        inputField = new GuiTextField(x, y, 200, 30, "");
        sizeField = new GuiTextField(x + 220, y, 200, 30, String.valueOf(maxSize));
        
        // Botões de adicionar
        addFirstButton = new GuiButton(x + 440, y, 150, 35, "Adicionar Início");
        addLastButton = new GuiButton(x + 440, y + 40, 150, 35, "Adicionar Fim");
        
        // Botões de remover
        removeFirstButton = new GuiButton(x + 600, y, 150, 35, "Remover Início");
        removeLastButton = new GuiButton(x + 600, y + 40, 150, 35, "Remover Fim");
        
        // Botões de aleatório
        randomFirstButton = new GuiButton(x + 760, y, 150, 35, "Aleat. Início");
        randomLastButton = new GuiButton(x + 760, y + 40, 150, 35, "Aleat. Fim");
        
        clearButton = new GuiButton(x + 920, y + 25, 150, 35, "Limpar Tudo");
    }
    
    public void reset() {
        deque.clear();
        inputField.setValue("");
        sizeField.setValue(String.valueOf(maxSize));
    }
    
    public void update(double delta) {
        inputField.update(delta);
        sizeField.update(delta);
        addFirstButton.update(delta);
        addLastButton.update(delta);
        removeFirstButton.update(delta);
        removeLastButton.update(delta);
        randomFirstButton.update(delta);
        randomLastButton.update(delta);
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
        
        // Adicionar no início
        if (addFirstButton.isMousePressed()) {
            if (deque.size() >= maxSize) {
                System.out.println("Deque cheio.");
            } else {
                String value = inputField.getValue().trim();
                if (value.isEmpty()) {
                    System.out.println("Deque cheio.");
                } else {
                    deque.addFirst(value);
                    inputField.setValue("");
                }
            }
        }
        
        // Adicionar no fim
        if (addLastButton.isMousePressed()) {
            if (deque.size() >= maxSize) {
                System.out.println("Deque cheio.");
            } else {
                String value = inputField.getValue().trim();
                if (value.isEmpty()) {
                    System.out.println("Valor nao valido.");
                } else {
                    deque.addLast(value);
                    inputField.setValue("");
                }
            }
        }
        
        // Remover do início
        if (removeFirstButton.isMousePressed()) {
            if (deque.isEmpty()) {
                System.out.println("Deque vazio.");
            } else {
                deque.removeFirst();
            }
        }
        
        // Remover do fim
        if (removeLastButton.isMousePressed()) {
            if (deque.isEmpty()) {
                System.out.println("Deque vazio.");
            } else {
                deque.removeLast();
            }
        }
        
        // Adicionar aleatório no início
        if (randomFirstButton.isMousePressed()) {
            if (deque.size() >= maxSize) {
                System.out.println("Deque cheio.");
            } else {
                int random = (int) (Math.random() * 100) + 1;
                deque.addFirst(String.valueOf(random));
            }
        }
        
        // Adicionar aleatório no fim
        if (randomLastButton.isMousePressed()) {
            if (deque.size() >= maxSize) {
                System.out.println("Deque cheio.");
            } else {
                int random = (int) (Math.random() * 100) + 1;
                deque.addLast(String.valueOf(random));
            }
        }
        
        // Limpar deque
        if (clearButton.isMousePressed()) {
            deque.clear();
        }
    }
    
    public void draw() {
        
        engine.drawText("Valor:", 50, 600, 18, EngineFrame.BLACK);
        inputField.draw();
        
        engine.drawText("Tamanho máximo:", 270, 600, 18, EngineFrame.BLACK);
        sizeField.draw();
        
        addFirstButton.draw();
        addLastButton.draw();
        removeFirstButton.draw();
        removeLastButton.draw();
        randomFirstButton.draw();
        randomLastButton.draw();
        clearButton.draw();
        
        // Desenha o deque
        int dequeX = 50;
        int dequeY = 250;
        int boxWidth = 80;
        int boxHeight = 60;
        int spacing = 10;
        
        engine.drawText("Deque", dequeX, dequeY - 80, 24, EngineFrame.DARKGRAY);
        
        int i = 0;
        for (String element : deque) {
            int xPos = dequeX + i * (boxWidth + spacing);

            // Mudar a cor para primeiro e ultimo elementos
            if (i == 0) {
                engine.fillRectangle(xPos, dequeY, boxWidth, boxHeight, EngineFrame.LIME);
                engine.drawRectangle(xPos, dequeY, boxWidth, boxHeight, EngineFrame.DARKGREEN);
            } else if (i == deque.size() - 1) {
                engine.fillRectangle(xPos, dequeY, boxWidth, boxHeight, EngineFrame.RED);
                engine.drawRectangle(xPos, dequeY, boxWidth, boxHeight, EngineFrame.BLACK);
            } else {
                engine.fillRectangle(xPos, dequeY, boxWidth, boxHeight, EngineFrame.SKYBLUE);
                engine.drawRectangle(xPos, dequeY, boxWidth, boxHeight, EngineFrame.DARKBLUE);
            }

            engine.drawText(
                element, 
                xPos + boxWidth / 2 - 9, 
                dequeY + 22, 
                18, 
                EngineFrame.BLACK
            );

            i++;
        }
        
        // Info
        engine.drawText(
            "Elementos: " + deque.size() + " / " + maxSize, 
            dequeX, 
            dequeY + boxHeight + 30,   
            16, 
            EngineFrame.DARKGRAY
        );
        
    }
}
