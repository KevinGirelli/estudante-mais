import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { ChatService, Message } from '../../services/chat/chat.service';
import { SidebarModule } from 'primeng/sidebar';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonModule, InputTextModule, SidebarModule],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss'],
})
export class ChatComponent implements OnInit {
  messages: Message[] = [];
  newMessage: string = '';
  currentUser: string = 'Aluno';
  currentConversation: string = '';
  conversations: string[] = [];
  sidebarExpanded: boolean = true;

  constructor(private chatService: ChatService) {}

  ngOnInit(): void {
    this.loadConversations();
  }

  loadConversations(): void {
    if (this.currentUser === 'Aluno') {
      this.conversations = ['Professor1_Turma1', 'Professor2_Turma1', 'Professor1_Turma2'];
    } else {
      this.conversations = ['Turma1_Professor1', 'Turma1_Professor2', 'Turma2_Professor1'];
    }
  }

  loadConversation(conversationId: string): void {
    this.currentConversation = conversationId;
    this.chatService.loadConversation(conversationId);
    this.chatService.getMessages(conversationId).subscribe((messages) => {
      this.messages = messages;
    });
  }

  sendMessage(): void {
    if (this.newMessage.trim() !== '') {
      const message: Message = {
        sender: this.currentUser,
        content: this.newMessage,
        timestamp: new Date(),
      };
      this.chatService.addMessage(this.currentConversation, message);
      this.newMessage = '';
    }
  }

  toggleUser(): void {
    this.currentUser = this.currentUser === 'Aluno' ? 'Professor' : 'Aluno';
    this.loadConversations();
  }

  back(){

  }
}
