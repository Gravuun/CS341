#include "BST.h"

BST::BST()
{
	this->root = nullptr;
	size = 0;
}

BST::BST(int value) {
	root = new Node(value);
	size = 1;
}

BST::BST(std::vector<int> values) {
	if (values.empty()) {
		root = nullptr;
		return;
	}
	root = new Node(values[0]);
	for (unsigned int i = 1; i < values.size(); i++) {
		insertNode(root, values[i]);
	}

	size = values.size();
}


BST::~BST()
{
	cleanupTree(root);
}

// DFS for delete so that we don't orphan any garbage
void BST::cleanupTree(Node* target) {
	if (target->left != nullptr) {
		cleanupTree(target->left);
	}
	if (target->right != nullptr) {
		cleanupTree(target->right);
	}

	delete target;
}

bool BST::isEmpty() const {
	return (root == nullptr);
}

void BST::insertNode(int value) {
	if (isEmpty()) {
		root = new Node(value);
	}

	else if (contains(value)) {
		// no duplicates in tree
		return;
	}

	else {
		insertNode(root, value);
	}
	size++;
}

void BST::insertNode(Node* target, int value) {
	if (target->value > value) {
		if (target->left == nullptr) {
			target->left = new Node(value);
		}
		else {
			insertNode(target->left, value);
		}
	}

	else {
		if (target->right == nullptr) {
			target->right = new Node(value);
		}
		else {
			insertNode(target->right, value);
		}
	}
}

void BST::deleteNode(int value) {
	if (isEmpty()) {
		return;
	}

	else if (!contains(value)) {
		return;
	}

	else {
		deleteNode(root, value);
		size--;
	}
}

int BST::findMin(Node* target) const {
	if (target->left != nullptr) {
		return findMin(target->left);
	}
	else {
		return target->value;
	}
}

Node* BST::deleteNode(Node* target, int value) {
	if (target->value != value) {
		if (target->value > value) {
			// If child is null then value does not exist in tree and our contains function failed
			assert(target->left != nullptr);
			target->left = deleteNode(target->left, value);
		}
		else if (target->value < value) {
			// If child is null then value does not exist in tree and our contains function failed
			assert(target->right != nullptr);
			target->right = deleteNode(target->right, value);
		}
	}

	// Found node with value
	else {
		// If we have two children, we replace target with the min of the right subtree
		// This is a value that is guarenteed to be between target->right and target->left
		if (target->left != nullptr && target->right != nullptr) {
			target->value = findMin(target->right);
			// Delete the node whose value we just moved into target, it should have max 1 child
			deleteNode(target->right, target->value);
		}
		else {
			Node* old = target;
			// If target has a child then we want to move target to point to that child
			if (target->right != nullptr) {
				// Validate that there is only one child
				assert(target->left == nullptr);
				target = target->right;
			}
			else if (target->left != nullptr) {
				// Validate that there is only one child
				assert(target->right == nullptr);
				target = target->left;
			}
			else {
				// if no choldren target is nullptr
				target = nullptr;
			}

			if (old == root) {
				root = target;
			}

			delete old;
		}
	}

	// return target so we can reassign parent node to proper child
	return target;
}

bool BST::contains(int value) const {
	if (isEmpty()) {
		return false;
	}

	// Call internal helper function
	return contains(root, value);
}

bool BST::contains(Node* target, int value) const {
	if (target->value == value) {
		return true;
	}
	else if (target->value > value && target->left != nullptr) {
		return contains(target->left, value);
	}
	else if(target->value < value && target->right != nullptr) {
		return contains(target->right, value);
	}
	else {
		return false;
	}
}

// BFS to print tree
void BST::printTree(Node* target) const {
	if (target->left != nullptr) {
		printTree(target->left);
	}
	std::cout << target->value << ' ';
	if (target->right != nullptr) {
		printTree(target->right);
	}
}

void BST::printTree() const {
	if (isEmpty()) {
		std::cout << "This tree is empty!";
		return;
	}
	else {
		printTree(root);
		std::cout << std::endl;
	}
}

bool BST::equals(BST* rhs) const {

	if (size != rhs->size) {
		return false;
	}

	std::unordered_set<int> contents;

	catalog(root, contents);

	for (auto itr = contents.begin(); itr != contents.end(); itr++) {
		if (!rhs->contains(*itr)) {
			return false;
		}
	}

	return true;
}

void BST::catalog(Node* target, std::unordered_set<int>& contents) const {
	if (target->left != nullptr) {
		catalog(target->left, contents);
	}
	assert(contents.find(target->value) == contents.cend());
	contents.insert(target->value);
	if (target->right != nullptr) {
		catalog(target->right, contents);
	}
}

int BST::get_size() const {
	return size;
}