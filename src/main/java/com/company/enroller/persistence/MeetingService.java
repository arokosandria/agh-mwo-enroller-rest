package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;

import com.company.enroller.model.Participant;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}
 public Meeting findByLogin(long id) {
		 
		 return (Meeting) connector.getSession().get(Meeting.class, id);
		 
	 }
 public void addMeeting(Meeting meeting) {
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().save(meeting);
		transaction.commit();
}
 public void addParticipant(Long id, Participant participant) {
		Meeting meeting = findByLogin(id);
		meeting.addParticipant(participant);
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().update(meeting);
		transaction.commit();
	}
 public Collection<Participant> getParticipants(long meetingId) {
		Meeting meeting = findByLogin(meetingId);
		return meeting.getParticipants();
}
		

 public void deleteMeeting(Meeting meeting) {
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().delete(meeting);
		transaction.commit();
	}

	public void updateMeeting(Meeting meetingUpdate) {
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().merge(meetingUpdate);
		transaction.commit();
}
	public void removeParticipant(Long id, Participant participant) {
		Meeting meeting = findByLogin(id);
		meeting.removeParticipant(participant);
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().update(meeting);
		transaction.commit();
}
}
