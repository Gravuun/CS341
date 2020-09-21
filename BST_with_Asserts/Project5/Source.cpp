#include "BST.h"
#include <stdlib.h>

using namespace std;

int main() {
	
	vector<int> testVector;


	for (int i = 0; i < 15; i++) {
		testVector.push_back(rand() % 10000);
	}

	BST* test = new BST(testVector);

	cout << test->isEmpty() << endl;

	test->insertNode(1);
	test->insertNode(2500);
	test->insertNode(5000);
	test->insertNode(7500);
	test->insertNode(9999);

	test->printTree();

	test->deleteNode(1);
	test->deleteNode(2500);
	test->deleteNode(5000);
	test->deleteNode(7500);
	test->deleteNode(9999);

	cout << endl << endl;

	test->printTree();
	
	cin.get();
}