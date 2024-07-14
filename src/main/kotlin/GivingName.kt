interface GivingName {
    fun giveName():String
}

fun nameOfArchiveAndNumberOfExit (list: List<GivingName>) {
    var k = 1
    for (i in list) println("${k++} -> Нажмите для входа в папку ${i.giveName()}")
    println("${list.size + 1} -> Нажмите для выхода")
}