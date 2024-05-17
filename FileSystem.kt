import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.Instant
import java.time.ZoneId

fun main() {
    val fileSystem = FileSystem()
    
    //// Test 1: Should print "Already at the root directory"
    println(fileSystem.currentPath + "$ cd ..")
	fileSystem.navigateToParentDir()
    
    //// Test 2: Should navigate to home directory and update current path appropriately
    println(fileSystem.currentPath + "$ mkdir home")
    println(fileSystem.currentPath + "$ cd home")
    fileSystem.createDir("home")
    fileSystem.navigateToNextDir("home")
    
    //// Test 3: Should navigate to make 2 user folders and list them
    println(fileSystem.currentPath + "$ mkdir user1 user2")
    fileSystem.createDir("user1")
    fileSystem.createDir("user2")
    println(fileSystem.currentPath + "$ ls")
    fileSystem.listDir()
    
    //// Test 4: Should navigate into user1 folder, make a document then list dir
    println(fileSystem.currentPath + "$ cd user1")
    fileSystem.navigateToNextDir("user1")
    println(fileSystem.currentPath + "$ touch very_important.txt")
    fileSystem.createTextFile("very_important.txt")
    println(fileSystem.currentPath + "$ ls")
    fileSystem.listDir()
    
    //// Test 5: Should navigate into back up a folder, then into user2 folder, make a document then list dir
    println(fileSystem.currentPath + "$ cd ..")
    fileSystem.navigateToParentDir()
    println(fileSystem.currentPath + "$ cd user2")
    fileSystem.navigateToNextDir("user2")
    println(fileSystem.currentPath + "$ touch not_at_all_important.txt")
    fileSystem.createTextFile("not_at_all_important.txt")
    println(fileSystem.currentPath + "$ ls")
    fileSystem.listDir()
    
    //// Test 6: Delete file then list dir
    println(fileSystem.currentPath + "$ rm not_at_all_important.txt")
    fileSystem.deleteFile("not_at_all_important.txt")
    println(fileSystem.currentPath + "$ ls")
    fileSystem.listDir()
}

//////////////////////////////////////////////////////////////////
//TextFile Class
//////////////////////////////////////////////////////////////////
class TextFile(val name:String){
    private val creationTime =  System.currentTimeMillis()
    //In real system associate this with creator name
    private val creator = "You"
    private val dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    //In real system could do more permissions such as admin required to change readOnly status
    private val readOnly = false
    val createdDateTime : String
    val modifiedDateTime : String
    var contents: String = ""
    
    init{
        //To make human readable need to convert creationTime to instant then local time then it can be formatted 
        val instant = Instant.ofEpochMilli(creationTime)
        val creationDateTimeFormat = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        createdDateTime = creationDateTimeFormat.format(dateTimeFormat)
        modifiedDateTime = createdDateTime
    }
    
    fun read():String{
        return contents
    }
    
    //Real one would have options to append and insert. 
    fun write(text : String){
        contents = text
    }
}

//////////////////////////////////////////////////////////////////
//Directory Class
//////////////////////////////////////////////////////////////////
class Directory(val name:String){
    private val creationTime =  System.currentTimeMillis()
    //In real system associate this with creator name
    private val creator = "You"
    private val dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val createdDateTime : String
    //Using MutableMap with filename/dirname as the key. 
    //This enforces no repeat filenames/dirnames and is efficient for lookup and add
    var parent : Directory? = null
        get(){
			return field
        }
        set(value){
            field = value
        }
    private val children : MutableMap<String, Any> = mutableMapOf()

    init{
        //To make human readable need to convert creationTime to instant then local time then it can be formatted 
        val instant = Instant.ofEpochMilli(creationTime)
        val creationDateTimeFormat = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        createdDateTime = creationDateTimeFormat.format(dateTimeFormat)
    }
   	
    fun getAllChildrenNames():List<String>{
        return children.keys.toList()
    }
    
    fun getChild(name: String):Any?{
        return children[name]
    }
    
    //Could expand to require permissions or return info on whether or not child successfully added/deleted
    fun addChild(child: Any){
        //Need this check because you need to check input param type
        //Could alternately do this where you overload the same function and only accept that specific item in each version of the function
        if(child is TextFile){ 
        	children[child.name] = child
        } else if (child is Directory){
            child.parent = this
        	children[child.name] = child
        }
    }
    fun removeChild(name:String){
        children.remove(name)
    }
}
//////////////////////////////////////////////////////////////////
//FileSystem Class
//Assumption: Client wants to use cat emojis instead of slashes because slashes are kind of played out
//////////////////////////////////////////////////////////////////
class FileSystem(){
    private val root = Directory("🐱")
    private var currentDirectory: Directory = root
    public var currentPath = root.name
    
    //Further expansion would let user navigate to more than 1 dir at a time like when you do "cd C:\Users\catfan01\Pictures"
    public fun navigateToNextDir(dirName: String): Boolean{
        val targetDir = currentDirectory.getChild(dirName)
        if(targetDir != null && targetDir is Directory ){
            currentPath += dirName + "🐱"
            currentDirectory = targetDir
            return true
        } else {
            println("Dir not found")
            return false
        }
    }
    
    public fun navigateToParentDir(){
        //Need to do this because parent directory can change after null check
        val parentDir = currentDirectory.parent 
        if(parentDir != null){
            //Add 2 because there's always an extra cat emoji we need to remove and need to skip first initial cat emoji
            currentPath = currentPath.substring(0, currentPath.length - (currentDirectory.name.length + 2))
        	currentDirectory = parentDir
        } else {
            println("Already in root")
        }
    }
    
    //Could expand to require permissions or return info on whether or not dir successfully added/deleted
    public fun createDir(name: String){
        if(currentDirectory.getChild(name) != null){
            println("Directory already exists.")
        } else {
            currentDirectory.addChild(Directory(name))
        }
    }
    
    public fun deleteDir(name: String){
        currentDirectory.removeChild(name)
    }
    
    public fun createTextFile(name: String){
        currentDirectory.addChild(TextFile(name))
    }
    
    public fun deleteFile(name: String){
        currentDirectory.removeChild(name)
    }
    
    public fun listDir(){
        currentDirectory.getAllChildrenNames().forEach { child ->
            print(child + "\t")
        }
        println()
    }
    
    
    init{
        unnecessaryBootSequence()
    }
    public fun unnecessaryBootSequence(){
            println("GatoOS 3.14 install disk activated")
            println("loading...")
            println("*********************************")
            println("*\tmem check passed!\t* ")
            println("*********************************")
            println("sound card...\t\t OK")
            println("graphics card...\t OK")
            println("network card...\t\t OK")
            println("Installing GatoOS 3.14!")
            println("---------------------------------")
    }
}


