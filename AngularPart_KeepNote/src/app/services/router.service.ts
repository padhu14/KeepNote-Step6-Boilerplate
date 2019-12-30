import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Injectable()
export class RouterService {

  constructor(public router: Router, private location: Location) { }

  routeBack() {
    this.location.back();
  }

  routeToDashboard() {
    this.router.navigate(['dashboard']);
  }

  routeToLogin() {
    this.router.navigate(['login']);
  }

  routeToSignUp() {
    this.router.navigate(['signup']);
  }

  routeToNoteView() {
    this.router.navigate(['dashboard/view/noteview']);
  }

  routeToListView() {
    this.router.navigate(['dashboard/view/listview']);
  }

  routeToCategory() {
    this.router.navigate(['dashboard/category']);
  }

  routeToReminder() {
    this.router.navigate(['dashboard/reminder']);
  }

  routeToUser() {
    this.router.navigate(['user']);
  }

  routeToEditNoteView(noteId) {
    this.router.navigate(['dashboard', {
      outlets: {
        noteEditOutlet: ['note', noteId, 'edit']
      }
    }]);
  }

  routeToEditCategoryView(categoryId) {
    this.router.navigate(['dashboard/category', {
      outlets: {
        categoryEditOutlet: ['category', categoryId, 'edit']
      }
    }]);
  }

  routeToEditReminderView(reminderId) {
    this.router.navigate(['dashboard/reminder', {
      outlets: {
        reminderEditOutlet: ['reminder', reminderId, 'edit']
      }
    }]);
  }

  routeToAddReminderView(noteId){
    this.router.navigate(['dashboard', {
      outlets: {
        noteReminderOutlet: ['note', noteId, 'addreminder']
      }
    }]);
  }

  routeToEditUserView(userId){
    this.router.navigate(['user/edit',userId]);
  }

  routeToUserDetails(){
    this.router.navigate(['user']);
  }

  routeToCategoryNotes(categoryId){
    this.router.navigate(['dashboard/category/view/noteview',categoryId]);
  }

  routeToReminderNotes(reminderId){
    this.router.navigate(['dashboard/reminder/view/noteview',reminderId]);
  }

}
