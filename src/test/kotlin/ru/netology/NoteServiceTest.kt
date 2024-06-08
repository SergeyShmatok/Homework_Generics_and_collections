package ru.netology

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.netology.NoteService.comments
import ru.netology.NoteService.notes

class NoteServiceTest {

    @Before
    fun clearBeforeTest() {
        NoteService.clear()
    }


//---------------------------------------


    @Test
    fun add_testing() {
        val note = Note()
        NoteService.add(note)
        assertTrue(notes.last().id != 0 && notes.isNotEmpty())
    }

    @Test
    fun add_testing_with_null() {
        val note = Note(null, likes = null, comments = null)
        NoteService.add(note)
        assertTrue(notes.last().id != 0 && notes.isNotEmpty())
    }


//---------------------------------------


    @Test
    fun createComment() {
        NoteService.add(Note())
        val comment = Comment(message = " У жирафов длинная шея, но короткое туловище")
        NoteService.createComment(1, comment)
        assertTrue(comments.last().id != 0 && comments.isNotEmpty())

    }

    @Test(expected = NoteNotFoundException::class)
    fun createComment_adding_to_missing_note() {
        val comment = Comment(message = " У жирафов длинная шея, но короткое туловище")
        NoteService.createComment(1, comment)
    }


//---------------------------------------


    @Test
    fun delete() {
        val note = Note()
        NoteService.add(note)
        NoteService.delete(1)
        assertTrue(notes.isEmpty())

    }


    @Test(expected = NoteNotFoundException::class)
    fun delete_deleting_missing_note() {
        NoteService.delete(1)
    }


    @Test
    fun deleteComment() {
        NoteService.add(Note())
        NoteService.createComment(1, Comment(message = " У жирафов длинная шея, но короткое туловище"))
        val result = NoteService.deleteComment(1)
        assertEquals(1, result)

    }


    @Test(expected = CommentNotFoundException::class)
    fun deleteComment_deleting_deleted_comment() {
        NoteService.add(Note())
        NoteService.createComment(1, Comment(message = " У жирафов длинная шея, но короткое туловище"))
        NoteService.deleteComment(1)
        NoteService.deleteComment(1)
    }


    @Test(expected = CommentNotFoundException::class)
    fun deleteComment_deleting_missing_note() {
        NoteService.deleteComment(1)
    }


//---------------------------------------


    @Test
    fun edit() {
        val note = Note(tytle = " Заметка 1", text = " О жирафах")
        NoteService.add(note)
        val result = NoteService.edit(1, " Заметка 1(edit)", " Edit text")
        assertEquals(1, result)
    }


    @Test
    fun edit_testing_with_null() {
        val note = Note(tytle = " Заметка 1", text = " О жирафах", likes = null, comments = null)
        NoteService.add(note)
        val result = NoteService.edit(1, " Заметка 1(edit)", " Edit text")
        assertEquals(1, result)
    }


    @Test(expected = NoteNotFoundException::class)
    fun edit_editing_missing_note() {
        NoteService.edit(1, " Заметка 1(edit)", "")
    }


    @Test
    fun editComment() {
        NoteService.add(Note())
        val comment = Comment(message = " У жирафов длинная шея, но короткое туловище")
        NoteService.createComment(1, comment)
        val result = NoteService.editComment(1, "")
        assertEquals(1, result)
    }


    @Test(expected = CommentNotFoundException::class)
    fun editComment_editing_deleted_comment() {
        NoteService.add(Note())
        val comment = Comment(message = " У жирафов длинная шея, но короткое туловище")
        NoteService.createComment(1, comment)
        NoteService.deleteComment(1)
        NoteService.editComment(1, "")
    }


    @Test(expected = CommentNotFoundException::class)
    fun editComment_editing_missing_comment() {
        NoteService.editComment(1, "")
    }


//---------------------------------------


    @Test
    fun getNotes() {
        val note1 = Note(tytle = " Заметка 1", text = " О жирафах")
        val note2 = Note(tytle = " Заметка 2", text = " О слонах")
        val note3 = Note(tytle = " Заметка 3", text = " О гиенах")

        println(NoteService.add(note1))
        println(NoteService.add(note2))
        println(NoteService.add(note3))

        val result = NoteService.getNotes(124124, 1, 2, 3)
        assertTrue(result.size == 3)


    }


    @Test(expected = NoteNotFoundException::class)
    fun getNotes_getting_missing_notes() {
        NoteService.getNotes(124124, 1, 2, 3)
    }


    @Test
    fun getById() {
        val note1 = Note(tytle = " Заметка 1", text = " О жирафах")
        NoteService.add(note1)
        val result = NoteService.getById(1)
        assertEquals(Note(id = 1, tytle = " Заметка 1", text = " О жирафах"), result)

    }


    @Test(expected = NoteNotFoundException::class)
    fun getById_getting_missing_note() {
        NoteService.getById(1)
    }


    @Test
    fun getComments() {
        NoteService.add(Note())
        val comment = Comment(message = " У жирафов длинная шея, но короткое туловище")
        NoteService.createComment(1, comment)
        val result = NoteService.getComments(1)
        assertTrue(result.size == 1)
    }


    @Test(expected = CommentNotFoundException::class)
    fun getComments_getting_deleted_comment() {
        NoteService.add(Note())
        val comment = Comment(message = " У жирафов длинная шея, но короткое туловище")
        NoteService.createComment(1, comment)
        NoteService.deleteComment(1)
        NoteService.getComments(1)
    }


    @Test(expected = CommentNotFoundException::class)
    fun getComments_getting_missing_comment() {
        NoteService.getComments(1)
    }


//--------------------------------------


    @Test
    fun restoreComment() {
        NoteService.add(Note())
        val comment = Comment(message = " У жирафов длинная шея, но короткое туловище")
        NoteService.createComment(1, comment)
        NoteService.deleteComment(1)
        NoteService.restoreComment(1)
        assertTrue(!comments[0].deleted)
    }


    @Test(expected = CommentNotFoundException::class)
    fun restoreComment_restore_missing_comment() {
        NoteService.restoreComment(1)
    }
//    @Test
//    fun clear() {
//    }


}