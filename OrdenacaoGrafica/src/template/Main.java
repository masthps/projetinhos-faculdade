package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends EngineFrame {
    
    private static final java.awt.Color FUNDO_DARK = new java.awt.Color(40, 40, 40);

    private List<GuiButton> menuButtons;
    private GuiButton btnSelection;
    private GuiButton btnInsertion;
    private GuiButton btnShell;
    private GuiButton btnMerge;
    private GuiButton btnCounting;
    private GuiButton btnBucket;
    
    private String currentScreen;
    
    private List<int[]> arraysSelection;
    private List<int[]> arraysInsertion;
    private List<int[]> arraysShell;
    private List<int[]> arraysMerge;
    private List<int[]> arraysCounting;
    private List<int[]> arraysBucket;
    
    private int copiaAtual;
    private double tempoParaMudar; 
    private double contadorTempo;
    private int quantidadeNumeros;
    
    public Main() {
        super(
            1200,                
            800,                 
            "Simulador de Algoritmos", 
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
        
        tempoParaMudar = 0.04; 
        quantidadeNumeros = 50; 
        
        useAsDependencyForIMGUI();
        
        menuButtons = new ArrayList<>();
        
        int centerX = getScreenWidth() / 2;
        int startY = 300;
        int buttonWidth = 300;
        int buttonHeight = 60;
        int spacingY = 80;
        int spacingX = 350; 
        
        btnSelection = new GuiButton(centerX - spacingX/2 - buttonWidth/2, startY, buttonWidth, buttonHeight, "SELECTION SORT");
        btnShell = new GuiButton(centerX - spacingX/2 - buttonWidth/2, startY + spacingY, buttonWidth, buttonHeight, "SHELL SORT");
        btnCounting = new GuiButton(centerX - spacingX/2 - buttonWidth/2, startY + spacingY * 2, buttonWidth, buttonHeight, "COUNTING SORT");
        
        btnInsertion = new GuiButton(centerX + spacingX/2 - buttonWidth/2, startY, buttonWidth, buttonHeight, "INSERTION SORT");
        btnMerge = new GuiButton(centerX + spacingX/2 - buttonWidth/2, startY + spacingY, buttonWidth, buttonHeight, "MERGE SORT");
        btnBucket = new GuiButton(centerX + spacingX/2 - buttonWidth/2, startY + spacingY * 2, buttonWidth, buttonHeight, "BUCKET SORT");
        
        menuButtons.add(btnSelection);
        menuButtons.add(btnInsertion);
        menuButtons.add(btnShell);
        menuButtons.add(btnMerge);
        menuButtons.add(btnCounting);
        menuButtons.add(btnBucket);
        

        int[] arrayPai = new int[quantidadeNumeros];
        for (int i = 0; i < quantidadeNumeros; i++) {
            arrayPai[i] = i + 1; 
        }
        
        Random rand = new Random();
        for (int i = 0; i < quantidadeNumeros; i++) {
            int p = rand.nextInt(quantidadeNumeros);
            int temp = arrayPai[i];
            arrayPai[i] = arrayPai[p];
            arrayPai[p] = temp;
        }
        
        arraysSelection = new ArrayList<>();
        arraysInsertion = new ArrayList<>();
        arraysShell = new ArrayList<>();
        arraysMerge = new ArrayList<>();
        arraysCounting = new ArrayList<>();
        arraysBucket = new ArrayList<>();
        
        selectionSort(arrayPai.clone());
        insertionSort(arrayPai.clone());
        shellSort(arrayPai.clone());
        mergeSort(arrayPai.clone());
        countingSort(arrayPai.clone());
        bucketSort(arrayPai.clone());
        
        currentScreen = "menu";
    }

    @Override
    public void update( double delta ) {
        if (currentScreen.equals("menu")) {
            for (GuiButton button : menuButtons) {
                button.update(delta);
            }
            
            if (btnSelection.isMousePressed()) trocarTela("selection");
            if (btnInsertion.isMousePressed()) trocarTela("insertion");
            if (btnShell.isMousePressed()) trocarTela("shell");
            if (btnMerge.isMousePressed()) trocarTela("merge");
            if (btnCounting.isMousePressed()) trocarTela("counting");
            if (btnBucket.isMousePressed()) trocarTela("bucket");
            
        } else {
            if (isKeyPressed(KEY_TAB)) {
                currentScreen = "menu";
            } else {
                contadorTempo += delta;
                if (contadorTempo >= tempoParaMudar) {
                    contadorTempo = 0;
                    int maxFrames = getListaAtual().size();
                    if (copiaAtual < maxFrames - 1) {
                        copiaAtual++;
                    }
                }
            }
        }
    }
    
    private void trocarTela(String novaTela) {
        currentScreen = novaTela;
        copiaAtual = 0;
        contadorTempo = 0;
    }
    
    private List<int[]> getListaAtual() {
        switch (currentScreen) {
            case "selection": return arraysSelection;
            case "insertion": return arraysInsertion;
            case "shell": return arraysShell;
            case "merge": return arraysMerge;
            case "counting": return arraysCounting;
            case "bucket": return arraysBucket;
            default: return new ArrayList<>();
        }
    }

    @Override
    public void draw() {
        clearBackground( FUNDO_DARK );

        if (currentScreen.equals("menu")) {
            drawMenu();
        } else {
            drawAlgoritmo();
        }
    }
    
    private void drawMenu() {
        drawText("Simulador de Algoritmos", getScreenWidth() / 2 - 250, 100, 40, WHITE);
        drawText("Selecione um método de ordenação", getScreenWidth() / 2 - 180, 160, 20, LIGHTGRAY);
        
        for (GuiButton button : menuButtons) {
            button.draw();
        }
    }
    
    private void drawAlgoritmo() {
        
        java.awt.Color cor = WHITE;
        String titulo = "";
        
        switch (currentScreen) {
            case "selection": cor = new java.awt.Color(0, 255, 255); titulo = "Selection Sort"; break; 
            case "insertion": cor = RED; titulo = "Insertion Sort"; break;
            case "shell": cor = GREEN; titulo = "Shell Sort"; break;
            case "merge": cor = MAGENTA; titulo = "Merge Sort"; break;
            case "counting": cor = ORANGE; titulo = "Counting Sort"; break;
            case "bucket": cor = YELLOW; titulo = "Bucket Sort"; break;
        }
        
        drawText(titulo, getScreenWidth() / 2 - (titulo.length() * 8), 80, 30, cor);
        
        List<int[]> listaAtual = getListaAtual();
        drawText("Frame: " + (copiaAtual + 1) + " / " + listaAtual.size(), getScreenWidth() / 2 - 80, 130, 20, LIGHTGRAY);
        if (copiaAtual == listaAtual.size() - 1) {
            drawText("ORDENADO!", getScreenWidth() / 2, 160, 20, GREEN);
        }
        
        if (!listaAtual.isEmpty()) {
            int[] array = listaAtual.get(copiaAtual);
            
            int larguraBarra = 16;
            int espaco = 4;
            int fatorAltura = 10; 
            
            int larguraTotal = (array.length * larguraBarra) + ((array.length - 1) * espaco);
            int startX = (getScreenWidth() - larguraTotal) / 2;
            int startY = 700; 

            for ( int i = 0; i < array.length; i++ ) {
                if (array[i] == 0) continue; 
                
                int altura = array[i] * fatorAltura;
                int x = startX + (larguraBarra + espaco) * i;
                int y = startY - altura;
                
                fillRectangle(x, y, larguraBarra, altura, cor);
            }
            
            fillRectangle(startX - 20, startY, larguraTotal + 40, 5, WHITE);
        }
    }

    private void selectionSort( int[] array ) {
        copiarArray( array, arraysSelection );
        for ( int i = 0; i < array.length; i++ ) {
            int min = i;
            for ( int j = i + 1; j < array.length; j++ ) {
                if ( array[j] < array[min] ) min = j;
            }
            copiarArray( array, arraysSelection );
            swap( array, i, min );
            copiarArray( array, arraysSelection );
        }
    }
    
    private void insertionSort( int[] array ) {
        copiarArray( array, arraysInsertion );
        for ( int i = 1; i < array.length; i++ ) {
            int chave = array[i];  
            int j = i - 1;         
            copiarArray( array, arraysInsertion );
            while ( j >= 0 && array[j] > chave ) {
                array[j + 1] = array[j];  
                j = j - 1;                
                copiarArray( array, arraysInsertion );
            }
            array[j + 1] = chave;
            copiarArray( array, arraysInsertion );
        }
    }
    
    private void shellSort(int[] array) {
        copiarArray( array, arraysShell ); 
        int h = 1;
        while (h < array.length / 3) h = 3 * h + 1;
        while (h >= 1) {
            for (int i = h; i < array.length; i++) {
                int j = i;
                copiarArray(array, arraysShell);
                while (j >= h && array[j - h] > array[j]) {
                    swap(array, j - h, j);
                    j = j - h;
                    copiarArray(array, arraysShell);
                }
            }
            h = h / 3; 
        }
    }

    private void mergeSort( int[] array ) {
        copiarArray( array, arraysMerge ); 
        mergeSortRecursivo(array, 0, array.length - 1);
    }
    
    private void mergeSortRecursivo( int[] array, int esquerda, int direita ) {
        if ( esquerda < direita ) {
            int meio = ( esquerda + direita ) / 2;
            mergeSortRecursivo( array, esquerda, meio );
            mergeSortRecursivo( array, meio + 1, direita );
            merge( array, esquerda, meio, direita );
        }
    }

    private void merge( int[] array, int esquerda, int meio, int direita ) {
        int[] arrayEsquerdo = new int[meio - esquerda + 1];
        int[] arrayDireito = new int[direita - meio];
        for ( int i = 0; i < arrayEsquerdo.length; i++ ) arrayEsquerdo[i] = array[esquerda + i];
        for ( int j = 0; j < arrayDireito.length; j++ ) arrayDireito[j] = array[meio + 1 + j];

        int i = 0, j = 0, k = esquerda;
        while ( i < arrayEsquerdo.length && j < arrayDireito.length ) {
            if ( arrayEsquerdo[i] <= arrayDireito[j] ) {
                array[k++] = arrayEsquerdo[i++];
            } else {
                array[k++] = arrayDireito[j++];
            }
            copiarArray( array, arraysMerge );
        }
        while ( i < arrayEsquerdo.length ) {
            array[k++] = arrayEsquerdo[i++];
            copiarArray( array, arraysMerge );
        }
        while ( j < arrayDireito.length ) {
            array[k++] = arrayDireito[j++];
            copiarArray( array, arraysMerge );
        }
    }

    private void countingSort(int[] array) {
        copiarArray(array, arraysCounting);
        if (array.length == 0) return;

        int max = array[0];
        for (int i = 1; i < array.length; i++) if (array[i] > max) max = array[i];

        int[] count = new int[max + 1];
        for (int j : array) count[j]++;
        
        for (int i = 1; i <= max; i++) count[i] += count[i - 1];

        int[] originalClone = array.clone();
        
        for(int i = 0; i < array.length; i++) array[i] = 0;
        copiarArray(array, arraysCounting);

        for (int i = originalClone.length - 1; i >= 0; i--) {
            int valor = originalClone[i];
            int posicaoDestino = count[valor] - 1;
            
            array[posicaoDestino] = valor;
            count[valor]--;
            
            copiarArray(array, arraysCounting);
        }
    }

    private void bucketSort(int[] array) {
        copiarArray(array, arraysBucket);
        if (array.length == 0) return;

        int max = array[0];
        for (int i = 1; i < array.length; i++) if (array[i] > max) max = array[i];

        int numeroDeBaldes = 5; 
        List<List<Integer>> baldes = new ArrayList<>(numeroDeBaldes);
        for (int i = 0; i < numeroDeBaldes; i++) baldes.add(new ArrayList<>());

        for (int valor : array) {
            int indiceBalde = (valor * numeroDeBaldes) / (max + 1);
            if (indiceBalde >= numeroDeBaldes) indiceBalde = numeroDeBaldes - 1;
            baldes.get(indiceBalde).add(valor);
        }

        int k = 0;
        for (List<Integer> balde : baldes) {
            for (int valor : balde) {
                array[k++] = valor;
                copiarArray(array, arraysBucket);
            }
        }

        for (int i = 1; i < array.length; i++) {
            int chave = array[i];  
            int j = i - 1;         
            while (j >= 0 && array[j] > chave) {
                array[j + 1] = array[j];  
                j = j - 1;                
                copiarArray(array, arraysBucket);
            }
            array[j + 1] = chave;
            copiarArray(array, arraysBucket);
        }
    }

    private void copiarArray(int[] array1, List<int[]> destino) {
        int[] copia = new int[array1.length];
        System.arraycopy(array1, 0, copia, 0, array1.length);
        destino.add(copia);
    }

    private void swap(int[] array, int p1, int p2) {
        int temp = array[p1];
        array[p1] = array[p2];
        array[p2] = temp;
    }

    public static void main( String[] args ) {
        new Main();
    }
}