package com.stackroute.keepnote.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;
import com.stackroute.keepnote.repository.NoteRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class NoteServiceImpl implements NoteService {

	Logger LOGGER = LoggerFactory.getLogger(NoteServiceImpl.class);

	/*
	 * Autowiring should be implemented for the NoteRepository and MongoOperation.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	private NoteRepository noteRepository;

	/*
	 * This method should be used to save a new note.
	 */
	public boolean createNote(Note note) {
		NoteUser noteUser = new NoteUser();
		noteUser.setUserId(note.getNoteCreatedBy());
		noteUser.setNotes(Arrays.asList(note));
		NoteUser noteUser2 = noteRepository.insert(noteUser);
		if (noteUser2 == null) {
			return false;
		}
		return noteUser2.getNotes().stream().findFirst().isPresent();
	}

	/* This method should be used to delete an existing note. */

	public boolean deleteNote(String userId, int noteId) {
		NoteUser noteUser = new NoteUser();
		noteUser.setUserId(userId);
		try {
			noteUser.setNotes(Arrays.asList(getNoteByNoteId(userId, noteId)));
		} catch (NoteNotFoundExeption e) {
			LOGGER.debug("Note is not found", e);
			return false;
		}
		noteRepository.delete(noteUser);
		return true;
	}

	/* This method should be used to delete all notes with specific userId. */

	public boolean deleteAllNotes(String userId) {
		noteRepository.deleteById(userId);
		return true;
	}

	/*
	 * This method should be used to update a existing note.
	 */
	public Note updateNote(Note note, int id, String userId) throws NoteNotFoundExeption {
		try {
			Optional<NoteUser> noteUserOptional = noteRepository.findById(userId);
			NoteUser noteUser = noteUserOptional.get();
			if (noteUser.getNotes() != null) {
				List<Note> notes = noteUser.getNotes();
				List<Note> updateNotesList = new ArrayList<>();
				for (Note noteIter : notes) {
					if (noteIter.getNoteId() == id) {
						updateNotesList.add(note);
					} else {
						updateNotesList.add(noteIter);
					}
				}
				noteUser.setNotes(updateNotesList);
				noteRepository.save(noteUser);
			}
		} catch (NoSuchElementException exception) {
			throw new NoteNotFoundExeption("NoteNotFoundExeption");
		}
		return note;
	}

	/*
	 * This method should be used to get a note by noteId created by specific user
	 */
	public Note getNoteByNoteId(String userId, int noteId) throws NoteNotFoundExeption {
		NoteUser noteUser = null;
		try {
			noteUser = noteRepository.findById(userId).orElseThrow(() -> new NoteNotFoundExeption(""));
		} catch (NoSuchElementException ex) {
			throw new NoteNotFoundExeption("");
		}
		return noteUser.getNotes().stream().findAny().orElseThrow(() -> new NoteNotFoundExeption(""));
	}

	/*
	 * This method should be used to get all notes with specific userId.
	 */
	public List<Note> getAllNoteByUserId(String userId) {
		try {
			return noteRepository.findById(userId).get().getNotes();
		} catch (NoSuchElementException e) {
			return new ArrayList<>();
		}

	}

}
