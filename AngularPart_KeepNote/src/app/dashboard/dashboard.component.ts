import { Component } from '@angular/core';
import { RouterService } from '../services/router.service';
import { NotesService } from '../services/notes.service';
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})

export class DashboardComponent implements OnInit{

  isNoteView = false;
  userId: string;


  constructor(private routeService: RouterService) {
  
  }

  ngOnInit(){
    this.userId = localStorage.getItem('userId');
  }
  routeToNoteView() {
    this.isNoteView = true;
    this.routeService.routeToNoteView();
  }

  routeToListView() {
    this.isNoteView = true;
    this.routeService.routeToListView();
  }

  routeToCategory() {
    this.isNoteView = false;
    this.routeService.routeToCategory();
  }

  routeToReminder() {
    this.isNoteView = false;
    this.routeService.routeToReminder();
  }
}
