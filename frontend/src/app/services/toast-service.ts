import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ToastParams } from '../../@types/types';

@Injectable({
  providedIn: 'root',
})
export class ToastService {
  private toastSubject = new BehaviorSubject<ToastParams | null>(null);
  toast$ = this.toastSubject.asObservable();
  showToast = false;

  show(params: ToastParams, duration = 5000) {
    this.toastSubject.next(params);
    this.showToast = true;
    setTimeout(() => {
      this.showToast = false;
      this.toastSubject.next(null);
    }, duration);
  }
}
