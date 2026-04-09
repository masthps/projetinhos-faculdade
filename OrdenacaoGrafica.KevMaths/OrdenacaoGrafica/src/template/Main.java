package template;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
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
    
    /**
    private int[] array;
    private List<int[]> arrays;
    private int copiaAtual;
    
    private double tempoParaMudar;
    private double contadorTempo;
    */
    
    private List<int[]> arraysSelection;
    private List<int[]> arraysInsertion;
    private List<int[]> arraysShell;
    private List<int[]> arraysMerge;
    
    private int copiaAtual;
    
    private double tempoParaMudar;
    private double contadorTempo;
    
    
    public Main() {
        
        super(
            800,                 // largura                      / width
            600,                 // algura                       / height
            "Projeto Ordenações",      // título                       / title
            60,                  // quadros por segundo desejado / target FPS
            true,                // suavização                   / antialiasing
            false,               // redimensionável              / resizable
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
        
        /**array = new int[] { 7, 3, 1, 2, 9, 4, 6, 8, 5, 10 };
        arrays = new ArrayList<>();
        selectionSort( array );
        tempoParaMudar = 0.5;
        */
        
        int[] arrayPai = new int [] { 7, 3, 1, 2, 9, 4, 6, 8, 5, 10 };
        
        arraysSelection = new ArrayList<>();
        arraysInsertion = new ArrayList<>();
        arraysShell = new ArrayList<>();
        arraysMerge = new ArrayList<>();
        
        
        int[] arraySelection = arrayPai.clone(); 
        selectionSort( arraySelection );
        
        int[] arrayInsertion = arrayPai.clone();
        insertionSort( arrayInsertion );
        
        int[] arrayShell = arrayPai.clone();
        shellSort( arrayShell );
        
        int[] arrayMerge = arrayPai.clone();  
        mergeSort( arrayMerge );
        
        tempoParaMudar = 0.8;
        copiaAtual = 0;
        
    }
    
    private void selectionSort( int[] array ) {
        
        copiarArray( array, arraysSelection );
        
        for ( int i = 0; i < array.length; i++ ) {
            int min = i;
            
            for ( int j = i + 1; j < array.length; j++ ) {
                if ( array[j] < array[min] ) {
                    min = j;
                }
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
        int length = array.length;

        while (h < length / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < length; i++) {
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

        for ( int i = 0; i < arrayEsquerdo.length; i++ ) {
            arrayEsquerdo[i] = array[esquerda + i];
        }
        for ( int j = 0; j < arrayDireito.length; j++ ) {
            arrayDireito[j] = array[meio + 1 + j];
        }

        int i = 0, j = 0, k = esquerda;

        while ( i < arrayEsquerdo.length && j < arrayDireito.length ) {
            if ( arrayEsquerdo[i] <= arrayDireito[j] ) {
                array[k] = arrayEsquerdo[i];
                i++;
            } else {
                array[k] = arrayDireito[j];
                j++;
            }
            k++;

            copiarArray( array, arraysMerge );
        }

        while ( i < arrayEsquerdo.length ) {
            array[k] = arrayEsquerdo[i];
            i++;
            k++;
            copiarArray( array, arraysMerge );
        }

        while ( j < arrayDireito.length ) {
            array[k] = arrayDireito[j];
            j++;
            k++;
            copiarArray( array, arraysMerge );
        }
    }

    
    /**private void copiar(int[] array1) {
        int[] copia = new int[array1.length];
        System.arraycopy(array1, 0, copia, 0, array1.length);
        arrays.add( copia );
    }
    */
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
        
        contadorTempo += delta;
        
        if ( contadorTempo >= tempoParaMudar ) {
            contadorTempo = 0;
                
            int maxFrames = 0;
            maxFrames = Math.max(maxFrames, arraysSelection.size());
            maxFrames = Math.max(maxFrames, arraysInsertion.size());
            maxFrames = Math.max(maxFrames, arraysShell.size());
            maxFrames = Math.max(maxFrames, arraysMerge.size());
            
            if ( copiaAtual < maxFrames - 1 ){
                copiaAtual++;
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
    
        int tamanho = 18;
        int espaco = 2;
    
        // Layout de cima
        int xIniSelection = 30;
        int xIniInsertion = 420;
        int yIniCima = 250;     

        // Layout de baixo
        int xIniShell = 30;       
        int xIniMerge = 420;      
        int yIniBaixo = 500;      

        drawText( "Selection Sort", xIniSelection, 40, 16, BLACK );   
        drawText( "Insertion Sort", xIniInsertion, 40, 16, BLACK );  
        
        drawText( "Shell Sort", xIniShell, 280, 16, BLACK );          
        drawText( "Merge Sort", xIniMerge, 280, 16, BLACK );   

        
        // Fileira de cima
        int frameSelection = Math.min( copiaAtual, arraysSelection.size() - 1 );
        int[] arraySelection = arraysSelection.get( frameSelection );
        desenharArray( arraySelection, xIniSelection, yIniCima, tamanho, espaco, BLUE );

        int frameInsertion = Math.min( copiaAtual, arraysInsertion.size() - 1 );
        int[] arrayInsertion = arraysInsertion.get( frameInsertion );
        desenharArray( arrayInsertion, xIniInsertion, yIniCima, tamanho, espaco, RED );

        // Fileira de baixo
        int frameShell = Math.min( copiaAtual, arraysShell.size() - 1 );
        int[] arrayShell = arraysShell.get( frameShell );
        desenharArray( arrayShell, xIniShell, yIniBaixo, tamanho, espaco, GREEN );

        int frameMerge = Math.min( copiaAtual, arraysMerge.size() - 1 );
        int[] arrayMerge = arraysMerge.get( frameMerge );
        desenharArray( arrayMerge, xIniMerge, yIniBaixo, tamanho, espaco, MAGENTA );

        
        drawText( "Frame: " + ( copiaAtual + 1 ), 10, getScreenHeight() - 80, 12, GRAY );
        drawText( "Selection frames: " + arraysSelection.size(), 10, getScreenHeight() - 65, 12, BLUE );
        drawText( "Insertion frames: " + arraysInsertion.size(), 10, getScreenHeight() - 50, 12, RED );
        drawText( "Shell frames: " + arraysShell.size(), 10, getScreenHeight() - 35, 12, GREEN );
        drawText( "Merge frames: " + arraysMerge.size(), 10, getScreenHeight() - 20, 12, MAGENTA );

    }
        
        
    private void desenharArray( int[] array, int xIni, int yIni, int tamanho, int espaco, java.awt.Color cor ) {
        for ( int i = 0; i < array.length; i++ ) {
            int altura = tamanho * array[i];
            
            fillRectangle(
                xIni + (tamanho + espaco) * i,
                yIni - altura,
                tamanho,
                altura, 
                cor
            );
            
            drawText(String.valueOf( array[i]), xIni + (tamanho + espaco) * i + tamanho/4, yIni - altura - 15, 10, BLACK );
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
