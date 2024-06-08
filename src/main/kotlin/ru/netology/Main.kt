package ru.netology


fun main() {

    val note1 = Note(tytle = " Заметка 1", text = " О жирафах")
    val note2 = Note(tytle = " Заметка 2", text = " О слонах")
    val note3 = Note(tytle = " Заметка 3", text = " О гиенах")

    println(NoteService.add(note1))
    println(NoteService.add(note2))
    println(NoteService.add(note3))

    println()

    NoteService.notes.forEach { println(it) } // добавление работает

    println()

    val comment1 = Comment(message = " У жирафов длинная шея, но короткое туловище")
    val comment2 = Comment(message = " У жирафов длинные ноги")
    val comment3 = Comment(message = " Жирафы могут развивать скорость до 60 км/ч")

    println(NoteService.createComment(1, comment1))
    println(NoteService.createComment(1, comment2))
    println(NoteService.createComment(1, comment3))

    println()

    NoteService.comments.forEach { println(it) } // добавление работает

    println()

    println(NoteService.delete(3)) // удаление заметки

    println()

    NoteService.notes.forEach { println(it) }

    println()

    println(NoteService.deleteComment(3)) // удаление комментария

    println()

    NoteService.comments.forEach { println(it) }

    println()

    println(NoteService.edit(2, " Заметка 2(edit)", " О верблюдах")) // редактирование заметки

    println()

    NoteService.notes.forEach { println(it) }

    println()

    println(NoteService.editComment(2, " \"Edit message\"")) // редактирование комментария

    println()

    NoteService.comments.forEach { println(it) }

    println()

    println(NoteService.getNotes(124124, 1, 2, 3)) // возврат списка указанных заметок

    println()

    println(NoteService.getById(2)) // Возвращает заметку по её id

    println()

    println(NoteService.getComments(1)) // возвращает список указанных заметок

    println()

    println(NoteService.restoreComment(3)) // восстанавливает удалённый комментарий (если комментарий не был удалён,
    // то ничего не произойдёт)
    println()

    NoteService.comments.forEach { println(it) }

    println()


}

