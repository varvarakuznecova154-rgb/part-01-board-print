fun createEmptyField(size: Int = 10): Array<CharArray> {
    return Array(size) { CharArray(size) { '.' } }
}

private fun cellToChar(ch: Char, showShips: Boolean): Char {
    return if (ch == '#' && !showShips) '.' else ch
}

fun printField(
    field: Array<CharArray>,
    title: String,
    showShips: Boolean = true,
    debug: Boolean = false
) {
    if (field.isEmpty()) {
        println("=== $title ===")
        println("Пустое поле")
        return
    }

    val rows = field.size
    val cols = field[0].size

    if (debug) {
        println("=== $title (debug) ===")
        print("    ")
        for (c in 0 until cols) print(" $c  ")
        println()

        val border = "   " + "+---".repeat(cols) + "+"
        println(border)
        for (r in field.indices) {
            print("${r.toString().padStart(2, ' ')} |")
            for (c in field[r].indices) {
                val ch = cellToChar(field[r][c], showShips)
                print(" $ch |")
            }
            println()
            println(border)
        }
        return
    }

    println("=== $title ===")
    print("   ")
    for (c in 0 until cols) print("$c ")
    println()

    for (r in field.indices) {
        print("$r  ")
        for (c in field[r].indices) {
            val ch = cellToChar(field[r][c], showShips)
            print("$ch ")
        }
        println()
    }
}

fun printBothFields(
    player: Array<CharArray>,
    enemy: Array<CharArray>,
    showEnemyShips: Boolean = false
) {
    if (player.size != enemy.size) {
        println("Ошибка: размеры полей не совпадают (${player.size} != ${enemy.size})")
        return
    }

    val size = player.size
    for (i in 0 until size) {
        if (player[i].size != size || enemy[i].size != size) {
            println("Ошибка: поля должны быть квадратными и одинакового размера")
            return
        }
    }

    val leftTitle = "=== Ваше поле ==="
    val rightTitle = "=== Поле противника ==="
    val gap = "     "

    println("$leftTitle$gap$rightTitle")

    print("   ")
    for (c in 0 until size) print("$c ")
    print(gap)
    print("   ")
    for (c in 0 until size) print("$c ")
    println()

    for (r in 0 until size) {
        print("$r  ")
        for (c in 0 until size) {
            val ch = cellToChar(player[r][c], showShips = true)
            print("$ch ")
        }
        print(gap)
        print("$r  ")
        for (c in 0 until size) {
            val ch = cellToChar(enemy[r][c], showShips = showEnemyShips)
            print("$ch ")
        }
        println()
    }
}

fun printFields(
    fields: Array<Array<CharArray>>,
    titles: Array<String>,
    showShips: BooleanArray
) {
    if (fields.size != titles.size || fields.size != showShips.size) {
        println("Ошибка: размеры массивов fields, titles и showShips должны совпадать")
        return
    }
    if (fields.isEmpty()) return

    val size = fields[0].size
    for (f in fields) {
        if (f.size != size) {
            println("Ошибка: все поля должны быть одинакового размера")
            return
        }
        for (row in f) {
            if (row.size != size) {
                println("Ошибка: все поля должны быть квадратными")
                return
            }
        }
    }

    val gap = "     "

    for (i in fields.indices) {
        print("=== ${titles[i]} ===")
        if (i < fields.lastIndex) print(gap)
    }
    println()

    for (i in fields.indices) {
        print("   ")
        for (c in 0 until size) print("$c ")
        if (i < fields.lastIndex) print(gap)
    }
    println()

    for (r in 0 until size) {
        for (i in fields.indices) {
            print("$r  ")
            for (c in 0 until size) {
                val ch = cellToChar(fields[i][r][c], showShips[i])
                print("$ch ")
            }
            if (i < fields.lastIndex) print(gap)
        }
        println()
    }
}

fun main() {
    val playerField = createEmptyField()
    playerField[3][2] = '#'
    playerField[3][3] = '#'
    playerField[3][4] = '#'
    playerField[3][5] = '#'

    val enemyField = createEmptyField()
    enemyField[5][7] = '#'
    enemyField[6][7] = '#'
    enemyField[7][7] = '#'
    enemyField[5][5] = 'X'
    enemyField[5][6] = 'O'

    printField(playerField, "Ваше поле")
    println()
    printField(enemyField, "Поле противника", showShips = false)
    println()

    val smallField = createEmptyField(8)
    printField(smallField, "Маленькое поле 8x8")
    println()

    val bigField = createEmptyField(12)
    printField(bigField, "Большое поле 12x12")
    println()

    val defaultField = createEmptyField()
    printField(defaultField, "Поле по умолчанию 10x10")
    println()

    val p2 = createEmptyField(10)
    p2[3][2] = '#'
    p2[3][3] = '#'

    val e2 = createEmptyField(10)
    e2[5][7] = '#'
    e2[5][5] = 'X'

    printBothFields(p2, e2)
    println()

    printBothFields(createEmptyField(8), createEmptyField(10))
    println()

    printField(playerField, "Ваше поле", debug = true)
    println()

    val thirdField = createEmptyField(10)
    thirdField[1][1] = '#'
    val fields = arrayOf(playerField, enemyField, thirdField)
    val titles = arrayOf("Ваше поле", "Противник", "Третье")
    val show = booleanArrayOf(true, false, false)
    printFields(fields, titles, show)
}