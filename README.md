# java-huffman-app

# Huffman Encoding in Java

A Java application that implements Huffman Encoding, a lossless data compression algorithm used to reduce the size of text data.

## Overview
This project demonstrates the implementation of the Huffman Encoding algorithm. The program analyzes character frequencies in a given input, constructs a Huffman Tree, and generates optimal binary codes for each character to achieve compression.

Huffman Encoding is widely used in file compression formats and showcases core computer science concepts such as greedy algorithms, trees, and recursion.

## Key Concepts
- Greedy Algorithms
- Binary Trees
- Data Compression
- Frequency Analysis
- Recursive Traversal

## How It Works
1. Reads input text
2. Calculates the frequency of each character
3. Builds a Huffman Tree using character frequencies
4. Assigns binary codes to characters
5. Encodes the input using the generated codes

## Technologies Used
- Java
- Object-Oriented Programming
- Data Structures (Trees, Maps)

## Project Structure
src/
└── HuffmanApp.java

## How to Run

### Compile
javac src/HuffmanApp.java

### Run
java src.HuffmanApp
