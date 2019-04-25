package com.company.enroller.controllers;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {
	@Autowired
	MeetingService meetingService;
	@Autowired
	ParticipantService participantService;
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getMeetings() {
	Collection<Meeting> meetings = meetingService.getAll();
	return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {
		Meeting meeting = meetingService.findByLogin(id);
		if (meeting == null) {
		return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting) {
		if (meetingService.findByLogin(meeting.getId()) != null) {
		return new ResponseEntity("Unable to create. A meeting with id " + meeting.getId() + " already exist.",
		HttpStatus.CONFLICT);
		}
		meetingService.addMeeting(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
}
	@RequestMapping(value = "/{id}/participants/{participantLogin}", method = RequestMethod.POST)
	public ResponseEntity<?> addParticipant(@PathVariable("id") Long id, @PathVariable("participantLogin") String login) {
		Meeting meeting = meetingService.findByLogin(id);
		Participant participant = participantService.findByLogin(login);
		if (meeting == null || participant == null) {
			return new ResponseEntity("Unable to update. A meeting with id " + id + " does not exist or participants" + login, HttpStatus.NOT_FOUND);
		}
		meetingService.addParticipant(id, participant);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
		
}
	
	@RequestMapping(value = "/{id}/participants", method = RequestMethod.GET)
	public ResponseEntity<?> getMeetingparticipant(@PathVariable("id") long id) {
		Meeting meeting = meetingService.findByLogin(id);
		
		if (meeting == null ) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		Collection<Participant> participants = meetingService.getParticipants(id);
		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteMeeting(@PathVariable("id") long id) {
		Meeting meeting = meetingService.findByLogin(id);
		if (meeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		meetingService.deleteMeeting(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateMeeting(@PathVariable("id") long id,@RequestBody Meeting meetingUpdate) {
		Meeting meeting = meetingService.findByLogin(id);
		if (meeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		meetingUpdate.setId(id);
		meetingService.updateMeeting(meetingUpdate);
		return new ResponseEntity<Meeting>(meetingUpdate, HttpStatus.OK);
}
	@RequestMapping(value = "/{id}/participants/{participantLogin}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeParticipant(@PathVariable("id") Long id, @PathVariable("participantLogin") String login) {
	Meeting meeting = meetingService.findByLogin(id);
	Participant participant = participantService.findByLogin(login);
	if (meeting == null || participant == null) {
		return new ResponseEntity("Unable to delete", HttpStatus.NOT_FOUND);
	}
	meetingService.removeParticipant(id, participant);
	return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
}

	}




