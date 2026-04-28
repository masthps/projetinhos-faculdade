package buscaemtrie;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class JanelaPrincipal extends javax.swing.JFrame {

    private Trie trie;
    private final String textoBase = "o rato roeu a roupa do rei de roma e a rainha com raiva roeu o resto e resolveu remendar a roupa do rei";

    public JanelaPrincipal() {
        initComponents();
        
        listResultado.setModel(new DefaultListModel<String>());
        textPaneTexto.setText(textoBase);
        
        trie = new Trie();
        construirTrie();
    }
    
    private void construirTrie() {
        String[] palavras = textoBase.split("\\s+");
        int currentIndex = 0;
        
        for (String palavra : palavras) {
            trie.insert(palavra, currentIndex);
            currentIndex += palavra.length() + 1; // +1 pelo espaço
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        scrollPaneTexto = new javax.swing.JScrollPane();
        textPaneTexto = new javax.swing.JTextPane();
        painelPesquisa = new javax.swing.JPanel();
        lblBuscarPor = new javax.swing.JLabel();
        txtBuscarPor = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        scrollPaneResultado = new javax.swing.JScrollPane();
        listResultado = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Busca em Trie");

        scrollPaneTexto.setViewportView(textPaneTexto);

        painelPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar"));

        lblBuscarPor.setText("Buscar por:");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);

        listResultado.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollPaneResultado.setViewportView(listResultado);

        javax.swing.GroupLayout painelPesquisaLayout = new javax.swing.GroupLayout(painelPesquisa);
        painelPesquisa.setLayout(painelPesquisaLayout);
        painelPesquisaLayout.setHorizontalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPaneResultado)
                    .addGroup(painelPesquisaLayout.createSequentialGroup()
                        .addComponent(lblBuscarPor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscarPor))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPesquisaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBuscar)))
                .addContainerGap())
        );
        painelPesquisaLayout.setVerticalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscarPor)
                    .addComponent(txtBuscarPor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPaneResultado, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneTexto, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPaneTexto))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        String valor = txtBuscarPor.getText().trim();
        
        DefaultListModel<String> model = (DefaultListModel<String>) listResultado.getModel();
        model.clear();
        
        Highlighter highlighter = textPaneTexto.getHighlighter();
        highlighter.removeAllHighlights();
        
        if (valor.isEmpty()) return;

        List<Integer> indices = trie.search(valor);
        
        if (indices.isEmpty()) {
            model.addElement("Nenhum resultado para: '" + valor + "'");
        } else {
            try {
                Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
                
                for (int index : indices) {
                    model.addElement(String.format("'%s' achado no índice: %d", valor, index));
                    highlighter.addHighlight(index, index + valor.length(), painter);
                }
            } catch (BadLocationException exc) {
                exc.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        FlatDarkLaf.setup();
        java.awt.EventQueue.invokeLater(() -> new JanelaPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnBuscar;
    private javax.swing.JLabel lblBuscarPor;
    private javax.swing.JList<String> listResultado;
    private javax.swing.JPanel painelPesquisa;
    private javax.swing.JScrollPane scrollPaneResultado;
    private javax.swing.JScrollPane scrollPaneTexto;
    private javax.swing.JTextPane textPaneTexto;
    private javax.swing.JTextField txtBuscarPor;
    // End of variables declaration                   
}
