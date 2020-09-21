#include "BST.h"
#include <stdlib.h>

using namespace std;

int main() {
	
	vector<int> testVector;
	BST* tree = new BST();

	assert(tree->isEmpty());

	for (int i = 0; i < 15; i++) {
		int insertion = rand() % 100;
		testVector.push_back(insertion);
		tree->insertNode(insertion);
	}

	assert(!tree->isEmpty());

	BST* test = new BST(testVector);

	assert(!test->isEmpty());

	test->insertNode(1);
	test->insertNode(25);
	test->insertNode(50);
	test->insertNode(75);
	test->insertNode(99);

	assert(test->contains(1) && test->contains(25) && test->contains(50) && test->contains(75) && test->contains(99));

	assert(!(tree->equals(test)));

	test->printTree();

	test->deleteNode(1);
	test->deleteNode(25);
	test->deleteNode(50);
	test->deleteNode(75);
	test->deleteNode(99);

	assert(!test->contains(1) && !test->contains(25) && !test->contains(50) && !test->contains(75) && !test->contains(99));

	assert(tree->equals(test));

	cout << endl << endl;

	test->printTree();
	
	cin.get();
}