package ru.netology

// Тут реализованны data-классы, класс-сервис
// и классы-исключения


//----------Note и его классы------------


data class Note(
    val id: Int? = 0,
    val tytle: String = "text",
    val text: String = "text",
    val privacy: Int = 0,
    val commentPrivacy: Int = 0,
    val canComment: Int = 1,
    val likes: Likes? = Likes(),
    val comments: Comments? = Comments(),
)

data class Likes(
    var count: Int = 0,
    val userLikes: Boolean = true,
    val scanLike: Boolean = true,
    val canPublish: Boolean = true,
)


data class Comments(
    var count: Int = 0,
    val canPost: Boolean = true,
    val groupsCanPost: Boolean = true,
    val canClose: Boolean = true,
    val canOpen: Boolean = true,
)


//---------Класс-сервис и Comment для него------------


data class Comment(
    // Comment для NoteService и 'списка' comments
    val id: Int = 0, // собственный id комментария
    var message: String = "text",
    val fromId: Int = 0,
    val canClose: Boolean = true,
    var commentNoteID: Int = 0, // id комментария заметки к которой он относится (равен id этой заметки)
    var deleted: Boolean = false, // параметр, определяющий удалён ли комментарий
)


object NoteService {

    var notes = mutableListOf<Note>()
    var comments = mutableListOf<Comment>()

    private var nextNoteId: Int = 0
    private var nextCommentId: Int = 0


    fun add(note: Note): Int? {
        val newNote = note.copy(id = ++nextNoteId, likes = note.likes?.copy(), comments = note.comments?.copy())
        notes += newNote
        return notes.last().id // После успешного выполнения возвращает идентификатор созданной заметки
    }


    fun createComment(noteId: Int, comment: Comment): Int {
        for (note in notes)
            if (noteId == note.id) {
                comment.commentNoteID = note.id // передача id заметки комментарию
                comments += comment.copy(id = ++nextCommentId)
                return comments.last().id // После успешного выполнения возвращает идентификатор созданного комментария
            }
        throw NoteNotFoundException("Заметки с id номер: $noteId не существует")
    }


    fun delete(noteId: Int): Int {
        for ((index, note) in notes.withIndex())
            if (noteId == note.id) {
                notes.removeAt(index)
                return 1 // После успешного выполнения возвращает 1
            }
        throw NoteNotFoundException("Заметки с id номер: $noteId не существует")
    }


    fun deleteComment(commentId: Int): Int {
        for (comment in comments)
            if (commentId == comment.id && !comment.deleted) {
                comment.deleted = true
                return 1
            }
        throw CommentNotFoundException("Комментария с id $commentId нет или он уже был удалён")
    }


    fun edit(noteId: Int, tytle: String, text: String): Int {
        for ((index, editableNote) in notes.withIndex())
            if (noteId == editableNote.id) {
                notes[index] = notes[index].copy(
                    tytle = tytle,
                    text = text,
                    likes = notes[index].likes?.copy(),
                    comments = notes[index].comments?.copy()
                )
                return 1
            }
        throw NoteNotFoundException("Заметки с id номер: $noteId не существует")
    }


    fun editComment(commentId: Int, message: String): Int {
        for (comment in comments)
            if (commentId == comment.id && !comment.deleted) {
                comment.message = message
                return 1
            }
        throw CommentNotFoundException("Комментария с id номер: $commentId нет или он был удалён")
    }


    fun getNotes(userId: Int, vararg numbersId: Int): List<Note> {  // Возвращает список заметок (те, которые есть)
        // Тут условный передаваемый id пользователя, но,
        // мы будем просто возвращать все запрашиваемые заметки, которые есть, т.к. создание
        // пользователей или друзей пользователей не предусматривается заданием
        val notesUser = mutableListOf<Note>()
        for (id in numbersId)
            for (note in notes)
                if (id == note.id) {
                    notesUser += note
                }

        if (notesUser.isEmpty()) throw NoteNotFoundException("Заметок с указанными номерами нет") else return notesUser

    }

    fun getById(noteId: Int): Note {
        for (note in notes)
            if (note.id == noteId) {
                return note
            }
        throw NoteNotFoundException("Заметки с id номер: $noteId не существует")

    }


    fun getComments(noteId: Int): List<Comment> { // Возвращает список комментариев к заметке
        val noteComments = mutableListOf<Comment>()
        for (comment in comments) {
            if (comment.commentNoteID == noteId && !comment.deleted)
                noteComments += comment
        }
        if (noteComments.isEmpty()) throw CommentNotFoundException("Комментарии к заметке необнаружены") else return noteComments
    }


    fun restoreComment(commentId: Int): Int {   // Восстанавливает удалённый комментарий
        for (comment in comments)
            if (commentId == comment.id) {
                comment.deleted = false
                return 1
            }
        throw CommentNotFoundException("Комментария с id номер: $commentId не существует")
    }


    fun clear() {    // Очистка сервиса
        notes.clear()
        comments.clear()
        nextNoteId = 0
        nextCommentId = 0
    }
}


//----------Исключения-----------


class NoteNotFoundException(message: String) : RuntimeException(message)

class CommentNotFoundException(message: String) : RuntimeException(message)




















