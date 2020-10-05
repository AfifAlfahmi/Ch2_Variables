
fun main() {

    Game.play()
}


object Game {
    private var currentRoom: Room = TownSquare()
    var beIntheGame =true
    var newPoist :Coordinate? = null
    private var worldMap = listOf(listOf(currentRoom, Room("Tavern"),
        Room("Back Room")),listOf(Room("Long Corridor"), Room("Generic Room")))
    private fun map(): String {
        return try {

            val direction = Direction.valueOf("north".toUpperCase())
            val newPosition = direction.updateCoordinate(Coordinate(1, 2))
            newPoist = newPosition

            val newRoom = worldMap[newPosition.y][newPosition.x]


            "OK, you move $direction to the ${newRoom.name}.\n${newRoom.load()}"
        }
        catch (e: Exception) {
            "Invalid direction: "
        }

    }


    fun ringBell(){
        println("The bell is ringing")
    }
    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1) { "" }
        private fun commandNotFound() = "I'm not quite sure what you're trying to do!"


        fun processCommand(): Any = when (command.toLowerCase()) {

            "quit","exit"->  {beIntheGame=false}
            "map" -> {map()
                println(" $newPoist")}

            "ring" -> ringBell()
            else -> commandNotFound()
        }
    }
    init {
        println("Welcome, adventurer.")

    }
    fun play() {
        var i=0
        while (beIntheGame) {
            // Play NyetHack
            println(currentRoom.description())
            println(currentRoom.load())

            print("> Enter your command: ")
            println( GameInput(readLine()).processCommand() )
            i++
        }
        println( "the game terminated" )

    }




}




open class Room(val name: String) {

    open fun load() = "Nothing much to see here..."
    open fun description() = "Room: $name\n"
}

class TownSquare : Room("Foyer"){
    override  fun load() = "Nothing much to see here..."
    override fun toString(): String =
        if (this is TownSquare) {
            "instance of TownSquare"
        }
        else  if (this is Room){
            "instance of Room"
        }
        else {
            "instance of Any"

        }

}
class Player(    _name: String,    var healthPoints: Int = 100,    val isBlessed: Boolean,    private val isImmortal: Boolean)
{    var name = "Afif"
    private val hometown = "homeTown"

    var currentPosition = Coordinate(0, 0)


}

data class Coordinate(val x: Int, val y: Int) {
    val isInBounds = x >= 0 && y >= 0

    operator fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)
}

enum class Direction(private val coordinate: Coordinate) {
    NORTH(Coordinate(0, -1)),
    EAST(Coordinate(1, 0)),
    SOUTH(Coordinate(0, 1)),
    WEST(Coordinate(-1, 0));
    fun updateCoordinate(playerCoordinate: Coordinate) =
        Coordinate(playerCoordinate.x + coordinate.x,playerCoordinate.y + coordinate.y)

}

