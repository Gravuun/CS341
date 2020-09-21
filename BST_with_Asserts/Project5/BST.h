#pragma once

#include <assert.h>
#include<iostream>
#include <vector>
#include <unordered_set>

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
	int size;

	void cleanupTree(Node* target);
	void insertNode(Node* target, int value);
	int findMin(Node* target) const;
	Node* deleteNode(Node* target, int value);
	bool contains(Node* target, int value) const;
	void printTree(Node* target) const;
	void catalog(Node* target, std::unordered_set<int>& contents) const;
public:
	BST();
	BST(int value);
	BST(std::vector<int> values);
	~BST();
	bool isEmpty() const;
	void insertNode(int value);
	void deleteNode(int value);
	bool contains(int value) const;
	void printTree() const;
	bool equals(BST* rhs) const;
	int get_size() const;
};

