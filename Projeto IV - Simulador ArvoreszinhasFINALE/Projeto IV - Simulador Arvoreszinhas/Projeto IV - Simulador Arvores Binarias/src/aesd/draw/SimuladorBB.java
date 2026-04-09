package aesd.draw;

import aesd.arvores.ArvoreBB;
import aesd.ds.interfaces.BinaryTree;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiTextField;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bastards
 */
public class SimuladorBB {
    
    private EngineFrame engine;
    private ArvoreBB<Integer, String> arvore;
    private Map<BinaryTree.Node<Integer, String>, NodePosition> nodePositions;
    
    private GuiTextField inputField;
    private GuiButton addButton;
    private GuiButton removeButton;
    private GuiButton randomButton;
    private GuiButton clearButton;
    
    private static final int NODE_RADIUS = 20;
    private static final int VERTICAL_SPACING = 60;
    private static final int MAX_LEVEL = 4;
    private static final Color NODE_COLOR = new Color(220, 53, 69);
    private static final Color NODE_BORDER_COLOR = new Color(132, 22, 32);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color LINE_COLOR = new Color(100, 100, 100);
    private static final Color LEVEL_COLOR = Color.WHITE;
    
    private static class NodePosition {
        double x;
        double y;
        int level;
        
        NodePosition(double x, double y, int level) {
            this.x = x;
            this.y = y;
            this.level = level;
        }
    }
    
    public SimuladorBB(EngineFrame engine) {
        this.engine = engine;
        this.arvore = new ArvoreBB<>();
        this.nodePositions = new HashMap<>();
        
        createComponents();
    }
    
    private void createComponents() {
        int x = 50;
        int y = 620;
        
        inputField = new GuiTextField(x, y, 200, 30, "");
        addButton = new GuiButton(x + 220, y, 150, 40, "Adicionar");
        removeButton = new GuiButton(x + 380, y, 150, 40, "Remover");
        randomButton = new GuiButton(x + 540, y, 150, 40, "Aleatorio");
        clearButton = new GuiButton(x + 700, y, 150, 40, "Limpar");
    }
    
    public void reset() {
        arvore.clear();
        inputField.setValue("");
    }
    
    public void update(double delta) {
        inputField.update(delta);
        addButton.update(delta);
        removeButton.update(delta);
        randomButton.update(delta);
        clearButton.update(delta);
        
        if (addButton.isMousePressed()) {
            String value = inputField.getValue().trim();
            int valor = Integer.parseInt(value);
            arvore.put(valor, String.valueOf(valor));
            inputField.setValue("");
        }
        
        if (removeButton.isMousePressed()) {
            String value = inputField.getValue().trim();
            int valor = Integer.parseInt(value);
            arvore.delete(valor);
            inputField.setValue("");
        }
        
        if (randomButton.isMousePressed()) {
            addRandomNumber();
        }
        
        if (clearButton.isMousePressed()) {
            arvore.clear();
        }
    }
    
    private void addRandomNumber() {
        int maxAttempts = 100;
        int attempts = 0;
        
        while (attempts < maxAttempts) {
            int random = (int) (Math.random() * 100) + 1;
            
            if (arvore.get(random) != null) {
                attempts++;
                continue;
            }
            
            if (!wouldExceedMaxLevel(random)) {
                arvore.put(random, String.valueOf(random));
                return;
            }
            
            attempts++;
        }
    }
    
    private boolean wouldExceedMaxLevel(int value) {
        BinaryTree.Node<Integer, String> parent = findInsertionParent(getRoot(), value);
        if (parent == null) {
            return false; 
        }
        
        int parentLevel = getNodeLevel(parent);
        return (parentLevel + 1) > MAX_LEVEL;
    }
    
    private BinaryTree.Node<Integer, String> findInsertionParent(BinaryTree.Node<Integer, String> node, int value) {
        if (node == null) return null;
        
        if (value < node.key) {
            return node.left == null ? node : findInsertionParent(node.left, value);
        } else if (value > node.key) {
            return node.right == null ? node : findInsertionParent(node.right, value);
        } else {
            return node; 
        }
    }
    
    public void draw() {
        engine.drawText("Valor:", 50, 600, 18, EngineFrame.WHITE);
        inputField.draw();
        
        addButton.draw();
        removeButton.draw();
        randomButton.draw();
        clearButton.draw();
        
        if (!arvore.isEmpty()) {
            nodePositions.clear();
            double startX = 540;
            double startY = 100;
            calculatePositions(getRoot(), startX, startY, 250, 0);
            drawTree(getRoot());
        }
    }
    
    private BinaryTree.Node<Integer, String> getRoot() {
        try {
            java.lang.reflect.Field rootField = ArvoreBB.class.getDeclaredField("root");
            rootField.setAccessible(true);
            return (BinaryTree.Node<Integer, String>) rootField.get(arvore);
        } catch (Exception e) {
            return null;
        }
    }
    
    private int getNodeLevel(BinaryTree.Node<Integer, String> node) {
        NodePosition pos = nodePositions.get(node);
        return pos != null ? pos.level : -1;
    }
    
    private void calculatePositions(BinaryTree.Node<Integer, String> node, 
                                    double x, double y, double horizontalSpacing, int currentLevel) {
        if (node == null || currentLevel > MAX_LEVEL) return;
        
        nodePositions.put(node, new NodePosition(x, y, currentLevel));
        
        double newSpacing = horizontalSpacing / 2.0;
        double nextY = y + VERTICAL_SPACING;
        int nextLevel = currentLevel + 1;
        
        if (node.left != null && nextLevel <= MAX_LEVEL) {
            calculatePositions(node.left, x - horizontalSpacing, nextY, newSpacing, nextLevel);
        }
        
        if (node.right != null && nextLevel <= MAX_LEVEL) {
            calculatePositions(node.right, x + horizontalSpacing, nextY, newSpacing, nextLevel);
        }
    }
    
    private void drawTree(BinaryTree.Node<Integer, String> node) {
        if (node == null) return;
        
        NodePosition pos = nodePositions.get(node);
        if (pos == null || pos.level > MAX_LEVEL) return;
        
        if (node.left != null) {
            NodePosition leftPos = nodePositions.get(node.left);
            if (leftPos != null && leftPos.level <= MAX_LEVEL) {
                engine.drawLine(pos.x, pos.y, leftPos.x, leftPos.y, LINE_COLOR);
                drawTree(node.left);
            }
        }
        
        if (node.right != null) {
            NodePosition rightPos = nodePositions.get(node.right);
            if (rightPos != null && rightPos.level <= MAX_LEVEL) {
                engine.drawLine(pos.x, pos.y, rightPos.x, rightPos.y, LINE_COLOR);
                drawTree(node.right);
            }
        }
        
        engine.fillCircle(pos.x, pos.y, NODE_RADIUS, NODE_COLOR);
        engine.drawCircle(pos.x, pos.y, NODE_RADIUS, NODE_BORDER_COLOR);
        
        String text = node.key.toString();
        int fontSize = 16;
        int textWidth = engine.measureText(text, fontSize);
        engine.drawText(text, pos.x - textWidth / 2.0, pos.y + (fontSize / 3.0), fontSize, TEXT_COLOR);
        
        String levelText = "h:" + pos.level;
        int levelTextWidth = engine.measureText(levelText, 12);
        engine.drawText(levelText, pos.x - levelTextWidth / 2.0, pos.y - NODE_RADIUS - 2, 12, LEVEL_COLOR);
    }
}