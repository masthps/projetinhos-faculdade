package telaInicial;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiTextField;
import java.util.ArrayList;

/**
 *
 * @author Mthss
 */
public class Lista {
    
    private EngineFrame engine;
    private ArrayList<String> list;
    private int maxSize;
    
    private GuiTextField inputField;
    private GuiTextField sizeField;
    private GuiButton addButton;
    private GuiButton removeButton;
    private GuiButton randomButton;
    private GuiButton clearButton;
    
    public Lista(EngineFrame engine) {
        this.engine = engine;
        this.list = new ArrayList<>();
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
        list.clear();
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
            if (list.size() >= maxSize) {
                System.out.println("Lista cheia.");
            } else {
                String value = inputField.getValue().trim();
                
                if (value.isEmpty()) {
                    System.out.println("Valor nao valido.");
                } else {
                    list.add(value);
                    inputField.setValue("");
                }
            }
        }
        
        // Remover elemento
        if (removeButton.isMousePressed()) {
            if (list.isEmpty()) {
                System.out.println("Lista vazia.");
            } else {
                list.remove(list.size() - 1);
            }
        }
        
        // Adicionar aleatório
        if (randomButton.isMousePressed()) {
            if (list.size() >= maxSize) {
                System.out.println("Lista cheia.");
            } else {
                int random = (int) (Math.random() * 100) + 1;
                list.add(String.valueOf(random));
            }
        }
        
        // Limpar lista
        if (clearButton.isMousePressed()) {
            list.clear();
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
        
        // Desenha a lista
        int listX = 50;
        int listY = 250;
        int boxWidth = 80;
        int boxHeight = 60;
        int spacing = 10;
        
        engine.drawText("LISTA", listX, listY - 30, 20, EngineFrame.DARKGRAY);
        
        for (int i = 0; i < list.size(); i++) {
            int xPos = listX + i * (boxWidth + spacing);

            engine.fillRectangle(xPos, listY, boxWidth, boxHeight, EngineFrame.SKYBLUE);
            engine.drawRectangle(xPos, listY, boxWidth, boxHeight, EngineFrame.DARKBLUE);

            String value = list.get(i);
            engine.drawText(
                value, 
                xPos + boxWidth / 2 - 9, 
                listY + 22, 
                18, 
                EngineFrame.BLACK);
        }
        
        // Info
        engine.drawText(
            "Elementos: " + list.size() + " / " + maxSize, 
            listX, 
            listY + boxHeight + 30, 
            16, 
            EngineFrame.DARKGRAY
        );
        
    }
}