package io.onicodes.boredmembers.service;

import io.onicodes.boredmembers.entity.Message;

public interface MessageService {
	
	void deleteMessage(int id);
	void deleteMessage(Message message);
}
