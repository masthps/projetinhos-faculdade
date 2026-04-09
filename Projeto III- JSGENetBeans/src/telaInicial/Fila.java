package telaInicial;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiTextField;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Mthss
 */
public class Fila {
    
    private EngineFrame engine;
    private Queue<String> queue;
    private int maxSize;
    
    private GuiTextField inputField;
    private GuiTextField sizeField;
    private GuiButton enqueueButton;
    private GuiButton dequeueButton;
    private GuiButton randomButton;
    private GuiButton clearButton;
    
    public Fila(EngineFrame engine) {
        this.engine = engine;
        this.queue = new LinkedList<>();
        this.maxSize = 10;
        
        createComponents();
    }
    
    private void createComponents() {
        int x = 50;
        int y = 620;
        
        inputField = new GuiTextField(x, y, 200, 30, "");
        sizeField = new GuiTextField(x + 220, y, 200, 30, String.valueOf(maxSize));
        
        enqueueButton = new GuiButton(x + 440, y, 150, 40, "Enfileirar");
        dequeueButton = new GuiButton(x + 600, y, 150, 40, "Desenfileirar");
        randomButton = new GuiButton(x + 760, y, 150, 40, "Aleatório");
        clearButton = new GuiButton(x + 920, y, 150, 40, "Limpar");
    }
    
    public void reset() {
        queue.clear();
        inputField.setValue("");
        sizeField.setValue(String.valueOf(maxSize));
    }
    
    public void update(double delta) {
        inputField.update(delta);
        sizeField.update(delta);
        enqueueButton.update(delta);
        dequeueButton.update(delta);
        randomButton.update(delta);
        clearButton.update(delta);
        
        // Atualiza tamanho máximo
        try {
            int newSize = Integer.parseInt(sizeField.getValue());
            if (newSize > 0) {
                maxSize = newSize;
            }
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Digite apenas números.");
        }
        
        // Enfileirar elemento
        if (enqueueButton.isMousePressed()) {
            if (queue.size() >= maxSize) {
                System.out.println("Fila cheia.");
            } else {
                String value = inputField.getValue().trim();
                
                if (value.isEmpty()) {
                    System.out.println("Valor nao valido.");
                } else {
                    queue.offer(value);
                    inputField.setValue("");
                }
            }
        }
        
        // Desenfileirar elemento
        if (dequeueButton.isMousePressed()) {
            if (queue.isEmpty()) {
                System.out.println("Fila vazia.");
            } else {
                queue.poll();
            }
        }
        
        // Adicionar aleatório
        if (randomButton.isMousePressed()) {
            if (queue.size() >= maxSize) {
                System.out.println("Fila cheia.");
            } else {
                int random = (int) (Math.random() * 100) + 1;
                queue.offer(String.valueOf(random)); 
            }
        }
        
        // Limpar fila
        if (clearButton.isMousePressed()) {
            queue.clear();
        }
    }
    
    public void draw() {
        
        engine.drawText("Valor:", 50, 600, 18, EngineFrame.BLACK);
        inputField.draw();
        
        engine.drawText("Tamanho máximo:", 270, 600, 18, EngineFrame.BLACK);
        sizeField.draw();
        
        enqueueButton.draw();
        dequeueButton.draw();
        randomButton.draw();
        clearButton.draw();
        
        // Desenha a fila
        int queueX = 50;
        int queueY = 250;
        int boxWidth = 80;
        int boxHeight = 60;
        int spacing = 10;
        
        engine.drawText("FILA", queueX, queueY - 50, 20, EngineFrame.DARKGRAY);
        
        int i = 0;
        for (String element : queue) {
            int xPos = queueX + i * (boxWidth + spacing);

            engine.fillRectangle(xPos, queueY, boxWidth, boxHeight, EngineFrame.SKYBLUE);
            engine.drawRectangle(xPos, queueY, boxWidth, boxHeight, EngineFrame.DARKBLUE);
            engine.drawText(
                element, 
                xPos + boxWidth / 2 - 9, 
                queueY + 22, 
                18, 
                EngineFrame.BLACK
            );

            i++;
        }
        
        // Info
        engine.drawText(
            "Elementos: " + queue.size() + " / " + maxSize, 
            queueX, 
            queueY + boxHeight + 30, 
            16, 
            EngineFrame.DARKGRAY
        );
        
    }
    
}