import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface Message {
  sender: string;
  content: string;
  timestamp: Date;
}

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  private conversations: { [key: string]: Message[] } = {};

  private messagesSubject = new BehaviorSubject<Message[]>([]);

  getMessages(conversationId: string) {
    return this.messagesSubject.asObservable();
  }

  loadConversation(conversationId: string) {
    if (!this.conversations[conversationId]) {
      this.conversations[conversationId] = [];
    }
    this.messagesSubject.next(this.conversations[conversationId]);
  }

  addMessage(conversationId: string, message: Message) {
    if (!this.conversations[conversationId]) {
      this.conversations[conversationId] = [];
    }
    this.conversations[conversationId].push(message);
    this.loadConversation(conversationId);
  }
}
