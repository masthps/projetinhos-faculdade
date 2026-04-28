package buscaemtrie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {
    
    private class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord = false;
        // Guarda as posições exatas onde essa palavra aparece no texto
        List<Integer> indices = new ArrayList<>(); 
    }

    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Insere uma palavra e a posição inicial dela no texto
    public void insert(String word, int startIndex) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current.children.putIfAbsent(ch, new TrieNode());
            current = current.children.get(ch);
        }
        current.isEndOfWord = true;
        current.indices.add(startIndex);
    }

    // Busca a palavra exata e retorna a lista de índices
    public List<Integer> search(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            TrieNode node = current.children.get(ch);
            if (node == null) {
                return new ArrayList<>(); // Caminho não existe
            }
            current = node;
        }
        // Retorna os índices apenas se for uma palavra completa
        if (current.isEndOfWord) {
            return current.indices;
        }
        return new ArrayList<>();
    }
}