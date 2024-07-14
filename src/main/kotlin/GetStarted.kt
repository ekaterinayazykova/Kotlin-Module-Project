import java.util.*

object GetStarted {

    private val scanner = Scanner(System.`in`)
    private val archiveList = mutableListOf<Archives>()

    fun startedFunction(screen: Screen) {
        when (screen) {
            is Screen.MainScreen -> {
                println("Вы находитесь в папке \"Архивы\"")
                println("0 -> Создать архив")
                nameOfArchiveAndNumberOfExit(archiveList)
                val number = checkInput()
                when (number) {
                    0 -> startedFunction(Screen.CreateArchive)
                    archiveList.size + 1 -> {
                        println("Увидимся!")
                        System.exit(0)
                    }
                    in 1..archiveList.size -> startedFunction(Screen.OpenArchive(archiveList[number - 1]))
                    else -> {
                        println("Некорректный выбор")
                        startedFunction(Screen.MainScreen)
                    }
                }
            }
            is Screen.CreateArchive -> {
                println("Введите название архива:")
                val nameOfArchive = scanner.nextLine()
                if (nameOfArchive.isNotEmpty()) {
                    archiveList.add(Archives(nameOfArchive))
                    startedFunction(Screen.MainScreen)
                } else {
                    println("Введите название архива")
                }
            }
            is Screen.OpenArchive -> {
                val archives = screen.archives
                println("Архив: ${archives.name}")
                println("0 -> Создать заметку")
                nameOfArchiveAndNumberOfExit(archives.note)
                val number = checkInput()
                when (number) {
                    0 -> {
                        println("Введите название заметки:")
                        val name = scanner.nextLine()
                        if (name.isNotEmpty()) {
                            startedFunction(Screen.CreateNote(archives, name))
                        } else {
                            println("Введите название заметки")
                        }
                    }
                    archives.note.size + 1 -> startedFunction(Screen.MainScreen)
                    in 1..archives.note.size -> {
                        val note = archives.note[number - 1]
                        println("Заметка: ${note.title}")
                        println("Текст: ${note.text}")
                        println("Нажмите любую клавишу для выхода")
                        scanner.nextLine()
                        startedFunction(Screen.OpenArchive(archives))
                    }
                    else -> {
                        println("Некорректный выбор")
                        startedFunction(Screen.OpenArchive(archives))
                    }
                }
            }
            is Screen.CreateNote -> {
                val archives = screen.archives
                val title = screen.name
                if (title.isNotEmpty()) {
                    println("Введите текст заметки:")
                    val text = scanner.nextLine()
                    archives.note.add(Note(title, text, archives))
                    startedFunction(Screen.OpenArchive(archives))
                } else {
                    println("Введите название заметки")
                    startedFunction(Screen.CreateNote(archives, ""))
                }
            }
            is Screen.ShowNote -> {
                val note = screen.note
                println("Заметка: ${note.title}")
                println("Текст: ${note.text}")
                println("Нажмите любую клавишу для выхода")
                scanner.nextLine()
                startedFunction(Screen.OpenArchive(note.archive))
            }
        }
    }

    private fun checkInput(): Int {
        while (true) {
            try {
                val input = scanner.nextLine()
                return input.toInt()
            } catch (e: NumberFormatException) {
                println("Введите числовое значение")
            }
        }
    }
}