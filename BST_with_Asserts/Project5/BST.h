#pragma once

#include <assert.h>
#include<iostream>
#include <vector>

struct Node {
	int value;
	Node* right;
	Node* left;

	Node(int value) {
		this->value = value;
		this->right = nullptr;
		this->left = nullptr;
	}
};

class BST
{
private:
	Node* root;

	void cleanupTree(Node* target);
	void insertNode(Node* target, int value);
	int findMin(Node* target);
	Node* deleteNode(Node* target, int value);
	bool contains(Node* target, int value);
	void printTree(Node* target);
public:
	BST();
	BST(int value);
	BST(std::vector<int> values);
	~BST();
	bool isEmpty();
	void insertNode(int value);
	void deleteNode(int value);
	bool contains(int value);
	void printTree();
};

