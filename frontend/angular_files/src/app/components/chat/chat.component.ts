import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { ChatService, Message } from '../../services/chat/chat.service';
import { SidebarModule } from 'primeng/sidebar';
import { ActivatedRoute, Router } from '@angular/router';
import SockJS from 'sockjs-client';
import { over } from 'stompjs';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonModule, InputTextModule, SidebarModule],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss'],
  providers: [ChatService]
})
export class ChatComponent implements OnInit {
  messages: Message[] = [];
  newMessage: string = '';
  currentUser: any = '';
  currentConversation: string = '';
  conversations: string[] = [];
  sidebarExpanded: boolean = true;
  teachersIDS: string[] = [];
  idSelected: any = ""
  classesIDS: string[] = [] ;
  connected: boolean = false;
  stompClient: any = null
  subscription: any = null;

  constructor(private chatService: ChatService, private route: ActivatedRoute, private routes: Router) {}

  async getMessageHistory(){
    if(this.currentUser == "student"){
      const response = await fetch("http://localhost:8080/getMessageHistory/" + localStorage.getItem("classID") + "," + this.idSelected,{
        method: "GET",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      })

      response.json().then(data =>{
        this.chatService.deleteAllMessages(this.currentConversation)
        for(let i = 0; i <= data.length-1; i++){
          const frontendMessage: Message = {
            sender: data[i].senderName,
            content: data[i].messageText,
            timestamp: new Date(),
          };
          this.chatService.addMessage(this.currentConversation,frontendMessage)
        }

        this.chatService.getMessages(this.currentConversation).subscribe((messages) => {
          this.messages = messages;
        });
        this.chatService.loadConversation(this.currentConversation)
      })
    }
  }

  onPublicMessageRecived(payload: any) {
    let payloadData = JSON.parse(payload.body);
    
    const frontendMessage: Message = {
      sender: payloadData.senderName,
      content: payloadData.message,
      timestamp: new Date(),
    };
    this.chatService.addMessage(this.currentConversation,frontendMessage)
  }

  async onSockJSConnected(){
    if(this.subscription) this.subscription.unsubscribe();

    if(this.currentUser == "student"){
      this.subscription = this.stompClient.subscribe(`/chatroom/${this.idSelected}/${localStorage.getItem("classID")}`, this.onPublicMessageRecived.bind(this));
    }
    
    if(this.currentUser == "teacher"){
      this.subscription = this.stompClient.subscribe(`/chatroom/${localStorage.getItem("userID")}/${this.idSelected}`,this.onPublicMessageRecived.bind(this))
    }
  }

  async ngOnInit(): Promise<void> {
    if(this.currentUser == "student"){
      const verifyResponse = await fetch("http://localhost:8080/auth/verifyStudentToken", {
        method: "POST",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      });
  
      if (verifyResponse.status === 403) {
        this.routes.navigate(["403"]);
        return;
      }  
    }

    if(this.currentUser == "teacher"){
      const verifyResponse = await fetch("http://localhost:8080/auth/verifyTeacherToken", {
        method: "POST",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      });
  
      if (verifyResponse.status === 403) {
        this.routes.navigate(["403"]);
        return;
      }  
    }
    

    this.route.paramMap.subscribe(params => {
      const currentUser = params.get('chatUser');
      this.currentUser = currentUser
    });
    this.loadConversations();
  }

  async loadConversations(): Promise<void> {
    if (this.currentUser === 'student') {
      const response = await fetch("http://localhost:8080/admin/teacherDataManager/getAllTeacherFromClass/" + localStorage.getItem("classID"),{
        method: "GET",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      })

      if(response.status == 200){
        response.json().then(data =>{
          for(let i = 0; i <= data.length-1;i++){
            if(!this.teachersIDS.includes(data[i].teacherID)){
              this.teachersIDS.push(data[i].teacherID)
              this.conversations.push(data[i].teacherName)
            }
          }
        });
      }
    } else if(this.currentUser === 'teacher') {
      const response = await fetch("http://localhost:8080/teacher/getAllClassesFromTeacher/" + localStorage.getItem("userID"),{
        method: "GET",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token")
        }
      })

      response.json().then(data =>{
        for(let i = 0; i <= data.length-1;i++){
          if(!this.teachersIDS.includes(data[i].teacherID)){
            this.classesIDS.push(data[i].classID)
            this.conversations.push(data[i].className)
          }
        }
      });
    }
  }

  loadConversation(conversationId: string): void {
    if(this.currentUser == "student"){
      this.conversations.forEach((con, index) =>{
        if(con == conversationId){
          this.currentConversation = conversationId
          this.idSelected = this.teachersIDS.at(index);

          this.chatService.loadConversation(conversationId)
          this.getMessageHistory();
        }
      })

       let sock = new SockJS("http://localhost:8080/ws",);
       this.stompClient = over(sock);
       this.stompClient.connect({},this.onSockJSConnected.bind(this))
    }

    if(this.currentUser == "teacher"){
      this.conversations.forEach((con, index) =>{
        if(con == conversationId){
          this.currentConversation = conversationId
          this.chatService.loadConversation(conversationId)
          this.idSelected = this.classesIDS.at(index)
          this.chatService.getMessages(conversationId).subscribe((messages) => {
            this.messages = messages;
          });
        }
      })

      let sock = new SockJS("http://localhost:8080/ws",);
      this.stompClient = over(sock);
      this.stompClient.connect({},this.onSockJSConnected.bind(this))
    }
   
  }

  sendMessage(): void {
    if (this.newMessage.trim() !== '') {
      const message: Message = {
        sender: localStorage.getItem("username"),
        content: this.newMessage,
        timestamp: new Date(),
      };

      if(this.currentUser == "student"){
        let backendMessage = {
          senderName: message.sender,
          message: this.newMessage,
        }
        this.stompClient.send(`/app/sendMessage/${this.idSelected}/${localStorage.getItem("classID")}`, {},JSON.stringify(backendMessage)) 
        this.newMessage = ''; 
      }

      if(this.currentUser == "teacher"){
        let backendMessage = {
          senderName: "Professor " + message.sender,
          message: this.newMessage,
        }
        
        this.stompClient.send(`/app/sendMessage/${localStorage.getItem("userID")}/${this.idSelected}`, {},JSON.stringify(backendMessage))
        this.newMessage = '';
      }
      
    }
  }

  back(){
      if(this.currentUser == "student"){
        this.routes.navigate(["student"])
      }

      if(this.currentUser == "teacher"){
        this.routes.navigate(["teacher"])
      }
  }
}
