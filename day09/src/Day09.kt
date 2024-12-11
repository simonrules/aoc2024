import java.io.File

data class Block(val id: Int, val size: Int, var free: Int)

class Day09(file: String) {
    private val blocks: List<Block>

    init {
        val map = File(file).readText().trim()
        val b = mutableListOf<Block>()

        val lastBlockId = map.length / 2
        for (id in 0..lastBlockId) {
            val free = if (id == lastBlockId) {
                0
            } else {
                map[id * 2 + 1].digitToInt()
            }
            b.add(Block(id, map[id * 2].digitToInt(), free))
        }

        blocks = b.toList()
    }

    private fun checksum(list: List<Block>): Long {
        var sum = 0L
        var i = 0
        for (b in list) {
            repeat (b.size) {
                sum += i * b.id
                i++
            }
            i += b.free
        }
        return sum
    }

    private fun printBlocks(list: List<Block>) {
        list.forEach { block ->
            repeat(block.size) {
                print(block.id)
            }
            repeat(block.free) {
                print(".")
            }
        }
        println()
    }

    fun part1(): Long {
        val blocksRemaining = blocks.toMutableList()
        val defragged = mutableListOf<Block>()

        do {
            // Move blocks over until we reach one with free space
            while (blocksRemaining.isNotEmpty() && blocksRemaining.first().free == 0) {
                defragged.add(blocksRemaining.removeFirst())
            }

            if (blocksRemaining.isEmpty()) {
                break // all done
            }

            // Fit as many blocks into the space and then move on
            var freeRemaining = blocksRemaining.first().free
            while (freeRemaining > 0) {
                val firstBlock = blocksRemaining.removeFirst()
                if (blocksRemaining.isEmpty()) {
                    // Last block has some free space, so put at the end and mark free as zero
                    defragged.add(Block(firstBlock.id, firstBlock.size, 0))
                    freeRemaining = 0
                } else {
                    val lastBlock = blocksRemaining.removeLast()
                    defragged.add(Block(firstBlock.id, firstBlock.size, 0))
                    if (lastBlock.size <= freeRemaining) {
                        // Last block fits entirely into free space
                        freeRemaining -= lastBlock.size
                        blocksRemaining.addFirst(Block(lastBlock.id, lastBlock.size, freeRemaining))
                    } else {
                        // Last block partially fits into free space so needs to be split
                        defragged.add(Block(lastBlock.id, freeRemaining, 0))
                        blocksRemaining.add(Block(lastBlock.id, lastBlock.size - freeRemaining, 0))
                        freeRemaining = 0
                    }
                }
            }
        } while (blocksRemaining.isNotEmpty())

        return checksum(defragged)
    }

    fun part2(): Long {
        // Next time I will just use a linked list :(
        var defragged = blocks.toMutableList()
        val numBlocks = blocks.size

        for (id in numBlocks - 1 downTo 0) {
            val blockToMove = defragged.indexOfFirst { it.id == id }
            val blockBeforeBlockToMove = blockToMove - 1
            val blockWithSpace = defragged.indexOfFirst { it.free >= defragged[blockToMove].size }
            if (blockWithSpace != -1 && blockWithSpace < blockToMove) {
                val remainingFree = defragged[blockWithSpace].free - defragged[blockToMove].size
                defragged[blockBeforeBlockToMove].free += defragged[blockToMove].size + defragged[blockToMove].free
                // Move the block here by creating a new list:
                // startBlocks | blockWithSpace | blockToMove | endBlocks
                val a = defragged.subList(0, blockWithSpace).toList()
                val b = listOf(Block(defragged[blockWithSpace].id, defragged[blockWithSpace].size, 0)).toList()
                val c = listOf(Block(defragged[blockToMove].id, defragged[blockToMove].size, remainingFree)).toList()
                defragged.removeAt(blockToMove)
                val d = defragged.subList(blockWithSpace + 1, numBlocks - 1).toList()
                defragged = (a + b + c + d).toMutableList()
            }
        }

        return checksum(defragged)
    }
}

fun main() {
    val aoc = Day09("day09/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
