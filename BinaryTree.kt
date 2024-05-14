//Assumptions -
//1. Assuming can have duplicate values. If so, send to left.
//2. Assuming no features needed such as rebalancing
//3. The overall goal is to just make a binary tree, not a specific BinaryTree object

class Node(val nodeVal: Int){
    var left : Node? = null
    var right : Node? = null
    
    fun addNode(newVal : Int){
        //Assuming duplicates possible - If value is less than or equal to, then add to the left.
        if(newVal <= nodeVal){
            if(left == null){
                left = Node(newVal)
                println("[$newVal] <- $nodeVal")
            } else {
                val leftNode = left // Need to do to ensure mutable property has not changed
                leftNode!!.addNode(newVal)
            }
        } else {
            if(right == null){
                right = Node(newVal)
                println("$nodeVal -> [$newVal]")
            } else {
                val rightNode = right // Need to do to ensure mutable property has not changed
                rightNode!!.addNode(newVal)
            }
        }
    }
}

//Sanity Check
fun main() {
	val root = Node(nodeVal = 5)
    for (i in 1..10){
        var newVal = (1..10).random()
        root.addNode(newVal)
    }
}