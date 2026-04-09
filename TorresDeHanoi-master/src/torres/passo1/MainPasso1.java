package torres.passo1;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.core.utils.ColorUtils;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.math.Vector2;
import torres.Disco;
import torres.Haste;

/**
 * Simulador do Jogo Torres de Hanói.
 * 
 * Material complementar da disciplina de estruturas de dados para ensino de
 * recursão e aplicação de pilhas.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class MainPasso1 extends EngineFrame {
    
    /**
     * Implementar movimentação de discos e botões.
     */
    
    private Haste h1;
    private Haste h2;
    private Haste h3;
    private int quantidadeDiscos;
    
    private GuiButton btn12;
    private GuiButton btn13;
    
    private GuiButton btn21;
    private GuiButton btn23;
    
    private GuiButton btn31;
    private GuiButton btn32;
    
    public MainPasso1() {
        super( 800, 450, "Jogo Torres de Hanói", 60, true );
    }
    
    @Override
    public void create() {
        
        useAsDependencyForIMGUI();
        
        int margem = 50;
        Vector2 dim = new Vector2( 20, 200 );
        Vector2 centro = new Vector2( getScreenWidth() / 2, getScreenHeight() - margem );
        
        int espacamento = 250;
        
        h1 = new Haste( centro.x - espacamento, centro.y, dim.x, dim.y, BLACK );
        h2 = new Haste( centro.x, centro.y, dim.x, dim.y, BLACK );
        h3 = new Haste( centro.x + espacamento, centro.y, dim.x, dim.y, BLACK );
        
        preparar();
        
        btn12 = new GuiButton(100, 100, 100, 20, "-> 2");
        btn13 = new GuiButton(btn12.getBounds().x, 130 ,100, 20, "-> 3");
        
    }
    
    @Override
    public void update( double delta ) {
        
        h1.atualizar();
        h2.atualizar();
        h3.atualizar();
        
        btn12.update(delta);
        btn13.update(delta);
        
        if( btn12.isMousePressed() ) {
            moverDisco( h1, h2 );
        }
        if( btn13.isMousePressed() ) {
            moverDisco( h1, h3 );
        }

        
    }
    
    @Override
    public void draw() {
        
        clearBackground( WHITE );
        
        h1.desenhar( this );
        h2.desenhar( this );
        h3.desenhar( this );
        
        fillRectangle( 50, h1.getPos().y, getScreenWidth() - 100, 20, BLACK );
        
        btn12.draw();
        btn13.draw();
        
    }
    
    private void preparar() {
        
        quantidadeDiscos = 9;
        
        h1.limpar();
        h2.limpar();
        h3.limpar();
        
        int ini = 2;
        int fim = quantidadeDiscos + ini - 1;
        
        for ( int i = fim; i >= ini; i-- ) {
            double porc = ( (double) ( fim - i ) / quantidadeDiscos );            
            h1.empilhar( 
                new Disco( 
                    i - ini + 1,
                    0, 0, i * 20, 20, 
                    ColorUtils.colorFromHSV( porc * 360, 1, 1 )
                )
            );
        }
        
        h1.atualizar();
        h2.atualizar();
        h3.atualizar();
        
    }
    
    /**
     * Move um disco da haste de origem para a haste de destino.
     * 
     * A haste de origem não pode estar vazia e o disco que vai ser movido
     * não pode ter diâmetro (largura do retângulo) maior que o que está no
     * topo da haste de destino. Se a hasta de destino não possuir discos,
     * qualquer um pode ser movido para a mesma.
     */
    private void moverDisco( Haste origem, Haste destino ) {
        
        if ( !origem.estaVazia() ) {
            
            if ( destino.estaVazia() ) {
                destino.empilhar( origem.desempilhar() );
            } else {
                Disco dOrigem = origem.verTopo();
                Disco dDestino = destino.verTopo();
            
                if( dOrigem.getDim().x < dDestino.getDim().x ) {
                    destino.empilhar( origem.desempilhar() );
                }
            }
        
        } 
        
    }
    
    public static void main( String[] args ) {
        new MainPasso1();
    }
    
}
