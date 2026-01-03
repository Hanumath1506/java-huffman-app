import java.io.*;
import java.util.PriorityQueue;

public class HuffmanApp {
    public static void main(String[] args) {
        try {
            // File path to the input file
            String filePath = "/Users/hanumathmandadi/Desktop/captmidn.txt";

            // Step 1: Read file and build frequency table
            int[] frequencyTable = buildFrequencyTable(filePath);

            // Step 2: Build Huffman tree
            HuffmanTree huffmanTree = new HuffmanTree();
            huffmanTree.buildTree(frequencyTable);

            // Step 3: Print the Huffman code tree
            System.out.println("Huffman Code Tree:");
            huffmanTree.printCodeTable();

            // Step 4: Encode the contents of the file
            String originalText = readFile(filePath);
            String encodedText = huffmanTree.encode(originalText);
            System.out.println("\nEncoded Text: " + encodedText);

            // Step 5: Decode the binary string back to the original text
            String decodedText = huffmanTree.decode(encodedText);
            System.out.println("\nDecoded Text: " + decodedText);

            // Verify the decoded text matches the original
            if (originalText.equals(decodedText)) {
                System.out.println("\nSuccess: The decoded text matches the original.");
            } else {
                System.out.println("\nError: The decoded text does not match the original.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to build a frequency table from the file
    public static int[] buildFrequencyTable(String filePath) throws IOException {
        int[] frequencyTable = new int[128]; // Supports ASCII characters
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                frequencyTable[ch]++;
            }
        }
        return frequencyTable;
    }

    // Helper method to read the entire content of the file as a string
    public static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                content.append((char) ch);
            }
        }
        return content.toString();
    }
}

// HuffmanTree class
class HuffmanTree {
    private HuffmanNode root;
    private String[] codeTable = new String[128]; // Array to store codes for each character (ASCII)

    // Build Huffman tree from character frequencies
    public void buildTree(int[] freqTable) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();

        // Create leaf nodes for each character with a non-zero frequency
        for (int i = 0; i < freqTable.length; i++) {
            if (freqTable[i] > 0) {
                queue.add(new HuffmanNode((char) i, freqTable[i]));
            }
        }

        // Build the Huffman Tree
        while (queue.size() > 1) {
            HuffmanNode left = queue.poll();
            HuffmanNode right = queue.poll();
            HuffmanNode parent = new HuffmanNode(left.frequency + right.frequency, left, right);
            queue.add(parent);
        }

        root = queue.poll(); // Root of the Huffman tree
        buildCodeTable(root, ""); // Generate codes for each character
    }

    // Recursive helper to build code table
    private void buildCodeTable(HuffmanNode node, String code) {
        if (node == null) return;
        if (node.left == null && node.right == null) { // Leaf node
            codeTable[node.character] = code;
        }
        buildCodeTable(node.left, code + "0");
        buildCodeTable(node.right, code + "1");
    }

    // Encode text using the Huffman code table
    public String encode(String text) {
        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            encoded.append(codeTable[c]);
        }
        return encoded.toString();
    }

    // Decode a binary string back to the original text
    public String decode(String encodedText) {
        StringBuilder decoded = new StringBuilder();
        HuffmanNode currentNode = root;
        for (char bit : encodedText.toCharArray()) {
            currentNode = (bit == '0') ? currentNode.left : currentNode.right;
            if (currentNode.left == null && currentNode.right == null) { // Leaf node
                decoded.append(currentNode.character);
                currentNode = root;
            }
        }
        return decoded.toString();
    }

    // Print the Huffman code table
    public void printCodeTable() {
        for (int i = 0; i < codeTable.length; i++) {
            if (codeTable[i] != null) {
                System.out.printf("%c - %s\n", (char) i, codeTable[i]);
            }
        }
    }
}

// HuffmanNode class
class HuffmanNode implements Comparable<HuffmanNode> {
    char character;
    int frequency;
    HuffmanNode left, right;

    HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.character = '\0'; // Internal node does not store a character
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return this.frequency - o.frequency;
    }
}

